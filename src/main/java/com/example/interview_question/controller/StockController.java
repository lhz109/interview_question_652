package com.example.interview_question.controller;

import com.example.interview_question.dto.ProductDto;
import com.example.interview_question.dto.TotalDto;
import com.example.interview_question.service.StockService;
import com.example.interview_question.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock")
@CrossOrigin
public class StockController {

    @Autowired
    StockService service;

    @PutMapping("/out")
    public Result out(@RequestBody ProductDto productDto) {
        Integer number = service.outStore(productDto);
        if (number != null) {
            return Result.success(number);
        }
        return Result.error("抱歉，出库失败");
    }

    @PutMapping("/in")
    public Result in(@RequestBody ProductDto productDto) {
        Integer number = service.inStore(productDto);
        if (number != null) {
            return Result.success(number);
        }
        return Result.error("抱歉，入库失败");
    }

    @GetMapping("/{id}")
    public Result getTotal(@PathVariable String id) {
        TotalDto totalDto = service.statsProduct(id);
        return Result.success(totalDto);
    }
}
