package org.hikuro.hikucraft.listener;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.hikuro.hikucraft.service.EconomyService;
import org.hikuro.hikucraft.service.PermissionService;

public class LumberjackJobListener extends JobListener {
	private static final Map<Material, Double> woodValues = new EnumMap<>(Material.class);

	public LumberjackJobListener(
			EconomyService economyService, PermissionService permissionService) {
		super(economyService, permissionService, "hikucraft.job.lumberjack");
		woodValues.put(Material.OAK_LOG, 2.0);
		woodValues.put(Material.SPRUCE_LOG, 3.0);
		woodValues.put(Material.BIRCH_LOG, 3.0);
		woodValues.put(Material.JUNGLE_LOG, 4.0);
		woodValues.put(Material.ACACIA_LOG, 4.0);
		woodValues.put(Material.DARK_OAK_LOG, 5.0);
		// TODO: Add more wood types
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (!isRightJob(player)) return;

		Optional.ofNullable(woodValues.get(event.getBlock().getType()))
				.ifPresent(
						value -> {
							this.economyService.deposit(player.getUniqueId(), value);
							player.sendMessage(
									"You chopped down "
											+ event.getBlock().getType()
											+ " worth "
											+ value
											+ " coins.");
						});
	}
}
