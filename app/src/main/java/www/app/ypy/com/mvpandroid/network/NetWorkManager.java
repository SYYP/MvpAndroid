package www.app.ypy.com.mvpandroid.network;


import android.util.Log;
import android.view.WindowManager;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import www.app.ypy.com.mvpandroid.BuildConfig;
import www.app.ypy.com.mvpandroid.application.Myapplication;
import www.app.ypy.com.mvpandroid.bean.Bindlistbean;
import www.app.ypy.com.mvpandroid.bean.DeleteBean;
import www.app.ypy.com.mvpandroid.bean.DeleteEqument;
import www.app.ypy.com.mvpandroid.bean.InforBean;
import www.app.ypy.com.mvpandroid.bean.LoginBean;
import www.app.ypy.com.mvpandroid.dialog.LoadingProgressDialog;
import www.app.ypy.com.mvpandroid.utils.Contact;
import www.app.ypy.com.mvpandroid.utils.SpUtils;
import www.app.ypy.com.mvpandroid.utils.SystemUtils;

/**
 */
public final class NetWorkManager {

    private static final int HTTP_CONNECTION_TIMEOUT = 30 * 1000;

    private ServerApi mServerApi;

    public ServerApi getServerApi() {
        return mServerApi;
    }

    MyHttpLoggingInterceptor interceptor;

    public void openBODY() {
        if (interceptor != null) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
    }

    private NetWorkManager() {
        // 配置日志输出，因为Retrofit2不支持输出日志，只能用OkHttp来输出
        interceptor = new MyHttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        Interceptor headerInterceptor = new Interceptor() {

            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                String token= SpUtils.getString(Myapplication.getApplication(), Contact.TOKEN, "");
                Request orignaRequest = chain.request();
                Request.Builder newBuilder = orignaRequest.newBuilder();
                newBuilder.header("Content-Type", "application/json");
                newBuilder.header("Accept", "application/json");
                newBuilder.addHeader("X-User-Token", token);
                Log.d("TAG","taoken------"+token);
                newBuilder.method(orignaRequest.method(), orignaRequest.body());
                newBuilder.addHeader("X-Device-Model", SystemUtils.getDeviceModel());
//                if (NetConfig.isRelase) {
                newBuilder.addHeader("X-Auth-Options", "1e7904f32c4fcfd59b8a524d1bad1d8a.qg0J9zG*FIkBk^vo");
//                }
                Request request = newBuilder.build();
                return chain.proceed(request);
            }
        };

        builder.readTimeout(HTTP_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(HTTP_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(HTTP_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor).addInterceptor(headerInterceptor)
                .retryOnConnectionFailure(true);

        OkHttpClient mClient = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(mClient)
                .baseUrl(NetConfig.SERVER_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mServerApi = retrofit.create(ServerApi.class);
    }

    private static NetWorkManager mInstance; // 单例

    private static LoadingProgressDialog loadingProgressDialog;

    /**
     * 获取游戏API请求实例
     *
     * @return 单例对象
     */
    public static NetWorkManager getInstance() {
        return getInstance(true);
    }

    public static NetWorkManager getInstance(boolean flag) {
        if (mInstance == null) {
            synchronized (NetWorkManager.class) {
                if (mInstance == null) {
                    mInstance = new NetWorkManager();
                }
            }
        }
        synchronized (NetWorkManager.class) {
            if (flag) {
                if (loadingProgressDialog == null) {
                    loadingProgressDialog = new LoadingProgressDialog(Myapplication.getInstance().getApplicationContext());
                    loadingProgressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                    loadingProgressDialog.show();
                } else {
                    if (!loadingProgressDialog.isShowing()) {
                        loadingProgressDialog.show();
                    }
                }
            }
        }
            return mInstance;
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }

    /**
     * 登录接口
     *
     * @param userName
     * @param userPassword
     * @return
     */
    public Observable<LoginBean> login(String userName, String userPassword) {

        return getServerApi().getLogin(userName, userPassword)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult(loadingProgressDialog));
    }

    /**
     * 查询个人信息
     *
     * @param userName
     * @param userPassword
     * @return
     */
    public Observable<InforBean> getInfor(String userName, int userPassword) {

        return getServerApi().getInformation(userName, userPassword)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult(loadingProgressDialog));
    }
    /**
     * 年级接口
     *
     * @param userId
     * @param sectionNameCode
     * @return
     */
    public Observable<List<InforBean.MainTeacherClazzBean>> getGradeName(int userId, String sectionNameCode) {

        return getServerApi().getGradeName(userId, sectionNameCode)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult(loadingProgressDialog));

    }

    /**
     * b班级或者接口
     *
     * @param userId
     * @param gradeIds
     * @return
     */
    public Observable<List<InforBean.MainTeacherClazzBean>> getClassName(int userId, String gradeIds, int type) {
        return getServerApi().getClassName(userId, type,gradeIds)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult(loadingProgressDialog));

    }

    /**
     * @param userId
     * @param searchTag
     * @param classIds
     * @param keywords
     * @param
     * @param subjectIds
     * @param ps
     * @param pn
     * @return
     */
    public Observable<List<Bindlistbean>> getBindList(int userId, int searchTag, String gradeId, String classIds, String keywords, int bindingTag, String subjectIds, int ps, int pn) {
        return getServerApi().getBindlist(userId, searchTag, gradeId,classIds, keywords, bindingTag, subjectIds, ps, pn)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult(loadingProgressDialog));

    }

    /**
     *   解绑设备
     * @param deleteEqumentList
     * @return
     */
    public Observable<List<DeleteEqument>> getDeleteEqument(List<DeleteBean> deleteEqumentList) {
        return getServerApi().getDeleteEqument(deleteEqumentList)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult(loadingProgressDialog));
    }

    /**
     *     绑定接口
     * @param
     * @return
     */
    public Observable<List<DeleteEqument>> getBindEqument(List<DeleteBean> deleteEqumentList) {
        return getServerApi().getBindEqument(deleteEqumentList)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult(loadingProgressDialog));
    }
}
