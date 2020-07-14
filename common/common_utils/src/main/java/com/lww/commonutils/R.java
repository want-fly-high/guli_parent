package com.lww.commonutils;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data
//统一返回结果
public class R {
    @ApiModelProperty
   private Boolean success;

    @ApiModelProperty
   private Integer code;

    @ApiModelProperty
   private String message;

    @ApiModelProperty
   private Map<String,Object> data = new HashMap<>();

    private R(){ }
//成功方法
    public static R ok(){
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return  r;
    }

    //失败方法
    public static R error(){
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return  r;
    }

    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
