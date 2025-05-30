package com.leclowndu93150.perfectparity.entity.utils;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class ParityTags {

    public static final TagKey<Biome> SPAWNS_WARM_VARIANT_FARM_ANIMALS = createBiomeTag("spawns_warm_variant_farm_animals");
    public static final TagKey<Biome> SPAWNS_COLD_VARIANT_FARM_ANIMALS = createBiomeTag("spawns_cold_variant_farm_animals");


    private static TagKey<Biome> createBiomeTag(String string) {
        return TagKey.create(Registries.BIOME, ResourceLocation.withDefaultNamespace(string));
    }

    public static final TagKey<Block> REPLACEABLE_BY_MUSHROOMS = createBlockTag("replaceable_by_mushrooms");
    public static final TagKey<Block> TRIGGERS_AMBIENT_DESERT_DRY_VEGETATION_BLOCK_SOUNDS = createBlockTag("plays_ambient_desert_dry_vegetation_block_sounds");
    public static final TagKey<Block> TRIGGERS_AMBIENT_DESERT_SAND_BLOCK_SOUNDS = createBlockTag("plays_ambient_desert_sand_block_sounds");

    private static TagKey<Block> createBlockTag(String string) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.withDefaultNamespace(string));
    }

    public static final TagKey<Item> FLOWERS = createItemTag("flowers");
    public static final TagKey<Item> EGGS = createItemTag("eggs");

    private static TagKey<Item> createItemTag(String string) {
        return TagKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace(string));
    }

}
