package com.example.interview_question.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.interview_question.dao.LedgerMapper;
import com.example.interview_question.dto.LedgerDto;
import com.example.interview_question.entry.Ledger;
import com.example.interview_question.service.LedgerService;
import com.example.interview_question.vo.PageInfo;
import org.springframework.stereotype.Service;

@Service
public class LedgerServiceImpl extends ServiceImpl<LedgerMapper, Ledger> implements LedgerService {
    @Override
    public PageInfo getLedgersByPage(LedgerDto ledgerDto) {
        ledgerDto.setName("%" + ledgerDto.getName() + "%");
        PageInfo pageInfo = new PageInfo();
        pageInfo.setData(baseMapper.getLedgersByPage(ledgerDto.getName(), ledgerDto.getTime(), (ledgerDto.getPageCurrentNumber() - 1) * ledgerDto.getPageDataLength(), ledgerDto.getPageDataLength()));
        pageInfo.setSize(ledgerDto.getPageDataLength());
        pageInfo.setCurrent(ledgerDto.getPageCurrentNumber());
        int total = baseMapper.getAllLedgers(ledgerDto.getName(), ledgerDto.getTime()).size();
        int pages = ((total / ledgerDto.getPageDataLength()));
        if (total % ledgerDto.getPageDataLength() != 0) {
            pages += 1;
        }
        pageInfo.setTotal((long) pages);
        return pageInfo;
    }
}
