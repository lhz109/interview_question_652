package com.example.interview_question.vo;

import lombok.Data;

@Data
public class Result {
    private Integer code;
    private Object data;
    private String msg;

    public static Result success(Object o) {
        Result result = new Result();
        result.code = Code.OK;
        result.data = o;
        return result;
    }

    public static Result success() {
        Result result = new Result();
        result.code = Code.OK;
        return result;
    }

    public static Result error(String s) {
        Result result = new Result();
        result.code = Code.ERROR;
        result.msg = s;
        return result;
    }


}
