package com.github.teamfusion.summonerscrolls.client.particle;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import com.github.teamfusion.summonerscrolls.platform.CoreRegistry;
import com.github.teamfusion.summonerscrolls.platform.Environment;
import com.github.teamfusion.summonerscrolls.platform.client.ParticleRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;

import java.util.function.Supplier;

public class SummonerScrollsParticles {
    public static final CoreRegistry<ParticleType<?>> PARTICLE_TYPES = CoreRegistry.create(BuiltInRegistries.PARTICLE_TYPE, SummonerScrolls.MOD_ID);

    public static final Supplier<SimpleParticleType> SUMMON_PARTICLE = PARTICLE_TYPES.register("summon", () -> new SimpleParticleType(false) {});

    public static void init() {
        PARTICLE_TYPES.register();
        if (Environment.isClientSide()) {
            ParticleRegistry.create(SUMMON_PARTICLE, SummonParticle.Provider::new);
        }
    }
}
