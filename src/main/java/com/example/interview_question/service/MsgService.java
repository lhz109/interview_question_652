package com.example.interview_question.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.interview_question.dto.MsgDto;
import com.example.interview_question.dto.ProductDto;
import com.example.interview_question.entry.Msg;
import com.example.interview_question.vo.PageInfo;

public interface MsgService extends IService<Msg> {
    //发送消息

    void sendMsg(ProductDto productDto);

    //定时器
    void sendPhoneMsg();

    //获取消息
    PageInfo getMsgsByPage(MsgDto msgDto);

    //读取消息
    boolean readMsg(String id);
}
