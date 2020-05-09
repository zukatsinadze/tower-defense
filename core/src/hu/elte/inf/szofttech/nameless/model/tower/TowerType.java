package hu.elte.inf.szofttech.nameless.model.tower;

import com.badlogic.gdx.graphics.Texture;
import hu.elte.inf.szofttech.nameless.Textures;

public enum TowerType {
    Basic1(Textures.basic1, 0, 75, 10, 10, 10, new NormalAttack()),
    Basic2(Textures.basic2, 0, 100, 12, 15, 8, new NormalAttack()),
    Basic3(Textures.basic3, 0, 125, 8, 8, 20, new NormalAttack()),
    AdvancedSlow(Textures.advanced11, 15, 100, 20, 10, 12, new SlowAttack()),
    AdvancedFreeze(Textures.advanced12, 20, 125, 10, 20, 10, new FreezeAttack()),
    AdvancedTeleport(Textures.advanced21, 20, 125, 18, 15, 12, new TeleportAttack()),
    AdvancedExplosion(Textures.advanced22, 25, 150, 12, 18, 8, new ExplosionAttack()),
    AdvancedPoison(Textures.advanced31, 25, 150, 15, 10, 20, new PoisonAttack()),
    AdvancedFire(Textures.advanced32, 30, 175, 8, 8, 30, new FireAttack());

    //public final String name;
    public final Texture texture;
    public final int xp;
    public final int price;
    public final int damage;
    public final int range;
    public final int attackSpeed;
    public final AttackAbility attackAbility;

    TowerType(Texture texture, int xp, int price, int damage, int range, int attackSpeed,
              AttackAbility attackAbility) {
        //this.name = name;
        this.texture = texture;
        this.xp = xp;
        this.price = price;
        this.damage = damage;
        this.range = range;
        this.attackSpeed = attackSpeed;
        this.attackAbility = attackAbility;
    }
}
