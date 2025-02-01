package com.github.teamfusion.summonerscrolls.common.sound;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import com.github.teamfusion.summonerscrolls.platform.CoreRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class SummonerSoundEvents {
    public static final CoreRegistry<SoundEvent> SOUND_EVENTS = CoreRegistry.create(Registry.SOUND_EVENT, SummonerScrolls.MOD_ID);

    public static final Supplier<SoundEvent> SUMMON_DEATH = SOUND_EVENTS.register("summon_death", ()->
            new SoundEvent(new ResourceLocation(SummonerScrolls.MOD_ID, "summon_death")));

    public static final Supplier<SoundEvent> SUMMON_DEATH_APRIL = SOUND_EVENTS.register("summon_death_april", ()->
            new SoundEvent(new ResourceLocation(SummonerScrolls.MOD_ID, "summon_death_april")));

    public static void register() {
        SOUND_EVENTS.register();
    }
}
