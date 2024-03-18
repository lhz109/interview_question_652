package com.example.interview_question.controller;

import com.example.interview_question.dto.LedgerDto;
import com.example.interview_question.service.LedgerService;
import com.example.interview_question.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ledger")
@CrossOrigin
public class LedgerController {

    @Autowired
    LedgerService service;

    @GetMapping
    public Result getLedgers(@RequestParam(name = "current") Integer current, @RequestParam(name = "length") Integer length, @RequestParam(name = "name") String name, @RequestParam(name = "time") Long time) {
        LedgerDto condition = new LedgerDto();
        condition.setName(name);
        condition.setPageDataLength(length);
        condition.setPageCurrentNumber(current);
        condition.setTime(time);
        return Result.success(service.getLedgersByPage(condition));
    }
}
