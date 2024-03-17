package com.example.interview_question.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.interview_question.dao.ProductMapper;
import com.example.interview_question.entry.Product;
import com.example.interview_question.service.ProductService;
import com.example.interview_question.vo.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Override
    public List<Product> getProductsByPage(ProductDto productDto) {
        LambdaQueryWrapper<Product> condition = new LambdaQueryWrapper<>();
        condition.like(Product::getName, productDto.getName());
        condition.orderByDesc(Product::getId);
        Page<Product> page = new Page<>(productDto.getPageCurrentNumber(), productDto.getPageDataLength(), false);
        IPage<Product> result = baseMapper.selectPage(page, condition);
        return result.getRecords();
    }
}
