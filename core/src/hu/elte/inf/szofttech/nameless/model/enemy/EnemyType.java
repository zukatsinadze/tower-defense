package hu.elte.inf.szofttech.nameless.model.enemy;

import com.badlogic.gdx.graphics.Texture;
import hu.elte.inf.szofttech.nameless.Textures;

public enum EnemyType {
    RED(Textures.redBalloon, 1, 5, 3, 10, 10),
    PINK(Textures.pinkBalloon, 2, 8, 5, 15, 15),
    BLUE(Textures.blueBalloon, 4, 10, 10, 8, 30),
    WHITE(Textures.whiteBalloon, 4, 10, 10, 30, 8),
    YELLOW(Textures.yellowBalloon, 5, 15, 15, 25, 25);

    public final Texture texture;
    public final int xp;
    public final int money;
    public final int damage;
    public final int speed;
    public final int maxHealth;

    EnemyType(Texture texture, int xp, int money, int damage, int speed, int maxHealth) {
        this.texture = texture;
        this.xp = xp;
        this.money = money;
        this.damage = damage;
        this.speed = speed;
        this.maxHealth = maxHealth;
    }
}
