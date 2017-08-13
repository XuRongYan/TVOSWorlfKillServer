package com.rongyan.tvosworlfkillserver.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;

import com.rongyan.tvosworlfkillserver.R;
import com.rongyan.model.entity.UserEntity;
import com.rongyant.commonlib.CommonAdapter.CommonAdapter;
import com.rongyant.commonlib.CommonAdapter.ViewHolder;

import java.util.List;

/**
 * Created by XRY on 2017/7/27.
 */

public class PlayerAdapter extends CommonAdapter<UserEntity> {

    public PlayerAdapter(Context context, List<UserEntity> list) {
        super(context, list);
    }

    public PlayerAdapter(Context context, List<UserEntity> list, RecyclerView recyclerView) {
        super(context, list, recyclerView);
    }

    @Override
    public int setLayoutId(int position) {
        return R.layout.item_player;
    }

    @Override
    public void onBindVH(ViewHolder viewHolder, UserEntity item, int position) {
        viewHolder.setText(R.id.item_player_name, item.getUsername())
                .setImageBitmap(R.id.item_player_img, BitmapFactory.decodeByteArray(item.getHeadImg(), 0, item.getHeadImg().length));

        if (item.isSpeeching()) {
            viewHolder.setBackgroundRes(R.id.ll_player_layout, R.drawable.bg_accent_stroke);
        }
    }

    public void nextSpeech(int id) {
        initSpeechState();
        for (UserEntity userEntity : list) {
            if (userEntity.getUserId() == id) {
                userEntity.setSpeeching(true);
            }
        }
        notifyDataSetChanged();
    }

    private void initSpeechState() {
        for (UserEntity userEntity : list) {
            userEntity.setSpeeching(false);
        }
    }
}
