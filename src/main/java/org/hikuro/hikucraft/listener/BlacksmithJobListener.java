package org.hikuro.hikucraft.listener;

import java.util.EnumMap;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.hikuro.hikucraft.service.EconomyService;

public class BlacksmithJobListener extends JobListener {
	private static final EnumMap<Material, Double> smeltedValues = new EnumMap<>(Material.class);

	public BlacksmithJobListener(EconomyService economyService) {
		super(economyService, "hikucraft.job.blacksmith");
		smeltedValues.put(Material.IRON_INGOT, 5.0);
		smeltedValues.put(Material.GOLD_INGOT, 8.0);
		// TODO: Add more smelted items
	}

	@EventHandler
	public void onFurnaceExtract(FurnaceExtractEvent event) {
		Player player = event.getPlayer();
		if (!isRightJob(player)) {
			return;
		}
		Material smeltedMaterial = event.getItemType();
		if (smeltedValues.containsKey(smeltedMaterial)) {
			double value = smeltedValues.get(smeltedMaterial) * event.getItemAmount();
			this.economyService.deposit(player, value);
			player.sendMessage("You crafted " + smeltedMaterial + " worth " + value + " coins.");
		}
	}
}
