package hu.elte.inf.szofttech.nameless.model.enemy;

public enum EffectType {
    SLOW(3, new EffectAction() {
        @Override
        public double modifySpeed(int speed) {
            return speed * 0.5f;
        }
    }),
    FREEZE(1, new EffectAction() {
        @Override
        public double modifySpeed(int speed) {
            return 0;
        }
    }),
    POISON(5, new EffectAction() {
        @Override
        public void affectEnemy(Enemy enemy) {
            enemy.loseHealth(2);
        }
    });

    public final float time;
    public final EffectAction action;

    EffectType(float time, EffectAction action) {
        this.time = time;
        this.action = action;
    }
}
