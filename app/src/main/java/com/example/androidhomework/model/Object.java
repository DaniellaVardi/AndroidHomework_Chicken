package com.example.androidhomework.model;

public abstract class Object {
    protected int x;
    protected int y;
    protected int image;

    public Object(int x, int y) {
        this.x = x;
        this.y = y;
        this.image = -1;
    }

    public int getX() {
        return x;
    }

    public Object setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public Object setY(int y) {
        this.y = y;
        return this;
    }

    public int getImage() {
        return image;
    }

    public Object setImage(int image) {
        this.image = image;
        return this;
    }
}
