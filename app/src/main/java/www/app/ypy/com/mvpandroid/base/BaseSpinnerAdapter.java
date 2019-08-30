package www.app.ypy.com.mvpandroid.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import java.util.List;

import www.app.ypy.com.mvpandroid.R;

/**
 * Created by liugengsheng on 2018/11/6.
 * </p>
 * adapter 基础样式
 */
public class BaseSpinnerAdapter extends ArrayAdapter {

    public BaseSpinnerAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public BaseSpinnerAdapter(@NonNull Context context, List mList) {
        super(context, R.layout.adapter_base_spiner_item, mList);
    }

    public BaseSpinnerAdapter(@NonNull Context context) {
        super(context, R.layout.adapter_base_spiner_item);
    }

    //获取选择条目
    public Object getObjectItem(int position) {
        return super.getItem(position);
    }


    //这个条目决定显示名称
    @Nullable
    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

}
