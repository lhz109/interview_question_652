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
@TableName("msgs")
public class Msg {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    @TableField
    private String content;
    @TableField
    private Long time;
    @TableField("is_read")
    private Integer readState;
}
