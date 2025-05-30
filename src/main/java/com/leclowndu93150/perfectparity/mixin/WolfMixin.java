package com.leclowndu93150.perfectparity.mixin;

import com.google.common.collect.Maps;
import com.leclowndu93150.perfectparity.entity.utils.WolfSoundVariant;
import com.leclowndu93150.perfectparity.sound.ModSounds;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(Wolf.class)
public class WolfMixin {

    private static final EntityDataAccessor<String> DATA_SOUND_VARIANT_ID = SynchedEntityData.defineId(Wolf.class, EntityDataSerializers.STRING);
    private static final Map<WolfSoundVariant, Map<String, SoundEvent>> SOUND_VARIANT_MAP =
            Util.make(Maps.newEnumMap(WolfSoundVariant.class), map -> {
                map.put(WolfSoundVariant.CLASSIC, ModSounds.WOLF_CLASSIC);
                // Other maps will be added dynamically when available
            });

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void addAdditionSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        compoundTag.putString("sound_variant", this.getTypeSoundVariant());
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void readAdditionSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        ((Entity)(Object)this).getEntityData().set(DATA_SOUND_VARIANT_ID, compoundTag.getString("sound_variant"));
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    protected void defineSynchedData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(DATA_SOUND_VARIANT_ID, "minecraft:classic");
    }

    public WolfSoundVariant getSoundVariant() {
        return WolfSoundVariant.getById(this.getTypeSoundVariant());
    }

    private String getTypeSoundVariant(){
        return ((Entity)(Object)this).getEntityData().get(DATA_SOUND_VARIANT_ID);
    }

    public void setSoundVariant(WolfSoundVariant wolfSoundVariant) {
        ((Entity)(Object)this).getEntityData().set(DATA_SOUND_VARIANT_ID, wolfSoundVariant.getIdentifier());
    }

    @Inject(method = "finalizeSpawn", at = @At("RETURN"))
    public void defineSynchedData(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, SpawnGroupData spawnGroupData, CallbackInfoReturnable<SpawnGroupData> cir) {
        this.setSoundVariant(WolfSoundVariant.getRandom());
    }

    private SoundEvent getSafeSound(String soundType) {
        WolfSoundVariant variant = this.getSoundVariant();

        // First try to get from the static map
        Map<String, SoundEvent> soundMap = SOUND_VARIANT_MAP.get(variant);
        if (soundMap != null && soundMap.get(soundType) != null) {
            return soundMap.get(soundType);
        }

        // If not available, try to get from ModSounds (in case they've been initialized)
        try {
            switch (variant) {
                case PUGLIN:
                    if (ModSounds.WOLF_PUGLIN != null) return ModSounds.WOLF_PUGLIN.get(soundType);
                    break;
                case SAD:
                    if (ModSounds.WOLF_SAD != null) return ModSounds.WOLF_SAD.get(soundType);
                    break;
                case ANGRY:
                    if (ModSounds.WOLF_ANGRY != null) return ModSounds.WOLF_ANGRY.get(soundType);
                    break;
                case GRUMPY:
                    if (ModSounds.WOLF_GRUMPY != null) return ModSounds.WOLF_GRUMPY.get(soundType);
                    break;
                case BIG:
                    if (ModSounds.WOLF_BIG != null) return ModSounds.WOLF_BIG.get(soundType);
                    break;
                case CUTE:
                    if (ModSounds.WOLF_CUTE != null) return ModSounds.WOLF_CUTE.get(soundType);
                    break;
                case CLASSIC:
                default:
                    if (ModSounds.WOLF_CLASSIC != null) return ModSounds.WOLF_CLASSIC.get(soundType);
                    break;
            }
        } catch (Exception e) {
            // Fallback to vanilla sounds if anything goes wrong
        }

        // Final fallback to vanilla sounds
        return switch (soundType) {
            case "growl" -> SoundEvents.WOLF_GROWL;
            case "whine" -> SoundEvents.WOLF_WHINE;
            case "pant" -> SoundEvents.WOLF_PANT;
            case "hurt" -> SoundEvents.WOLF_HURT;
            case "death" -> SoundEvents.WOLF_DEATH;
            default -> SoundEvents.WOLF_AMBIENT;
        };
    }

    /**
     * @author timelord1102
     * @reason adding sound variant logic
     */
    @Overwrite
    public SoundEvent getAmbientSound() {
        if (((NeutralMob)(Object)this).isAngry()) {
            return getSafeSound("growl");
        } else if (((Entity)(Object)this).getRandom().nextInt(3) == 0) {
            return ((TamableAnimal)(Object)this).isTame() && ((LivingEntity)(Object)this).getHealth() < 20.0F ?
                    getSafeSound("whine") : getSafeSound("pant");
        } else {
            return getSafeSound("ambient");
        }
    }

    /**
     * @author timelord1102
     * @reason adding sound variant logic
     */
    @Overwrite
    public SoundEvent getHurtSound(DamageSource damageSource) {
        return ((WolfInvoker) this).callCanArmorAbsorb(damageSource) ? SoundEvents.WOLF_ARMOR_DAMAGE : getSafeSound("hurt");
    }

    /**
     * @author timelord1102
     * @reason adding sound variant logic
     */
    @Overwrite
    public SoundEvent getDeathSound() {
        return getSafeSound("death");
    }

    /**
     * @author timelord1102
     * @reason adding sound variant logic
     */
    @Overwrite
    public Wolf getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        Wolf wolf = (Wolf)EntityType.WOLF.create(serverLevel);
        if (wolf != null && ageableMob instanceof Wolf wolf2) {
            if (((Entity)(Object)this).getRandom().nextBoolean()) {
                wolf.setVariant(((Wolf)(Object)this).getVariant());
            } else {
                wolf.setVariant(wolf2.getVariant());
            }

            if (((Wolf) (Object) this).isTame()) {
                wolf.setOwnerUUID(((Wolf) (Object) this).getOwnerUUID());
                wolf.setTame(true, true);
                if (((Wolf) (Object) this).getRandom().nextBoolean()) {
                    ((WolfInvoker) (Object) wolf).canSetCollarColor(((Wolf) (Object) this).getCollarColor());
                } else {
                    ((WolfInvoker) (Object) wolf).canSetCollarColor(wolf2.getCollarColor());
                }
            }

            ((WolfMixin)(Object)wolf).setSoundVariant(WolfSoundVariant.getRandom());
        }

        return wolf;
    }
}