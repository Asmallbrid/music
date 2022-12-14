package com.yt.yunxiaoweimusic.data.repository;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import io.reactivex.disposables.Disposable;

public abstract class ResponseObserver<T> extends BaseObserver<T> {
    private boolean mShowDialog;
    private ProgressDialog dialog;
    private Context mContext;
    private Disposable d;

    public ResponseObserver(Context context, Boolean showDialog) {
        mContext = context;
        mShowDialog = showDialog;
    }

    public ResponseObserver(Context context) {
        this(context,true);
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
        if (mContext == null){
            if (d.isDisposed()) {
                d.dispose();
            }
            return;
        }
        if (!isConnected(mContext)) {
            //Toast.makeText(mContext,"未连接网络",Toast.LENGTH_SHORT).show();
            if (d.isDisposed()) {
                d.dispose();
            }
        } else {
            if (dialog == null && mShowDialog == true) {
                dialog = new ProgressDialog(mContext);
                dialog.setMessage("正在加载中");
                dialog.show();
            }
        }
    }
    @Override
    public void onError(Throwable e) {
        if (d.isDisposed()) {
            d.dispose();
        }
        hidDialog();
        super.onError(e);
    }

    @Override
    public void onComplete() {
        if (d.isDisposed()) {
            d.dispose();
        }
        hidDialog();
        super.onComplete();
    }


    public void hidDialog() {
        if (dialog != null && mShowDialog == true)
            dialog.dismiss();
        dialog = null;
    }
    /**
     * 是否有网络连接，不管是wifi还是数据流量
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null)
        {
            return false;
        }
        boolean available = info.isAvailable();
        return available;
    }

    /**
     * 取消订阅
     */
    public void cancleRequest(){
        if (d!=null&&d.isDisposed()) {
            d.dispose();
            hidDialog();
        }
    }
}

