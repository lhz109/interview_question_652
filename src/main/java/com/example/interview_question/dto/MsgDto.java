package com.example.interview_question.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgDto {
    private Long start;
    private Long end;
    private Integer pageCurrentNumber;
    private Integer pageDataLength;
}
