package hu.elte.inf.szofttech.nameless.model.enemy;

import com.badlogic.gdx.math.Vector2;
import hu.elte.inf.szofttech.nameless.Utils;
import hu.elte.inf.szofttech.nameless.model.Path;

/**
 * handle the position of enemy
 */
public class EnemyPos {
    private int next;
    private Path path;
    private Vector2 pos;

    public EnemyPos(Path path) {
        // first element of path
        this.pos = Utils.gridToVector(path.getFirst());
        this.next = 1;
        this.path = path;
    }

    public Vector2 getPos() {
        return this.pos;
    }

    public void move(double speed, float delta) {
        // time in seconds
        // speed in 0.1 square/second
        // distance in square
        double distance = speed * 0.1 * delta;
        Vector2 nextPos = Utils.gridToVector(this.path.get(this.next));

        if (this.pos.x == nextPos.x) {
            // already moves to the end
            if (this.pos.y == nextPos.y) {
                if (this.next != this.path.length() - 1) {
                    this.next++;
                }
            } else if (this.pos.y > nextPos.y) {
                // moves over the destination
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

    public boolean ended() {
        return this.pos.equals(Utils.gridToVector(this.path.getLast()));
    }

    public void teleportBack() {
        this.next = Math.max(1, this.next - 3);
        this.pos = Utils.gridToVector(this.path.get(this.next - 1));
    }
}
