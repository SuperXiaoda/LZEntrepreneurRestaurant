package cn.com.lz.entrepreneur.restaurant;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

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
        // 初始化
        init();
    }

    private void init(){
        initBugly();
    }

    /**
    *Description:初始化bugly
    *author:lhd
    *Date:2018/1/22
    */
    private void initBugly(){
        // 设置开发设备
        CrashReport.setIsDevelopmentDevice(getApplicationContext(), BuildConfig.DEBUG);
        CrashReport.initCrashReport(getApplicationContext());
        CrashReport.startCrashReport();
    }

}
