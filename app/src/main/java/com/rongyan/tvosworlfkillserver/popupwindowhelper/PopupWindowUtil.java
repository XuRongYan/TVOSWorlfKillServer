package com.rongyan.tvosworlfkillserver.popupwindowhelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.rongyant.commonlib.imageloader.ImageLoader;
import com.rongyant.commonlib.imageloader.ImageLoaderUtil;

/**
 * Created by XRY on 2017/8/9.
 */

public class PopupWindowUtil {
    private SparseArray<View> mViews;
    private PopupWindow mPopupWindow;
    private Context mContext;
    private View mContentView;

    public PopupWindowUtil() {

    }

    public PopupWindow getPopupWindow() {
        return mPopupWindow;
    }

    public View getContentView() {
        return mContentView;
    }

    public void show(View parent, int gravity, int x, int y) {
        if (mPopupWindow != null) {
            mPopupWindow.showAtLocation(parent, gravity, x, y);
        }
    }

    public void dismiss() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    public boolean isShowing() {
        return mPopupWindow.isShowing();
    }

    public SparseArray<View> getViews() {
        return mViews;
    }

    public void setViews(SparseArray<View> mViews) {
        this.mViews = mViews;
    }

    public void setPopupWindow(PopupWindow mPopupWindow) {
        this.mPopupWindow = mPopupWindow;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public void setContentView(View mContentView) {
        this.mContentView = mContentView;
    }



    public static class Builder {
        private PopupWindowUtil mHelper;
        private Context mContext;
        private SparseArray<View> mViews;
        private PopupWindow mPopupWindow;
        private View mContentView;
        public Builder(Context context) {
            mHelper = new PopupWindowUtil();
            mViews = new SparseArray<>();
            mContext = context;
            mPopupWindow = new PopupWindow(context);

        }

        @SuppressWarnings("unchecked")
        public <T extends View> T getView(int resId) {
            View view = mViews.get(resId);
            if (view == null) {
                view = mContentView.findViewById(resId);
                mViews.put(resId, view);
            }
            return ((T) view);
        }

        public Builder setView(int resId) {
            View inflate = LayoutInflater.from(mContext).inflate(resId, null);
            mContentView = inflate;
            mPopupWindow.setContentView(inflate);
            return this;
        }

        public Builder setView(View view) {
            mPopupWindow.setContentView(view);
            mContentView = view;
            return this;
        }

        public Builder setLayoutParams(int width, int height) {
            mPopupWindow.setWidth(width);
            mPopupWindow.setHeight(height);
            return this;
        }

        public Builder setLayoutParams(ViewGroup.LayoutParams layoutParams) {
            mPopupWindow.setWidth(layoutParams.width);
            mPopupWindow.setHeight(layoutParams.height);
            return this;
        }

        public Builder setFocusable(boolean focusable) {
            mPopupWindow.setFocusable(focusable);
            return this;
        }

        /**
         * 设置点击外部消失
         * @return
         */
        public Builder setDismissWhenOutsideClick() {
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            return this;
        }

        public Builder setTouchable(boolean touchable) {
            mPopupWindow.setTouchable(touchable);
            return this;
        }

        public Builder setText(int resId, String text) {
            TextView textView = getView(resId);
            textView.setText(text);
            return this;
        }

        public Builder setTextColor(int resId, int color) {
            TextView textView = getView(resId);
            textView.setTextColor(color);
            return this;
        }

        public Builder setTextColorRes(int resId, int colorRes) {
            TextView textView = getView(resId);
            textView.setTextColor(mContext.getResources().getColor(colorRes));
            return this;
        }

        /**
         * 为TextView设置超链接
         *
         * @param viewId
         * @return
         */
        public Builder linkify(int viewId) {
            TextView textView = getView(viewId);
            Linkify.addLinks(textView, Linkify.ALL);
            return this;
        }

        /**
         * 设置字体
         * @param typeFace
         * @param viewIds
         * @return
         */
        public Builder setTypeFace(Typeface typeFace, int... viewIds) {
            for (int viewId : viewIds) {
                TextView view = getView(viewId);
                view.setTypeface(typeFace);
                view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
            }
            return this;
        }

        public Builder setImageResource(int viewId, int resId) {
            ImageView imageView = getView(viewId);
            imageView.setImageResource(resId);
            return this;
        }

        public Builder setImageBitmap(int viewId, Bitmap bitmap) {
            ImageView imageView = getView(viewId);
            imageView.setImageBitmap(bitmap);
            return this;
        }

        public Builder setImageDrawable(int viewId, Drawable drawable) {
            ImageView imageView = getView(viewId);
            imageView.setImageDrawable(drawable);
            return this;
        }

        public Builder setBackgroundColor(int viewId, int color) {
            View view = getView(viewId);
            view.setBackgroundColor(color);
            return this;
        }

        public Builder setBackgroundRes(int viewId, int backgroundRes) {
            View view = getView(viewId);
            view.setBackgroundResource(backgroundRes);
            return this;
        }

        public Builder setImageUrl(int viewId, String url, int placeHolder) {
            ImageView imageView = getView(viewId);
            ImageLoader loader = new ImageLoader.Builder()
                    .imgView(imageView)
                    .placeHolder(placeHolder)
                    .url(url)
                    .build();
            ImageLoaderUtil.getInstance().loadImage(mContext, loader);
            return this;
        }

        public Builder setImageUrl(int viewId, String url) {
            ImageView imageView = getView(viewId);

            ImageLoader loader = new ImageLoader.Builder()
                    .imgView(imageView)
                    .url(url)
                    .build();
            ImageLoaderUtil.getInstance().loadImage(mContext, loader);
            return this;
        }

        @SuppressLint("NewApi")
        public Builder setAlpha(int viewId, float value) {
            getView(viewId).setAlpha(value);
            return this;
        }

        public Builder setVisible(int viewId, boolean visible) {
            View view = getView(viewId);
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
            return this;
        }

        public Builder setProgress(int viewId, int progress) {
            ProgressBar view = getView(viewId);
            view.setProgress(progress);
            return this;
        }

        public Builder setProgress(int viewId, int progress, int max) {
            ProgressBar view = getView(viewId);
            view.setMax(max);
            view.setProgress(progress);
            return this;
        }

        public Builder setMax(int viewId, int max) {
            ProgressBar view = getView(viewId);
            view.setMax(max);
            return this;
        }

        public Builder setRating(int viewId, float rating) {
            RatingBar view = getView(viewId);
            view.setRating(rating);
            return this;
        }

        public Builder setRating(int viewId, float rating, int max) {
            RatingBar view = getView(viewId);
            view.setMax(max);
            view.setRating(rating);
            return this;
        }

        public Builder setTag(int viewId, Object tag) {
            View view = getView(viewId);
            view.setTag(tag);
            return this;
        }

        public Builder setTag(int viewId, int key, Object tag) {
            View view = getView(viewId);

            view.setTag(key, tag);
            return this;
        }

        public Builder setChecked(int viewId, boolean checked) {
            Checkable view = getView(viewId);
            view.setChecked(checked);
            return this;
        }

        public Builder setCheckBoxText(int viewId, String text) {
            CheckBox view = getView(viewId);
            view.setText(text);
            return this;
        }

        public Builder addOnCheckedChangedListener(int viewId, CompoundButton.OnCheckedChangeListener listener) {
            CheckBox checkBox = getView(viewId);
            checkBox.setOnCheckedChangeListener(listener);
            return this;
        }

        public Builder setCheckState(int viewId, boolean isCheck) {
            CheckBox checkBox = getView(viewId);
            checkBox.setChecked(isCheck);
            return this;
        }

        /**
         * 关于事件的
         */
        public Builder setOnClickListener(int viewId,
                                             View.OnClickListener listener) {
            View view = getView(viewId);
            view.setOnClickListener(listener);
            return this;
        }

        public Builder setOnTouchListener(int viewId,
                                             View.OnTouchListener listener) {
            View view = getView(viewId);
            view.setOnTouchListener(listener);
            return this;
        }

        public Builder setOnLongClickListener(int viewId,
                                                 View.OnLongClickListener listener) {
            View view = getView(viewId);
            view.setOnLongClickListener(listener);
            return this;
        }

        public Builder setClickable(int viewId, boolean clickable) {
            View view = getView(viewId);
            view.setClickable(clickable);
            return this;
        }

        public PopupWindowUtil build() {
            mHelper.setContentView(mContentView);
            mHelper.setContext(mContext);
            mHelper.setPopupWindow(mPopupWindow);
            mHelper.setViews(mViews);
            return mHelper;
        }


    }
}
