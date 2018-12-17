package com.hjj.listPhoto;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import ch.ielse.view.imagewatcher.ImageWatcher;


/**
 * @author hjj
 * @date 2018/12/17
 */
public class FriendCircleAdapter extends RecyclerView.Adapter<FriendCircleAdapter.WordAndImagesViewHolder> {

    private Context mContext;

    private LayoutInflater mLayoutInflater;

    private List<FriendCircleBean> mFriendCircleBeans;

    private RequestOptions mRequestOptions;
    private DrawableTransitionOptions mDrawableTransitionOptions;
    private ImageWatcher mImageWatcher;

    public interface OnImageClickListener {
        void onImageClick(int position, int index);
    }

    private OnImageClickListener mListener;

    public void setOnImageClickListener(OnImageClickListener listener) {
        this.mListener = listener;
    }


    public FriendCircleAdapter(Context context, List<FriendCircleBean> friendCircleBeans,ImageWatcher mImageWatcher) {
        this.mContext = context;
        this.mFriendCircleBeans = friendCircleBeans;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mRequestOptions = new RequestOptions().centerCrop();
        this.mDrawableTransitionOptions = DrawableTransitionOptions.withCrossFade();
        this.mImageWatcher=mImageWatcher;

    }

    @Override
    public WordAndImagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WordAndImagesViewHolder(mLayoutInflater.inflate(R.layout.item_recycler_firend_circle_word_and_images, parent, false));

    }

    @Override
    public void onBindViewHolder(final WordAndImagesViewHolder holder, final int position) {

        final FriendCircleBean friendCircleBean = mFriendCircleBeans.get(position);
        holder.nineGridView.setOnImageClickListener(new NineGridView.OnImageClickListener() {
            @Override
            public void onImageClick(int index, View view) {
                mImageWatcher.show((ImageView) view, holder.nineGridView.getImageViews(),
                        friendCircleBean.getImageUrls());

            }
        });

        holder.nineGridView.setAdapter(new NineImageAdapter(mContext, mRequestOptions,
                mDrawableTransitionOptions, friendCircleBean.getImageUrls()));

    }


    @Override
    public int getItemCount() {
        return mFriendCircleBeans == null ? 0 : mFriendCircleBeans.size();
    }


    class WordAndImagesViewHolder extends RecyclerView.ViewHolder {

        NineGridView nineGridView;

        public WordAndImagesViewHolder(View itemView) {
            super(itemView);
            nineGridView = (NineGridView) itemView.findViewById(R.id.nine_grid_view);
        }
    }


}
