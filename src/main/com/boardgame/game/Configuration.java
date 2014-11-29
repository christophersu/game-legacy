package com.boardgame.game;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.boardgame.game.Location.Terrain;

/**
 * Represents configuration information for different game types.
 *
 */
class Configuration {
	//this object assumes the JSON files have been validated against their 
	//schemas
	
	private static final String PATH = "res/";
	
	private final JSONObject jsonConfiguration;
	private final Map<Faction, Player> factionsToPlayers;
	private final List<Location> locations;
	private final GameType gameType;
	
	enum GameType {
		STANDARD_6
	}
	
	/**
	 * Creates a new configuration based on the information in the file with the
	 * given filename.
	 * @param gameType  the type of the game to load, not null
	 * @throws ParseException if the file is not valid JSON
	 * @throws IOException if the file with filename does not exist or some 
	 * other file issue happens
	 * @throws IllegalArgumentException if gameType is null
	 */
	Configuration(GameType gameType) throws IOException, ParseException {
		if (gameType == null) {
			throw new IllegalArgumentException("Game type was null.");
		}
		
		locations = findLocations();
		
		String gameTypeFilename = getGameTypeFilename(gameType);
		FileReader fileReader = new FileReader(PATH + gameTypeFilename);
		JSONParser parser = new JSONParser();
		jsonConfiguration = (JSONObject) parser.parse(fileReader);

		factionsToPlayers = findFactionsToPlayers();
		this.gameType = gameType;
	}
	
	/**
	 * Gets a list of the location objects for the board
	 * @return a list of the location objects for the board, not null, nonempty,
	 * no null elements
	 * @throws ParseException if the JSON is parsed incorrectly
	 * @throws IOException if the file couldn't be read correctly
	 */
	private List<Location> findLocations() throws IOException, ParseException {
		String boardFilename = "board.json";
		FileReader fileReader = new FileReader(PATH + boardFilename);
		JSONParser parser = new JSONParser();
		
		JSONObject jsonBoard = (JSONObject) parser.parse(fileReader);
		
		List<Location> resultLocations = new ArrayList<Location>();
		Set<Location> locationsSet = new HashSet<Location>();
		
		JSONArray locationsArray = (JSONArray) jsonBoard.get("locations");
		
		for (Object locationsArrayElement : locationsArray) {
			JSONObject locationObject = (JSONObject) locationsArrayElement;
			String name = (String) locationObject.get("name");
			Terrain terrain = (Terrain) locationObject.get("terrain");
			JSONObject baseObject = (JSONObject) locationObject.get("base");
			int baseSize = (Integer) baseObject.get("size");
			Base base = new Base(baseSize);
			int supply = (Integer) locationObject.get("supply");
			int invest = (Integer) locationObject.get("invest");
			
			Location location = 
					new Location(name, terrain, base, supply, invest);
			boolean isLocationDuplicate = !locationsSet.add(location);
			
			if (isLocationDuplicate) {
				throw new RuntimeException("Duplicate location encountered."
						+ "Name: " + location.getName());
			}
			
			resultLocations.add(location);
		}
		
		JSONArray adjacenciesArray = (JSONArray) jsonBoard.get("adjacencies");
		
		for (Object adjacenciesArrayElement : adjacenciesArray) {
			JSONArray adjacencyPair = (JSONArray) adjacenciesArrayElement;

			int locationIndexA = (Integer) adjacencyPair.get(0);
			int locationIndexB = (Integer) adjacencyPair.get(1);
			
			if (locationIndexA >= resultLocations.size() || 
					locationIndexB >= resultLocations.size()) {
				throw new RuntimeException("Location index out of bounds.");
			}
			
			if (locationIndexA == locationIndexB) {
				throw new RuntimeException("Location cannot be adjacent to "
						+ "itself");
			}

			Location locationA = resultLocations.get(locationIndexA);
			Location locationB = resultLocations.get(locationIndexB);

			locationA.addAdjacentLocation(locationB);
			locationB.addAdjacentLocation(locationA);
		}
		
		return resultLocations;
	}
	
	/**
	 * Returns the name of the configuration file for the given game type
	 * @param gameType  the type of the game, not null
	 * @return the name of the file with the configuration for the given game 
	 * type
	 */
	private String getGameTypeFilename(GameType gameType) {
		assert(gameType != null);
		
		switch (gameType) {
			case STANDARD_6 :
				return "standard6.json";
			default :
				throw new IllegalStateException("Game type not in switch "
						+ "statement: " + gameType);
		}
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
	
	/**
	 * @return a map of factions to player objects, not null, no null elements,
	 * nonempty
	 */
	Map<Faction, Player> getFactionsToPlayers() {
		return factionsToPlayers;
	}
	
	/**
	 * @return the locations on the board, not null, nonempty, no null elements
	 */
	List<Location> getLocations() {
		return locations;
	}
	
	/**
	 * @return the game type, not null
	 */
	GameType getGameType() {
		return gameType;
	}
	
	
}
