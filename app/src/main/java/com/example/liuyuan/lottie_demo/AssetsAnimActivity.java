package com.example.liuyuan.lottie_demo;

import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.OnCompositionLoadedListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liuyuan on 17/5/15.
 */
public class AssetsAnimActivity extends AppCompatActivity {

    @BindView(R.id.loadAssets)
    LottieAnimationView lottieAnimationView;
    @BindView(R.id.animChange)
    Button animChange;
    @BindView(R.id.tvProgress)
    TextView tvProgress;
    @BindView(R.id.sbScale)
    SeekBar seekBarScale;
    @BindView(R.id.tv_scaleNum)
    TextView tvScaleNum;
    private String currentFile;
    private StringBuilder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets_anim);
        ButterKnife.bind(this);
        builder = new StringBuilder("进度：");
        lottieAnimationView.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                builder.delete(0, builder.length());
                tvProgress.setText(builder.append("进度：").append((int) (animation.getAnimatedFraction() * 100)).toString());
            }
        });

        seekBarScale.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                lottieAnimationView.setScale(progress / 50f);
                tvScaleNum.setText(String.format(Locale.US, "%.2f", lottieAnimationView.getScale()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @OnClick(R.id.animChange)
    public void onViewClicked() {
        try {
            List<String> files = AssetUtils.getJsonAssets(this, "");
            if (files.size() <= 0) return;

            if (TextUtils.isEmpty(currentFile)) {
                currentFile = files.get(0);
            } else {
                int i = files.indexOf(currentFile);
                int currentIndex;
                if (i == files.size() - 1) {
                    currentIndex = 0;
                } else {
                    currentIndex = i + 1;
                }
                currentFile = files.get(currentIndex);
            }

            lottieAnimationView.setImageAssetsFolder(currentFile);
            LottieComposition.Factory.fromAssetFileName(this, currentFile, new OnCompositionLoadedListener() {
                @Override
                public void onCompositionLoaded(LottieComposition lottieComposition) {
                    lottieAnimationView.setComposition(lottieComposition);
                    tvScaleNum.setText(String.format(Locale.US, "%.2f", lottieAnimationView.getScale()));
                    seekBarScale.setProgress((int) (lottieAnimationView.getScale() * 50f));
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
