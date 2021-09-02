package com.example.pirates;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class PiratesActivity extends AppCompatActivity {

    private Random random = new Random();
    private int result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pirates);
        ImageView ivPicture = findViewById(R.id.pirates_picture);
        TextView tvResult = findViewById(R.id.pirates_result);
        result = random.nextInt(3);
        switch (result) {
            case 0:
                ivPicture.setImageResource(R.drawable.pirates_back2);
                tvResult.setText("Никто не присоединился к вашей команде");
                break;
            case 1:
                ivPicture.setImageResource(R.drawable.pirates_back3);
                tvResult.setText("К вашей команде присоединяется 1 человек");
                break;
            case 2:
                ivPicture.setImageResource(R.drawable.pirates_back1);
                tvResult.setText("К вашей команде присоединяются 2 человека");
                break;
        }
        TextView back = findViewById(R.id.button_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PiratesActivity.this, GameFieldActivity.class);
                intent.putExtra("Pirates result", result);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
