package hu.elte.inf.szofttech.nameless.model;

import com.badlogic.gdx.math.Vector2;

public class EnemyPos {
    private int nextPos;
    private Vector2 pos;
    private Path path;

    public EnemyPos(Path path) {
        this.pos = new Vector2(path.get(0).x,path.get(0).y);
        this.nextPos = 1;
        this.path = path;
    }

    public Vector2 getPos() {
        return this.pos;
    }

    public void move(int speed) {

    }

}
