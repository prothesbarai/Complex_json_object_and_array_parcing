package com.aspprothes.complex_json_nested_objects;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {
    private LottieAnimationView animationView;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setNavigationBarColor(getResources().getColor(R.color.sky));
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.sky));
        setContentView(R.layout.activity_main);

        animationView = findViewById(R.id.animationView);
        listView = findViewById(R.id.listView);

    }
}