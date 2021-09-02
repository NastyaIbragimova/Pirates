package com.example.pirates;

import android.widget.TextView;

public class Player {

    private int treasures;
    private int people;

    public Player(int treasures, int people) {
        this.treasures = treasures;
        this.people = people;
    }

    public int getTreasures() {
        return treasures;
    }

    public void setTreasures(int treasures, TextView textView) {
        textView.setText("Сундуки с сокровищами: " + treasures);
        this.treasures = treasures;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people, TextView textView) {
        textView.setText("Команда: " + people + " человек");
        this.people = people;
    }
}
