package com.boardgame.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

final class Location {
	private final String name;
	private final Terrain terrain;
	private final Base base;
	private final int supply;
	private final int invest;
	
	private final Set<Location> adjacentLocations;
	
	//owner == null -> units.isEmpty()
	//actionToken != null -> owner != null
	//actionToken != null -> !units.isEmpty()
	//base != null -> terrain = LAND
	
	private final Collection<AbstractUnit> units;
	private Faction owner;
	private AbstractActionToken actionToken;
	
	enum Terrain {
		LAND,
		SEA,
		PORT
	}
	
	private Location(String name, Terrain terrain, Base base, int supply,
			int invest, Set<Location> adjacentLocations, 
			Collection<AbstractUnit> units, Faction owner) {
		this.name = name;
		this.terrain = terrain;
		this.base = base;
		this.supply = supply;
		this.invest = invest;
		this.adjacentLocations = adjacentLocations;
		this.units = units;
		this.owner = owner;
		this.actionToken = null;
		
		checkRep();
	}
	
	Location(String name, Terrain terrain, Base base, int supply, int invest) {
		this(name, terrain, base, supply, invest, new HashSet<>(), 
				new ArrayList<AbstractUnit>(), null);
		checkRep();
	}
	
	Location(Location location, Base base, Set<Location> adjacentLocations, 
			Collection<AbstractUnit> units, Faction owner) {
		this(location.name, location.terrain, base, location.supply,
				location.invest, adjacentLocations, units, owner);
		checkRep();
	}
	
	boolean addAdjacentLocation(Location other) {
		checkRep();
		assert other != null : "Null location";
		assert !this.equals(other) : "Same location";
		
		boolean result = adjacentLocations.add(other);
		
		checkRep();
		return result;
	}
	
	/**
	 * @throws IllegalStateException if there is no owner
	 */
	void addUnit(AbstractUnit unit) {
		checkRep();
		assert unit != null : "Null unit";
		
		if (owner == null) {
			throw new IllegalStateException("No owner.");
		}
		
		boolean result = units.add(unit);
		
		assert(result);
		checkRep();
	}
	
	/**
	 * @throws IllegalStateException if there is no owner
	 */
	boolean removeUnit(AbstractUnit unit) {
		checkRep();
		assert unit != null : "Null unit";
		
		if (owner == null) {
			throw new IllegalStateException("No owner.");
		}
		
		boolean result = units.remove(unit);
		
		if (units.isEmpty()) {
			owner = null;
		}
		
		checkRep();
		return result;
	}
	
	boolean hasUnits() {
		checkRep();
		return !units.isEmpty();
	}

	/**
	 * @throws IllegalStateException if there is no owner
	 * @return whether the token was placed
	 */
	boolean placeActionToken(AbstractActionToken actionToken) {
		checkRep();

		assert actionToken != null : "Null token";
		
		if (owner == null) {
			throw new IllegalStateException("No owner.");
		}
		
		boolean hasToken = this.actionToken != null;
		
		if (!hasToken) {
			this.actionToken = actionToken;	
		}
		
		checkRep();
		
		return !hasToken;
	}
	
	/**
	 * @throws IllegalStateException if owner already owns this location
	 * @throws IllegalStateException if there are units on the location still 
	 */
	void changeOwner(Faction owner) {
		checkRep();
		assert owner != null : "Null owner";
		
		if (this.owner == owner) {
			throw new IllegalStateException("Owner already owns this.");
		}
		
		if (hasUnits()) {
			throw new IllegalStateException("There are still units.");
		}
		
		this.owner = owner;
		
		checkRep();
	}
	
	Faction getOwner() {
		checkRep();
		return owner;
	}
	
	AbstractActionToken getActionToken() {
		checkRep();
		return actionToken;
	}
	
	Set<Location> getAdjacentLocations() {
		return new HashSet<>(adjacentLocations);
	}
	
	AbstractActionToken removeActionToken() {
		checkRep();
		
		AbstractActionToken previousToken = actionToken;
		actionToken = null;
		
		return previousToken;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Location)) {
			return false;
		}
		
		Location other = (Location) o;
		
		return this.name.equals(other.name);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	String getName() {
		checkRep();
		return name;
	}
	
	Terrain getTerrain() {
		checkRep();
		return terrain;
	}
	
	Base getBaseStrength() {
		checkRep();
		return base;
	}
	
	int getSupply() {
		checkRep();
		return supply;
	}
	
	int getInvest() {
		checkRep();
		return invest;
	}
	
	boolean hasToken() {
		return actionToken != null;
	}
	
	private void checkRep() {
		assert name != null : "Null name";
		assert terrain != null : "Null terrain";
		assert base != null : "Null base";
		assert supply >= 0 : "Negative supply";
		assert invest >= 0 : "Negative invest";
		
		assert adjacentLocations != null : "Null adjacent locations";
		
		for (Location location : adjacentLocations) {
			assert location != null : "Null adjacent location";
		}
		
		assert units != null : "Null units";
		
		for (AbstractUnit unit : units) {
			assert unit != null : "Null unit";
		}
		
		if (owner == null) {
			assert units.isEmpty();
		}
		
		if (actionToken != null) {
			assert owner != null : "Token on location with no owner";
			assert !units.isEmpty() : "Token on empty location";
		}
		
		if (base != null) {
			assert terrain == Terrain.LAND : "Base on non-land";
		}
	}
}
