package com.gumer.gumgame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

public class NightBlade extends ItemEffect{
    boolean finish=false;
    int count=0;
    int damage;
    int facing;
    public NightBlade(GameSurface gameSurface, Bitmap image, int x, int y, int damage,int facing) {
        super(gameSurface, image, 1, 1, x, y);
        this.image= BitmapFactory.decodeResource(gameSurface.getResources(),R.drawable.effect_nightblade);
        this.damage=damage;
        this.facing=facing;
    }

    @Override
    public void update() {
        count++;
        if(count==10)finish=true;
    }

    @Override
    public void draw(Canvas canvas) {
        if(count>5){
            if(facing==1)
            canvas.drawBitmap(image,x+getWidth()/3,y,null);
            else canvas.drawBitmap(image,x-getWidth()/3,y,null);
            if(count==9)dealDmg();
        }
    }
    public boolean isFinish() {
        return finish;
    }
    public void dealDmg(){
        List<Enemy> currentTargets=new ArrayList<Enemy>();
        if(facing==1)
        currentTargets.addAll(gameSurface.checkForTargets(new Rect(x,y,x+getWidth(),y+getHeight())));
        else currentTargets.addAll(gameSurface.checkForTargets(new Rect(x-getWidth(),y,x,y+getHeight())));
        for(Enemy enemy:currentTargets){
            enemy.takeDamage(damage);
        }
    }
}
