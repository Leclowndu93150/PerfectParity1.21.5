package com.leclowndu93150.perfectparity.sound;

import com.leclowndu93150.perfectparity.PerfectParity;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, PerfectParity.MOD_ID);

    public static final Supplier<SoundEvent> FIREFLY_BUSH_IDLE = registerSoundEvent("block.firefly_bush.idle");
    public static final Supplier<SoundEvent> CACTUS_FLOWER_BREAK = registerSoundEvent("block.cactus_flower.break");
    public static final Supplier<SoundEvent> CACTUS_FLOWER_PLACE = registerSoundEvent("block.cactus_flower.place");

    public static final Supplier<SoundEvent> LEAF_LITTER_BREAK = registerSoundEvent("block.leaf_litter.break");
    public static final Supplier<SoundEvent> LEAF_LITTER_STEP = registerSoundEvent("block.leaf_litter.step");
    public static final Supplier<SoundEvent> LEAF_LITTER_PLACE = registerSoundEvent("block.leaf_litter.place");
    public static final Supplier<SoundEvent> LEAF_LITTER_HIT = registerSoundEvent("block.leaf_litter.hit");
    public static final Supplier<SoundEvent> LEAF_LITTER_FALL = registerSoundEvent("block.leaf_litter.fall");

    public static final Supplier<SoundEvent> DEAD_BUSH_IDLE = registerSoundEvent("block.deadbush.idle");
    public static final Supplier<SoundEvent> SAND_IDLE = registerSoundEvent("block.sand.idle");
    public static final Supplier<SoundEvent> DRY_GRASS = registerSoundEvent("block.dry_grass.ambient");

    // Wolf sounds
    public static final Supplier<SoundEvent> WOLF_PUGLIN_AMBIENT = registerSoundEvent("entity.wolf_puglin.ambient");
    public static final Supplier<SoundEvent> WOLF_PUGLIN_PANT = registerSoundEvent("entity.wolf_puglin.pant");
    public static final Supplier<SoundEvent> WOLF_PUGLIN_WHINE = registerSoundEvent("entity.wolf_puglin.whine");
    public static final Supplier<SoundEvent> WOLF_PUGLIN_GROWL = registerSoundEvent("entity.wolf_puglin.growl");
    public static final Supplier<SoundEvent> WOLF_PUGLIN_DEATH = registerSoundEvent("entity.wolf_puglin.death");
    public static final Supplier<SoundEvent> WOLF_PUGLIN_HURT = registerSoundEvent("entity.wolf_puglin.hurt");

    public static final Supplier<SoundEvent> WOLF_SAD_AMBIENT = registerSoundEvent("entity.wolf_sad.ambient");
    public static final Supplier<SoundEvent> WOLF_SAD_PANT = registerSoundEvent("entity.wolf_sad.pant");
    public static final Supplier<SoundEvent> WOLF_SAD_WHINE = registerSoundEvent("entity.wolf_sad.whine");
    public static final Supplier<SoundEvent> WOLF_SAD_GROWL = registerSoundEvent("entity.wolf_sad.growl");
    public static final Supplier<SoundEvent> WOLF_SAD_DEATH = registerSoundEvent("entity.wolf_sad.death");
    public static final Supplier<SoundEvent> WOLF_SAD_HURT = registerSoundEvent("entity.wolf_sad.hurt");

    public static final Supplier<SoundEvent> WOLF_ANGRY_AMBIENT = registerSoundEvent("entity.wolf_angry.ambient");
    public static final Supplier<SoundEvent> WOLF_ANGRY_PANT = registerSoundEvent("entity.wolf_angry.pant");
    public static final Supplier<SoundEvent> WOLF_ANGRY_WHINE = registerSoundEvent("entity.wolf_angry.whine");
    public static final Supplier<SoundEvent> WOLF_ANGRY_GROWL = registerSoundEvent("entity.wolf_angry.growl");
    public static final Supplier<SoundEvent> WOLF_ANGRY_DEATH = registerSoundEvent("entity.wolf_angry.death");
    public static final Supplier<SoundEvent> WOLF_ANGRY_HURT = registerSoundEvent("entity.wolf_angry.hurt");

    public static final Supplier<SoundEvent> WOLF_GRUMPY_AMBIENT = registerSoundEvent("entity.wolf_grumpy.ambient");
    public static final Supplier<SoundEvent> WOLF_GRUMPY_PANT = registerSoundEvent("entity.wolf_grumpy.pant");
    public static final Supplier<SoundEvent> WOLF_GRUMPY_WHINE = registerSoundEvent("entity.wolf_grumpy.whine");
    public static final Supplier<SoundEvent> WOLF_GRUMPY_GROWL = registerSoundEvent("entity.wolf_grumpy.growl");
    public static final Supplier<SoundEvent> WOLF_GRUMPY_DEATH = registerSoundEvent("entity.wolf_grumpy.death");
    public static final Supplier<SoundEvent> WOLF_GRUMPY_HURT = registerSoundEvent("entity.wolf_grumpy.hurt");

    public static final Supplier<SoundEvent> WOLF_BIG_AMBIENT = registerSoundEvent("entity.wolf_big.ambient");
    public static final Supplier<SoundEvent> WOLF_BIG_PANT = registerSoundEvent("entity.wolf_big.pant");
    public static final Supplier<SoundEvent> WOLF_BIG_WHINE = registerSoundEvent("entity.wolf_big.whine");
    public static final Supplier<SoundEvent> WOLF_BIG_GROWL = registerSoundEvent("entity.wolf_big.growl");
    public static final Supplier<SoundEvent> WOLF_BIG_DEATH = registerSoundEvent("entity.wolf_big.death");
    public static final Supplier<SoundEvent> WOLF_BIG_HURT = registerSoundEvent("entity.wolf_big.hurt");

    public static final Supplier<SoundEvent> WOLF_CUTE_AMBIENT = registerSoundEvent("entity.wolf_cute.ambient");
    public static final Supplier<SoundEvent> WOLF_CUTE_PANT = registerSoundEvent("entity.wolf_cute.pant");
    public static final Supplier<SoundEvent> WOLF_CUTE_WHINE = registerSoundEvent("entity.wolf_cute.whine");
    public static final Supplier<SoundEvent> WOLF_CUTE_GROWL = registerSoundEvent("entity.wolf_cute.growl");
    public static final Supplier<SoundEvent> WOLF_CUTE_DEATH = registerSoundEvent("entity.wolf_cute.death");
    public static final Supplier<SoundEvent> WOLF_CUTE_HURT = registerSoundEvent("entity.wolf_cute.hurt");

    // Sound types using suppliers to avoid null reference issues
    public static final Supplier<SoundType> CACTUS_FLOWER = () -> new SoundType(1.0F, 1.0F,
            CACTUS_FLOWER_BREAK.get(), SoundEvents.EMPTY, CACTUS_FLOWER_PLACE.get(),
            SoundEvents.EMPTY, SoundEvents.EMPTY);

    public static final Supplier<SoundType> LEAF_LITTER = () -> new SoundType(1.0F, 1.0F,
            LEAF_LITTER_BREAK.get(), LEAF_LITTER_STEP.get(), LEAF_LITTER_PLACE.get(),
            LEAF_LITTER_HIT.get(), LEAF_LITTER_FALL.get());

    // Wolf sound maps
    public static final Map<String, SoundEvent> WOLF_CLASSIC = Map.of(
            "ambient", SoundEvents.WOLF_AMBIENT,
            "pant", SoundEvents.WOLF_PANT,
            "whine", SoundEvents.WOLF_WHINE,
            "growl", SoundEvents.WOLF_GROWL,
            "death", SoundEvents.WOLF_DEATH,
            "hurt", SoundEvents.WOLF_HURT
    );

    public static Map<String, SoundEvent> WOLF_PUGLIN;
    public static Map<String, SoundEvent> WOLF_SAD;
    public static Map<String, SoundEvent> WOLF_ANGRY;
    public static Map<String, SoundEvent> WOLF_GRUMPY;
    public static Map<String, SoundEvent> WOLF_BIG;
    public static Map<String, SoundEvent> WOLF_CUTE;

    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(
                PerfectParity.MOD_ID.equals("minecraft") ?
                        net.minecraft.resources.ResourceLocation.withDefaultNamespace(name) :
                        net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(PerfectParity.MOD_ID, name)
        ));
    }

    public static void registerSounds() {
        // Initialize wolf sound maps
        WOLF_PUGLIN = Map.of(
                "ambient", WOLF_PUGLIN_AMBIENT.get(),
                "pant", WOLF_PUGLIN_PANT.get(),
                "whine", WOLF_PUGLIN_WHINE.get(),
                "growl", WOLF_PUGLIN_GROWL.get(),
                "death", WOLF_PUGLIN_DEATH.get(),
                "hurt", WOLF_PUGLIN_HURT.get()
        );

        WOLF_SAD = Map.of(
                "ambient", WOLF_SAD_AMBIENT.get(),
                "pant", WOLF_SAD_PANT.get(),
                "whine", WOLF_SAD_WHINE.get(),
                "growl", WOLF_SAD_GROWL.get(),
                "death", WOLF_SAD_DEATH.get(),
                "hurt", WOLF_SAD_HURT.get()
        );

        WOLF_ANGRY = Map.of(
                "ambient", WOLF_ANGRY_AMBIENT.get(),
                "pant", WOLF_ANGRY_PANT.get(),
                "whine", WOLF_ANGRY_WHINE.get(),
                "growl", WOLF_ANGRY_GROWL.get(),
                "death", WOLF_ANGRY_DEATH.get(),
                "hurt", WOLF_ANGRY_HURT.get()
        );

        WOLF_GRUMPY = Map.of(
                "ambient", WOLF_GRUMPY_AMBIENT.get(),
                "pant", WOLF_GRUMPY_PANT.get(),
                "whine", WOLF_GRUMPY_WHINE.get(),
                "growl", WOLF_GRUMPY_GROWL.get(),
                "death", WOLF_GRUMPY_DEATH.get(),
                "hurt", WOLF_GRUMPY_HURT.get()
        );

        WOLF_BIG = Map.of(
                "ambient", WOLF_BIG_AMBIENT.get(),
                "pant", WOLF_BIG_PANT.get(),
                "whine", WOLF_BIG_WHINE.get(),
                "growl", WOLF_BIG_GROWL.get(),
                "death", WOLF_BIG_DEATH.get(),
                "hurt", WOLF_BIG_HURT.get()
        );

        WOLF_CUTE = Map.of(
                "ambient", WOLF_CUTE_AMBIENT.get(),
                "pant", WOLF_CUTE_PANT.get(),
                "whine", WOLF_CUTE_WHINE.get(),
                "growl", WOLF_CUTE_GROWL.get(),
                "death", WOLF_CUTE_DEATH.get(),
                "hurt", WOLF_CUTE_HURT.get()
        );

        PerfectParity.LOGGER.info("Registering Sounds for PerfectParity");
    }
}