package dev.gaspard.ghastdash;

import dev.gaspard.ghastdash.client.DashHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

public class FabricClientMod implements ClientModInitializer {

    public static final KeyMapping DASH_KEY = new KeyMapping(
            "key.ghastdash.dash",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            KeyMapping.Category.GAMEPLAY
    );

    @Override
    public void onInitializeClient() {
        KeyMappingHelper.registerKeyMapping(DASH_KEY);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            DashHandler.onClientTick(DASH_KEY.isDown());
        });

        Constants.LOG.info("Happy Ghast Dash client initialized (Fabric)");
    }
}
