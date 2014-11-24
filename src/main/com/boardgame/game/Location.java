package com.boardgame.game;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a location on the game board.
 * 
 */
final class Location {
	private final String name;
	private final Terrain terrain;
	private final BaseStrength baseStrength;
	private final int supply;
	private final int invest;
	
	//owner == null -> units.isEmpty()
	//actionToken != null -> owner != null
	//actionToken != null -> !units.isEmpty()
	
	private final Collection<AbstractUnit> units;
	private Player owner;
	private AbstractActionToken actionToken;
	
	enum Terrain {
		LAND,
		SEA,
		PORT
	}
	
	enum BaseStrength {
		NONE,
		NORMAL,
		STRONG
	}
	
	/**
	 * Creates a new location with the given name, given supply, and given 
	 * invest potential.
	 * @param name  the name of this location, not null
	 * @param terrain  the terrain of this location, not null
	 * @param baseStrength  the strength of a base at this location, not null
	 * @param supply  the amount this location contributes to the owner's 
	 * supply, nonnegative
	 * @param invest  the amount of investment potential for the owner, 
	 * nonnegative
	 * @throws IllegalArgumentException  if name is null
	 * @throws IllegalArgumentException  if terrain is null
	 * @throws IllegalArgumentException  if baseStrength is null
	 * @throws IllegalArgumentException  if supply is negative
	 * @throws IllegalArgumentException  if invest is negative
	 */
	Location(String name, Terrain terrain, BaseStrength baseStrength, 
			int supply, int invest) {
		if (name == null) {
			throw new IllegalArgumentException("Name was null.");
		}
		
		if (terrain == null) {
			throw new IllegalArgumentException("Terrain was null.");
		}
		
		if (baseStrength == null) {
			throw new IllegalArgumentException("Base strength was null.");
		}
		
		if (supply < 0) {
			throw new IllegalArgumentException("Supply was negative: " + 
					supply);
		}
		
		if (invest < 0) {
			throw new IllegalArgumentException("Invest was negative: " + 
					invest);
		}
		
		this.name = name;
		this.terrain = terrain;
		this.baseStrength = baseStrength;
		this.supply = supply;
		this.invest = invest;
		
		units = new ArrayList<AbstractUnit>();
		owner = null;
		actionToken = null;
	}
	
	/**
	 * Adds the given unit to this location. This location must be owned by 
	 * somebody.
	 * @param unit  the unit to be added, not null
	 * @throws IllegalArgumentException  if unit is null
	 * @throws IllegalStateException  if there is no owner
	 */
	void addUnit(AbstractUnit unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Unit was null.");
		}
		
		if (owner == null) {
			throw new IllegalStateException("No owner.");
		}
		
		boolean result = units.add(unit);
		
		assert(result);
	}
	
	/**
	 * Removes the given unit from this location. This location must be owned by
	 * somebody.
	 * @param unit  the unit to be removed, not null
	 * @throws IllegalArgumentException  if unit is null
	 * @throws IllegalStateException if there is no owner
	 * @throws IllegalStateException if unit was not at this location beforehand
	 */
	boolean removeUnit(AbstractUnit unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Unit was null.");
		}
		
		if (owner == null) {
			throw new IllegalStateException("No owner.");
		}
		
		boolean result = units.remove(unit);
		
		if (units.isEmpty()) {
			owner = null;
		}
		
		return result;
	}
	
	/**
	 * Returns whether or not this location has units on it
	 * @return whether or not this location has units on it
	 */
	boolean hasUnits() {
		return !units.isEmpty();
	}
	
	/**
	 * Places the action token at this location to the given one. The location
	 * must be owned by somebody.
	 * @param actionToken  the action token that will be at this location, not
	 * null
	 * @throws IllegalArgumentException  if actionToken is null
	 * @throws IllegalStateException  if there is no owner
	 */
	void placeActionToken(AbstractActionToken actionToken) {
		if (actionToken == null) {
			throw new IllegalArgumentException("Action token was null.");
		}
		
		if (owner == null) {
			throw new IllegalStateException("No owner.");
		}
		
		this.actionToken = actionToken;
	}
	
	/**
	 * Changes the owner of this location to be the given owner
	 * @param owner  the next owner of this location, null means no owner
	 * @throws IllegalStateException if the next owner is the same as this owner
	 * @throws IllegalStateException if there are still units at this location
	 */
	void changeOwner(Player owner) {
		if (this.owner == owner) {
			throw new IllegalStateException("Owner already owns this.");
		}
		
		if (hasUnits()) {
			throw new IllegalStateException("There are still units.");
		}
		
		this.owner = owner;
	}
	
	/**
	 * Returns the owner of this location
	 * @return the owner of this location, null means no owner
	 */
	Player getOwner() {
		return owner;
	}
	
	/**
	 * Returns the action token at this location, or null if there is none
	 * @return the action token at this location, or null if there is none
	 */
	AbstractActionToken getActionToken() {
		return actionToken;
	}
	
	/**
	 * Removes the action token at this location, if there is one.
	 */
	void removeActionToken() {
		actionToken = null;
	}
	
	/**
	 * Returns the name of this location, not null
	 * @return the name of this location, not null
	 */
	String getName() {
		return name;
	}
	
	/**
	 * Returns the terrain of this location, not null
	 * @return the terrain of this location, not null
	 */
	Terrain getTerrain() {
		return terrain;
	}
	
	/**
	 * Returns the strength of the base at this location, not null
	 * @return the strength of the base at this location, not null
	 */
	BaseStrength getBaseStrength() {
		return baseStrength;
	}
	
	/**
	 * Returns the supply of this location, nonnegative
	 * @return the supply of this location, nonnegative
	 */
	int getSupply() {
		return supply;
	}
	
	/**
	 * Returns the invest potential of this location, nonnegative
	 * @return the invest potential of this location, nonnegative
	 */
	int getInvest() {
		return invest;
	}
}