package com.boardgame.game;

class AbstractCombatCard {
	private final String name;
	private final int strength;
	private final int killingPotential;
	private final int deathDefense;
	
	AbstractCombatCard(String name, int strength, int killingPotential, 
			int deathDefense) {
		this.name = name;
		this.strength = strength;
		this.killingPotential = killingPotential;
		this.deathDefense = deathDefense;
		
		checkRep();
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
	
	private void checkRep() {
		assert name != null : "Null name";
		assert strength >= 0 : "Negative strength";
		assert killingPotential >= 0 : "Negative killing potential";
		assert deathDefense >= 0 : "Negative death defense";
	}
}
