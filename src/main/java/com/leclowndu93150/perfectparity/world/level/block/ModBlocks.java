package com.leclowndu93150.perfectparity.world.level.block;

import com.leclowndu93150.perfectparity.PerfectParity;
import com.leclowndu93150.perfectparity.sound.ModSounds;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, PerfectParity.MOD_ID);

    public static final Supplier<Block> BUSH = BLOCKS.register("bush",
            () -> new ModBushBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .replaceable()
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY)));

    public static final Supplier<Block> CACTUS_FLOWER = BLOCKS.register("cactus_flower",
            () -> new CactusFlowerBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PINK)
                    .noCollission()
                    .instabreak()
                    .ignitedByLava()
                    .sound(ModSounds.CACTUS_FLOWER.get()) // Use .get() to resolve supplier
                    .pushReaction(PushReaction.DESTROY)));

    public static final Supplier<Block> SHORT_DRY_GRASS = BLOCKS.register("short_dry_grass",
            () -> new ShortDryGrassBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .replaceable()
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .ignitedByLava()
                    .offsetType(BlockBehaviour.OffsetType.XYZ)
                    .pushReaction(PushReaction.DESTROY)));

    public static final Supplier<Block> TALL_DRY_GRASS = BLOCKS.register("tall_dry_grass",
            () -> new TallDryGrassBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .replaceable()
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .ignitedByLava()
                    .offsetType(BlockBehaviour.OffsetType.XYZ)
                    .pushReaction(PushReaction.DESTROY)));

    public static final Supplier<Block> WILDFLOWERS = BLOCKS.register("wildflowers",
            () -> new PinkPetalsBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .sound(SoundType.PINK_PETALS)
                    .pushReaction(PushReaction.DESTROY)));

    public static final Supplier<Block> LEAF_LITTER = BLOCKS.register("leaf_litter",
            () -> new LeafLitterBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .replaceable()
                    .noCollission()
                    .sound(ModSounds.LEAF_LITTER.get()) // Use .get() to resolve supplier
                    .pushReaction(PushReaction.DESTROY)));

    public static final Supplier<Block> FIREFLY_BUSH = BLOCKS.register("firefly_bush",
            () -> new FireflyBushBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .ignitedByLava()
                    .lightLevel((blockStatex) -> 2)
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.SWEET_BERRY_BUSH)
                    .pushReaction(PushReaction.DESTROY)));

    public static void initialize() {
        // DeferredRegister will be registered to the mod event bus in the main mod class
    }

    public static VoxelShape column(double d, double e, double f) {
        return column(d, d, e, f);
    }

    public static VoxelShape column(double d, double e, double f, double g) {
        double h = d / 2.0;
        double i = e / 2.0;
        return Block.box(8.0 - h, f, 8.0 - i, 8.0 + h, g, 8.0 + i);
    }
}