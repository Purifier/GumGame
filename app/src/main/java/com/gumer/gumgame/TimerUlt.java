package com.gumer.gumgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class TimerUlt extends Projectile{

    public TimerUlt(Bitmap image, int x, int y, int mx) {
        super(image, 1, 1, x, y, mx, 0);
        collidesrc=5;
    }
    @Override
    public void draw(Canvas canvas){

        canvas.drawBitmap(image,x,y,null);
    }
    @Override
    public void onCollide(Enemy enemy) {
        if(enemy.collide(collidesrc)) {
            enemy.buffs.add(new TimerDebuff(enemy, image));
            isFinished=true;
        }
    }
}
