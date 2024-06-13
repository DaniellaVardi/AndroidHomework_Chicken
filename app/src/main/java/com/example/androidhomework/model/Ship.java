package com.example.androidhomework.model;

import com.example.androidhomework.Const;
import com.example.androidhomework.R;

public class Ship extends Object {

    public Ship(int x, int y) {
        super(x, y);
        image = R.drawable.rocket;
    }

    public void move(int dir){
        x += dir;
        if(x<0) x=0;
        if(x> Const.LENGTH-1) x = Const.LENGTH-1;
    }
}
