package com.rongyan.tvosworlfkillserver.adapter;

import android.content.Context;
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
                .setImageResource(R.id.item_player_img, item.getHeadImg());
    }
}
