package org.hikuro.hikucraft.listener;

import java.util.EnumMap;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.hikuro.hikucraft.service.EconomyService;

public class HunterJobListener extends JobListener {
	private static final EnumMap<EntityType, Double> killValues = new EnumMap<>(EntityType.class);

	public HunterJobListener(EconomyService economyService) {
		super(economyService, "hikucraft.job.hunter");
		killValues.put(EntityType.ZOMBIE, 5.0);
		killValues.put(EntityType.SKELETON, 5.0);
		killValues.put(EntityType.SPIDER, 4.0);
		// TODO: Add more kill values
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		LivingEntity entity = event.getEntity();
		Player player = entity.getKiller();
		if (player == null || !isRightJob(player)) {
			return;
		}
		EntityType entityType = entity.getType();
		if (killValues.containsKey(entityType)) {
			double value = killValues.get(entityType);
			this.economyService.deposit(player, value);
			player.sendMessage("You killed a " + entityType + " worth " + value + " coins.");
		}
	}
}
