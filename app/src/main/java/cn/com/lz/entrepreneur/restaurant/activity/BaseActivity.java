package cn.com.lz.entrepreneur.restaurant.activity;

import com.umeng.analytics.MobclickAgent;

/**
 * Description:基础Activity
 * author: lhd
 * Date: 2018/1/22
 */

public abstract class BaseActivity extends BaseMainActivity {
    @Override
    protected void onResume() {
        super.onResume();
        // 友盟统计进入页面
        MobclickAgent.onPageStart(TAG);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 友盟统计离开页面
        MobclickAgent.onPageEnd(TAG);
    }
}
