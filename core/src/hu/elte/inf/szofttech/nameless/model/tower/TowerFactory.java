package hu.elte.inf.szofttech.nameless.model.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import hu.elte.inf.szofttech.nameless.model.Enemy;
import hu.elte.inf.szofttech.nameless.model.EnemyPos;
import hu.elte.inf.szofttech.nameless.model.GDSprite;

public class TowerFactory {

    public static enum TowerType {
        Basic1,
        Basic2,
        Basic3,
        AdvancedTeleport,
        AdvancedExplosion,
        AdvancedCatchFire,
        AdvancedRingOfFire,
        AdvancedSlowBalloons,
        AdvancedFreezeBalloons
    }

    public static Tower createTower(TowerType towerType) {
        int XP;
        int price;
        int damage;
        float range;
        int attackspeed;
        GDSprite sprite;
        Tower tower = null;

        switch (towerType) {
            case Basic1:
                XP = 0;
                price = 75;
                range = 10;
                damage = 10;
                attackspeed = 10;
                tower = new Tower("basic1",XP,price,damage,range,attackspeed,new NoSpecialAbility());
                return tower;
            case Basic2:
                XP = 0;
                price = 100;
                range = 12;
                damage = 15;
                attackspeed = 8;
                tower = new Tower("basic2",XP,price,damage,range,attackspeed,new NoSpecialAbility());
                return tower;
            case Basic3:
                XP = 0;
                price = 125;
                range = 8;
                damage = 8;
                attackspeed = 20;
                tower = new Tower("basic3",XP,price,damage,range,attackspeed,new NoSpecialAbility());
                return tower;
            case AdvancedTeleport:
                XP = 15;
                price = 100;
                range = 20;
                damage = 10;
                attackspeed = 12;
                tower = new Tower("AdvancedTeleport",XP,price,damage,range,attackspeed,new Teleport());
                return tower;
            case AdvancedExplosion:
                XP = 20;
                price = 125;
                range = 10;
                damage = 18;
                attackspeed = 10;
                tower = new Tower("AdvancedExplosion",XP,price,damage,range,attackspeed,new Explosion());
                return tower;
            case AdvancedCatchFire:
                XP = 20;
                price = 125;
                range = 18;
                damage = 15;
                attackspeed = 12;
                tower = new Tower("AdvancedCatchFire",XP,price,damage,range,attackspeed,new CatchFire());
                return tower;
            case AdvancedRingOfFire:
                XP = 25;
                price = 150;
                range = 12;
                damage = 20;
                attackspeed = 8;
                tower = new Tower("AdvancedRingOfFire",XP,price,damage,range,attackspeed,new RingOfFire());
                return tower;
            case AdvancedSlowBalloons:
                XP = 25;
                price = 150;
                range = 15;
                damage = 10;
                attackspeed = 20;
                tower = new Tower("AdvancedSlowBalloons",XP,price,damage,range,attackspeed,new SlowBalloons());
                return tower;
            case AdvancedFreezeBalloons:
                XP = 30;
                price = 175;
                range = 8;
                damage = 8;
                attackspeed = 30;
                tower = new Tower("AdvancedFreezeBalloons",XP,price,damage,range,attackspeed,new FreezeBalloons());
                return tower;
            default:
                return tower;
        }
    }

}