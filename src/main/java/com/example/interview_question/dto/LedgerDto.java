package com.example.interview_question.dto;

import com.example.interview_question.entry.Ledger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LedgerDto extends Ledger {
    private String name;
    private Integer pageCurrentNumber;
    private Integer pageDataLength;
}
