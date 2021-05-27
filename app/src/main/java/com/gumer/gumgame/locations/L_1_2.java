package com.gumer.gumgame.locations;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.gumer.gumgame.GameSurface;
import com.gumer.gumgame.Location;
import com.gumer.gumgame.Mannequin;
import com.gumer.gumgame.R;

public class L_1_2 extends Location {

    public L_1_2(GameSurface gameSurface) {
        super(gameSurface);
        this.mannequin=BitmapFactory.decodeResource(gameSurface.getResources(),R.drawable.mannequin);
        this.bitmapPlatform=BitmapFactory.decodeResource(gameSurface.getResources(), R.drawable.dark_marble);
        Name="L_1_2";
    }

    @Override
    public void init() {
        gameSurface.createPlatform(bitmapPlatform,-100,getHeight()*3/4,getWidth()+100,getHeight());
        gameSurface.enemyList.add(new Mannequin(gameSurface,mannequin,getWidth()*1/4,getHeight()*3/4-mannequin.getHeight()/4));
    }
}
