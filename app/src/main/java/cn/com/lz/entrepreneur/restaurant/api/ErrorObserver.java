package cn.com.lz.entrepreneur.restaurant.api;

import android.app.Activity;

import com.google.gson.stream.MalformedJsonException;

import java.lang.ref.WeakReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import cn.com.lz.entrepreneur.restaurant.BuildConfig;
import cn.com.lz.entrepreneur.restaurant.R;
import cn.com.lz.entrepreneur.restaurant.util.ToastUtil;
import io.reactivex.Observer;


/**
 * Description: 统一处理异常Subscriber
 * author: lhd
 * Date: 2016/9/26 16:33
 */
public abstract class ErrorObserver<T> implements Observer<T> {

    // Activity弱引用
    private WeakReference<Activity> mWeakActivity;
    // 是否显示Toast信息，默认显示
    private boolean mIsShowToast = true;

    protected ErrorObserver(Activity activity) {
        this.mWeakActivity = new WeakReference<>(activity);
    }

    protected ErrorObserver(Activity activity, boolean isShowToast) {
        mWeakActivity = new WeakReference<>(activity);
        mIsShowToast = isShowToast;
    }

    @Override
    public void onError(Throwable t) {
        if (t instanceof SocketTimeoutException) { // 连接超时
            if (mIsShowToast) {
                ToastUtil.showShortSafe(R.string.connect_server_timeout);
            }
        } else if (t instanceof ConnectException) { // 连接异常
            if (mIsShowToast) {
                ToastUtil.showShortSafe(R.string.connect_server_fail);
            }
        } else if (t instanceof UnknownHostException) { // 未知主机异常
            if (mIsShowToast) {
                ToastUtil.showShortSafe(R.string.error_unknown);
            }
        } else if (t instanceof MalformedJsonException) { // 数据解析异常
            if (mIsShowToast) {
                ToastUtil.showShortSafe(R.string.error_json_parse);
            }
        } else if (t instanceof APIException) { // API异常
            String message = t.getMessage();
            if (message.contains(APIConstants.MESSAGE_LOGIN_EXPIRED_1)
                    || message.contains(APIConstants.ERROR_SSO_EXCEPTION_VALUE)) { // 登录过期
                ToastUtil.showShort(R.string.login_timeout);
                //TODO 重新登陆 reLogin();
            } else if (message.contains(APIConstants.MESSAGE_NOT_LOGIN)) { // 未登录
                //TODO 重新登陆 reLogin();
            } else {
                if (mIsShowToast) {
                    ToastUtil.showShort(t.getMessage());
                }
            }
        } else {
            if (mIsShowToast) {
                ToastUtil.showShortSafe(R.string.error_api);
            }
        }
        if (BuildConfig.DEBUG) {
            t.printStackTrace();
        }
    }

    @Override
    public void onComplete() {
    }

    /**
     * Description: 重新登陆
     * author: lhd
     * Date: 2016/9/27 9:33
     */
/*    private void reLogin() {
        Activity activity = mWeakActivity.get();
        if (activity != null) {
            HttpManager.getInstance().setRetrofit(null);
            Intent intent = new Intent(activity, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            activity.startActivity(intent);
        }
    }*/
}
