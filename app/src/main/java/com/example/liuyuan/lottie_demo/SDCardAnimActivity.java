package com.example.liuyuan.lottie_demo;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.OnCompositionLoadedListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liuyuan on 17/5/15.
 */
public class SDCardAnimActivity extends AppCompatActivity {
    private static final int RC_FILE = 1338;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets_anim);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.animChange)
    public void onViewClicked() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select a JSON file"), RC_FILE);
        } catch (ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data==null){
            return;
        }
        Uri uri = data.getData();
        InputStream fis;

        try {
            switch (uri.getScheme()) {
                case "file":
                    fis = new FileInputStream(uri.getPath());
                    break;
                case "content":
                    fis = this.getContentResolver().openInputStream(uri);
                    break;
                default:
                    onLoadError();
                    return;
            }
        } catch (FileNotFoundException e) {
            onLoadError();
            return;
        }
        LottieComposition.Factory
                .fromInputStream(this, fis, new OnCompositionLoadedListener() {
                    @Override
                    public void onCompositionLoaded(LottieComposition composition) {
                        lottieAnimationView.setComposition(composition);
                        tvScaleNum.setText(String.format(Locale.US, "%.2f", lottieAnimationView.getScale()));
                        seekBarScale.setProgress((int) (lottieAnimationView.getScale() * 50f));
                    }
                });
    }

    private void onLoadError() {
        Snackbar.make(rootView, "Failed to load animation", Snackbar.LENGTH_LONG).show();
    }
}
