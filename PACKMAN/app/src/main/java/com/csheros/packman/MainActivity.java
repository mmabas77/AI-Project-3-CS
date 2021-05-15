package com.csheros.packman;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.csheros.packman.view.StarterFragment;

public class MainActivity extends AppCompatActivity {

    private static boolean InForeGround;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.main_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, StarterFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.InForeGround = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.InForeGround = true;
    }

    public static boolean isInForeGround() {
        return InForeGround;
    }
}