package dev.gaspard.ghastdash;

import dev.gaspard.ghastdash.client.DashHandler;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.lwjgl.glfw.GLFW;

public class NeoForgeClientMod {

    public static final KeyMapping DASH_KEY = new KeyMapping(
            "key.ghastdash.dash",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            KeyMapping.Category.GAMEPLAY
    );

    public static void registerModBusEvents(IEventBus modBus) {
        modBus.addListener(NeoForgeClientMod::registerKeyMappings);
        NeoForge.EVENT_BUS.addListener(NeoForgeClientMod::onClientTick);
    }

    private static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(DASH_KEY);
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        DashHandler.onClientTick(DASH_KEY.isDown());
    }
}
