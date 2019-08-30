package www.app.ypy.com.mvpandroid.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.liaoinstan.springview.meituanheader.MeituanFooter;
import com.liaoinstan.springview.meituanheader.MeituanHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;
import www.app.ypy.com.mvpandroid.R;
import www.app.ypy.com.mvpandroid.activity.LoginActivity;
import www.app.ypy.com.mvpandroid.activity.SerachActivity;
import www.app.ypy.com.mvpandroid.adapter.CommonRecylerAdapter;
import www.app.ypy.com.mvpandroid.application.Myapplication;
import www.app.ypy.com.mvpandroid.base.BaseMvpFragment;
import www.app.ypy.com.mvpandroid.base.BaseSpinnerAdapter;
import www.app.ypy.com.mvpandroid.bean.Bindlistbean;
import www.app.ypy.com.mvpandroid.bean.DailogBean;
import www.app.ypy.com.mvpandroid.bean.DeleteBean;
import www.app.ypy.com.mvpandroid.bean.DeleteEqument;
import www.app.ypy.com.mvpandroid.bean.InforBean;
import www.app.ypy.com.mvpandroid.dailymoudel.DailyInterface;
import www.app.ypy.com.mvpandroid.dailymoudel.DailyPresenter;
import www.app.ypy.com.mvpandroid.dialog.CurrencyDailogs;
import www.app.ypy.com.mvpandroid.gradeadapter.BindListAdapter;
import www.app.ypy.com.mvpandroid.gradeadapter.ClassAdapter;
import www.app.ypy.com.mvpandroid.gradeadapter.GradeAadapter;
import www.app.ypy.com.mvpandroid.utils.Contact;
import www.app.ypy.com.mvpandroid.utils.NetUtils;
import www.app.ypy.com.mvpandroid.utils.SpUtils;
import www.app.ypy.com.mvpandroid.utils.UIUtils;

/**
 * Created by ypu
 * on 2019/8/27 0027
 */
public class DailyWorkFragment extends BaseMvpFragment<DailyInterface.Presenter> implements DailyInterface.View {
    @BindView(R.id.real_serach)
    RelativeLayout realSerach;
    @BindView(R.id.text_gl)
    TextView textGl;
    @BindView(R.id.spinner_school)
    Spinner spinnerSchool;
    @BindView(R.id.liner_school)
    LinearLayout linerSchool;
    @BindView(R.id.spinner_type)
    Spinner spinnerType;
    @BindView(R.id.liner_teacher)
    LinearLayout linerTeacher;
    @BindView(R.id.text_yera)
    TextView textYera;
    @BindView(R.id.liner_yearname)
    RelativeLayout linerYearname;
    @BindView(R.id.text_select_title)
    TextView textSelectTitle;
    @BindView(R.id.liner_classname)
    RelativeLayout linerClassname;
    @BindView(R.id.check_all)
    TextView checkAll;
    @BindView(R.id.img_select)
    ImageView imgSelect;
    @BindView(R.id.liner_check_all)
    LinearLayout linerCheckAll;
    @BindView(R.id.text_relieve)
    TextView textRelieve;
    @BindView(R.id.liner_gl)
    LinearLayout linerGl;
    @BindView(R.id.real_toobar)
    RelativeLayout realToobar;
    @BindView(R.id.recyler_list)
    RecyclerView recylerList;
    @BindView(R.id.springview)
    SpringView springview;
    @BindView(R.id.liner_recycler)
    LinearLayout linerRecycler;
    @BindView(R.id.img_nodata)
    ImageView imgNodata;
    @BindView(R.id.liner_nodata)
    LinearLayout linerNodata;
    @BindView(R.id.recycler_year_name)
    RecyclerView recyclerYearName;
    @BindView(R.id.text_grade_reset)
    TextView textGradeReset;
    @BindView(R.id.text_grade_sure)
    TextView textGradeSure;
    @BindView(R.id.liner_select_yearname)
    LinearLayout linerSelectYearname;
    @BindView(R.id.recycler_class)
    RecyclerView recyclerClass;
    @BindView(R.id.text_reset)
    TextView textReset;
    @BindView(R.id.text_sure)
    TextView textSure;
    @BindView(R.id.liner_select_classname)
    LinearLayout linerSelectClassname;
    List<Bindlistbean> bindlist = new ArrayList<>();//列表数据源
    //设置一个开关用来管理列表页面全选图片的切换
    boolean photoabool = false;
    private CurrencyDailogs mCurrencyDailogs;
    private InforBean mInforBean;//登录后的信息
    private List<InforBean.SectionInfosBean> mSectionInfos;//阶段类型数据源
    private String mRoleNameCode;
    List<InforBean.MainTeacherClazzBean> mainTeacherClazzBeans = new ArrayList<>();//年级的数据源
    int searchTag = 2; //类型区分，1是教师，2;学生
    String classIds = null; //班级的Id
    String keywords = null; //搜索的内容
    int pageTag = 1;//1:设备列表: 2：人员列表
    String subjectIds = null; //当是教师时候，有学科。可以多选
    int ps = 15;//请求的页面数量
    int pn = 1;//请求的页数
    int pageNum = 1;
    String gradeId = null;
    private GradeAadapter mGradeAadapter;
    private BindListAdapter mBindListAdapter;
    private static String USR_ID = "userid";
    private String mSchoolId;//阶段的Id
    boolean mabool = true;
    boolean isSelect = true;//全选按钮的开关
    boolean gradebool = true;//年级的开关
    boolean classbool = true;//班级的开关

    CurrencyDailogs mBatchDeleteDialog;
    List<InforBean.MainTeacherClazzBean> subjectList = new ArrayList<>();//科目的数据源
    List<InforBean.MainTeacherClazzBean> classNameList = new ArrayList<>();//班数据源
    private ClassAdapter mClassAdapter;

    public static Fragment newInstance(String userId) {
        DailyWorkFragment fragment = new DailyWorkFragment();
        Bundle args = new Bundle();
        args.putString(USR_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_daily;
    }

    @Override
    protected void lazyLoad() {
        //获取登录的信息
        String string = SpUtils.getString(Myapplication.getApplication(), Contact.USERINFO, "");
        if (!TextUtils.isEmpty(string)) {
            mInforBean = JSON.parseObject(string, InforBean.class);
            mSectionInfos = mInforBean.getSectionInfos();
        }
        /*
         *根据账号类型判断当前删选条件的显示与隐藏 教师：隐藏学校跟类型
         *  当是教师时候，他的班级数据是从登录那得到的
         */
        mRoleNameCode = mInforBean.getRoleNameCode();//拿到账号的类型
        if (mRoleNameCode.equals(Contact.TEACHER)) {
            getTeacherData();//当账号为教师时候操作的数据
        } else {
            initDataSpinnerSchool();//添加学校
            inintDataSpinnerType();//添加类型
        }
        if (!bindlist.isEmpty())//切换再次进来，重新请求除去之前的数据
            bindlist.clear();
        recylerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        springview.setType(SpringView.Type.FOLLOW);
        springview.setHeader(new MeituanHeader(getActivity()));
        springview.setFooter(new MeituanFooter(getActivity()));
        getRefresh();
    }

    @Override
    public void showNetError() {

    }

    @Override
    public void noNetWork() {
        //没有网络时候
        UIUtils.showToastSafe(getResources().getString(R.string.string_nonet), 0);
    }

    @Override
    public void setPresenter(DailyInterface.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new DailyPresenter(this);
        }
    }

    @OnClick({R.id.real_serach, R.id.text_gl, R.id.liner_check_all, R.id.text_relieve, R.id.liner_yearname, R.id.liner_classname
            , R.id.text_reset, R.id.text_sure, R.id.text_grade_reset, R.id.text_grade_sure})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.real_serach://搜索页面
                Intent intent = new Intent(getActivity(), SerachActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Contact.DISTRIBUTION, Contact.HISTORTDATA);
                if (bundle != null) {
                    intent.putExtras(bundle);
                }
                startActivity(intent);
                break;
            case R.id.text_gl://管理
                getManager();
                break;
            case R.id.liner_check_all://全选
                getIsSelect();
                break;
            case R.id.text_relieve://解绑
                getBatchRelieve();
                break;
            case R.id.liner_yearname://按照年级筛选
                if (mainTeacherClazzBeans != null && mainTeacherClazzBeans.size() > 0) {
                    if (gradebool) {
                        linerSelectYearname.setVisibility(View.VISIBLE);
                        if (linerSelectClassname.getVisibility() == View.VISIBLE) ;
                        linerSelectClassname.setVisibility(View.GONE);
                        gradebool = false;
                    } else {
                        linerSelectYearname.setVisibility(View.GONE);
                        if (linerSelectClassname.getVisibility() == View.VISIBLE) ;
                        linerSelectClassname.setVisibility(View.GONE);
                        gradebool = true;
                    }
                } else {
                    UIUtils.showToastSafe("暂无数据", 0);
                }
                break;
            case R.id.text_reset://重置(班级或者科目)
                setReset();
                break;

            case R.id.liner_classname:
                if (classbool) {
                    //先判断年级是否选中，未选中不让点击
                    if (mainTeacherClazzBeans.isEmpty()) {
                        UIUtils.showToastSafe("请先筛选年级", 0);
                        return;
                    }
                    int selectNum = 0;
                    for (int i = 0; i < mainTeacherClazzBeans.size(); i++) {
                        if (mainTeacherClazzBeans.get(i).isAbool())
                            selectNum++;
                    }
                    if (selectNum == 0) {
                        UIUtils.showToastSafe("请先筛选年级", 0);
                        return;
                    } else {
                        if (linerSelectYearname.getVisibility() == View.VISIBLE) ;
                        linerSelectYearname.setVisibility(View.GONE);
                        linerSelectClassname.setVisibility(View.VISIBLE);
                        //根据账号类型判断获取不同的数据源
                        if (mInforBean.getRoleNameCode().equals(Contact.MANAGER)) {
                            //网络请求筛选班级或者科目的数据源
                            getClassOrSubjectData();//校园管理数据源
                        } else {
                            //教师的数据源
                            getClassNameData();
                        }

                    }
                    classbool = false;
                } else {
                    if (linerSelectYearname.getVisibility() == View.VISIBLE) ;
                    linerSelectYearname.setVisibility(View.GONE);
                    linerSelectClassname.setVisibility(View.GONE);
                    classbool = true;
                }
                break;

            case R.id.text_grade_sure:
                bindlist.clear();
                //拿到年级的集合遍历，选中的数据。做条件语句筛选。
                if (linerSelectYearname.getVisibility() == View.VISIBLE) ;
                linerSelectYearname.setVisibility(View.GONE);
                gradebool = true;
                //拿到选中的年级的Id
                StringBuilder build = new StringBuilder();
                for (int i = 0; i < mainTeacherClazzBeans.size(); i++) {
                    if (mainTeacherClazzBeans.get(i).isAbool())
                        build.append(mainTeacherClazzBeans.get(i).getGradeId() + ",");
                }
                String str = build.toString();
                //把最后一个d逗号截取掉
                if (TextUtils.isEmpty(str)) {
                    presenter.getBindListData(mInforBean.getUserId(), searchTag, gradeId, classIds, keywords, pageTag, subjectIds, ps, pn);
                    return;
                } else {
                    String substring = str.substring(0, str.length() - 1);
                    presenter.getBindListData(mInforBean.getUserId(), searchTag, substring, classIds, keywords, pageTag, subjectIds, ps, pn);
                }
                break;

            case R.id.text_grade_reset://年级重置
                for (int i = 0; i < mainTeacherClazzBeans.size(); i++) {
                    if (mainTeacherClazzBeans.get(i).isAbool())
                        mainTeacherClazzBeans.get(i).setAbool(false);

                }
                if (mGradeAadapter != null) {
                    mGradeAadapter.getSuccessData(mainTeacherClazzBeans);
                }

                break;
            case R.id.text_sure://确定(班级或者科目)
                setSure();
                break;
        }

    }

    /**
     * 拿到科目或者班级的数据，请求网络
     */
    private void setSure() {
        bindlist.clear();
        pn = 1;
        if (linerSelectClassname.getVisibility() == View.VISIBLE) ;
        linerSelectClassname.setVisibility(View.GONE);
        if (searchTag == 1) {//拿到选科目的Id
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < subjectList.size(); i++) {
                List<InforBean.MainTeacherClazzBean.SubjectInfosBean> infosBeans = subjectList.get(i).getSubjectInfos();
                if (infosBeans == null || infosBeans.isEmpty())
                    return;
                for (int j = 0; j < infosBeans.size(); j++) {
                    if (infosBeans.get(i).isBoolean()) {
                        stringBuilder.append(infosBeans.get(i).getSubjectId() + ",");
                    }

                }
            }
            String subjectId = stringBuilder.toString();
            if (TextUtils.isEmpty(subjectId)) {
                presenter.getBindListData(mInforBean.getUserId(), searchTag, gradeId, classIds, keywords, pageTag, subjectIds, ps, pn);
            } else {
                String substring = subjectId.substring(0, subjectId.length() - 1);
                if (!TextUtils.isEmpty(substring)) {
                    presenter.getBindListData(mInforBean.getUserId(), searchTag, gradeId, classIds, keywords, pageTag, substring, ps, pn);
                }
            }
        } else {
            //清空班级集合
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < classNameList.size(); i++) {
                List<InforBean.MainTeacherClazzBean.ClassInfosBean> classInfos = classNameList.get(i).getClassInfos();
                if (classInfos == null || classInfos.isEmpty())
                    return;
                for (int j = 0; j < classInfos.size(); j++) {
                    if (classInfos.get(i).isAbool()) {
                        stringBuilder.append(classInfos.get(i).getClassId() + ",");
                    }
                }
            }
            String classId = stringBuilder.toString();
            if (TextUtils.isEmpty(classId)) {
                presenter.getBindListData(mInforBean.getUserId(), searchTag, gradeId, classIds, keywords, pageTag, subjectIds, ps, pn);
            } else {
                String classIdsing = classId.substring(0, classId.length() - 1);
                if (!TextUtils.isEmpty(classIdsing)) {
                    classIds = classIdsing;
                    presenter.getBindListData(mInforBean.getUserId(), searchTag, gradeId, classIds, keywords, pageTag, subjectIds, ps, pn);
                }
            }

        }
    }

    /**
     * 教师班级的数据源
     */
    private void getClassNameData() {
        if (mainTeacherClazzBeans.size() > 0) {
            classNameList.clear();
            for (int i = 0; i < mainTeacherClazzBeans.size(); i++) {
                if (mainTeacherClazzBeans.get(i).isAbool()) {
                    InforBean.MainTeacherClazzBean mainTeacherClazzBean = mainTeacherClazzBeans.get(i);
                    classNameList.add(mainTeacherClazzBean);
                }
            }
            if (mClassAdapter == null) {
                mClassAdapter = new ClassAdapter(classNameList, getActivity(), searchTag);
            }
            mClassAdapter.setClassAdapter(recyclerClass, mInforBean);
        }

    }

    /**
     * 获取班或者科目的数据源
     */
    private void getClassOrSubjectData() {
        if (searchTag == 2) { //每次点击清空再重新请求
            classNameList.clear();
        } else {
            subjectList.clear();
        }
        //拿到选中的年级的Id
        StringBuilder build = new StringBuilder();
        for (int i = 0; i < mainTeacherClazzBeans.size(); i++) {
            if (mainTeacherClazzBeans.get(i).isAbool())
                build.append(mainTeacherClazzBeans.get(i).getGradeId() + ",");
        }
        String str = build.toString();
        //把最后一个d逗号截取掉
        String substring = str.substring(0, str.length() - 1);
        if (TextUtils.isEmpty(substring)) {
            return;
        }
        presenter.getClassNameOrSubject(mInforBean.getUserId(), substring, searchTag);
    }

    /**
     * 重置
     */
    private void setReset() {
        //除去集合中选中的值。
        if (searchTag == 1) {//学生清空科目集合
            for (int i = 0; i < subjectList.size(); i++) {
                List<InforBean.MainTeacherClazzBean.SubjectInfosBean> subjectInfos = subjectList.get(i).getSubjectInfos();
                for (int j = 0; j < subjectInfos.size(); j++) {
                    subjectInfos.get(j).setBoolean(false);
                    if (mClassAdapter != null) {
                        mClassAdapter.getSuccessData(subjectList, searchTag);
                    }

                }
            }
        } else {
            //清空班级集合
            for (int i = 0; i < classNameList.size(); i++) {
                List<InforBean.MainTeacherClazzBean.ClassInfosBean> classInfos = classNameList.get(i).getClassInfos();
                for (int j = 0; j < classInfos.size(); j++) {
                    classInfos.get(j).setAbool(false);
                    if (mClassAdapter != null) {
                        mClassAdapter.getSuccessData(classNameList, searchTag);
                    }

                }
            }
        }
    }

    /**
     * 批量解绑
     */
    private void getBatchRelieve() {
        int mSelectDeleteSize = SelectDeleteSize();
        if (mSelectDeleteSize == 0) {
            UIUtils.showToastSafe("请选择要解绑的设备", 0);
        } else {
            List<DeleteBean> deleteEqumentList = new ArrayList<>();
            //获取数据源里面需要解绑的数据
            for (int i = 0; i < bindlist.size(); i++) {
                if (bindlist.get(i).isAbool()) {
                    DeleteBean deleteEqument = new DeleteBean();
                    String deviceId = bindlist.get(i).getDeviceId();
                    deleteEqument.setDeviceId(deviceId);
                    int userId = bindlist.get(i).getUserId();
                    deleteEqument.setUserId(userId);
                    String deviceModelCode = bindlist.get(i).getDeviceModelCode();
                    deleteEqument.setDeviceModelCode(deviceModelCode);
                    deleteEqumentList.add(deleteEqument);
                }
            }
            DailogBean dailogBean = new DailogBean();
            dailogBean.setTitle("该操作将解除设备与账号的关联");
            dailogBean.setCount("确定解绑" + mSelectDeleteSize + "台设备?");
            if (mBatchDeleteDialog == null) {
                mBatchDeleteDialog = new CurrencyDailogs(dailogBean, getActivity());
                mBatchDeleteDialog.setOnClickListener(new CurrencyDailogs.SetOnClick() {
                    @Override
                    public void getOk() {
                        textGl.setText(mabool == true ? "完成" : "管理");
                        mabool = !mabool;
                        presenter.DeleteEqument(deleteEqumentList);
                        mBatchDeleteDialog.dismiss();
                    }
                });

            }
            mBatchDeleteDialog.show();
        }
    }

    /**
     * 拿到解绑选中的数量
     *
     * @return
     */
    private int SelectDeleteSize() {
        int selectSize = 0;
        for (int i = 0; i < bindlist.size(); i++) {
            if (bindlist.get(i).isAbool()) {
                selectSize++;
            }
        }

        return selectSize;
    }

    /**
     * 是否全选
     */
    private void getIsSelect() {
        imgSelect.setImageResource(isSelect ? R.drawable.select_true : R.drawable.select_false);//全选按钮
        if (isSelect) {
            //全选的话，遍历数据源更改状态值
            for (int i = 0; i < bindlist.size(); i++) {
                if (!bindlist.get(i).isAbool())
                    bindlist.get(i).setAbool(true);
                mBindListAdapter.getBindList(bindlist, photoabool);

            }
        } else {
            for (int i = 0; i < bindlist.size(); i++) {
                if (bindlist.get(i).isAbool())
                    bindlist.get(i).setAbool(false);
                mBindListAdapter.getBindList(bindlist, photoabool);

            }
        }
        isSelect = !isSelect;
    }

    /**
     * 对列表的管理
     */
    private void getManager() {
        if (bindlist.isEmpty()) {
            UIUtils.showToastSafe("没有已绑定的账号。", 0);
            return;
        } else {
            linerGl.setVisibility(mabool == true ? View.VISIBLE : View.GONE);
            if ("管理".equals(textGl.getText().toString())) {
                photoabool = true;
            } else {
                photoabool = false;
            }
            if (mBindListAdapter != null) {
                mBindListAdapter.getBindList(bindlist, photoabool);
            }

            textGl.setText(mabool == true ? "完成" : "管理");
            mabool = !mabool;//该值用来控制切换是管理还是完成状态
        }
    }

    @Override
    public void SuccessData(List<Bindlistbean> bindlistbeans) {
        if (!bindlistbeans.isEmpty()) {
            bindlist.addAll(bindlistbeans);
            if (bindlist.size() == 0) {
                linerNodata.setVisibility(View.VISIBLE);
                linerRecycler.setVisibility(View.GONE);
            } else {
                linerNodata.setVisibility(View.GONE);
                linerRecycler.setVisibility(View.VISIBLE);
                initSetAdapter(bindlist);//添加列表数据
            }
        } else {
            if (bindlist.size() == 0) {
                linerNodata.setVisibility(View.VISIBLE);
                linerRecycler.setVisibility(View.GONE);
            } else {
                UIUtils.showToastSafe("没有更多数据了..", 0);
            }
        }
    }

    @Override
    public void BindListError() {
        //当token失效时候
        UIUtils.showToastSafe("登录过期：请重新登录", 0);
        SpUtils.putBoolean(Myapplication.getApplication(), Contact.LOGINSTATE, false);
        SpUtils.putString(Myapplication.getApplication(), Contact.USERINFO, "");
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void DeleteSuccessEqument(List<DeleteEqument> deleteEqumentList) {
        bindlist.clear();
        presenter.getBindListData(mInforBean.getUserId(), searchTag, gradeId, classIds, keywords, pageTag, subjectIds, ps, pn);
        linerGl.setVisibility(View.GONE);
        photoabool = false;
    }

    /**
     * 数据刷新
     */
    private void getRefresh() {
        springview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bindlist.clear();
                        pageNum = 1;
                        presenter.getBindListData(mInforBean.getUserId(), searchTag, gradeId, classIds, keywords, pageTag, subjectIds, ps, pageNum);
                        springview.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageNum++;
                        presenter.getBindListData(mInforBean.getUserId(), searchTag, gradeId, classIds, keywords, pageTag, subjectIds, ps, pageNum);
                        springview.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

        });
    }

    /**
     * 下拉框选择类别
     */
    private void inintDataSpinnerType() {
        //设置假数据
        List<String> typelList = new ArrayList<>();
        typelList.add("教师");
        typelList.add("学生");
        BaseSpinnerAdapter baseTypeAdapter = new BaseSpinnerAdapter(getActivity(), typelList) {
            @Nullable
            @Override
            public Object getItem(int position) {
                return typelList.get(position);
            }
        };
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name = (String) baseTypeAdapter.getObjectItem(i);
                if (!bindlist.isEmpty())
                    bindlist.clear();
                if (Contact.TEACHER_NAME.equals(name)) {
                    textSelectTitle.setText(R.string.string_subject);
                    searchTag = 1;//选择教师类型为1
                } else if (Contact.STUDENT.equals(name)) {
                    textSelectTitle.setText(R.string.string_class);
                    searchTag = 2;//选择学生类型为2
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!mainTeacherClazzBeans.isEmpty()) {
                            //切换时候需要将年级重置
                            for (int j = 0; j < mainTeacherClazzBeans.size(); j++) {
                                if (mainTeacherClazzBeans.get(j).isAbool())
                                    mainTeacherClazzBeans.get(j).setAbool(false);
                            }
                        }
                    }
                }).start();
                presenter.getBindListData(mInforBean.getUserId(), searchTag, gradeId, classIds, keywords, pageTag, subjectIds, ps, pn);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinnerType.setAdapter(baseTypeAdapter);
    }

    /**
     * 下拉框学校
     */
    private void initDataSpinnerSchool() {
        if (mSectionInfos.isEmpty())
            return;
        BaseSpinnerAdapter baseSchollAdapter = new BaseSpinnerAdapter(getActivity(), mSectionInfos) {
            @Nullable
            @Override
            public Object getItem(int position) {
                mSchoolId = mSectionInfos.get(position).getSectionNameCode();
                return mSectionInfos.get(position).getSectionNameValue();
            }
        };
        spinnerSchool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                InforBean.SectionInfosBean sectionInfosBean = (InforBean.SectionInfosBean) baseSchollAdapter.getObjectItem(i);
                /**
                 * 获取年级数据
                 *
                 * @param sectionId
                 */
                presenter.getClassData(mInforBean.getUserId(), sectionInfosBean.getSectionNameCode());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerSchool.setAdapter(baseSchollAdapter);

    }

    /**
     * 获取成功的年级数据
     *
     * @param mainTeacherClazzBean
     */
    @Override
    public void getSuccessClassData(List<InforBean.MainTeacherClazzBean> mainTeacherClazzBean) {
        mainTeacherClazzBeans.addAll(mainTeacherClazzBean);
        setGradeAdater(mainTeacherClazzBeans);//设置年级的数据源
    }

    @Override
    public void getSuccessClassOrSubject(List<InforBean.MainTeacherClazzBean> mainTeacherClazzBean) {
        if (searchTag == 2) {
            classNameList.clear();//每次清空之前数据
            classNameList.addAll(mainTeacherClazzBeans);
            //设置适配器
            if (mClassAdapter == null) {
                mClassAdapter = new ClassAdapter(classNameList, getActivity(), searchTag);
            }
            mClassAdapter.setClassAdapter(recyclerClass, mInforBean);
        } else {
            subjectList.addAll(mainTeacherClazzBeans);
            if (mClassAdapter == null) {
                mClassAdapter = new ClassAdapter(subjectList, getActivity(), searchTag);
            }
            mClassAdapter.setClassAdapter(recyclerClass, mInforBean);
        }
    }

    /**
     * 当账号为教师时候操作的数据
     */
    private void getTeacherData() {
        //教师
        linerSchool.setVisibility(View.GONE);//隐藏阶段类型
        linerTeacher.setVisibility(View.GONE);//隐藏选择教师还是学生筛选条件
        List<InforBean.MainTeacherClazzBean> mainTeacherClazz = mInforBean.getMainTeacherClazz();
        if (mainTeacherClazz != null && !mainTeacherClazz.isEmpty()) {
            if ((mainTeacherClazzBeans.isEmpty())) {
                mainTeacherClazzBeans.addAll(mainTeacherClazz);
                setGradeAdater(mainTeacherClazzBeans);
            }
        }
        presenter.getBindListData(mInforBean.getUserId(), searchTag, gradeId, classIds, keywords, pageTag, subjectIds, ps, 1);
    }

    /**
     * 设置列表页面数据
     */
    private void initSetAdapter(List<Bindlistbean> bindlistbeans) {
        /**
         *   加载更多数据时候。只有有数据加载下来
         *   当前全选按钮就会取消
         */
        imgSelect.setImageResource(R.drawable.select_false);//根据逻辑会改动
        if (mBindListAdapter == null) {
            mBindListAdapter = new BindListAdapter(photoabool, bindlistbeans);
            mBindListAdapter.initSetAdapter(getActivity(), recylerList);
        } else {
            mBindListAdapter.getBindList(bindlistbeans, photoabool);
        }

        mBindListAdapter.setOnClickListener(new BindListAdapter.SetOnClick() {
            @Override
            public void getOnClick(Bindlistbean bindlistbean, CommonRecylerAdapter mCommonRecylerAdapter) {

                if ("管理".equals(textGl.getText().toString())) {
                    setDeleteData(bindlistbean);
                } else {
                    bindlistbean.setAbool(!bindlistbean.isAbool());
                    mCommonRecylerAdapter.notifyDataSetChanged();
                    //检查数据源中的元素是否都选中
                    boolean isSelectBindList = getIsSelectBindList();
                    imgSelect.setImageResource(isSelectBindList ? R.drawable.select_true : R.drawable.select_false);//全选按钮
                }
            }
        });
        springview.onFinishFreshAndLoad();

    }

    //设置年级适配器
    private void setGradeAdater(List<InforBean.MainTeacherClazzBean> mainTeacherClazzBeans) {
        if (mGradeAadapter == null) {
            mGradeAadapter = new GradeAadapter(mainTeacherClazzBeans, getActivity());
        }
        mGradeAadapter.setGradeAdater(recyclerYearName);
    }

    /**
     * 解绑数据
     */
    private void setDeleteData(Bindlistbean bindlistbean) {
        if (mCurrencyDailogs == null) {
            DailogBean dailogBean = new DailogBean();
            dailogBean.setTitle("该操作将解除设备与账号的关联！");
            dailogBean.setCount("确定解绑！");
            mCurrencyDailogs = new CurrencyDailogs(dailogBean, getActivity());
            mCurrencyDailogs.setOnClickListener(new CurrencyDailogs.SetOnClick() {
                @Override
                public void getOk() {
                    NetDeleteWork(bindlistbean);
                    mCurrencyDailogs.dismiss();
                }

            });
        }
        mCurrencyDailogs.show();

    }

    /**
     * 解绑
     */
    private void NetDeleteWork(Bindlistbean deleteEqumentList) {
        List<DeleteBean> list = new ArrayList<>();
        //拼装数据
        DeleteBean deleteEqument = new DeleteBean();
        deleteEqument.setUserId(deleteEqumentList.getUserId());
        deleteEqument.setDeviceId(deleteEqumentList.getDeviceId());
        deleteEqument.setDeviceModelCode(deleteEqumentList.getDeviceModelCode());
        //还差一个设备类型
        list.add(deleteEqument);
        presenter.DeleteEqument(list);
    }

    /**
     * 检查集合中元素是否全选
     */
    private boolean getIsSelectBindList() {
        for (int i = 0; i < bindlist.size(); i++) {
            if (!bindlist.get(i).isAbool()) {

                return false;
            }
        }
        return true;
    }
}
