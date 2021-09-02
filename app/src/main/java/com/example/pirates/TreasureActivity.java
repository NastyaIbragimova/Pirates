package com.example.pirates;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class TreasureActivity extends AppCompatActivity {

    private Random random = new Random();
    private int result;
    private TextView tvResult, tvBack;
    private ImageView ivPicture;
    private ImageButton ibTreasure1, ibTreasure2, ibTreasure3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treasures);
        ivPicture = findViewById(R.id.treasure_picture);
        tvResult = findViewById(R.id.treasure_result);
        result = random.nextInt(3);
        ibTreasure1 = findViewById(R.id.ib_treasure1);
        ibTreasure2 = findViewById(R.id.ib_treasure2);
        ibTreasure3 = findViewById(R.id.ib_treasure3);
        tvBack = findViewById(R.id.button_back);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TreasureActivity.this, GameFieldActivity.class);
                intent.putExtra("Treasure result", result);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public void onClickTreasure(View view) {
        switch (result) {
            case 0:
                ivPicture.setImageResource(R.drawable.treasure_back1);
                tvResult.setText("Вы ничего не нашли");
                break;
            case 1:
                ivPicture.setImageResource(R.drawable.treasure_back2);
                tvResult.setText("Вы нашли один сундук с сокровищами");
                break;
            case 2:
                ivPicture.setImageResource(R.drawable.treasure_back3);
                tvResult.setText("Вы нашли два сундука с сокровищами");
                break;
        }
        tvBack.setVisibility(View.VISIBLE);
        ibTreasure1.setVisibility(View.INVISIBLE);
        ibTreasure2.setVisibility(View.INVISIBLE);
        ibTreasure3.setVisibility(View.INVISIBLE);

    }
}

