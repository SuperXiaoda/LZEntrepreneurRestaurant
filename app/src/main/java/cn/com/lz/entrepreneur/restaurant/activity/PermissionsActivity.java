package cn.com.lz.entrepreneur.restaurant.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.umeng.analytics.MobclickAgent;

import cn.com.lz.entrepreneur.restaurant.Constants;
import cn.com.lz.entrepreneur.restaurant.R;
import cn.com.lz.entrepreneur.restaurant.util.PermissionsChecker;


/**
 * Description: 动态权限界面
 * author: CodingHornet
 * Date: 2017/2/8 13:16
 */
public class PermissionsActivity extends AppCompatActivity {

    // 页面TAG
    final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 统计时长
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart(TAG);

        // 缺少权限时, 进入权限配置页面
        if (PermissionsChecker.lacksPermissions(getApplicationContext(), Constants.PERMISSIONS)) {
            showMissingPermissionDialog();
        } else {
            // 已经受权，关闭受权界面
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd(TAG);
    }

    /**
     * Description: 显示设置权限提醒Dialog
     * author: CodingHornet
     * Date: 2017/2/8 14:27
     */
    private void showMissingPermissionDialog() {
        new MaterialDialog.Builder(this)
                .autoDismiss(true)
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .title(R.string.prompt)
                .content(R.string.lack_of_permission)
                .positiveText(R.string.set)
                .negativeText(R.string.exit)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        // 跳转权限设置界面
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", getPackageName(), null));
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        // 退出到后台
                        moveTaskToBack(true);
                    }
                })
                .show();
    }
}
