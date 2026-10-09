package com.aura.ambrosia.effect;

import com.aura.ambrosia.Ambrosia;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ModEffects {
    public static final Holder<MobEffect> NOURISHMENT = register(
            "nourishment",
            new NourishmentEffect(MobEffectCategory.BENEFICIAL, 0xE8B64A)
    );

    public static final Holder<MobEffect> FAMISHED = register(
            "famished",
            new FamishedEffect(MobEffectCategory.BENEFICIAL, 0xCC5A2A)
    );

    private static Holder<MobEffect> register(String path, MobEffect effect) {
        return Registry.registerForHolder(
                BuiltInRegistries.MOB_EFFECT,
                Ambrosia.id(path),
                effect
        );
    }

    public static void register() {
    }
}
