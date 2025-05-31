package com.leclowndu93150.perfectparity.world.item;

import com.leclowndu93150.perfectparity.PerfectParity;
import com.leclowndu93150.perfectparity.world.level.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, PerfectParity.MOD_ID);

    public static final Supplier<Item> BUSH = registerBlockItem("bush", ModBlocks.BUSH);
    public static final Supplier<Item> CACTUS_FLOWER = registerBlockItem("cactus_flower", ModBlocks.CACTUS_FLOWER);
    public static final Supplier<Item> SHORT_DRY_GRASS = registerBlockItem("short_dry_grass", ModBlocks.SHORT_DRY_GRASS);
    public static final Supplier<Item> TALL_DRY_GRASS = registerBlockItem("tall_dry_grass", ModBlocks.TALL_DRY_GRASS);
    public static final Supplier<Item> WILDFLOWERS = ModList.get().isLoaded("wilderwild") ?
            null : registerBlockItem("wildflowers", ModBlocks.WILDFLOWERS);
    public static final Supplier<Item> LEAF_LITTER = registerBlockItem("leaf_litter", ModBlocks.LEAF_LITTER);
    public static final Supplier<Item> FIREFLY_BUSH = registerBlockItem("firefly_bush", ModBlocks.FIREFLY_BUSH);

    public static final Supplier<Item> BLUE_EGG = ITEMS.register("blue_egg",
            () -> new CustomEggItem("cold", new Item.Properties().stacksTo(16)));
    public static final Supplier<Item> BROWN_EGG = ITEMS.register("brown_egg",
            () -> new CustomEggItem("warm", new Item.Properties().stacksTo(16)));

    private static Supplier<Item> registerBlockItem(String name, Supplier<? extends Block> block) {
        return ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void initialize() {
        // This method can be kept for any initialization logic
    }

    public static void registerCompostable() {
        net.minecraft.world.level.block.ComposterBlock.COMPOSTABLES.put(BUSH.get(), 0.3f);
        net.minecraft.world.level.block.ComposterBlock.COMPOSTABLES.put(FIREFLY_BUSH.get(), 0.3f);
        net.minecraft.world.level.block.ComposterBlock.COMPOSTABLES.put(CACTUS_FLOWER.get(), 0.3f);
        net.minecraft.world.level.block.ComposterBlock.COMPOSTABLES.put(LEAF_LITTER.get(), 0.3f);
        net.minecraft.world.level.block.ComposterBlock.COMPOSTABLES.put(SHORT_DRY_GRASS.get(), 0.3f);
        net.minecraft.world.level.block.ComposterBlock.COMPOSTABLES.put(TALL_DRY_GRASS.get(), 0.3f);
    }

    // Separate event bus subscriber classes for different bus types
    @EventBusSubscriber(modid = "perfectparity", bus = EventBusSubscriber.Bus.MOD)
    public static class ModEvents {
        @SubscribeEvent
        public static void onCommonSetup(FMLCommonSetupEvent event) {
            event.enqueueWork(() -> {
                registerCompostable();
            });
        }

        @SubscribeEvent
        public static void buildCreativeModeTabs(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
                event.insertAfter(Items.FERN.getDefaultInstance(), SHORT_DRY_GRASS.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                event.insertAfter(Items.LARGE_FERN.getDefaultInstance(), TALL_DRY_GRASS.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                event.insertAfter(SHORT_DRY_GRASS.get().getDefaultInstance(), BUSH.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                event.insertAfter(Items.SPORE_BLOSSOM.getDefaultInstance(), FIREFLY_BUSH.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                event.insertAfter(Items.TORCHFLOWER.getDefaultInstance(), CACTUS_FLOWER.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

                if (WILDFLOWERS != null) {
                    event.insertAfter(Items.PINK_PETALS.getDefaultInstance(), WILDFLOWERS.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    event.insertAfter(WILDFLOWERS.get().getDefaultInstance(), LEAF_LITTER.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                } else {
                    event.insertAfter(Items.PINK_PETALS.getDefaultInstance(), LEAF_LITTER.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                }
            }

            if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
                event.insertAfter(Items.EGG.getDefaultInstance(), BROWN_EGG.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                event.insertAfter(BROWN_EGG.get().getDefaultInstance(), BLUE_EGG.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            }
        }
    }

    @EventBusSubscriber(modid = "perfectparity", bus = EventBusSubscriber.Bus.GAME)
    public static class NeoForgeEvents {
        @SubscribeEvent
        public static void onFurnaceFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
            if (event.getItemStack().is(LEAF_LITTER.get())) {
                event.setBurnTime(100);
            } else if (event.getItemStack().is(SHORT_DRY_GRASS.get())) {
                event.setBurnTime(100);
            } else if (event.getItemStack().is(TALL_DRY_GRASS.get())) {
                event.setBurnTime(100);
            }
        }
    }
}