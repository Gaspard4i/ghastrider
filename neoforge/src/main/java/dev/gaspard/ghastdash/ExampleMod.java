package dev.gaspard.ghastdash;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;

@Mod(Constants.MOD_ID)
public class ExampleMod {

    public ExampleMod(IEventBus modBus) {
        CommonClass.init();

        if (FMLLoader.getCurrent().getDist().isClient()) {
            NeoForgeClientMod.registerModBusEvents(modBus);
        }
    }
}
