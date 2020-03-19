package hu.elte.inf.szofttech.nameless.model;

import com.badlogic.gdx.math.Vector2;
import hu.elte.inf.szofttech.nameless.Utils;

public class EnemyPos {
    private int nextPos;
    private Vector2 pos;
    private Path path;

    public EnemyPos(Path path) {
        //first element of path
        this.pos = Utils.gridToVector(path.get(0));
        this.nextPos = 1;
        this.path = path;
    }

    public Vector2 getPos() {
        return this.pos;
    }

    public void move(int speed,long time) {
        //time in nanoseconds
        //speed in 0.1 square/10^9 nanosecond
        //distance in square
        double distance;
        Vector2 nextPos = Utils.gridToVector(this.path.get(this.nextPos));
        distance = (double)(speed/Math.pow(10,10)) * time;

        if (this.pos.x == nextPos.x) {
            //already end
            if (this.pos.y == nextPos.y) {
                if (this.nextPos != this.path.length()) {
                    this.nextPos ++;
                }
            } else if (this.pos.y > nextPos.y) {
                //moves over the destination
                if (this.pos.y - distance < nextPos.y) {
                    this.pos = nextPos;
                } else {
                    this.pos.y -= distance;
                }
            } else if (this.pos.y < nextPos.y) {
                if (this.pos.y + distance > nextPos.y) {
                    this.pos = nextPos;
                } else {
                    this.pos.y += distance;
                }
            }
        } else if (this.pos.x > nextPos.x) {
            if (this.pos.x - distance < nextPos.x) {
                this.pos = nextPos;
            } else {
                this.pos.x -= distance;
            }
        } else if (this.pos.x < nextPos.x) {
            if (this.pos.x + distance > nextPos.x) {
                this.pos = nextPos;
            } else {
                this.pos.x += distance;
            }
        }
    }

    public boolean end() {
        return this.pos.equals(Utils.gridToVector(this.path.get(this.path.length()-1)));
    }

}
