package com.yt.yunxiaoweimusic.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.badge.BadgeDrawable;
import com.google.gson.Gson;
import com.kunminx.binding_recyclerview.adapter.SimpleDataBindingAdapter;
import com.yt.yunxiaoweimusic.R;
import com.yt.yunxiaoweimusic.data.api.RequestUtils;
import com.yt.yunxiaoweimusic.data.bean.AccessToken;
import com.yt.yunxiaoweimusic.data.bean.BaseResponse;
import com.yt.yunxiaoweimusic.data.bean.RequestHeader;
import com.yt.yunxiaoweimusic.data.bean.SongBean;
import com.yt.yunxiaoweimusic.data.bean.TestPayload;
import com.yt.yunxiaoweimusic.data.bean.TestRequrst;
import com.yt.yunxiaoweimusic.data.repository.ResponseObserver;
import com.yt.yunxiaoweimusic.databinding.AdapterSearchSongItemBinding;
import com.yt.yunxiaoweimusic.player.PlayerManager;
import com.yt.yunxiaoweimusic.utils.AssetsUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SongResultAdapter extends SimpleDataBindingAdapter<SongBean, AdapterSearchSongItemBinding> {

    public Context context;

    public SongResultAdapter(Context context) {
        super(context, R.layout.adapter_search_song_item, new DiffUtil.ItemCallback<SongBean>() {
            @Override
            public boolean areItemsTheSame(SongBean oldItem, SongBean newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areContentsTheSame(SongBean oldItem, SongBean newItem) {
                return oldItem.songId == newItem.songId;
            }
        });
        this.context = context;
    }

    @Override
    protected void onBindItem(AdapterSearchSongItemBinding binding, SongBean item, RecyclerView.ViewHolder holder) {
        binding.setVm(item);
        binding.iconAddPlaylist.setOnClickListener(v -> {
            PlayerManager.getInstance().addMusic(item);
        });
        binding.more.setOnClickListener(v -> showDialog(item));
    }

    public FavoriteListener favoriteListen;

    public void setFavoriteListener(FavoriteListener favoriteListen) {
        this.favoriteListen = favoriteListen;
    }

    public interface FavoriteListener {
        void addFavorite();

        void deleteFavorite();
    }

    public void aiDoMainAddOrDelete(SongBean songBean) {
        RequestHeader header = new Gson().fromJson(AssetsUtil.getDataFromJson(context, "header.json"), RequestHeader.class);
        TestPayload.Value value = new TestPayload.Value();
        value.setText(String.valueOf(songBean.songId));
        value.setOriginalText(String.valueOf(songBean.songId));

        TestPayload.Slot slot = new TestPayload.Slot();
        slot.setName("songid");
        slot.setType("sys.music.songid");
        List<TestPayload.Value> valueList = new ArrayList<>();
        valueList.add(value);
        slot.setValues(valueList);

        TestPayload.Semantic semantic = new TestPayload.Semantic();
        semantic.setDomain("music");
        semantic.setIntent(songBean.hot == 0 ? "add_favorite" : "delete_favorite");
        List<TestPayload.Slot> slotList = new ArrayList<>();
        slotList.add(slot);
        semantic.setSlots(slotList);

        TestPayload.SemanticMeta semanticMeta = new TestPayload.SemanticMeta();
        semanticMeta.setSemantic(semantic);

        TestPayload testPayload = new TestPayload();
        testPayload.setSemanticMeta(semanticMeta);

        TestRequrst baseRequrst = new TestRequrst(header, testPayload);

        String requestBody = new Gson().toJson(baseRequrst);
        Map<String, String> map = new HashMap<>();
        map.put("appkey", "b9b49a20178a11edb96eabbf4fec7f4f");
        map.put("dsn", "YUNTUTEST0708_dev001");
        map.put("Authorization", "Bearer " + AccessToken.getInstance().getAuthorization());
        RequestBody body = RequestBody.create(requestBody, MediaType.parse("application/json;charset=utf-8"));

        ResponseObserver observer = new ResponseObserver<String>(context, false) {
            @Override
            public void onSuccess(String str) {
                Gson gson = new Gson();
                BaseResponse baseResponse = gson.fromJson(str, BaseResponse.class);
                String s = gson.toJson(baseResponse.payload);
                TestPayload payload = gson.fromJson(s, TestPayload.class);
                if (payload.skill.speakTipsText.contains("已收藏")) {
                    if (favoriteListen != null)
                        favoriteListen.addFavorite();
                } else if (payload.skill.speakTipsText.contains("已取消收藏")) {
                    if (favoriteListen != null)
                        favoriteListen.deleteFavorite();
                }
            }

            @Override
            public void onFailure(Throwable e, String errorMsg) {
            }
        };
        RequestUtils.aiDoMain(context, map, body, observer);
    }

    public void showDialog(SongBean songBean) {
        PopupWindow popupWindow = new PopupWindow();
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(false);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_song_more, null);
        TextView title_song = view.findViewById(R.id.title_song);
        TextView title_singer = view.findViewById(R.id.title_singer);
        TextView add_to_playlist = view.findViewById(R.id.add_to_playlist);
        TextView add_to_favorite = view.findViewById(R.id.add_to_favorite);


        title_song.setText(songBean.songName);
        title_singer.setText(songBean.singerName);
        add_to_favorite.setText(songBean.hot == 0 ? "收藏" : "取消收藏");
        add_to_playlist.setOnClickListener(v -> {
            PlayerManager.getInstance().addMusic(songBean);
            popupWindow.dismiss();
        });
        add_to_favorite.setOnClickListener(v -> {
            aiDoMainAddOrDelete(songBean);
            popupWindow.dismiss();
        });
        view.findViewById(R.id.hide).setOnClickListener(v -> popupWindow.dismiss());
        popupWindow.setContentView(view);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }
}
