package com.gumer.gumgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.PlaybackParams;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.gumer.gumgame.levels.Lev_1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

    public GameThread gameThread;

    private final List<Character> characterList = new ArrayList<Character>();
    private final List<Explosion> explosionList = new ArrayList<Explosion>();
    private final List<BackGround> bgList=new ArrayList<BackGround>();
    private final List<MoveButton> moveButtonList=new ArrayList<MoveButton>();
    public final List<Enemy> enemyList=new ArrayList<Enemy>();
    public final List<Platform> platforms=new ArrayList<Platform>();
    private static final int MAX_STREAMS=100;
    private int soundIdExplosion;
    private int soundIdBackground;
    private Rect characterButtons;
    private int fingerOnCharButtons;
    private boolean hasStarted=false;
    public Level curLevel;
    public String curLevelInfo=null;


    private boolean soundPoolLoaded;
    public SoundPool soundPool;
    public float gravity=0.001f;


    public GameSurface(Context context)  {
        super(context);

        // Make Game Surface focusable so it can handle events.
        this.setFocusable(true);

        // Sét callback.
        this.getHolder().addCallback(this);

        this.initSoundPool();
    }

    private void initSoundPool()  {
        // With Android API >= 21.
        if (Build.VERSION.SDK_INT >= 21 ) {

            AudioAttributes audioAttrib = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            SoundPool.Builder builder= new SoundPool.Builder();
            builder.setAudioAttributes(audioAttrib).setMaxStreams(MAX_STREAMS);

            this.soundPool = builder.build();
        }
        // With Android API < 21
        else {
            // SoundPool(int maxStreams, int streamType, int srcQuality)
            this.soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        }

        // When SoundPool load complete.
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPoolLoaded = true;

                // Playing background sound.
                playSoundBackground();
            }
        });

        // Load the sound background.mp3 into SoundPool
        this.soundIdBackground= this.soundPool.load(this.getContext(), R.raw.ponos,1);

        // Load the sound explosion.wav into SoundPool
        this.soundIdExplosion = this.soundPool.load(this.getContext(), R.raw.explosion,1);


    }

    public void playSoundExplosion()  {
        if(this.soundPoolLoaded) {
            float leftVolumn = 0.8f;
            float rightVolumn =  0.8f;
            // Play sound explosion.wav
            int streamId = this.soundPool.play(this.soundIdExplosion,leftVolumn, rightVolumn, 1, 0, 1f);
        }
    }

    public void playSoundBackground()  {
        if(this.soundPoolLoaded) {
            float leftVolumn = 0.8f;
            float rightVolumn =  0.8f;
            // Play sound background.mp3
            int streamId = this.soundPool.play(this.soundIdBackground,leftVolumn, rightVolumn, 1, -1, 1f);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        for(Character c:characterList){
            return c.checkUI(event);
        }
        return false;
    }









    public void update()  {
        for(BackGround backGround: this.bgList) {
            backGround.update();
        }
        for(Character character: characterList) {
            character.update();
        }
        for(Explosion explosion: this.explosionList)  {
            explosion.update();
        }

        for(MoveButton button: this.moveButtonList){
            button.update();
        }

        for(Enemy enemy:this.enemyList){
            enemy.update();
        }


        Iterator<Explosion> iterator= this.explosionList.iterator();
        while(iterator.hasNext())  {
            Explosion explosion = iterator.next();

            if(explosion.isFinish()) {
                // If explosion finish, Remove the current element from the iterator & list.
                iterator.remove();
                continue;
            }
        }

    }

    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);

        for(BackGround backGround: this.bgList){
            backGround.draw(canvas);
        }
        for(Platform platform:this.platforms){
            platform.draw(canvas);
        }
        for(Character chibi: characterList)  {
            chibi.draw(canvas);
        }

        for(Explosion explosion: this.explosionList)  {
            explosion.draw(canvas);
        }

        for(Enemy enemy:this.enemyList){
            enemy.draw(canvas);
        }

    }

    
    
    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if(!hasStarted) {
            curLevel=new Lev_1(this);
            curLevel.init();

            Bitmap chibiBitmap1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.chibi1);
            Character chibi1 = new Class_SwordMan(this, chibiBitmap1, 100, 50);

//            Bitmap chibiBitmap2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.chibi2);
//            Character chibi2 = new Character(this, chibiBitmap2, 300, 150);

            Bitmap background = BitmapFactory.decodeResource(this.getResources(), R.drawable.cave_bg);
            BackGround bc = new BackGround(this, background, this.getWidth(), this.getHeight());





            this.bgList.add(bc);
            this.characterList.add(chibi1);
//            this.characterList.add(chibi2);


            hasStarted=true;
        }

        this.soundPool.autoResume();
        this.gameThread = new GameThread(this,holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry= true;
         {
            try {
                this.soundPool.autoPause();
                this.gameThread.setRunning(false);

                // Parent thread must wait until the end of GameThread.
                this.gameThread.join();

            }catch(InterruptedException e)  {
                e.printStackTrace();
            }
            retry=true;
        }
    }

    public void createPlatform(Bitmap image,int x1,int y1,int x2,int y2){
        platforms.add(new Platform(image,x1,y1,x2,y2));
    }
    public void clearLevel(){
        this.enemyList.clear();
        this.platforms.clear();
    }

    public List<Enemy> checkForTargets(Rect rect){
        List<Enemy> currentTargets=new ArrayList<Enemy>();
        for(Enemy enemy:enemyList){
            if(rect.contains(enemy.stickyX,enemy.stickyY)||(enemy.getRect().contains(rect.left,rect.top)))currentTargets.add(enemy);
        }
        return currentTargets;
    }
}