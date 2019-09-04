package www.app.ypy.com.mvpandroid.gradeadapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import www.app.ypy.com.mvpandroid.R;
import www.app.ypy.com.mvpandroid.adapter.CommonRecylerAdapter;
import www.app.ypy.com.mvpandroid.adapter.CommonRecylerHolder;
import www.app.ypy.com.mvpandroid.bean.InforBean;

/**
 * Created by ypu
 * on 2019/8/28 0028
 */
public class GradeAadapter {
    CommonRecylerAdapter mCommonGradeRecylerAdapter;
    private List<InforBean.MainTeacherClazzBean> mainTeacherClazzBeans = new ArrayList<>();
    Context context;

    public GradeAadapter(List<InforBean.MainTeacherClazzBean> mainTeacherClazzBean, Context context) {
        mainTeacherClazzBeans.clear();
        mainTeacherClazzBeans.addAll(mainTeacherClazzBean);
        this.context = context;
    }

    //设置年级适配器
    public void setGradeAdater(RecyclerView recyclerYearName) {
        if (mCommonGradeRecylerAdapter == null) {
            recyclerYearName.setLayoutManager(new GridLayoutManager(context, 3));
            mCommonGradeRecylerAdapter = new CommonRecylerAdapter(context, mainTeacherClazzBeans, R.layout.item_select_yearname) {
                @Override
                public void convert(CommonRecylerHolder holder, Object item, int position, boolean isScrolling) {
                    InforBean.MainTeacherClazzBean gradeBean = (InforBean.MainTeacherClazzBean) item;
                    holder.setText(R.id.item_text_info, gradeBean.getGradeNameValue());
                    if (gradeBean.isAbool()) {
                        holder.setBackground(R.id.liner_text_info, context.getDrawable(R.drawable.shape_backblack_all));
                    } else {
                        holder.setBackground(R.id.liner_text_info, context.getDrawable(R.drawable.shape_backblack));
                    }

                    holder.setOnRecyclerItemClickListener(R.id.item_text_info, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            gradeBean.setAbool(!gradeBean.isAbool());
                            notifyDataSetChanged();
                        }
                    });
                }

            };
            recyclerYearName.setAdapter(mCommonGradeRecylerAdapter);
        }

    }

    public void getSuccessData(List<InforBean.MainTeacherClazzBean> mainTeacherClazzBean) {
        mainTeacherClazzBeans.clear();
        mainTeacherClazzBeans.addAll(mainTeacherClazzBean);
        mCommonGradeRecylerAdapter.setList(mainTeacherClazzBeans);
    }
}
