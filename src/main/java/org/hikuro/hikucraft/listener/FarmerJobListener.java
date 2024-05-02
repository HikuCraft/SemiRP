package org.hikuro.hikucraft.listener;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.hikuro.hikucraft.service.EconomyService;
import org.hikuro.hikucraft.service.PermissionService;

public class FarmerJobListener extends JobListener {
	private static final Map<Material, Double> cropValues = new EnumMap<>(Material.class);
	private static final Map<Material, Double> seedValues = new EnumMap<>(Material.class);

	public FarmerJobListener(EconomyService economyService, PermissionService permissionService) {
		super(economyService, permissionService, "hikucraft.job.farmer");
		cropValues.put(Material.WHEAT, 1.0);
		cropValues.put(Material.CARROTS, 2.0);
		cropValues.put(Material.POTATOES, 2.0);
		// TODO: Add more crops
		seedValues.put(Material.WHEAT_SEEDS, 0.5);
		seedValues.put(Material.CARROT, 1.0);
		seedValues.put(Material.POTATO, 1.0);
		// TODO: Add more seeds
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (!isRightJob(player)) return;

		Optional.ofNullable(cropValues.get(event.getBlock().getType()))
				.ifPresent(
						value -> {
							this.economyService.deposit(player.getUniqueId(), value);
							player.sendMessage(
									"You harvested "
											+ event.getBlock().getType()
											+ " worth "
											+ value
											+ " coins.");
						});
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (!isRightJob(player)) return;

		Optional.ofNullable(seedValues.get(event.getBlock().getType()))
				.ifPresent(
						value -> {
							this.economyService.deposit(player.getUniqueId(), value);
							player.sendMessage(
									"You planted "
											+ event.getBlock().getType()
											+ " worth "
											+ value
											+ " coins.");
						});
	}
}
