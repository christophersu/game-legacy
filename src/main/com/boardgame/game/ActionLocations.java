package com.boardgame.game;

import java.util.HashSet;
import java.util.Set;

final class ActionLocations implements Comparable<ActionLocations> {
	private final Faction faction;
	private final int factionPriority;
	private final Set<Location> locations;
	private final int tokenPriority;
	
	ActionLocations(Faction faction, int factionPriority, Location location) {
		assert faction != null;
		assert location != null;
		assert location.getActionToken() != null;
		
		this.faction = faction;
		this.factionPriority = factionPriority;
		this.locations = new HashSet<>();
		this.tokenPriority = location.getActionToken().getPriority();
		addLocation(location);
	}
	
	boolean addLocation(Location location) {
		assert location != null;
		assert location.getActionToken() != null;
		assert location.getActionToken().getPriority() == tokenPriority;
		
		return locations.add(location);
	}
	
	void removeLocation(Location location) {
		assert location != null;
		assert locations.contains(location);
		
		boolean removeResult = locations.remove(location);
		assert removeResult;
	}
	
	boolean isEmpty() {
		return locations.isEmpty();
	}
	
	public Faction getFaction() {
		return faction;
	}
	
	public Set<Location> getLocations() {
		return new HashSet<>(locations);
	}
	
	int getFactionPriority() {
		return factionPriority;
	}

	@Override
	public int compareTo(ActionLocations other) {
		int result = this.tokenPriority - other.tokenPriority;
		
		if (result == 0) {
			result = this.factionPriority - other.factionPriority;
		}
		
		return result;
	}
}
