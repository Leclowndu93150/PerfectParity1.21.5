package com.leclowndu93150.perfectparity.data.gen;

import com.leclowndu93150.perfectparity.PerfectParity;
import com.leclowndu93150.perfectparity.data.worldgen.configurations.FallenTreeConfiguration;
import com.leclowndu93150.perfectparity.data.worldgen.features.ModFeatures;
import com.leclowndu93150.perfectparity.data.worldgen.features.decorators.AttachedToLogsDecorator;
import com.leclowndu93150.perfectparity.world.level.block.ModBlocks;
import com.leclowndu93150.perfectparity.world.level.block.LeafLitterBlock;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModDatapackProvider extends DatapackBuiltinEntriesProvider {

    public ModDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, createBuilder(), Set.of(PerfectParity.MOD_ID));
    }

    private static RegistrySetBuilder createBuilder() {
        return new RegistrySetBuilder()
                .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
                .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
                .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap);
    }

    public static class ModConfiguredFeatures {
        // Configured Feature Keys
        public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_OAK_TREE =
                ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PerfectParity.MOD_ID, "fallen_oak_tree"));

        public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BUSH =
                ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PerfectParity.MOD_ID, "patch_bush"));

        public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FIREFLY_BUSH =
                ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PerfectParity.MOD_ID, "patch_firefly_bush"));

        public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_LEAF_LITTER =
                ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PerfectParity.MOD_ID, "patch_leaf_litter"));

        public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_DRY_GRASS =
                ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PerfectParity.MOD_ID, "patch_dry_grass"));

        public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
            // Fallen Oak Tree - production version
            context.register(FALLEN_OAK_TREE, new ConfiguredFeature<>(
                    ModFeatures.FALLEN_TREE.get(),
                    new FallenTreeConfiguration.FallenTreeConfigurationBuilder(
                            BlockStateProvider.simple(Blocks.OAK_LOG),
                            UniformInt.of(4, 7)  // Match Fabric: 4-7 logs
                    ).logDecorators(List.of(
                            new AttachedToLogsDecorator(0.1f,
                                    new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                            .add(Blocks.RED_MUSHROOM.defaultBlockState(), 2)
                                            .add(Blocks.BROWN_MUSHROOM.defaultBlockState(), 1)
                                            .build()),
                                    List.of(net.minecraft.core.Direction.UP))
                    )).build()
            ));

            // Bush patches - match Fabric configuration
            context.register(PATCH_BUSH, new ConfiguredFeature<>(
                    Feature.RANDOM_PATCH,
                    new RandomPatchConfiguration(24, 5, 3,
                            PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                                    new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.BUSH.get()))))
            ));

            // Firefly bush patches
            context.register(PATCH_FIREFLY_BUSH, new ConfiguredFeature<>(
                    Feature.RANDOM_PATCH,
                    new RandomPatchConfiguration(16, 6, 2,
                            PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                                    new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.FIREFLY_BUSH.get()))))
            ));

            // Leaf litter patches - match Fabric configuration with proper weighted states
            context.register(PATCH_LEAF_LITTER, new ConfiguredFeature<>(
                    Feature.RANDOM_PATCH,
                    new RandomPatchConfiguration(32, 7, 3,
                            PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                                    new SimpleBlockConfiguration(
                                            new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                                    // All 4 facings for segment amounts 1-3
                                                    .add(ModBlocks.LEAF_LITTER.get().defaultBlockState()
                                                            .setValue(LeafLitterBlock.FACING, Direction.NORTH)
                                                            .setValue(LeafLitterBlock.AMOUNT, 1), 1)
                                                    .add(ModBlocks.LEAF_LITTER.get().defaultBlockState()
                                                            .setValue(LeafLitterBlock.FACING, Direction.EAST)
                                                            .setValue(LeafLitterBlock.AMOUNT, 1), 1)
                                                    .add(ModBlocks.LEAF_LITTER.get().defaultBlockState()
                                                            .setValue(LeafLitterBlock.FACING, Direction.SOUTH)
                                                            .setValue(LeafLitterBlock.AMOUNT, 1), 1)
                                                    .add(ModBlocks.LEAF_LITTER.get().defaultBlockState()
                                                            .setValue(LeafLitterBlock.FACING, Direction.WEST)
                                                            .setValue(LeafLitterBlock.AMOUNT, 1), 1)
                                                    .add(ModBlocks.LEAF_LITTER.get().defaultBlockState()
                                                            .setValue(LeafLitterBlock.FACING, Direction.NORTH)
                                                            .setValue(LeafLitterBlock.AMOUNT, 2), 1)
                                                    .add(ModBlocks.LEAF_LITTER.get().defaultBlockState()
                                                            .setValue(LeafLitterBlock.FACING, Direction.EAST)
                                                            .setValue(LeafLitterBlock.AMOUNT, 2), 1)
                                                    .add(ModBlocks.LEAF_LITTER.get().defaultBlockState()
                                                            .setValue(LeafLitterBlock.FACING, Direction.SOUTH)
                                                            .setValue(LeafLitterBlock.AMOUNT, 2), 1)
                                                    .add(ModBlocks.LEAF_LITTER.get().defaultBlockState()
                                                            .setValue(LeafLitterBlock.FACING, Direction.WEST)
                                                            .setValue(LeafLitterBlock.AMOUNT, 2), 1)
                                                    .add(ModBlocks.LEAF_LITTER.get().defaultBlockState()
                                                            .setValue(LeafLitterBlock.FACING, Direction.NORTH)
                                                            .setValue(LeafLitterBlock.AMOUNT, 3), 1)
                                                    .add(ModBlocks.LEAF_LITTER.get().defaultBlockState()
                                                            .setValue(LeafLitterBlock.FACING, Direction.EAST)
                                                            .setValue(LeafLitterBlock.AMOUNT, 3), 1)
                                                    .add(ModBlocks.LEAF_LITTER.get().defaultBlockState()
                                                            .setValue(LeafLitterBlock.FACING, Direction.SOUTH)
                                                            .setValue(LeafLitterBlock.AMOUNT, 3), 1)
                                                    .add(ModBlocks.LEAF_LITTER.get().defaultBlockState()
                                                            .setValue(LeafLitterBlock.FACING, Direction.WEST)
                                                            .setValue(LeafLitterBlock.AMOUNT, 3), 1)
                                                    .build())
                                    ))
                    )));

            // Dry grass patches
            context.register(PATCH_DRY_GRASS, new ConfiguredFeature<>(
                    Feature.RANDOM_PATCH,
                    new RandomPatchConfiguration(32, 6, 2,
                            PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                                    new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.SHORT_DRY_GRASS.get()))))
            ));
        }
    }

    public static class ModPlacedFeatures {
        // Placed Feature Keys
        public static final ResourceKey<PlacedFeature> FALLEN_OAK_TREE_PLACED =
                ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PerfectParity.MOD_ID, "fallen_oak_tree"));

        public static final ResourceKey<PlacedFeature> PATCH_BUSH_PLACED =
                ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PerfectParity.MOD_ID, "patch_bush"));

        public static final ResourceKey<PlacedFeature> PATCH_FIREFLY_BUSH_PLACED =
                ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PerfectParity.MOD_ID, "patch_firefly_bush"));

        public static final ResourceKey<PlacedFeature> PATCH_LEAF_LITTER_PLACED =
                ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PerfectParity.MOD_ID, "patch_leaf_litter"));

        public static final ResourceKey<PlacedFeature> PATCH_DRY_GRASS_DESERT_PLACED =
                ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PerfectParity.MOD_ID, "patch_dry_grass_desert"));

        public static void bootstrap(BootstrapContext<PlacedFeature> context) {
            HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

            // Fallen trees - production version (moderately rare)
            context.register(FALLEN_OAK_TREE_PLACED, new PlacedFeature(
                    configuredFeatures.getOrThrow(ModConfiguredFeatures.FALLEN_OAK_TREE),
                    List.of(
                            RarityFilter.onAverageOnceEvery(80), // More common than Fabric for testing
                            InSquarePlacement.spread(),
                            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                            BiomeFilter.biome()
                    )
            ));

            // Bush patches - match Fabric: rarity 4 = 1/4 chance per chunk
            context.register(PATCH_BUSH_PLACED, new PlacedFeature(
                    configuredFeatures.getOrThrow(ModConfiguredFeatures.PATCH_BUSH),
                    List.of(
                            RarityFilter.onAverageOnceEvery(4),
                            InSquarePlacement.spread(),
                            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                            BiomeFilter.biome()
                    )
            ));

            // Firefly bush patches - match Fabric rarity 8
            context.register(PATCH_FIREFLY_BUSH_PLACED, new PlacedFeature(
                    configuredFeatures.getOrThrow(ModConfiguredFeatures.PATCH_FIREFLY_BUSH),
                    List.of(
                            RarityFilter.onAverageOnceEvery(8),
                            InSquarePlacement.spread(),
                            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                            BiomeFilter.biome()
                    )
            ));

            // Leaf litter patches - match Fabric: count 2
            context.register(PATCH_LEAF_LITTER_PLACED, new PlacedFeature(
                    configuredFeatures.getOrThrow(ModConfiguredFeatures.PATCH_LEAF_LITTER),
                    List.of(
                            CountPlacement.of(2),
                            InSquarePlacement.spread(),
                            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                            BiomeFilter.biome()
                    )
            ));

            // Dry grass in desert - match Fabric rarity 3
            context.register(PATCH_DRY_GRASS_DESERT_PLACED, new PlacedFeature(
                    configuredFeatures.getOrThrow(ModConfiguredFeatures.PATCH_DRY_GRASS),
                    List.of(
                            RarityFilter.onAverageOnceEvery(3),
                            InSquarePlacement.spread(),
                            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                            BiomeFilter.biome()
                    )
            ));
        }
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
                    HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.FALLEN_OAK_TREE_PLACED)),
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
                    HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.PATCH_BUSH_PLACED)),
                    GenerationStep.Decoration.VEGETAL_DECORATION
            ));

            // Add firefly bushes to most overworld biomes (excluding specific ones like the original Fabric code)
            context.register(ADD_FIREFLY_BUSHES, new BiomeModifiers.AddFeaturesBiomeModifier(
                    HolderSet.direct(
                            // Include most temperate biomes but exclude cold, hot, and special biomes
                            biomes.getOrThrow(Biomes.FOREST),
                            biomes.getOrThrow(Biomes.BIRCH_FOREST),
                            biomes.getOrThrow(Biomes.OLD_GROWTH_BIRCH_FOREST),
                            biomes.getOrThrow(Biomes.DARK_FOREST),
                            biomes.getOrThrow(Biomes.PLAINS),
                            biomes.getOrThrow(Biomes.SUNFLOWER_PLAINS),
                            biomes.getOrThrow(Biomes.WINDSWEPT_HILLS),
                            biomes.getOrThrow(Biomes.WINDSWEPT_FOREST),
                            biomes.getOrThrow(Biomes.WINDSWEPT_GRAVELLY_HILLS),
                            biomes.getOrThrow(Biomes.RIVER),
                            biomes.getOrThrow(Biomes.BEACH),
                            biomes.getOrThrow(Biomes.JUNGLE),
                            biomes.getOrThrow(Biomes.SPARSE_JUNGLE),
                            biomes.getOrThrow(Biomes.BAMBOO_JUNGLE),
                            biomes.getOrThrow(Biomes.TAIGA),
                            biomes.getOrThrow(Biomes.OLD_GROWTH_PINE_TAIGA),
                            biomes.getOrThrow(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
                    ),
                    HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.PATCH_FIREFLY_BUSH_PLACED)),
                    GenerationStep.Decoration.VEGETAL_DECORATION
            ));

            // Add leaf litter to forest biomes + meadow
            context.register(ADD_LEAF_LITTER, new BiomeModifiers.AddFeaturesBiomeModifier(
                    HolderSet.direct(
                            biomes.getOrThrow(Biomes.DARK_FOREST),
                            biomes.getOrThrow(Biomes.FOREST),
                            biomes.getOrThrow(Biomes.BIRCH_FOREST),
                            biomes.getOrThrow(Biomes.OLD_GROWTH_BIRCH_FOREST),
                            biomes.getOrThrow(Biomes.MEADOW)
                    ),
                    HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.PATCH_LEAF_LITTER_PLACED)),
                    GenerationStep.Decoration.VEGETAL_DECORATION
            ));

            // Add dry grass to desert
            context.register(ADD_DRY_GRASS_DESERT, new BiomeModifiers.AddFeaturesBiomeModifier(
                    HolderSet.direct(biomes.getOrThrow(Biomes.DESERT)),
                    HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.PATCH_DRY_GRASS_DESERT_PLACED)),
                    GenerationStep.Decoration.VEGETAL_DECORATION
            ));

            // Add dry grass to badlands
            context.register(ADD_DRY_GRASS_BADLANDS, new BiomeModifiers.AddFeaturesBiomeModifier(
                    HolderSet.direct(
                            biomes.getOrThrow(Biomes.BADLANDS),
                            biomes.getOrThrow(Biomes.WOODED_BADLANDS),
                            biomes.getOrThrow(Biomes.ERODED_BADLANDS)
                    ),
                    HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.PATCH_DRY_GRASS_DESERT_PLACED)),
                    GenerationStep.Decoration.VEGETAL_DECORATION
            ));
        }
    }
}