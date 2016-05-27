package com.cs165.domefavor.domefavor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.webkit.WebView;
import android.widget.FrameLayout;

public class AppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = View.inflate(this, R.layout.activity_app, null);
        setContentView(view);


        FrameLayout d = (FrameLayout) findViewById(R.id.test);
        WebView wView = new WebView(this);
        wView.setInitialScale(100);
        wView.loadUrl("file:///android_asset/start.jpg");


        d.addView(wView);
        ScaleAnimation scale = new ScaleAnimation(1.0f, 1.07f,1.0f,1.07f);
        scale.setDuration(3000);
        scale.setFillAfter(true);
        view.startAnimation(scale);
        scale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }

        });
    }
}
