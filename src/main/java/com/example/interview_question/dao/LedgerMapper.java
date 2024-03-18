package com.example.interview_question.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.interview_question.entry.Ledger;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LedgerMapper extends BaseMapper<Ledger> {
    @Select("select * from ledgers left join products p on ledgers.product_id = p.id where p.name like #{name} and ledgers.time >= #{time} order by ledgers.time desc  limit #{pageNum} , #{length} ")
    List<Ledger> getLedgersByPage(@Param("name") String name, @Param("time") long time, @Param("pageNum") int pageNum, @Param("length") int length);

    @Select("select * from ledgers left join products p on ledgers.product_id = p.id where p.name like #{name} and ledgers.time >= #{time}")
    List<Ledger> getAllLedgers(@Param("name") String name, @Param("time") long time);
}
