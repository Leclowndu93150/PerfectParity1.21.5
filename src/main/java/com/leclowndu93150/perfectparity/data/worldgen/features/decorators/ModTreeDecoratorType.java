package com.leclowndu93150.perfectparity.data.worldgen.features.decorators;

import com.leclowndu93150.perfectparity.PerfectParity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModTreeDecoratorType {

    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_TYPES = DeferredRegister.create(BuiltInRegistries.TREE_DECORATOR_TYPE, PerfectParity.MOD_ID);

    public static final Supplier<TreeDecoratorType<PlaceOnGroundDecorator>> PLACE_ON_GROUND =
            TREE_DECORATOR_TYPES.register("place_on_ground", () -> new TreeDecoratorType<>(PlaceOnGroundDecorator.CODEC));

    public static final Supplier<TreeDecoratorType<AttachedToLogsDecorator>> ATTACHED_TO_LOGS =
            TREE_DECORATOR_TYPES.register("attached_to_logs", () -> new TreeDecoratorType<>(AttachedToLogsDecorator.CODEC));

    public static void registerTreeDecorators() {
        PerfectParity.LOGGER.info("PerfectParity: Registering tree decorators");
    }
}