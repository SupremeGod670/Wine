package com.example.wine.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wine.R;
import com.example.wine.ui.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    private ImageView splash;
    private Animation anim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        splash = findViewById(R.id.splash);
        anim = AnimationUtils.loadAnimation(this, R.anim.animacao_splash);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        splash.setAnimation(anim);
        anim.start();

    }
}
