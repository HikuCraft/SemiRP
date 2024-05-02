package org.hikuro.hikucraft.listener;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.hikuro.hikucraft.service.EconomyService;
import org.hikuro.hikucraft.service.PermissionService;

public class BlacksmithJobListener extends JobListener {
	private static final Map<Material, Double> smeltedValues = new EnumMap<>(Material.class);

	public BlacksmithJobListener(
			EconomyService economyService, PermissionService permissionService) {
		super(economyService, permissionService, "hikucraft.job.blacksmith");
		smeltedValues.put(Material.IRON_INGOT, 5.0);
		smeltedValues.put(Material.GOLD_INGOT, 8.0);
		// TODO: Add more smelted items
	}

	@EventHandler
	public void onFurnaceExtract(FurnaceExtractEvent event) {
		Player player = event.getPlayer();
		if (!isRightJob(player)) return;

		Optional.ofNullable(smeltedValues.get(event.getItemType()))
				.map(value -> value * event.getItemAmount())
				.ifPresent(
						value -> {
							this.economyService.deposit(player.getUniqueId(), value);
							player.sendMessage(
									"You crafted "
											+ event.getItemType()
											+ " worth "
											+ value
											+ " coins.");
						});
	}
}
