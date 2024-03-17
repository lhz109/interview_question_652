package com.example.interview_question.controller;

import com.example.interview_question.entry.Product;
import com.example.interview_question.service.ProductService;
import com.example.interview_question.vo.ProductDto;
import com.example.interview_question.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@CrossOrigin
public class ProductController {

    @Autowired
    ProductService service;

    @GetMapping()
    public Result getProducts(@RequestParam(name = "current") Integer current, @RequestParam(name = "length") Integer length, @RequestParam(name = "name") String name) {
        ProductDto condition = new ProductDto();
        condition.setName(name);
        condition.setPageDataLength(length);
        condition.setPageCurrentNumber(current);
        return Result.success(service.getProductsByPage(condition));
    }

    @PostMapping
    public Result createProduct(@RequestBody Product product) {
        product.setDeleted(0);
        if (service.save(product)) {
            return Result.success();
        }
        return Result.error("抱歉，创建商品失败");
    }

    @PutMapping
    public Result updateProduct(@RequestBody Product product) {
        if (service.updateById(product)) {
            return Result.success();
        }
        return Result.error("抱歉，修改商品失败");
    }

    @DeleteMapping("/{id}")
    public Result deleteProduct(@PathVariable(value = "id") String id) {
        if (service.removeById(id)) {
            return Result.success();
        }
        return Result.error("抱歉，删除商品失败");
    }
}
