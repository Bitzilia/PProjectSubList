package com.example.sublistsbystore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.slider.Slider;

public class StoresAndSavingsActivity extends AppCompatActivity {

    TextView costEstimate;
    TextView numStores;
    SeekBar slide;
    int storesNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores_and_savings);
        costEstimate = findViewById(R.id.CostEstimateView);
        numStores = findViewById(R.id.numStoresText);
        slide = findViewById(R.id.numStoresSeekBar);
        storesNum = slide.getProgress();
    }
}