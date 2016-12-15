package ru.mail.park.models;

/**
 * Created by viacheslav on 14.12.16.
 */
import java.util.Random;


public class TestMap {

    int width = 100, height = 100;

    float chanceToStartAlive = 0.45f;

    Random rnd = new Random();

    public int deathLimit = 5, //5
            birthLimit = 3,    //3
            numberOfSteps = 6;  //6

    boolean[][] myMap;

    public boolean[][] initialiseMap(boolean[][] map){
        for(int x=0; x<width; x++){
            for(int y=0; y<height; y++){
                if(rnd.nextFloat() < chanceToStartAlive) map[x][y] = true;
            }
        }
        return map;
    }

    public int countAliveNeighbours(boolean[][] map, int x, int y){
        int count = 0;
        for(int i=-1; i<2; i++){
            for(int j=-1; j<2; j++){
                int neighbour_x = x+i;
                int neighbour_y = y+j;
                //If we're looking at the middle point
                if(i == 0 && j == 0){
                }
                else if(neighbour_x < 0 || neighbour_y < 0 || neighbour_x >= map.length || neighbour_y >= map[0].length){
                    count = count + 1;
                }
                else if(map[neighbour_x][neighbour_y]){
                    count = count + 1;
                }
            }
        }
        return count;
    }

    public boolean[][] doSimulationStep(boolean[][] oldMap){
        boolean[][] newMap = new boolean[width][height];
        for(int x=0; x<oldMap.length; x++){
            for(int y=0; y<oldMap[0].length; y++){
                int nbs = countAliveNeighbours(oldMap, x, y);
                if(oldMap[x][y]){
                    if(nbs < deathLimit){
                        newMap[x][y] = false;
                    } else {
                        newMap[x][y] = true;
                    }
                }
                else{
                    if(nbs > birthLimit){
                        newMap[x][y] = true;
                    } else {
                        newMap[x][y] = false;
                    }
                }
            }
        }
        return newMap;
    }

    public void generateMap(){
        boolean[][] cellmap = new boolean[width][height];
        cellmap = initialiseMap(cellmap);
        for(int i=0; i<numberOfSteps; i++){
            cellmap = doSimulationStep(cellmap);
        }
        this.myMap = cellmap;
    }

    public boolean[][] getMyMap(){
        return myMap;
    }

}

