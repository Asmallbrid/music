package com.yt.yunxiaoweimusic.domain.request;


import android.content.Context;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.kunminx.architecture.domain.message.MutableResult;
import com.kunminx.architecture.domain.message.Result;
import com.yt.yunxiaoweimusic.data.bean.AccessToken;
import com.yt.yunxiaoweimusic.data.bean.BaseRequrst;
import com.yt.yunxiaoweimusic.data.bean.RequestHeader;
import com.yt.yunxiaoweimusic.data.bean.RequestPayload;
import com.yt.yunxiaoweimusic.data.repository.DataRepository;
import com.yt.yunxiaoweimusic.data.response.DataResult;
import com.yt.yunxiaoweimusic.utils.AssetsUtil;
import com.yt.yunxiaoweimusic.utils.SignatureCalcUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;


public class BindAccountRequester extends ViewModel implements DefaultLifecycleObserver {


    private final MutableResult<DataResult<String>> tokenResult = new MutableResult<>();
    private final MutableResult<DataResult<String>> qrcodeResult = new MutableResult<>();

    public Result<DataResult<String>> getTokenResult() {
        return tokenResult;
    }


    public Result<DataResult<String>> getQrCodeResult() {
        return qrcodeResult;
    }

    public void bindAccount(Context context) {
        RequestHeader header = new Gson().fromJson(AssetsUtil.getDataFromJson(context, "header.json"), RequestHeader.class);
        RequestPayload payload = new Gson().fromJson(AssetsUtil.getDataFromJson(context, "payload.json"), RequestPayload.class);
        BaseRequrst baseRequrst = new BaseRequrst(header, payload);
        String requestBody = new Gson().toJson(baseRequrst);
        String time = "" + System.currentTimeMillis() / 1000;
        String signature = SignatureCalcUtil.getSignature(requestBody, time);
        Map<String, String> map = new HashMap<>();
        map.put("appkey", "b9b49a20178a11edb96eabbf4fec7f4f");
        map.put("dsn", "YUNTUTEST0708_dev001");
        map.put("timestamp", time);
        map.put("signature", signature);
        RequestBody body = RequestBody.create(requestBody, MediaType.parse("application/json;charset=utf-8"));
        DataRepository.getInstance().bindAccount(context, map, body, tokenResult::postValue);
    }

    public void getQrCode(Context context) {
        RequestHeader header = new Gson().fromJson(AssetsUtil.getDataFromJson(context, "header.json"), RequestHeader.class);
        RequestPayload payload = new RequestPayload.Builder().setQrCodeType("qqmusic").build();
        BaseRequrst baseRequrst = new BaseRequrst();
        baseRequrst.setHeader(header);
        baseRequrst.setPayload(payload);
        String requestBody = new Gson().toJson(baseRequrst);

        Map<String, String> map = new HashMap<>();
        map.put("appkey", "b9b49a20178a11edb96eabbf4fec7f4f");
        map.put("dsn", "YUNTUTEST0708_dev001");
        map.put("Authorization", "Bearer " + AccessToken.getInstance().getAuthorization());
        RequestBody body = RequestBody.create(requestBody, MediaType.parse("application/json;charset=utf-8"));
        DataRepository.getInstance().getQrCode(context, map, body, qrcodeResult::postValue);
    }
}
