package com.gumer.gumgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Saw extends GameObject{
    private int damage;
    Bitmap[] img=new Bitmap[2];
    int dmgsrcId=1;
    int cur=0,destx,desty;
    public Saw(Bitmap image, int x, int y, int damage, int destx,int desty) {
        super(image, 1, 2, x, y);
        img[0] = Bitmap.createScaledBitmap(createSubImageAt(0,0),100,100,false);
        img[1] = Bitmap.createScaledBitmap(createSubImageAt(0,1),100,100,false);
        this.destx=destx;
        this.desty=desty;
        this.damage=damage;
    }
    public void update(){
        cur=cur!=0?0:1;
        x-=(x-destx)/10;
        y-=(y-desty)/10;
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(img[cur],x,y,null);
    }
    public void setDestiny(int x,int y){
        destx=x;
        desty=y;
    }
    public Rect getRect(){
        return new Rect(x,y,x+getWidth(),y+getWidth());
    }

    public void dealDmg(Enemy enemy) {
        enemy.takeDamage(damage,dmgsrcId,500);
    }
}
