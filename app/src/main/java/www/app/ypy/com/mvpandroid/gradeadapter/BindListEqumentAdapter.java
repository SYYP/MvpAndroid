package www.app.ypy.com.mvpandroid.gradeadapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.liaoinstan.springview.meituanheader.MeituanFooter;
import com.liaoinstan.springview.meituanheader.MeituanHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import www.app.ypy.com.mvpandroid.R;
import www.app.ypy.com.mvpandroid.adapter.CommonRecylerAdapter;
import www.app.ypy.com.mvpandroid.adapter.CommonRecylerHolder;
import www.app.ypy.com.mvpandroid.bean.Bindlistbean;

import static www.app.ypy.com.mvpandroid.utils.UIUtils.getResources;

/**
 * Created by ypu
 * on 2019/9/3 0003
 */
public class BindListEqumentAdapter {

    CommonRecylerAdapter mCommonRecylerAdapter;
    List<Bindlistbean> bindlist = new ArrayList<>();
    Context mContext;
    SetOnClick mSetOnClick;
    RecyclerView mRecyclerView;

    public BindListEqumentAdapter(List<Bindlistbean> bindlists, Context context, RecyclerView recyclerView) {
        bindlist.clear();
        bindlist.addAll(bindlists);
        mContext = context;
        mRecyclerView = recyclerView;
    }
    public void initSetData() {
        if (mCommonRecylerAdapter == null) {
            //判断设备数量等于账号数量时候，点击选中，提示选中账号数量不能超过设备数量
            mCommonRecylerAdapter = new CommonRecylerAdapter(mContext, bindlist, R.layout.item_distribution) {
                @Override
                public void convert(CommonRecylerHolder holder, Object item, int position, boolean isScrolling) {
                    Bindlistbean infoBean = (Bindlistbean) item;
                    //姓名最多显示五个，教工号或者学号最多显示15位
                    String name = infoBean.getUserName();
                    String number = infoBean.getStudentNumber();
                    String teacherNumber = infoBean.getTeacherNumber();
                    if (!TextUtils.isEmpty(name) && name.length() > 5) {
                        holder.setText(R.id.text_name, name.substring(0, 2) + ".." + name.substring(name.length() - 2, name.length()));
                    } else {
                        holder.setText(R.id.text_name, infoBean.getUserName());
                    }
                    if (!TextUtils.isEmpty(number) && name.length() > 15) {
                        holder.setText(R.id.text_number, number.substring(0, 5) + ".." + number.substring(number.length() - 5, number.length()) + "");
                    } else if (!TextUtils.isEmpty(teacherNumber) && teacherNumber.length() > 15) {
                        holder.setText(R.id.text_number, teacherNumber.substring(0, 5) + ".." + teacherNumber.substring(teacherNumber.length() - 5, teacherNumber.length()) + "");
                    } else {
                        holder.setText(R.id.text_number, TextUtils.isEmpty(number) ? teacherNumber : number);
                    }
                    if (infoBean.isAbool()) {
                        holder.setBackground(R.id.real_select, getResources().getDrawable(R.drawable.shape_backblack_all));
                    } else {
                        holder.setBackground(R.id.real_select, getResources().getDrawable(R.drawable.shape_backline));
                    }
                    holder.setOnRecyclerItemClickListener(R.id.real_select, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mSetOnClick.getOnClick( position);
                            //判断设备数量等于账号数量时候，点击选中，提示选中账号数量不能超过设备数量
                        }
                    });
                }


            };
            mRecyclerView.setAdapter(mCommonRecylerAdapter);
        } else {
            mCommonRecylerAdapter.setList(bindlist);
        }
    }

    /**
     * 拿到数据源中选中的数据的数量
     *
     * @return
     */
    private int getNum() {
        int num = 0;
        for (int i = 0; i < bindlist.size(); i++) {
            if (bindlist.get(i).isAbool()) {
                num++;
            }
        }
        return num;
    }

    public interface SetOnClick {
        void getOnClick( int position);

    }

    public void setOnClickListener(SetOnClick setOnClick) {
        this.mSetOnClick = setOnClick;
    }

    public void getSuccessData(List<Bindlistbean> bindlistData) {
        bindlist.clear();
        bindlist.addAll(bindlistData);
        mCommonRecylerAdapter.setList(bindlistData);
    }
}
