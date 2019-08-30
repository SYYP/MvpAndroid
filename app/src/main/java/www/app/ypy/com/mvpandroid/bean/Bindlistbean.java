package www.app.ypy.com.mvpandroid.bean;

/**
 * Created by ypu
 * on 2019/5/13 0013
 */
public class Bindlistbean {
    /**
     * userName : 姓名
     * studentNumber : 学号
     * teacherNumber : 教师工号
     * account : 账号
     * deviceId : 设备Id
     * classWholeName : 班级全称
     * deviceModelCode  设备类型
     *
     */

    private boolean abool;
    private String userName;
    private String studentNumber;
    private String teacherNumber;
    private String account;
    private String deviceId;
    private String classWholeName;
    private String deviceModelCode;
    private int userId;
    private String teachSubjects;
    private String studentOfClass;

    public String getStudentOfClass() {
        return studentOfClass;
    }

    public void setStudentOfClass(String studentOfClass) {
        this.studentOfClass = studentOfClass;
    }

    public String getTeachSubjects() {
        return teachSubjects;
    }

    public void setTeachSubjects(String teachSubjects) {
        this.teachSubjects = teachSubjects;
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

    public boolean isAbool() {
        return abool;
    }

    public void setAbool(boolean abool) {
        this.abool = abool;
    }


        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getStudentNumber() {
            return studentNumber;
        }

        public void setStudentNumber(String studentNumber) {
            this.studentNumber = studentNumber;
        }

        public String getTeacherNumber() {
            return teacherNumber;
        }

        public void setTeacherNumber(String teacherNumber) {
            this.teacherNumber = teacherNumber;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getClassWholeName() {
            return classWholeName;
        }

        public void setClassWholeName(String classWholeName) {
            this.classWholeName = classWholeName;
        }

}
