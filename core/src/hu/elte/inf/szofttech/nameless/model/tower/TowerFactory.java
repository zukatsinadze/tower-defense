package hu.elte.inf.szofttech.nameless.model.tower;

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
        int range;
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
                tower = new Tower("tower1.png",XP,price,damage,range,attackspeed,new NoSpecialAbility());
                return tower;
            case Basic2:
                XP = 0;
                price = 100;
                range = 12;
                damage = 15;
                attackspeed = 8;
                tower = new Tower("tower2.png",XP,price,damage,range,attackspeed,new NoSpecialAbility());
                return tower;
            case Basic3:
                XP = 0;
                price = 125;
                range = 8;
                damage = 8;
                attackspeed = 20;
                tower = new Tower("tower3.png",XP,price,damage,range,attackspeed,new NoSpecialAbility());
                return tower;
            case AdvancedTeleport:
                XP = 15;
                price = 100;
                range = 20;
                damage = 10;
                attackspeed = 12;
                tower = new Tower("tower4.png",XP,price,damage,range,attackspeed,new Teleport());
                return tower;
            case AdvancedExplosion:
                XP = 20;
                price = 125;
                range = 10;
                damage = 18;
                attackspeed = 10;
                tower = new Tower("tower5.png",XP,price,damage,range,attackspeed,new Explosion());
                return tower;
            case AdvancedCatchFire:
                XP = 20;
                price = 125;
                range = 18;
                damage = 15;
                attackspeed = 12;
                tower = new Tower("tower6.png",XP,price,damage,range,attackspeed,new CatchFire());
                return tower;
            case AdvancedRingOfFire:
                XP = 25;
                price = 150;
                range = 12;
                damage = 20;
                attackspeed = 8;
                tower = new Tower("tower7.png",XP,price,damage,range,attackspeed,new RingOfFire());
                return tower;
            case AdvancedSlowBalloons:
                XP = 25;
                price = 150;
                range = 15;
                damage = 10;
                attackspeed = 20;
                tower = new Tower("tower8.png",XP,price,damage,range,attackspeed,new SlowBalloons());
                return tower;
            case AdvancedFreezeBalloons:
                XP = 30;
                price = 175;
                range = 8;
                damage = 8;
                attackspeed = 30;
                tower = new Tower("tower9.png",XP,price,damage,range,attackspeed,new FreezeBalloons());
                return tower;
            default:
                return tower;
        }
    }

}