package com.example.interview_question.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("stocks")
public class Stock {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    @TableField("product_id")
    private String productId;
    @TableField("store_id")
    private String storeId;
    @TableField
    private Integer quantity;
}
