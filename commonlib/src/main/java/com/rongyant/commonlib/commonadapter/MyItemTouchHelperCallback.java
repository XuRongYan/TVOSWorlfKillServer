package com.rongyant.commonlib.commonadapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * 自定义滑动拖拽事件的回调
 * Created by XRY on 2017/5/13.
 */

public class MyItemTouchHelperCallback<T> extends ItemTouchHelper.Callback {
    private CommonAdapter<T> adapter;
    private IsSwipeDragEnableListener mListener;

    public MyItemTouchHelperCallback(CommonAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlag;
        int swipeFlag;
        //如果LayoutManager是GridLayoutManager或者StaggeredGridLayoutManager则上下左右都可以滑动
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager ||
                recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END;
            swipeFlag = 0;
        } else {
            dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            swipeFlag = ItemTouchHelper.END | ItemTouchHelper.START;
        }
        return makeMovementFlags(dragFlag, swipeFlag);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        adapter.switchItems(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        adapter.remove(position);
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        if (mListener == null) {
            return true;
        }
        return mListener.isSwipeEnable();
    }

    @Override
    public boolean isLongPressDragEnabled() {
        if (mListener == null) {
            return true;
        }
        return mListener.isDragEnable();
    }

    public interface IsSwipeDragEnableListener {
        boolean isDragEnable();
        boolean isSwipeEnable();
    }

    public void setIsSwipeDragEnableListener(IsSwipeDragEnableListener listener) {
        mListener = listener;
    }
}
