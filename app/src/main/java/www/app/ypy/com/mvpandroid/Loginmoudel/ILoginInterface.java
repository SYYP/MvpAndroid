package www.app.ypy.com.mvpandroid.Loginmoudel;

import www.app.ypy.com.mvpandroid.base.IBasePresenter;
import www.app.ypy.com.mvpandroid.base.IBaseView;

import www.app.ypy.com.mvpandroid.bean.InforBean;

/**
 * Created by ypu
 * on 2019/8/23 0023
 */
public interface ILoginInterface {


    interface View extends IBaseView<Presenter>{

        void SuccessData(InforBean loginBean);
    }

    interface Presenter extends IBasePresenter{
        void getLoadData(String name,String password);
    }
}
