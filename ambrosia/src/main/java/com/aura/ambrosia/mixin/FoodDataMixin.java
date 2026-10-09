package com.aura.ambrosia.mixin;

import com.aura.ambrosia.effect.ModEffects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Prevents hunger (food level) from decreasing while Nourishment is active,
 * while still allowing saturation to drain.
 * <p>
 * Vanilla {@code FoodData.tick} converts overflow exhaustion ({@code > 4.0})
 * into {@code saturation - 1}, or {@code foodLevel - 1} when saturation is
 * empty. Healing also adds exhaustion internally, which then drains saturation
 * through that same path — that saturation drain must keep working.
 * <p>
 * Instead of targeting a specific bytecode instruction (brittle across
 * versions), this snapshots the food level before the tick and restores it
 * afterwards if it decreased while nourished. Saturation, exhaustion, and
 * tick-timer changes are left untouched, so saturation-powered healing works
 * normally.
 */
@Mixin(FoodData.class)
public abstract class FoodDataMixin {

    @Shadow
    private int foodLevel;

    @Unique
    private int ambrosia$cachedFoodLevel;

    @Unique
    private boolean ambrosia$nourished;

    @Inject(method = "tick", at = @At("HEAD"))
    private void ambrosia$captureFoodLevel(ServerPlayer player, CallbackInfo ci) {
        this.ambrosia$cachedFoodLevel = this.foodLevel;
        this.ambrosia$nourished = player.hasEffect(ModEffects.NOURISHMENT);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void ambrosia$restoreFoodLevel(ServerPlayer player, CallbackInfo ci) {
        if (this.ambrosia$nourished && this.foodLevel < this.ambrosia$cachedFoodLevel) {
            this.foodLevel = this.ambrosia$cachedFoodLevel;
        }
        this.ambrosia$nourished = false;
    }
}
