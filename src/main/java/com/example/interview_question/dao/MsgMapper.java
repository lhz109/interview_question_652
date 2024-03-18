package com.example.interview_question.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.interview_question.entry.Msg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MsgMapper extends BaseMapper<Msg> {
    @Update("update msgs set is_read = 1 where id =#{id}")
    int updateMsgReadState(@Param("id") String id);
}
