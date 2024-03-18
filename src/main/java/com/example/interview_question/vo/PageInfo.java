package com.example.interview_question.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo {
    private Integer current;
    private Integer size;
    private Long total;
    private Object data;
}
