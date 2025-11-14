package WizardTD;

import java.util.ArrayList;

public class Wave {
    private int duration;
    private double preWavePause;
    private ArrayList<Monster> monsters;
    /**
     * feild for each wave
     */

    public Wave(){}

    public Wave(int duration, double preWavePause, ArrayList<Monster> monsters) {
        this.duration = duration;
        this.preWavePause = preWavePause;
        this.monsters = monsters;
    }

    

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }



    public void draw(App app, int frameCount) {
        if(frameCount >= preWavePause * 60){
            for (int i = 0; i < monsters.size(); i++) {
            Monster m = monsters.get(i);
            m.draw(app,frameCount);
            }
        }
        /**
         * Draw each monster.
         */
    }   
}
