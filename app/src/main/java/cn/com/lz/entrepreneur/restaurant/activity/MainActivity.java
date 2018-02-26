package cn.com.lz.entrepreneur.restaurant.activity;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.lz.entrepreneur.restaurant.R;
import cn.com.lz.entrepreneur.restaurant.fragment.MineFragment;

/**
 * Description:MainActivity
 * author: lhd
 * Date: 2018/1/22
 */
public class MainActivity extends BaseActivity {

    // viewPager
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    // 导航栏
    @BindView(R.id.navigation)
    AHBottomNavigation mBottomNavigation;
    // Fragment集合
    private List<Fragment> mTabs = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        // 设置底部导航布局
        mBottomNavigation.setForceTitlesDisplay(true);
        mBottomNavigation.setAccentColor(ContextCompat.getColor(getApplicationContext(), R.color.windowBackground));
        mBottomNavigation.setInactiveColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

        // 我的
        MineFragment mineFragment = new MineFragment();
        mTabs.add(mineFragment);

        // 界面适配器
        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return mTabs.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mTabs.get(arg0);
            }
        };

        mViewPager.setOffscreenPageLimit(mTabs.size());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
        mBottomNavigation.setCurrentItem(0);
    }

    @Override
    protected void setListener() {
        mBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                mViewPager.setCurrentItem(position);
            }
        });
    }
}
