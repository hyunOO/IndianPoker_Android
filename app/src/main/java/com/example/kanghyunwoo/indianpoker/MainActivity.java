package com.example.kanghyunwoo.indianpoker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // When player press "GAME START" button, function start GameAcitivity
    public void gameStart(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    // TODO : bluetooth connection button "FIND PLAYERS"

    /**
     * TODO : function 'sendStartCode()'
     * function sends 0 to other player to start game
     */
}
