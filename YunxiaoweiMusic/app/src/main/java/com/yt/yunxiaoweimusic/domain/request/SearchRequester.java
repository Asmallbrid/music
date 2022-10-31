package com.yt.yunxiaoweimusic.domain.request;


import android.content.Context;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.kunminx.architecture.domain.message.MutableResult;
import com.kunminx.architecture.domain.message.Result;
import com.yt.yunxiaoweimusic.data.bean.AccessToken;
import com.yt.yunxiaoweimusic.data.bean.BaseRequrst;
import com.yt.yunxiaoweimusic.data.bean.GetLyricPayload;
import com.yt.yunxiaoweimusic.data.bean.GetSingerAlbumResponsePayload;
import com.yt.yunxiaoweimusic.data.bean.GetSingerDetailResponsePayload;
import com.yt.yunxiaoweimusic.data.bean.GetSongListCategory;
import com.yt.yunxiaoweimusic.data.bean.GetSongListDetailResponsePayload;
import com.yt.yunxiaoweimusic.data.bean.GetSongListSelfPayload;
import com.yt.yunxiaoweimusic.data.bean.LyricRequest;
import com.yt.yunxiaoweimusic.data.bean.LyricRequestPayload;
import com.yt.yunxiaoweimusic.data.bean.MusicRequest;
import com.yt.yunxiaoweimusic.data.bean.MusicRequestPayload;
import com.yt.yunxiaoweimusic.data.bean.RecHomePageResponsePayload;
import com.yt.yunxiaoweimusic.data.bean.RequestHeader;
import com.yt.yunxiaoweimusic.data.bean.RequestPayload;
import com.yt.yunxiaoweimusic.data.bean.SingerBean;
import com.yt.yunxiaoweimusic.data.bean.SingerListResponsePayload;
import com.yt.yunxiaoweimusic.data.bean.SongBean;
import com.yt.yunxiaoweimusic.data.bean.TopListDetailResponsePayload;
import com.yt.yunxiaoweimusic.data.bean.TopListResponsePayload;
import com.yt.yunxiaoweimusic.data.repository.DataRepository;
import com.yt.yunxiaoweimusic.data.response.DataResult;
import com.yt.yunxiaoweimusic.utils.AssetsUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;


public class SearchRequester extends ViewModel implements DefaultLifecycleObserver {


    private final MutableResult<DataResult<String>> searchResult = new MutableResult<>();
    private final MutableResult<DataResult<GetLyricPayload>> searchLyricResult = new MutableResult<>();
    private final MutableResult<DataResult<GetSongListDetailResponsePayload>> searchSongListDetailResult = new MutableResult<>();
    private final MutableResult<DataResult<List<GetSongListSelfPayload.SongListInfo>>> searchSongListFavResult = new MutableResult<>();
    private final MutableResult<DataResult<List<GetSongListSelfPayload.SongListInfo>>> searchSongListSelfResult = new MutableResult<>();
    private final MutableResult<DataResult<List<RecHomePageResponsePayload.Card>>> rcvSongListHomePageResult = new MutableResult<>();
    private final MutableResult<DataResult<List<RecHomePageResponsePayload.Card>>> rcvSongHomePageResult = new MutableResult<>();
    private final MutableResult<DataResult<List<SingerBean>>> searchSingerListResult = new MutableResult<>();
    private final MutableResult<DataResult<List<TopListResponsePayload.Group>>> searchTopListResult = new MutableResult<>();
    private final MutableResult<DataResult<TopListDetailResponsePayload>> searchTopListDetailResult = new MutableResult<>();
    private final MutableResult<DataResult<GetSongListCategory>> searchSongListCategoryResult = new MutableResult<>();
    private final MutableResult<DataResult<GetSingerDetailResponsePayload>> searchSingerDetailResult = new MutableResult<>();
    private final MutableResult<DataResult<GetSingerAlbumResponsePayload>> searchSingerAlbumResult = new MutableResult<>();
    private final MutableResult<DataResult<GetSongListDetailResponsePayload>> searchSongDetailBatchResult = new MutableResult<>();


    public Result<DataResult<String>> getSearchResult() {
        return searchResult;
    }

    public Result<DataResult<GetLyricPayload>> getSearchLyricResult() {
        return searchLyricResult;
    }

    public Result<DataResult<GetSongListDetailResponsePayload>> getSongListDetailResult() {
        return searchSongListDetailResult;
    }

    public Result<DataResult<List<GetSongListSelfPayload.SongListInfo>>> getSongListFavResult() {
        return searchSongListFavResult;
    }

    public Result<DataResult<List<GetSongListSelfPayload.SongListInfo>>> getSongListSelfResult() {
        return searchSongListSelfResult;
    }

    public Result<DataResult<List<RecHomePageResponsePayload.Card>>> getRcvSongListHomePageResult() {
        return rcvSongListHomePageResult;
    }

    public Result<DataResult<List<RecHomePageResponsePayload.Card>>> getRcvSongHomePageResult() {
        return rcvSongHomePageResult;
    }

    public Result<DataResult<List<SingerBean>>> getSingerListResult() {
        return searchSingerListResult;
    }

    public Result<DataResult<List<TopListResponsePayload.Group>>> getTopListResult() {
        return searchTopListResult;
    }

    public Result<DataResult<TopListDetailResponsePayload>> getTopListDetailResult() {
        return searchTopListDetailResult;
    }

    public Result<DataResult<GetSongListCategory>> getSongListCategoryResult() {
        return searchSongListCategoryResult;
    }

    public Result<DataResult<GetSingerDetailResponsePayload>> getSingerDetailResult() {
        return searchSingerDetailResult;
    }

    public Result<DataResult<GetSingerAlbumResponsePayload>> getSingerAlbumResult() {
        return searchSingerAlbumResult;
    }

    public Result<DataResult<GetSongListDetailResponsePayload>> getSongDetailBatchResult() {
        return searchSongDetailBatchResult;
    }

    public void serchCustom(Context context, String keyWord, int type) {
        MusicRequestPayload payload = new MusicRequestPayload.Builder().setKeyword(keyWord).setType(type).build();
        //MusicRequestPayload payload = new MusicRequestPayload(mStates.searchKeyWord.get(), 0, type, 0);
        MusicRequest baseRequest = new MusicRequest();
        baseRequest.setHeader(new RequestHeader());
        baseRequest.setPayload(payload);
        String requestBody = new Gson().toJson(baseRequest);

        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + AccessToken.getInstance().getAuthorization());
        map.put("appkey", "b9b49a20178a11edb96eabbf4fec7f4f");
        map.put("dsn", "YUNTUTEST0708_dev001");
        RequestBody body = RequestBody.create(requestBody, MediaType.parse("application/json;charset=utf-8"));
        DataRepository.getInstance().searchCustom(context, map, body, searchResult::postValue);
    }

    public void serchLyric(Context context, String songMid) {
        LyricRequestPayload payload = new LyricRequestPayload.Builder().setSongMid(songMid).build();
        LyricRequest baseRequest = new LyricRequest();
        baseRequest.setHeader(new RequestHeader());
        baseRequest.setPayload(payload);
        String requestBody = new Gson().toJson(baseRequest);
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + AccessToken.getInstance().getAuthorization());
        map.put("appkey", "b9b49a20178a11edb96eabbf4fec7f4f");
        map.put("dsn", "YUNTUTEST0708_dev001");
        RequestBody body = RequestBody.create(requestBody, MediaType.parse("application/json;charset=utf-8"));
        DataRepository.getInstance().searchLyric(context, map, body, searchLyricResult::postValue);
    }

    public void getSongListDetail(Context context, long songListId) {
        RequestHeader header = new Gson().fromJson(AssetsUtil.getDataFromJson(context, "header.json"), RequestHeader.class);
        MusicRequestPayload payload = new MusicRequestPayload.Builder().setSongListId(songListId).build();
        MusicRequest baseRequest = new MusicRequest();
        baseRequest.setHeader(header);
        baseRequest.setPayload(payload);
        String requestBody = new Gson().toJson(baseRequest);
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + AccessToken.getInstance().getAuthorization());
        map.put("appkey", "b9b49a20178a11edb96eabbf4fec7f4f");
        map.put("dsn", "YUNTUTEST0708_dev001");
        RequestBody body = RequestBody.create(requestBody, MediaType.parse("application/json;charset=utf-8"));
        DataRepository.getInstance().getSongListDetail(context, map, body, searchSongListDetailResult::postValue);
    }

    public void getSongListFav(Context context) {
        RequestHeader header = new Gson().fromJson(AssetsUtil.getDataFromJson(context, "header.json"), RequestHeader.class);
        MusicRequestPayload payload = new MusicRequestPayload.Builder().setCmd(3).build();
        MusicRequest baseRequest = new MusicRequest();
        baseRequest.setHeader(header);
        baseRequest.setPayload(payload);
        String requestBody = new Gson().toJson(baseRequest);
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + AccessToken.getInstance().getAuthorization());
        map.put("appkey", "b9b49a20178a11edb96eabbf4fec7f4f");
        map.put("dsn", "YUNTUTEST0708_dev001");
        RequestBody body = RequestBody.create(requestBody, MediaType.parse("application/json;charset=utf-8"));
        DataRepository.getInstance().getSongListFav(context, map, body, searchSongListFavResult::postValue);
    }

    public void getSongListSelf(Context context) {
        RequestHeader header = new Gson().fromJson(AssetsUtil.getDataFromJson(context, "header.json"), RequestHeader.class);
        MusicRequestPayload payload = new MusicRequestPayload.Builder().build();
        MusicRequest baseRequest = new MusicRequest();
        baseRequest.setHeader(header);
        baseRequest.setPayload(payload);
        String requestBody = new Gson().toJson(baseRequest);
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + AccessToken.getInstance().getAuthorization());
        map.put("appkey", "b9b49a20178a11edb96eabbf4fec7f4f");
        map.put("dsn", "YUNTUTEST0708_dev001");
        RequestBody body = RequestBody.create(requestBody, MediaType.parse("application/json;charset=utf-8"));
        DataRepository.getInstance().getSongListSelf(context, map, body, searchSongListSelfResult::postValue);
    }

    public void recSongListHomepage(Context context, String sn) {
        RequestHeader header = new Gson().fromJson(AssetsUtil.getDataFromJson(context, "header.json"), RequestHeader.class);
        RequestPayload payload = new RequestPayload.Builder().setSn(sn).setType("500").build();
        BaseRequrst baseRequrst = new BaseRequrst();
        baseRequrst.setHeader(header);
        baseRequrst.setPayload(payload);
        String requestBody = new Gson().toJson(baseRequrst);

        Map<String, String> map = new HashMap<>();
        map.put("appkey", "b9b49a20178a11edb96eabbf4fec7f4f");
        map.put("dsn", "YUNTUTEST0708_dev001");
        map.put("Authorization", "Bearer " + AccessToken.getInstance().getAuthorization());
        RequestBody body = RequestBody.create(requestBody, MediaType.parse("application/json;charset=utf-8"));

        DataRepository.getInstance().recHomepage(context, map, body, rcvSongListHomePageResult::postValue);
    }

    public void recSongHomepage(Context context, String sn) {
        RequestHeader header = new Gson().fromJson(AssetsUtil.getDataFromJson(context, "header.json"), RequestHeader.class);
        RequestPayload payload = new RequestPayload.Builder().setSn(sn).setType("200").build();
        BaseRequrst baseRequrst = new BaseRequrst();
        baseRequrst.setHeader(header);
        baseRequrst.setPayload(payload);
        String requestBody = new Gson().toJson(baseRequrst);

        Map<String, String> map = new HashMap<>();
        map.put("appkey", "b9b49a20178a11edb96eabbf4fec7f4f");
        map.put("dsn", "YUNTUTEST0708_dev001");
        map.put("Authorization", "Bearer " + AccessToken.getInstance().getAuthorization());
        RequestBody body = RequestBody.create(requestBody, MediaType.parse("application/json;charset=utf-8"));

        DataRepository.getInstance().recHomepage(context, map, body, rcvSongHomePageResult::postValue);
    }

    public void getSingerList(Context context) {
        RequestHeader header = new Gson().fromJson(AssetsUtil.getDataFromJson(context, "header.json"), RequestHeader.class);
        RequestPayload payload = new RequestPayload.Builder().build();
        BaseRequrst baseRequrst = new BaseRequrst();
        baseRequrst.setHeader(header);
        baseRequrst.setPayload(payload);
        String requestBody = new Gson().toJson(baseRequrst);

        Map<String, String> map = new HashMap<>();
        map.put("appkey", "b9b49a20178a11edb96eabbf4fec7f4f");
        map.put("dsn", "YUNTUTEST0708_dev001");
        map.put("Authorization", "Bearer " + AccessToken.getInstance().getAuthorization());
        RequestBody body = RequestBody.create(requestBody, MediaType.parse("application/json;charset=utf-8"));
        DataRepository.getInstance().getSingerList(context, map, body, searchSingerListResult::postValue);
    }

    public void getSingerList(Context context, int area, int type, int genre) {
        RequestHeader header = new Gson().fromJson(AssetsUtil.getDataFromJson(context, "header.json"), RequestHeader.class);
        MusicRequestPayload payload = new MusicRequestPayload.Builder().setArea(area).setType(type).setGenre(genre).build();
        MusicRequest baseRequrst = new MusicRequest();
        baseRequrst.setHeader(header);
        baseRequrst.setPayload(payload);
        String requestBody = new Gson().toJson(baseRequrst);

        Map<String, String> map = new HashMap<>();
        map.put("appkey", "b9b49a20178a11edb96eabbf4fec7f4f");
        map.put("dsn", "YUNTUTEST0708_dev001");
        map.put("Authorization", "Bearer " + AccessToken.getInstance().getAuthorization());
        RequestBody body = RequestBody.create(requestBody, MediaType.parse("application/json;charset=utf-8"));
        DataRepository.getInstance().getSingerList(context, map, body, searchSingerListResult::postValue);
    }

    public void getTopList(Context context) {
        RequestHeader header = new Gson().fromJson(AssetsUtil.getDataFromJson(context, "header.json"), RequestHeader.class);
        RequestPayload payload = new RequestPayload.Builder().build();
        BaseRequrst baseRequrst = new BaseRequrst();
        baseRequrst.setHeader(header);
        baseRequrst.setPayload(payload);
        String requestBody = new Gson().toJson(baseRequrst);

        Map<String, String> map = new HashMap<>();
        map.put("appkey", "b9b49a20178a11edb96eabbf4fec7f4f");
        map.put("dsn", "YUNTUTEST0708_dev001");
        map.put("Authorization", "Bearer " + AccessToken.getInstance().getAuthorization());
        RequestBody body = RequestBody.create(requestBody, MediaType.parse("application/json;charset=utf-8"));
        DataRepository.getInstance().getTopList(context, map, body, searchTopListResult::postValue);
    }

    public void getTopListDetail(Context context, int topId) {
        RequestHeader header = new Gson().fromJson(AssetsUtil.getDataFromJson(context, "header.json"), RequestHeader.class);
        MusicRequestPayload payload = new MusicRequestPayload.Builder().setTopId(topId).build();
        MusicRequest baseRequrst = new MusicRequest();
        baseRequrst.setHeader(header);
        baseRequrst.setPayload(payload);
        String requestBody = new Gson().toJson(baseRequrst);

        Map<String, String> map = new HashMap<>();
        map.put("appkey", "b9b49a20178a11edb96eabbf4fec7f4f");
        map.put("dsn", "YUNTUTEST0708_dev001");
        map.put("Authorization", "Bearer " + AccessToken.getInstance().getAuthorization());
        RequestBody body = RequestBody.create(requestBody, MediaType.parse("application/json;charset=utf-8"));
        DataRepository.getInstance().getTopListDetail(context, map, body, searchTopListDetailResult::postValue);
    }

    public void getSongListCategory(Context context, int cmd, int category) {
        RequestHeader header = new Gson().fromJson(AssetsUtil.getDataFromJson(context, "header.json"), RequestHeader.class);
        MusicRequestPayload payload = new MusicRequestPayload.Builder().setCmd(cmd).setCategoryId(category).build();
        MusicRequest baseRequrst = new MusicRequest();
        baseRequrst.setHeader(header);
        baseRequrst.setPayload(payload);
        String requestBody = new Gson().toJson(baseRequrst);

        Map<String, String> map = new HashMap<>();
        map.put("appkey", "b9b49a20178a11edb96eabbf4fec7f4f");
        map.put("dsn", "YUNTUTEST0708_dev001");
        map.put("Authorization", "Bearer " + AccessToken.getInstance().getAuthorization());
        RequestBody body = RequestBody.create(requestBody, MediaType.parse("application/json;charset=utf-8"));
        DataRepository.getInstance().getSongListCategory(context, map, body, searchSongListCategoryResult::postValue);
    }

    public void getSingerDetail(Context context, int singerId, int pageIndex, int pageSize) {
        RequestHeader header = new Gson().fromJson(AssetsUtil.getDataFromJson(context, "header.json"), RequestHeader.class);
        MusicRequestPayload payload = new MusicRequestPayload.Builder().setSingerId(singerId).setPageIndex(pageIndex).setPageSize(pageSize).build();
        MusicRequest baseRequrst = new MusicRequest();
        baseRequrst.setHeader(header);
        baseRequrst.setPayload(payload);
        String requestBody = new Gson().toJson(baseRequrst);

        Map<String, String> map = new HashMap<>();
        map.put("appkey", "b9b49a20178a11edb96eabbf4fec7f4f");
        map.put("dsn", "YUNTUTEST0708_dev001");
        map.put("Authorization", "Bearer " + AccessToken.getInstance().getAuthorization());
        RequestBody body = RequestBody.create(requestBody, MediaType.parse("application/json;charset=utf-8"));
        DataRepository.getInstance().getSingerDetail(context, map, body, searchSingerDetailResult::postValue);
    }

    public void getSingerAlbum(Context context, int singerId, int pageIndex, int pageSize) {
        RequestHeader header = new Gson().fromJson(AssetsUtil.getDataFromJson(context, "header.json"), RequestHeader.class);
        MusicRequestPayload payload = new MusicRequestPayload.Builder().setSingerId(singerId).setPageIndex(pageIndex).setPageSize(pageSize).build();
        MusicRequest baseRequrst = new MusicRequest();
        baseRequrst.setHeader(header);
        baseRequrst.setPayload(payload);
        String requestBody = new Gson().toJson(baseRequrst);

        Map<String, String> map = new HashMap<>();
        map.put("appkey", "b9b49a20178a11edb96eabbf4fec7f4f");
        map.put("dsn", "YUNTUTEST0708_dev001");
        map.put("Authorization", "Bearer " + AccessToken.getInstance().getAuthorization());
        RequestBody body = RequestBody.create(requestBody, MediaType.parse("application/json;charset=utf-8"));
        DataRepository.getInstance().getSingerAlbum(context, map, body, searchSingerAlbumResult::postValue);
    }

    public void getSongDetailBatch(Context context, String songId) {
        RequestHeader header = new Gson().fromJson(AssetsUtil.getDataFromJson(context, "header.json"), RequestHeader.class);
        MusicRequestPayload payload = new MusicRequestPayload.Builder().setSongIds(songId).build();
        MusicRequest baseRequrst = new MusicRequest();
        baseRequrst.setHeader(header);
        baseRequrst.setPayload(payload);
        String requestBody = new Gson().toJson(baseRequrst);

        Map<String, String> map = new HashMap<>();
        map.put("appkey", "b9b49a20178a11edb96eabbf4fec7f4f");
        map.put("dsn", "YUNTUTEST0708_dev001");
        map.put("Authorization", "Bearer " + AccessToken.getInstance().getAuthorization());
        RequestBody body = RequestBody.create(requestBody, MediaType.parse("application/json;charset=utf-8"));
        DataRepository.getInstance().getSongDetailBatch(context, map, body, searchSongDetailBatchResult::postValue);
    }
}
