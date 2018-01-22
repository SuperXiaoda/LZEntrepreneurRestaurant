package cn.com.lz.entrepreneur.restaurant;

import android.app.Application;

/**
 * Description:App
 * author: lhd
 * Date: 2018/1/22
 */
public class App extends Application {

    private static App mInstance;

    /**
     * 获取Application实例
     *
     * @return Application实例
     */
    public static App getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
