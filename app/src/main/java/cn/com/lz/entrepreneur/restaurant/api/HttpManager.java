package cn.com.lz.entrepreneur.restaurant.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import cn.com.lz.entrepreneur.restaurant.App;
import cn.com.lz.entrepreneur.restaurant.BuildConfig;
import cn.com.lz.entrepreneur.restaurant.R;
import cn.com.lz.entrepreneur.restaurant.util.ToastUtil;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Description: Http请求工具
 * author: CodingHornet
 * Date: 2016/9/26 14:08
 */
public class HttpManager {

    // Retrofit对象
    private Retrofit mRetrofit;
    // 服务器格式错误异常
    private final static Throwable THROWABLE_ERROR_SERVER = new Throwable("服务器地址格式错误！");


    //构造方法私有
    private HttpManager() {
    }

    // 在访问HttpManager时创建单例
    private static class SingletonHolder {
        private static final HttpManager INSTANCE = new HttpManager();
    }

    /**
     * Description: 获取Retrofit对象
     * author: CodingHornet
     * Date: 2016/9/26 15:45
     */
    private Retrofit getRetrofit() {
        if (mRetrofit == null) {
            synchronized (Retrofit.class) {
                OkHttpClient okHttpClient;
                // 配置OkHttp
                OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                        .connectTimeout(APIConstants.TIME_OUT, TimeUnit.SECONDS)
                        .readTimeout(APIConstants.TIME_OUT, TimeUnit.SECONDS)
                        .writeTimeout(APIConstants.TIME_OUT, TimeUnit.SECONDS)
                        .cookieJar(new CookiesManager(App.getInstance().getApplicationContext()))
                        .retryOnConnectionFailure(true);
                if (BuildConfig.DEBUG) {
                    // 添加Http日志拦截器
                    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    okHttpBuilder.addInterceptor(loggingInterceptor);
                }
                okHttpClient = okHttpBuilder.build();

                try { // 创建Retrofit
                    Gson gson = new GsonBuilder()
                            .setLenient()
                            .create();

                    mRetrofit = new Retrofit.Builder()
                            .baseUrl(APIConstants.DEFAULT_SERVICE_URL)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(okHttpClient)
                            .build();
                    // 初始化Services
                    initServices();
                } catch (IllegalArgumentException e) {
                    ToastUtil.showShortSafe(R.string.invalid_server_address);
                    mRetrofit = null;
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            if (!mRetrofit.baseUrl().equals(APIConstants.DEFAULT_SERVICE_URL)) {
                mRetrofit = null;
                getRetrofit();
            }
        }
        return mRetrofit;
    }

    /**
     * Description: 设置Retrofit
     * author: zs
     * Date: 2017/7/6 11:42
     **/
    public void setRetrofit(Retrofit retrofit) {
        this.mRetrofit = retrofit;
    }

    /**
     * Description: 获取服务器url
     * author: zs
     * Date: 2017/07/24 16:42
     */
    public String getBaseUrl() {
        return mRetrofit == null ? "" : mRetrofit.baseUrl().toString();
    }

    /**
     * Description: 更新服务器地址
     * author: CodingHornet
     * Date: 2017/7/6 11:42
     */
    public void updateServerHost() {
        if (mRetrofit != null) {
            synchronized (Retrofit.class) {
                mRetrofit = null;
            }
            getRetrofit();
        }
    }

    /**
     * Description: 初始化Services
     * author: CodingHornet
     * Date: 2016/9/26 15:36
     */
    private void initServices() {

    }

    /**
     * Description: 获取网络工具单例
     * author: CodingHornet
     * Date: 2016/9/26 15:59
     */
    public static HttpManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * Description: 检查服务器地址
     * author: CodingHornet
     * Date: 2016/9/26 15:59
     */
    private boolean checkServer(Observer observer) {
        if (getRetrofit() != null) {
            return true;
        } else {
            observer.onError(THROWABLE_ERROR_SERVER);
            return false;
        }
    }

    /**
     * Description: 添加线程管理并订阅
     * author: CodingHornet
     * Date: 2016/9/26 15:12
     */
    @SuppressWarnings("unchecked")
    private void toSubscribe(Observable<?> observable, Observer observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunction())
                .subscribe(observer);
    }
}
