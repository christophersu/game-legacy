package com.boardgame.game;

abstract class AbstractCombatCard {
	private final String name;
	private final int strength;
	private final int killingPotential;
	private final int deathDefense;
	
	AbstractCombatCard(String name, int strength, int killingPotential, int deathDefense) {
		assert name != null;
		assert strength >= 0;
		assert killingPotential >= 0;
		assert deathDefense >= 0;
		
		this.name = name;
		this.strength = strength;
		this.killingPotential = killingPotential;
		this.deathDefense = deathDefense;
	}
	
	String getName() {
		return name;
	}
	
	int getStrength() {
		return strength;
	}
	
	int getKillingPotential() {
		return killingPotential;
	}
	
	int getDeathDefense() {
		return deathDefense;
	}
}
