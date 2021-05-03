package com.example.sublistsbystore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    public void nextPageButton(View view) {
        //FIXME: replace class name for 'how many stops' page --
        startActivity(new Intent(getApplicationContext(), StoreResultsActivity.class));
    }

    public void prevPageButton(View view) {
        //FIXME: replace class name for 'how many stops' page --
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}