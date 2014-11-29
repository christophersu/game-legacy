package com.boardgame.game;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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
final class Configuration {
	//this object assumes the JSON files have been validated against their 
	//schemas
	
	private static final String PATH = "res/";
	
	private final JSONObject jsonConfiguration;

	private final GameType gameType;
	private final List<Location> locations;
	
	private final Map<Faction, Player> factionsToPlayers;
	private final List<Faction> turnOrder;
	private final List<Faction> tieBreakingOrder;
	private final List<Faction> specialTokenOrder;
	private final List<Integer> specialTokensPerPosition;
	private final Map<Faction, Integer> factionsToSupplies;
	private final Map<Faction, Integer> factionsToNumBases;
	private final int threatLevel;
	private final int round;
	private final Queue<AbstractEventCard> eventCards1Stack;
	private final Queue<AbstractEventCard> eventCards1Discard;
	private final Queue<AbstractEventCard> eventCards2Stack;
	private final Queue<AbstractEventCard> eventCards2Discard;
	private final Queue<AbstractEventCard> eventCards3Stack;
	private final Queue<AbstractEventCard> eventCards3Discard;
	private final Queue<AbstractThreatCard> threatCardsStack;
	private final Queue<AbstractThreatCard> threatCardsDiscard;
	private final List<AbstractCombatCard> combatCards;
	private final boolean hasCombatBonusBeenUsed;
	private final boolean hasSightPowerBeenUsed;
	
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
	public Configuration(GameType gameType) throws IOException, ParseException {
		if (gameType == null) {
			throw new IllegalArgumentException("Game type was null.");
		}

		this.gameType = gameType;
		
		locations = findLocations();
		
		String gameTypeFilename = getGameTypeFilename(gameType);
		FileReader fileReader = new FileReader(PATH + gameTypeFilename);
		JSONParser parser = new JSONParser();
		jsonConfiguration = (JSONObject) parser.parse(fileReader);

		combatCards = findCombatCards();
		factionsToPlayers = findFactionsToPlayers();
		turnOrder = findTurnOrder();
		tieBreakingOrder = findTieBreakingOrder();
		specialTokenOrder = findSpecialTokenOrder();
		specialTokensPerPosition = findSpecialTokensPerPosition();
		factionsToSupplies = findFactionsToSupplies();
		factionsToNumBases = findFactionsToNumBases();
		threatLevel = findThreatLevel();
		round = findRound();
		loadLocationAdditions();

		List<Long> eventCards1StackIndexes = (List<Long>) 
				jsonConfiguration.get("eventCards1Stack");
		List<Long> eventCards1DiscardIndexes = (List<Long>) 
				jsonConfiguration.get("eventCards1Discard");
		List<AbstractEventCard> eventCards1 = (List<AbstractEventCard>) 
				jsonConfiguration.get("eventCards1");
		
		List<Long> eventCards2StackIndexes = (List<Long>) 
				jsonConfiguration.get("eventCards2Stack");
		List<Long> eventCards2DiscardIndexes = (List<Long>) 
				jsonConfiguration.get("eventCards2Discard");
		List<AbstractEventCard> eventCards2 = (List<AbstractEventCard>) 
				jsonConfiguration.get("eventCards2");
		
		List<Long> eventCards3StackIndexes = (List<Long>) 
				jsonConfiguration.get("eventCards3Stack");
		List<Long> eventCards3DiscardIndexes = (List<Long>) 
				jsonConfiguration.get("eventCards3Discard");
		List<AbstractEventCard> eventCards3 = (List<AbstractEventCard>) 
				jsonConfiguration.get("eventCards3");

		List<AbstractEventCard> eventCards1StackList = 
				buildSublist(eventCards1StackIndexes, eventCards1);
		List<AbstractEventCard> eventCards1DiscardList = 
				buildSublist(eventCards1DiscardIndexes, eventCards1);
		List<AbstractEventCard> eventCards2StackList = 
				buildSublist(eventCards2StackIndexes, eventCards2);
		List<AbstractEventCard> eventCards2DiscardList = 
				buildSublist(eventCards2DiscardIndexes, eventCards2);
		List<AbstractEventCard> eventCards3StackList = 
				buildSublist(eventCards3StackIndexes, eventCards3);
		List<AbstractEventCard> eventCards3DiscardList = 
				buildSublist(eventCards3DiscardIndexes, eventCards3);
		eventCards1Stack = new LinkedList<>(eventCards1StackList);
		eventCards1Discard = new LinkedList<>(eventCards1DiscardList);
		eventCards2Stack = new LinkedList<>(eventCards2StackList);
		eventCards2Discard = new LinkedList<>(eventCards2DiscardList);
		eventCards3Stack = new LinkedList<>(eventCards3StackList);
		eventCards3Discard= new LinkedList<>(eventCards3DiscardList);
		
		List<Long> threatCardsStackIndexes = (List<Long>) 
				jsonConfiguration.get("threatCardsStack");
		List<Long> threatCardsDiscardIndexes = (List<Long>) 
				jsonConfiguration.get("threatCardsDiscard");
		List<AbstractThreatCard> threatCards = (List<AbstractThreatCard>) 
				jsonConfiguration.get("threatCards");
		
		List<AbstractThreatCard> threatCardsStackList = 
				buildSublist(threatCardsStackIndexes, threatCards);
		List<AbstractThreatCard> threatCardsDiscardList = 
				buildSublist(threatCardsDiscardIndexes, threatCards);
		
		threatCardsStack = new LinkedList<>(threatCardsStackList);
		threatCardsDiscard = new LinkedList<>(threatCardsDiscardList);
		hasCombatBonusBeenUsed = findHasCombatBonusBeenUsed();
		hasSightPowerBeenUsed = findHasSightPowerBeenUsed();
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
			String terrainString = (String) locationObject.get("terrain");
			Terrain terrain = Terrain.valueOf(terrainString);
			Base base = null;
			
			JSONObject baseObject = (JSONObject) locationObject.get("base");
			
			if (baseObject != null) {
				int baseSize = ((Long) baseObject.get("size")).intValue();
				base = new Base(baseSize);	
			}
			
			int supply = 0;
			
			Long supplyLong = (Long) locationObject.get("supply");
			
			if (supplyLong != null) {
				supply = supplyLong.intValue();
			}

			int invest = 0;
			
			Long investLong = (Long) locationObject.get("invest");
			
			if (investLong != null) {
				invest = investLong.intValue();
			}
			
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

			int locationIndexA = ((Long) adjacencyPair.get(0)).intValue();
			int locationIndexB = ((Long) adjacencyPair.get(1)).intValue();
			
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
				return "standardGame6.json";
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
		
		List<Long> combatCardsConf = 
				(List<Long>) playerConf.get("combatCardsInHand");

		JSONArray unitsConf = (JSONArray) playerConf.get("units");
		int cashInHand = ((Long) playerConf.get("cashInHand")).intValue();
		int cashPool = ((Long) playerConf.get("cashPool")).intValue();
		
		List<AbstractCombatCard> combatCardsInHandList = 
				buildSublist(combatCardsConf, combatCards);
		Set<AbstractCombatCard> combatCardsInHand = 
				new HashSet<>(combatCardsInHandList);
		Set<AbstractUnit> units = new HashSet<>(unitsConf);
		
		return new Player(combatCardsInHand, units, cashInHand, cashPool);
	}
	
	private List<Faction> findTurnOrder() {
		return (List<Faction>) jsonConfiguration.get("turnOrder");
	}
	
	private List<Faction> findTieBreakingOrder() {
		return (List<Faction>) jsonConfiguration.get("tieBreakingOrder");
	}
	
	private List<Faction> findSpecialTokenOrder() {
		return (List<Faction>) jsonConfiguration.get("specialTokenOrder");
	}
	
	private List<Integer> findSpecialTokensPerPosition() {
		return (List<Integer>) 
				jsonConfiguration.get("specialTokensPerPosition");
	}
	
	private Map<Faction, Integer> findFactionsToSupplies() {
		return (Map<Faction, Integer>) 
				jsonConfiguration.get("factionsToSupplies"); 
	}
	
	private Map<Faction, Integer> findFactionsToNumBases() {
		return (Map<Faction, Integer>) 
				jsonConfiguration.get("factionsToNumBases");
	}
	
	private int findThreatLevel() {
		return ((Long) jsonConfiguration.get("threatLevel")).intValue();
	}
	
	private int findRound() {
		return ((Long) jsonConfiguration.get("round")).intValue();
	}
	
	private void loadLocationAdditions() {
		throw new UnsupportedOperationException();
	}

	private <T> List<T> buildSublist(List<Long> indexes, List<T> items) {
		List<T> result = new ArrayList<>();
		
		for (Long i : indexes) {
			int index = i.intValue();
			
			
			if (index >= items.size()) {
				throw new RuntimeException("Index outside bounds: " + index);
			}
			
			result.add(items.get(index));
		}
		
		return result;
	}
	
	private List<AbstractCombatCard> findCombatCards() {
		List<AbstractCombatCard> result = new ArrayList<>();
		
		JSONArray combatCardArray = 
				(JSONArray) jsonConfiguration.get("combatCards");
		
		for (Object combatCardArrayElement : combatCardArray) {
			JSONObject combatCardObject = (JSONObject) combatCardArrayElement;
			String name = (String) combatCardObject.get("name");
			Integer strength = 
					((Long) combatCardObject.get("strength")).intValue();
			Integer killingPotential = 
				((Long) combatCardObject.get("killingPotential")).intValue();
			Integer deathDefense = 
					((Long) combatCardObject.get("deathDefense")).intValue();
			AbstractCombatCard card = new AbstractCombatCard(name, strength, 
					killingPotential, deathDefense);
			result.add(card);
		}
		
		return result;
	}
	
	private boolean findHasCombatBonusBeenUsed() {
		return (Boolean) jsonConfiguration.get("hasCombatBonusBeenUsed");
	}
	
	private boolean findHasSightPowerBeenUsed() {
		return (Boolean) jsonConfiguration.get("hasSightPowerBeenUsed");
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
