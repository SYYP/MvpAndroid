package www.app.ypy.com.mvpandroid.bean;

import java.util.List;

/**
 * Created by ypu
 * on 2019/5/29 0029
 */
public class InforBean {
    /**
     * teacherNumber : 123456
     * userId : 123456
     * account : 123456
     * userName : 袁野
     * userGenderValue : 男
     * userEmail : xx@163.com
     * userMobile : 18800000000
     * roleNameCode : UB08
     * schoolName : 人大附中
     * mainTeacherClazz : [{"classInfos":[{"classNameValue":"一班","classId":"0001"}],"grade":"三年级","gradeId":"001"}]
     * userToken : 0644RSfxUUMk5ImjU2
     * sectionInfos : [{"sectionNameValue":"初中","sectionNameCode":"SD02"}]
     */

    private String teacherNumber;
    private int userId;
    private String account;
    private String userName;
    private String userGenderValue;
    private String userEmail;
    private String userMobile;
    private String roleNameCode;
    private String schoolName;
    private String userToken;
    private List<MainTeacherClazzBean> mainTeacherClazz;
    private List<SectionInfosBean> sectionInfos;
    private List<TeachClass> mTeachClasses;
    private String deviceId;
    private String studentNumber;
    private  int schoolPkg ;

    public int getSchoolPkg() {
        return schoolPkg;
    }

    public void setSchoolPkg(int schoolPkg) {
        this.schoolPkg = schoolPkg;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public List<TeachClass> getTeachClasses() {
        return mTeachClasses;
    }

    public void setTeachClasses(List<TeachClass> teachClasses) {
        mTeachClasses = teachClasses;
    }

    public String getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserGenderValue() {
        return userGenderValue;
    }

    public void setUserGenderValue(String userGenderValue) {
        this.userGenderValue = userGenderValue;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getRoleNameCode() {
        return roleNameCode;
    }

    public void setRoleNameCode(String roleNameCode) {
        this.roleNameCode = roleNameCode;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public List<MainTeacherClazzBean> getMainTeacherClazz() {
        return mainTeacherClazz;
    }

    public void setMainTeacherClazz(List<MainTeacherClazzBean> mainTeacherClazz) {
        this.mainTeacherClazz = mainTeacherClazz;
    }

    public List<SectionInfosBean> getSectionInfos() {
        return sectionInfos;
    }

    public void setSectionInfos(List<SectionInfosBean> sectionInfos) {
        this.sectionInfos = sectionInfos;
    }

    public static class MainTeacherClazzBean {
        /**
         * classInfos : [{"classNameValue":"一班","classId":"0001"}]
         * grade : 三年级
         * gradeId : 001
         */

        private String gradeNameValue;
        private String gradeId;
        private List<ClassInfosBean> classInfos;
        private List<SubjectInfosBean> subjectInfos;

        private boolean abool;

        public String getGradeNameValue() {
            return gradeNameValue;
        }

        public void setGradeNameValue(String gradeNameValue) {
            this.gradeNameValue = gradeNameValue;
        }

        public boolean isAbool() {
            return abool;
        }

        public void setAbool(boolean abool) {
            this.abool = abool;
        }

        public String getGradeId() {
            return gradeId;
        }

        public void setGradeId(String gradeId) {
            this.gradeId = gradeId;
        }

        public List<ClassInfosBean> getClassInfos() {
            return classInfos;
        }

        public void setClassInfos(List<ClassInfosBean> classInfos) {
            this.classInfos = classInfos;
        }

        public List<SubjectInfosBean> getSubjectInfos() {
            return subjectInfos;
        }

        public void setSubjectInfos(List<SubjectInfosBean> subjectInfos) {
            this.subjectInfos = subjectInfos;
        }

        public static class ClassInfosBean {
            /**
             * classNameValue : 一班
             * classId : 0001
             */

            private String classNameValue;
            private String classId;
            private boolean abool;

            public boolean isAbool() {
                return abool;
            }

            public void setAbool(boolean abool) {
                this.abool = abool;
            }

            public String getClassNameValue() {
                return classNameValue;
            }

            public void setClassNameValue(String classNameValue) {
                this.classNameValue = classNameValue;
            }

            public String getClassId() {
                return classId;
            }

            public void setClassId(String classId) {
                this.classId = classId;
            }
        }


        public static class SubjectInfosBean {
            /**
             * subjectId : 20100
             * subjectNameValue : 语文
             */

            private int subjectId;
            private String subjectNameValue;
           private boolean abool;

            public boolean isAbool() {
                return abool;
            }

            public void setAbool(boolean abool) {
                this.abool = abool;
            }

            public int getSubjectId() {
                return subjectId;
            }

            public void setSubjectId(int subjectId) {
                this.subjectId = subjectId;
            }

            public String getSubjectNameValue() {
                return subjectNameValue;
            }

            public void setSubjectNameValue(String subjectNameValue) {
                this.subjectNameValue = subjectNameValue;
            }
        }
    }

    public static class SectionInfosBean {
        /**
         * sectionNameValue : 初中
         * sectionNameCode : SD02
         */

        private String sectionNameValue;
        private String sectionNameCode;
        private String sectionId;

        public String getSectionId() {
            return sectionId;
        }

        public void setSectionId(String sectionId) {
            this.sectionId = sectionId;
        }

        public String getSectionNameValue() {
            return sectionNameValue;
        }

        public void setSectionNameValue(String sectionNameValue) {
            this.sectionNameValue = sectionNameValue;
        }

        public String getSectionNameCode() {
            return sectionNameCode;
        }

        public void setSectionNameCode(String sectionNameCode) {
            this.sectionNameCode = sectionNameCode;
        }
    }
    public static class TeachClass{
           private String classId;
           private String classNameValue;

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public String getClassNameValue() {
            return classNameValue;
        }

        public void setClassNameValue(String classNameValue) {
            this.classNameValue = classNameValue;
        }
    }
}
