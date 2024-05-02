package org.hikuro.hikucraft.listener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;
import org.hikuro.hikucraft.service.EconomyService;
import org.hikuro.hikucraft.service.PermissionService;

public class AlchemistJobListener extends JobListener {
	private static final Map<PotionEffectType, Double> potionValues = new HashMap<>();

	public AlchemistJobListener(
			EconomyService economyService, PermissionService permissionService) {
		super(economyService, permissionService, "hikucraft.job.alchemist");
		potionValues.put(PotionEffectType.INCREASE_DAMAGE, 15.0);
		potionValues.put(PotionEffectType.SPEED, 20.0);
		// TODO: Add more potion effects and values
	}

	@EventHandler
	public void onBrew(BrewEvent event) {
		Player player = (Player) event.getContents().getHolder();
		if (!isRightJob(player)) return;

		Arrays.stream(event.getContents().getContents())
				.filter(Objects::nonNull)
				.filter(ingredient -> ingredient.getType() != Material.AIR)
				.filter(
						ingredient ->
								ingredient.hasItemMeta()
										&& ingredient.getItemMeta() instanceof PotionMeta)
				.map(ingredient -> (PotionMeta) ingredient.getItemMeta())
				.flatMap(potionMeta -> potionMeta.getCustomEffects().stream())
				.filter(potionEffect -> potionValues.containsKey(potionEffect.getType()))
				.findFirst()
				.ifPresent(
						potionEffect -> {
							double value = potionValues.get(potionEffect.getType());
							this.economyService.deposit(player.getUniqueId(), value);
							player.sendMessage("You brewed a potion worth " + value + " coins.");
						});
	}
}
