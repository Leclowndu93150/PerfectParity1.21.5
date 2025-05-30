package com.leclowndu93150.perfectparity.particle;

import com.leclowndu93150.perfectparity.PerfectParity;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, PerfectParity.MOD_ID);

    public static final Supplier<SimpleParticleType> FIREFLY = PARTICLE_TYPES.register("firefly",
            () -> new SimpleParticleType(true));

    public static void registerParticles() {
        PerfectParity.LOGGER.info("Registering Particles for PerfectParity");
    }
}