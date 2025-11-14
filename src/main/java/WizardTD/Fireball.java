package WizardTD;

import java.util.ArrayList;


import processing.core.PImage;

public class Fireball{

    private PImage fireImage;
    private int fireLocationX;
    private int fireLocationY;
    private int damage;
    private Monster target;
    private boolean fireExist;
    private int distanceX ;
    private int distanceY ;


    public Fireball(){};


    public Fireball(PImage fireImage, int fireLocationX, int fireLocationY, int damage, Monster target) {
        this.fireImage = fireImage;
        this.fireLocationX = fireLocationX;
        this.fireLocationY = fireLocationY;
        this.damage = damage;
        this.target = target;
        this.fireExist= true;
    }

    public void setDistanceX(int distanceX) {
        this.distanceX = distanceX;
    }
    public void setDistanceY(int distanceY) {
        this.distanceY = distanceY;
    }

    public void draw(App app) {
        if (fireExist == true) {
            distanceX = Math.abs(target.getMonsterX() - fireLocationX);
            distanceY = Math.abs(target.getMonsterY() - fireLocationY);
            double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
            if (distance > 5.0) {
                double cos = Math.abs(distanceX / distance);
                double sin = Math.abs(distanceY / distance);
                if (target.getMonsterX() > fireLocationX) {
                    fireLocationX += (int) (5 * cos);
                } else {
                    fireLocationX -= (int) (5 * cos);
                }
                if (target.getMonsterY() > fireLocationY) {
                    fireLocationY += (int) (5 * sin);
                } else {
                    fireLocationY -= (int) (5 * sin);
                }
            } else {
                fireExist = false;
                target.setHp(target.getHp() - (damage - target.getArmour()));
            }
        }
        if (fireExist) {
            app.image(fireImage, fireLocationX, fireLocationY);
        }
    }
}
