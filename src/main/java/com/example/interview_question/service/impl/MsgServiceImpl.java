package com.example.interview_question.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.interview_question.dao.MsgMapper;
import com.example.interview_question.dto.MsgDto;
import com.example.interview_question.dto.ProductDto;
import com.example.interview_question.entry.Msg;
import com.example.interview_question.service.MsgService;
import com.example.interview_question.vo.PageInfo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableAsync
@EnableScheduling
public class MsgServiceImpl extends ServiceImpl<MsgMapper, Msg> implements MsgService {
    @Override
    @Async
    public void sendMsg(ProductDto productDto) {
        Msg msg = new Msg();
        msg.setContent("出库信息如下：" + productDto.toString());
        msg.setReadState(0);
        msg.setTime(System.currentTimeMillis());
        baseMapper.insert(msg);
    }

    //每天每小时每分钟第0秒都会执行这个定时方法
    @Override
    @Scheduled(cron = "0 * * * * ?")
    public void sendPhoneMsg() {
        //查询出库时间到现在的时间，大于三个小时小于三个小时一分钟的所有未读消息
        //现在的时间戳减去三个小时，如果小于，意味着已经超过三个小时
        long time1 = System.currentTimeMillis() - 1000 * 60 * 60 * 3;
        //现在时间戳减去三个小时一分钟，如果大于，意味着还没有超过三个小时一分钟
        Long time2 = time1 - 1000 * 60;
        LambdaQueryWrapper<Msg> condition = new LambdaQueryWrapper<>();
        condition.ge(Msg::getTime, time2).le(Msg::getTime, time1).eq(Msg::getReadState, 0);
        List<Msg> msgs = baseMapper.selectList(condition);
        //开个异步,发送短信
        sendPhoneMsgHelper(msgs);
    }

    @Async
    void sendPhoneMsgHelper(List<Msg> msgs) {
        msgs.forEach((e) -> {
            //发送短信
        });
    }

    @Override
    public PageInfo getMsgsByPage(MsgDto msgDto) {
        LambdaQueryWrapper<Msg> condition = new LambdaQueryWrapper<>();
        condition.ge(Msg::getTime, msgDto.getStart());
        condition.le(Msg::getTime, msgDto.getEnd());
        condition.orderByDesc(Msg::getTime);
        Page<Msg> page = new Page<>(msgDto.getPageCurrentNumber(), msgDto.getPageDataLength());
        IPage<Msg> result = baseMapper.selectPage(page, condition);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrent((int) result.getCurrent());
        pageInfo.setSize((int) result.getSize());
        pageInfo.setTotal(result.getPages());
        pageInfo.setData(result.getRecords());
        return pageInfo;
    }

    @Override
    public boolean readMsg(String id) {
        return baseMapper.updateMsgReadState(id) == 1;
    }
}
