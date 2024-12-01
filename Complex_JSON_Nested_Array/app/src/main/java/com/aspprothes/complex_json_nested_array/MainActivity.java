package com.aspprothes.complex_json_nested_array;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {
    private long lastBackPressedBtnTime = 0;
    private static final long backPressed_btn_time = 2000;
    private LottieAnimationView animationView;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setNavigationBarColor(getResources().getColor(R.color.sky));
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.sky));
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        animationView = findViewById(R.id.animationView);

    }
    // =============================== On Create Method End Here ===========================================






    // ===========================================On BackPressed Method start Here ===================================================
    @Override
    public void onBackPressed() {
        if (isTaskRoot()){
            long getCurrentTime = System.currentTimeMillis();
            if (getCurrentTime - lastBackPressedBtnTime < backPressed_btn_time){
                finish();
            }else{
                lastBackPressedBtnTime = getCurrentTime;
                Toast.makeText(this, "Tap again to exit", Toast.LENGTH_SHORT).show();
            }
        }else{
            super.onBackPressed();
        }
    }
    // ===========================================On BackPressed Method End Here =====================================================
}