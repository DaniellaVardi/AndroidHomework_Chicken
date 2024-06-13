package com.example.androidhomework.model;

import android.util.Log;

import com.example.androidhomework.Const;

import java.util.Random;

public class GameManager {
    private int[][] logicBoard;
    private Ship ship;
    private int lives;

    public GameManager(){
        logicBoard = new int[Const.HEIGHT][Const.LENGTH];
        ship = new Ship(1, 9);
        lives = 3;
    }

    public int[][] getLogicBoard() {
        return logicBoard;
    }

    public Ship getShip() {
        return ship;
    }

    public int getLives() {
        return lives;
    }

    public GameManager setLives(int lives) {
        this.lives = lives;
        return this;
    }

    public void addNewChicken(){
        Random rand = new Random();
        int place = rand.nextInt(Const.LENGTH);
        logicBoard[0][place] = 1;
    }

    public void moveDown(){
        for (int y = Const.HEIGHT - 1 ; y >= 0 ; y--){
            for (int x = 0; x < Const.LENGTH ; x++){
                if (y > 0 && logicBoard[y][x] == 0 && logicBoard[y-1][x] == 1){
                    logicBoard[y][x] = 1;
                    logicBoard[y-1][x] = 0;
                    if(checkCollision(ship.x,x,y))
                    {
                        collision();
                    }
                }

                if (y == Const.HEIGHT- 1 && logicBoard[y][x] == 1){
                    logicBoard[y][x] = 0;
                }

            }
        }
    }

    public void move(int dir){
        ship.move(dir);
    }

    private void collision() {
        //life, sound vibration

        //reset
        ship.setX(1);
        logicBoard = new int[Const.HEIGHT][Const.LENGTH];
        lives--;

    }

    public boolean checkCollision(int shipX, int chickenX, int chickenY){
        if(chickenY !=9) return false;
        if(shipX == chickenX) return true;
        return false;
    }

}
