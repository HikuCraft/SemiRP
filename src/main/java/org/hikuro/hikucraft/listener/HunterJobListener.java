package org.hikuro.hikucraft.listener;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.hikuro.hikucraft.service.EconomyService;
import org.hikuro.hikucraft.service.PermissionService;

public class HunterJobListener extends JobListener {
	private static final Map<EntityType, Double> killValues = new EnumMap<>(EntityType.class);

	public HunterJobListener(EconomyService economyService, PermissionService permissionService) {
		super(economyService, permissionService, "hikucraft.job.hunter");
		killValues.put(EntityType.ZOMBIE, 5.0);
		killValues.put(EntityType.SKELETON, 5.0);
		killValues.put(EntityType.SPIDER, 4.0);
		// TODO: Add more kill values
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		Player player =
				Optional.ofNullable(event.getEntity().getKiller())
						.filter(this::isRightJob)
						.orElse(null);
		if (player == null) return;

		Optional.ofNullable(killValues.get(event.getEntity().getType()))
				.ifPresent(
						value -> {
							this.economyService.deposit(player.getUniqueId(), value);
							player.sendMessage(
									"You killed a "
											+ event.getEntity().getType()
											+ " worth "
											+ value
											+ " coins.");
						});
	}
}
