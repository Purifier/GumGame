package com.gumer.gumgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class TimerDebuff extends EnemyBuff{
    long lastCalc=-1;
    int remaining;
    int sum;
    TimerDebuff(Enemy enemy, Bitmap image) {
        super(enemy,image);
        this.image=Bitmap.createScaledBitmap(image,image.getWidth()/2,image.getHeight()/2,false);
        remaining=5000;
        eventType=EnemyBuff.TAKINGDMG;
    }

    @Override
    public void evaluate() {
        long now=System.nanoTime();
        if(lastCalc==-1){
            lastCalc=now;
        }
        int deltatime=(int)(now-lastCalc)/1000000;
        if(remaining<=0){
            enemy.takeDamage(sum/100);
            isFinished=true;
        }
        remaining-=deltatime;
        sum+=deltatime;

        lastCalc=now;
    }

    @Override
    public void onEvent() {
        remaining+=200;
    }
    @Override
    public void draw(Canvas canvas){
        canvas.drawBitmap(image,enemy.x+enemy.getWidth()/2-image.getWidth(),enemy.y-50,null);
    }

}
