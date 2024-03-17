package com.example.interview_question.entry;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("products")
public class Product {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    @TableField
    private String name;
    @TableField
    private Float price;
    @TableField
    private String specification;
    @TableLogic(value = "0", delval = "1")
    private Integer deleted;
}
