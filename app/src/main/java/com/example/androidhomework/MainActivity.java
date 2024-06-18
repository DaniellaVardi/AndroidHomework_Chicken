package com.example.androidhomework;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;


import com.example.androidhomework.model.GameManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private AppCompatImageView[][] board;
    private AppCompatImageView[] shipPlacement;
    private AppCompatImageView[] ship_IMG_hearts;
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

        ship_IMG_hearts = new AppCompatImageView[] {
                findViewById(R.id.trivia_IMG_heart1),
                findViewById(R.id.trivia_IMG_heart2),
                findViewById(R.id.trivia_IMG_heart3),
                findViewById(R.id.trivia_IMG_heart4),
        };

        shipPlacement = new AppCompatImageView[]{
                findViewById(R.id.ship_0),
                findViewById(R.id.ship_1),
                findViewById(R.id.ship_2),
        };

        left_btn = findViewById(R.id.btn_left);
        right_btn = findViewById(R.id.btn_right);

    }

    private void tick() {
        Log.d("pttt", "tick");
        if (count%3 == 0){
            gameManager.addNewChicken();
        }

        moveDown();

        if (gameManager.getLives() < 1) {
            stop();
            resetGame();
        } else {
            drawBoard(gameManager.getLogicBoard());
        }

        updateLivesUI();

        count++;

    }

    private CountDownTimer countDownTimer;

    private void stop() {
        countDownTimer.cancel();
    }

    private void start() {
        countDownTimer = new CountDownTimer(1_000_000_000, DELAY) {
            public void onTick(long millisUntilFinished) {
                tick();
            }

            public void onFinish() {}
        };
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
        updateShipPosition();
    }


    //TODO - need to check with guy if i need to move the function
    public void moveDown() {
        for (int y = Const.HEIGHT - 1; y >= 0; y--) {
            for (int x = 0; x < Const.LENGTH; x++) {
                // Move chicken down if the current cell is empty and the cell above contains a chicken
                if (y > 0 && gameManager.logicBoard[y][x] == 0 && gameManager.logicBoard[y - 1][x] == 1) {
                    gameManager.logicBoard[y][x] = 1;
                    gameManager.logicBoard[y - 1][x] = 0;

                    // Check for collision with the ship
                    if (y == gameManager.ship.getY() && x == gameManager.ship.getX()) {
                        collision();
                    }
                }
                // Clear chicken if it has reached the bottom row
                if (y == Const.HEIGHT - 1 && gameManager.logicBoard[y][x] == 1) {
                    gameManager.logicBoard[y][x] = 0;
                }
            }
        }
    }

    private void move(int dir) {
        int currentX = gameManager.getShip().getX();
        int newX = currentX + dir;
        if (newX >= 0 && newX < Const.LENGTH) { // Ensure movement is within the array of size 3
            clearShipPosition(); // Clear the current position of the ship
            gameManager.move(dir);
            updateShipPosition();
        }
    }

    //TODO - need to check with guy if i need to move the function
    private void collision() {
        gameManager.ship.setX(1);
        gameManager.logicBoard = new int[Const.HEIGHT][Const.LENGTH];
        Toast.makeText(this, "Collision", Toast.LENGTH_SHORT).show();
        vibrate();
        gameManager.decreaseLive();
    }

    private void resetGame() {
        clearBoard();
        gameManager = new GameManager();
        count = 0;
        updateLivesUI();
        drawBoard(gameManager.getLogicBoard());
        start();
    }

    private void clearBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j].setBackgroundResource(R.color.trans);
            }
        }
        clearShipPosition();
    }

    private void updateShipPosition() {
        clearShipPosition(); // Clear any previous ship position
        shipPlacement[gameManager.getShip().getX()].setBackgroundResource(R.drawable.rocket);
    }

    private void clearShipPosition() {

        for (AppCompatImageView appCompatImageView : shipPlacement) {
            appCompatImageView.setBackgroundResource(R.color.trans);
        }
    }

    private void updateLivesUI() {
        int SZ = ship_IMG_hearts.length;

        for (AppCompatImageView shipImgHeart : ship_IMG_hearts) {
            shipImgHeart.setVisibility(View.VISIBLE);
        }

        for (int i = 0; i < SZ - gameManager.getLives(); i++) {
            ship_IMG_hearts[i].setVisibility(View.INVISIBLE);
        }
    }

    public void vibrate() {
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }
}