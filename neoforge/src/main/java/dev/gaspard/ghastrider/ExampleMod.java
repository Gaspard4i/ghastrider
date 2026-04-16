package dev.gaspard.ghastrider;

import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(Constants.MOD_ID)
public class ExampleMod {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, Constants.MOD_ID);

    static {
        SOUND_EVENTS.register("dash", () -> ModSounds.DASH);
        SOUND_EVENTS.register("charge_ready", () -> ModSounds.CHARGE_READY);
        SOUND_EVENTS.register("ice_feed", () -> ModSounds.ICE_FEED);
        SOUND_EVENTS.register("ghast_happy", () -> ModSounds.GHAST_HAPPY);
    }

    public ExampleMod(IEventBus modBus) {
        CommonClass.init();
        SOUND_EVENTS.register(modBus);

        if (FMLLoader.getCurrent().getDist().isClient()) {
            NeoForgeClientMod.registerModBusEvents(modBus);
        }
    }
}
