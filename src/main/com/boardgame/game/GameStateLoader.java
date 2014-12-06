package com.boardgame.game;

import java.io.FileNotFoundException;
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

import com.boardgame.game.AbstractActionToken.TokenString;
import com.boardgame.game.AbstractUnit.UnitString;
import com.boardgame.game.Location.Terrain;

/**
 * Loads game state from a variety of sources.
 *
 */
public final class GameStateLoader {
	private static final String BOARD_PATH = "res/board.json";
	private static final String STANDARD_6_PATH = "res/standardGame6.json";
	
	private static final Map<UnitString, AbstractUnit> unitStringsToUnits = 
			new HashMap<UnitString, AbstractUnit>();
	private static final Map<TokenString, AbstractActionToken> tokenStringsToTokens = 
			new HashMap<TokenString, AbstractActionToken>();
	
	static {
		unitStringsToUnits.put(UnitString.INFANTRY, new InfantryUnit());
		unitStringsToUnits.put(UnitString.ADVANCED, new AdvancedUnit());
		unitStringsToUnits.put(UnitString.SHIP, new ShipUnit());
		unitStringsToUnits.put(UnitString.BASE_ASSAULT, new BaseAssaultUnit());
		
		tokenStringsToTokens.put(TokenString.BAD_MOVE, new MoveToken(false, TokenString.BAD_MOVE, -1));
		tokenStringsToTokens.put(TokenString.NORMAL_MOVE, new MoveToken(false, TokenString.NORMAL_MOVE, 0));
		tokenStringsToTokens.put(TokenString.MOVE_S, new MoveToken(true, TokenString.MOVE_S, 1));
		tokenStringsToTokens.put(TokenString.INVEST_A, new InvestToken(false, TokenString.INVEST_A));
		tokenStringsToTokens.put(TokenString.INVEST_B, new InvestToken(false, TokenString.INVEST_B));
		tokenStringsToTokens.put(TokenString.INVEST_S, new InvestToken(true, TokenString.INVEST_S));
		tokenStringsToTokens.put(TokenString.BLITZ_A, new BlitzToken(false, TokenString.BLITZ_A));
		tokenStringsToTokens.put(TokenString.BLITZ_B, new BlitzToken(false, TokenString.BLITZ_B));
		tokenStringsToTokens.put(TokenString.BLITZ_S, new BlitzToken(true, TokenString.BLITZ_S));
		tokenStringsToTokens.put(TokenString.DEFENSE_A, new DefenseToken(false, TokenString.DEFENSE_A, 1));
		tokenStringsToTokens.put(TokenString.DEFENSE_B, new DefenseToken(false, TokenString.DEFENSE_B, 1));
		tokenStringsToTokens.put(TokenString.DEFENSE_S, new DefenseToken(true, TokenString.DEFENSE_S, 2));
		tokenStringsToTokens.put(TokenString.ASSIST_A, new AssistToken(false, TokenString.ASSIST_A, 1));
		tokenStringsToTokens.put(TokenString.ASSIST_B, new AssistToken(false, TokenString.ASSIST_B, 1));
		tokenStringsToTokens.put(TokenString.ASSIST_S, new AssistToken(true, TokenString.ASSIST_S, 2));
	}
	
	enum GameType {
		STANDARD_6
	}
	
	/**
	 * Loads a game with the given game type
	 * @param gameType  the type of the game to load, not null
	 * @throws IllegalArgumentException if gameType is null
	 * @return the state of the loaded game
	 */
	public static GameState load(GameType gameType) {
		if (gameType == null) {
			throw new IllegalArgumentException("Game type was null.");
		}
		
		try {
			switch (gameType) {
				case STANDARD_6 :
					return load(STANDARD_6_PATH);
				default :
					throw new IllegalStateException("Game type not in switch "
							+ "statement: " + gameType);
			}
		} catch (IOException | ParseException | SchemaMatchingException e) {
			throw new AssertionError(e);
		}
	}
	
	/**
	 * Loads game state from the file with the given filePath.
	 * @param filePath  the path to the file with the game state
	 * @throws IOException if something goes wrong reading the file
	 * @throws ParseException if the file could not be parsed correctly
	 * @throws SchemaMatchingException if the file does not match its schema
	 * @return the state of the loaded game
	 */
	public static GameState load(String filePath) 
			throws IOException, ParseException, SchemaMatchingException {
		if (!ValidateJsonFiles.validateVariableGameState(filePath)) {
			throw new SchemaMatchingException();
		}
		
		FileReader fileReader = new FileReader(filePath);
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
		
		gameStateBuilder.setLocations(findLocations(root))
			.setUnitStringsToUnits(unitStringsToUnits);

		return gameStateBuilder.build();
	}
	
	private static List<Location> findLocations(JSONObject variableRoot) {
		assert ValidateJsonFiles.validateConstantGameState(BOARD_PATH) :
			"Board failed to validate against its schema.";
		
		FileReader fileReader;
		try {
			fileReader = new FileReader(BOARD_PATH);
		} catch (FileNotFoundException e) {
			throw new AssertionError(e);
		}
		
		JSONParser parser = new JSONParser();
		
		JSONObject constantRoot;
		try {
			constantRoot = (JSONObject) parser.parse(fileReader);
		} catch (IOException | ParseException e) {
			throw new AssertionError(e);
		}
		
		List<Location> resultLocations = new ArrayList<Location>();
		Set<Location> locationsSet = new HashSet<Location>();
		
		JSONArray locationsArray = (JSONArray) constantRoot.get("locations");
		
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
			
			assert !isLocationDuplicate : "Duplicate location encountered."
					+ "Name: " + location.getName();
			
			resultLocations.add(location);
		}

		loadLocationAdditions(variableRoot, resultLocations);
		
		JSONArray adjacenciesArray = 
				(JSONArray) constantRoot.get("adjacencies");
		
		for (Object adjacenciesArrayElement : adjacenciesArray) {
			JSONArray adjacencyPair = (JSONArray) adjacenciesArrayElement;

			int locationIndexA = ((Long) adjacencyPair.get(0)).intValue();
			int locationIndexB = ((Long) adjacencyPair.get(1)).intValue();

			assert locationIndexA >= 0 && 
					locationIndexA < resultLocations.size() : 
						"Location index out of bounds: " + locationIndexA;

			assert locationIndexB >= 0 && 
					locationIndexB < resultLocations.size() : 
						"Location index out of bounds: " + locationIndexB;
			
			assert locationIndexA != locationIndexB : "Location cannot be "
					+ "adjacent to itself: " + locationIndexA;

			Location locationA = resultLocations.get(locationIndexA);
			Location locationB = resultLocations.get(locationIndexB);

			locationA.addAdjacentLocation(locationB);
			locationB.addAdjacentLocation(locationA);
		}
		
		return resultLocations;
	}
	
	private static Map<Faction, Player> findFactionsToPlayers(JSONObject root, 
			List<AbstractCombatCard> combatCards) {
		Map<Faction, Player> result = new HashMap<>();
		
		for (Faction faction : Faction.values()) {
			result.put(faction, createPlayer(root, combatCards, faction));
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	private static Player createPlayer(JSONObject root, 
			List<AbstractCombatCard> combatCards, Faction faction) {
		assert(faction != null);
		
		String factionKey = faction.toString();
		
		JSONObject playersConf = 
				(JSONObject) root.get("factionsToPlayers");
		JSONObject playerConf = (JSONObject) playersConf.get(factionKey);
		
		List<Long> combatCardsInHandIndexes = 
				(List<Long>) playerConf.get("combatCardsInHand");

		List<Long> combatCardsDiscardIndexes = 
				(List<Long>) playerConf.get("combatCardsDiscard");
		
		JSONArray unitsConf = (JSONArray) playerConf.get("units");
		int cashInHand = ((Long) playerConf.get("cashInHand")).intValue();
		int cashPool = ((Long) playerConf.get("cashPool")).intValue();
		
		List<AbstractCombatCard> combatCardsInHandList = 
				buildSublist(combatCardsInHandIndexes, combatCards);
		Set<AbstractCombatCard> combatCardsInHand = 
				new HashSet<>(combatCardsInHandList);

		Set<AbstractCombatCard> combatCardsDiscard = new HashSet<>();
		
		if (combatCardsDiscardIndexes != null) {
			List<AbstractCombatCard> combatCardsDiscardList = 
					buildSublist(combatCardsDiscardIndexes, combatCards);
			combatCardsDiscard.addAll(combatCardsDiscardList);
		}
		
		
		Set<AbstractUnit> units = new HashSet<>(unitsConf);
		

		
		return new Player(combatCardsInHand, combatCardsDiscard, units, 
				cashInHand, cashPool);
	}
	
	@SuppressWarnings("unchecked")
	private static List<Faction> findTurnOrder(JSONObject root) {
		return (List<Faction>) root.get("turnOrder");
	}
	
	@SuppressWarnings("unchecked")
	private static List<Faction> findTieBreakingOrder(JSONObject root) {
		return (List<Faction>) root.get("tieBreakingOrder");
	}
	
	@SuppressWarnings("unchecked")
	private static List<Faction> findSpecialTokenOrder(JSONObject root) {
		return (List<Faction>) root.get("specialTokenOrder");
	}
	
	@SuppressWarnings("unchecked")
	private static List<Integer> findSpecialTokensPerPosition(JSONObject root) {
		return (List<Integer>) root.get("specialTokensPerPosition");
	}       
	        
	@SuppressWarnings("unchecked")
	private static Map<Faction, Integer> findFactionsToSupplies(JSONObject root) {
		return (Map<Faction, Integer>) root.get("factionsToSupplies"); 
	}       
	        
	@SuppressWarnings("unchecked")
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
			int targetLocationIndex = ((Long) locationAddition.get("targetLocation")).intValue();
			
			Location location = locations.get(targetLocationIndex);
			
			Base base = null;
			
			if (baseObject != null) {
				int baseDefense = ((Long) baseObject.get("defense")).intValue();
				boolean isDefenseActive = 
						(Boolean) baseObject.get("isDefenseActive");
				base = new Base(location.getBaseStrength(), baseDefense, 
						isDefenseActive);	
			}
			
			Faction owner = null;
			
			if (ownerString != null) {
				owner = Faction.valueOf(ownerString);
			}
			 
			Collection<AbstractUnit> units = getUnits(unitsArray); 
			
			Location modifiedLocation = 
					new Location(location, base, new HashSet<>(), units, owner);
			
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
	
	@SuppressWarnings("unchecked")
	private static List<Long> findCardIndexes(JSONObject root, String key) {
		return (List<Long>) root.get(key);
	}
	
	@SuppressWarnings("unchecked")
	private static <T> List<T> findCards(JSONObject root, String key) {
		return (List<T>) root.get(key);
	}
	
	private static Collection<AbstractUnit> getUnits(JSONArray units) {
		Collection<AbstractUnit> result = new ArrayList<AbstractUnit>();
		
		if (units != null) {
			for (Object element : units) {
				UnitString unitString = UnitString.valueOf((String) element);
				AbstractUnit unit = unitStringsToUnits.get(unitString);
				result.add(unit);
			}	
		}
		
		return result;
	}
}
