package com.divy.prakash.paathsala.bahikhata.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class SplashScreen extends AppCompatActivity {
    public ImageView splashLogoImView;
    public Animation splashFadeinAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Used To Set Color Of Status Bar If Sdk Is > Lollipop */
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        /* This Method Is Used So That Splash Screen Activity Can Cover The Entire Screen */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        /* Component Intialization */
        splashLogoImView = findViewById(R.id.splash_logo_Im_View);

        /* For Give Animation To Imageview */
        splashFadeinAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_screen_fadein_animation);
        splashLogoImView.startAnimation(splashFadeinAnimation);
        int SPLASH_SCREEN_TIME_OUT = 4000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Intent Is Used To Switch From Splash Activity To Login Activity */
                Intent i = new Intent(getApplicationContext(), LogINScreen.class);
                /* Invoke The Second Activity */
                startActivity(i);
                /* The Current Activity Will Get Finished */
                finish();

            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}
