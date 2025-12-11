package cn.edu.zjut.backend.util;

import java.io.Serializable;

/**
 * 通用 JSON 返回结果
 */
public class Response<T> implements Serializable {
    // 响应状态码（200成功，500失败）
    private Integer code;
    // 响应消息
    private String msg;
    // 响应数据
    private T data;

    // 成功响应（带数据）
    public static <T> Response<T> success(T data) {
        Response<T> result = new Response<T>();
        result.setCode(200);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    // 成功响应（无数据）
    public static <T> Response<T> success() {
        return success(null);
    }

    // 失败响应
    public static <T> Response<T> error(String msg) {
        Response<T> result = new Response<>();
        result.setCode(500);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public Response() {}

    public Response(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}