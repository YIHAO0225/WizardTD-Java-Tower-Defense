package WizardTD;
import java.io.IOException;
import java.util.ArrayList;
import org.checkerframework.checker.units.qual.degrees;
import processing.core.PApplet;
import processing.core.PImage;

public class Map{
    private PImage grass;
    private PImage shrub;
    private PImage path;
    private PImage pathCorner;
    private PImage pathTjunction;
    private PImage pathCrossroads;

    /**
     * creat some feild for the image
     */
    private int pathDegree;
    private int pathCornerDegree;
    private int pathTjunctionDegree;
    /**
     * creat some feild to save the image degree
     */
    private ArrayList<String[]> map = new ArrayList<>();
    /**
     * save information og the map
     */

    public Map(){
    }

    public void setGrass(PImage grass) {
        this.grass = grass;
    }

    public void setPath(PImage path) {
        this.path = path;
    }

    public void setShrub(PImage shrub) {
        this.shrub = shrub;
    }

    public void setPathCorner(PImage pathCorner) {
        this.pathCorner = pathCorner;
    }

    public void setPathTjunction(PImage pathTjunction) {
        this.pathTjunction = pathTjunction;
    }

    public void setPathCrossroads(PImage pathCrossroads) {
        this.pathCrossroads = pathCrossroads;
    }

    public void setMap(ArrayList<String[]> map) {
        this.map = map;
    }

    /**
     * set those feild
     */

    private int straight_path_degree(ArrayList<String[]> map, int y, int x ) {
        App app = new App();
        if ((x - 1) >= 0 && (x + 1) < 20 && map.get(y)[x - 1].equals("X")
                                            && map.get(y)[x + 1].equals("X")) {
            return 0;
        }
        if ((y - 1) >= 0 && (y + 1) < 20 && !map.get(y - 1)[x].equals("X")
                && !map.get(y + 1)[x].equals("X")) {
            return 0;
        }
        if ((y - 1) >= 0 && (y + 1) < 20 && map.get(y - 1)[x].equals("X")
                                            && map.get(y + 1)[x].equals("X")) {
            return 90;
        }
        if ((x - 1) >= 0 && (x + 1) < 20 && !map.get(y)[x - 1].equals("X") 
                                            && !map.get(y)[x + 1].equals("X")) {
            return 90;
        }
        /**
         *If the left and right are both X
         * the degree dont change
         * if  top and bottom are bath X
         * thse degree change to 90
         */
        return -1;
    }

    private int path_is_corner(ArrayList<String[]> map, int y, int x ) {
        if((y + 1) < 20 && (x - 1) >= 0){
            if(map.get(y + 1)[x].equals ("X") && map.get(y)[x - 1].equals ("X")){
                return 0;
            }
        }/**
         *If the left and bottom are both X
         * the degree dont change
         */
        if((y - 1) >= 0 && (x - 1) >= 0){
            if(map.get(y - 1)[x].equals ("X") && map.get(y)[x - 1].equals ("X")){
                return 90;
            }
        }/**
         *If the left and top are both X
         * the degree  change to 90
         */
        if((y - 1) >= 0 && (x + 1) < 20){
            if(map.get(y - 1)[x].equals ("X") && map.get(y)[x + 1].equals ("X")){
                return 180;
            }
        }/**
         *If the right and top are both X
         * the degree  change to 180
         */
        if((y + 1) < 20 && (x + 1) < 20){
            if(map.get(y + 1)[x].equals ("X") && map.get(y)[x + 1].equals ("X")){
                return 270;
            }
        }/**
         *If the right and bottom are both X
         * the degree  change to 270
         */

        return -1;
    }

    private int path_is_tjunction(ArrayList<String[]> map, int y, int x ) {
        if((y + 1) < 20 && (x - 1) >= 0 && (x + 1) < 20 ){
            if(map.get(y + 1)[x].equals ("X") && map.get(y)[x - 1].equals ("X")
                                                        && map.get(y)[x + 1].equals("X")){
                return 0;
            }
        }/**
         *If the left and bottom  and rignt are both X
         * the degree dont change
         */
        if((y - 1) >= 0 && (x - 1) >= 0 && (y + 1) < 20){
            if(map.get(y - 1)[x].equals ("X") && map.get(y)[x - 1].equals ("X") 
                                                        && map.get(y + 1)[x].equals("X")){
                return 90;
            }
        }/**
         *If the left and bottom  and up are both X
         * the degree change 90
         */
        if((y - 1) >= 0 && (x + 1) < 20 && (x - 1) >=0 ){
            if(map.get(y - 1)[x].equals ("X") && map.get(y)[x + 1].equals ("X") 
                                                        && map.get(y)[x - 1].equals("X")){
                return 180;
            }
        }/**
         *If the left and right  and up are both X
         * the degree change 180
         */
        if((y + 1) < 20 && (x + 1) < 20 && (y - 1) >=0){
            if(map.get(y + 1)[x].equals ("X") && map.get(y)[x + 1].equals ("X") 
                                                        && map.get(y - 1)[x].equals("X")){
                return 270;
            }
        }/**
         *If the bottom and right  and up are both X
         * the degree change 270
         */
        return -1;
    }

    private boolean path_is_crossroad(ArrayList<String[]> map, int y, int x ){
        if((y - 1) >= 0 && (x + 1) < 20 && (x - 1) >=0 && (y + 1) < 20 ){
            if(map.get(y - 1)[x].equals ("X") && map.get(y)[x + 1].equals ("X")
            && map.get(y)[x - 1].equals("X") && map.get(y + 1)[x].equals("X") ){
                return true;
            }
        }/**
         *If the bottom and right  and up  and left are both X
         * return true
         */
        return false;
    }

    public void draw(App app) {
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                if (map.get(y)[x].equals("X")) {
                    pathDegree = straight_path_degree(map, y, x);
                    pathCornerDegree = path_is_corner(map, y, x);
                    pathTjunctionDegree = path_is_tjunction(map,y,x);
                    if (pathDegree >= 0){
                        PImage pathIsStraight = app.rotateImageByDegrees(path, pathDegree);
                        app.image(pathIsStraight, x * 32, y * 32 + 40);
                    }
                    if (pathCornerDegree >= 0) {
                        PImage pathIsCorner = app.rotateImageByDegrees(pathCorner, pathCornerDegree);
                        app.image(pathIsCorner, x * 32, y * 32 + 40);
                    } 
                     if (pathTjunctionDegree >= 0){
                        PImage pathIsTjunction = app.rotateImageByDegrees(pathTjunction, pathTjunctionDegree);
                        app.image(pathIsTjunction, x * 32, y * 32 + 40);
                    }
                    if(path_is_crossroad(map, y, x)){
                        app.image(pathCrossroads, x * 32, y * 32 + 40);
                    }
                }else if(map.get(y)[x].equals("S")) {
                    app.image(shrub, x * 32 , y * 32 + 40);
                }else{
                    app.image(grass, x * 32 , y * 32 + 40);
                }
            }
        }
        /**
         *draw  different image depend on diffent letter
         */
    }   
}