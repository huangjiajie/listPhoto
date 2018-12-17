package com.hjj.listPhoto;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ch.ielse.view.imagewatcher.ImageWatcher;

/**
 * Created by hjj on 2018/12/17.
 *
 */
public class MainActivity extends AppCompatActivity implements ImageWatcher.OnPictureLongPressListener, ImageWatcher.Loader{

    private FriendCircleAdapter mFriendCircleAdapter;
    private RecyclerView recyclerView;
    private List<FriendCircleBean> friendCircleBeans;
    private ImageWatcher mImageWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mImageWatcher = (ImageWatcher) findViewById(R.id.image_watcher);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!isDestroyed()) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        Glide.with(MainActivity.this).resumeRequests();
                    } else {
                        Glide.with(MainActivity.this).pauseRequests();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });


        friendCircleBeans = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            FriendCircleBean friendCircleBean = new FriendCircleBean();
            friendCircleBean.setImageUrls(makeImages());
            friendCircleBeans.add(friendCircleBean);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new FriendsCircleAdapterDivideLine(MainActivity.this));
        mFriendCircleAdapter = new FriendCircleAdapter(this, friendCircleBeans, mImageWatcher);
        mFriendCircleAdapter.setOnImageClickListener(new FriendCircleAdapter.OnImageClickListener() {
            @Override
            public void onImageClick(int position, int index) {

            }
        });


        recyclerView.setAdapter(mFriendCircleAdapter);

        mImageWatcher.setTranslucentStatus(Utils.calcStatusBarHeight(this));
        mImageWatcher.setErrorImageRes(R.mipmap.error_picture);
        mImageWatcher.setOnPictureLongPressListener(this);
        mImageWatcher.setLoader(this);

    }

    @Override
    public void load(Context context, String url, ImageWatcher.LoadCallback lc) {
        Glide.with(MainActivity.this).asBitmap().load(url).into(new GlideSimpleTarget(lc));
    }

    @Override
    public void onPictureLongPress(ImageView v, String url, int pos) {
        saveImageDialog(url);
    }

    private void saveImageDialog(final String url) {
        Toast.makeText(MainActivity.this,"保存图片",Toast.LENGTH_LONG).show();
    }

    private List<String> makeImages() {
        List<String> imageBeans = new ArrayList<>();
        int randomCount = (int) (Math.random() * 9);
        if (randomCount == 0) {
            randomCount = randomCount + 1;
        } else if (randomCount == 8) {
            randomCount = randomCount + 1;
        }
        for (int i = 0; i < randomCount; i++) {
            imageBeans.add(ImagDataUtils.IMAGE_URL[(int) (Math.random() * 50)]);
        }
        return imageBeans;
    }




}
