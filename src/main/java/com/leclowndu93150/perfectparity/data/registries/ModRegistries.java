package com.leclowndu93150.perfectparity.data.registries;

import com.leclowndu93150.perfectparity.PerfectParity;
import com.leclowndu93150.perfectparity.entity.utils.WolfSoundSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class ModRegistries {

    public static final ResourceKey<Registry<WolfSoundSet>> WOLF_SOUND_VARIANT =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(PerfectParity.MODID, "wolf_sound_variant"));

    public static Registry<WolfSoundSet> WOLF_SOUND_VARIANT_REGISTRY;
    public static DeferredRegister<WolfSoundSet> WOLF_SOUND_SETS;

    public static void registerRegistries(NewRegistryEvent event) {
        event.register(new RegistryBuilder<>(WOLF_SOUND_VARIANT).sync(true).create());
        WOLF_SOUND_SETS = DeferredRegister.create(WOLF_SOUND_VARIANT, PerfectParity.MODID);
    }
}