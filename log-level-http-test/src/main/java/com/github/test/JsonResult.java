package com.github.test;


import java.io.Serializable;

public class JsonResult implements Serializable {

    private String code;
    private String msg;
    private Object data;

    public JsonResult() {
    }


    public JsonResult(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static JsonResult success() {
        return success(null);
    }

    public static JsonResult success(Object data) {
        return new JsonResult("success","", data);
    }

    public static JsonResult error(String code, String msg, Object data) {
        return new JsonResult(code, msg, data);
    }

    public static JsonResult error(String msg) {
        return new JsonResult("2000", msg, null);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
