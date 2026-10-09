package com.aura.ambrosia.mixin;

import com.aura.ambrosia.effect.ModEffects;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Blocks all activity-based exhaustion while Nourishment is active.
 * <p>
 * All vanilla exhaustion sources (sprinting, jumping, swimming, attacking,
 * taking damage, Hunger effect, etc.) funnel through
 * {@link Player#causeFoodExhaustion(float)}. Cancelling here keeps exhaustion
 * at zero so it can never overflow into saturation/hunger drain.
 * <p>
 * Exhaustion added internally by {@code FoodData.tick} for natural healing is
 * NOT blocked (it calls {@code FoodData.addExhaustion} directly), so saturation
 * is still consumed to heal while hunger itself is protected by
 * {@link FoodDataMixin}.
 */
@Mixin(Player.class)
public abstract class NourishmentExhaustionMixin {

    @Inject(method = "causeFoodExhaustion", at = @At("HEAD"), cancellable = true)
    private void ambrosia$blockExhaustion(float exhaustion, CallbackInfo ci) {
        Player self = (Player) (Object) this;
        if (self.hasEffect(ModEffects.NOURISHMENT)) {
            ci.cancel();
        }
    }
}
