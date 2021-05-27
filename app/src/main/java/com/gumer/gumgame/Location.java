package com.gumer.gumgame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public abstract class Location {
    public GameSurface gameSurface;
    public Location Up=null;
    public Location Down=null;
    public Location Right=null;
    public Location Left=null;
    public Bitmap bitmapPlatform;
    public Bitmap mannequin;
    public String Name;
    private int width;
    private int height;
    public Location(GameSurface gameSurface){
        this.gameSurface=gameSurface;
        this.width=gameSurface.getWidth();
        this.height=gameSurface.getHeight();
    }
    public void start(){
        gameSurface.clearLevel();
        this.init();
    }
    public abstract void init();
    public int getWidth(){
        return(width);
    }
    public int getHeight(){
        return(height);
    }
}
