package www.app.ypy.com.mvpandroid.dailymoudel;

        import android.content.Intent;
        import android.text.TextUtils;
        import android.util.Log;
        import android.view.View;

        import org.greenrobot.eventbus.EventBus;

        import java.lang.ref.SoftReference;
        import java.util.ArrayList;
        import java.util.List;

        import rx.functions.Action1;
        import www.app.ypy.com.mvpandroid.R;
        import www.app.ypy.com.mvpandroid.activity.MainActivity;
        import www.app.ypy.com.mvpandroid.application.Myapplication;
        import www.app.ypy.com.mvpandroid.bean.Bindlistbean;
        import www.app.ypy.com.mvpandroid.bean.DailogBean;
        import www.app.ypy.com.mvpandroid.bean.DeleteBean;
        import www.app.ypy.com.mvpandroid.bean.DeleteEqument;
        import www.app.ypy.com.mvpandroid.bean.EventMessage;
        import www.app.ypy.com.mvpandroid.bean.InforBean;
        import www.app.ypy.com.mvpandroid.dialog.BaseDialog;
        import www.app.ypy.com.mvpandroid.dialog.CurrencyDailogs;
        import www.app.ypy.com.mvpandroid.network.ApiException;
        import www.app.ypy.com.mvpandroid.network.NetWorkManager;
        import www.app.ypy.com.mvpandroid.utils.Contact;
        import www.app.ypy.com.mvpandroid.utils.HLog;
        import www.app.ypy.com.mvpandroid.utils.NetUtils;
        import www.app.ypy.com.mvpandroid.utils.SpUtils;

/**
 * Created by ypu
 * on 2019/8/28 0028
 */
public class DailyPresenter implements DailyInterface.Presenter {

    SoftReference<DailyInterface.View> mView;

    public DailyPresenter(DailyInterface.View view) {
        this.mView = new SoftReference<DailyInterface.View>(view);
    }

    @Override
    public void doErrorData() {

    }

    @Override
    public void destort() {
        mView.clear();
        mView = null;
    }

    @Override
    public void getBindListData(int userId, int serachTag, String gradeId, String classIds, String keywords, int pageTag, String subjectIds, int ps, int pn) {
        if (NetUtils.isNetworkAvailable()) {
            NetWorkManager.getInstance()
                    .getBindList(userId, serachTag, gradeId, classIds, keywords, pageTag, subjectIds, ps, pn)
                    .compose(mView.get().bindToLife())
                    .subscribe(new Action1<List<Bindlistbean>>() {
                        @Override
                        public void call(List<Bindlistbean> bindlistbeans) {
                            Log.d("TAG", "-----走这边啊----");
                            if (mView != null && mView.get() != null) {
                                mView.get().SuccessData(bindlistbeans);
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Log.d("TAG", "-----路过----" + ((ApiException) throwable).getCode());
                            if (throwable instanceof ApiException) {
                                if (((ApiException) throwable).getCode().equals("401")) {//错误码
                                    return;
                                } else if (("100202").equals(((ApiException) throwable).getCode())) {//错误码
                                    if (mView != null && mView.get() != null) {
                                        mView.get().BindListError();
                                    }
                                    return;
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

    /**
     * 解绑设备
     *
     * @param list
     */
    @Override
    public void DeleteEqument(List<DeleteBean> list) {
        if (NetUtils.isNetworkAvailable()) {
            NetWorkManager.getInstance().getDeleteEqument(list)
                    .compose(mView.get().bindToLife())
                    .subscribe(new Action1<List<DeleteEqument>>() {
                        @Override
                        public void call(List<DeleteEqument> deleteEqumentList) {
                            if (mView != null && mView.get() != null) {
                                mView.get().DeleteSuccessEqument(deleteEqumentList);
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {

                        }
                    });
        } else {
            if (mView != null && mView.get() != null) {
                mView.get().noNetWork();
            }
        }
    }

    /**
     * 获取年级请求数据
     *
     * @param userId
     * @param sectionId
     */
    @Override
    public void getClassData(int userId, String sectionId) {
        if (NetUtils.isNetworkAvailable()) {
            NetWorkManager.getInstance(false).getGradeName(userId, sectionId)
                    .compose(mView.get().bindToLife())
                    .subscribe(new Action1<List<InforBean.MainTeacherClazzBean>>() {
                        @Override
                        public void call(List<InforBean.MainTeacherClazzBean> mainTeacherClazzBean) {
                            if (mView != null && mView.get() != null) {
                                mView.get().getSuccessClassData(mainTeacherClazzBean);
                            }
                        }

                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            throwable.printStackTrace();
                            if (throwable instanceof ApiException) {
                                if (((ApiException) throwable).getCode().equals("100202")) {//错误码
                                    if (mView != null && mView.get() != null) {
                                        mView.get().BindListError();
                                    }
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

    /**
     * 获取班级或者科目的数据源
     *
     * @param userId
     * @param name
     * @param searchTag
     */
    @Override
    public void getClassNameOrSubject(int userId, String name, int searchTag) {
        if (NetUtils.isNetworkAvailable()) {
            NetWorkManager.getInstance().getClassName(userId, name, searchTag)
                    .compose(mView.get().bindToLife())
                    .subscribe(new Action1<List<InforBean.MainTeacherClazzBean>>() {
                        @Override
                        public void call(List<InforBean.MainTeacherClazzBean> mainTeacherClazzBeans) {
                            if (mView != null && mView.get() != null) {
                                mView.get().getSuccessClassOrSubject(mainTeacherClazzBeans);
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            if (throwable instanceof ApiException) {
                                if (((ApiException) throwable).getCode().equals("100202")) {//错误码
                                    if (mView != null && mView.get() != null) {
                                        mView.get().BindListError();
                                    }
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

    @Override
    public void getBindEqumentData(List<DeleteEqument> list) {
        List<DeleteBean> list1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            DeleteBean deleteBean = new DeleteBean();
            deleteBean.setDeviceId(list.get(i).getDeviceId());
            deleteBean.setDeviceModelCode(list.get(i).getDeviceModelCode());
            deleteBean.setUserId(list.get(i).getUserId());
            list1.add(deleteBean);
        }
        if (NetUtils.isNetworkAvailable()) {
            NetWorkManager.getInstance().getBindEqument(list1)
                    .compose(mView.get().bindToLife())
                    .subscribe(new Action1<List<DeleteEqument>>() {
                        @Override
                        public void call(List<DeleteEqument> deleteEqumentList) {
                            int successNumber = 0;
                            int failNumber = 0;
                            for (int i = 0; i < deleteEqumentList.size(); i++) {
                                if (deleteEqumentList.get(i).getBindTag() == Contact.BIND_FAIL || deleteEqumentList.get(i).getBindTag() == Contact.BIND_FAIL_EQUMENT) {
                                    //绑定失败学生被绑定
                                    failNumber++;
                                } else {
                                    //成功
                                    successNumber++;
                                }
                            }
                            if (mView != null && mView.get() != null) {
                                mView.get().getSuccessData(failNumber, successNumber);
                            }

                            /** 1：
                             * 如果绑定成功，检查是否设备列表里面还有未选中的，
                             * 如果有给出提示，是否继续绑定，绑定跳到绑定页面，不绑定，清空未绑定的数据跳到设备管理
                             * 2：绑定失败，弹出提示，成功多少给。失败多少个。是否继续绑定，确定跳到绑定页面，再加上未绑定的，取消跳到
                             *    设备管理
                             *
                             */

                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            if (throwable instanceof ApiException) {
                                if (((ApiException) throwable).getCode().equals("100202")) {
                                    if (mView != null && mView.get() != null) {
                                        mView.get().BindListError();
                                    }
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

}
