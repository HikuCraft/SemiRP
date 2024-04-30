package org.hikuro.hikucraft.service;

import org.bukkit.entity.Player;

public class EconomyService {

	public void deposit(Player player, double amount) {
		// Deposit money to player's account
	}

	public void withdraw(Player player, double amount) {
		// Withdraw money from player's account
	}

	public double getBalance(Player player) {
		// Get player's account balance
		return 0.0;
	}

	public boolean hasEnough(Player player, double amount) {
		// Check if player has enough money
		return false;
	}

	public void transfer(Player fromPlayer, Player toPlayer, double amount) {
		// Transfer money from one player to another
	}

	public void setBalance(Player player, double amount) {
		// Set player's account balance
	}

	public void resetBalance(Player player) {
		// Reset player's account balance
	}
}
