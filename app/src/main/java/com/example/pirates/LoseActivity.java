package com.example.pirates;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose);
        TextView back = findViewById(R.id.button_back);
        Intent intent = getIntent();
        TextView treasureResult = findViewById(R.id.pirates_treasure_result);
        TextView peopleResult = findViewById(R.id.pirates_people_result);
        treasureResult.setText("Сундуки с сокровищами: " + intent.getIntExtra("treasureResult", 0));
        peopleResult.setText("Команда: " + + intent.getIntExtra("peopleResult", 0) + " человек");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoseActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}