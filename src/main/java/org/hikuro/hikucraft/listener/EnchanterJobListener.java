package org.hikuro.hikucraft.listener;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.hikuro.hikucraft.service.EconomyService;
import org.hikuro.hikucraft.service.PermissionService;

public class EnchanterJobListener extends JobListener {
	private static final Map<Enchantment, Double> enchantmentValues = new HashMap<>();

	public EnchanterJobListener(
			EconomyService economyService, PermissionService permissionService) {
		super(economyService, permissionService, "hikucraft.job.enchanter");
		enchantmentValues.put(Enchantment.DURABILITY, 10.0);
		enchantmentValues.put(Enchantment.LOOT_BONUS_MOBS, 15.0);
		// TODO: add more enchantments
	}

	@EventHandler
	public void onEnchantItem(EnchantItemEvent event) {
		Player player = event.getEnchanter();
		if (!isRightJob(player)) return;

		double totalValue =
				event.getEnchantsToAdd().entrySet().stream()
						.filter(entry -> enchantmentValues.containsKey(entry.getKey()))
						.mapToDouble(
								entry -> enchantmentValues.get(entry.getKey()) * entry.getValue())
						.sum();

		this.economyService.deposit(player.getUniqueId(), totalValue);
		player.sendMessage("You enchanted an item worth " + totalValue + " coins.");
	}
}
