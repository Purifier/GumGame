package com.gumer.gumgame;

public abstract class Buff {
    Character character;
    boolean isFinished=false;
    Buff(Character character){
        this.character=character;
    }
    public abstract void evaluate();

}
