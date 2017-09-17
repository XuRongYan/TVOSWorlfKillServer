package com.rongyan.tvosworlfkillserver.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.CompoundButton;

import com.rongyan.tvosworlfkillserver.R;
import com.rongyan.tvosworlfkillserver.entity.GodCheck;
import com.rongyant.commonlib.commonadapter.CommonAdapter;
import com.rongyant.commonlib.commonadapter.ViewHolder;

import java.util.List;

/**
 * Created by XRY on 2017/8/2.
 */

public class CheckGodAdapter extends CommonAdapter<GodCheck> {

    public CheckGodAdapter(Context context, List<GodCheck> list) {
        super(context, list);
    }

    public CheckGodAdapter(Context context, List<GodCheck> list, RecyclerView recyclerView) {
        super(context, list, recyclerView);
    }

    @Override
    public int setLayoutId(int position) {
        return R.layout.item_check_god;
    }

    @Override
    public void onBindVH(ViewHolder viewHolder, final GodCheck item, int position) {
        viewHolder.setCheckBoxText(R.id.cb_check_god_box, item.getGodName())
                .setCheckState(R.id.cb_check_god_box, item.isCheck())
                .addOnCheckedChangedListener(R.id.cb_check_god_box, new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        item.setCheck(isChecked);
                    }
                });


    }
}
