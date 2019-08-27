package www.app.ypy.com.mvpandroid.base;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import www.app.ypy.com.mvpandroid.R;
import www.app.ypy.com.mvpandroid.event.BaseEvent;

/**
 * Created by ypu
 * on 2019/8/23 0023
 */
public  abstract class BaseMvpActivity<T extends IBasePresenter> extends RxAppCompatActivity implements IBaseView<T> {
    private Unbinder mUnbind;
    /**
     * 处于前台的activity 使用的时候可能为null
     */
    private static BaseMvpActivity mForegroundActivity = null;
    static WeakReference<BaseMvpActivity> weakReference;
    /*** 记录所有活动的Activity */
    private static final List<BaseMvpActivity> mActivities = new LinkedList<>();
    /***封装toast对象**/
    private static Toast toast;
    protected T presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initActivityData();
        setContentView(getXmlView());
        mUnbind = ButterKnife.bind(this);
        processExtraData();
        loadData();
        setPresenter(presenter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        processExtraData();
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return bindToLifecycle();
    }

    /**
     * 获取XML View bind绑定View
     */
    protected abstract int getXmlView();

    @Override
    protected void onResume() {
        super.onResume();
        mForegroundActivity = this;
        //设置为软弱引用
        weakReference = new WeakReference<>(mForegroundActivity);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mForegroundActivity = null;
        weakReference.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbind.unbind();
        mActivities.remove(this);
        EventBus.getDefault().unregister(this);
    }

    /***初始化activity中的基本数据*/
    private void initActivityData() {
        mActivities.add(this);
        EventBus.getDefault().register(this);
    }

    /**
     * 接收ExtraData  无需关心启动模式.
     */
    public void processExtraData() {
    }

    /***
     * 加载数据 包含网络请求
     */
    protected abstract void loadData();
    /**
     * 获取当前处于前台的activity
     */
    public static BaseMvpActivity getForegroundActivity() {
        if (weakReference.get() != null) {
            return weakReference.get();
        } else {
            return getCurrentActivity();
        }
    }

    public static Context getContext() {
        return BaseMvpActivity.getForegroundActivity();

    }

    public static BaseMvpActivity getCurrentActivity() {
        List<BaseMvpActivity> copy;

        synchronized (mActivities) {
            copy = new ArrayList<>(mActivities);
        }
        if (copy.size() > 0) {
            return copy.get(copy.size() - 1);
        }
        return null;
    }

    public void startIntent(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    /**
     * 含有参数的传递
     *
     * @param cls
     * @param extras
     */
    public void startIntentWithExtras(Class<?> cls, Bundle extras) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(extras);
        startActivity(intent);
    }

    /**
     * 关闭所有Activity
     */
    public static void finishAll() {
        List<BaseMvpActivity> copy;
        synchronized (mActivities) {
            copy = new ArrayList<BaseMvpActivity>(mActivities);
        }
        for (BaseMvpActivity activity : copy) {
            activity.finish();
        }
        copy.clear();
    }

    /**
     * 显示长toast
     *
     * @param msg
     */
    public void toastLong(String msg) {
        if (null == toast) {
            toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setText(msg);
            toast.show();
        } else {
            toast.setText(msg);
            toast.show();
        }
    }
    public void onEventMainThread(BaseEvent event) {
        if (event == null) {
            return;
        }
    }
    /**
     * 显示短toast
     *
     * @param msg
     */
    public void toastShort(String msg) {
        if (null == toast) {
            toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
            //  toast.setDuration(Toast.LENGTH_SHORT);
            toast.setText(msg);
            toast.show();
        } else {
            toast.setText(msg);
            toast.show();
        }
    }
}


