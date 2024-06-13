package com.example.androidhomework;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidhomework.model.GameManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {


    private AppCompatImageView[][] board;
    private AppCompatImageView[] shipPlacement;
    private int count = 0;

    private GameManager gameManager;
    private final int DELAY = 1000;

    private FloatingActionButton left_btn;
    private FloatingActionButton right_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        left_btn.setOnClickListener(v -> move(-1));
        right_btn.setOnClickListener(v -> move(1));


        gameManager = new GameManager();
        shipPlacement[gameManager.getShip().getX()].setImageResource(R.drawable.rocket);
        start();

    }

    public void findViews(){

        board = new AppCompatImageView[][]{
                {findViewById(R.id.space_00),findViewById(R.id.space_01),findViewById(R.id.space_02)},
                {findViewById(R.id.space_10),findViewById(R.id.space_11),findViewById(R.id.space_12)},
                {findViewById(R.id.space_20),findViewById(R.id.space_21),findViewById(R.id.space_22)},
                {findViewById(R.id.space_30),findViewById(R.id.space_31),findViewById(R.id.space_32)},
                {findViewById(R.id.space_40),findViewById(R.id.space_41),findViewById(R.id.space_42)},
                {findViewById(R.id.space_50),findViewById(R.id.space_51),findViewById(R.id.space_52)},
                {findViewById(R.id.space_60),findViewById(R.id.space_61),findViewById(R.id.space_62)},
                {findViewById(R.id.space_70),findViewById(R.id.space_71),findViewById(R.id.space_72)},
                {findViewById(R.id.space_80),findViewById(R.id.space_81),findViewById(R.id.space_82)},
                {findViewById(R.id.space_90),findViewById(R.id.space_91),findViewById(R.id.space_92)}
        };

        shipPlacement = new AppCompatImageView[]{
                findViewById(R.id.chicken_0),
                findViewById(R.id.chicken_1),
                findViewById(R.id.chicken_2),
        };

        left_btn = findViewById(R.id.btn_left);
        right_btn = findViewById(R.id.btn_right);

    }

    private void tick() {

        if (count%3 == 0){
            gameManager.addNewChicken();
        }

        gameManager.moveDown();

        drawBoard(gameManager.getLogicBoard());

        count++;

    }

    private final CountDownTimer countDownTimer = new CountDownTimer(1_000_000_000, DELAY) {
        public void onTick(long millisUntilFinished) {
            tick();
        }

        public void onFinish() {}

    };

    private void stop() {
        countDownTimer.cancel();
    }

    private void start() {
        countDownTimer.start();
    }

    private void drawBoard(int[][] logicBoard) {
        for (int i = 0; i < logicBoard.length; i++) {
            for (int j = 0; j < logicBoard[i].length; j++) {
                if (logicBoard[i][j] == 1) {
                    board[i][j].setBackgroundResource(R.drawable.ic_chicken);
                } else if (logicBoard[i][j] == 0) {
                    board[i][j].setBackgroundResource(R.color.trans);
                }
            }
        }
    }
    private void move(int dir) {
        int currentX = gameManager.getShip().getX();

        // Clear the rocket image from the previous position
        shipPlacement[currentX].setImageResource(android.R.color.transparent);

        // Move the ship
        gameManager.move(dir);

        // Get the new position of the ship
        int newX = gameManager.getShip().getX();

        // Draw the rocket image at the new position
        shipPlacement[newX].setImageResource(R.drawable.rocket);
    }

}