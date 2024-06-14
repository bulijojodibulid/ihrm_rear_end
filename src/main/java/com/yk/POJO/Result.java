package com.yk.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result {
    Integer code;
    String msg;
    Object data;

    public static Result success(){
        return new Result(200, "success", null);
    }

    public static Result success(Object data){
        return new Result(200, "success", data);
    }

    public static Result error(String msg){
        return new Result(500, msg, null);
    }
}
