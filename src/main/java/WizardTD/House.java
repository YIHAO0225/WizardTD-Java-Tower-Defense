package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;

public class House {
    private PImage  houseImage;
    private int houseX,houseY;

    public House() {
    }

    public House(int houseX, int houseY) {
        this.houseX = houseX;
        this.houseY = houseY;
    }

    public PImage getHouseImage() {
        return houseImage;
    }
    public void setHouseImage(PImage houseImage) {
        this.houseImage = houseImage;
    }
    public int getHouseX() {
        return houseX;
    }
    public void setHouseX(int houseX) {
        this.houseX = houseX;
    }
    public int getHouseY() {
        return houseY;
    }
    public void setHouseY(int houseY) {
        this.houseY = houseY;
    } 
    
    public void draw(App app){
        app.image(houseImage, houseX * 32 , houseY * 32 + 32);
    }
}
