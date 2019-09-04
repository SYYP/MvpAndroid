package www.app.ypy.com.mvpandroid.gradeadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import www.app.ypy.com.mvpandroid.R;
import www.app.ypy.com.mvpandroid.adapter.CommonRecylerAdapter;
import www.app.ypy.com.mvpandroid.adapter.CommonRecylerHolder;
import www.app.ypy.com.mvpandroid.bean.Bindlistbean;

/**
 * Created by ypu
 * on 2019/8/28 0028
 */
public class BindListAdapter {
    CommonRecylerAdapter mCommonRecylerAdapter;
    SetOnClick mSetOnClick;
    boolean mPhotoabool;
    List<Bindlistbean> mBindlistbeans = new ArrayList<>();

    public BindListAdapter(boolean photoabool, List<Bindlistbean> bindlistbean) {
        mPhotoabool = photoabool;
        mBindlistbeans.clear();
        mBindlistbeans.addAll(bindlistbean);
    }

    /**
     * 设置列表页面数据
     */
    public void initSetAdapter(Context context, RecyclerView recylerList) {
        if (mCommonRecylerAdapter == null) {
            //条目点击事件
            mCommonRecylerAdapter = new CommonRecylerAdapter(context, mBindlistbeans, R.layout.item_devicement) {
                @Override
                public void convert(CommonRecylerHolder holder, Object item, int position, boolean isScrolling) {

                    Bindlistbean bindlistbean = (Bindlistbean) item;
                    holder.setText(R.id.text_name, bindlistbean.getUserName());
                    holder.setText(R.id.text_class, bindlistbean.getClassWholeName());
                    if (!TextUtils.isEmpty(bindlistbean.getTeacherNumber())) {
                        getAdapterTeacherData(holder, bindlistbean);
                    }
                    if (!TextUtils.isEmpty(bindlistbean.getStudentNumber())) {
                        getAdapterStudentData(holder, bindlistbean);
                    }
                    holder.setText(R.id.text_number, bindlistbean.getAccount());
                    holder.setText(R.id.text_equmentnumber, bindlistbean.getDeviceId());
                    if (mPhotoabool) {  //photoabool 该开关用来切换，绑定的图片跟选中时候图片
                        if (bindlistbean.isAbool()) {
                            holder.setImageResource(R.id.img_bind, R.drawable.select_true);
                        } else {
                            holder.setImageResource(R.id.img_bind, R.drawable.select_false);
                        }
                    } else {
                        holder.setImageResource(R.id.img_bind, R.drawable.img_binding);
                    }
                    holder.setOnRecyclerItemClickListener(R.id.img_bind, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (mSetOnClick != null) {
                                mSetOnClick.getOnClick(bindlistbean, mCommonRecylerAdapter);
                            }
                        }
                    });

                }

            };
            recylerList.setAdapter(mCommonRecylerAdapter);
        }
    }

    /**
     * 当数据为教师时候，填充适配器的数据
     *
     * @param holder
     * @param bindlistbean
     */
    private void getAdapterTeacherData(CommonRecylerHolder holder, Bindlistbean bindlistbean) {
        holder.setText(R.id.text_numbername, "教工号:");
        holder.setText(R.id.text_number, bindlistbean.getTeacherNumber());
        holder.setText(R.id.text_classorsubject, "学科:");
        holder.setText(R.id.text_class, bindlistbean.getTeachSubjects());
        if (bindlistbean.getTeacherNumber().length() > 20) {
            String substring = bindlistbean.getTeacherNumber().substring(0, 5);
            String substrings = bindlistbean.getTeacherNumber().substring(bindlistbean.getTeacherNumber().length() - 5, bindlistbean.getTeacherNumber().length());
            holder.setText(R.id.text_stunumber, substring + "..." + substrings);
        } else {
            holder.setText(R.id.text_stunumber, bindlistbean.getTeacherNumber());
        }
    }

    /**
     * 当数据为学生时候填充的数据
     *
     * @param holder
     * @param bindlistbean
     */
    private void getAdapterStudentData(CommonRecylerHolder holder, Bindlistbean bindlistbean) {
        holder.setText(R.id.text_numbername, "学号:");
        holder.setText(R.id.text_classorsubject, "班级:");
        holder.setText(R.id.text_class, bindlistbean.getStudentOfClass());
        if (bindlistbean.getStudentNumber().length() > 20) {
            String substring = bindlistbean.getStudentNumber().substring(0, 5);
            String substrings = bindlistbean.getStudentNumber().substring(bindlistbean.getStudentNumber().length() - 5, bindlistbean.getStudentNumber().length());
            holder.setText(R.id.text_stunumber, substring + "..." + substrings);
        } else {
            holder.setText(R.id.text_stunumber, bindlistbean.getStudentNumber());
        }
    }

    public void getBindList(List<Bindlistbean> mBindlistbean, boolean photoabool) {
        mBindlistbeans.clear();
        mBindlistbeans.addAll(mBindlistbean);
        this.mPhotoabool = photoabool;
        mCommonRecylerAdapter.setList(mBindlistbeans);
    }

    public interface SetOnClick {
        void getOnClick(Bindlistbean bindlistbean, CommonRecylerAdapter mCommonRecylerAdapter);

    }

    public void setOnClickListener(SetOnClick setOnClick) {
        this.mSetOnClick = setOnClick;
    }


}
