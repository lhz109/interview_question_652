package com.example.interview_question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.interview_question.entry.Product;
import com.example.interview_question.vo.ProductDto;

import java.util.List;

public interface ProductService extends IService<Product> {
    List<Product> getProductsByPage(ProductDto productDto);
}
