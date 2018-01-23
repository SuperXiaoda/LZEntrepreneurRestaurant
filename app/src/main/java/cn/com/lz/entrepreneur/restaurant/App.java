package cn.com.lz.entrepreneur.restaurant;

import android.app.Application;
import android.support.v4.content.ContextCompat;

import com.tencent.bugly.crashreport.CrashReport;

import java.io.File;

import cn.com.lz.entrepreneur.restaurant.util.FileUtil;
import cn.com.lz.entrepreneur.restaurant.util.GlideImageLoader;
import cn.com.lz.entrepreneur.restaurant.util.GlidePauseOnScrollListener;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;

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
        // 初始化图片选择模块
        initGalleryFinal();
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

    /**
     * Description: 初始化图片选择模块
     * author: CodingHornet
     * Date: 2016/6/21 11:14
     */
    private void initGalleryFinal() {
        // 主题
        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarBgColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setFabNornalColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setFabPressedColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .setCheckSelectedColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setCropControlColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .build();
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableRotate(true)
                .setEnablePreview(true)
                .setEnableCrop(true)
                .setMutiSelectMaxSize(Constants.MAX_PICTURES)
                .build();
        CoreConfig coreConfig = new CoreConfig.Builder(this, new GlideImageLoader(), theme)
                .setFunctionConfig(functionConfig)
                .setPauseOnScrollListener(new GlidePauseOnScrollListener(false, false))
                // 图片选择库拍照路径
                .setTakePhotoFolder(new File(FileUtil.getDiskDir(Constants.PATH_TAKE_PHOTO)))
                // 图片选择库编辑路径
                .setEditPhotoCacheFolder(new File(FileUtil.getDiskCacheDir(getApplicationContext(), Constants.PATH_EDIT)))
                .setNoAnimcation(true)
                .build();
        GalleryFinal.init(coreConfig);
        GalleryFinal.cleanCacheFile();
    }

}
