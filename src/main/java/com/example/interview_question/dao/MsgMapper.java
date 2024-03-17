package com.example.interview_question.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.interview_question.entry.Msg;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MsgMapper extends BaseMapper<Msg> {
}
