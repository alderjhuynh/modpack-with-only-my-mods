package com.aura.ambrosia.mixin;

import com.aura.ambrosia.effect.ModEffects;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Allows eating any food while Famished is active, regardless of hunger level.
 * <p>
 * Vanilla gates food consumption in {@code Consumable.canConsume}, which
 * delegates to {@code Player.canEat(canAlwaysEat)} for stacks with a food
 * component. Forcing {@code canEat} to true while famished opens every food
 * item; non-food consumables already return true from {@code canConsume} and
 * are unaffected.
 */
@Mixin(Player.class)
public abstract class FamishedCanEatMixin {

    @Inject(method = "canEat", at = @At("HEAD"), cancellable = true)
    private void ambrosia$allowEatWhenFamished(boolean canAlwaysEat, CallbackInfoReturnable<Boolean> cir) {
        Player self = (Player) (Object) this;
        if (self.hasEffect(ModEffects.FAMISHED)) {
            cir.setReturnValue(true);
        }
    }
}
