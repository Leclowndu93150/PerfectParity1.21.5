package com.leclowndu93150.perfectparity;

import com.leclowndu93150.perfectparity.entity.models.ModModelLayers;
import com.leclowndu93150.perfectparity.entity.models.chicken.ColdChickenModel;
import com.leclowndu93150.perfectparity.entity.models.cow.ColdCowModel;
import com.leclowndu93150.perfectparity.entity.models.cow.ModCowModel;
import com.leclowndu93150.perfectparity.entity.models.cow.WarmCowModel;
import com.leclowndu93150.perfectparity.entity.models.pig.ColdPigModel;
import com.leclowndu93150.perfectparity.entity.models.pig.ModPigModel;
import com.leclowndu93150.perfectparity.particle.FireflyParticle;
import com.leclowndu93150.perfectparity.particle.ModParticles;
import com.leclowndu93150.perfectparity.utils.DryFoliageColor;
import com.leclowndu93150.perfectparity.world.item.ModEntityTypes;
import com.leclowndu93150.perfectparity.world.level.block.ModBlocks;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.level.GrassColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class PerfectParityClient {
	private static final String PATH = "/assets/minecraft/textures/colormap/dry_foliage.png";

	public static void registerClientEvents(IEventBus modEventBus) {
		modEventBus.addListener(PerfectParityClient::registerEntityRenderers);
		modEventBus.addListener(PerfectParityClient::registerLayerDefinitions);
		modEventBus.addListener(PerfectParityClient::registerParticleProviders);
		modEventBus.addListener(PerfectParityClient::registerBlockColors);
		modEventBus.addListener(PerfectParityClient::registerItemColors);
		modEventBus.addListener(PerfectParityClient::onClientSetup);
	}

	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		PerfectParity.LOGGER.info("Registering entity renderers...");
		event.registerEntityRenderer(ModEntityTypes.BLUE_EGG.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(ModEntityTypes.BROWN_EGG.get(), ThrownItemRenderer::new);
		PerfectParity.LOGGER.info("Entity renderers registered!");
	}

	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		PerfectParity.LOGGER.info("Registering model layers...");

		event.registerLayerDefinition(ModModelLayers.WARM_COW, WarmCowModel::createBodyLayer);
		PerfectParity.LOGGER.info("Registered WARM_COW: {}", ModModelLayers.WARM_COW);

		event.registerLayerDefinition(ModModelLayers.COLD_COW, ColdCowModel::createBodyLayer);
		PerfectParity.LOGGER.info("Registered COLD_COW: {}", ModModelLayers.COLD_COW);

		event.registerLayerDefinition(ModModelLayers.NEW_COW, ModCowModel::createBodyLayer);
		PerfectParity.LOGGER.info("Registered NEW_COW: {}", ModModelLayers.NEW_COW);

		event.registerLayerDefinition(ModModelLayers.COLD_CHICKEN, ColdChickenModel::createBodyLayer);
		PerfectParity.LOGGER.info("Registered COLD_CHICKEN: {}", ModModelLayers.COLD_CHICKEN);

		event.registerLayerDefinition(ModModelLayers.COLD_PIG, ColdPigModel::createBodyLayer);
		PerfectParity.LOGGER.info("Registered COLD_PIG: {}", ModModelLayers.COLD_PIG);

		event.registerLayerDefinition(ModModelLayers.NEW_PIG, ModPigModel::createBodyLayer);
		PerfectParity.LOGGER.info("Registered NEW_PIG: {}", ModModelLayers.NEW_PIG);

		PerfectParity.LOGGER.info("Model layer registration complete!");
	}

	public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(ModParticles.FIREFLY.get(), FireflyParticle.FireflyProvider::new);
	}

	public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
		event.register(
				(state, world, pos, tintIndex) ->
						(world != null && pos != null) ? BiomeColors.getAverageGrassColor(world, pos) : GrassColor.getDefaultColor(),
				ModBlocks.BUSH.get()
		);

		event.register(
				(state, world, pos, tintIndex) ->
						world != null && pos != null ? DryFoliageColor.getTint(world, pos) : DryFoliageColor.FOLIAGE_DRY_DEFAULT,
				ModBlocks.LEAF_LITTER.get()
		);

		event.register(
				(state, world, pos, tintIndex) -> {
					if (tintIndex == 1) {
						return BiomeColors.getAverageGrassColor(world, pos);
					}
					return 0xFFFFFFFF;
				},
				ModBlocks.WILDFLOWERS.get()
		);
	}

	public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
		event.register((state, tintIndex) -> GrassColor.getDefaultColor(), ModBlocks.BUSH.get().asItem());
	}

	public static void onClientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			registerColormap();
			setupRenderLayers();
		});
	}

	private static void setupRenderLayers() {
		ItemBlockRenderTypes.setRenderLayer(ModBlocks.BUSH.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(ModBlocks.FIREFLY_BUSH.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(ModBlocks.CACTUS_FLOWER.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(ModBlocks.LEAF_LITTER.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(ModBlocks.TALL_DRY_GRASS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHORT_DRY_GRASS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(ModBlocks.WILDFLOWERS.get(), RenderType.cutout());
	}

	private static void registerColormap() {
		try (InputStream in = PerfectParityClient.class.getResourceAsStream(PATH)) {
			if (in == null) {
				throw new IOException("Resource not found: " + PATH);
			}
			BufferedImage img = ImageIO.read(in);
			int w = img.getWidth(), h = img.getHeight();
			int[] pixels = new int[w * h];
			img.getRGB(0, 0, w, h, pixels, 0, w);
			DryFoliageColor.init(pixels);
			PerfectParity.LOGGER.info("Loaded dry_foliage colormap ({}×{})", w, h);
		} catch (IOException e) {
			PerfectParity.LOGGER.error("Failed to load dry_foliage.png from classpath", e);
		}
	}
}