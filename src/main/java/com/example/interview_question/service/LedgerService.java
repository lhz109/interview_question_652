package com.example.interview_question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.interview_question.dto.LedgerDto;
import com.example.interview_question.entry.Ledger;
import com.example.interview_question.vo.PageInfo;

public interface LedgerService extends IService<Ledger> {
    PageInfo getLedgersByPage(LedgerDto ledgerDto);
}
