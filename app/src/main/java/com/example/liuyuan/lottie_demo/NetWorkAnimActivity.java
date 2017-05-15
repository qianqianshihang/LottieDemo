package com.example.liuyuan.lottie_demo;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.OnCompositionLoadedListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liuyuan on 17/5/15.
 */
public class NetWorkAnimActivity extends AppCompatActivity {
    @BindView(R.id.animChange)
    Button animChange;
    @BindView(R.id.sbScale)
    SeekBar seekBarScale;
    @BindView(R.id.tv_scaleNum)
    TextView tvScaleNum;
    @BindView(R.id.tvProgress)
    TextView tvProgress;
    @BindView(R.id.loadAssets)
    LottieAnimationView lottieAnimationView;
    @BindView(R.id.rootView)
    LinearLayout rootView;
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets_anim);
        ButterKnife.bind(this);
    }

    public void requestNetwork(String url) {
        Request request;
        try {
            request = new Request.Builder()
                    .url(url)
                    .build();
        } catch (IllegalArgumentException e) {
            onLoadError();
            return;
        }


        if (client == null) {
            client = new OkHttpClient();
        }
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onLoadError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    onLoadError();
                }
                Snackbar.make(rootView, "Request Successful", Snackbar.LENGTH_LONG).show();
                try {
                    JSONObject json = new JSONObject(response.body().string());
                    LottieComposition.Factory
                            .fromJson(getResources(), json, new OnCompositionLoadedListener() {
                                @Override
                                public void onCompositionLoaded(LottieComposition composition) {
                                    lottieAnimationView.setComposition(composition);
                                    tvScaleNum.setText(String.format(Locale.US, "%.2f", lottieAnimationView.getScale()));
                                    seekBarScale.setProgress((int) (lottieAnimationView.getScale() * 50f));
                                }
                            });
                } catch (JSONException e) {
                    onLoadError();
                }
            }
        });
    }

    private void onLoadError() {
        Snackbar.make(rootView, "Failed to load animation", Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.animChange)
    public void onViewClicked() {
        requestNetwork("http://www.lottiefiles.com/storage/datafiles/lwjM2vQf6cSbmAu/data.json");
    }
}
