package hu.elte.inf.szofttech.nameless.model.tower;

public class TowerFactory {

    public enum TowerType {
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

    public static Tower createTower(TowerType towerType, int x, int y) {
        int XP;
        int price;
        int damage;
        int range;
        int attackspeed;
        Tower tower = null;

        switch (towerType) {
            case Basic1:
                XP = 0;
                price = 75;
                range = 10;
                damage = 10;
                attackspeed = 10;
                tower = new Tower("basic1.png",XP,price,damage,range,attackspeed,x,y,new NoSpecialAbility());
                return tower;
            case Basic2:
                XP = 0;
                price = 100;
                range = 12;
                damage = 15;
                attackspeed = 8;
                tower = new Tower("basic2.png",XP,price,damage,range,attackspeed,x,y,new NoSpecialAbility());
                return tower;
            case Basic3:
                XP = 0;
                price = 125;
                range = 8;
                damage = 8;
                attackspeed = 20;
                tower = new Tower("basic3.png",XP,price,damage,range,attackspeed,x,y,new NoSpecialAbility());
                return tower;
            case AdvancedTeleport:
                XP = 15;
                price = 100;
                range = 20;
                damage = 10;
                attackspeed = 12;
                tower = new Tower("advance11.png",XP,price,damage,range,attackspeed,x,y,new Teleport());
                return tower;
            case AdvancedExplosion:
                XP = 20;
                price = 125;
                range = 10;
                damage = 18;
                attackspeed = 10;
                tower = new Tower("advance12.png",XP,price,damage,range,attackspeed,x,y,new Explosion());
                return tower;
            case AdvancedCatchFire:
                XP = 20;
                price = 125;
                range = 18;
                damage = 15;
                attackspeed = 12;
                tower = new Tower("advance21.png",XP,price,damage,range,attackspeed,x,y,new CatchFire());
                return tower;
            case AdvancedRingOfFire:
                XP = 25;
                price = 150;
                range = 12;
                damage = 20;
                attackspeed = 8;
                tower = new Tower("advance22.png",XP,price,damage,range,attackspeed,x,y,new RingOfFire());
                return tower;
            case AdvancedSlowBalloons:
                XP = 25;
                price = 150;
                range = 15;
                damage = 10;
                attackspeed = 20;
                tower = new Tower("advance31.png",XP,price,damage,range,attackspeed,x,y,new SlowBalloons());
                return tower;
            case AdvancedFreezeBalloons:
                XP = 30;
                price = 175;
                range = 8;
                damage = 8;
                attackspeed = 30;
                tower = new Tower("advance32.png",XP,price,damage,range,attackspeed,x,y,new FreezeBalloons());
                return tower;
            default:
                return tower;
        }
    }

}