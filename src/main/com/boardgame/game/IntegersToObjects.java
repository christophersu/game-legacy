package com.boardgame.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class IntegersToObjects {
	private final Map<Integer, Faction> playerIdsToFactions;
	private final List<Location> locations;
	
	IntegersToObjects(GameState gameState) {
		playerIdsToFactions = new HashMap<>();
		locations = gameState.getLocations();
	}
	
	Map<Integer, Faction> getModifiablePlayerIdsToFactions() {
		return playerIdsToFactions;
	}
	
	/**
	 * Returns the faction associated with the given player id, or null if there
	 * is no such association
	 * @param playerId  the id for which the faction will be found
	 * @return the faction associated with the given id, or null if there's no 
	 * association
	 */
	public Faction getFaction(int playerId) {
		return playerIdsToFactions.get(playerId);
	}
	
	/**
	 * Returns the location associated with the given location id.
	 * @param locationId  the id for which the associated location will be 
	 * returned, must be a valid id
	 * @throws IllegalArgumentException if locationId is invalid
	 * @return the location associated with the given id, not null
	 */
	public Location getLocation(int locationId) {
		if (locationId < 0 || locationId >= locations.size()) {
			throw new IllegalArgumentException("Invalid location id.");
		}
		
		Location result = locations.get(locationId);
		
		assert(result != null);
		
		return result;
	}	
}
