package cn.com.lz.entrepreneur.restaurant.api;

/**
 * Description:API常量类
 * author: lhd
 * Date: 2018/1/23
 */

public interface APIConstants {
    // 网络请求超时时间（秒）
    int TIME_OUT = 60;
    // 分页加载初始页
    int PAGE_START = 1;
    // 通用列表每页加载条数
    int LIMIT_COMMON = 10;
    // 简单条目列表每页加载条数
    int LIMIT_MORE = 20;
    // 请求编码
    String API_ENCODED = "Content-Type: application/x-www-form-urlencoded;charset=UTF-8";
    /**
     * 接口请求码
     */
    // 请求成功
    int CODE_SUCCESS = 200;
    // 登陆失败
    int CODE_LOGIN_FAILED = 500;
    // 平台异常
    int CODE_ERROR_PLATFORM = 7777;
    // 接口请求失败
    int CODE_ERROR_API = 8888;
    // 上传成功
    String CODE_UPLOAD_SUCCESS = "SUCCESS";
    /**
     * 平台返回异常信息
     */
    // 登录过期
    String MESSAGE_LOGIN_EXPIRED_1 = "ERR_001";
    // sso单点登录错误
    String ERROR_SSO_EXCEPTION_VALUE = "TXIAutoLoginException";
    // 未登录
    String MESSAGE_NOT_LOGIN = "请求：null失败！";

    // 默认服务器地址
    String DEFAULT_SERVICE_URL="";
}
