package com.rongyant.commonlib.CommonAdapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

/**
 * Created by XRY on 2017/5/12.
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context context;
    protected List<T> list;
    protected int layoutResId;
    protected OnItemClickListener mOnItemClickListener;
    protected OnBindHeaderOrFooter mOnBindHeaderOrFooter;
    private RecyclerView recyclerView;
    private int count = 0;
    private int headerView;
    private int footerView;

    public CommonAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
    }

    public CommonAdapter(Context context, List<T> list, RecyclerView recyclerView) {
        this.context = context;
        this.list = list;
        this.recyclerView = recyclerView;
    }

    public List<T> getList() {
        return list;
    }

    @Override
    public int getItemViewType(int position) {
        if (!hasHeaderView() && !hasFooterView()) {
            layoutResId = setLayoutId(position);
        } else {
            if (isHeaderView(position)) {
                layoutResId = headerView;
            } else if (isFooterView(position)) {
                layoutResId = footerView;
            } else {
                layoutResId = setLayoutId(position);
            }
        }
        return layoutResId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.createViewHolder(context, parent, viewType);
        setListener(parent, viewHolder, viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (hasFooterView()) {
            if ( list.size() <= 0){
                if (position == getItemCount() - 1) {
                    holder.getConvertView().setVisibility(View.GONE);
                }
            } else {
                if (position == getItemCount() - 1) {
                    holder.getConvertView().setVisibility(View.VISIBLE);
                }
            }
        }
        if (!isHeaderView(position) && !isFooterView(position)) {
            if (hasHeaderView()) {
                position--;
            }
            T item = list.get(position);
            onBindVH(holder, item, position);
        } else {
            if (mOnBindHeaderOrFooter == null) {
                return;
            }
            if (position == 0) {
                mOnBindHeaderOrFooter.onHeader(holder);
            }
            if (position == getItemCount() - 1) {
                mOnBindHeaderOrFooter.onFooter(holder);
            }
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (this.recyclerView == null && this.recyclerView != recyclerView) {
            this.recyclerView = recyclerView;
        }
        ifGridLayoutManager();
    }

    @Override
    public int getItemCount() {
        int count = list == null ? 0 : list.size();
        if (footerView != 0) {
            count++;
        }
        if (headerView != 0) {
            count++;
        }
        return count;
    }

    protected void setListener(ViewGroup parent, final ViewHolder viewHolder, int viewType) {

        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    if ((!hasHeaderView() || position != 0) && (!hasFooterView() || position != getItemCount() - 1)) {
                        mOnItemClickListener.OnItemClick(v, viewHolder, position);
                    }
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    if ((!hasHeaderView() || position != 0) && (!hasFooterView() || position != getItemCount() - 1)) {
                        return mOnItemClickListener.onItemLongClick(v, viewHolder, position);
                    }
                }
                return false;
            }
        });
    }

    public abstract int setLayoutId(int position);

    public abstract void onBindVH(ViewHolder viewHolder, T item, int position);

    public void addHeaderView(int viewId) {
        if (hasHeaderView()) {
            throw new IllegalStateException("header view is already exists");
        } else {
            headerView = viewId;
            ifGridLayoutManager();
            notifyItemInserted(0);
        }
    }

    public void addFooterView(int viewId) {
        if (hasFooterView()) {
            throw new IllegalStateException("header view is already exists");
        } else {
            footerView = viewId;
            ifGridLayoutManager();
            notifyItemInserted(getItemCount() - 1);
        }
    }

    public boolean hasHeaderView() {
        return headerView != 0;
    }

    public boolean hasFooterView() {
        return footerView != 0;
    }

    public boolean isHeaderView(int position) {
        return hasHeaderView() && position == 0;
    }

    public boolean isFooterView(int position) {
        return hasFooterView() && position == getItemCount() - 1;
    }

    /**
     * 替换某个元素
     *
     * @param item
     * @param position
     */
    public void replaceItem(T item, int position) {
        if (item != null) {
            this.list.remove(position);
            this.list.add(position, item);
            notifyDataSetChanged();
        }
    }

    /**
     * 替换一个范围
     *
     * @param list
     * @param start
     * @param end
     */
    public void replaceRange(List<T> list, int start, int end) {
        if (list != null) {
            if (end < start) {
                throw new IllegalStateException("end < start,end = " + end + " start = " + start);
            }
            for (int i = start; i < end; i++) {
                list.remove(i);
            }

            list.addAll(start, list);
            notifyDataSetChanged();
        }
    }

    /**
     * 替换整个列表
     *
     * @param list
     */
    public void replaceList(List<T> list) {
        this.list.clear();
        if (list != null) {

            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    /**
     * 删除RecyclerView指定的数据
     */
    public void remove(T item) {
        if (list != null) {
            this.list.remove(item);
            notifyDataSetChanged();
        }
    }

    /**
     * 删除RecyclerView指定位置的数据
     */
    public void remove(int position) {
        if (position >= 0 && position <= list.size() && list != null) {
            this.list.remove(position);
            notifyItemRemoved(position);
        }
    }

    /**
     * 删除RecyclerView所有数据
     */
    public void removeAll() {
        if (this.list != null) {
            this.list.clear();
            notifyItemRangeChanged(0, list.size());
        }
    }

    /**
     * 添加数据列表到列表头部
     */
    public void addListAtStart(List<T> list) {
        if (list != null && !list.isEmpty()) {
            this.list.addAll(0, list);
            notifyDataSetChanged();
        }
    }

    /**
     * 添加数据列表到列表尾部
     */
    public void addListAtEnd(List<T> list) {
        if (list != null && !list.isEmpty()) {
            this.list.addAll(list);
            notifyItemRangeInserted(list.size() - 1, list.size());
        }

    }


    /**
     * 添加数据列表到列表尾部
     */
    public void addListAtEndAndNotify(List<T> list) {
        if (list != null && !list.isEmpty()) {
            this.list.addAll(list);
            notifyDataSetChanged();
        }
    }


    /**
     * 添加单个元素到列表头
     */
    public void addListBeanAtStart(T item) {
        if (item != null) {
            list.add(0, item);
            notifyItemInserted(0);
        }

    }

    /**
     * 添加单个元素到列表尾
     */
    public void addListBeanAtEnd(T item) {
        if (item != null) {
            list.add(item);
            notifyItemInserted(list.size() - 1);
        }
    }

    public void switchItems(int from, int to) {
        if (from < to) {
            for (int i = from; i < to; i++) {
                Collections.swap(list, i, i + 1);
            }
        } else {
            for (int i = to; i > from; i--) {
                Collections.swap(list, i, i - 1);
            }
        }
        notifyItemMoved(from, to);
    }




    public interface OnItemClickListener {
        void OnItemClick(View view, ViewHolder holder, int position);

        boolean onItemLongClick(View view, ViewHolder holder, int position);
    }

    /**
     * 用于控制添加的头部和尾部的接口
     */
    public interface OnBindHeaderOrFooter {
        void onHeader(ViewHolder viewHolder);

        void onFooter(ViewHolder viewHolder);
    }

    public void setOnBindHeaderOrFooter(OnBindHeaderOrFooter onBindHeaderOrFooter) {
        mOnBindHeaderOrFooter = onBindHeaderOrFooter;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    private void ifGridLayoutManager() {
        if (recyclerView == null) {
            return;
        }
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        //如果是GridLayout就获取他的spanSize
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager.SpanSizeLookup spanSizeLookup = ((GridLayoutManager) layoutManager)
                    .getSpanSizeLookup();

            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return isHeaderView(position) || isFooterView(position) ?
                            ((GridLayoutManager) layoutManager).getSpanCount() : 1;
                }
            });
        }

    }
}
