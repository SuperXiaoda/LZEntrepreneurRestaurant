package cn.com.lz.entrepreneur.restaurant.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.com.lz.entrepreneur.restaurant.Constants;


/**
 * description：图片工具
 * author：CodingHornet
 * date：2015/11/3 0003 15:29
 */
public class ImageUtil {

    // 上传图片压缩尺寸限制
    private static final int IMAGE_COMPRESS_MAX_PIXEL = 1600;
    // 上传图片压缩大小限制
    private static final int IMAGE_COMPRESS_MAX_SIZE = 200;

    /**
     * 通过图片路径压缩图片并返回压缩后路径
     *
     * @param sourcePath 源文件路径
     * @return 压缩后文件路径
     */
    public static String compressImage(Context context, String sourcePath) {
        try {
            if (new File(sourcePath).length() / 1024 < IMAGE_COMPRESS_MAX_SIZE) {
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;// 不去真的解析图片，只是获取图片的头部信息，包含宽高等；
                BitmapFactory.decodeFile(sourcePath, opts);
                // 得到图片的宽度、高度；
                if (opts.outWidth <= IMAGE_COMPRESS_MAX_PIXEL && opts.outHeight <= IMAGE_COMPRESS_MAX_PIXEL) {
                    return sourcePath;
                }
            }
            boolean isPng = isPng(sourcePath);
            return compressQualityAndSave(compressAndRotateBySize(sourcePath, IMAGE_COMPRESS_MAX_PIXEL),
                    IMAGE_COMPRESS_MAX_SIZE, FileUtil.getDiskCacheDir(context, Constants.PATH_UPLOAD),
                    StringUtil.createFileNameWithExtension(isPng ? "png" : "jpg"), isPng);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据文件路径压缩图片到最大宽高范围内
     *
     * @param path    图片路径
     * @param maxSize 压缩后最大宽高
     * @return
     */
    public static Bitmap compressAndRotateBySize(String path, int maxSize) {
        int angle = getPictureAngle(path);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;// 不去真的解析图片，只是获取图片的头部信息，包含宽高等；
        BitmapFactory.decodeFile(path, opts);
        // 得到图片的宽度、高度；
        int imgWidth = opts.outWidth;
        int imgHeight = opts.outHeight;
        // 分别计算图片宽度、高度与目标宽度、高度的比例；取大于等于该比例的最小整数；
        int widthRatio = (int) Math.ceil(imgWidth / (float) maxSize);
        int heightRatio = (int) Math.ceil(imgHeight / (float) maxSize);
        if (widthRatio > 1 || heightRatio > 1) {
            if (widthRatio > heightRatio) {
                opts.inSampleSize = widthRatio;
            } else {
                opts.inSampleSize = heightRatio;
            }
            if (opts.inSampleSize < 1)
                opts.inSampleSize = 1;
        }
        // 设置好缩放比例后，加载图片进内容；
        opts.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, opts);
        // 计算宽高缩放率
        float scaleSize = bitmap.getWidth() > bitmap.getHeight() ? bitmap.getWidth() : bitmap.getHeight();
        float scale = ((float) maxSize) / scaleSize;
        if (angle != 0 || scale < 1) {
            // 精确旋转、压缩图片
            Matrix matrix = new Matrix();
            if (angle != 0)
                matrix.postRotate(angle);
            if (scale < 1)
                // 缩放图片动作
                matrix.postScale(scale, scale);
            // 创建新的图片
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        return bitmap;
    }

    /**
     * 质量压缩图片并保存为文件
     *
     * @param bitmap   源图Bitmap
     * @param maxSize  最大文件大小
     * @param filePath 存储目录
     * @param fileName 存储文件名
     * @param isPng    是否为PNG格式
     * @return 压缩后完整路径
     * @throws Exception
     */
    public static String compressQualityAndSave(Bitmap bitmap, int maxSize, String filePath, String fileName, boolean isPng) throws Exception {
        // 创建存储文件
        File saveFile = new File(filePath + File.separator + fileName);
        if (saveFile.exists()) {
            saveFile.delete();
        }
        if (!new File(filePath).exists()) {
            new File(filePath).mkdirs();
        }
        saveFile.createNewFile();
        // 存储压缩后文件字节流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 初始化按原质量加载图像
        int quality = 100;
        Bitmap.CompressFormat cf = isPng ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG;
        bitmap.compress(cf, quality, baos);
        System.out.println("图片压缩前大小：" + baos.toByteArray().length + "byte");
        while (baos.toByteArray().length / 1024 > maxSize) {
            quality -= 10;
            baos.reset();
            bitmap.compress(cf, quality, baos);
            System.out.println("质量压缩到原来的" + quality + "%时大小为："
                    + baos.toByteArray().length + "byte");
            // 当压缩质量低于20%时不再压缩
            if (quality <= 10)
                break;
        }
        System.out.println("图片压缩后大小：" + baos.toByteArray().length + "byte");
        // 文件流
        FileOutputStream fos = new FileOutputStream(saveFile);
        // 将压缩后的字节流写入文件流
        fos.write(baos.toByteArray());
        baos.flush();
        baos.close();
        fos.flush();
        fos.close();
        bitmap.recycle();
        System.gc();
        return saveFile.getPath();
    }

    /**
     * 判断是否为png图片
     *
     * @param filePath 文件路径或名称
     * @return 是否为png图片
     */
    public static boolean isPng(String filePath) {
        try {
            if (".png".equals(filePath.substring(filePath.lastIndexOf("."))))
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 读取图片旋转角度
     *
     * @param path
     * @return
     */
    public static int getPictureAngle(String path) {
        int angle = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    angle = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    angle = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    angle = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return angle;
    }

}
