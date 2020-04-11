package hu.elte.inf.szofttech.nameless;

import hu.elte.inf.szofttech.nameless.model.Enemy;
import hu.elte.inf.szofttech.nameless.model.Level;
import hu.elte.inf.szofttech.nameless.model.Path;
import hu.elte.inf.szofttech.nameless.model.Wave;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * read path coordinates and enemies information from a file
 */
public class ReadLevels {

    private final List<Level> levelList;

    public ReadLevels() {
        this.levelList = new ArrayList<>();
    }

    public List<Level> getLevelList() {
        return this.levelList;
    }

    public void read() {
        try {
            File myObj = new File("core/assets/levels.txt");
            Scanner myReader = new Scanner(myObj);
            for (int k = 0; k < 5; k++) {
                Path.Builder path = new Path.Builder();
                List<Wave> waveList = new ArrayList<>();
                int num = Integer.parseInt(myReader.next());
                for (int i = 0; i < num / 2; i++) {
                    path.add(Integer.parseInt(myReader.next()), Integer.parseInt(myReader.next()));
                }
                for (int h = 0; h < 10; h++) {
                    Wave.Builder wave = new Wave.Builder(path.build());
                    for (int i = 0; i < 5; i++) {
                        int enemyNum = Integer.parseInt(myReader.next());
                        for (int j = 0; j < enemyNum; j++) {
                            wave.add(Enemy.EnemyType.values()[i]);
                        }
                    }
                    waveList.add(wave.build());
                }
                levelList.add(new Level(path.build(), waveList));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
