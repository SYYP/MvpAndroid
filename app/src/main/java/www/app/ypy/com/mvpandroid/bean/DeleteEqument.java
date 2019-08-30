package www.app.ypy.com.mvpandroid.bean;

/**
 * Created by ypu
 * on 2019/6/1 0001
 */
public class DeleteEqument {

    /**
     * deviceId : sdfg0001
     * userId : 1000000001
     * deviceModelCode : edu
     */

    private String deviceId;
    private int userId;
    private String deviceModelCode;
    private int bindTag;

    public int getBindTag() {
        return bindTag;
    }

    public void setBindTag(int bindTag) {
        this.bindTag = bindTag;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDeviceModelCode() {
        return deviceModelCode;
    }

    public void setDeviceModelCode(String deviceModelCode) {
        this.deviceModelCode = deviceModelCode;
    }
}
