package com.leclowndu93150.perfectparity.data.gen;

import com.leclowndu93150.perfectparity.PerfectParity;
import com.leclowndu93150.perfectparity.data.worldgen.configurations.FallenTreeConfiguration;
import com.leclowndu93150.perfectparity.data.worldgen.features.ModFeatures;
import com.leclowndu93150.perfectparity.data.worldgen.features.decorators.AttachedToLogsDecorator;
import com.leclowndu93150.perfectparity.world.level.block.ModBlocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, createBuilder(), Set.of(PerfectParity.MOD_ID));
    }

    private static RegistrySetBuilder createBuilder() {
        return new RegistrySetBuilder()
                .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
                .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
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
            // Fallen Oak Tree
            context.register(FALLEN_OAK_TREE, new ConfiguredFeature<>(
                    ModFeatures.FALLEN_TREE.get(),
                    new FallenTreeConfiguration.FallenTreeConfigurationBuilder(
                            BlockStateProvider.simple(Blocks.OAK_LOG),
                            UniformInt.of(3, 6)
                    ).logDecorators(List.of(
                            new AttachedToLogsDecorator(0.25f, BlockStateProvider.simple(ModBlocks.BUSH.get()),
                                    List.of(net.minecraft.core.Direction.UP))
                    )).build()
            ));

            // Bush patches
            context.register(PATCH_BUSH, new ConfiguredFeature<>(
                    Feature.RANDOM_PATCH,
                    new RandomPatchConfiguration(32, 6, 2,
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

            // Leaf litter patches
            context.register(PATCH_LEAF_LITTER, new ConfiguredFeature<>(
                    Feature.RANDOM_PATCH,
                    new RandomPatchConfiguration(24, 4, 3,
                            PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                                    new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.LEAF_LITTER.get()))))
            ));

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

            // Fallen trees - rare spawn in forests
            context.register(FALLEN_OAK_TREE_PLACED, new PlacedFeature(
                    configuredFeatures.getOrThrow(ModConfiguredFeatures.FALLEN_OAK_TREE),
                    List.of(
                            RarityFilter.onAverageOnceEvery(80),
                            InSquarePlacement.spread(),
                            PlacementUtils.HEIGHTMAP,
                            BiomeFilter.biome()
                    )
            ));

            // Bush patches
            context.register(PATCH_BUSH_PLACED, new PlacedFeature(
                    configuredFeatures.getOrThrow(ModConfiguredFeatures.PATCH_BUSH),
                    List.of(
                            RarityFilter.onAverageOnceEvery(5),
                            InSquarePlacement.spread(),
                            PlacementUtils.HEIGHTMAP,
                            BiomeFilter.biome()
                    )
            ));

            // Firefly bush patches
            context.register(PATCH_FIREFLY_BUSH_PLACED, new PlacedFeature(
                    configuredFeatures.getOrThrow(ModConfiguredFeatures.PATCH_FIREFLY_BUSH),
                    List.of(
                            RarityFilter.onAverageOnceEvery(8),
                            InSquarePlacement.spread(),
                            PlacementUtils.HEIGHTMAP,
                            BiomeFilter.biome()
                    )
            ));

            // Leaf litter patches
            context.register(PATCH_LEAF_LITTER_PLACED, new PlacedFeature(
                    configuredFeatures.getOrThrow(ModConfiguredFeatures.PATCH_LEAF_LITTER),
                    List.of(
                            CountPlacement.of(2),
                            InSquarePlacement.spread(),
                            PlacementUtils.HEIGHTMAP,
                            BiomeFilter.biome()
                    )
            ));

            // Dry grass in desert
            context.register(PATCH_DRY_GRASS_DESERT_PLACED, new PlacedFeature(
                    configuredFeatures.getOrThrow(ModConfiguredFeatures.PATCH_DRY_GRASS),
                    List.of(
                            CountPlacement.of(4),
                            InSquarePlacement.spread(),
                            PlacementUtils.HEIGHTMAP,
                            BiomeFilter.biome()
                    )
            ));
        }
    }
}