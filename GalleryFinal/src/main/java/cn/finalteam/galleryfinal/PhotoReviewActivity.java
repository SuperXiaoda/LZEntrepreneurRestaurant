/*
 * Copyright (C) 2014 pengjianbo(pengjianbosoft@gmail.com), Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package cn.finalteam.galleryfinal;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import cn.finalteam.galleryfinal.adapter.PhotoReviewListAdapter;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.galleryfinal.model.PhotoTempModel;
import cn.finalteam.galleryfinal.widget.FloatingActionButton;
import cn.finalteam.galleryfinal.widget.HorizontalListView;
import cn.finalteam.galleryfinal.widget.zoonview.PhotoView;
import cn.finalteam.toolsfinal.ActivityManager;

/**
 * Description:图片裁剪
 * Author:CodingHornet
 * Date:15/10/10 下午5:40
 */
public class PhotoReviewActivity extends PhotoBaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    public static final String SELECT_MAP = "select_map";
    public static final String INIT_POSITION = "init_position";

    private ImageView mIvBack;
    private TextView mTvTitle;
    private ImageView mIvPreView;
    private PhotoView mIvSourcePhoto;
    private TextView mTvEmptyView;
    private FloatingActionButton mFabCrop;
    private HorizontalListView mLvGallery;
    private LinearLayout mLlGallery;
    private LinearLayout mTitlebar;

    private ArrayList<PhotoInfo> mPhotoList;
    private PhotoReviewListAdapter mPhotoEditListAdapter;
    private int mSelectIndex = 0;

    private ArrayList<PhotoInfo> mSelectPhotoList;
    private LinkedHashMap<Integer, PhotoTempModel> mPhotoTempMap;
    private File mEditPhotoCacheFile;

    private Drawable mDefaultDrawable;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("selectPhotoMap", mSelectPhotoList);
        outState.putSerializable("editPhotoCacheFile", mEditPhotoCacheFile);
        outState.putSerializable("photoTempMap", mPhotoTempMap);
        outState.putInt("selectIndex", mSelectIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mSelectPhotoList = (ArrayList<PhotoInfo>) getIntent().getSerializableExtra("selectPhotoMap");
        mEditPhotoCacheFile = (File) savedInstanceState.getSerializable("editPhotoCacheFile");
        mPhotoTempMap = new LinkedHashMap<>((HashMap<Integer, PhotoTempModel>) getIntent().getSerializableExtra("results"));
        mSelectIndex = savedInstanceState.getInt("selectIndex");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (GalleryFinal.getFunctionConfig() == null || GalleryFinal.getGalleryTheme() == null) {
            resultFailureDelayed(getString(R.string.please_reopen_gf), true);
        } else {
            setContentView(R.layout.gf_activity_photo_review);
            mDefaultDrawable = getResources().getDrawable(R.drawable.ic_gf_default_photo);

            mSelectPhotoList = (ArrayList<PhotoInfo>) getIntent().getSerializableExtra(SELECT_MAP);
            mSelectIndex = getIntent().getIntExtra(INIT_POSITION, 0);

            if (mSelectPhotoList == null) {
                mSelectPhotoList = new ArrayList<>();
            }
            mPhotoTempMap = new LinkedHashMap<>();
            mPhotoList = new ArrayList<>(mSelectPhotoList);

            mEditPhotoCacheFile = GalleryFinal.getCoreConfig().getEditPhotoCacheFolder();

            if (mPhotoList == null) {
                mPhotoList = new ArrayList<>();
            }

            for (PhotoInfo info : mPhotoList) {
                mPhotoTempMap.put(info.getPhotoId(), new PhotoTempModel(info.getPhotoPath()));
            }

            findViews();
            setListener();
            setTheme();

            mPhotoEditListAdapter = new PhotoReviewListAdapter(this, mPhotoList, mScreenWidth);
            mLvGallery.setAdapter(mPhotoEditListAdapter);
            mLvGallery.scrollTo(mSelectIndex);

            try {
                File nomediaFile = new File(mEditPhotoCacheFile, ".nomedia");
                if (!nomediaFile.exists()) {
                    nomediaFile.createNewFile();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (mPhotoList.size() > 0) {
                loadImage(mPhotoList.get(mSelectIndex));
                updateTitle();
            }
        }
    }

    private void setTheme() {
        mIvBack.setImageResource(GalleryFinal.getGalleryTheme().getIconBack());
        if (GalleryFinal.getGalleryTheme().getIconBack() == R.drawable.ic_gf_back) {
            mIvBack.setColorFilter(GalleryFinal.getGalleryTheme().getTitleBarIconColor());
        }

        mIvPreView.setImageResource(GalleryFinal.getGalleryTheme().getIconPreview());
        if (GalleryFinal.getGalleryTheme().getIconPreview() == R.drawable.ic_gf_preview) {
            mIvPreView.setColorFilter(GalleryFinal.getGalleryTheme().getTitleBarIconColor());
        }

        if (GalleryFinal.getGalleryTheme().getEditPhotoBgTexture() != null) {
            mIvSourcePhoto.setBackgroundDrawable(GalleryFinal.getGalleryTheme().getEditPhotoBgTexture());
        }

        mFabCrop.setIcon(GalleryFinal.getGalleryTheme().getIconFab());
        mTitlebar.setBackgroundColor(GalleryFinal.getGalleryTheme().getTitleBarBgColor());
        mTvTitle.setTextColor(GalleryFinal.getGalleryTheme().getTitleBarTextColor());
        mFabCrop.setColorPressed(GalleryFinal.getGalleryTheme().getFabPressedColor());
        mFabCrop.setColorNormal(GalleryFinal.getGalleryTheme().getFabNornalColor());
    }

    private void findViews() {
        mIvSourcePhoto = (PhotoView) findViewById(R.id.iv_source_photo);
        mLvGallery = (HorizontalListView) findViewById(R.id.lv_gallery);
        mLlGallery = (LinearLayout) findViewById(R.id.ll_gallery);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvEmptyView = (TextView) findViewById(R.id.tv_empty_view);
        mFabCrop = (FloatingActionButton) findViewById(R.id.fab_crop);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTitlebar = (LinearLayout) findViewById(R.id.titlebar);
        mIvPreView = (ImageView) findViewById(R.id.iv_preview);
    }

    private void setListener() {
        mIvBack.setOnClickListener(this);
        mLvGallery.setOnItemClickListener(this);
        mFabCrop.setOnClickListener(this);
        mIvPreView.setOnClickListener(this);
    }

    @Override
    protected void takeResult(PhotoInfo info) {
        if (!GalleryFinal.getFunctionConfig().isMutiSelect()) {
            mPhotoList.clear();
            mSelectPhotoList.clear();
        }
        mPhotoList.add(0, info);
        mSelectPhotoList.add(info);
        mPhotoTempMap.put(info.getPhotoId(), new PhotoTempModel(info.getPhotoPath()));
        if (!GalleryFinal.getFunctionConfig().isEditPhoto()) {
            resultAction();
        } else {
            if (GalleryFinal.getFunctionConfig().isEnablePreview()) {
                mIvPreView.setVisibility(View.VISIBLE);
            }
            mPhotoEditListAdapter.notifyDataSetChanged();

            PhotoSelectActivity activity = (PhotoSelectActivity) ActivityManager.getActivityManager().getActivity(PhotoSelectActivity.class.getName());
            if (activity != null) {
                activity.takeRefreshGallery(info, true);
            }
            loadImage(info);
        }
    }

    private void loadImage(PhotoInfo photo) {
        mTvEmptyView.setVisibility(View.GONE);
        mIvSourcePhoto.setVisibility(View.VISIBLE);

        String path = "";
        if (photo != null) {
            path = photo.getPhotoPath();
        }

        GalleryFinal.getCoreConfig().getImageLoader().displayImage(this, path, mIvSourcePhoto, mDefaultDrawable, mScreenWidth, mScreenHeight, false);
    }

    public void deleteIndex(int position, PhotoInfo dPhoto) {
        if (dPhoto != null) {
            PhotoSelectActivity activity = (PhotoSelectActivity) ActivityManager.getActivityManager().getActivity(PhotoSelectActivity.class.getName());
            if (activity != null) {
                activity.deleteSelect(dPhoto.getPhotoId());
            }

            try {
                for (Iterator<PhotoInfo> iterator = mSelectPhotoList.iterator(); iterator.hasNext(); ) {
                    PhotoInfo info = iterator.next();
                    if (info != null && info.getPhotoId() == dPhoto.getPhotoId()) {
                        iterator.remove();
                        break;
                    }
                }
            } catch (Exception e) {
            }
        }

        if (mPhotoList.size() == 0) {
            mSelectIndex = 0;
            mTvEmptyView.setText(R.string.no_photo);
            mTvEmptyView.setVisibility(View.VISIBLE);
            mIvSourcePhoto.setVisibility(View.GONE);
            mIvPreView.setVisibility(View.GONE);
        } else {
            if (position == 0) {
                mSelectIndex = 0;
            } else if (position == mPhotoList.size()) {
                mSelectIndex = position - 1;
            } else {
                mSelectIndex = position;
            }

            PhotoInfo photoInfo = mPhotoList.get(mSelectIndex);
            loadImage(photoInfo);
        }
        updateTitle();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (mSelectIndex != i) {
            mSelectIndex = i;
            PhotoInfo photoInfo = mPhotoList.get(i);
            loadImage(photoInfo);
            updateTitle();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fab_crop) {
            //完成选择
            resultAction();
        } else if (id == R.id.iv_back) {
            finish();
        } else if (id == R.id.iv_preview) {
            Intent intent = new Intent(this, PhotoPreviewActivity.class);
            intent.putExtra(PhotoPreviewActivity.PHOTO_LIST, mSelectPhotoList);
            startActivity(intent);
        }
    }

    private void resultAction() {
        resultData(mSelectPhotoList);
    }

    private void updateTitle() {
        if (mPhotoList.size() == 0) {
            mTvTitle.setText("");
        } else {
            mTvTitle.setText(getString(R.string.review_index, mSelectIndex + 1, mPhotoList.size()));
        }
    }
}
