package com.gumer.gumgame;



import android.app.Activity;
import android.os.Bundle;

import android.view.Window;
import android.view.WindowManager;
import android.widget.SimpleAdapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class GameActivity extends Activity {
    GameSurface surface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        surface=new GameSurface(this);

        this.setContentView(surface);
    }
    //
//        FileInputStream fis=null;
//        try {
//            fis=openFileInput("LevelInfo.txt");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        String curlevel=surface.curLevel.currentLoc.Name;
        FileOutputStream fos=null;
        try {
            fos=openFileOutput("LevelInfo.txt",MODE_PRIVATE);
            fos.write(curlevel.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}