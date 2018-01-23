package cn.com.lz.entrepreneur.restaurant.config;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

@GlideModule
public final class CustomAppGlideModule extends AppGlideModule {

    /**
     * 通过GlideBuilder设置默认的结构(Engine,BitmapPool ,ArrayPool,MemoryCache等等).
     *
     * @param context
     * @param builder
     */
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // 设置日志级别
        builder.setLogLevel(Log.ERROR);
        //重新设置内存限制
        builder.setMemoryCache(new LruResourceCache(100 * 1024 * 1024));
    }

//
//    /**
//     * 为App注册一个自定义的String类型的BaseGlideUrlLoader
//     *
//     * @param context
//     * @param registry
//     */
//    @Override
//    public void registerComponents(Context context, Registry registry) {
//
//             registry.append(String.class, InputStream.class,new CustomBaseGlideUrlLoader.Factory());
//    }

    /**
     * 清单解析的开启
     * <p>
     * 这里不开启，避免添加相同的modules两次
     *
     * @return
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}