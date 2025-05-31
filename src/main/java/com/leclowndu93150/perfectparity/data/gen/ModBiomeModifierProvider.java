package com.leclowndu93150.perfectparity.data.gen;

import com.leclowndu93150.perfectparity.PerfectParity;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModBiomeModifierProvider extends DatapackBuiltinEntriesProvider {

    public ModBiomeModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, createBuilder(), Set.of(PerfectParity.MOD_ID));
    }

    private static RegistrySetBuilder createBuilder() {
        return new RegistrySetBuilder()
                .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap);
    }

    public static class ModBiomeModifiers {

        public static final ResourceKey<BiomeModifier> ADD_FALLEN_TREES = ResourceKey.create(
                NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                ResourceLocation.fromNamespaceAndPath(PerfectParity.MOD_ID, "add_fallen_trees"));

        public static final ResourceKey<BiomeModifier> ADD_BUSHES = ResourceKey.create(
                NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                ResourceLocation.fromNamespaceAndPath(PerfectParity.MOD_ID, "add_bushes"));

        public static final ResourceKey<BiomeModifier> ADD_FIREFLY_BUSHES = ResourceKey.create(
                NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                ResourceLocation.fromNamespaceAndPath(PerfectParity.MOD_ID, "add_firefly_bushes"));

        public static final ResourceKey<BiomeModifier> ADD_LEAF_LITTER = ResourceKey.create(
                NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                ResourceLocation.fromNamespaceAndPath(PerfectParity.MOD_ID, "add_leaf_litter"));

        public static final ResourceKey<BiomeModifier> ADD_DRY_GRASS_DESERT = ResourceKey.create(
                NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                ResourceLocation.fromNamespaceAndPath(PerfectParity.MOD_ID, "add_dry_grass_desert"));

        public static final ResourceKey<BiomeModifier> ADD_DRY_GRASS_BADLANDS = ResourceKey.create(
                NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                ResourceLocation.fromNamespaceAndPath(PerfectParity.MOD_ID, "add_dry_grass_badlands"));

        public static void bootstrap(BootstrapContext<BiomeModifier> context) {
            var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
            var biomes = context.lookup(Registries.BIOME);

            // Add fallen trees to forest biomes
            context.register(ADD_FALLEN_TREES, new BiomeModifiers.AddFeaturesBiomeModifier(
                    HolderSet.direct(
                            biomes.getOrThrow(Biomes.FOREST),
                            biomes.getOrThrow(Biomes.BIRCH_FOREST),
                            biomes.getOrThrow(Biomes.OLD_GROWTH_BIRCH_FOREST),
                            biomes.getOrThrow(Biomes.DARK_FOREST)
                    ),
                    HolderSet.direct(placedFeatures.getOrThrow(ModWorldGenProvider.ModPlacedFeatures.FALLEN_OAK_TREE_PLACED)),
                    GenerationStep.Decoration.VEGETAL_DECORATION
            ));

            // Add bushes to various biomes
            context.register(ADD_BUSHES, new BiomeModifiers.AddFeaturesBiomeModifier(
                    HolderSet.direct(
                            biomes.getOrThrow(Biomes.BIRCH_FOREST),
                            biomes.getOrThrow(Biomes.OLD_GROWTH_BIRCH_FOREST),
                            biomes.getOrThrow(Biomes.FOREST),
                            biomes.getOrThrow(Biomes.PLAINS),
                            biomes.getOrThrow(Biomes.WINDSWEPT_HILLS),
                            biomes.getOrThrow(Biomes.WINDSWEPT_GRAVELLY_HILLS),
                            biomes.getOrThrow(Biomes.WINDSWEPT_FOREST),
                            biomes.getOrThrow(Biomes.RIVER),
                            biomes.getOrThrow(Biomes.FROZEN_RIVER)
                    ),
                    HolderSet.direct(placedFeatures.getOrThrow(ModWorldGenProvider.ModPlacedFeatures.PATCH_BUSH_PLACED)),
                    GenerationStep.Decoration.VEGETAL_DECORATION
            ));

            // Add firefly bushes to most overworld biomes (excluding specific ones)
            context.register(ADD_FIREFLY_BUSHES, new BiomeModifiers.AddFeaturesBiomeModifier(
                    biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                    HolderSet.direct(placedFeatures.getOrThrow(ModWorldGenProvider.ModPlacedFeatures.PATCH_FIREFLY_BUSH_PLACED)),
                    GenerationStep.Decoration.VEGETAL_DECORATION
            ));

            // Add leaf litter to forest biomes
            context.register(ADD_LEAF_LITTER, new BiomeModifiers.AddFeaturesBiomeModifier(
                    HolderSet.direct(
                            biomes.getOrThrow(Biomes.DARK_FOREST),
                            biomes.getOrThrow(Biomes.FOREST),
                            biomes.getOrThrow(Biomes.BIRCH_FOREST),
                            biomes.getOrThrow(Biomes.OLD_GROWTH_BIRCH_FOREST)
                    ),
                    HolderSet.direct(placedFeatures.getOrThrow(ModWorldGenProvider.ModPlacedFeatures.PATCH_LEAF_LITTER_PLACED)),
                    GenerationStep.Decoration.VEGETAL_DECORATION
            ));

            // Add dry grass to desert
            context.register(ADD_DRY_GRASS_DESERT, new BiomeModifiers.AddFeaturesBiomeModifier(
                    HolderSet.direct(biomes.getOrThrow(Biomes.DESERT)),
                    HolderSet.direct(placedFeatures.getOrThrow(ModWorldGenProvider.ModPlacedFeatures.PATCH_DRY_GRASS_DESERT_PLACED)),
                    GenerationStep.Decoration.VEGETAL_DECORATION
            ));

            // Add dry grass to badlands
            context.register(ADD_DRY_GRASS_BADLANDS, new BiomeModifiers.AddFeaturesBiomeModifier(
                    HolderSet.direct(
                            biomes.getOrThrow(Biomes.BADLANDS),
                            biomes.getOrThrow(Biomes.WOODED_BADLANDS),
                            biomes.getOrThrow(Biomes.ERODED_BADLANDS)
                    ),
                    HolderSet.direct(placedFeatures.getOrThrow(ModWorldGenProvider.ModPlacedFeatures.PATCH_DRY_GRASS_DESERT_PLACED)),
                    GenerationStep.Decoration.VEGETAL_DECORATION
            ));
        }
    }
}