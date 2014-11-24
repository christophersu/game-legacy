package com.boardgame.game;

/**
 * General properties for a combat card. Immutable.
 *
 */
abstract class AbstractCombatCard {
	private final int strength;
	private final int killingPotential;
	private final int deathDefense;
	private final String name;
	
	/**
	 * Creates a new combat card with the given name, strength, 
	 * killing potential, death defense
	 * @param name  the name of the combat card, not null
	 * @param strength  the combat strength of the card, nonnegative
	 * @param killingPotential  the potential number of units this card can 
	 * kill, nonnegative
	 * @param deathDefense  the number of units that can be defended from death,
	 * nonnegative 
	 * @throws IllegalArgumentException if name is null
	 * @throws IllegalArgumentException if strength is negative
	 * @throws IllegalArgumentException if killingPotential is negative
	 * @throws IllegalArgumentException if deathDefense is negative
	 */
	AbstractCombatCard(String name, int strength, int killingPotential, 
			int deathDefense) {
		if (name == null) {
			throw new IllegalArgumentException("Name was null.");
		}
		
		if (strength < 0) {
			throw new IllegalArgumentException("Strength was negative.");
		}
		
		if (killingPotential < 0) {
			throw new IllegalArgumentException("Killing potential was "
					+ "negative.");
		}
		
		if (deathDefense < 0) {
			throw new IllegalArgumentException("Death defense was negative.");
		}
		
		this.name = name;
		this.strength = strength;
		this.killingPotential = killingPotential;
		this.deathDefense = deathDefense;
	}
	
	/**
	 * Returns the name of this card
	 * @return the name of this card
	 */
	String getName() {
		return name;
	}
	
	/**
	 * Returns the strength of this card
	 * @return the strength of this card
	 */
	int getStrength() {
		return strength;
	}
	
	/**
	 * Returns the killing potential of this card
	 * @return the killing potential of this card
	 */
	int getKillingPotential() {
		return killingPotential;
	}
	
	/**
	 * Returns the death defense of this card
	 * @return the death defense of this card
	 */
	int getDeathDefense() {
		return deathDefense;
	}
}
