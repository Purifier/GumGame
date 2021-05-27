package com.gumer.gumgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class ItemEffect extends GameObject{
    protected GameSurface gameSurface;
    public ItemEffect(GameSurface gameSurface,Bitmap image, int rowCount, int colCount, int x, int y) {
        super(image, rowCount, colCount, x, y);
        this.gameSurface=gameSurface;
    }
    public abstract void update();
    public abstract void draw(Canvas canvas);
    public abstract boolean isFinish();
}
