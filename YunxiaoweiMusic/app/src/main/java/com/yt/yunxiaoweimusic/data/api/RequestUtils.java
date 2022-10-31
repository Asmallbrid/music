package com.yt.yunxiaoweimusic.data.api;

import android.content.Context;

import com.yt.yunxiaoweimusic.data.request.RetrofitUtils;
import com.yt.yunxiaoweimusic.data.repository.ResponseObserver;
import com.yt.yunxiaoweimusic.data.response.RxHelper;
import com.yt.yunxiaoweimusic.data.bean.BaseResponse;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class RequestUtils {
    public static ApiUrl apiUrl = new RetrofitUtils().getApiUrl(APIs.BASE_URL, ApiUrl.class);
    ;

    public interface ApiUrl {
        @Headers({"Content-Type: application/json", "Accept: application/json"})
        @POST("auth/v2/o2/token")
        Observable<BaseResponse> bindAccount(@HeaderMap Map<String, String> headers, @Body RequestBody body);

        @Headers({"Content-Type: application/json", "Accept: application/json"})
        @POST("account/openapi/v1/get-qr-code")
        Observable<BaseResponse> getQrCode(@HeaderMap Map<String, String> headers, @Body RequestBody body);

        @Headers({"Content-Type: application/json", "Accept: application/json"})
        @POST("music/api/v1/search-custom")
        Observable<BaseResponse> searchCustom(@HeaderMap Map<String, String> headers, @Body RequestBody body);

        @Headers({"Content-Type: application/json", "Accept: application/json"})
        @POST("/music/api/v1/get-lyric")
        Observable<BaseResponse> searchLyric(@HeaderMap Map<String, String> headers, @Body RequestBody body);

        @Headers({"Content-Type: application/json", "Accept: application/json"})
        @POST("music/api/v1/get-songlist-detail")
        Observable<BaseResponse> getSongListDetail(@HeaderMap Map<String, String> headers, @Body RequestBody body);

        @Headers({"Content-Type: application/json", "Accept: application/json"})
        @POST("/music/api/v1/get-songlist-self")
        Observable<BaseResponse> getSongListSelf(@HeaderMap Map<String, String> headers, @Body RequestBody body);

        @Headers({"Content-Type: application/json", "Accept: application/json"})
        @POST("/music/api/v1/get-songlist-fav")
        Observable<BaseResponse> getSongListFav(@HeaderMap Map<String, String> headers, @Body RequestBody body);

        @Headers({"Content-Type: application/json", "Accept: application/json"})
        @POST("music/api/v1/rec-homepage")
        Observable<BaseResponse> recHomepage(@HeaderMap Map<String, String> headers, @Body RequestBody body);

        @Headers({"Content-Type: application/json", "Accept: application/json"})
        @POST("ai/dialog2")
        Observable<BaseResponse> aiDomainDialog2(@HeaderMap Map<String, String> headers, @Body RequestBody body);

        @Headers({"Content-Type: application/json", "Accept: application/json"})
        @POST("music/api/v1/get-singer-list")
        Observable<BaseResponse> getSingerList(@HeaderMap Map<String, String> headers, @Body RequestBody body);

        @Headers({"Content-Type: application/json", "Accept: application/json"})
        @POST("music/api/v1/get-toplist")
        Observable<BaseResponse> getTopList(@HeaderMap Map<String, String> headers, @Body RequestBody body);

        @Headers({"Content-Type: application/json", "Accept: application/json"})
        @POST("music/api/v1/get-toplist-detail")
        Observable<BaseResponse> getTopListDetail(@HeaderMap Map<String, String> headers, @Body RequestBody body);

        @Headers({"Content-Type: application/json", "Accept: application/json"})
        @POST("music/api/v1/get-songlist-category")
        Observable<BaseResponse> getSongListCategory(@HeaderMap Map<String, String> headers, @Body RequestBody body);

        @Headers({"Content-Type: application/json", "Accept: application/json"})
        @POST("/music/api/v1/get-singer-detail")
        Observable<BaseResponse> getSingerDetail(@HeaderMap Map<String, String> headers, @Body RequestBody body);

        @Headers({"Content-Type: application/json", "Accept: application/json"})
        @POST("/music/api/v1/get-singer-album")
        Observable<BaseResponse> getSingerAlbum(@HeaderMap Map<String, String> headers, @Body RequestBody body);

        @Headers({"Content-Type: application/json", "Accept: application/json"})
        @POST("/music/api/v1/get-song-detail-batch")
        Observable<BaseResponse> getSongDetailBatch(@HeaderMap Map<String, String> headers, @Body RequestBody body);
    }

    public static void bindAccount(Context context, Map<String, String> headers, RequestBody body, ResponseObserver consumer) {
        apiUrl.bindAccount(headers, body)
                .compose(RxHelper.observableIO2Main(context)).subscribe(consumer);
    }

    public static void searchCustom(Context context, Map<String, String> headers, RequestBody body, ResponseObserver consumer) {
        apiUrl.searchCustom(headers, body)
                .compose(RxHelper.observableIO2Main(context)).subscribe(consumer);
    }

    public static void getQrCode(Context context, Map<String, String> headers, RequestBody body, ResponseObserver consumer) {
        apiUrl.getQrCode(headers, body)
                .compose(RxHelper.observableIO2Main(context)).subscribe(consumer);
    }

    public static void searchLyric(Context context, Map<String, String> headers, RequestBody body, ResponseObserver consumer) {
        apiUrl.searchLyric(headers, body)
                .compose(RxHelper.observableIO2Main(context)).subscribe(consumer);
    }

    public static void getSongListDetail(Context context, Map<String, String> headers, RequestBody body, ResponseObserver consumer) {
        apiUrl.getSongListDetail(headers, body)
                .compose(RxHelper.observableIO2Main(context)).subscribe(consumer);
    }

    public static void getSongListSelf(Context context, Map<String, String> headers, RequestBody body, ResponseObserver consumer) {
        apiUrl.getSongListSelf(headers, body)
                .compose(RxHelper.observableIO2Main(context)).subscribe(consumer);
    }

    public static void getSongListFav(Context context, Map<String, String> headers, RequestBody body, ResponseObserver consumer) {
        apiUrl.getSongListFav(headers, body)
                .compose(RxHelper.observableIO2Main(context)).subscribe(consumer);
    }

    public static void recHomepage(Context context, Map<String, String> headers, RequestBody body, ResponseObserver consumer) {
        apiUrl.recHomepage(headers, body)
                .compose(RxHelper.observableIO2Main(context)).subscribe(consumer);
    }

    public static void aiDoMain(Context context, Map<String, String> headers, RequestBody body, ResponseObserver consumer) {
        apiUrl.aiDomainDialog2(headers, body)
                .compose(RxHelper.observableIO2Main(context)).subscribe(consumer);
    }

    public static void getSingerList(Context context, Map<String, String> headers, RequestBody body, ResponseObserver consumer) {
        apiUrl.getSingerList(headers, body)
                .compose(RxHelper.observableIO2Main(context)).subscribe(consumer);
    }

    public static void getTopList(Context context, Map<String, String> headers, RequestBody body, ResponseObserver consumer) {
        apiUrl.getTopList(headers, body)
                .compose(RxHelper.observableIO2Main(context)).subscribe(consumer);
    }

    public static void getTopListDetail(Context context, Map<String, String> headers, RequestBody body, ResponseObserver consumer) {
        apiUrl.getTopListDetail(headers, body)
                .compose(RxHelper.observableIO2Main(context)).subscribe(consumer);
    }

    public static void getSongListCategory(Context context, Map<String, String> headers, RequestBody body, ResponseObserver consumer) {
        apiUrl.getSongListCategory(headers, body)
                .compose(RxHelper.observableIO2Main(context)).subscribe(consumer);
    }

    public static void getSingerDetail(Context context, Map<String, String> headers, RequestBody body, ResponseObserver consumer) {
        apiUrl.getSingerDetail(headers, body)
                .compose(RxHelper.observableIO2Main(context)).subscribe(consumer);
    }

    public static void getSingerAlbum(Context context, Map<String, String> headers, RequestBody body, ResponseObserver consumer) {
        apiUrl.getSingerAlbum(headers, body)
                .compose(RxHelper.observableIO2Main(context)).subscribe(consumer);
    }

    public static void getSongDetailBatch(Context context, Map<String, String> headers, RequestBody body, ResponseObserver consumer) {
        apiUrl.getSongDetailBatch(headers, body)
                .compose(RxHelper.observableIO2Main(context)).subscribe(consumer);
    }
}

