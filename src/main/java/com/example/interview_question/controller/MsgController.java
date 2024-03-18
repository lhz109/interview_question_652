package com.example.interview_question.controller;

import com.example.interview_question.dto.MsgDto;
import com.example.interview_question.service.MsgService;
import com.example.interview_question.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/msg")
@CrossOrigin
public class MsgController {
    @Autowired
    MsgService service;

    @PutMapping("/{id}")
    public Result updateReadState(@PathVariable String id) {
        if (service.readMsg(id)) {
            return Result.success();
        }
        return Result.error("抱歉，读取消息失败");
    }

    @GetMapping
    public Result getMsgs(@RequestParam(name = "current") Integer current, @RequestParam(name = "length") Integer length, @RequestParam(name = "start") long start, @RequestParam(name = "end") long end) {
        MsgDto condition = new MsgDto();
        condition.setStart(start);
        condition.setEnd(end);
        condition.setPageDataLength(length);
        condition.setPageCurrentNumber(current);
        return Result.success(service.getMsgsByPage(condition));
    }

}
