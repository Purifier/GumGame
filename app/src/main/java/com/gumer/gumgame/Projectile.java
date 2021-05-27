package com.gumer.gumgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public abstract class Projectile extends GameObject{
    long lastTime=-1;
    int collidesrc;
    int movingX;
    int movingY;
    boolean isFinished=false;
    public Projectile(Bitmap image, int rowCount, int colCount, int x, int y, int mx,int my) {
        super(image, rowCount, colCount, x, y);
        this.movingX=mx;
        this.movingY=my;
    }
    public void update(){
//        long now=System.nanoTime();
//        if(lastTime==-1)lastTime=System.nanoTime();
//        int deltatime=(int)(lastTime-now);
        x+=movingX;
        y+=movingY;

    }
    public Rect getRect(){
        return new Rect(x,y,x+getWidth(),y+getHeight());
    }
    public abstract void onCollide(Enemy enemy);
    public abstract void draw(Canvas canvas);

}
