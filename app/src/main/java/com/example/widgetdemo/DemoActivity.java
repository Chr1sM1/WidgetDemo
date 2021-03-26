package com.example.widgetdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.example.widgetdemo.databinding.ActivityInfoBinding;

public class DemoActivity extends AppCompatActivity {
    private ActivityInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
    }
}