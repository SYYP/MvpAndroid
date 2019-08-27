package www.app.ypy.com.mvpandroid.base;

import com.trello.rxlifecycle.LifecycleTransformer;

/**
 * Created by ypu
 * on 2019/8/23 0023
 */
public interface IBaseView<T> {



    /**
     * 显示网络错误，modify 对网络异常在 BaseActivity 和 BaseFragment 统一处理
     */
    void showNetError();

    /**
     * 绑定生命周期
     * @param <T>
     * @return
     */
    <T> LifecycleTransformer<T> bindToLife();

    /**
     *  无网络时候
     */
     void noNetWork();

    /**
     * 设置 presenter
     */
    void setPresenter(T presenter);
}
