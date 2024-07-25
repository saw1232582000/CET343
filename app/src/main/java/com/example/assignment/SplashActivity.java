package com.example.assignment;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import com.airbnb.lottie.LottieAnimationView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Handle the splash screen transition.
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        // Load and play the Lottie animation.
        LottieAnimationView animationView = findViewById(R.id.animationView);
        animationView.setAnimation("bbuy_lottie.json"); // Ensure this file is in the assets folder
        animationView.playAnimation();

        // Set a listener to start the main activity once the animation is done.
        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                startMainActivity();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                startMainActivity();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
    }

    private void startMainActivity() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
