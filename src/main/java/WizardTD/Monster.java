package WizardTD;

import java.util.ArrayList;

import org.checkerframework.checker.units.qual.degrees;
import org.checkerframework.checker.units.qual.m;

import processing.core.PImage;

public class Monster {

    private PImage type;
    private int count;
    private double hp;
    private float speed;
    private double armour;
    private int hpMax;
    private int manaGainedOnKill;
    private int deathAnimationIndex = 0;
    private boolean isLive;
    private double preMonsterPause;
    private PImage []  dieAnimation;
    private int monsterX,monsterY;
    /**
     * feild for each monster attributes
     */

    private int stepCount = 0;
    /**
     * count step for each box
     */
    private ArrayList<BFSpoint> shortestPath = new ArrayList<>();

    public Monster(){}

    public Monster(PImage type, double hp, float speed, double armour, int manaGainedOnKill,double preMonsterPause,ArrayList<BFSpoint> shortestPath,PImage []  dieAnimation) {
        this.type = type;
        this.hp = hp;
        this.speed = speed;
        this.armour = armour;
        this.manaGainedOnKill = manaGainedOnKill;
        this.preMonsterPause = preMonsterPause;
        this.shortestPath = shortestPath;
        this.dieAnimation = dieAnimation;
        this.isLive = true;
        this.hpMax = (int)hp;
        this.count = shortestPath.size()-1;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }
    public void setMonsterX(int monsterX) {
        this.monsterX = monsterX;
    }
    public void setMonsterY(int monsterY) {
        this.monsterY = monsterY;
    }
    public double getHp() {
        return hp;
    }
    public double getArmour() {
        return armour;
    }
    public int getMonsterX() {
        return monsterX;
    }
    public int getMonsterY() {
        return monsterY;
    }
    /**
     * count step for each box
     */
    public void draw(App app,int frameCount) {
        int hpRed = 30;
        int hpGreen = (int)(hpRed * (hp /hpMax));
        if(frameCount >= preMonsterPause * 60){
            if(count >= 0 && count -1 >= 0){
                BFSpoint step = shortestPath.get(count);
                BFSpoint nextStep = shortestPath.get(count -1);
                if (app.isFPressed) {
                    stepCount *= 2;
                }
                if(step.getY() == nextStep.getY()){
                    if(step.getX() < nextStep.getX()){
                        if(hp > 0){
                            app.fill(255,0,0);
                            app.stroke(255,0,0);
                            app.rect(step.getX() *32 + 1 + stepCount,step.getY() *32 + 35,hpRed,3);

                            app.fill(131,249,125);
                            app.stroke(131,249,125);
                            app.rect(step.getX() *32 + 1 + stepCount,step.getY() *32 + 35,hpGreen,3);
                            app.image(type,step.getX() *32 + 6 + stepCount,step.getY() *32 + 40);
                            setMonsterX(step.getX() *32 + 6 + stepCount);
                        setMonsterY(step.getY() *32 + 40);
                        stepCount += speed;
                        }else{
                            if (deathAnimationIndex < dieAnimation.length) {
                                app.image(dieAnimation[deathAnimationIndex], step.getX() * 32 + 6 + stepCount, step.getY() * 32 + 40);
                                deathAnimationIndex++;
                            }
                        }
                        /**
                         * monster go to right side
                         */
                    }
                    if(step.getX() > nextStep.getX()){
                        if(hp > 0){
                            app.fill(255,0,0);
                            app.stroke(255,0,0);
                            app.rect(step.getX() *32 + 1 - stepCount,step.getY() *32 + 35,hpRed,3);

                            app.fill(131,249,125);
                            app.stroke(131,249,125);
                            app.rect(step.getX() *32 + 1 - stepCount,step.getY() *32 + 35,hpGreen,3);

                            app.image(type,step.getX() *32 + 6 - stepCount,step.getY() *32 + 40);
                            setMonsterX(step.getX() *32 + 6 + stepCount);
                        setMonsterY(step.getY() *32 + 40);
                        stepCount += speed;
                        }else {
                            if (deathAnimationIndex < dieAnimation.length ) {
                                app.image(dieAnimation[deathAnimationIndex], step.getX() * 32 + 6 - stepCount, step.getY() * 32 + 40);
                                deathAnimationIndex++;
                            }
                        }
                        /**
                         * monster go to left side
                         */
                    }
                }
                if(step.getX() == nextStep.getX()){
                    if(step.getY() > nextStep.getY()){
                        if(hp > 0){
                            app.fill(255,0,0);
                            app.stroke(255,0,0);
                            app.rect(step.getX() *32 + 1,step.getY() *32 + 35 - stepCount,hpRed,3);

                            app.fill(131,249,125);
                            app.stroke(131,249,125);
                            app.rect(step.getX() *32 + 1,step.getY() *32 + 35 - stepCount,hpGreen,3);

                            app.image(type,step.getX() *32 + 6 ,step.getY() *32 + 40 - stepCount);
                            setMonsterX(step.getX() *32 + 6 + stepCount);
                        setMonsterY(step.getY() *32 + 40);
                        stepCount += speed;
                        }else{
                            if (deathAnimationIndex < dieAnimation.length) {
                                app.image(dieAnimation[deathAnimationIndex], step.getX() * 32 + 6 , step.getY() * 32 + 40 - stepCount);
                                deathAnimationIndex++;
                            }
                        }
                        /**
                         * monster go to up
                         */
                    }
                    if(step.getY() < nextStep.getY()){ //go down side
                        if(hp > 0){
                            app.fill(255,0,0);
                            app.stroke(255,0,0);
                            app.rect(step.getX() *32 + 1,step.getY() *32 + 35 + stepCount,hpRed,3);

                            app.fill(131,249,125);
                            app.stroke(131,249,125);
                            app.rect(step.getX() *32 + 1,step.getY() *32 + 35 + stepCount,hpGreen,3);

                            app.image(type,step.getX() *32 + 6,step.getY() *32 + 40 + stepCount);
                            setMonsterX(step.getX() *32 + 6 + stepCount);
                            setMonsterY(step.getY() *32 + 40);
                            stepCount += speed;
                        }else{
                            if (deathAnimationIndex < dieAnimation.length) {
                                app.image(dieAnimation[deathAnimationIndex], step.getX() * 32 + 6 , step.getY() * 32 + 40 + stepCount);
                                deathAnimationIndex++;
                            }
                        }
                        /**
                         * monster go to down
                         */
                    }
                }
            }
            if (stepCount >= 32){
                count --;
                stepCount = 0;
            }
            /**
             * if one box finish,go to next box
             */
            if( count == 0 && isLive){
                app.mana -= hp;
                isLive = false;
            }
            /**
             * When the monsters are gone, reduce the mana
             */
            if(hp <= 0 && isLive){
                app.mana += manaGainedOnKill;
                isLive = false;
            }
            /**
             * when monster die , add mana
             */
        }
    }
}
