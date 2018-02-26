package cn.com.lz.entrepreneur.restaurant.fragment;

import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import cn.com.lz.entrepreneur.restaurant.R;
import cn.com.lz.entrepreneur.restaurant.util.FileUtil;
import cn.com.lz.entrepreneur.restaurant.util.ToastUtil;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Description: 我的
 * author: lhd
 * Date: 2018/2/9
 */

public class MineFragment extends BaseMainFragment implements View.OnClickListener{

    // 头像
    @BindView(R.id.avatar)
    AppCompatImageView mAvatar;
    // 图片选择请求吗
    private static final int REQUEST_CODE_IMAGE = 1001;

    @Override
    protected int getContentView() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void init(View rootView) {

    }

    @Override
    protected void setListener() {
        mAvatar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.avatar:
                GalleryFinal.openGallerySingle(REQUEST_CODE_IMAGE, mOnHandlerResultCallback);
                break;
        }
    }

    // 图片选择库回调函数
    private GalleryFinal.OnHandlerResultCallback mOnHandlerResultCallback = new GalleryFinal.OnHandlerResultCallback() {
        @Override
        public void onHandlerSuccess(int requestCode, List<PhotoInfo> resultList) {
            if (resultList != null && requestCode == REQUEST_CODE_IMAGE) {
                // 获取选择后照片路径
                String path = resultList.get(0).getPhotoPath();
                // 头像图片处理
                File file = new File(FileUtil.getDiskCacheDir(getActivity(), "avatar.png"));
                if (file.exists()) {
                    file.delete();
                }
               // uploadFile(path);
            }
        }

        @Override
        public void onHandlerFailure(int requestCode, String errorMsg) {
            ToastUtil.showShort(errorMsg);
        }
    };


}
