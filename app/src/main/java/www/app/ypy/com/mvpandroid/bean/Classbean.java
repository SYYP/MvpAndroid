package www.app.ypy.com.mvpandroid.bean;

import java.util.List;

/**
 * Created by ypu
 * on 2019/5/13 0013
 */
public class Classbean {

      private String title;

      private List<GradeBean> mBeanList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<GradeBean> getBeanList() {
        return mBeanList;
    }

    public void setBeanList(List<GradeBean> beanList) {
        mBeanList = beanList;
    }
}
