package dev.gaspard.ghastrider.compat.jade;

import dev.gaspard.ghastrider.Constants;
import dev.gaspard.ghastrider.GhastStatHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum GhastStatsProvider implements IEntityComponentProvider {

	INSTANCE;

	private static final Identifier UID = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "ghast_stats");

	@Override
	public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
		if (!(accessor.getEntity() instanceof HappyGhast ghast)) return;

		float speed = GhastStatHelper.getEffectiveSpeed(ghast);
		float dash = GhastStatHelper.getEffectiveDashStrength(ghast);
		float cooldown = GhastStatHelper.getEffectiveCooldown(ghast);

		tooltip.add(formatStat("Speed", speed));
		tooltip.add(formatStat("Dash", dash));
		tooltip.add(formatStat("Cooldown", cooldown));
	}

	private static Component formatStat(String name, float value) {
		int percentage = Math.round((value / Constants.STAT_MAX) * 100f);
		int bars = Math.max(0, Math.min(10,
				Math.round((value - Constants.STAT_MIN) / (Constants.STAT_MAX - Constants.STAT_MIN) * 10f)));

		StringBuilder bar = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			bar.append(i < bars ? '|' : '.');
		}

		MutableComponent comp = Component.literal(name + ": ");
		comp.append(Component.literal("[" + bar + "]").withStyle(style ->
				style.withColor(getColor(bars))));
		comp.append(Component.literal(" " + percentage + "%"));
		return comp;
	}

	private static int getColor(int bars) {
		if (bars <= 2) return 0xFF5555;  // red
		if (bars <= 5) return 0xFFAA00;  // orange
		if (bars <= 7) return 0xFFFF55;  // yellow
		return 0x55FF55;                  // green
	}

	@Override
	public Identifier getUid() {
		return UID;
	}
}
