package www.app.ypy.com.mvpandroid.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import www.app.ypy.com.mvpandroid.application.Myapplication;

/**
 * Created by ypu
 * on 2019/7/24 0024
 */
public class NetUtils {

    /**
     * 判断网络是否连接
     *
     * @return
     */
    public static boolean isWifiConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) Myapplication.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.getType() == 1) {
            return networkInfo.isAvailable();
        }
        return false;
    }

    /**
     * 判断MOBILE网络是否可用
     * @param
     * @return
     */
    public static boolean isMobileConnected() {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) Myapplication.getApplication()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }

        return false;
    }
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivity = (ConnectivityManager) Myapplication.getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }
}
