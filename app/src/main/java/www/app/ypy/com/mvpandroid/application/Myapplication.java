package www.app.ypy.com.mvpandroid.application;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import www.app.ypy.com.mvpandroid.utils.Contact;

/**
 * Created by ypu
 * on 2019/5/8 0008
 */
public class Myapplication extends Application {

    private volatile static Myapplication instance;
    private static Handler mMainThreadHandler = null;
    private static int mMainThreadId = -1;
    private static Thread mMainThread;
    private static Looper mMainLooper;

    // 单例模式中获取唯一的MyApplication实例
    public static Myapplication getInstance() {
        return instance;
    }

    private void initThreadData() {
        mMainThreadHandler = new Handler();
        mMainThreadId = android.os.Process.myTid();
        mMainThread = Thread.currentThread();
        mMainLooper = getMainLooper();
    }

    public static boolean inMainProcess(Context context) {
        String packageName = context.getPackageName();
        String processName = getProcessName(context);
        return packageName.equals(processName);
    }

    private static String getProcessName(Context context) {
        String processName = null;
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        while (true) {
            for (ActivityManager.RunningAppProcessInfo info : am.getRunningAppProcesses()) {
                if (info.pid == android.os.Process.myPid()) {
                    processName = info.processName;
                    break;
                }
            }
            if (!TextUtils.isEmpty(processName)) {
                return processName;
            }
        }
    }

    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }


    public static int getMainThreadId() {
        return mMainThreadId;
    }


    public static Thread getMainThread() {
        return mMainThread;
    }

    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Context context = getApplicationContext();
// 获取当前包名
        String packageName = context.getPackageName();
// 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
// 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
// 初始化Bugly
        Bugly.init(getApplicationContext(), Contact.BUGLY_ID, false);
        if (inMainProcess(this)) {
            initThreadData();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

    /**
     * @return 返回值
     */

    public static Myapplication getApplication() {
        if (instance == null) {
            instance = getApplication();
        }
        return instance;
    }

    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
