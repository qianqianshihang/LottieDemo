package com.example.liuyuan.lottie_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FontViewActivity extends AppCompatActivity {
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.fontView)
    LottieFontViewGroup fontView;

    private final ViewTreeObserver.OnGlobalLayoutListener layoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            scrollView.fullScroll(View.FOCUS_DOWN);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font_view);
        ButterKnife.bind(this);
        fontView.getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);
    }
}
