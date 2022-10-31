/*
 * Copyright 2018-present KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yt.yunxiaoweimusic.data.repository;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yt.yunxiaoweimusic.data.api.RequestUtils;
import com.yt.yunxiaoweimusic.data.bean.BaseResponse;
import com.yt.yunxiaoweimusic.data.bean.BindAccountPayload;
import com.yt.yunxiaoweimusic.data.bean.GetLyricPayload;
import com.yt.yunxiaoweimusic.data.bean.GetQrCodePayload;
import com.yt.yunxiaoweimusic.data.bean.GetQrCodeResponse;
import com.yt.yunxiaoweimusic.data.bean.GetSingerAlbumResponsePayload;
import com.yt.yunxiaoweimusic.data.bean.GetSingerDetailResponsePayload;
import com.yt.yunxiaoweimusic.data.bean.GetSongListCategory;
import com.yt.yunxiaoweimusic.data.bean.GetSongListDetailResponsePayload;
import com.yt.yunxiaoweimusic.data.bean.GetSongListSelfPayload;
import com.yt.yunxiaoweimusic.data.bean.RecHomePageResponsePayload;
import com.yt.yunxiaoweimusic.data.bean.SingerBean;
import com.yt.yunxiaoweimusic.data.bean.SingerListResponsePayload;
import com.yt.yunxiaoweimusic.data.bean.SongBean;
import com.yt.yunxiaoweimusic.data.bean.TopListDetailResponsePayload;
import com.yt.yunxiaoweimusic.data.bean.TopListResponsePayload;
import com.yt.yunxiaoweimusic.data.response.DataResult;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

public class DataRepository {
    private static final String TAG = "RetrofitUtils";
    private static final DataRepository S_REQUEST_MANAGER = new DataRepository();

    private DataRepository() {
    }

    public static DataRepository getInstance() {
        return S_REQUEST_MANAGER;
    }

    public void bindAccount(Context context, Map<String, String> headers, RequestBody body, DataResult.Result<String> result) {
        ResponseObserver observer = new ResponseObserver<String>(context, false) {
            @Override
            public void onSuccess(String str) {
                Gson gson = new Gson();
                BaseResponse baseResponse = gson.fromJson(str, BaseResponse.class);
                String s = gson.toJson(baseResponse.payload);
                BindAccountPayload bindAccountPayload = gson.fromJson(s, BindAccountPayload.class);
                result.onResult(new DataResult<>(bindAccountPayload.accessToken));
            }

            @Override
            public void onFailure(Throwable e, String errorMsg) {
            }
        };
        RequestUtils.bindAccount(context, headers, body, observer);
    }

    public void getQrCode(Context context, Map<String, String> headers, RequestBody body, DataResult.Result<String> result) {
        ResponseObserver observer = new ResponseObserver<String>(context, false) {
            @Override
            public void onSuccess(String str) {
                Gson gson = new Gson();
                GetQrCodeResponse baseResponse = gson.fromJson(str, GetQrCodeResponse.class);
                GetQrCodePayload getQrCodePayload = baseResponse.getPayload();
                result.onResult(new DataResult<>(getQrCodePayload.qrCode));
            }

            @Override
            public void onFailure(Throwable e, String errorMsg) {
            }
        };
        RequestUtils.getQrCode(context, headers, body, observer);
    }

    public void searchCustom(Context context, Map<String, String> headers, RequestBody body, DataResult.Result<String> result) {
        ResponseObserver observer = new ResponseObserver<String>(context, false) {
            @Override
            public void onSuccess(String str) {
                result.onResult(new DataResult<>(str));
            }

            @Override
            public void onFailure(Throwable e, String errorMsg) {
            }
        };
        RequestUtils.searchCustom(context, headers, body, observer);
    }

    public void searchLyric(Context context, Map<String, String> headers, RequestBody body, DataResult.Result<GetLyricPayload> result) {
        ResponseObserver observer = new ResponseObserver<String>(context, false) {
            @Override
            public void onSuccess(String str) {
                Gson gson = new Gson();
                BaseResponse baseResponse = gson.fromJson(str, BaseResponse.class);
                String s = gson.toJson(baseResponse.payload);
                GetLyricPayload getLyricPayload = gson.fromJson(s, GetLyricPayload.class);
                result.onResult(new DataResult<>(getLyricPayload));
            }

            @Override
            public void onFailure(Throwable e, String errorMsg) {
            }
        };
        RequestUtils.searchLyric(context, headers, body, observer);
    }

    public void getSongListDetail(Context context, Map<String, String> headers, RequestBody body, DataResult.Result<GetSongListDetailResponsePayload> result) {
        ResponseObserver observer = new ResponseObserver<String>(context, false) {
            @Override
            public void onSuccess(String str) {
                Gson gson = new Gson();
                BaseResponse baseResponse = gson.fromJson(str, BaseResponse.class);
                String s = gson.toJson(baseResponse.payload);
                GetSongListDetailResponsePayload getSongListDetailResponsePayload = gson.fromJson(s, GetSongListDetailResponsePayload.class);
                result.onResult(new DataResult<>(getSongListDetailResponsePayload));
            }

            @Override
            public void onFailure(Throwable e, String errorMsg) {
            }
        };
        RequestUtils.getSongListDetail(context, headers, body, observer);
    }

    public void getSongListSelf(Context context, Map<String, String> headers, RequestBody body, DataResult.Result<List<GetSongListSelfPayload.SongListInfo>> result) {
        ResponseObserver observer = new ResponseObserver<String>(context, false) {
            @Override
            public void onSuccess(String str) {
                Gson gson = new Gson();
                BaseResponse baseResponse = gson.fromJson(str, BaseResponse.class);
                String s = gson.toJson(baseResponse.payload);
                GetSongListSelfPayload getSongListDetailResponsePayload = gson.fromJson(s, GetSongListSelfPayload.class);
                result.onResult(new DataResult<>(getSongListDetailResponsePayload.songListInfos));
            }

            @Override
            public void onFailure(Throwable e, String errorMsg) {
            }
        };
        RequestUtils.getSongListSelf(context, headers, body, observer);
    }

    public void getSongListFav(Context context, Map<String, String> headers, RequestBody body, DataResult.Result<List<GetSongListSelfPayload.SongListInfo>> result) {
        ResponseObserver observer = new ResponseObserver<String>(context, false) {
            @Override
            public void onSuccess(String str) {
                Gson gson = new Gson();
                BaseResponse baseResponse = gson.fromJson(str, BaseResponse.class);
                String s = gson.toJson(baseResponse.payload);
                GetSongListSelfPayload getSongListDetailResponsePayload = gson.fromJson(s, GetSongListSelfPayload.class);
                result.onResult(new DataResult<>(getSongListDetailResponsePayload.songListInfos));
            }

            @Override
            public void onFailure(Throwable e, String errorMsg) {
            }
        };
        RequestUtils.getSongListFav(context, headers, body, observer);
    }

    public void recHomepage(Context context, Map<String, String> headers, RequestBody body, DataResult.Result<List<RecHomePageResponsePayload.Card>> result) {
        ResponseObserver observer = new ResponseObserver<String>(context, false) {
            @Override
            public void onSuccess(String str) {
                Gson gson = new Gson();
                BaseResponse baseResponse = gson.fromJson(str, BaseResponse.class);
                String s = gson.toJson(baseResponse.payload);
                RecHomePageResponsePayload recHomePageResponsePayload = gson.fromJson(s, RecHomePageResponsePayload.class);
                result.onResult(new DataResult<>(recHomePageResponsePayload.shelfArr.get(0).cardArr));
            }

            @Override
            public void onFailure(Throwable e, String errorMsg) {
            }
        };
        RequestUtils.recHomepage(context, headers, body, observer);
    }

    public void getSingerList(Context context, Map<String, String> headers, RequestBody body, DataResult.Result<List<SingerBean>> result) {
        ResponseObserver observer = new ResponseObserver<String>(context, false) {
            @Override
            public void onSuccess(String str) {
                Gson gson = new Gson();
                BaseResponse baseResponse = gson.fromJson(str, BaseResponse.class);
                String s = gson.toJson(baseResponse.payload);
                SingerListResponsePayload singerListResponsePayload = gson.fromJson(s, SingerListResponsePayload.class);
                result.onResult(new DataResult<>(singerListResponsePayload.singerList));
            }

            @Override
            public void onFailure(Throwable e, String errorMsg) {
            }
        };
        RequestUtils.getSingerList(context, headers, body, observer);
    }

    public void getTopList(Context context, Map<String, String> headers, RequestBody body, DataResult.Result<List<TopListResponsePayload.Group>> result) {
        ResponseObserver observer = new ResponseObserver<String>(context, false) {
            @Override
            public void onSuccess(String str) {
                Gson gson = new Gson();
                BaseResponse baseResponse = gson.fromJson(str, BaseResponse.class);
                String s = gson.toJson(baseResponse.payload);
                TopListResponsePayload topListResponsePayload = gson.fromJson(s, TopListResponsePayload.class);
                result.onResult(new DataResult<>(topListResponsePayload.groupList));
            }

            @Override
            public void onFailure(Throwable e, String errorMsg) {
            }
        };
        RequestUtils.getTopList(context, headers, body, observer);
    }

    public void getSongListCategory(Context context, Map<String, String> headers, RequestBody body, DataResult.Result<GetSongListCategory> result) {
        ResponseObserver observer = new ResponseObserver<String>(context, false) {
            @Override
            public void onSuccess(String str) {
                Gson gson = new Gson();
                BaseResponse baseResponse = gson.fromJson(str, BaseResponse.class);
                String s = gson.toJson(baseResponse.payload);
                GetSongListCategory getSongListCategory = gson.fromJson(s, GetSongListCategory.class);
                result.onResult(new DataResult<>(getSongListCategory));
            }

            @Override
            public void onFailure(Throwable e, String errorMsg) {

            }
        };
        RequestUtils.getSongListCategory(context, headers, body, observer);
    }

    public void getSingerDetail(Context context, Map<String, String> headers, RequestBody body, DataResult.Result<GetSingerDetailResponsePayload> result) {
        ResponseObserver observer = new ResponseObserver<String>(context, false) {
            @Override
            public void onSuccess(String str) {
                Gson gson = new Gson();
                BaseResponse baseResponse = gson.fromJson(str, BaseResponse.class);
                String s = gson.toJson(baseResponse.payload);
                GetSingerDetailResponsePayload getSingerDetailResponsePayload = gson.fromJson(s, GetSingerDetailResponsePayload.class);
                result.onResult(new DataResult<>(getSingerDetailResponsePayload));
            }

            @Override
            public void onFailure(Throwable e, String errorMsg) {

            }
        };
        RequestUtils.getSingerDetail(context, headers, body, observer);
    }

    public void getTopListDetail(Context context, Map<String, String> headers, RequestBody body, DataResult.Result<TopListDetailResponsePayload> result) {
        ResponseObserver observer = new ResponseObserver<String>(context, false) {
            @Override
            public void onSuccess(String str) {
                Gson gson = new Gson();
                BaseResponse baseResponse = gson.fromJson(str, BaseResponse.class);
                String s = gson.toJson(baseResponse.payload);
                TopListDetailResponsePayload topListDetailResponsePayload = gson.fromJson(s, TopListDetailResponsePayload.class);
                result.onResult(new DataResult<>(topListDetailResponsePayload));
            }

            @Override
            public void onFailure(Throwable e, String errorMsg) {
            }
        };
        RequestUtils.getTopListDetail(context, headers, body, observer);
    }

    public void getSingerAlbum(Context context, Map<String, String> headers, RequestBody body, DataResult.Result<GetSingerAlbumResponsePayload> result) {
        ResponseObserver observer = new ResponseObserver<String>(context, false) {
            @Override
            public void onSuccess(String str) {
                Gson gson = new Gson();
                BaseResponse baseResponse = gson.fromJson(str, BaseResponse.class);
                String s = gson.toJson(baseResponse.payload);
                GetSingerAlbumResponsePayload getSingerAlbumResponsePayload = gson.fromJson(s, GetSingerAlbumResponsePayload.class);
                result.onResult(new DataResult<>(getSingerAlbumResponsePayload));
            }

            @Override
            public void onFailure(Throwable e, String errorMsg) {

            }
        };
        RequestUtils.getSingerAlbum(context, headers, body, observer);
    }

    public void getSongDetailBatch(Context context, Map<String, String> headers, RequestBody body, DataResult.Result<GetSongListDetailResponsePayload> result) {
        ResponseObserver observer = new ResponseObserver<String>(context, false) {
            @Override
            public void onSuccess(String str) {
                Gson gson = new Gson();
                BaseResponse baseResponse = gson.fromJson(str, BaseResponse.class);
                String s = gson.toJson(baseResponse.payload);
                GetSongListDetailResponsePayload getSongListDetailResponsePayload = gson.fromJson(s, GetSongListDetailResponsePayload.class);
                result.onResult(new DataResult<>(getSongListDetailResponsePayload));
            }

            @Override
            public void onFailure(Throwable e, String errorMsg) {

            }
        };
        RequestUtils.getSongDetailBatch(context, headers, body, observer);
    }

}
