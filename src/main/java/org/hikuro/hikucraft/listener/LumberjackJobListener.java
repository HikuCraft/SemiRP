package org.hikuro.hikucraft.listener;

import java.util.EnumMap;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.hikuro.hikucraft.service.EconomyService;

public class LumberjackJobListener extends JobListener {
	private static final EnumMap<Material, Double> woodValues = new EnumMap<>(Material.class);

	public LumberjackJobListener(EconomyService economyService) {
		super(economyService, "hikucraft.job.lumberjack");
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
		if (!isRightJob(player)) {
			return;
		}
		Block block = event.getBlock();
		Material material = block.getType();
		if (woodValues.containsKey(material)) {
			double value = woodValues.get(material);
			this.economyService.deposit(player, value);
			player.sendMessage("You chopped down " + material + " worth " + value + " coins.");
		}
	}
}
