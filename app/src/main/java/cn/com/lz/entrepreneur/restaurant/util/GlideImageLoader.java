package cn.com.lz.entrepreneur.restaurant.util;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.ImageViewTarget;

import cn.com.lz.entrepreneur.restaurant.R;
import cn.com.lz.entrepreneur.restaurant.config.GlideApp;
import cn.finalteam.galleryfinal.widget.GFImageView;


/**
 * Description: 图片选择组件的图片加载器
 * Author:CodingHornet
 * Date:2016/6/6 0009 18:18
 */
public class GlideImageLoader implements cn.finalteam.galleryfinal.ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, final GFImageView imageView,
                             Drawable defaultDrawable, int width, int height, boolean isCenterCrop) {
        GlideApp.with(activity)
                .load("file://" + path)
                .centerCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.place)
                .error(R.drawable.place)
                .into(new ImageViewTarget<Drawable>(imageView) {
                    @Override
                    protected void setResource(@Nullable Drawable resource) {
                        imageView.setImageDrawable(resource);
                    }

                    @Override
                    public void setRequest(@Nullable Request request) {
                        imageView.setTag(R.id.adapter_item_tag_key, request);
                    }

                    @Nullable
                    @Override
                    public Request getRequest() {
                        return (Request) imageView.getTag(R.id.adapter_item_tag_key);
                    }
                });
    }

    @Override
    public void clearMemoryCache() {

    }
}
