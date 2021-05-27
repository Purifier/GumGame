    package com.gumer.gumgame;

import android.content.ClipData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Class_SwordMan extends Character{
    boolean ability1=true;
    private Bitmap sawimg,sawpict,sawpictdecreased,timerult;
    Rect LeftB,RightB,UpB,DownB,atkRect,sawRect,ultRect;
    MoveButton buttonLeft,buttonDown,buttonRight,buttonUp,buttonAtk,button1ab,buttonUlt;
    private List<MoveButton> moveButtonList=new ArrayList<MoveButton>();
    private List<Saw> saws=new ArrayList<Saw>();
    private boolean attacking=false;
    private List<Projectile> projectiles=new ArrayList<Projectile>();
    private int ab1state=0;


    public Class_SwordMan(GameSurface gameSurface, Bitmap image, int x, int y) {
        super(gameSurface, image, x, y);
        this.baseAttackDamage=228;
        this.baseStrength=10;
        this.baseAgility=10;
        this.baseIntelligence=5;
        this.baseEndurance=5;
        this.baseAttackSpeed=2;

        sawimg=BitmapFactory.decodeResource(gameSurface.getResources(),R.drawable.saw);
        timerult=BitmapFactory.decodeResource(gameSurface.getResources(),R.drawable.timerult);


        Bitmap bleft = BitmapFactory.decodeResource(gameSurface.getResources(), R.drawable.btn_left);
        Bitmap blefta = BitmapFactory.decodeResource(gameSurface.getResources(), R.drawable.btn_left_activated);
        buttonLeft = new MoveButton(gameSurface, bleft, blefta, 0, gameSurface.getHeight() - bleft.getHeight());
        LeftB = new Rect(0, gameSurface.getHeight() - bleft.getHeight(), bleft.getWidth(), gameSurface.getHeight());

        Bitmap bdown = BitmapFactory.decodeResource(gameSurface.getResources(), R.drawable.btn_down);
        Bitmap bdowna = BitmapFactory.decodeResource(gameSurface.getResources(), R.drawable.btn_down_activated);
        buttonDown = new MoveButton(gameSurface, bdown, bdowna, bleft.getWidth(), gameSurface.getHeight() - bdown.getHeight());
        DownB = new Rect(bleft.getWidth(), gameSurface.getHeight() - bdown.getHeight(), bleft.getWidth() + bdown.getWidth(), gameSurface.getHeight());

        Bitmap bright = BitmapFactory.decodeResource(gameSurface.getResources(), R.drawable.btn_right);
        Bitmap brighta = BitmapFactory.decodeResource(gameSurface.getResources(), R.drawable.btn_right_activated);
        buttonRight = new MoveButton(gameSurface, bright, brighta, bleft.getWidth() + bdown.getWidth(), gameSurface.getHeight() - bright.getHeight());
        RightB = new Rect(bleft.getWidth() + bdown.getWidth(), gameSurface.getHeight() - bright.getHeight(), bleft.getWidth() + bright.getWidth() + bdown.getWidth(), gameSurface.getHeight());

        Bitmap bup = BitmapFactory.decodeResource(gameSurface.getResources(), R.drawable.btn_up);
        Bitmap bupa = BitmapFactory.decodeResource(gameSurface.getResources(), R.drawable.btn_up_activated);
        buttonUp = new MoveButton(gameSurface, bup, bupa, bleft.getWidth() + 1, gameSurface.getHeight() - bdown.getHeight() * 2);
        UpB = new Rect(bleft.getWidth(), gameSurface.getHeight() - bdown.getHeight() * 2, bleft.getWidth() + bdown.getWidth(), gameSurface.getHeight() - bdown.getHeight());



        Bitmap atkBtn=BitmapFactory.decodeResource(gameSurface.getResources(),R.drawable.btn_attack);
        buttonAtk=new MoveButton(gameSurface,atkBtn,atkBtn,gameSurface.getWidth()-atkBtn.getWidth(),gameSurface.getHeight()-atkBtn.getHeight());
        atkRect=new Rect(gameSurface.getWidth()-atkBtn.getWidth(),gameSurface.getHeight()-atkBtn.getHeight(),gameSurface.getWidth(),gameSurface.getHeight());

        sawpict=BitmapFactory.decodeResource(gameSurface.getResources(),R.drawable.btn_saw);
        sawpictdecreased=Bitmap.createScaledBitmap(sawpict,sawpict.getWidth()/2,sawpict.getHeight()/2,false);
        button1ab=new MoveButton(gameSurface,sawpict,sawpict,gameSurface.getWidth()-atkBtn.getWidth()-sawpict.getWidth(),gameSurface.getHeight()-sawpict.getHeight());
        sawRect=new Rect(gameSurface.getWidth()-atkBtn.getWidth()-sawpict.getWidth(),gameSurface.getHeight()-sawpict.getHeight(),gameSurface.getWidth()-atkBtn.getWidth(),gameSurface.getHeight());

        buttonUlt=new MoveButton(gameSurface,Bitmap.createScaledBitmap(timerult,sawpict.getWidth()/2,sawpict.getHeight()/2,false),Bitmap.createScaledBitmap(sawpict,sawpict.getWidth()/2,sawpict.getHeight()/2,false), (int) (gameSurface.getWidth()-atkBtn.getWidth()-sawpict.getWidth()*1.5),gameSurface.getHeight()-sawpict.getHeight());
        ultRect=new Rect((int) (gameSurface.getWidth()-atkBtn.getWidth()-sawpict.getWidth()*1.5),gameSurface.getHeight()-sawpict.getHeight(),gameSurface.getWidth()-atkBtn.getWidth()-sawpict.getWidth(),gameSurface.getHeight()-sawpict.getHeight()/2);

        moveButtonList.add(button1ab);
        moveButtonList.add(buttonDown);
        moveButtonList.add(buttonLeft);
        moveButtonList.add(buttonRight);
        moveButtonList.add(buttonUp);
        moveButtonList.add(buttonAtk);
        moveButtonList.add(buttonUlt);
    }


    @Override
    public void Ability1() {
        if(ab1state==2) {
            for (Saw saw : saws) {
                saw.setDestiny(x, y);
                saw.dmgsrcId++;
            }
            ab1state=0;
            return;
        }
        ab1state=1;

        /*for another class*/
//        if(ability1){
//        Warcry warcry=new Warcry(this);
//        buffs.add(warcry);
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                buffs.remove(warcry);
//                ability1=true;
//            }
//        },10000);
//        ability1=false;
//        }

    }

    @Override
    public void Ability2() {
        projectiles.add(new TimerUlt(Bitmap.createScaledBitmap(timerult,50,50,false),x,y,facing*20));
    }
    @Override
    public void UI(Canvas canvas) {
        for(MoveButton moveButton:this.moveButtonList){
            moveButton.draw(canvas);
        }
    }

    @Override
    public boolean checkUI(MotionEvent event) {
        int x = (int) event.getX(event.getActionIndex()/*говно*/);
        int y = (int) event.getY(event.getActionIndex()/*говно*/);

        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                boolean f=true;
                if (LeftB.contains(x, y)) {
                    buttonLeft.setpId(event.getPointerId(event.getActionIndex()));
                    onLeftPressed(true);
                    f=false;
                }
                if (RightB.contains(x, y)) {
                    buttonRight.setpId(event.getPointerId(event.getActionIndex()));
                    onRightPressed(true);
                    f=false;
                }
                if(UpB.contains(x,y)){
                    buttonUp.setpId(event.getPointerId(event.getActionIndex()));
                    onUpPressed(true);
                    f=false;
                }
                if(DownB.contains(x,y)){
                    buttonDown.setpId(event.getPointerId(event.getActionIndex()));
                    onDownPressed(true);
                    f=false;
                }
                if(atkRect.contains(x,y)){
                    buttonAtk.setpId(event.getPointerId(event.getActionIndex()));
                    attacking=true;
                    f=false;
                }
                if(sawRect.contains(x,y)){
                    Ability1();
                    f=false;
                }
                if(ultRect.contains(x,y)){
                    Ability2();
                    f=false;
                }
                if(ab1state==1&&f){
                    saws.add(new Saw(sawimg, this.x-50, this.y-50,10,x,y));
                    ab1state=2;
                }




                return true;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP: {
                if (event.getPointerId(event.getActionIndex())==buttonLeft.getpId()) {
                    onLeftPressed(false);
                }
                if (event.getPointerId(event.getActionIndex())==buttonRight.getpId()) {
                    onRightPressed(false);
                }
                if (event.getPointerId(event.getActionIndex())==buttonUp.getpId()){
                    onUpPressed(false);
                }
                if (event.getPointerId(event.getActionIndex())==buttonDown.getpId()){
                    onDownPressed(false);
                }
                if(event.getPointerId(event.getActionIndex())==buttonAtk.getpId()){
                    attacking=false;
                }


                return true;
            }

            case MotionEvent.ACTION_MOVE: {

                return true;
            }
            default:{

            }
        }
        return false;

    }

    @Override
    public void evaluateStats() {


    }

    @Override
    public void evaluateBuffs() {
        annul();
        for(Buff buff:buffs){
            buff.evaluate();
        }
    }

    @Override
    public void attack() {
        if(attack&&attacking) {
            List<Enemy> currentTargets = new ArrayList<Enemy>();
            int damage = baseAttackDamage + addedAttackDamage;
            damage*=0;
            if (facing == 1)
                currentTargets.addAll(gameSurface.checkForTargets(new Rect(x, y, x + getWidth() * 2, y + getHeight() + 20)));
            else
                currentTargets.addAll(gameSurface.checkForTargets(new Rect(x - getWidth() * 2, y, x + getWidth(), y + getHeight() + 20)));
            for (Enemy enemy : currentTargets) {
                enemy.takeDamage(damage);
            }
            this.effectList.add(new NightBlade(gameSurface, image, x, y, 50, facing));
            attack=false;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    attack=true;
                }
            },1000/baseAttackSpeed);
        }
    }

    private void onLeftPressed(boolean state) {
        if (state) {
            setMovingVectorX(-1);
            buttonLeft.setAct(true);
        }
        else{
            buttonLeft.setAct(false);
        }
    }

    private void onRightPressed(boolean state) {
        if (state) {
            setMovingVectorX(11);
            buttonRight.setAct(true);
        }
        else{
            setMovingVectorX(0);
            buttonRight.setAct(false);
        }
    }

    private void onUpPressed(boolean state) {
        if (state) {
            jump();
            buttonUp.setAct(true);
        }
        else{
            buttonUp.setAct(false);
        }
    }

    private void onDownPressed(boolean state) {
        if (state) {
            setOnSurface(true);
            buttonDown.setAct(true);
        }
        else{
            buttonDown.setAct(false);
        }
    }

    @Override
    public void update() {
        super.update();
        Iterator<Saw> sawIterator=saws.iterator();
        while(sawIterator.hasNext()){
            List<Enemy> currentTargets=new ArrayList<Enemy>();
            Saw saw=sawIterator.next();
            saw.update();
            currentTargets.addAll(gameSurface.checkForTargets(saw.getRect()));
            for(Enemy enemy:currentTargets){
                saw.dealDmg(enemy);
            }
            if(saw.getRect().contains(x+getWidth()/2,y+getHeight()/2)&&ab1state==0)sawIterator.remove();
        }
        Iterator<Projectile> iterator=projectiles.iterator();
        while(iterator.hasNext()){
            Projectile projectile=iterator.next();
            projectile.update();
            List<Enemy> currentTargets=new ArrayList<Enemy>();
            currentTargets.addAll(gameSurface.checkForTargets(projectile.getRect()));
            for(Enemy enemy:currentTargets){
                projectile.onCollide(enemy);
                if(projectile.isFinished==true)iterator.remove();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        for(Saw saw:saws){
            saw.draw(canvas);
        }
        for(Projectile projectile:projectiles){
            projectile.draw(canvas);
        }
        Paint paint=new Paint();
        paint.setColor(Color.RED);
        //canvas.drawRect(ultRect,paint);
    }
}
