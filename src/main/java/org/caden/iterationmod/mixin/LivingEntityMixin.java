package org.caden.iterationmod.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import org.caden.iterationmod.Gravity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "createLivingAttributes", at = @At("RETURN"), cancellable = true)
    private static void addCustomAttribute(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        cir.getReturnValue().add(Gravity.GRAVITY_ATTRIBUTE);
    }

    @Inject(method = "tick", at = @At("RETURN"))
    private void onTick(CallbackInfo info) {
        LivingEntity entity = (LivingEntity) (Object) this;
        EntityAttributeInstance attributeInstance = entity.getAttributeInstance(Gravity.GRAVITY_ATTRIBUTE);
        if (attributeInstance != null) {
            double value = attributeInstance.getValue();
            if (!entity.isPlayer() && (!(entity.isOnGround() || entity.isSubmergedInWater() || entity.isFallFlying()) || value<0)) {
                entity.addVelocity(0, 0.08 - (0.08 * value), 0);
            }
        }
    }
}
