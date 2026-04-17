package dev.gaspard.ghastrider.compat.jade;

import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class GhastRiderJadePlugin implements IWailaPlugin {

	@Override
	public void registerClient(IWailaClientRegistration registration) {
		registration.registerEntityComponent(GhastStatsProvider.INSTANCE, HappyGhast.class);
	}
}
