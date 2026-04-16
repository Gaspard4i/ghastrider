package dev.gaspard.ghastrider;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class ExampleMod implements ModInitializer {

    @Override
    public void onInitialize() {
        CommonClass.init();

        Registry.register(BuiltInRegistries.SOUND_EVENT, ModSounds.DASH_ID, ModSounds.DASH);
        Registry.register(BuiltInRegistries.SOUND_EVENT, ModSounds.CHARGE_READY_ID, ModSounds.CHARGE_READY);
        Registry.register(BuiltInRegistries.SOUND_EVENT, ModSounds.ICE_FEED_ID, ModSounds.ICE_FEED);
        Registry.register(BuiltInRegistries.SOUND_EVENT, ModSounds.GHAST_HAPPY_ID, ModSounds.GHAST_HAPPY);
    }
}
