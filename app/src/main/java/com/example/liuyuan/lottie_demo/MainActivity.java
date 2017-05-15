package com.example.liuyuan.lottie_demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.FromAssets)
    Button FromAssets;
    @BindView(R.id.FromSDCard)
    Button FromSDCard;
    @BindView(R.id.FromNetWork)
    Button FromNetWork;
    @BindView(R.id.FontView)
    Button FontView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.FromAssets, R.id.FromSDCard, R.id.FromNetWork, R.id.FontView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.FromAssets:
                goToActivity(AssetsAnimActivity.class);
                break;
            case R.id.FromSDCard:
                goToActivity(SDCardAnimActivity.class);
                break;
            case R.id.FromNetWork:
                goToActivity(NetWorkAnimActivity.class);
                break;
            case R.id.FontView:
                goToActivity(FontViewActivity.class);
                break;
        }
    }


    public void goToActivity(Class<? extends Activity> aClass) {
        Intent intent = new Intent(this, aClass);
        startActivity(intent);
    }


}
