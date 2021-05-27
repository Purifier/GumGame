package com.gumer.gumgame;

public class Warcry extends Buff{

    Warcry(Character character) {
        super(character);
    }

    @Override
    public void evaluate() {
        this.character.addedAttackDamage+=10;
    }
}
