package hu.elte.inf.szofttech.nameless;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import hu.elte.inf.szofttech.nameless.model.Path;
import hu.elte.inf.szofttech.nameless.model.Wave;
import hu.elte.inf.szofttech.nameless.model.Level;
import hu.elte.inf.szofttech.nameless.model.enemy.EnemyType;

/**
 * Reading path coordinates and enemies information from a file
 */
public final class ReadLevels {
    private ReadLevels() {
    }

    public static List<Level> read() {
        List<Level> levels = new ArrayList<>(5);
        try (Scanner myReader = new Scanner(Gdx.files.internal("levels.txt").reader())) {
            for (int k = 0; k < 5; k++) {
                Path.Builder path = new Path.Builder();
                List<Wave> waveList = new ArrayList<>();
                int num = Integer.parseInt(myReader.next());
                for (int i = 0; i < num; i++) {
                    path.add(Integer.parseInt(myReader.next()), Integer.parseInt(myReader.next()));
                }
                for (int h = 0; h < 10; h++) {
                    Wave.Builder wave = new Wave.Builder(path.build());
                    for (int i = 0; i < 5; i++) {
                        int enemyNum = Integer.parseInt(myReader.next());
                        for (int j = 0; j < enemyNum; j++) {
                            wave.add(EnemyType.values()[i]);
                        }
                    }
                    waveList.add(wave.build());
                }
                levels.add(new Level(path.build(), waveList));
            }
        }
        return levels;
    }
}
