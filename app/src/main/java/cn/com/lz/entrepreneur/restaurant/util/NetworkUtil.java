package cn.com.lz.entrepreneur.restaurant.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.widget.Toast;

import cn.com.lz.entrepreneur.restaurant.R;

/**
 * Description: 网路检查工具
 * author: CodingHornet
 * Date: 2016/9/22 15:54
 */
public class NetworkUtil {

    /**
     * Description: 网络链接是否有效
     * author: CodingHornet
     * Date: 2016/9/22 15:54
     */
    public static boolean isConnected(@NonNull Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr == null) {
            return false;
        }
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * Description: WIFI是否链接
     * author: CodingHornet
     * Date: 2016/9/22 15:53
     */
    public static boolean isWifiConnected(@NonNull Context context) {
        return isConnected(context, ConnectivityManager.TYPE_WIFI);
    }

    /**
     * Description: 移动网络是否链接
     * author: CodingHornet
     * Date: 2016/9/22 15:53
     */
    public static boolean isMobileConnected(@NonNull Context context) {
        return isConnected(context, ConnectivityManager.TYPE_MOBILE);
    }

    /**
     * Description: 判断指定网路是否连接
     * author: CodingHornet
     * Date: 2016/9/22 15:52
     */
    private static boolean isConnected(@NonNull Context context, int type) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(type);
            return networkInfo != null && networkInfo.isConnected();
        } else {
            return isConnected(connMgr, type);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static boolean isConnected(@NonNull ConnectivityManager connMgr, int type) {
        Network[] networks = connMgr.getAllNetworks();
        NetworkInfo networkInfo;
        for (Network mNetwork : networks) {
            networkInfo = connMgr.getNetworkInfo(mNetwork);
            if (networkInfo != null && networkInfo.getType() == type && networkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Description: 检查网路并提示错误信息
     * author: CodingHornet
     * Date: 2016/9/22 15:56
     */
    public static boolean checkNetworkWithFailedToast(Context context) {
        if (isConnected(context)) {
            return true;
        }
        Toast.makeText(context, R.string.invalid_network, Toast.LENGTH_SHORT).show();
        return false;
    }
}
