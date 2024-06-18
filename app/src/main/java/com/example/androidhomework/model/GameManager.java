package com.example.androidhomework.model;

import com.example.androidhomework.Const;

import java.util.Random;

public class GameManager {
    public int[][] logicBoard;
    public final Ship ship;
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

    public void decreaseLive() {
        lives--;
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

    public void move(int dir){
        ship.move(dir);
    }

}
