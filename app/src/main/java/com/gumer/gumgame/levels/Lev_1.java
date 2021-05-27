package com.gumer.gumgame.levels;

import com.gumer.gumgame.GameSurface;
import com.gumer.gumgame.Level;
import com.gumer.gumgame.Location;
import com.gumer.gumgame.locations.*;

public class Lev_1 extends Level {

    public Lev_1(GameSurface gameSurface) {
        super(gameSurface);
    }

    @Override
    public void init() {
        Location l1=new L_1_1(gameSurface);
        currentLoc=l1;
        currentLoc.init();
        Location l2=new L_1_2(gameSurface);
        Location l3=new L_1_3(gameSurface);
        l1.Right=l2;
        l2.Left=l1;
        l2.Right=l3;
        l3.Left=l2;
    }
}
