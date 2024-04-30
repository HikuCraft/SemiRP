package org.hikuro.hikucraft.listener;

import java.util.EnumMap;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.hikuro.hikucraft.service.EconomyService;

public class BuilderJobListener extends JobListener {
	private static final EnumMap<Material, Double> buildValues = new EnumMap<>(Material.class);

	public BuilderJobListener(EconomyService economyService) {
		super(economyService, "hikucraft.job.builder");
		buildValues.put(Material.DIRT, 1.0);
		buildValues.put(Material.COBBLESTONE, 2.0);
		// TODO: Add more materials
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (!isRightJob(player)) {
			return;
		}
		Block block = event.getBlock();
		Material material = block.getType();
		if (buildValues.containsKey(material)) {
			double value = buildValues.get(material);
			this.economyService.deposit(player, value);
			player.sendMessage("You placed " + material + " costing " + value + " coins.");
		}
	}
}
