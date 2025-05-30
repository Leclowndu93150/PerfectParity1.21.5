package com.leclowndu93150.perfectparity.world.item;

import com.leclowndu93150.perfectparity.PerfectParity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, PerfectParity.MODID);

    public static final Supplier<EntityType<CustomThrownEgg>> BROWN_EGG = ENTITY_TYPES.register("brown_egg",
            () -> EntityType.Builder.of(
                            (EntityType<CustomThrownEgg> entityType, net.minecraft.world.level.Level world) -> new CustomThrownEgg(entityType, world, "warm"),
                            MobCategory.MISC
                    )
                    .sized(0.25f, 0.25f)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("brown_egg"));

    public static final Supplier<EntityType<CustomThrownEgg>> BLUE_EGG = ENTITY_TYPES.register("blue_egg",
            () -> EntityType.Builder.of(
                            (EntityType<CustomThrownEgg> entityType, net.minecraft.world.level.Level world) -> new ColdThrownEgg(entityType, world),
                            MobCategory.MISC
                    )
                    .sized(0.25f, 0.25f)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("blue_egg"));

    public static void registerEntityTypes() {
        // Method kept for compatibility
    }
}