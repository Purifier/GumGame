package com.gumer.gumgame;

public abstract class Level {
    public GameSurface gameSurface;
    public Location currentLoc;
    public Level(GameSurface gameSurface){
        this.gameSurface=gameSurface;
    }
    public abstract void init();
    public String getCurrentLoc(){
        return currentLoc.Name;
    }
}
