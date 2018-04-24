package com.rt.sm.internet;


import com.rt.sm.bean.MattressBean;
import com.rt.sm.bean.MemberBean;
import com.rt.sm.bean.ResultData;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by gaodesong on 17/4/14.
 */

public interface Api {




    @GET("sys/lg/sendlogincode")
    Observable<ResultData<MemberBean>> sendlogincode(@Query("phone") String phone);

    @GET("sys/lg/login")
    Observable<ResultData<MemberBean>> login(@Query("phone") String phone,
                                       @Query("code") String code);

    /*
    {
    "username":"",//用户名
    "email":"",//邮箱
    "sex":"",//性别(0:女  1:男)
    "bodyweight":"",//体重
    "bodyheight":"",//身高
    "mode":""//模式
     }
     */
    @POST("sys/lg/updateUserInfo")
    Observable<ResultData<MemberBean>> updateUserInfo(@Query("sessiontoken") String sessiontoken,
                                                      @Body MemberBean memberBean);


    @POST("pub/public/uploadImage")
    Observable<ResultData<MemberBean>> uploadImage(@Query("sessiontoken") String sessiontoken,
                                                   @Body RequestBody body);


    @GET("sys/lg/checktoken")
    Observable<ResultData<MemberBean>> checktoken(@Query("sessiontoken") String token);


    @POST("pub/mattres/addMattres")
    Observable<ResultData<MattressBean>> addMattres(@Query("sessiontoken") String sessiontoken,
                                            @Body MattressBean mattressBean);

    @GET("pub/mattres/getMattresList")
    Observable<ResultData<List<MattressBean>>> getMattresList(@Query("sessiontoken") String token);


    @GET("pub/mattres/getOne")
    Observable<ResultData<MattressBean>> getOne(@Query("sessiontoken") String sessiontoken,
                                                @Query("mattresno") String mattresno);


    @POST("pub/mattres/updateMattres")
    Observable<ResultData<MattressBean>> updateMattres(@Query("sessiontoken") String sessiontoken,
                                                       @Body MattressBean mattressBean);


    @GET("pub/mattres/deleteMattres")
    Observable<ResultData<MattressBean>> deleteMattres(@Query("sessiontoken") String sessiontoken,
                                                       @Query("mattresno") String mattresno);

    @GET("pub/household/getHouseholdList")
    Observable<ResultData<List<MemberBean>>> getHouseholdList(@Query("sessiontoken") String sessiontoken);

    @POST("pub/household/addHousehold")
    Observable<ResultData<MemberBean>> addHousehold(@Query("sessiontoken") String sessiontoken,
                                                    @Body MemberBean memberBean);

    @POST("pub/household/getOne")
    Observable<ResultData<MemberBean>> getOneSleepMember(@Query("sessiontoken") String sessiontoken,
                                                         @Query("householdacc") String householdacc);

    @POST("pub/household/deletehousehold")
    Observable<ResultData<MemberBean>> deletehousehold(@Query("sessiontoken") String sessiontoken,
                                                       @Query("householdacc") String householdacc);


    @POST("pub/household/updateHousehold")
    Observable<ResultData<MemberBean>> updateHousehold(@Query("sessiontoken") String sessiontoken,
                                                       @Body MemberBean memberBean);

    @GET("pub/mattres/getDefaultMattres")
    Observable<ResultData<MattressBean>> getDefaultMattres(@Query("sessiontoken") String sessiontoken);


    @POST("pub/mattres/updateMattresHousehold")
    Observable<ResultData<MemberBean>> updateMattresHousehold(@Query("sessiontoken") String sessiontoken,
                                                              @Body MemberBean memberBean);

    //
    @POST("pub/mattres/updateDefaultMattres")
    Observable<ResultData<MattressBean>> updateDefaultMattres(@Query("sessiontoken") String sessiontoken,
                                                            @Query("mattresno") String mattresno);


}
