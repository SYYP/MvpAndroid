package www.app.ypy.com.mvpandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Log助手
 */
public class HLog
{
    private static Toast mToast;
    private static final boolean DEBUG = true;//测试打印

    public static void showToast(Context context, int resId, int duration)
    {
        if (mToast == null)
        {
            mToast = Toast.makeText(context, context.getString(resId), duration);
        }
        else
        {
            mToast.setText(context.getString(resId));
        }
        mToast.show();
    }

    public static void showToast(Context context, String msg, int duration)
    {
        if (mToast == null)
        {
            mToast = Toast.makeText(context, msg, duration);
        }
        else
        {
            mToast.setText(msg);
        }
        mToast.show();
    }

    public static void i(String TAG, String methodName, String msg)
    {
        if (DEBUG)
        {
            Log.i(TAG + "-->", methodName + "-->" + msg);
        }
    }

    public static void d(String TAG, String methodName, String msg)
    {
        if (DEBUG)
        {
            Log.d(TAG + "-->", methodName + "-->" + msg);
        }
    }

    public static void w(String TAG, String methodName, String msg)
    {
        if (DEBUG)
        {
            Log.w(TAG + "-->", methodName + "-->" + msg);
        }
    }

    public static void e(String TAG, String methodName, String msg)
    {
        if (DEBUG)
        {
            Log.e(TAG + "-->", methodName + "-->" + msg);
        }
    }

    public static void v(String TAG, String methodName, String msg)
    {
        if (DEBUG)
        {
            Log.v(TAG + "-->", methodName + "-->" + msg);
        }
    }

    public static void toast(Context context, String text)
    {
        if (mToast == null)
        {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }
        else
        {
            mToast.setText(text);
        }
        mToast.show();
    }
    public static void toast(Context context, int resId)
    {
        if (mToast == null)
        {
            mToast = Toast.makeText(context, context.getString(resId), Toast.LENGTH_LONG);
        }
        else
        {
            mToast.setText(context.getString(resId));
        }
        mToast.show();
    }
    public static void toastOnUI(final Activity activity, final  int resId)
    {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast == null)
                {
                    mToast = Toast.makeText(activity, activity.getString(resId), Toast.LENGTH_LONG);
                }
                else
                {
                    mToast.setText(activity.getString(resId));
                }
                mToast.show();
            }
        });

    }
    /**
     *toast取消
     */
    public static void cancel()
    {
        if (mToast != null)
        {
            mToast.cancel();
            mToast = null;
        }
    }
}