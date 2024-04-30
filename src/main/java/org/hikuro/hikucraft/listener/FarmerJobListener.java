package org.hikuro.hikucraft.listener;

import java.util.EnumMap;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.hikuro.hikucraft.service.EconomyService;

public class FarmerJobListener extends JobListener {
	private static final EnumMap<Material, Double> cropValues = new EnumMap<>(Material.class);
	private static final EnumMap<Material, Double> seedValues = new EnumMap<>(Material.class);

	public FarmerJobListener(EconomyService economyService) {
		super(economyService, "hikucraft.job.farmer");
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
		if (isRightJob(player)) {
			return;
		}
		Block block = event.getBlock();
		Material material = block.getType();
		if (cropValues.containsKey(material)) {
			double value = cropValues.get(material);
			this.economyService.deposit(player, value);
			player.sendMessage("You harvested " + material + " worth " + value + " coins.");
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (isRightJob(player)) {
			return;
		}
		Block block = event.getBlock();
		Material material = block.getType();
		if (seedValues.containsKey(material)) {
			double value = seedValues.get(material);
			this.economyService.deposit(player, value);
			player.sendMessage("You planted " + material + " worth " + value + " coins.");
		}
	}
}
