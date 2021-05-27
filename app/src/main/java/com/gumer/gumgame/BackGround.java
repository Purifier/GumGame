package com.gumer.gumgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class BackGround extends GameObject{
    private GameSurface gameSurface;

    public BackGround(GameSurface gameSurface, Bitmap image, int width, int height) {
        super(image, 1, 1, 0,0);
        this.image=Bitmap.createScaledBitmap(image,width,height,false);
        this.gameSurface=gameSurface;
    }
    public void draw(Canvas canvas)  {
            Bitmap bitmap = this.image;
            canvas.drawBitmap(bitmap,x, y, null);
    }
    public void update(){

    }
}
