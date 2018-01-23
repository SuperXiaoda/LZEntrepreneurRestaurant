package cn.com.lz.entrepreneur.restaurant.util;


import cn.com.lz.entrepreneur.restaurant.config.GlideApp;
import cn.finalteam.galleryfinal.PauseOnScrollListener;

/**
 * Description: 图片选择组件的滑动监听控制
 * Author:CodingHornet
 * Date:2016/6/6 0009 18:18
 */
public class GlidePauseOnScrollListener extends PauseOnScrollListener {

    public GlidePauseOnScrollListener(boolean pauseOnScroll, boolean pauseOnFling) {
        super(pauseOnScroll, pauseOnFling);
    }

    @Override
    public void resume() {
        GlideApp.with(getActivity()).resumeRequests();
    }

    @Override
    public void pause() {
        GlideApp.with(getActivity()).pauseRequests();
    }
}
