package www.app.ypy.com.mvpandroid.dailymoudel;

import java.util.List;

import www.app.ypy.com.mvpandroid.base.IBasePresenter;
import www.app.ypy.com.mvpandroid.base.IBaseView;
import www.app.ypy.com.mvpandroid.bean.Bindlistbean;
import www.app.ypy.com.mvpandroid.bean.DeleteBean;
import www.app.ypy.com.mvpandroid.bean.DeleteEqument;
import www.app.ypy.com.mvpandroid.bean.InforBean;

/**
 * Created by ypu
 * on 2019/8/28 0028
 */
public interface DailyInterface {

    interface View extends IBaseView<Presenter> {
        void SuccessData(List<Bindlistbean> bindlistbeans);

        void BindListError();

        /**
         *  解绑成功
         */
         void DeleteSuccessEqument(List<DeleteEqument> deleteEqumentList);

        /**
         *  获取年级数据 成功之后
         * @param mainTeacherClazzBean
         */
         void getSuccessClassData(List<InforBean.MainTeacherClazzBean> mainTeacherClazzBean);

        /**
         *   获取年级数据或者科目 成功之后
         * @param mainTeacherClazzBean
         */
         void getSuccessClassOrSubject(List<InforBean.MainTeacherClazzBean> mainTeacherClazzBean);
        /**
         *  绑定成功后的集合
         */
        void getSuccessData(int failNumber,int successNumber);
    }

    interface Presenter extends IBasePresenter {
        /**
         *  获取列表数据
         * @param userId
         * @param serachTag
         * @param gradeId
         * @param classIds
         * @param keywords
         * @param pageTag
         * @param subjectIds
         * @param ps
         * @param pn
         */
        void getBindListData(int userId, int serachTag, String gradeId, String classIds, String keywords, int pageTag, String subjectIds, int ps, int pn);

        /**
         *  解绑设备
         */
        void DeleteEqument(List<DeleteBean> list);

        /**
         *  获取年级数据
         * @param userId
         * @param sectionId
         */
         void getClassData(int userId,String sectionId);

        /**
         *  获取班级或者学科的数据源
         * @param userId
         * @param name
         * @param searchTag
         */
         void getClassNameOrSubject(int userId,String name,int searchTag);

          void getBindEqumentData(List<DeleteEqument> list);
    }
}
