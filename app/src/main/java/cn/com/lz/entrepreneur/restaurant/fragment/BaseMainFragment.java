package cn.com.lz.entrepreneur.restaurant.fragment;

import com.umeng.analytics.MobclickAgent;

/**
 * Description: 主页Fragment基类（用于页面统计）
 * author: CodingHornet
 * Date: 2017/11/6 10:26
 */
public abstract class BaseMainFragment extends BaseFragment {

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //统计页面，TAG为页面名称，可自定义
            MobclickAgent.onPageStart(TAG);
        } else {
            MobclickAgent.onPageEnd(TAG);
        }
    }
}
