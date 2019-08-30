package www.app.ypy.com.mvpandroid.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * RecyclerView的ViewHolder的公共类
 *
 *
 */
public class CommonRecylerHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> viewSparseArray;
    private Context context;

    public CommonRecylerHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        viewSparseArray = new SparseArray<>(8);
    }

    public static CommonRecylerHolder getRecylerHolder(Context context, View itemView) {
        return new CommonRecylerHolder(itemView, context);
    }

    public SparseArray<View> getViewSparseArray() {
        return viewSparseArray;
    }

    public <T extends View> T getView(int viewId) {
        View view = viewSparseArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            viewSparseArray.put(viewId, view);
        }
        return (T) view;
    }


    /**
     * 设置字符串
     */
    public CommonRecylerHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public CommonRecylerHolder setTextColor(int viewId, int color) {
        TextView textView = getView(viewId);
        textView.setTextColor(color);
        return this;
    }

    public CommonRecylerHolder setVisibility(int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    public CommonRecylerHolder setBackground(int viewId, Drawable background) {
        View view = getView(viewId);
        view.setBackground(background);
        return this;
    }


    /**
     * 设置图片
     */
    public CommonRecylerHolder setImageResource(int viewId, int drawableId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(drawableId);
        return this;
    }

    /**
     * 设置图片
     */
    public CommonRecylerHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }

//    /**
//     * 设置图片
//     */
//    public CommonRecylerHolder setImageByUrl(int viewId, String url) {
//            GlideApp.with(context).load(url)
//                .placeholder(R.drawable.ease_default_image)//图片加载出来前，显示的图片
//                .error(R.drawable.ease_default_image)//图片加载失败后，显示的图片
//                .into((ImageView) getView(viewId));
//        return this;
//    }


    public CommonRecylerHolder setOnRecyclerItemClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }


}
