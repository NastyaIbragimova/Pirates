package com.example.pirates;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void onClickStart(View view) {
        Intent intent = new Intent(StartActivity.this, GameFieldActivity.class);
        intent.putExtra("IsNewGame", true);
        startActivity(intent);

    }
}