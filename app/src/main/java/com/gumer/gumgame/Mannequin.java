package com.gumer.gumgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Mannequin extends Enemy{
    public int count;
    public class DmgNumber {

        public int y;
        public int x;
        public Integer number;
        public int count;
        private boolean finish= false;
        private GameSurface gameSurface;

        public DmgNumber(int number,int x, int y) {
            this.number=number;
            this.x=x;
            this.y=y;
            count=0;
        }

        public void update()  {
            count++;
            y-=5;
            if(count==20)finish=true;
        }

        public void draw(Canvas canvas)  {
            if(!finish)  {
                Paint paint=new Paint();
                paint.setTextSize(50);
                paint.setColor(Color.RED);
                canvas.drawText(number.toString(),x,y,paint);
            }
        }

        public boolean isFinish() {
            return finish;
        }

    }

    private final List<DmgNumber> dmgNumbers=new ArrayList<DmgNumber>();
    public Mannequin(GameSurface gameSurface,Bitmap image,int x, int y) {
        super(gameSurface,image, 4, 3, x, y);
        count=0;
    }


    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        if(damage>0)this.dmgNumbers.add(new DmgNumber(damage,x,y));
    }

    @Override
    public void update() {
        super.update();
        count++;
        if(count%4==0) {
            colUsing++;
            if (colUsing >= this.colCount) {
                this.colUsing = 0;
            }
            count=0;
        }
        for(DmgNumber dmgNumber:dmgNumbers){
            dmgNumber.update();
        }
        this.stickyX=x+getWidth()/2;
        this.stickyY=y+getHeight();
        Iterator<DmgNumber> iterator= this.dmgNumbers.iterator();
        while(iterator.hasNext())  {
            DmgNumber dmgNumber = iterator.next();

            if(dmgNumber.isFinish()) {
                // If explosion finish, Remove the current element from the iterator & list.
                iterator.remove();
                continue;
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(getCurrentMoveBitmap(),x,y,null);
        for(DmgNumber dmgNumber:dmgNumbers){
            dmgNumber.draw(canvas);
        }
    }
}
