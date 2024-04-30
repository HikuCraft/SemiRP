package org.hikuro.hikucraft.listener;

import java.util.EnumMap;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.hikuro.hikucraft.service.EconomyService;

public class MinerJobListener extends JobListener {
	private static final EnumMap<Material, Double> oreValues = new EnumMap<>(Material.class);

	public MinerJobListener(EconomyService economyService) {
		super(economyService, "hikucraft.job.miner");
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
		if (isRightJob(player)) {
			return;
		}
		Block block = event.getBlock();
		Material material = block.getType();
		if (oreValues.containsKey(material)) {
			double value = oreValues.get(material);
			this.economyService.deposit(player, value);
			player.sendMessage("You mined " + material + " worth " + value + " coins.");
		}
	}
}
