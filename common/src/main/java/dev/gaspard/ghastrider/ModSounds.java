package dev.gaspard.ghastrider;

import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class ModSounds {

    public static final Identifier DASH_ID = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "dash");
    public static final Identifier CHARGE_READY_ID = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "charge_ready");
    public static final Identifier ICE_FEED_ID = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "ice_feed");
    public static final Identifier GHAST_HAPPY_ID = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "ghast_happy");

    public static final SoundEvent DASH = SoundEvent.createVariableRangeEvent(DASH_ID);
    public static final SoundEvent CHARGE_READY = SoundEvent.createVariableRangeEvent(CHARGE_READY_ID);
    public static final SoundEvent ICE_FEED = SoundEvent.createVariableRangeEvent(ICE_FEED_ID);
    public static final SoundEvent GHAST_HAPPY = SoundEvent.createVariableRangeEvent(GHAST_HAPPY_ID);
}
