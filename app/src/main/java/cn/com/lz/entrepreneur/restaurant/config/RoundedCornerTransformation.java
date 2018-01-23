package cn.com.lz.entrepreneur.restaurant.config;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.security.MessageDigest;

/**
 * Description: Glide图片圆角转换
 * author: CodingHornet
 * Date: 2017/8/8 14:13
 */
public class RoundedCornerTransformation extends BitmapTransformation {

    private static final String ID = "cn.com.thit.ticwr.configp.RoundedCornerTransformation";
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

    private static float radius = 0f;

    public RoundedCornerTransformation() {
        this(4);
    }

    public RoundedCornerTransformation(int dp) {
        radius = Resources.getSystem().getDisplayMetrics().density * dp;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
//        int size = Math.min(toTransform.getWidth(), toTransform.getHeight());
//        int x = (toTransform.getWidth() - size) / 2;
//        int y = (toTransform.getHeight() - size) / 2;
//
//        Bitmap squared = Bitmap.createBitmap(toTransform, x, y, size, size);
//
//        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
//        if (result == null) {
//            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
//        }

//        Canvas canvas = new Canvas(result);
//        Paint paint = new Paint();
//        //画布中背景图片与绘制图片交集部分
//        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
//        paint.setAntiAlias(true);
//        float r = size / 2f;
//        canvas.drawCircle(r, r, r, paint);
//        return result;

//        int width = toTransform.getWidth();
//        int height = toTransform.getHeight();
//
//        Bitmap bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888);
//        if (bitmap == null) {
//            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        }
//
//        Canvas canvas = new Canvas(bitmap);
//        Paint paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setShader(new BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
////        drawRoundRect(canvas, paint, width, height);
//        canvas.drawRoundRect(new RectF(0, 0, width, height), mRadius, mRadius, paint);
//        return toTransform;

        return roundCrop(pool, toTransform);
    }

    private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CenterCrop;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }
}
