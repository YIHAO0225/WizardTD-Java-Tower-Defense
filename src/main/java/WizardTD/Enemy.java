package WizardTD;
import java.util.ArrayList;
import java.util.Random;

import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.core.PImage;


public class Enemy {
    public Random random = new Random();
    /**
     * craet random instance
     */
    private JSONArray totalWaves;
    /**
     * feild for tatalwave of monster
     */
    private PImage beetle;
    private PImage gremlin;
    private PImage worm;
    /**
     * feild for type of monster
     */

    private ArrayList<Wave> waves = new ArrayList<>();
    /**
     *  save every wave to a arraylist
     */
    private ArrayList<BFSpoint> totalBirthPoint = new ArrayList<>();
    /**
     *  save every point which monster may appear
     */
    private ArrayList<String[]> map = new ArrayList<>();
    /**
     *  save the information of map
     */
    private PImage []  dieAnimation=  new PImage [5];
    /**
     *  array to save the die animation of monster
     */
    private BFSpoint endPoint = new BFSpoint();
    /**
     *  craet instance of BFSpoint
     */
    public Enemy(){
    }


    public void setBeetle(PImage beetle) {
        this.beetle = beetle;
    }
    public void setGremlin(PImage gremlin) {
        this.gremlin = gremlin;
    }
    public void setDieAnimation1(PImage dieAnimation1) {
        this.dieAnimation[0] = dieAnimation1;
    }
    public void setDieAnimation2(PImage dieAnimation2) {
        this.dieAnimation[1] = dieAnimation2;
    }
    public void setDieAnimation3(PImage dieAnimation3) {
        this.dieAnimation[2] = dieAnimation3;
    }
    public void setDieAnimation4(PImage dieAnimation4) {
        this.dieAnimation[3] = dieAnimation4;
    }
    public void setDieAnimation5(PImage dieAnimation5) {
        this.dieAnimation[4] = dieAnimation5;
    }
    public void setWorm(PImage worm) {
        this.worm = worm;
    }
    public void setTotalWaves(JSONArray totalWaves) {
        this.totalWaves = totalWaves;
    }
    public void setMap(ArrayList<String[]> map) {
        this.map = map;
    }

    /**
     *  set up all the feild
     */

    public ArrayList<Wave> getWaves() {
        return waves;
    }
    /**
     *  get all the monsters for the waves
     */

    public void wavesClear(){
        this.waves.clear();
    }
    /**
     *  clear all the monsters for the waves
     */

    public void load_enemy(){
        double preWavePause = 0 ;
        for (int i = 0; i < totalWaves.size(); i++) {
            JSONObject singleWave = (JSONObject)totalWaves.get(i);
            int duration = singleWave.getInt("duration");
            preWavePause += singleWave.getDouble("pre_wave_pause");
            /**
             *  read each wave from total waves.
             */
            this.set_birth_point(totalBirthPoint);
            int num = random.nextInt(totalBirthPoint.size());
            BFSpoint birthPoint = totalBirthPoint.get(num);
            this.set_end_point(endPoint);
            ArrayList<BFSpoint> shortestPath = new ArrayList<>();
            this.BFS_path_find(shortestPath,map,birthPoint,endPoint);
            /**
             *  find the shorts way use BFS
             */
            JSONArray monstersArray = (JSONArray)singleWave.get("monsters");
            ArrayList<Monster> monsters = new ArrayList<>();
            for (int j = 0; j < monstersArray.size(); j++) {
                monsters.addAll(this.set_monster((JSONObject)monstersArray.get(j),duration,preWavePause,shortestPath,dieAnimation));
            }
            /**
             *  Loop monster and create monster
             */
            Wave wave = new Wave(duration, preWavePause, monsters);
            waves.add(wave);
        }
    } 

    public ArrayList<Monster> set_monster(JSONObject jsonobject, int duration, double preWavePause,ArrayList<BFSpoint> shortestPath,PImage []dieAnimation){
        ArrayList<Monster> all = new ArrayList<>();
        int quantity = jsonobject.getInt("quantity");
        double preMonsterPause = duration / (double)quantity;
        for (int i = 0; i < quantity; i++) {
            String type = jsonobject.getString("type");
            double hp = jsonobject.getInt("hp");
            float speed = jsonobject.getInt("speed");
            double armour = jsonobject.getDouble("armour");
            int manaGainedOnKill = jsonobject.getInt("mana_gained_on_kill");
            Monster eachMonster = null;
            if (type.equals("beetle")) {
                eachMonster = new Monster(beetle, hp, speed, armour, manaGainedOnKill,preWavePause,shortestPath,dieAnimation);
            } else if (type.equals("gremlin")) {
                eachMonster = new Monster(gremlin, hp, speed, armour, manaGainedOnKill,preWavePause,shortestPath,dieAnimation);
            } else if (type.equals("worm")) {
                eachMonster = new Monster(worm, hp, speed, armour, manaGainedOnKill,preWavePause,shortestPath,dieAnimation);
            }
             
            if (eachMonster != null) {
                all.add(eachMonster);
                preWavePause += preMonsterPause;
            }
        }/**
         *  Check how many monsters are in each wave.
         * Place different pictures according to different monster types
         */
        return all;
    }


    private void BFS_path_find(ArrayList<BFSpoint> path,ArrayList<String[]> map,BFSpoint start,BFSpoint end){
        BFSpoint[][] prev = new BFSpoint[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                prev[i][j] = null;
            }
        }
        /**
         *  prev array store the previous point of each point in the path
         */
        ArrayList<BFSpoint> q = new ArrayList<>();
        q.add(start);
        prev[start.getY()][start.getX()] = start;

        while(q.size() > 0){
            /**
             *  Starting a BFS search
             */
            BFSpoint temp = q.remove(0);
            if(temp.getY() == end.getY() && temp.getX() == end.getX()){
                break;
            }
            int Y = temp.getY();
            int X = temp.getX();
            if (Y + 1 < 20 && (map.get(Y + 1)[X].equals("X") || map.get(Y + 1)[X].equals("W"))) {
                if (prev[Y + 1][X] == null) {
                    prev[Y + 1][X] = temp;
                    q.add(new BFSpoint(Y + 1, X));
                }
            }
    
            if (Y - 1 >= 0 && (map.get(Y - 1)[X].equals("X") || map.get(Y - 1)[X].equals("W"))) {
                if (prev[Y - 1][X] == null) {
                    prev[Y - 1][X] = temp;
                    q.add(new BFSpoint(Y - 1, X));
                }
            }
    
            if (X + 1 < 20 && (map.get(Y)[X + 1].equals("X") || map.get(Y)[X + 1].equals("W"))) {
                if (prev[Y][X + 1] == null) {
                    prev[Y][X + 1] = temp;
                    q.add(new BFSpoint(Y, X + 1));
                }
            }
    
            if (X - 1 >= 0 && (map.get(Y)[X - 1].equals("X") || map.get(Y)[X - 1].equals("W"))) {
                if (prev[Y][X - 1] == null) {
                    prev[Y][X - 1] = temp;
                    q.add(new BFSpoint(Y, X - 1));
                }
            }
        }
        this.get_path(path, prev, start, end);
    }

    private void get_path(ArrayList<BFSpoint> path,BFSpoint[][] prev,BFSpoint start,BFSpoint end){
        if(start.getY() == end.getY() && start.getX() == end.getX()){
            path.add(start);
            return;
        }
        path.add(end);
        BFSpoint next = prev[end.getY()][end.getX()];
        get_path(path, prev, start, next);
        /**
         *  If the start point and the destination point are the same,
         *  add the start point to the path
         */
    }

    private void set_birth_point(ArrayList<BFSpoint> birthPoint){
        for (int i = 0; i < 20; i++) {
            if(map.get(0)[i].equals("X")){
                birthPoint.add(new BFSpoint(0, i));
            }
        }
        for (int i = 0; i < 20; i++) {
            if(map.get(19)[i].equals("X")){
                birthPoint.add(new BFSpoint(19, i));
            }
        }
        for (int i = 0; i < 20; i++) {
            if(map.get(i)[0].equals("X")){
                birthPoint.add(new BFSpoint(i, 0));
            }
        }
        for (int i = 0; i < 20; i++) {
            if(map.get(i)[19].equals("X")){
                birthPoint.add(new BFSpoint(i, 19));
            }
        }
        /**
         * Add the coordinates of possible initial monsters to the array.
         */
    }

    private void set_end_point(BFSpoint endPoint) {
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                if (map.get(y)[x].equals("W")) {
                    this.endPoint = new BFSpoint(y, x);
                    return;  
                }
            }
        }
    }
    
    public void draw(App app, int frameCount) {
        boolean gameOver = true;

        for (Wave w : waves) {
            for (Monster m : w.getMonsters()) {
                if (m.getHp() > 0) {
                    gameOver = false;
                    break;
                }
            }
        }
        /**
         * If one monster has more than 0 HP, the game will not end.
         */

        if (gameOver) {
            app.fill(0);
            app.textSize(40);
            app.text("You won!", 280, 350);
            app.pause();
            return;
        }/**
         * endgame
         */
        
        for (int i = 0; i < waves.size(); i++) {
            Wave w = waves.get(i);
            w.draw(app, frameCount);
        }
        /**
         * Draw each wave,
         */
    }
}
