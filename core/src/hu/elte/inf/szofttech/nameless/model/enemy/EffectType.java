package hu.elte.inf.szofttech.nameless.model.enemy;

import java.util.function.Supplier;

public enum EffectType {
    SLOW(SlowEffect::new),
    FREEZE(FreezeEffect::new),
    POISON(PoisonEffect::new);

    public final Supplier<? extends EnemyEffect> effect;

    EffectType(Supplier<? extends EnemyEffect> effect) {
        this.effect = effect;
    }
}
