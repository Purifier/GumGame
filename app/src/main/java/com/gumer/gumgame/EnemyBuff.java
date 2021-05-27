package com.gumer.gumgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class EnemyBuff {
    int eventType;
    Bitmap image;
    public final static int TAKINGDMG=0;
    Enemy enemy;
    boolean isFinished=false;
    EnemyBuff(Enemy enemy,Bitmap image){
        this.enemy=enemy;
        this.image=image;
    }
    public abstract void evaluate();
    public abstract void onEvent();
    public abstract void draw(Canvas canvas);
}