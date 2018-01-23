package cn.com.lz.entrepreneur.restaurant;

import android.Manifest;

/**
 * Description:
 * author: lhd
 * Date: 2018/1/22
 */

public interface Constants {
    
    /**
     * 本地存储的配置
     */
    // 所需的全部权限
    String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,                 // 相机
            Manifest.permission.READ_EXTERNAL_STORAGE,  // 读取存储
            Manifest.permission.WRITE_EXTERNAL_STORAGE, // 写入存储
            Manifest.permission.READ_PHONE_STATE        // 读取电话状态
    };
    // 状态栏透明度
    int STATUS_BAR_ALPHA = 0;
    // 下载目录
    String PATH_DOWNLOAD = "download/";
    // 上传目录
    String PATH_UPLOAD = "upload/";
    // 编辑目录
    String PATH_EDIT = "edit/";
    // 照相目录
    String PATH_TAKE_PHOTO = "DCIM/TiIntegrity/";
    // 问题模块最多选择图片数
    int MAX_PICTURES = 9;
}
