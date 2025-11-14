package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.MouseEvent;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import org.checkerframework.checker.units.qual.g;
/**
 * Import proessing
 */

public class App extends PApplet {  

    public static final int CELLSIZE = 32;
    public static final int SIDEBAR = 120;
    public static final int TOPBAR = 40;
    public static final int BOARD_WIDTH = 20;
    public static int WIDTH = CELLSIZE*BOARD_WIDTH+SIDEBAR;
    public static int HEIGHT = BOARD_WIDTH*CELLSIZE+TOPBAR;
    public static final int FPS = 60;
    private String configPath;
    /**
     * Setting the window size
     */
    private Map map;
    private Enemy enemy;
    private House house;

    /**
     * Creating OBject instance
     */
    private int frameCount = 0;
    /**
     * this field for frameCount
     */

    ArrayList<String[]> mapLoad = new ArrayList<>();
    private ArrayList<Tower> towers =  new ArrayList<>();
    /**
     *  save information of the map and tower
     */
    private int towerRange;
    private double towerFiringSpeed;
    private int towerDamage;
    public int mana;
    private int manaCap;
    private int manaGainedPerSecond;
    private int towerCost;
    private int updatePooCost;
    private int poorIncercs;

    /**
     *  save information of json file
     */
    public boolean isTPressed = false;
    public boolean isFPressed = false;
    public boolean isPPressed = false;
    public boolean isU1Pressed = false;
    public boolean isU2Pressed = false;
    public boolean isU3Pressed = false;
    public boolean isMPressed = false;

    /**
     *  whether the butter is pressed
     */

    private boolean noMoney = false;
    /**
     *  whether user have money to build tower and update poor
     */
    private PImage towerImage ;
    private PImage fireballImage;

    /**
     *  save the image;
     */


    public App() {
        this.map = new Map();
        this.configPath = "config.json";
        this.enemy = new Enemy();
        this.house = new House();
        /**
         * Creating OBject instance
         */
    }

	@Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }
    /**
     * Setting the window size
     */

    public void json_file_load(){
        /**
         * this method for load informatin from json file
         * and map information
         */

        try{
            FileReader fr = new FileReader(new File(this.configPath));
            JSONObject jb = new JSONObject(fr);
            String layout = jb.getString("layout");
            File file = new File(layout);
            BufferedReader br = new BufferedReader(new FileReader(file));
            for (int y = 0; y < 20; y++) {
                String[] row = br.readLine().split("");
                while (row.length < 20) {
                    String[] newRow = new String[row.length + 1];
                    System.arraycopy(row, 0, newRow, 0, row.length);
                    newRow[row.length] = " "; 
                    row = newRow;
                }
                mapLoad.add(row);
            }
            /**
             * if map is incomplete , add " "
             */

            for (int y = 0; y < 20; y++) {
                for (int x = 0; x < 20; x++) {
                    if (mapLoad.get(y)[x].equals("W")) {
                        this.house.setHouseX(x);
                        this.house.setHouseY(y);
                    }
                }
            }
            /**
             * save the loction of house
             */

            JSONArray jsonArray = (JSONArray) jb.get("waves");
            towerRange = jb.getInt("initial_tower_range");
            towerFiringSpeed = jb.getDouble("initial_tower_firing_speed");
            towerDamage = jb.getInt("initial_tower_damage");
            mana = jb.getInt("initial_mana");
            manaCap = jb.getInt("initial_mana_cap");
            manaGainedPerSecond = jb.getInt("initial_mana_gained_per_second");
            towerCost = jb.getInt("tower_cost");
            updatePooCost = jb.getInt("mana_pool_spell_initial_cost");
            poorIncercs = jb.getInt("mana_pool_spell_cost_increase_per_use");
            this.map.setMap(mapLoad);
            this.enemy.setTotalWaves(jsonArray);
            this.enemy.setMap(mapLoad);
            /**
             * get the data and pass to every class
             */

        }catch (IOException e){
            e.printStackTrace();
        }
    }

	@Override
    public void setup() {
        frameRate(FPS);
        json_file_load();
        towerImage = loadImage("WizardTD/tower0.png");
        fireballImage = loadImage("WizardTD/fireball.png");
        this.map.setPath(this.loadImage("WizardTD/path0.png"));
        this.map.setPathCorner(this.loadImage("WizardTD/path1.png"));
        this.map.setPathTjunction(this.loadImage("WizardTD/path2.png"));
        this.map.setPathCrossroads(this.loadImage("WizardTD/path3.png"));
        this.map.setShrub(this.loadImage("WizardTD/shrub.png"));
        this.map.setGrass(this.loadImage("WizardTD/grass.png"));
        this.house.setHouseImage(this.loadImage("WizardTD/wizard_house.png"));
        this.enemy.setBeetle(this.loadImage("WizardTD/beetle.png"));
        this.enemy.setGremlin(this.loadImage("WizardTD/gremlin.png"));
        this.enemy.setDieAnimation1(this.loadImage("WizardTD/gremlin1.png"));
        this.enemy.setDieAnimation2(this.loadImage("WizardTD/gremlin2.png"));
        this.enemy.setDieAnimation3(this.loadImage("WizardTD/gremlin3.png"));
        this.enemy.setDieAnimation4(this.loadImage("WizardTD/gremlin4.png"));
        this.enemy.setDieAnimation5(this.loadImage("WizardTD/gremlin5.png"));
        this.enemy.setWorm(this.loadImage("WizardTD/worm.png"));
        this.enemy.load_enemy();
        /**
         *  loading  Image and set up the game
         */

    }


	@Override
    public void keyPressed(){
        if (key == 'T' || key == 't') {
            isTPressed = true;
          }
        if (key == 'P' || key == 'p') {
            isPPressed = true;
        }
        if (key == 'F' || key == 'f') {
            isFPressed = true;
        }
        if (key == '1') {
            isU1Pressed = true;
        }
        if (key == '2') {
            isU2Pressed = true;
        }
        if (key == '3') {
            isU3Pressed = true;
        }
        if (key == 'M' || key == 'm') {
            isMPressed = true;

            if(mana < towerCost){
                noMoney = true;
            }else{
                mana -= updatePooCost;
                manaCap += poorIncercs;
            }
        }
        /**
         * when the player presses key, set feild to ture, and excute some code
         */
    }
  

	@Override
    public void keyReleased(){
        if (key == 'T' || key == 't') {
            isTPressed = false;
        }
        if (key == 'P' || key == 'p') {
            isPPressed = false;
        }
        if (key == 'F' || key == 'f') {
            isFPressed = false;
        }
        if (key == '1') {
            isU1Pressed = false;
        }
        if (key == '2') {
            isU2Pressed = false;
        }
        if (key == '3') {
            isU3Pressed = false;
        }
        if (key == 'M' || key == 'm') {
            isMPressed = false;
            noMoney = false;
        }


        if (key == 'R' || key == 'r') {
            this.enemy.wavesClear();
            System.out.println(this.enemy.getWaves().size());
            this.towers.clear();
            setup();
        }
        /**
         * when the player released key, set feild to ture, and excute some code
         */
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isTPressed) {
            int mouseLocationX = e.getX() / 32;
            int mouseLocationY = e.getY() / 32 - 1;
    
            if (mouseLocationX >= 0 && mouseLocationX < 20 && mouseLocationY >= 0 && mouseLocationY < 20 
                                    && mapLoad.get(mouseLocationY)[mouseLocationX].equals(" ")) {
                        
                Tower eachTower = new Tower(towerImage,fireballImage, towerRange, towerFiringSpeed, towerDamage, mouseLocationX, mouseLocationY,enemy.getWaves());
                if(mana <= towerCost){
                    noMoney = true;
                }else{
                    mana -= towerCost;
                    towers.add(eachTower);
                }
            }
            /**
             * When the player clike somewhere which is grass ,
             * craet tower instance.
             */
        }
    }

    public void mouseMoved(MouseEvent e) {
        int mouseLocationX = e.getX() / 32;
        int mouseLocationY = e.getY() / 32 - 1;

        for (Tower t : towers) {
            if(t.getLoctionX() == mouseLocationX && t.getLoctionY() == mouseLocationY){
                t.setHover(true);
            }else{
                t.setHover(false);
            }
        }
        /**
         * When the player mouse move  somewhere which is tower ,
         * show the range of tower..
         */
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        noMoney = false;
    }

	@Override
    public void draw() {
        if(mana <= 0){
            stroke(0,0);
            fill(255,255,255);
            int manaInCap = (int)(330 * ((double)mana /manaCap)); 
            rect(391, 12, 300, 17);

            fill(0);
            textSize(19);
            text(String.format("%d / %d",0,manaCap),500,28);
            
            fill(0);
            textSize(40);
            text("You lost!",280, 350);

            fill(0);
            textSize(30);
            text("R restart",300, 400);
            pause();
            return;
            /**
             * when the mana equal 0 , show the result "You lost!"
             */
        }

        frameCount +=1;
        if(isPPressed){
            stroke(0);
            fill(13, 115, 74);
            rect(650,110, 40, 40);
            fill(0);
            textSize(24);
            text("P",656,139);
            pause();
            return;
        }
        /**
         * when the palyer pressed the p , pause the game
         */

        background(132, 115, 74);
        this.map.draw(this);
        for (Tower t : towers) {
            t.draw(this, frameCount);
        }
        this.enemy.draw(this,frameCount);

        /**
         * draw the map, tower and enemy
         */

        if(noMoney){
            fill(0);
            textSize(40);
            text("Mana is not enough. ",200, 350);
        }

        fill(0);
        textSize(20);
        text("Wave:",20, 27);

        fill(0);
        textSize(20);
        text("Start:",120, 27);

        fill(0);
        textSize(20);
        text("MANA:",320, 27);

        stroke(0);
        strokeWeight(2);
        fill(255,255,255);
        rect(390, 10, 330, 20);

        stroke(0);
        fill(25,255,255);
        int manaInCap = (int)(330 * ((double)mana /manaCap)); 
        rect(390, 10, manaInCap, 20);

        fill(0);
        textSize(19);
        text(String.format("%d / %d",mana,manaCap),500,28);

        stroke(0);
        fill(132, 115, 74);
        rect(650,50, 40, 40);
        fill(0);
        textSize(24);
        text("FF",656,79);
        textSize(13);
        text("2x speed",697,62);

        stroke(0);
        fill(132, 115, 74);
        rect(650,110, 40, 40);
        fill(0);
        textSize(24);
        text("P",656,139);
        textSize(13);
        text("PAUSE",697,122);

        stroke(0);
        fill(132, 115, 74);
        rect(650,170, 40, 40);
        fill(0);
        textSize(24);
        text("T",656,199);
        textSize(13);
        text("Build",697,182);
        text("tower",697,202);

        stroke(0);
        fill(132, 115, 74);
        rect(650,230, 40, 40);
        fill(0);
        textSize(24);
        text("U1",656,259);
        textSize(13);
        text("Upgrade",697,242);
        text("range",697,262);

        stroke(0);
        fill(132, 115, 74);
        rect(650,290, 40, 40);
        fill(0);
        textSize(24);
        text("U2",656,319);
        textSize(13);
        text("Upgrade",697,302);
        text("speed",697,322);

        stroke(0);
        fill(132, 115, 74);
        rect(650,350, 40, 40);
        fill(0);
        textSize(24);
        text("U3",656,379);
        textSize(13);
        text("Upgrade",697,362);
        text("damage",697,382);

        stroke(0);
        fill(132, 115, 74);
        rect(650,410, 40, 40);
        fill(0);
        textSize(24);
        text("M",656,439);
        textSize(13);
        text("Upgrade",697,422);
        text("damage",697,442);

        /**
         * draw the game screen of the buttons
         */


        if(isTPressed == true){
            stroke(0);
            fill(13, 115, 74);
            rect(650,170, 40, 40);
            fill(0);
            textSize(24);
            text("T",656,199);
        }
        
        if(isFPressed == true){
            stroke(0);
            fill(13, 115, 74);
            rect(650,50, 40, 40);
            fill(0);
            textSize(24);
            text("FF",656,79);
        }

        if(isU1Pressed){
            stroke(0);
            fill(13, 115, 74);
            rect(650,230, 40, 40);
            fill(0);
            textSize(24);
            text("U1",656,259);
        }

        if(isU2Pressed){
            stroke(0);
            fill(13, 115, 74);
            rect(650,290, 40, 40);
            fill(0);
            textSize(24);
            text("U2",656,319);
        }
        if(isU3Pressed){
            stroke(0);
            fill(13, 115, 74);
            rect(650,350, 40, 40);
            fill(0);
            textSize(24);
            text("U3",656,379);
        }
        if(isMPressed){
            stroke(0);
            fill(13, 115, 74);
            rect(650,410, 40, 40);
            fill(0);
            textSize(24);
            text("M",656,439);
        }

        /**
         * when the mouse move to some button, show different color.
         */
        if(frameCount% 60 == 0) {
            this.mana += manaGainedPerSecond;
            if (this.mana >= manaCap) {
                this.mana = manaCap;
            }
        }
        this.house.draw(this);
    }

    public static void main(String[] args) {
        PApplet.main("WizardTD.App");
    }

    public PImage rotateImageByDegrees(PImage pimg, double angle) {
        BufferedImage img = (BufferedImage) pimg.getNative();
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        PImage result = this.createImage(newWidth, newHeight, RGB);
        BufferedImage rotated = (BufferedImage) result.getNative();
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        for (int i = 0; i < newWidth; i++) {
            for (int j = 0; j < newHeight; j++) {
                result.set(i, j, rotated.getRGB(i, j));
            }
        }

        return result;
    }
}
