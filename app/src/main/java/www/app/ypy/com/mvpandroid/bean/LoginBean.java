package www.app.ypy.com.mvpandroid.bean;

/**
 * Created by ypu
 * on 2019/6/6 0006
 */
public class LoginBean {

    /**
     * userId : 10000414
     * userName : 李巧
     * userGenderCode : UA02
     * userStatusCode : UC01
     * userRoleNameCode : UB09
     * userToken : kyRV2nYpUyrRy56G
     */

    private int userId;
    private String userName;
    private String userGenderCode;
    private String userStatusCode;
    private String userRoleNameCode;
    private String userToken;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserGenderCode() {
        return userGenderCode;
    }

    public void setUserGenderCode(String userGenderCode) {
        this.userGenderCode = userGenderCode;
    }

    public String getUserStatusCode() {
        return userStatusCode;
    }

    public void setUserStatusCode(String userStatusCode) {
        this.userStatusCode = userStatusCode;
    }

    public String getUserRoleNameCode() {
        return userRoleNameCode;
    }

    public void setUserRoleNameCode(String userRoleNameCode) {
        this.userRoleNameCode = userRoleNameCode;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
