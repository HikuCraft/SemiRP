package org.hikuro.hikucraft.listener;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.hikuro.hikucraft.service.EconomyService;
import org.hikuro.hikucraft.service.PermissionService;

public class BuilderJobListener extends JobListener {
	private static final Map<Material, Double> buildValues = new EnumMap<>(Material.class);

	public BuilderJobListener(EconomyService economyService, PermissionService permissionService) {
		super(economyService, permissionService, "hikucraft.job.builder");
		buildValues.put(Material.DIRT, 1.0);
		buildValues.put(Material.COBBLESTONE, 2.0);
		// TODO: Add more materials
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (!isRightJob(player)) return;

		Optional.ofNullable(buildValues.get(event.getBlock().getType()))
				.ifPresent(
						value -> {
							this.economyService.deposit(player, value);
							player.sendMessage(
									"You placed "
											+ event.getBlock().getType()
											+ " costing "
											+ value
											+ " coins.");
						});
	}
}
