package hu.elte.inf.szofttech.nameless.model.tower;

import com.badlogic.gdx.graphics.Texture;
import hu.elte.inf.szofttech.nameless.Textures;

public enum TowerType {
    Basic1(Textures.basic1, 0, 75, 10, 10, 10, new NoSpecialAbility()),
    Basic2(Textures.basic2, 0, 100, 12, 15, 8, new NoSpecialAbility()),
    Basic3(Textures.basic3, 0, 125, 8, 8, 20, new NoSpecialAbility()),
    AdvancedSlow(Textures.advanced11, 15, 100, 20, 10, 12, new SlowBalloons()),
    AdvancedFreeze(Textures.advanced12, 20, 125, 10, 20, 10, new FreezeBalloons()),
    AdvancedTeleport(Textures.advanced21, 20, 125, 18, 15, 12, new Teleport()),
    AdvancedExplosion(Textures.advanced22, 25, 150, 12, 18, 8, new Explosion()),
    AdvancedPoison(Textures.advanced31, 25, 150, 15, 10, 20, new CatchFire()),
    AdvancedFire(Textures.advanced32, 30, 175, 8, 8, 30, new RingOfFire());

    public final Texture texture;
    public final int xp;
    public final int price;
    public final int damage;
    public final int range;
    public final int attackSpeed;
    public final SpecialAbility specialAbility;

    TowerType(Texture texture, int xp, int price, int damage, int range, int attackSpeed,
              SpecialAbility specialAbility) {
        this.texture = texture;
        this.xp = xp;
        this.price = price;
        this.damage = damage;
        this.range = range;
        this.attackSpeed = attackSpeed;
        this.specialAbility = specialAbility;
    }
}
