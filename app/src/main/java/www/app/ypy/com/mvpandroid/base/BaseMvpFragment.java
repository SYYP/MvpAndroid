package www.app.ypy.com.mvpandroid.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.LifecycleTransformer;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import www.app.ypy.com.mvpandroid.bean.EventMessage;
import www.app.ypy.com.mvpandroid.event.BaseEvent;
import www.app.ypy.com.mvpandroid.fragment.RxFragment;

/**
 * Created by ypu
 * on 2019/8/26 0026
 */
public abstract class BaseMvpFragment<T extends IBasePresenter> extends RxFragment implements IBaseView<T> {
    protected T presenter;
    private View view;
    protected Context mContext;
    protected Activity mActivity;
    protected boolean isInit = false;
    protected boolean isLoad = false;
    private Unbinder mUnbind;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter(presenter);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(this.setContentView(), container, false);
        mUnbind = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        isInit = true;
        mContext = this.getContext();
        mActivity = this.getActivity();
        isCanLoadData();
        return view;
    }

    protected abstract int setContentView();

    private void isCanLoadData() {
        if (this.isInit) {
            if (this.getUserVisibleHint()) {
                this.lazyLoad();
                this.isLoad = true;
            } else if (this.isLoad) {
                this.stopLoad();
            }

        }
    }

    protected void startIntent(Context packageContext, Class<?> cls) {
        Intent intent = new Intent(packageContext, cls);
        startActivity(intent);
    }

    protected abstract void lazyLoad();

    protected void stopLoad() {
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isCanLoadData();
    }
    protected void startIntent(Class<?> cls) {
        Intent intent = new Intent(mContext, cls);
        startActivity(intent);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventMessage event) {
        try {

        }catch (Exception e){
            e.printStackTrace();
        }

        if (event == null) {
            return;
        }
    }
    public void startIntentWithExtras(Class<? extends Activity> cls, Bundle extras) {
        Intent intent = new Intent(mContext, cls);
        intent.putExtras(extras);
        startActivity(intent);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mUnbind.unbind();
    }

    @Override
    public <T1> LifecycleTransformer<T1> bindToLife() {
        return bindToLifecycle();
    }
}
