package com.csheros.packman;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class Splash_Activity extends AppCompatActivity {

    // Don't convert to local var (GC will remove it)
    private static MediaPlayer introMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        introMediaPlayer = MediaPlayer.create(this, R.raw.intro);
        introMediaPlayer.setOnCompletionListener(mp -> {
            mp.release();
            Intent Start = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(Start);
            finish();
        });
        introMediaPlayer.start();
    }
}