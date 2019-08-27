package www.app.ypy.com.mvpandroid.event;

/**
 * 功能：EventBus能够简化各组件间的通信，让我们的代码书写变得简单，
 * 能有效的分离事件发送方和接收方(也就是解耦的意思)，
 * 能避免复杂和容易出错的依赖性和生命周期问题。
 * <p>
 * 作者: YUAN_YE
 * 日期: 2019/4/23
 * 时间: 9:37
 */
public class BaseEvent {

    private String type;

    private Object ExtraData;


    private String gradeId;
    private String subjectId;

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public BaseEvent() {
    }

    public BaseEvent(String type) {
        this.type = type;
    }

    public BaseEvent(String type, Object extraData) {
        this.type = type;
        ExtraData = extraData;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setExtraData(Object extraData) {
        ExtraData = extraData;
    }

    public String getType() {
        return type;
    }


    public Object getExtraData() {
        return ExtraData;
    }
}
