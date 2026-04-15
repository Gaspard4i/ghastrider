package dev.gaspard.ghastdash;

import dev.gaspard.ghastdash.client.DashHandler;
import dev.gaspard.ghastdash.client.DashHudRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
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

        HudElementRegistry.attachElementAfter(
                VanillaHudElements.EXPERIENCE_LEVEL,
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "dash_bar"),
                (guiGraphics, deltaTracker) -> {
                    DashHudRenderer.render(guiGraphics, deltaTracker.getGameTimeDeltaPartialTick(true));
                }
        );

        Constants.LOG.info("Happy Ghast Dash client initialized (Fabric)");
    }
}
