package www.app.ypy.com.mvpandroid.gradeadapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import www.app.ypy.com.mvpandroid.R;
import www.app.ypy.com.mvpandroid.adapter.CommonRecylerAdapter;
import www.app.ypy.com.mvpandroid.adapter.CommonRecylerHolder;
import www.app.ypy.com.mvpandroid.bean.InforBean;
import www.app.ypy.com.mvpandroid.utils.Contact;

/**
 * Created by ypu
 * on 2019/8/29 0029
 */
public class ClassAdapter {
    CommonRecylerAdapter mClassRecylerAdapter;
    CommonRecylerAdapter mRecylerAdapter;
    CommonRecylerAdapter mRecylerSubject;
    List<InforBean.MainTeacherClazzBean> classNameOrSubjectList;
    Context mContext;
    int mSerachTag;

    public ClassAdapter(List<InforBean.MainTeacherClazzBean> classNameOrSubjectList, Context context, int searchTag) {
        this.classNameOrSubjectList = classNameOrSubjectList;
        this.mContext = context;
        this.mSerachTag = searchTag;
    }

    //设置班级适配器
    public void setClassAdapter(RecyclerView recyclerClass, InforBean mInforBean
    ) {
        //设置数据
        if (mClassRecylerAdapter == null) {
            recyclerClass.setLayoutManager(new LinearLayoutManager(mContext));
            mClassRecylerAdapter = new CommonRecylerAdapter(mContext, classNameOrSubjectList, R.layout.item_select_class_info) {
                @Override
                public void convert(CommonRecylerHolder holder, Object item, int position, boolean isScrolling) {
                    holder.setIsRecyclable(false);
                    InforBean.MainTeacherClazzBean classbean = (InforBean.MainTeacherClazzBean) item;
                    holder.setText(R.id.text_grade_name, classbean.getGradeNameValue());
                    RecyclerView recyclerView = null;
                    if (recyclerView == null) {
                        recyclerView = holder.getView(R.id.recycler_grade_name);
                    }
                    if (mInforBean.getRoleNameCode().equals(Contact.TEACHER)) {
                        List<InforBean.MainTeacherClazzBean.ClassInfosBean> classInfos = classbean.getClassInfos();
                        addDataAdapter(recyclerView, classInfos, context);
                    } else {
                        if (mSerachTag == 1) {
                            List<InforBean.MainTeacherClazzBean.SubjectInfosBean> subjectInfos = classbean.getSubjectInfos();
                            addDataSubjectAdapter(recyclerView, subjectInfos, context, classbean);
                        } else {
                            List<InforBean.MainTeacherClazzBean.ClassInfosBean> classInfos = classbean.getClassInfos();
                            addDataAdapter(recyclerView, classInfos, context);
                        }
                    }
                }

            };
            recyclerClass.setAdapter(mClassRecylerAdapter);
        }
    }

    /**
     * 添加班级的适配器
     *
     * @param recyclerView
     * @param classInfos
     */
    private void addDataAdapter(RecyclerView recyclerView, List<InforBean.MainTeacherClazzBean.ClassInfosBean> classInfos, Context context) {
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        mRecylerAdapter = new CommonRecylerAdapter(context, classInfos, R.layout.item_select_yearname) {
            @Override
            public void convert(CommonRecylerHolder holder, Object item, int position, boolean isScrolling) {
                holder.setIsRecyclable(false);
                InforBean.MainTeacherClazzBean.ClassInfosBean gradeBean = (InforBean.MainTeacherClazzBean.ClassInfosBean) item;
                holder.setText(R.id.item_text_info, gradeBean.getClassNameValue());
                if (gradeBean.isAbool()) {
                    holder.setBackground(R.id.liner_text_info, context.getDrawable(R.drawable.shape_backblack_all));
                } else {
                    holder.setBackground(R.id.liner_text_info, context.getDrawable(R.drawable.shape_backblack));
                }
                holder.setOnRecyclerItemClickListener(R.id.item_text_info, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gradeBean.setAbool(!gradeBean.isAbool());
                        mRecylerAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
        recyclerView.setAdapter(mRecylerAdapter);

    }

    /**
     * 添加科目的适配器
     *
     * @param recyclerView
     * @param infosBeans
     * @param classbean
     */
    private void addDataSubjectAdapter(RecyclerView recyclerView, List<InforBean.MainTeacherClazzBean.SubjectInfosBean> infosBeans, Context context, InforBean.MainTeacherClazzBean classbean) {
        if (mRecylerSubject == null) {
            mRecylerSubject = new CommonRecylerAdapter(context, infosBeans, R.layout.item_select_yearname) {
                @Override
                public void convert(CommonRecylerHolder holder, Object item, int position, boolean isScrolling) {
                    holder.setIsRecyclable(false);
                    InforBean.MainTeacherClazzBean.SubjectInfosBean gradeBean = (InforBean.MainTeacherClazzBean.SubjectInfosBean) item;
                    holder.setText(R.id.item_text_info, gradeBean.getSubjectNameValue());
                    if (gradeBean.isBoolean())
                        holder.setBackground(R.id.liner_text_info, context.getDrawable(R.drawable.shape_backblack_all));
                    else
                        holder.setBackground(R.id.liner_text_info, context.getDrawable(R.drawable.shape_backblack));

                    holder.setOnRecyclerItemClickListener(R.id.item_text_info, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            gradeBean.setBoolean(!gradeBean.isBoolean());
                            mClassRecylerAdapter.notifyDataSetChanged();
                        }
                    });
                }
            };
            recyclerView.setAdapter(mRecylerSubject);
        } else {
            mRecylerSubject.setList(infosBeans);
        }

    }

    public void getSuccessData(List<InforBean.MainTeacherClazzBean> classNameOrSubjectList, int serachTag) {
        this.classNameOrSubjectList = classNameOrSubjectList;
        this.mSerachTag = serachTag;
        mClassRecylerAdapter.setList(classNameOrSubjectList);
    }
}
