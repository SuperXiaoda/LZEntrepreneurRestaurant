package cn.com.lz.entrepreneur.restaurant.api;

import android.text.TextUtils;

/**
 * Description:处理API返回异常
 * author: lhd
 * Date: 2018/1/23
 */

public class APIException extends RuntimeException {

    private int mCode;

    APIException(int resultCode, String responseMessage) {

    }

    private APIException(String detailMessage) {
        super(detailMessage);
    }

    /**
    *Description:获取异常编码
    *author:lhd
    *Date:2018/1/23
    */
    public int getCode() {
        return this.mCode;
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     *
     * @param code API错误编码
     * @return 异常提示信息
     */
    private static String getApiExceptionMessage(int code, String responseMessage) {
        String message;
        switch (code) {
            default:
                message = TextUtils.isEmpty(responseMessage) ? "未知错误" : responseMessage;
        }
        return message;
    }

}
