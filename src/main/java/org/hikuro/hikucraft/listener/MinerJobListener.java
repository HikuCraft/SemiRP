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

public class MinerJobListener extends JobListener {
	private static final Map<Material, Double> oreValues = new EnumMap<>(Material.class);

	public MinerJobListener(EconomyService economyService, PermissionService permissionService) {
		super(economyService, permissionService, "hikucraft.job.miner");
		oreValues.put(Material.COAL_ORE, 1.0);
		oreValues.put(Material.IRON_ORE, 2.0);
		oreValues.put(Material.GOLD_ORE, 3.0);
		oreValues.put(Material.DIAMOND_ORE, 5.0);
		oreValues.put(Material.EMERALD_ORE, 7.0);
		oreValues.put(Material.LAPIS_ORE, 4.0);
		oreValues.put(Material.REDSTONE_ORE, 3.0);
		oreValues.put(Material.NETHER_QUARTZ_ORE, 2.0);
		// TODO: Add more ores
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (!isRightJob(player)) return;

		Optional.ofNullable(oreValues.get(event.getBlock().getType()))
				.ifPresent(
						value -> {
							this.economyService.deposit(player.getUniqueId(), value);
							player.sendMessage(
									"You mined "
											+ event.getBlock().getType()
											+ " worth "
											+ value
											+ " coins.");
						});
	}
}
