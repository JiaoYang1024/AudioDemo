package com.sid.util;



import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * 作者： ZlyjD.
 * 时间：2017/6/28.
 */

public interface RetrofitApi {


    @FormUrlEncoded   //单个订单评价
    @POST(Constant.TEST_LIST)
    Observable<String> getIndentEvaluation(
            @Field("workerId") String accountId,
            @Field("indentId") String indentId,
            @Field("token") String token
    );


    @FormUrlEncoded   //单个订单评价
    @POST(Constant.TEST_LIST)
    Observable<String> testPost(
            @Field("workerId") int accountId
    );

    @GET
    Observable<String> test(
            @Url String path
    );







}
