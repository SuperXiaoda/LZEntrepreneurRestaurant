package cn.com.lz.entrepreneur.restaurant.api;


import cn.com.lz.entrepreneur.restaurant.model.HttpResult;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

import cn.com.lz.entrepreneur.restaurant.util.StringUtil;

/**
 * Description: 用来统一处理Http的code,并将HttpResult的Data部分剥离出来返回给subscriber
 * author: CodingHornet
 * Date: 2017/7/6 15:33
 *
 * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
 */
public class HttpResultFunction<T> implements Function<HttpResult<T>, T> {

    @Override
    public T apply(@NonNull HttpResult<T> tHttpResult) throws Exception {
        if (tHttpResult == null) {
            throw new APIException(APIConstants.CODE_ERROR_API, "");
        }
        if (tHttpResult.getCode() != APIConstants.CODE_SUCCESS) {
            if (StringUtil.isEmpty(tHttpResult.getError())) { // 程序错误信息
                throw new APIException(tHttpResult.getCode(), tHttpResult.getMessage());
            } else { // error不空，为平台异常
                throw new APIException(APIConstants.CODE_ERROR_PLATFORM, tHttpResult.getError());
            }
        }
        return tHttpResult.getData();
    }
}
