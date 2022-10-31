package com.yt.yunxiaoweimusic.data.repository;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.yt.yunxiaoweimusic.data.bean.BaseResponse;
import com.yt.yunxiaoweimusic.utils.RxExceptionUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<BaseResponse> {
    private static final String TAG = "BaseObserver";
    private static Gson gson = new Gson();

    @Override
    public void onNext(BaseResponse response) {
        if (response.header != null) {
            String jsonString = gson.toJson(response);
            if (TextUtils.isEmpty(jsonString) || TextUtils.equals("null", jsonString)) {
                onSuccess(null);
                return;
            }
            Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            if (jsonString.startsWith("[")) {
                jsonString = changeToJsonArrayList(jsonString);
            }
            onSuccess(jsonString);
        } else {
            onFailure(new Throwable("" + response.header.code), response.header.message);
        }
    }

    private String changeToJsonArrayList(String oriStr) {
        return "{\"list\":" + oriStr + "}";
    }

    @Override
    public void onError(Throwable e) {
        onFailure(e, RxExceptionUtil.exceptionHandler(e));
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Disposable d) {
    }

    public abstract void onSuccess(String result);

    public abstract void onFailure(Throwable e, String errorMsg);


    public static class StringDefaultAdapter implements JsonSerializer<String>, JsonDeserializer<String> {
        @Override
        public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            try {
                if (null == json.getAsString() || json.getAsString().equals("")) {//定义为long类型,如果后台返回""或者null,则返回0
                    return "";
                }
            } catch (Exception ignore) {
            }
            try {
                return json.getAsString();
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src);
        }
    }
}
