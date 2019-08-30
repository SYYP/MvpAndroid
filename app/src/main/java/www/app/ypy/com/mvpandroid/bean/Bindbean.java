package www.app.ypy.com.mvpandroid.bean;

/**
 * Created by ypu
 * on 2019/5/15 0015
 */
public class Bindbean {

    private int userId;//信息ID
    private String equmentId;//设备ID
    private String equmentType;//设备类型

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEqumentId() {
        return equmentId;
    }

    public void setEqumentId(String equmentId) {
        this.equmentId = equmentId;
    }

    public String getEqumentType() {
        return equmentType;
    }

    public void setEqumentType(String equmentType) {
        this.equmentType = equmentType;
    }
}
