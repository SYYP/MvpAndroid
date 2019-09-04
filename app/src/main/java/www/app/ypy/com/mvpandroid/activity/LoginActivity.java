package www.app.ypy.com.mvpandroid.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.alibaba.fastjson.JSON;

import java.util.List;

import butterknife.BindView;
import www.app.ypy.com.mvpandroid.R;
import www.app.ypy.com.mvpandroid.application.Myapplication;
import www.app.ypy.com.mvpandroid.base.BaseMvpActivity;
import www.app.ypy.com.mvpandroid.bean.InforBean;
import www.app.ypy.com.mvpandroid.Loginmoudel.ILoginInterface;
import www.app.ypy.com.mvpandroid.Loginmoudel.LoginPresenter;
import www.app.ypy.com.mvpandroid.utils.Contact;
import www.app.ypy.com.mvpandroid.utils.LogUtils;
import www.app.ypy.com.mvpandroid.utils.SpUtils;
import www.app.ypy.com.mvpandroid.utils.UIUtils;

/**
 * Created by ypu
 * on 2019/8/27 0027
 */
public class LoginActivity extends BaseMvpActivity<ILoginInterface.Presenter> implements ILoginInterface.View, View.OnClickListener {
    @BindView(R.id.img_logo)
    ImageView imgLogo;
    @BindView(R.id.edit_num)
    EditText editNum;
    @BindView(R.id.edit_pwd)
    EditText editPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    String mEdtinum;

    @Override
    protected int getXmlView() {
        return R.layout.activity_login;
    }

    @Override
    protected void loadData() {
        btnLogin.setOnClickListener(this);//添加点击事件
        editNum.setOnClickListener(this);
        editPwd.setOnClickListener(this);
        //如果存在保存的状态值，证明登录过。直接登录
        boolean aBoolean = SpUtils.getBoolean(Myapplication.getApplication(), Contact.LOGINSTATE, false);
        if (aBoolean) {
            startIntent(MainActivity.class
            );
            finish();
        }
    }

    @Override
    public void SuccessData(InforBean loginBean) {
        if (Contact.TEACHER.equals(loginBean.getRoleNameCode())) {
            List<InforBean.MainTeacherClazzBean> mainTeacherClazz = loginBean.getMainTeacherClazz();
            if (mainTeacherClazz != null && !mainTeacherClazz.isEmpty()) {
                //保存返回的信息
                startNewActivity(loginBean);
                LogUtils.e(loginBean.toString());
            } else {
                toastShort("当前班主任无授课班级");
            }
        } else {
            startNewActivity(loginBean);
        }
    }

    private void startNewActivity(InforBean loginBean) {
        SpUtils.putString(LoginActivity.this, Contact.USERINFO, JSON.toJSONString(loginBean));
        SpUtils.putBoolean(Myapplication.getApplication(), Contact.LOGINSTATE, true);
        Bundle bundle = new Bundle();//接口成功后传递数据
        startIntentWithExtras(MainActivity.class, bundle);
    }

    @Override
    public void showNetError() {

    }

    @Override
    public void noNetWork() {
        UIUtils.showToastSafe(R.string.string_nonet, 0);
    }

    @Override
    public void setPresenter(ILoginInterface.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new LoginPresenter(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_num:
                changeScrollView();
                break;
            case R.id.edit_pwd:
                changeScrollView();
                break;
            case R.id.btn_login:
                mEdtinum = editNum.getText().toString();
                String editpwd = editPwd.getText().toString();//密码
                if (TextUtils.isEmpty(mEdtinum)) {
                    toastShort("请输入账号");
                    return;
                } else if (TextUtils.isEmpty(editpwd)) {
                    toastShort("请输入密码");
                    return;
                }
                //网络请求
                presenter.getLoadData(mEdtinum, editpwd);
                break;
            default:
                break;
        }
    }


    private void changeScrollView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //将ScrollView滚动到底
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        }, 100);
    }
}
