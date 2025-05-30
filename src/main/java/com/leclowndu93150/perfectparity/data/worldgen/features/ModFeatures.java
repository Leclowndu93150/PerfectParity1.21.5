package com.leclowndu93150.perfectparity.data.worldgen.features;

import com.leclowndu93150.perfectparity.PerfectParity;
import com.leclowndu93150.perfectparity.data.worldgen.configurations.FallenTreeConfiguration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, PerfectParity.MOD_ID);

    public static final Supplier<Feature<FallenTreeConfiguration>> FALLEN_TREE = FEATURES.register("fallen_tree",
            () -> new FallenTreeFeature(FallenTreeConfiguration.CODEC));

    public static void registerFeatures() {
        PerfectParity.LOGGER.info("PerfectParity: Registering custom features");
    }
}