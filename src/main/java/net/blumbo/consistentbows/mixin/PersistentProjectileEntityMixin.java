package net.blumbo.consistentbows.mixin;

import net.minecraft.entity.projectile.PersistentProjectileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(PersistentProjectileEntity.class)
public class PersistentProjectileEntityMixin {

    @ModifyArg(method = "onEntityHit", index = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/random/Random;nextInt(I)I"))
    private int setCritRandomBound(int bound) {
        // Removes random extra crit damage
        return 1;
    }

    @ModifyArg(method = "onEntityHit", index = 0, at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(JJ)J"))
    private long setCritDamage(long damage) {
        // Adds extra crit damage
        return damage + damage / 4 + 1;
    }

}
