package www.app.ypy.com.mvpandroid.utils;

/**
 * Created by ypu
 * on 2019/5/14 0014
 */
public class Contact {
    public static String FILENAME = "config.xml";
    public static final String LOGINSTATE = "loginstate";
    public static final String HISTORTDATA = "historydata";//搜索页面历史数据
    public static final String HISTORTREQURESCODE = "1001";//历史页面请求码
    public static final String SCANNINGDATA = "scandata";
    public static final String NOSELECTDATA = "noselectdata";
    public static final String SELECTDATA = "selectdata";
    public static final String DISTRIBUTION = "distribution";//分配角色也页面标识
    public static final String SCANRESULTACTIVITY = "scanResultactiivty";//扫码页面。设备的数据源
    public static final int SCANRESULT = 10011;//设备列表页跳转到人员分配页面请求码；
    public static final int DISTRIBUTON = 10012;//人员分配页面跳转设备列表页到请求码；
    public static final String USERINFO = "userinfo";//成功之后保存个人信息
    public static final String TOKEN = "userToken";//保存token
    public static final String SCAN = "scan";
    public static final String REFRECH = "refrech";
    public static final String STUDENT = "学生";
    public static final String TEACHER_NAME = "教师";
    /**
     * UB	UB08	学校管理员
     * <p>
     * UB09	教师
     * <p>
     * UB10	学生
     */
    public static final String ROLENAMECODE = "UB10";
    public static final String TEACHER = "UB09";
    public static final String MANAGER = "UB08";
    /**
     * 1绑定成功，2绑定失败（学生被绑定），3绑定失败（设备被绑定）
     */
    public static final int BIND_SUCCESS = 1;
    public static final int BIND_FAIL = 2;
    public static final int BIND_FAIL_EQUMENT = 3;
    /**
     * 1.直返班级信息
     * 2.以年级为单位返回班级信息
     */
    public static final int ONE = 1;
    public static final int TWO = 2;
}
