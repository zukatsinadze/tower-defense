package hu.elte.inf.szofttech.nameless.model.tower;

import com.badlogic.gdx.graphics.Texture;
import hu.elte.inf.szofttech.nameless.Textures;

public enum TowerType {
    Basic1("basic1", Textures.basic1, 0, 75, 10, 10, 10, new NormalAttack()),
    Basic2("basic2", Textures.basic2, 0, 100, 15, 12, 8, new NormalAttack()),
    Basic3("basic3", Textures.basic3, 0, 125, 8, 8, 20, new NormalAttack()),
    AdvancedSlow("Slow", Textures.advanced11, 15, 100, 10, 20, 12, new SlowAttack()),
    AdvancedFreeze("Freeze", Textures.advanced12, 20, 125, 20, 10, 10, new FreezeAttack()),
    AdvancedTeleport("Teleport", Textures.advanced21, 20, 125, 15, 18, 12, new TeleportAttack()),
    AdvancedExplosion("Explosion", Textures.advanced22, 25, 150, 18, 12, 8, new ExplosionAttack()),
    AdvancedPoison("Poison", Textures.advanced31, 25, 150, 10, 15, 20, new PoisonAttack()),
    AdvancedFire("Fire", Textures.advanced32, 30, 175, 8, 8, 30, new FireAttack());

    public final String name;
    public final Texture texture;
    public final int xp;
    public final int price;
    public final int range;
    public final int damage;
    public final int attackSpeed;
    public final AttackAbility attackAbility;

    TowerType(String name, Texture texture, int xp, int price, int range, int damage, int attackSpeed,
              AttackAbility attackAbility) {
        this.name = name;
        this.texture = texture;
        this.xp = xp;
        this.price = price;
        this.range = range;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.attackAbility = attackAbility;
    }
}
