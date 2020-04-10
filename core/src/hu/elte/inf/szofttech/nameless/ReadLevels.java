package hu.elte.inf.szofttech.nameless;

import hu.elte.inf.szofttech.nameless.model.Enemy;
import hu.elte.inf.szofttech.nameless.model.Path;
import hu.elte.inf.szofttech.nameless.model.Wave;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files


/**
 *  read path coordinates and enemies information from a file
 */
public class ReadLevels {

    private Path.Builder path;
    private Wave.Builder wave;

    public ReadLevels() {
        this.path = new Path.Builder();
        this.wave = new Wave.Builder(this.path.build());
    }

    public Path getPath() {
        return this.path.build();
    }

    public Wave getWave() {
        return this.wave.build();
    }

    public void read() {
        try {
            File myObj = new File("core/assets/levels.txt");
            Scanner myReader = new Scanner(myObj);
            int num = Integer.parseInt(myReader.next());
            for (int i = 0; i < num / 2; i++) {
                path.add(Integer.parseInt(myReader.next()),Integer.parseInt(myReader.next()));
            }
            for (int i = 0; i < 5; i++) {
                int enemyNum = Integer.parseInt(myReader.next());
                for (int j = 0; j < enemyNum; j++) {
                    wave.add(Enemy.EnemyType.values()[i]);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
