package com.boardgame.game;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.boardgame.game.Location.Terrain;

/**
 * Loads game state from a variety of sources.
 *
 */
public final class GameStateLoader {
	//this object assumes the JSON files have been validated against their 
	//schemas
	
	private static final String PATH = "res/";
	
	enum GameType {
		STANDARD_6
	}
	
	public static GameState load(GameType gameType) throws IOException, ParseException {
		if (gameType == null) {
			throw new IllegalArgumentException("Game type was null.");
		}
		
		switch (gameType) {
			case STANDARD_6 :
				return load("standardGame6.json");
			default :
				throw new IllegalStateException("Game type not in switch "
						+ "statement: " + gameType);
		}
	}
	
	public static GameState load(String filename) throws IOException, ParseException {
		FileReader fileReader = new FileReader(PATH + filename);
		JSONParser parser = new JSONParser();
		JSONObject root = (JSONObject) parser.parse(fileReader);
		
		GameState.Builder gameStateBuilder = new GameState.Builder();
		
		List<AbstractCombatCard> combatCards = findCombatCards(root);
		
		gameStateBuilder.setCombatCards(combatCards)
			.setFactionsToPlayers(findFactionsToPlayers(root, combatCards))
			.setTurnOrder(findTurnOrder(root))
			.setTieBreakingOrder(findTieBreakingOrder(root))
			.setSpecialTokenOrder(findSpecialTokenOrder(root))
			.setSpecialTokensPerPosition(findSpecialTokensPerPosition(root))
			.setFactionsToSupplies(findFactionsToSupplies(root))
			.setFactionsToNumBases(findFactionsToNumBases(root))
			.setThreatLevel(findThreatLevel(root))
			.setRound(findRound(root));

		List<Long> eventCards1StackIndexes = findCardIndexes(root, "eventCards1Stack");
		List<Long> eventCards1DiscardIndexes = findCardIndexes(root, "eventCards1Discard");
		List<Long> eventCards2StackIndexes = findCardIndexes(root, "eventCards2Stack");
		List<Long> eventCards2DiscardIndexes = findCardIndexes(root, "eventCards2Discard");
		List<Long> eventCards3StackIndexes = findCardIndexes(root, "eventCards3Stack");
		List<Long> eventCards3DiscardIndexes = findCardIndexes(root, "eventCards3Discard"); 

		List<AbstractEventCard> eventCards1 = findCards(root, "eventCards1");
		List<AbstractEventCard> eventCards2 = findCards(root, "eventCards2");
		List<AbstractEventCard> eventCards3 = findCards(root, "eventCards3");
		
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

		gameStateBuilder.setEventCards1Stack(new LinkedList<>(eventCards1StackList))
			.setEventCards1Discard(new LinkedList<>(eventCards1DiscardList))
			.setEventCards2Stack(new LinkedList<>(eventCards2StackList))
			.setEventCards2Discard(new LinkedList<>(eventCards2DiscardList))
			.setEventCards3Stack(new LinkedList<>(eventCards3StackList))
			.setEventCards3Discard(new LinkedList<>(eventCards3DiscardList));

		List<Long> threatCardsStackIndexes = findCardIndexes(root, "threatCardsStack");
		List<Long> threatCardsDiscardIndexes = findCardIndexes(root, "threatCardsDiscard");
		
		List<AbstractThreatCard> threatCards = findCards(root, "threatCards");

		List<AbstractThreatCard> threatCardsStackList = 
				buildSublist(threatCardsStackIndexes, threatCards);
		List<AbstractThreatCard> threatCardsDiscardList = 
				buildSublist(threatCardsDiscardIndexes, threatCards);

		gameStateBuilder.setThreatCardsStack(new LinkedList<>(threatCardsStackList))
			.setThreatCardsDiscard(new LinkedList<>(threatCardsDiscardList));
		
		gameStateBuilder.setHasCombatBonusBeenUsed(findHasCombatBonusBeenUsed(root))
			.setHasSightPowerBeenUsed(findHasSightPowerBeenUsed(root));
		
		List<Location> locations = findLocations();
		
		loadLocationAdditions(root, locations);
		gameStateBuilder.setLocations(locations);
		
		return gameStateBuilder.build();
	}
	
	/**
	 * Gets a list of the location objects for the board
	 * @return a list of the location objects for the board, not null, nonempty,
	 * no null elements
	 * @throws ParseException if the JSON is parsed incorrectly
	 * @throws IOException if the file couldn't be read correctly
	 */
	private static List<Location> findLocations() throws IOException, ParseException {
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
	private static String getGameTypeFilename(GameType gameType) {
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
	private static Set<Integer> findValidNumPlayers(JSONObject root) {
		JSONArray validNumPlayersArray = (JSONArray) root.get("validNumPlayers");
		return new HashSet<>(validNumPlayersArray);
	}
	
	/**
	 * Finds the mapping of factions to player data
	 * @return the mapping of factions to player data, not null, no null 
	 * elements
	 */
	private static Map<Faction, Player> findFactionsToPlayers(JSONObject root, 
			List<AbstractCombatCard> combatCards) {
		Map<Faction, Player> result = new HashMap<>();
		
		for (Faction faction : Faction.values()) {
			result.put(faction, createPlayer(root, combatCards, faction));
		}
		
		return result;
	}
	
	/**
	 * Creates a player for the given faction
	 * @param faction  the player's faction, not null 
	 * @return the player with the given faction
	 */
	private static Player createPlayer(JSONObject root, 
			List<AbstractCombatCard> combatCards, Faction faction) {
		assert(faction != null);
		
		String factionKey = faction.toString();
		
		JSONObject playersConf = 
				(JSONObject) root.get("factionsToPlayers");
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
	
	private static List<Faction> findTurnOrder(JSONObject root) {
		return (List<Faction>) root.get("turnOrder");
	}
	
	private static List<Faction> findTieBreakingOrder(JSONObject root) {
		return (List<Faction>) root.get("tieBreakingOrder");
	}
	
	private static List<Faction> findSpecialTokenOrder(JSONObject root) {
		return (List<Faction>) root.get("specialTokenOrder");
	}
	
	private static List<Integer> findSpecialTokensPerPosition(JSONObject root) {
		return (List<Integer>) root.get("specialTokensPerPosition");
	}       
	        
	private static Map<Faction, Integer> findFactionsToSupplies(JSONObject root) {
		return (Map<Faction, Integer>) root.get("factionsToSupplies"); 
	}       
	        
	private static Map<Faction, Integer> findFactionsToNumBases(JSONObject root) {
		return (Map<Faction, Integer>) root.get("factionsToNumBases");
	}       
	        
	private static int findThreatLevel(JSONObject root) {
		return ((Long) root.get("threatLevel")).intValue();
	}       
	        
	private static int findRound(JSONObject root) {
		return ((Long) root.get("round")).intValue();
	}       
	        
	private static void loadLocationAdditions(JSONObject root, 
			List<Location> locations) {
		JSONArray locationAdditionsArray = (JSONArray) root.get("locationAdditions");
		
		for (Object locationAdditionsElement : locationAdditionsArray) {
			JSONObject locationAddition = (JSONObject) locationAdditionsElement;
			JSONObject baseObject = (JSONObject) locationAddition.get("base");
			String ownerString = (String) locationAddition.get("owner");
			JSONArray unitsArray = (JSONArray) locationAddition.get("units");
			String tokenString = (String) locationAddition.get("actionToken");
			int targetLocationIndex = ((Long) locationAddition.get("targetLocation")).intValue();
			
			Location location = locations.get(targetLocationIndex);
			
			Base base = null;
			
			if (baseObject != null) {
				int baseDefense = ((Long) baseObject.get("defense")).intValue();
				base = new Base(location.getBaseStrength(), baseDefense);	
			}
			
			Faction owner = ownerString == null ? null : Faction.valueOf(ownerString);
			Collection<AbstractUnit> units = getUnits(unitsArray); 
			AbstractActionToken actionToken = tokenString == null ? null : new AbstractActionToken(tokenString);
			Location modifiedLocation = 
					new Location(location, base, units, owner, actionToken);
			
			locations.set(targetLocationIndex, modifiedLocation);
		}
	}       
            
	private static <T> List<T> buildSublist(List<Long> indexes, List<T> items) {
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
	
	private static List<AbstractCombatCard> findCombatCards(JSONObject root) {
		List<AbstractCombatCard> result = new ArrayList<>();
		
		JSONArray combatCardArray = (JSONArray) root.get("combatCards");
		
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
	
	private static boolean findHasCombatBonusBeenUsed(JSONObject root) {
		return (Boolean) root.get("hasCombatBonusBeenUsed");
	}
	
	private static boolean findHasSightPowerBeenUsed(JSONObject root) {
		return (Boolean) root.get("hasSightPowerBeenUsed");
	}
	
	private static List<Long> findCardIndexes(JSONObject root, String key) {
		return (List<Long>) root.get(key);
	}
	
	private static <T> List<T> findCards(JSONObject root, String key) {
		return (List<T>) root.get(key);
	}
	
	private static Collection<AbstractUnit> getUnits(JSONArray units) {
		Collection<AbstractUnit> result = new ArrayList<AbstractUnit>();
		
		if (units != null) {
			for (Object element : units) {
				String unitString = (String) element;
				result.add((AbstractUnit) new AbstractUnit(unitString));
			}	
		}
		
		return result;
	}
}
