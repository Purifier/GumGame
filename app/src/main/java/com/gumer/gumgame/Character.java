package com.gumer.gumgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;

public abstract class Character extends GameObject {

    private static final int ROW_TOP_TO_BOTTOM = 0;
    private static final int ROW_RIGHT_TO_LEFT = 1;
    private static final int ROW_LEFT_TO_RIGHT = 2;
    private static final int ROW_BOTTOM_TO_TOP = 3;
    protected final List<ItemEffect> effectList=new ArrayList<ItemEffect>();
    // Row index of Image are being used.
    private int rowUsing = ROW_LEFT_TO_RIGHT;
    private boolean isOnSurface=true;
    private int colUsing;
    private int jumpsLeft=1;
    private int stickyX;
    private int stickyY;
    private int distx;
    private int yHalf;
    public int baseAttackDamage; public int addedAttackDamage;
    public int baseStrength; public int addedStrength;
    public int baseAgility; public int addedAgility;
    public int baseIntelligence; public int addedIntelligence;
    public int baseEndurance; public int addedEndurance;
    public int baseAttackSpeed;
    public boolean attack=true;
    public List<Buff> buffs=new ArrayList<Buff>();

    private Bitmap[] leftToRights;
    private Bitmap[] rightToLefts;
    private Bitmap[] topToBottoms;
    private Bitmap[] bottomToTops;

    // Velocity of game character (pixel/millisecond)
    public static final float VELOCITY = 1.2f;

    private int movingVectorX = 0;
    private int movingVectorY = 0;
    private float gravity;
    private float VELOCITYY=0.18f;
    private long lastDrawNanoTime =-1;
    protected int facing=1;
    private Location curLoc;

    protected GameSurface gameSurface;
    public int lastStickyX;
    public int lastStickyY;

    public Character(GameSurface gameSurface, Bitmap image, int x, int y) {
        super(image, 4, 3, x, y);
        this.gameSurface= gameSurface;
        this.curLoc=gameSurface.curLevel.currentLoc;
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


    public void update()  {
        facing=movingVectorX>0?1:movingVectorX<0?-1:facing;
        Iterator<ItemEffect> iterator=effectList.iterator();
        attack();
        while(iterator.hasNext())  {
            ItemEffect effect = iterator.next();
            effect.update();
            if(effect.isFinish()) {
                // If explosion finish, Remove the current element from the iterator & list.
                iterator.remove();
                continue;
            }
        }
        this.colUsing++;
        if(colUsing >= this.colCount)  {
            this.colUsing =0;
        }
        // Current time in nanoseconds
        long now = System.nanoTime();

        // Never once did draw.
        if(lastDrawNanoTime==-1) {
            lastDrawNanoTime= now;
        }
        // Change nanoseconds to milliseconds (1 nanosecond = 1000000 milliseconds).
        int deltaTime = (int) ((now - lastDrawNanoTime)/ 1000000 );

        // Distance moves
        float distance = VELOCITY * deltaTime;

        double movingVectorLength = Math.sqrt(movingVectorX* movingVectorX);
        if(!isOnSurface) movingVectorY += VELOCITYY*deltaTime+gravity*(deltaTime*deltaTime);
        else {movingVectorY=0; jumpsLeft=1;}
        switch (isOutOfSurface()){
            case -1:
                break;
            case 0:
                x=lastStickyX;
                y=lastStickyY;
                break;
            case 1:
                if(curLoc.Left!=null)curLoc=curLoc.Left;
                curLoc.start();
                x=gameSurface.getWidth();
                break;
            case 2:
                if(curLoc.Right!=null)curLoc=curLoc.Right;
                curLoc.start();
                x=x-gameSurface.getWidth();
                break;
            case 3:
                if(gameSurface.curLevel.currentLoc.Down!=null)(gameSurface.curLevel.currentLoc=gameSurface.curLevel.currentLoc.Down).start();
                y=0;
                break;
            case 4:
                if(gameSurface.curLevel.currentLoc.Up!=null)(gameSurface.curLevel.currentLoc=gameSurface.curLevel.currentLoc.Up).start();
                y=gameSurface.getHeight();
                break;
        }
        // Calculate the new position of the game character.
        distx= (int)(distance* movingVectorX / movingVectorLength);
        yHalf=y+getHeight()/2;
        int lastY = y;
        this.x = x+distx;
        this.y = y +  Math.min(movingVectorY,40);
        this.stickyX=x+getWidth()/2;

        this.stickyY=y+getHeight();
        isOnSurface=false;
        for(Platform platform:gameSurface.platforms){

                if (platform.rect.contains(stickyX, stickyY)&&lastY<=platform.y1) {
                    isOnSurface = true;
                    y = platform.y1 - getHeight();
                    lastStickyX = x;
                    lastStickyY = y;
                }


                if(platform.rect.contains(stickyX,stickyY-3)&&!platform.rect.contains(stickyX-distx,stickyY-3)){
                    this.x=x-distx;
                }
        }
        // When the game's character touches the edge of the screen, then change direction

//        if(this.x < 0 )  {
//            this.x = 0;
//            this.movingVectorX = - this.movingVectorX;
//        } else if(this.x > this.gameSurface.getWidth() -width)  {
//            this.x= this.gameSurface.getWidth()-width;
//            this.movingVectorX = - this.movingVectorX;
//        }
//
//        if(this.y < 0 )  {
//            this.y = 0;
//            this.movingVectorY = - this.movingVectorY;
//        } else if(this.y > this.gameSurface.getHeight()- height)  {
//            this.y= this.gameSurface.getHeight()- height;
//            this.movingVectorY = - this.movingVectorY ;
//        }

        // rowUsing
        if( movingVectorX > 0 )  {
            if(movingVectorY > 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                this.rowUsing = ROW_TOP_TO_BOTTOM;
            }else if(movingVectorY < 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                this.rowUsing = ROW_BOTTOM_TO_TOP;
            }else  {
                this.rowUsing = ROW_LEFT_TO_RIGHT;
            }
        } else if(movingVectorX<0){
            if(movingVectorY > 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                this.rowUsing = ROW_TOP_TO_BOTTOM;
            }else if(movingVectorY < 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                this.rowUsing = ROW_BOTTOM_TO_TOP;
            }else  {
                this.rowUsing = ROW_RIGHT_TO_LEFT;
            }
        }

    }

    public void draw(Canvas canvas)  {
        Bitmap bitmap = this.getCurrentMoveBitmap();
        canvas.drawBitmap(bitmap,x, y, null);
        UI(canvas);
        for(ItemEffect effect:effectList){
            effect.draw(canvas);
        }

        // Last draw time.
        this.lastDrawNanoTime= System.nanoTime();
    }

    public void setMovingVector(int movingVectorX, int movingVectorY)  {
        this.movingVectorX= movingVectorX;
        this.movingVectorY = movingVectorY;
    }
    public void jump(){
        if(jumpsLeft>0) {
            jumpsLeft--;
            isOnSurface = false;
            setMovingVectorY(-50);
        }
    }

    public void setMovingVectorX(int movingVectorX) {
        this.movingVectorX=movingVectorX;
    }

    public void setMovingVectorY(int movingVectorY) {
        this.movingVectorY=movingVectorY;
    }
    public void setOnSurface(boolean state){
        isOnSurface=state;
    }

    public abstract void Ability1();

    public abstract void Ability2();

    public abstract void UI(Canvas canvas);

    public abstract void evaluateStats();

    public abstract void attack();

    public abstract boolean checkUI(MotionEvent event);

    public int isOutOfSurface(){
        if(x<0-getWidth())return 1;
        if(x>gameSurface.getWidth())return 2;
        if(y>gameSurface.getHeight())return 0;
        if(y<0-getHeight())return 4;
        return -1;
    }
    public abstract void evaluateBuffs();
    public void annul(){
        addedAttackDamage=0;
        addedStrength=0;
        addedAgility=0;
        addedIntelligence=0;
        addedEndurance=0;
    }
}
