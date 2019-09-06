package www.app.ypy.com.mvpandroid.network;
import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import www.app.ypy.com.mvpandroid.bean.Bindlistbean;
import www.app.ypy.com.mvpandroid.bean.DeleteBean;
import www.app.ypy.com.mvpandroid.bean.DeleteEqument;
import www.app.ypy.com.mvpandroid.bean.InforBean;
import www.app.ypy.com.mvpandroid.bean.LoginBean;

/**
 * 游戏用的请求管理器
 */
public interface ServerApi {
  //  @FormUrlEncoded
    @GET("login")
    Observable<BaseResult<LoginBean>> getLogin(@Query("login") String account, @Query("password") String password);
    //查询个人信息
    @GET("mobile/user/info/{account}")
    Observable<BaseResult<InforBean>> getInformation(@Path("account") String account, @Query("mainView") int mainView);

    /**
     *   获取年级
     * @param userId
     * @param sectionNameCode
     * @return
     */
    @GET("mobile/school/admin/{userId}")
    Observable<BaseResult<List<InforBean.MainTeacherClazzBean>>> getGradeName(@Path("userId") int userId, @Query("sectionNameCode") String sectionNameCode);

    /**
     *    获取班级或者科目
     * @param userId
     * @param gradeIds
     * @param type
     * @return
     */
    @GET("mobile/school/admin/{userId}/type/{type}")
    Observable<BaseResult<List<InforBean.MainTeacherClazzBean>>> getClassName(@Path("userId") int userId, @Path("type") int type, @Query("gradeIds") String gradeIds);



    @GET("mobile/user/list")
    Observable<BaseResult<List<Bindlistbean>>> getBindlist(@Query("userId") int userId, @Query("searchTag") int searchTag, @Query("gradeIds") String gradeIds, @Query("classIds") String classIds,
                                                           @Query("keywords") String keywords, @Query("bindingTag") int bindingTag, @Query("subjectIds") String subjectIds,
                                                           @Query("ps") int ps, @Query("pn") int pn);

   //  解绑
  //   @DELETE("mobile/common/devices")
     @HTTP(method = "DELETE",path ="mobile/common/devices",hasBody = true)
     Observable<BaseResult<List<DeleteEqument>>> getDeleteEqument(@Body List<DeleteBean> deleteEqumentList);

     //绑定接口
     @HTTP(method = "POST",path ="mobile/common/devices",hasBody = true)
     Observable<BaseResult<List<DeleteEqument>>> getBindEqument(@Body List<DeleteBean> bingEqumentList);
//    /**
//     * 图书下载2
//     */
//    @FormUrlEncoded
//    @POST("bookStore")
//    @DefaultField(keys = {"m"}, values = {"downloadBook"})
//    Observable<BaseResult<List<OssInfoBean>>> downloadFile(@Field("bookId") String bookId, @Field("atchTypeCode") String atchTypeCode);


}
