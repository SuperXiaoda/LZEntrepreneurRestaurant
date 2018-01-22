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
}
