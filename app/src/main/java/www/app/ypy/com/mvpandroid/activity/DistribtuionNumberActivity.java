package www.app.ypy.com.mvpandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.app.ypy.com.mvpandroid.R;
import www.app.ypy.com.mvpandroid.adapter.CommonRecylerAdapter;
import www.app.ypy.com.mvpandroid.application.Myapplication;
import www.app.ypy.com.mvpandroid.base.BaseMvpActivity;
import www.app.ypy.com.mvpandroid.base.BaseSpinnerAdapter;
import www.app.ypy.com.mvpandroid.bean.Bindlistbean;
import www.app.ypy.com.mvpandroid.bean.DailogBean;
import www.app.ypy.com.mvpandroid.bean.DeleteEqument;
import www.app.ypy.com.mvpandroid.bean.EventMessage;
import www.app.ypy.com.mvpandroid.bean.InforBean;
import www.app.ypy.com.mvpandroid.bean.Scanbean;
import www.app.ypy.com.mvpandroid.dailymoudel.DailyInterface;
import www.app.ypy.com.mvpandroid.dailymoudel.DailyPresenter;
import www.app.ypy.com.mvpandroid.dialog.BaseDialog;
import www.app.ypy.com.mvpandroid.dialog.CurrencyDailogs;
import www.app.ypy.com.mvpandroid.gradeadapter.BindListEqumentAdapter;
import www.app.ypy.com.mvpandroid.gradeadapter.ClassAdapter;
import www.app.ypy.com.mvpandroid.gradeadapter.GradeAadapter;
import www.app.ypy.com.mvpandroid.utils.Contact;
import www.app.ypy.com.mvpandroid.utils.HLog;
import www.app.ypy.com.mvpandroid.utils.SpUtils;
import www.app.ypy.com.mvpandroid.utils.UIUtils;

/**
 * Created by ypu
 * on 2019/9/3 0003
 */
public class DistribtuionNumberActivity extends BaseMvpActivity<DailyInterface.Presenter> implements DailyInterface.View {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.text_right)
    TextView textRight;
    @BindView(R.id.img_serachs)
    ImageView imgSerachs;
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
    @BindView(R.id.liner_select)
    LinearLayout linerSelect;
    @BindView(R.id.text_titles)
    TextView textTitles;
    @BindView(R.id.recycler_data)
    RecyclerView recyclerData;
    @BindView(R.id.spring_view)
    SpringView springView;
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
    @BindView(R.id.text_select_all)
    TextView textSelectAll;
    @BindView(R.id.img_select_all)
    ImageView imgSelectAll;
    @BindView(R.id.liner_bninding)
    LinearLayout linerBninding;
    @BindView(R.id.text_select_number)
    TextView textSelectNumber;
    private ArrayList<Scanbean> mParcelableArrayList;//设备的数据源
    private InforBean mInforBean;//登录后的信息
    private List<InforBean.SectionInfosBean> mSectionInfos;//阶段类型数据源
    private String mRoleNameCode;//选中的阶段的Id
    int searchTag = 1; //类型区分，1是教师，2;学生
    List<InforBean.MainTeacherClazzBean> mainTeacherClazzBeans = new ArrayList<>();//年级的数据源
    private List<Bindlistbean> bindlist = new ArrayList<>();//列表数据源
    private GradeAadapter mGradeAadapter;
    String classIds = null; //班级的Id
    String keywords = null; //搜索的内容
    int pageTag = 2;//1:设备列表: 2：人员列表
    String subjectIds = null; //当是教师时候，有学科。可以多选
    int ps = 15;//请求的页面数量
    int pn = 1;//请求的页数
    boolean gradeBool = true;//年级的状态值
    private String gradeId = null;
    private String mSchoolId;//阶段的Id
    boolean classBool = true;//班级或者科目条目的状态值
    List<InforBean.MainTeacherClazzBean> subjectList = new ArrayList<>();//科目的数据源
    List<InforBean.MainTeacherClazzBean> classNameList = new ArrayList<>();//班数据源
    ClassAdapter mClassAdapter;
    private BindListEqumentAdapter mBindListEqumentAdapter;
    boolean selectInfo = true;//全选中的一个状态
    CurrencyDailogs mCurrencyDailogs;
    CurrencyDailogs mCurrencyFailDailogs;

    @Override
    protected int getXmlView() {
        return R.layout.activity_distribution_num;
    }

    @Override
    protected void loadData() {
        textTitle.setText("分配账号");
        imgSerachs.setVisibility(View.VISIBLE);
        textRight.setVisibility(View.GONE);
        textSelectNumber.setText("(0/" + mParcelableArrayList.size() + ")");//显示传过来的设备数量
        recyclerData.setLayoutManager(new GridLayoutManager(this, 3));
        //获取登录的信息
        String string = SpUtils.getString(Myapplication.getApplication(), Contact.USERINFO, "");
        if (!TextUtils.isEmpty(string)) {
            mInforBean = JSON.parseObject(string, InforBean.class);
            mSectionInfos = mInforBean.getSectionInfos();
        }
        //根据账号类型判断当前删选条件的显示与隐藏 教师：隐藏学校跟类型
        mRoleNameCode = mInforBean.getRoleNameCode();
        if (mRoleNameCode.equals(Contact.TEACHER)) {
            getTeacherData();
        } else {
            initDataSpinnerSchool();//添加学校
            inintDataSpinnerType();//添加类型
        }
        getRefresh();//刷新数据
    }

    @Override
    public void processExtraData() {
        super.processExtraData();
        //拿到intent 传过来值(扫码的集合数据源)
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        mParcelableArrayList = extras.getParcelableArrayList(Contact.SELECTDATA);

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

    /**
     * n当用户为教师时候加载的数据
     */
    private void getTeacherData() {
        searchTag = 2;
        //教师
        linerSchool.setVisibility(View.GONE);
        linerTeacher.setVisibility(View.GONE);
        List<InforBean.MainTeacherClazzBean> mainTeacherClazz = mInforBean.getMainTeacherClazz();
        mainTeacherClazzBeans.addAll(mainTeacherClazz);
        setGradeAdater(mainTeacherClazzBeans);
        presenter.getBindListData(mInforBean.getUserId(), searchTag, gradeId, classIds, keywords, pageTag, subjectIds, ps, 1);
    }

    //设置年级适配器
    private void setGradeAdater(List<InforBean.MainTeacherClazzBean> mainTeacherClazzBeans) {
        if (mGradeAadapter == null) {
            mGradeAadapter = new GradeAadapter(mainTeacherClazzBeans, this);
            mGradeAadapter.setGradeAdater(recyclerYearName);
        }else {
            mGradeAadapter.getSuccessData(mainTeacherClazzBeans);
        }

    }

    /**
     * 数据刷新
     */
    private void getRefresh() {
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bindlist.clear();
                        pn = 1;
                        presenter.getBindListData(mInforBean.getUserId(), searchTag, gradeId, classIds, keywords, pageTag, subjectIds, ps, pn);
                        springView.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pn++;
                        presenter.getBindListData(mInforBean.getUserId(), searchTag, gradeId, classIds, keywords, pageTag, subjectIds, ps, pn);
                        springView.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

        });
    }

    /**
     * 下拉框学校
     */
    private void initDataSpinnerSchool() {

        BaseSpinnerAdapter baseSchollAdapter = new BaseSpinnerAdapter(this, mSectionInfos) {
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
     * 下拉框选择类别
     */
    private void inintDataSpinnerType() {
        //设置假数据
        List<String> typelList = new ArrayList<>();
        typelList.add("教师");
        typelList.add("学生");
        BaseSpinnerAdapter baseTypeAdapter = new BaseSpinnerAdapter(this, typelList) {
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
                            mGradeAadapter.getSuccessData(mainTeacherClazzBeans);
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

    @Override
    public void SuccessData(List<Bindlistbean> bindlistbeans) {
        if (!bindlistbeans.isEmpty()) {
            bindlist.addAll(bindlistbeans);
            if (bindlist.size() == 0) {
                linerNodata.setVisibility(View.VISIBLE);
                recyclerData.setVisibility(View.GONE);
                textTitles.setText(View.GONE);
            } else {
                linerNodata.setVisibility(View.GONE);
                recyclerData.setVisibility(View.VISIBLE);
                initSetAdapter(bindlist);//添加列表数据
            }
        } else {
            if (bindlist.size() == 0) {
                linerNodata.setVisibility(View.VISIBLE);
                recyclerData.setVisibility(View.GONE);
                textTitles.setText(View.GONE);
            } else {
                toastShort("没有更多数据了..");
            }
        }
    }

    public void initSetAdapter(List<Bindlistbean> bindlist) {
        springView.onFinishFreshAndLoad();

        if (mBindListEqumentAdapter == null) {
            mBindListEqumentAdapter = new BindListEqumentAdapter(bindlist, this, recyclerData);
            mBindListEqumentAdapter.initSetData();
        } else {
            mBindListEqumentAdapter.getSuccessData(bindlist);
        }
        mBindListEqumentAdapter.setOnClickListener(new BindListEqumentAdapter.SetOnClick() {
            @Override
            public void getOnClick(int position) {
                boolean isnum = true;
                if (bindlist != null && bindlist.size() > 0) {


                    if (!bindlist.get(position).isAbool()) {
                        int num = getNum(bindlist);
                        if (mParcelableArrayList.size() == num) {
                            toastShort("选中账号数量不能超过设备数量");
                            isnum = false;
                            return;
                        }
                    }
                    if (isnum) {
                        bindlist.get(position).setAbool(!bindlist.get(position).isAbool());
                        mBindListEqumentAdapter.getSuccessData(bindlist);
                    }
                    int num = getNum(bindlist);
                    textSelectNumber.setText("(" + num + "/" + mParcelableArrayList.size() + ")");//显示传过来的设备数量
                    if (num == bindlist.size()) {
                        imgSelectAll.setImageResource(R.drawable.select_off);
                    } else {
                        imgSelectAll.setImageResource(R.drawable.select_no);
                    }
                }
            }
                private int getNum (List < Bindlistbean > bindlist) {
                    int num = 0;
                    for (int i = 0; i < bindlist.size(); i++) {
                        if (bindlist.get(i).isAbool())
                            num++;
                    }
                    return num;
                }

        });

    }

    @Override
    public void BindListError() {
        //当token失效时候
        UIUtils.showToastSafe("登录过期：请重新登录", 0);
        SpUtils.putBoolean(Myapplication.getApplication(), Contact.LOGINSTATE, false);
        SpUtils.putString(Myapplication.getApplication(), Contact.USERINFO, "");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void DeleteSuccessEqument(List<DeleteEqument> deleteEqumentList) {

    }

    @OnClick({R.id.img_back, R.id.img_serachs, R.id.liner_yearname, R.id.text_reset, R.id.text_sure, R.id.liner_classname, R.id.text_grade_reset,
            R.id.text_grade_sure, R.id.img_select_all, R.id.liner_bninding})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_serachs://搜索
                //搜索
                Bundle bundle = new Bundle();
                bundle.putString(Contact.DISTRIBUTION, Contact.DISTRIBUTION);
                startIntentWithExtras(SerachActivity.class, bundle);
                break;
            case R.id.liner_yearname://按照年级筛选
                if (mainTeacherClazzBeans != null && mainTeacherClazzBeans.size() > 0) {
                    if (gradeBool) {
                        linerSelectYearname.setVisibility(View.VISIBLE);
                        if (linerSelectClassname.getVisibility() == View.VISIBLE) ;
                        linerSelectClassname.setVisibility(View.GONE);
                        gradeBool = false;
                    } else {
                        linerSelectYearname.setVisibility(View.GONE);
                        if (linerSelectClassname.getVisibility() == View.VISIBLE) ;
                        linerSelectClassname.setVisibility(View.GONE);
                        gradeBool = true;
                    }
                } else {
                    HLog.toast(this, "暂无数据");
                }

                break;
            case R.id.text_reset://重置
                //除去集合中选中的值。
                setRest();
                break;
            case R.id.text_sure://确定
                getClassOrSubject();
                break;
            case R.id.liner_classname://按照班级筛选
                if (classBool) {
                    //先判断年级是否选中，未选中不让点击
                    if (mainTeacherClazzBeans.isEmpty()) {
                        HLog.toast(this, "请先筛选年级");
                        return;
                    }
                    int selectNum = 0;
                    for (int i = 0; i < mainTeacherClazzBeans.size(); i++) {
                        if (mainTeacherClazzBeans.get(i).isAbool())
                            selectNum++;
                    }
                    if (selectNum == 0) {
                        HLog.toast(this, "请先筛选年级");
                        return;
                    } else {
                        //根据账号类型判断获取不同的数据源
                        if (mInforBean.getRoleNameCode().equals(Contact.MANAGER)) {
                            //网络请求筛选班级或者科目的数据源
                            getClassOrSubjectData();//校园管理数据源
                        } else {
                            //教师的数据源
                            getClassNameData();
                        }

                    }
                    classBool = false;
                } else {
                    if (linerSelectYearname.getVisibility() == View.VISIBLE) {
                        linerSelectYearname.setVisibility(View.GONE);
                    }
                    linerSelectClassname.setVisibility(View.GONE);

                    classBool = true;
                }
                break;
            case R.id.text_grade_reset://年级重置
                for (int i = 0; i < mainTeacherClazzBeans.size(); i++) {
                    if (mainTeacherClazzBeans.get(i).isAbool())
                        mainTeacherClazzBeans.get(i).setAbool(false);
                }
                mClassAdapter.getSuccessData(mainTeacherClazzBeans, searchTag);
                break;
            case R.id.text_grade_sure://确定
                //拿到年级的集合遍历，选中的数据。做条件语句筛选。
                if (linerSelectYearname.getVisibility() == View.VISIBLE) ;
                linerSelectYearname.setVisibility(View.GONE);
                gradeBool = true;
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
                }
                bindlist.clear();
                String substring = str.substring(0, str.length() - 1);
                presenter.getBindListData(mInforBean.getUserId(), searchTag, substring, classIds, keywords, pageTag, subjectIds, ps, pn);
                break;

            case R.id.img_select_all://全选
                getisSelect();
                break;
            case R.id.liner_bninding://账号绑定
                List<DeleteEqument> list = new ArrayList<>();
                //组装数据
                int selectnum = 0;
                for (int i = 0; i < bindlist.size(); i++) {
                    if (bindlist.get(i).isAbool()) {
                        DeleteEqument bindbean = new DeleteEqument();
                        bindbean.setUserId(bindlist.get(i).getUserId());
                        bindbean.setDeviceId(mParcelableArrayList.get(selectnum).getDeviceId());
                        bindbean.setDeviceModelCode(mParcelableArrayList.get(selectnum).getDeviceModelCode());
                        selectnum++;
                        list.add(bindbean);
                    }
                }
                presenter.getBindEqumentData(list);
                Log.d("tag", JSON.toJSONString(list));
                break;
        }
    }

    @Override
    public void getSuccessClassData(List<InforBean.MainTeacherClazzBean> mainTeacherClazzBean) {
        mainTeacherClazzBeans.clear();
        mainTeacherClazzBeans.addAll(mainTeacherClazzBean);
        setGradeAdater(mainTeacherClazzBeans);//设置年级的数据源
    }

    @Override
    public void getSuccessClassOrSubject(List<InforBean.MainTeacherClazzBean> mainTeacherClazzBean) {
        Log.d("TAG", mainTeacherClazzBean.size() + "------------");
        if (mainTeacherClazzBean == null || mainTeacherClazzBean.size() <= 0) {
            if (linerSelectYearname.getVisibility() == View.VISIBLE) {
                linerSelectYearname.setVisibility(View.GONE);
            }
            UIUtils.showToastSafe("暂时没有数据", 0);
            linerSelectClassname.setVisibility(View.GONE);
        } else {
            linerSelectClassname.setVisibility(View.VISIBLE);
            if (searchTag == 2) {
                classNameList.clear();//每次清空之前数据
                classNameList.addAll(mainTeacherClazzBean);
                //设置适配器
                if (mClassAdapter == null) {
                    mClassAdapter = new ClassAdapter(classNameList, this, searchTag);
                    mClassAdapter.setClassAdapter(recyclerClass, mInforBean);
                    Log.d("TAG", "--------------走这边吗" + classNameList.size());
                } else {
                    mClassAdapter.getSuccessData(classNameList, searchTag);
                    Log.d("TAG", "--------------zhekau" + classNameList.size());
                }

            } else {
                subjectList.clear();
                subjectList.addAll(mainTeacherClazzBean);
                if (mClassAdapter == null) {
                    mClassAdapter = new ClassAdapter(subjectList, this, searchTag);
                    mClassAdapter.setClassAdapter(recyclerClass, mInforBean);
                }else {
                    mClassAdapter.getSuccessData(subjectList,searchTag);
                }

            }
        }
    }

    @Override
    public void getSuccessData(int failNumber, int successNumber) {
        String string = SpUtils.getString(Myapplication.getApplication(), Contact.NOSELECTDATA, "");
        Log.d("TAG", string + "--------数据-----");
        if (failNumber == 0) {
            if (!TextUtils.isEmpty(string) && !"[]".equals(string)) {
                getShowSuccessDialog(failNumber, successNumber);//设备绑定成功，提示还有未绑定的是否绑定
            } else {
                EventMessage eventMessage = new EventMessage();
                eventMessage.setMsg(Contact.REFRECH);
                EventBus.getDefault().postSticky(eventMessage);
                toastShort("设备绑定成功");
                startIntent(MainActivity.class);
            }
        } else {
            getShowFailDialog(failNumber, successNumber);
        }
    }

    /**
     * 显示成功的dialog
     *
     * @param failNumber
     * @param successNumber
     */
    private void getShowSuccessDialog(int failNumber, int successNumber) {
        DailogBean dailogBean = new DailogBean();
        dailogBean.setTitle("成功绑定" + successNumber + "台设备," + "失败" + failNumber + "台");
        dailogBean.setCount("还有未绑定设备是否继续绑定！");
        if (mCurrencyDailogs == null) {
            mCurrencyDailogs = new CurrencyDailogs(dailogBean, this);
            mCurrencyDailogs.setOnClickListener(new CurrencyDailogs.SetOnClick() {
                @Override
                public void getOk() {
                    mCurrencyDailogs.dismiss();
                    setResult(Contact.DISTRIBUTON);
                    finish();
                }

                @Override
                public void getCancle() {
                    startIntent(MainActivity.class);
                    mCurrencyDailogs.dismiss();
                    finish();
                }
            });
        }
        mCurrencyDailogs.show();
    }

    /**
     * 失败显示的dialog
     */
    private void getShowFailDialog(int failNumber, int successNumber) {
        DailogBean dailogBean = new DailogBean();
        dailogBean.setTitle("绑定失败，成功绑定" + successNumber + "台设备," + "失败" + failNumber + "台");
        dailogBean.setCount("是否继续绑定！");
        if (mCurrencyFailDailogs == null) {
            mCurrencyFailDailogs = new CurrencyDailogs(dailogBean, this);
            mCurrencyFailDailogs.setOnClickListener(new CurrencyDailogs.SetOnClick() {
                @Override
                public void getOk() {
                    mCurrencyFailDailogs.dismiss();
                    setResult(Contact.DISTRIBUTON);
                    finish();
                }

                @Override
                public void getCancle() {
                    mCurrencyFailDailogs.dismiss();
                    startIntent(MainActivity.class);
                    finish();
                }
            });
        }
        mCurrencyFailDailogs.show();
    }

    /**
     * 重置数据
     */
    private void setRest() {
        if (searchTag == 1) {//教师清空科目集合
            for (int i = 0; i < subjectList.size(); i++) {
                List<InforBean.MainTeacherClazzBean.SubjectInfosBean> infosBeans = subjectList.get(i).getSubjectInfos();
                if (infosBeans != null && infosBeans.size() > 0) {
                    for (int j = 0; j < infosBeans.size(); j++) {
                        infosBeans.get(j).setAbool(false);
                        mClassAdapter.getSuccessData(subjectList, searchTag);

                    }
                }
            }
        } else {
            //清空班级集合
            for (int i = 0; i < classNameList.size(); i++) {
                List<InforBean.MainTeacherClazzBean.ClassInfosBean> classInfos = classNameList.get(i).getClassInfos();
                if (classInfos != null && classInfos.size() > 0) {
                    for (int j = 0; j < classInfos.size(); j++) {
                        classInfos.get(j).setAbool(false);
                        mClassAdapter.getSuccessData(classNameList, searchTag);

                    }
                }
            }
        }
    }

    /**
     * 科目或者班级筛选的确定点击事件
     */
    private void getClassOrSubject() {
        bindlist.clear();
        pn = 1;
        if (linerSelectClassname.getVisibility() == View.VISIBLE)
            linerSelectClassname.setVisibility(View.GONE);
        if (searchTag == 1) {//拿到选科目的Id（教师）
            StringBuilder subjectId = new StringBuilder();
            for (int i = 0; i < subjectList.size(); i++) {
                List<InforBean.MainTeacherClazzBean.SubjectInfosBean> infosBeans = subjectList.get(i).getSubjectInfos();
                if (infosBeans == null)
                    return;
                for (int j = 0; j < infosBeans.size(); j++) {
                    if (infosBeans.get(j).isAbool()) {
                        subjectId.append(infosBeans.get(j).getSubjectId() + ",");
                    }
                }
            }
            String subjectid = subjectId.toString();
            if (TextUtils.isEmpty(subjectid)) {
                presenter.getBindListData(mInforBean.getUserId(), searchTag, gradeId, classIds, keywords, pageTag, subjectIds, ps, pn);
            } else {
                String substring = subjectid.substring(0, subjectid.length() - 1);
                if (!TextUtils.isEmpty(substring)) {
                    presenter.getBindListData(mInforBean.getUserId(), searchTag, gradeId, classIds, keywords, pageTag, substring, ps, pn);
                }
            }
        } else {
            //清空班级集合（学生）
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < classNameList.size(); i++) {
                List<InforBean.MainTeacherClazzBean.ClassInfosBean> classInfos = classNameList.get(i).getClassInfos();
                if (classInfos == null)
                    return;
                for (int j = 0; j < classInfos.size(); j++) {
                    if (classInfos.get(j).isAbool()) {
                        stringBuilder.append(classInfos.get(j).getClassId() + ",");
                    }

                }
            }
            String classId = stringBuilder.toString();
            if (TextUtils.isEmpty(classId)) {
                presenter.getBindListData(mInforBean.getUserId(), searchTag, gradeId, classIds, keywords, pageTag, subjectIds, ps, pn);
            } else {
                String classIdsing = classId.substring(0, classId.length() - 1);
                if (!TextUtils.isEmpty(classIdsing)) {
                    presenter.getBindListData(mInforBean.getUserId(), searchTag, gradeId, classIdsing, keywords, pageTag, subjectIds, ps, pn);
                }
            }
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
     * 教师班级的数据源
     */
    private void getClassNameData() {
        linerSelectClassname.setVisibility(View.VISIBLE);
        if (mainTeacherClazzBeans.size() > 0) {
            classNameList.clear();
            for (int i = 0; i < mainTeacherClazzBeans.size(); i++) {
                if (mainTeacherClazzBeans.get(i).isAbool()) {
                    InforBean.MainTeacherClazzBean mainTeacherClazzBean = mainTeacherClazzBeans.get(i);
                    classNameList.add(mainTeacherClazzBean);
                }
            }
            if (mClassAdapter == null) {
                mClassAdapter = new ClassAdapter(classNameList, this, searchTag);
            }
            mClassAdapter.setClassAdapter(recyclerClass, mInforBean);
        }

    }

    /**
     * 全选 按钮
     */
    private void getisSelect() {
        //先拿到选中人员的数量
        //判断设备数量跟人员列表数量作对比
        if (mParcelableArrayList.size() >= bindlist.size()) {
            if (selectInfo) {
                textSelectNumber.setText("(" + bindlist.size() + "/" + mParcelableArrayList.size() + ")");//显示传过来的设备数量
                imgSelectAll.setImageResource(R.drawable.select_off);
                //如果设备数量大于人员数量，可以全选
                for (int i = 0; i < bindlist.size(); i++) {
                    if (!bindlist.get(i).isAbool()) {
                        bindlist.get(i).setAbool(true);
                    }
                }
                mBindListEqumentAdapter.getSuccessData(bindlist);
            } else {
                textSelectNumber.setText("(0/" + mParcelableArrayList.size() + ")");//显示传过来的设备数量
                imgSelectAll.setImageResource(R.drawable.select_no);
                //如果设备数量大于人员数量，可以全选
                for (int i = 0; i < bindlist.size(); i++) {
                    if (bindlist.get(i).isAbool())
                        bindlist.get(i).setAbool(false);
                }
                mBindListEqumentAdapter.getSuccessData(bindlist);
            }
            selectInfo = !selectInfo;
        } else {
            toastShort("当前人员数量超过设备数量,已为您自动选中");
            //人员大于设备数量，选取前几个人员，直到和设备数量匹配为止
            for (int i = 0; i < mParcelableArrayList.size(); i++) {
                bindlist.get(i).setAbool(true);
            }
            mBindListEqumentAdapter.getSuccessData(bindlist);
            int selectnumber = 0;
            for (int i = 0; i < bindlist.size(); i++) {
                if (bindlist.get(i).isAbool())
                    selectnumber++;
            }
            textSelectNumber.setText("(" + selectnumber + "/" + mParcelableArrayList.size() + ")");//显示传过来的设备数量
        }
    }


    /**
     * 加载数据
     *
     * @param传过来的值
     */
    @Override
    public void onEventMainThread(EventMessage event) {
        super.onEventMainThread(event);
        if (Contact.DISTRIBUTION.equals(event.getMsg())) {
            String msg2 = event.getMsg2();
            Log.d("TAG", msg2 + "-----------OK-");
            //拿到传过来的值去请求网络
            pn = 1;
            classIds = null;
            subjectIds = null;
            bindlist.clear();
            textSelectNumber.setText("(" + 0 + "/" + mParcelableArrayList.size() + ")");//显示传过来的设备数量
            presenter.getBindListData(mInforBean.getUserId(), searchTag, gradeId, classIds, msg2, pageTag, subjectIds, ps, pn);
        }
    }
}
