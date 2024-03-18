package com.example.interview_question.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.interview_question.dao.StockMapper;
import com.example.interview_question.entry.Ledger;
import com.example.interview_question.entry.Product;
import com.example.interview_question.entry.Stock;
import com.example.interview_question.service.LedgerService;
import com.example.interview_question.service.MsgService;
import com.example.interview_question.service.ProductService;
import com.example.interview_question.service.StockService;
import com.example.interview_question.dto.ProductDto;
import com.example.interview_question.dto.TotalDto;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableAsync
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements StockService {

    @Autowired
    ProductService productService;

    @Autowired
    LedgerService ledgerService;

    @Autowired
    MsgService msgService;

    @Override
    @Transactional
    public Integer outStore(ProductDto productDto) {
        //检查出库数量是否足够
        LambdaQueryWrapper<Stock> condition = new LambdaQueryWrapper<>();
        condition.eq(Stock::getProductId, productDto.getId()).eq(Stock::getStoreId, productDto.getStoreId());
        Stock stock = baseMapper.selectOne(condition);
        if (stock != null) {
            if (stock.getQuantity() >= productDto.getNumber()) {
                //出库,并且使用乐观锁判断
                stock.setQuantity(stock.getQuantity() - productDto.getNumber());
                condition.ge(Stock::getQuantity, productDto.getNumber());
                if (baseMapper.update(stock, condition) > 0) {
                    //出库成功，开个异步注解，发送消息，记录流水
                    StockService stockService = (StockService) AopContext.currentProxy();
                    stockService.addLedger(productDto, 0);
                    msgService.sendMsg(productDto);
                    return stock.getQuantity();
                }
            }
        }
        return null;
    }

    @Override
    @Transactional
    public Integer inStore(ProductDto productDto) {
        LambdaQueryWrapper<Stock> condition = new LambdaQueryWrapper<>();
        condition.eq(Stock::getProductId, productDto.getId()).eq(Stock::getStoreId, productDto.getStoreId());
        Stock stock = baseMapper.selectOne(condition);
        //查询存在，使用乐观锁更新数据
        if (stock != null) {
            int lastQuantity = stock.getQuantity();
            stock.setQuantity(lastQuantity + productDto.getNumber());
            condition.eq(Stock::getQuantity, lastQuantity);
            if (baseMapper.update(stock, condition) > 0) {
                //入库成功，获取代理对象，开个异步，加个流水
                StockService stockService = (StockService) AopContext.currentProxy();
                stockService.addLedger(productDto, 1);
                return stock.getQuantity();
            }
            return null;
        }
        //查询不存在，先使用悲观锁锁住这段插入代码(对于同样的商品和门店而言)
        synchronized (productDto.getStoreId() + productDto.getId()) {
            //再使用乐观锁判断局势
            Stock stockForCheck = baseMapper.selectOne(condition);
            //存在，结束
            if (stockForCheck != null) {
                return null;
            }
            //依旧不存在，完成插入
            Stock insertStock = new Stock();
            insertStock.setQuantity(productDto.getNumber());
            insertStock.setStoreId(productDto.getStoreId());
            insertStock.setProductId(productDto.getId());
            if (baseMapper.insert(insertStock) > 0) {
                //入库成功，获取代理对象，开个异步，加个流水
                StockService stockService = (StockService) AopContext.currentProxy();
                stockService.addLedger(productDto, 1);
                return productDto.getNumber();
            }
        }
        return null;
    }

    @Override
    public TotalDto statsProduct(String id) {
        TotalDto totalDto = new TotalDto(0, 0F);
        LambdaQueryWrapper<Stock> condition = new LambdaQueryWrapper<>();
        condition.eq(Stock::getProductId, id);
        List<Stock> stocks = baseMapper.selectList(condition);
        stocks.forEach((stock -> totalDto.setNumber(totalDto.getNumber() + stock.getQuantity())));
        Product product = productService.getById(id);
        totalDto.setPrice(product.getPrice() * totalDto.getNumber());
        return totalDto;
    }

    @Override
    @Async
    public void addLedger(ProductDto productDto, int isStore) {
        Ledger ledger = new Ledger();
        ledger.setQuantity(productDto.getNumber());
        ledger.setStoreId(productDto.getStoreId());
        ledger.setStorage(isStore);
        ledger.setProductId(productDto.getId());
        ledger.setTime(System.currentTimeMillis());
        ledgerService.save(ledger);
    }


}
