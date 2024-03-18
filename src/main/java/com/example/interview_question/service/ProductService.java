package com.example.interview_question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.interview_question.entry.Product;
import com.example.interview_question.dto.ProductDto;
import com.example.interview_question.vo.PageInfo;

public interface ProductService extends IService<Product> {
    PageInfo getProductsByPage(ProductDto productDto);
}
