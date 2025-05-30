package com.leclowndu93150.perfectparity;

import com.leclowndu93150.perfectparity.data.registries.ModRegistries;
import com.leclowndu93150.perfectparity.data.worldgen.ModWorldGeneration;
import com.leclowndu93150.perfectparity.data.worldgen.features.ModFeatures;
import com.leclowndu93150.perfectparity.data.worldgen.features.decorators.ModTreeDecoratorType;
import com.leclowndu93150.perfectparity.particle.ModParticles;
import com.leclowndu93150.perfectparity.utils.ModCustomTrades;
import com.leclowndu93150.perfectparity.world.item.ModEntityTypes;
import com.leclowndu93150.perfectparity.world.item.ModItems;
import com.leclowndu93150.perfectparity.world.level.block.ModBlocks;
import com.leclowndu93150.perfectparity.sound.ModSounds;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod("perfectparity")
public class PerfectParity {
	public static final String MOD_ID = "minecraft";
	public static final String MODID = "minecraft";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public PerfectParity(IEventBus modEventBus) {
		modEventBus.addListener(this::commonSetup);
		modEventBus.addListener(ModRegistries::registerRegistries);

		ModBlocks.BLOCKS.register(modEventBus);
		ModItems.ITEMS.register(modEventBus);
		ModSounds.SOUND_EVENTS.register(modEventBus);
		ModParticles.PARTICLE_TYPES.register(modEventBus);
		ModFeatures.FEATURES.register(modEventBus);
		ModTreeDecoratorType.TREE_DECORATOR_TYPES.register(modEventBus);
		ModEntityTypes.ENTITY_TYPES.register(modEventBus);

		// Register client events only on client side
		if (FMLEnvironment.dist == Dist.CLIENT) {
			PerfectParityClient.registerClientEvents(modEventBus);
		}
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			ModSounds.registerSounds(); // Move sound registration here
			ModWorldGeneration.generateModWorldGen();
			ModCustomTrades.registerCustomTrades();
		});
	}
}