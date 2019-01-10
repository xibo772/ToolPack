package android.biginner.com.tool_aar.network;


/**
 * Created by jun on 2016/11/7.
 */

public interface RequestService {

    /**
     * @GET	        表明这是get请求
     * @POST	    表明这是post请求
     * @PUT	        表明这是put请求
     * @DELETE	    表明这是delete请求
     * @PATCH	    表明这是一个patch请求，该请求是对put请求的补充，用于更新局部资源
     * @HEAD	    表明这是一个head请求
     * @OPTIONS	    表明这是一个option请求
     * @HTTP	    通用注解,可以替换以上所有的注解，其拥有三个属性：method，path，hasBody
     */

    /**
     * 请求头
     * @Headers	    用于添加固定请求头，可以同时添加多个。通过该注解添加的请求头不会相互覆盖，而是共同存在
     * @Header	    作为方法的参数传入，用于添加不固定值的Header，该注解会更新已有的请求头
     */

    /**
     * 添加多个请求头
     * @Headers({
     * "User-Agent:android",
     * "Cache-Control:public,max-age=120"
     * })*/

    /**
     * 请求参数类
     * @Body	    多用于post请求发送非表单数据,比如想要以post方式传递json格式数据
     * @Filed	    多用于post请求中表单字段,Filed和FieldMap需要FormUrlEncoded结合使用
     * @FiledMap	和@Filed作用一致，用于不确定表单参数
     * @Part	    用于表单字段,Part和PartMap与Multipart注解结合使用,适合文件上传的情况
     * @Query       后台接受字段类型
     */
//    登录
//    @FormUrlEncoded
//    @POST("dataController/syncUserData.do")
//    Call<HttpResponse<LoginInfo>> login(@Field("companyID") String companyID,
//                                        @Field("loginName") String loginName, @Field("password") String password,
//                                        @Query("__ajax") String ajax, @Query("mobileLogin") String mobileLogin);

    //登录  requestbody方式
//    @POST("/loginController/userLogin.do")
//    Call<HttpResponse<LoginInfo>> login(@Body LoginRequest bean);

     //参数方式
//    @POST("/loginController/userLogin.do")
//    Call<HttpResponse<LoginInfo>> login(@Query("password") String password, @Query("account") String uname);


    //修改密码
//    @POST("/userController/updatePassword.do")
//    Call<HttpResponse> setPassword(@Query("id") String id, @Query("password") String password, @Query("oldPassword") String oldPassword);


    /*上传照片*/
//    @Multipart
//    @POST("/uploadController/uploadPicture.do")
//    Call<HttpResponse<LoginInfo>> uploadPicture (@Query("id") String description, @Part() MultipartBody.Part imgs );


}
