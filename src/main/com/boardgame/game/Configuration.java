package com.boardgame.game;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

class Configuration {
	private final JSONObject jsonConfiguration;
	private final Set<Integer> validNumPlayers;
	private final Map<Faction, Player> factionsToPlayers;
	
	/**
	 * Creates a new configuration based on the information in the file with the
	 * given filename.
	 * @param filename  the name of the file with configuration information, not
	 * null
	 * @throws ParseException if the file is not valid JSON
	 * @throws IOException if the file with filename does not exist or some 
	 * other file issue happens
	 * @throws IllegalArgumentException if filename is null
	 */
	Configuration(String filename) throws IOException, ParseException {
		if (filename == null) {
			throw new IllegalArgumentException("Filename was null.");
		}
		
		FileReader fileReader = new FileReader(filename);
		JSONParser parser = new JSONParser();
		jsonConfiguration = (JSONObject) parser.parse(fileReader);

		validNumPlayers = findValidNumPlayers();
		factionsToPlayers = findFactionsToPlayers();
	}
	
	/**
	 * Finds the set of valid numbers of players
	 * @return the set of valid numbers of players, not null, no null elements
	 */
	private Set<Integer> findValidNumPlayers() {
		JSONArray validNumPlayersArray = 
				(JSONArray) jsonConfiguration.get("validNumPlayers");
		return new HashSet<>(validNumPlayersArray);
	}
	
	/**
	 * Determines whether or not the given numPlayers is a valid number of 
	 * players according to this configuration
	 * @param numPlayers  the number of players to be checked
	 * @return whether or not the given numPlayers is a valid number of players
	 * according to this configuration
	 */
	boolean isValidNumPlayers(int numPlayers) {
		return validNumPlayers.contains(numPlayers);
	}
	
	/**
	 * Finds the mapping of factions to player data
	 * @return the mapping of factions to player data, not null, no null 
	 * elements
	 */
	private Map<Faction, Player> findFactionsToPlayers() {
		Map<Faction, Player> result = new HashMap<>();
		
		for (Faction faction : Faction.values()) {
			result.put(faction, createPlayer(faction));
		}
		
		return result;
	}
	
	/**
	 * Creates a player for the given faction
	 * @param faction  the player's faction, not null 
	 * @return the player with the given faction
	 */
	private Player createPlayer(Faction faction) {
		assert(faction != null);
		
		String factionKey = faction.toString();
		
		JSONObject playersConf = 
				(JSONObject) jsonConfiguration.get("factionsToPlayers");
		JSONObject playerConf = (JSONObject) playersConf.get(factionKey);
		
		JSONArray combatCardsConf = (JSONArray) playerConf.get("combatCards");
		JSONArray unitsConf = (JSONArray) playerConf.get("units");
		int cashInHand = (Integer) playerConf.get("cashInHand");
		int cashPool = (Integer) playerConf.get("cashPool");
		
		Set<AbstractCombatCard> combatCards = new HashSet<>(combatCardsConf);
		Set<AbstractUnit> units = new HashSet<>(unitsConf);
		
		return new Player(combatCards, units, cashInHand, cashPool);
	}
	
	
}
