package www.app.ypy.com.mvpandroid.dailymoudel;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import java.lang.ref.SoftReference;
import java.util.List;

import rx.functions.Action1;
import www.app.ypy.com.mvpandroid.bean.Bindlistbean;
import www.app.ypy.com.mvpandroid.bean.DeleteBean;
import www.app.ypy.com.mvpandroid.bean.DeleteEqument;
import www.app.ypy.com.mvpandroid.bean.InforBean;
import www.app.ypy.com.mvpandroid.network.ApiException;
import www.app.ypy.com.mvpandroid.network.NetWorkManager;
import www.app.ypy.com.mvpandroid.utils.NetUtils;

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
                            if (!mainTeacherClazzBeans.isEmpty()) {
                                if (mView != null && mView.get() != null) {
                                    mView.get().getSuccessClassOrSubject(mainTeacherClazzBeans);
                                }

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
}
