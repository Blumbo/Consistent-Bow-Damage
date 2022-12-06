package net.blumbo.consistentbow.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin extends ProjectileEntity {

    @Shadow private double damage;

    public PersistentProjectileEntityMixin(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyArg(method = "onEntityHit", index = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/random/Random;nextInt(I)I"))
    private int setCritRandomBound(int bound) {
        // Removes random extra crit damage
        return 1;
    }

    @ModifyArg(method = "onEntityHit", index = 0, at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(JJ)J"))
    private long setCritDamage(long vanillaDamage) {

        // Slightly lower than vanilla
        int nerfedDamage = MathHelper.ceil(MathHelper.clamp(getVelocity().length() * damage - 0.8f, 0.0D, 2.147483647E9D));

        return nerfedDamage + (nerfedDamage + 3) / 4 + 1;
    }

}
