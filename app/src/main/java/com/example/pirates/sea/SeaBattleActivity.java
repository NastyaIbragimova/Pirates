package com.example.pirates.sea;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pirates.R;

public class SeaBattleActivity extends AppCompatActivity {

    private SeaView seaView;
    private Ship ship;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sea_battle);
        seaView = findViewById(R.id.sea_view);
        ship = seaView.getShip();
        seaView.setCountToWin(getIntent().getIntExtra("countEnemy", 10));
    }

    public void onClickFire(View view) {
        seaView.fillPlayerBombArray(ship.getY() + 140);
    }
}
