package www.app.ypy.com.mvpandroid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.app.ypy.com.mvpandroid.R;
import www.app.ypy.com.mvpandroid.activity.LoginActivity;
import www.app.ypy.com.mvpandroid.application.Myapplication;
import www.app.ypy.com.mvpandroid.base.BaseMvpFragment;
import www.app.ypy.com.mvpandroid.bean.InforBean;
import www.app.ypy.com.mvpandroid.utils.Contact;
import www.app.ypy.com.mvpandroid.utils.SpUtils;

/**
 * Created by ypu
 * on 2019/8/30 0030
 */
public class MyFragment extends BaseMvpFragment {
    private static String USR_ID = "userid";
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.text_right)
    TextView textRight;
    @BindView(R.id.img_serachs)
    ImageView imgSerachs;
    @BindView(R.id.text_class)
    TextView textClass;
    @BindView(R.id.text_school_type)
    TextView textSchoolType;
    @BindView(R.id.text_account_number)
    TextView textAccountNumber;
    @BindView(R.id.text_administration_class)
    TextView textAdministrationClass;
    @BindView(R.id.linger_manger)
    LinearLayout lingerManger;
    @BindView(R.id.img_my)
    ImageView imgMy;
    @BindView(R.id.string_name)
    TextView stringName;
    @BindView(R.id.liner_name)
    LinearLayout linerName;
    @BindView(R.id.string_administration)
    TextView stringAdministration;
    @BindView(R.id.liner_pwd)
    LinearLayout linerPwd;
    @BindView(R.id.liner_end)
    LinearLayout linerEnd;

    //传递数据
    public static Fragment newInstance(String userId) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString(USR_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_my;
    }

    @Override
    protected void lazyLoad() {
        //取出登录时候保存的信息
        String string = SpUtils.getString(Myapplication.getApplication(), Contact.USERINFO, "");
        if (!TextUtils.isEmpty(string)) {
            InforBean inforBean = JSON.parseObject(string, InforBean.class);

            //添加数据
            textClass.setText(inforBean.getSchoolName());//学校名称
            String roleNameCode = inforBean.getRoleNameCode();
            //判断账号类型是否是教师
            if (roleNameCode.equals(Contact.TEACHER)) {
                textAccountNumber.setText("教师");//账号类型
                List<InforBean.MainTeacherClazzBean> mainTeacherClazz = inforBean.getMainTeacherClazz();
                StringBuilder builder=new StringBuilder();
                if(mainTeacherClazz!=null&&!mainTeacherClazz.isEmpty()) {
                    for (int i = 0; i < mainTeacherClazz.size(); i++) {
                        List<InforBean.MainTeacherClazzBean.ClassInfosBean> classInfos = mainTeacherClazz.get(i).getClassInfos();
                        for (int j = 0; j < classInfos.size(); j++) {
                            String classNameValue = classInfos.get(j).getClassNameValue();
                            builder.append(classNameValue + ",");

                        }
                    }
                    String s = builder.toString();
                    if (!TextUtils.isEmpty(s)) {
                        String substring = s.substring(0, s.length() - 1);
                        textAdministrationClass.setText(substring);//管理班级
                    }
                }
            } else {
                textAccountNumber.setText("校园管理员");
                lingerManger.setVisibility(View.GONE);

            }
            stringName.setText(inforBean.getUserName());//姓名
            stringAdministration.setText(TextUtils.isEmpty(inforBean.getAccount()) ? "" : inforBean.getAccount());//账号

        }

        //添加数据

        //退出登录
        linerEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //清除Sp里面保存的值
                SpUtils.putBoolean(Myapplication.getApplication(), Contact.LOGINSTATE, false);
                SpUtils.putString(Myapplication.getApplication(), Contact.USERINFO, "");
                SpUtils.putString(Myapplication.getApplication(),Contact.NOSELECTDATA,"");
                SpUtils.putString(Myapplication.getApplication(), Contact.DISTRIBUTION, "");
                SpUtils.putString(Myapplication.getApplication(), Contact.HISTORTDATA, "");
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }



    @Override
    public void showNetError() {

    }

    @Override
    public void noNetWork() {

    }

    @Override
    public void setPresenter(Object presenter) {

    }

}
