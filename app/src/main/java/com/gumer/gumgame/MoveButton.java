package com.gumer.gumgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class MoveButton extends GameObject{
    private GameSurface gameSurface;
    private Bitmap act_image;
    private boolean isAct;
    private int pId;
    public MoveButton(GameSurface surface,Bitmap image,Bitmap act_image, int x, int y) {

        super(image, 1, 1, x, y);
        this.isAct=false;
        this.act_image=act_image;
        this.gameSurface=surface;
    }

    public void draw(Canvas canvas){
        Bitmap bitmap;
        if (!isAct){
            bitmap = this.image;
        }
        else{
            bitmap = this.act_image;
        }
        canvas.drawBitmap(bitmap,x,y,null);
    }

    public void update(){

    }

    public void setpId(int pId){
        this.pId=pId;

    }
    public int getpId(){
        return this.pId;
    }
    public void setAct(boolean act){
        this.isAct=act;
    }
}
