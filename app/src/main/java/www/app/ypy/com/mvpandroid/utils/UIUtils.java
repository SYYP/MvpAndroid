package www.app.ypy.com.mvpandroid.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import www.app.ypy.com.mvpandroid.application.Myapplication;
import www.app.ypy.com.mvpandroid.base.BaseMvpActivity;

public class UIUtils {

//    private static Toast mToast;

    //--------------------------------SchoolBagsApplication---------------------------------------
    public static Context getContext() {
        return Myapplication.getInstance();
    }

    public static int getMainThreadId() {
        return Myapplication.getMainThreadId();
    }

    public static Thread getMainThread() {
        return Myapplication.getMainThread();
    }

    public static Handler getMainThreadHandler() {
        return Myapplication.getMainThreadHandler();
    }

    public static Looper getMainThreadLooper() {
        return Myapplication.getMainThreadLooper();
    }

    public static boolean isRunInMainThread() {
        return Thread.currentThread().getId() == getMainThreadId();
    }

    //-------------------------------Handler---------------------------------------------------
    public static boolean post(Runnable runnable) {
        boolean result = false;
        Handler handler = getMainThreadHandler();
        if (handler != null) {
            result = handler.post(runnable);
        }
        return result;
    }


    public static boolean postDelayed(Runnable runnable, long delay) {
        boolean result = false;
        Handler handler = getMainThreadHandler();
        if (handler != null) {
            result = handler.postDelayed(runnable, delay);
        }
        return result;
    }

    public static void removeCallbacks(Runnable runnable) {
        Handler handler = getMainThreadHandler();
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }

    /**
     * 在主线程执行
     */
    public static void runInMainThread(Runnable runnable) {
        if (isRunInMainThread()) {
            runnable.run();
        } else {
            post(runnable);
        }
    }

    //---------------------------资源-------------------------------------------
    public static Resources getResources() {
        if (getContext() != null) {
            return getContext().getResources();
        } else {
            return null;
        }
    }

    public static Drawable getDrawable(int resId) {
        if (getResources() != null) {
            return getResources().getDrawable(resId);
        } else {
            return null;
        }
    }

    public static Bitmap getBitmap(int resId) {
        if (getResources() != null) {
            return BitmapFactory.decodeResource(getResources(), resId);
        } else {
            return null;
        }
    }

    public static int getColor(int resId) {
        if (getResources() != null) {
            return getResources().getColor(resId);
        } else {
            return 0;
        }
    }

    public static String getString(int resId) {
        if (getResources() != null) {
            return getResources().getString(resId);
        } else {
            return null;
        }
    }

    /**
     * 获取文字，并按照后面的参数进行格式化
     */
    public static String getString(int resId, Object... formatAgrs) {
        if (getResources() != null) {
            return getResources().getString(resId, formatAgrs);
        } else {
            return null;
        }
    }

    public static View inflate(int resId) {
        Context context = UIUtils.getContext();
        if (context != null) {
            return LayoutInflater.from(context).inflate(resId, null);
        }
        return null;
    }

    public static int getScreenWidth() {
        BaseMvpActivity activity = BaseMvpActivity.getCurrentActivity();
        if (activity != null) {
            DisplayMetrics metric = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
            return metric.widthPixels;     // 屏幕宽度（像素）
        }
        return 0;
    }

    public static int getScreenHeight() {
        BaseMvpActivity activity = BaseMvpActivity.getCurrentActivity();
        if (activity != null) {
            DisplayMetrics metric = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
            return metric.heightPixels;     // 屏幕高度（像素）
        }
        return 0;
    }


    public static int getDimension(int id) {

        return getResources().getDimensionPixelOffset(id);
    }

    //---------------------------toast-------------------------------------------

    public static void showToastSafe(final int resId, final int duration) {

        if (Process.myTid() == getMainThreadId()) {
            // 调用在UI线程
            if (BaseMvpActivity.getContext() != null) {
                Toast.makeText(BaseMvpActivity.getContext(), resId, duration).show();
            }
        } else {
            // 调用在非UI线程
            post(new Runnable() {
                @Override
                public void run() {
                    if (BaseMvpActivity.getContext() != null) {
                        Toast.makeText(BaseMvpActivity.getContext(), resId, duration).show();
                    }
                }
            });
        }
    }

    public static void showToastSafe(final CharSequence text, final int duration) {

        if (Process.myTid() == getMainThreadId()) {
            Toast.makeText(getContext(), text, duration).show();
        } else {
            // 调用在非UI线程
            post(() -> {
                Toast.makeText(BaseMvpActivity.getContext(), text, duration).show();


            });
        }
    }
}
