package www.app.ypy.com.mvpandroid.Loginmoudel;

import java.lang.ref.SoftReference;

import rx.functions.Action1;
import www.app.ypy.com.mvpandroid.application.Myapplication;
import www.app.ypy.com.mvpandroid.bean.InforBean;
import www.app.ypy.com.mvpandroid.bean.LoginBean;
import www.app.ypy.com.mvpandroid.network.ApiException;
import www.app.ypy.com.mvpandroid.network.NetWorkManager;
import www.app.ypy.com.mvpandroid.utils.Contact;
import www.app.ypy.com.mvpandroid.utils.NetUtils;
import www.app.ypy.com.mvpandroid.utils.SpUtils;
import www.app.ypy.com.mvpandroid.utils.UIUtils;

/**
 * Created by ypu
 * on 2019/8/23 0023
 */
public class LoginPresenter implements ILoginInterface.Presenter {
    SoftReference<ILoginInterface.View> mView;
    public LoginPresenter(ILoginInterface.View view) {
        this.mView = new SoftReference<ILoginInterface.View>(view);
    }
    @Override
    public void getLoadData(String name, String password) {
        if (NetUtils.isNetworkAvailable()) {
            NetWorkManager.getInstance().login(name, password)
                    .compose(mView.get().bindToLife())
                    .subscribe(new Action1<LoginBean>() {
                        @Override
                        public void call(LoginBean loginBean) {
                            String roleNameCode = loginBean.getUserRoleNameCode();
                            if (Contact.TEACHER.equals(roleNameCode) || Contact.MANAGER.equals(roleNameCode)) { //判断账号类型，只有校园管理跟教师可以登陆
                                //当是班主任时候返回带年级的信息，//校园管理，返回不带班级的信息
                                getInformation(name, Contact.TWO);
                                /**
                                 *  登录成功保存一个状态值，用来下次直接登录
                                 *  登录成功保存Token信息
                                 */
                                SpUtils.putString(Myapplication
                                        .getApplication(), Contact.TOKEN, loginBean.getUserToken());
                            } else {
                                UIUtils.showToastSafe("请用班主任或校园管理员登录", 0);
                                return;
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            throwable.printStackTrace();
                            if (throwable instanceof ApiException) {
                                if (((ApiException) throwable).getCode().equals("100101")) {//错误码
                                    UIUtils.showToastSafe("登录失败：用户不存在", 0);
                                    return;
                                } else if (((ApiException) throwable).getCode().equals("100102")) {//错误码
                                    UIUtils.showToastSafe("登录失败：用户密码错误", 0);
                                    return;
                                } else {
                                    UIUtils.showToastSafe(throwable.getMessage(), 0);
                                }
                            }
                        }
                    });
        } else {
            if (mView != null && mView.get() != null) {
                mView.get().noNetWork();
            }
        }
    }

    //得到个人信息
    private void getInformation(String mEdtinum, int mainView) {
        NetWorkManager.getInstance(false).getInfor(mEdtinum, mainView)
                .compose(mView.get().bindToLife())
                .subscribe(new Action1<InforBean>() {
                    @Override
                    public void call(InforBean inforBean) {
                        if (mView != null && mView.get() != null) {
                            mView.get().SuccessData(inforBean);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        UIUtils.showToastSafe("登录失败" + throwable.getMessage(), 0);
                    }
                });
    }

    @Override
    public void doErrorData() {

    }

    @Override
    public void destort() {
        mView.clear();
        mView = null;
    }

}
