package cn.com.lz.entrepreneur.restaurant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jaeger.library.StatusBarUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import cn.com.lz.entrepreneur.restaurant.Constants;
import cn.com.lz.entrepreneur.restaurant.R;
import cn.com.lz.entrepreneur.restaurant.util.PermissionsChecker;

/**
 * Description:主页基础Activity
 * author: lhd
 * Date: 2018/1/22
 */

public abstract class BaseMainActivity extends AppCompatActivity {

    // 页面TAG
    final String TAG = getClass().getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        // 设置透明状态栏
        StatusBarUtil.setTranslucentForImageView(this, Constants.STATUS_BAR_ALPHA, null);

        ButterKnife.bind(this);

        init();

        setListener();
    }

    @Override
    protected void onResume() {
        // 检测程序是否回到前段
        super.onResume();
        // 统计时长
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart(TAG);

        // 缺少权限时, 进入权限配置页面
        if (PermissionsChecker.lacksPermissions(getApplicationContext(), Constants.PERMISSIONS)) {
            Intent intent = new Intent(this, PermissionsActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd(TAG);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.activity_in, 0);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.activity_in, 0);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.activity_out);
    }

    /**
     * Description: 获取布局Layout
     * author: lhd
     * Date: 2018-1-22 14:31:27
     */
    protected abstract int getContentView();

    /**
     * Description: 初始化
     * author: lhd
     * Date: 2018-1-22 14:31:27
     */
    protected abstract void init();

    /**
     * Description: 添加事件监听器
     * author: lhd
     * Date: 2018-1-22 14:31:27
     */
    protected abstract void setListener();
}
