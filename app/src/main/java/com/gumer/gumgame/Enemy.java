package com.gumer.gumgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Enemy extends GameObject{
    int health;
    int damage;
    private float gravity;
    private static final int ROW_TOP_TO_BOTTOM = 0;
    private static final int ROW_RIGHT_TO_LEFT = 1;
    private static final int ROW_LEFT_TO_RIGHT = 2;
    private static final int ROW_BOTTOM_TO_TOP = 3;
    private List<Integer> dmgsrcList, collidesrcList;
    // Row index of Image are being used.
    private int rowUsing = ROW_LEFT_TO_RIGHT;
    private boolean isOnSurface=true;
    protected int colUsing;
    protected int stickyX;
    protected int stickyY;
    public List<EnemyBuff> buffs=new ArrayList<EnemyBuff>();

    private Bitmap[] leftToRights;
    private Bitmap[] rightToLefts;
    private Bitmap[] topToBottoms;
    private Bitmap[] bottomToTops;
    private GameSurface gameSurface;
    public Enemy(GameSurface gameSurface,Bitmap image, int rowCount, int colCount, int x, int y) {
        super(image, 4, 3, x, y);
        this.gameSurface= gameSurface;
        this.dmgsrcList=new ArrayList<Integer>();
        this.collidesrcList =new ArrayList<Integer>();
        this.gravity=gameSurface.gravity;
        this.topToBottoms = new Bitmap[colCount]; // 3
        this.rightToLefts = new Bitmap[colCount]; // 3
        this.leftToRights = new Bitmap[colCount]; // 3
        this.bottomToTops = new Bitmap[colCount]; // 3

        for(int col = 0; col< this.colCount; col++ ) {
            this.topToBottoms[col] = this.createSubImageAt(ROW_TOP_TO_BOTTOM, col);
            this.rightToLefts[col]  = this.createSubImageAt(ROW_RIGHT_TO_LEFT, col);
            this.leftToRights[col] = this.createSubImageAt(ROW_LEFT_TO_RIGHT, col);
            this.bottomToTops[col]  = this.createSubImageAt(ROW_BOTTOM_TO_TOP, col);
        }
    }
    public Bitmap[] getMoveBitmaps()  {
        switch (rowUsing)  {
            case ROW_BOTTOM_TO_TOP:
                return  this.bottomToTops;
            case ROW_LEFT_TO_RIGHT:
                return this.leftToRights;
            case ROW_RIGHT_TO_LEFT:
                return this.rightToLefts;
            case ROW_TOP_TO_BOTTOM:
                return this.topToBottoms;
            default:
                return null;
        }
    }

    public Bitmap getCurrentMoveBitmap()  {
        Bitmap[] bitmaps = this.getMoveBitmaps();
        return bitmaps[this.colUsing];
    }
    public void takeDamage(int damage){
        for(EnemyBuff buff:buffs){
            if(buff.eventType==EnemyBuff.TAKINGDMG){
                buff.onEvent();
            }
        }

    }

    public void takeDamage(int damage,int dmgsrcId,int delay){
        if(!dmgsrcList.contains(dmgsrcId)){
            takeDamage(damage);
            dmgsrcList.add(dmgsrcId);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Iterator<Integer> iterator=dmgsrcList.iterator();
                    while(iterator.hasNext()){
                        int cur=iterator.next();
                        if(cur==dmgsrcId)iterator.remove();
                    }
                }
            },delay);
        }
    };
    public void update(){
        Iterator<EnemyBuff> iterator=buffs.iterator();
        while(iterator.hasNext()){
            EnemyBuff buff=iterator.next();
            buff.evaluate();
            if(buff.isFinished)iterator.remove();
        }

    }


    public void draw(Canvas canvas) {
        Iterator<EnemyBuff> iterator = buffs.iterator();
        while (iterator.hasNext()) {
            EnemyBuff buff = iterator.next();
            buff.draw(canvas);
        }
    }
    public boolean collide(int collidesrc){
        if(!this.collidesrcList.contains(collidesrc)){
            this.collidesrcList.add(collidesrc);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Iterator<Integer> iterator=collidesrcList.iterator();
                    while(iterator.hasNext()){
                        int cur=iterator.next();
                        if(cur==collidesrc)iterator.remove();
                    }
                }
            },10000);
            return true;
        }
        return false;
    }
    public Rect getRect(){
        return new Rect(x,y,x+getWidth(),y+getHeight());
    }
}
