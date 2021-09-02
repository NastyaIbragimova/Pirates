package com.example.pirates;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pirates.sea.*;

import java.util.ArrayList;
import java.util.Random;

public class GameFieldActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private final int SIDE = 6;
    private ArrayList<Cell> gameField = new ArrayList<>();
    private final String[] types = new String[]{"Pirates", "Hidden treasures", "Sea battle", "Lose"};
    private final Random random = new Random();
    private Player player;
    private TextView tvCrew, tvTreasures, tvBattle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_field);
        player = new Player(1, 10);
        init();
    }

    private void init() {
        tvTreasures = findViewById(R.id.gold);
        tvCrew = findViewById(R.id.crew);
        tvBattle = findViewById(R.id.battle);
        gridLayout = findViewById(R.id.grid);
        gridLayout.setColumnCount(SIDE);
        setField();
    }

    private void setField() {
        int displayHeight = this.getResources().getDisplayMetrics().heightPixels - 50;
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(R.drawable.question);
                imageView.setBackground(getDrawable(R.drawable.cell));
                int cellSize = (displayHeight / SIDE);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(cellSize, cellSize);
                imageView.setLayoutParams(params);
                imageView.setPadding(10, 10, 10, 10);
                Cell cell;
                int q;
                if (random.nextInt(4) != 1) {
                    q = random.nextInt(3);
                } else {
                    q = random.nextInt(4);
                }
                cell = new Cell(x, y, types[q], imageView);
                gameField.add(cell);
                setOnClick(cell);
                gridLayout.addView(cell.imageView);
            }
        }
    }

    private void setOnClick(Cell cell) {
        cell.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                tvBattle.setText("");
                if (!cell.isOpen) {
                    cell.isOpen = true;
                    switch (cell.type) {
                        case "Pirates":
                            cell.imageView.setImageResource(R.drawable.pirate);
                            intent = new Intent(GameFieldActivity.this, PiratesActivity.class);
                            startActivityForResult(intent, 1);
                            break;
                        case "Hidden treasures":
                            cell.imageView.setImageResource(R.drawable.map);
                            intent = new Intent(GameFieldActivity.this, TreasureActivity.class);
                            startActivityForResult(intent, 1);
                            break;
                        case "Sea battle":
                            cell.imageView.setImageResource(R.drawable.sea_battle);
                            intent = new Intent(GameFieldActivity.this, SeaBattleActivity.class);
                            intent.putExtra("countEnemy", player.getPeople());
                            startActivityForResult(intent, 1);
                            break;
                        case "Lose":
                            cell.imageView.setImageResource(R.drawable.lose);
                            intent = new Intent(GameFieldActivity.this, LoseActivity.class);
                            intent.putExtra("treasureResult", player.getTreasures());
                            intent.putExtra("peopleResult", player.getPeople());
                            startActivity(intent);
                            break;
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int people = data.getIntExtra("Pirates result", 0) + player.getPeople();
        int treasure = data.getIntExtra("Treasure result", 0) + player.getTreasures();
        player.setTreasures(treasure, tvTreasures);
        if (data.hasExtra("isWin")) {
            if (data.getBooleanExtra("isWin", false)) {
                tvBattle.setText("Вы победили и получили хорошую добычу!");
                treasure = player.getTreasures() + 5;
            } else {
                tvBattle.setText("Вы проиграли и потеряли золото");
                treasure = 0;
            }
        }
        player.setPeople(people, tvCrew);
        player.setTreasures(treasure, tvTreasures);
    }
}
