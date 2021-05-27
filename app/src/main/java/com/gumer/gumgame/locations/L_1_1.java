package com.gumer.gumgame.locations;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.gumer.gumgame.GameSurface;
import com.gumer.gumgame.Location;
import com.gumer.gumgame.Mannequin;
import com.gumer.gumgame.R;

public class L_1_1 extends Location {

    public L_1_1(GameSurface gameSurface) {
        super(gameSurface);
        this.mannequin=BitmapFactory.decodeResource(gameSurface.getResources(),R.drawable.mannequin);
        this.bitmapPlatform=BitmapFactory.decodeResource(gameSurface.getResources(), R.drawable.dark_marble);
        Name="L_1_1";
    }

    @Override
    public void init() {

        gameSurface.createPlatform(bitmapPlatform,0,getHeight()*3/4,getWidth()/4,getHeight());
        gameSurface.createPlatform(bitmapPlatform,getWidth()/2,getHeight()*3/4,getWidth()+100,getHeight());
        gameSurface.createPlatform(bitmapPlatform,getWidth()/2,getHeight()*2/4,getWidth()*3/4,getHeight()*3/5);
        gameSurface.enemyList.add(new Mannequin(gameSurface,mannequin,getWidth()*3/4,getHeight()*3/4-mannequin.getHeight()/4));

    }
}
