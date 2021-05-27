package com.gumer.gumgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Timer;

public class Platform extends GameObject{
    Rect rect;
    int x1,x2,y1,y2;
    public Platform(Bitmap image,  int x1, int y1, int x2, int y2) {
        super(image, 1, 1, x1, y1);
        this.x1=x1;
        this.x2=x2;
        this.y1=y1;
        this.y2=y2;
        this.image=Bitmap.createScaledBitmap(image,(x2-x1)/10,(y2-y1)/10,false);
        rect=new Rect(x1,y1,x2,y2);
    }
    public void draw(Canvas canvas){


        for(int i = x1; i<x2; i+=image.getWidth()){
            for(int j=y1;j<y2;j+=image.getHeight())
                if(i<x2-image.getWidth())canvas.drawBitmap(image,i,j,null);
        }
//        Paint paint=new Paint();
//        paint.setColor(Color.RED);
//        canvas.drawRect(rect,paint);
    }

}
