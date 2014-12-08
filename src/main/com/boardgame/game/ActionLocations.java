package com.boardgame.game;

import java.util.HashSet;
import java.util.Set;

final class ActionLocations {
	private final Faction faction;
	private final int factionPriority;
	private final Set<Location> locations;
	
	ActionLocations(Faction faction, int factionPriority, Location location) {
		assert faction != null;
		assert location != null;
		
		this.faction = faction;
		this.factionPriority = factionPriority;
		this.locations = new HashSet<>();
	}
	
	boolean addLocation(Location location) {
		assert location != null;
		assert location.getActionToken() != null;
		
		for (Location existingLocation : locations) {
			assert existingLocation.getActionToken().getPriority() == 
					location.getActionToken().getPriority();
		}
		
		return locations.add(location);
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
}
