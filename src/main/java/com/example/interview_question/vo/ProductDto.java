package com.example.interview_question.vo;

import com.example.interview_question.entry.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto extends Product {
    private Integer pageCurrentNumber;
    private Integer pageDataLength;
}
