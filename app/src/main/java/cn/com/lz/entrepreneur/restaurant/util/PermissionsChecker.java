package cn.com.lz.entrepreneur.restaurant.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.PermissionChecker;

/**
 * Description: 权限检测工具
 * author: CodingHornet
 * Date: 2017/2/8 13:54
 */
public class PermissionsChecker {

    /**
     * Description: 判断是否缺少权限集合
     * author: CodingHornet
     * Date: 2017/2/8 14:07
     *
     * @param context     Context
     * @param permissions 权限集合
     * @return true 缺少权限，false 拥有全部权限
     */
    public static boolean lacksPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (!checkSelfPermission(context, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Description: 检查是否有指定权限
     * author: CodingHornet
     * Date: 2017/2/8 13:57
     *
     * @param context    Context
     * @param permission 指定权限
     * @return true 拥有该权限，false 没有该权限
     */
    public static boolean checkSelfPermission(Context context, String permission) {
        // Android 6.0 以前，全部默认授权
        boolean result = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isTarget23(context)) {
                // targetSdkVersion >= 23, 使用Context#checkSelfPermission
                result = context.checkSelfPermission(permission)
                        == PackageManager.PERMISSION_GRANTED;
            } else {
                // targetSdkVersion < 23, 需要使用 PermissionChecker
                result = PermissionChecker.checkSelfPermission(context, permission)
                        == PermissionChecker.PERMISSION_GRANTED;
            }
        }
        return result;
    }

    /**
     * Description: 判断targetSdkVersion是否高于等于23
     * author: CodingHornet
     * Date: 2017/2/8 14:05
     */
    private static boolean isTarget23(Context context) {
        int targetSdkVersion = 1;
        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            targetSdkVersion = info.applicationInfo.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return targetSdkVersion >= Build.VERSION_CODES.M;
    }
}
