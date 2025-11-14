package WizardTD;

import java.util.ArrayList;
import java.util.Random;
import processing.core.PImage;

public class Tower {
    private int range;
    private double firingSpeed;
    private int damage;
    private int loctionX;
    private int loctionY;
    private PImage towerImage;
    private PImage fireballImage;
    private boolean isHover;
    private int distanceX ;
    private int distanceY ;

    /**
     * feild for Tower
     */

    public Random random = new Random();

    private ArrayList<Fireball> fireballs;
    private ArrayList<Wave> waves ;
    private Fireball fireball = new Fireball();

    public Tower() {
    }


    public Tower(PImage towerImage,PImage fireballImage,int range, double firingSpeed, int damage, int loctionX, int loctionY, ArrayList<Wave> waves) {
        this.towerImage = towerImage;
        this. fireballImage = fireballImage;
        this.range = range;
        this.firingSpeed = firingSpeed * 60;
        this.damage = damage;
        this.loctionX = loctionX;
        this.loctionY = loctionY;
        this.isHover = false;
        this.waves = waves;
        this.fireballs = new ArrayList<>();

    }



    public void setHover(boolean isHover) {
        this.isHover = isHover;
    }
    public void setfireballImage(PImage fireballImage) {
        this.fireballImage = fireballImage;
    }
    public int getLoctionX() {
        return loctionX;
    }
    public int getLoctionY() {
        return loctionY;
    }



    private ArrayList<Monster> find_targets() {
        ArrayList<Monster> rangeMonster = new ArrayList<>();
        for (Wave w : waves) {
            for (Monster m : w.getMonsters()) {
                distanceX = Math.abs(m.getMonsterX() - (loctionX * 32 + 16));
                distanceY = Math.abs(m.getMonsterY() - (loctionY * 32 + 56));
                if (distanceX <= range  && distanceY <= range && m.getHp() >=0) {
                        rangeMonster.add(m);
                }
            }
        }
        return rangeMonster;
    }
    /**
     * to check if the monsters in the tower range
     */


    public void draw(App app,int frameCount){
        if(isHover){
            app.stroke(250,250,0);
            app.noFill();
            app.ellipse(loctionX* 32 +16,loctionY * 32 + 56, range *2, range * 2);
        }
        app.image(towerImage, loctionX * 32, loctionY * 32 + 40);
        ArrayList<Monster> rangeMonster = find_targets();
        if(rangeMonster.size() != 0){
            int randomIndex = random.nextInt(rangeMonster.size());
            if(frameCount % firingSpeed == 0){
                Monster target = rangeMonster.get(randomIndex);
                    Fireball eachFireball = new Fireball(fireballImage, loctionX * 32 +16, loctionY * 32 +56, damage,target);
                    fireballs.add(eachFireball);
                /**
                 *if there are target in the range, random chooes one
                 * and create fireball
                 */
            }
        }       
        for (Fireball f : fireballs) {
            f.draw(app);
        }
    }
}
 