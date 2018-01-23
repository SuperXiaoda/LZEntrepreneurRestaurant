package cn.com.lz.entrepreneur.restaurant.model;

import com.google.gson.annotations.SerializedName;

/**
 * Description: 接口返回数据基础结构
 * author: CodingHornet
 * Date: 2017/7/3 14:27
 */
public class HttpResult<T> {

    // 返回码
    @SerializedName("code")
    private int code;
    // 消息
    @SerializedName("message")
    private String message;
    // 数据
    @SerializedName("data")
    private T data;
    // 平台异常信息
    @SerializedName("EAF_ERROR")
    private String error;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
