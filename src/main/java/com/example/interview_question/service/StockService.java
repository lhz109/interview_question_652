package com.example.interview_question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.interview_question.entry.Stock;
import com.example.interview_question.dto.ProductDto;
import com.example.interview_question.dto.TotalDto;

public interface StockService extends IService<Stock> {
    Integer outStore(ProductDto productDto);

    Integer inStore(ProductDto productDto);

    TotalDto statsProduct(String id);

    void addLedger(ProductDto productDto, int isStore);
}
