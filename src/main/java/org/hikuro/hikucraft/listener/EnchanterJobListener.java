package org.hikuro.hikucraft.listener;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;
import org.hikuro.hikucraft.service.EconomyService;

import java.util.EnumMap;
import java.util.Map;

public class EnchanterJobListener extends JobListener {
	private static final EnumMap<Enchantment, Double> enchantmentValues = new EnumMap<>(Enchantment.class);

	public EnchanterJobListener(EconomyService economyService) {
		super(economyService, "hikucraft.job.enchanter");
		enchantmentValues.put(Enchantment.DURABILITY, 10.0);
		enchantmentValues.put(Enchantment.LOOT_BONUS_MOBS, 15.0);
		// TODO: add more enchantments
	}

	@EventHandler
	public void onEnchantItem(EnchantItemEvent event) {
		Player player = event.getEnchanter();
		if (!isRightJob(player)) {
			return;
		}

		ItemStack item = event.getItem();
		Map<Enchantment, Integer> enchantments = event.getEnchantsToAdd();

		// Calculate the total value of enchantments applied to the item
		double totalValue = 0.0;
		for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
			Enchantment enchantment = entry.getKey();
			int level = entry.getValue();
			if (enchantmentValues.containsKey(enchantment)) {
				totalValue += enchantmentValues.get(enchantment) * level;
			}
		}

		if (totalValue > 0.0) {
			economyService.deposit(player, totalValue);
			player.sendMessage("You enchanted an item worth " + totalValue + " coins.");
		}
	}
}
