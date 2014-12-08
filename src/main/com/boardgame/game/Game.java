package com.boardgame.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * A game object that holds the state of the game and has methods that can 
 * manipulate that state when used appropriately.
 *
 */
public final class Game {
	private final GameState gameState;
	private final IntegersToObjects integersToObjects;
	
	private final Queue<ActionLocations> actionLocationsQueue;
	
	private RoundState roundState;
	
	/**
	 * Creates a new game with the given initial game state
	 * @param gameState  the initial game state, not null
	 * @throws IllegalArgumentException if gameState is null
	 */
	public Game(GameState gameState) {
		if (gameState == null) {
			throw new IllegalArgumentException("Game state was null.");
		}
		
		this.gameState = gameState;
		this.integersToObjects = new IntegersToObjects();
		this.actionLocationsQueue = new PriorityQueue<>();
	}
	
	/**
	 * Puts the player with the given playerId in the game (if not already), and
	 * associates the player with the given faction. The association between 
	 * playerId and faction is 1 to 1. 
	 * @param playerId  an identifier for a particular player
	 * @param faction  the faction that the player will be associated with, not
	 * null
	 * @throws IllegalArgumentException if faction is null
	 * @throws IllegalStateException if the game is full and playerId is not 
	 * already in the game
	 * @throws IllegalStateException if called after the game has started
	 * @return true if the player was successfully associated with the given 
	 * faction, false if another player is already associated with the faction
	 */
	public boolean putPlayer(int playerId, Faction faction) {
		if (faction == null) {
			throw new IllegalArgumentException("Null faction");
		}
		
		if (hasStartedGame()) {
			throw new IllegalStateException("Can't call after game start");
		}
		
		OneToOneMap<Integer, Faction> integersToFactions = 
				integersToObjects.integersToFactions;
		
		int expectedPlayers = gameState.getNumFactions();
		int currentPlayers = integersToFactions.size();
		
		assert currentPlayers <= expectedPlayers;
		
		if (!integersToFactions.containsKey(playerId) && 
				currentPlayers == expectedPlayers) {
			throw new IllegalStateException("Too many players");
		}
		
		boolean success = !integersToFactions.containsValue(faction) &&
				integersToFactions.getKey(faction) == playerId;
		
		integersToObjects.integersToFactions.put(playerId, faction);
		
		return success;
	}
	
	/**
	 * Starts the game
	 * @throws IllegalStateException if the game has already been started
	 */
	public void startGame() {
		if (hasStartedGame()) {
			throw new IllegalStateException("Can't call after game start");
		}
		
		int numPlayers = integersToObjects.integersToFactions.size();
		int numExpected = gameState.getNumFactions();
		
		if (numPlayers < numExpected) {
			throw new IllegalStateException("Not enough players");
		}
		
		assert numPlayers == numExpected;
		
		roundState = new RoundState();
	}
	
	private boolean hasStartedGame() {
		return roundState != null;
	}
	
	/**
	 * Places the given faction's given token at the given location
	 * @param faction  the faction whose token will be placed, not null
	 * @param token  the token that will be placed, not null
	 * @param location  the location at which the token will be placed, not null
	 * @throws IllegalArgumentException if faction is null
	 * @throws IllegalArgumentException if token is null
	 * @throws IllegalArgumentException if location is null
	 * @throws IllegalStateException if not in the token placing state
	 * @throws IllegalStateException if faction is not the owner of location
	 * @throws IllegalStateException if faction cannot place token
	 * @return whether or not the token was placed
	 */
	public boolean placeToken(Faction faction, AbstractActionToken token, 
			Location location) {
		if (!roundState.getRoundPhase().getCanPlaceToken()) {
			throw new IllegalStateException("Can't call when not placing "
					+ "tokens");
		}
		
		return placeTokenHelper(faction, token, location);
	}
	
	private boolean placeTokenHelper(Faction faction, AbstractActionToken token, 
			Location location) {
		if (faction == null) {
			throw new IllegalArgumentException("Null faction");
		}
		
		if (token == null) {
			throw new IllegalArgumentException("Null token");
		}
		
		if (location == null) {
			throw new IllegalArgumentException("Null location");
		}
		
		if (location.getOwner() != faction) {
			throw new IllegalStateException("Faction does not own location");
		}
		
		Player player = gameState.getFactionsToPlayers().get(faction);
		
		if (!player.hasToken(token)) {
			throw new IllegalStateException("Faction does not have token");
		}
		
		if (token.isSpecial && player.getNumSpecialTokensUsed() + 1 == 
				gameState.getNumSpecialTokensForFaction(faction)) {
			throw new IllegalStateException("Can't use more special tokens");
		}
		
		assert player.getNumSpecialTokensUsed() + 1 <= 
				gameState.getNumSpecialTokensForFaction(faction);

		boolean wasTokenPlaced = location.placeActionToken(token);
		
		if (!wasTokenPlaced) {
			boolean removeResult = player.useToken(token);
			assert removeResult;
		}
		
		return wasTokenPlaced;
	}
	
	/**
	 * Removes the given faction's  token from the given location if one is 
	 * there
	 * @param faction  the faction whose token will be removed, not null
	 * @param location  the location where the token will be removed, not null
	 * @throws IllegalArgumentException if faction is null
	 * @throws IllegalArgumentException if location is null
	 * @throws IllegalStateException if not in the token placing state
	 * @throws IllegalStateException if faction is not the owner of location
	 * @return whether or not a token was removed
	 */
	public boolean removeToken(Faction faction, Location location) {
		if (roundState.getRoundPhase().getCanPlaceToken()) {
			throw new IllegalStateException("Can't call when not placing "
					+ "tokens");
		}
		
		return removeTokenHelper(faction, location);
	}
	
	private boolean removeTokenHelper(Faction faction, Location location) {
		if (faction == null) {
			throw new IllegalArgumentException("Faction was null");
		}
		
		if (location == null) {
			throw new IllegalArgumentException("Location was null");
		}
		
		if (location.getOwner() != faction) {
			throw new IllegalStateException("Faction does not own location");
		}
		
		Player player = gameState.getFactionsToPlayers().get(faction);

		AbstractActionToken removedToken = location.removeActionToken();
		
		boolean wasTokenRemoved = false;
		
		if (removedToken != null) {
			wasTokenRemoved = true;
			player.unUseToken(removedToken);
		}
		
		return wasTokenRemoved;
	}
	
	/**
	 * Switches the given faction's token at the given location for the given 
	 * token
	 * @param faction  the faction whose tokens will be switched, not null
	 * @param nextToken  the token to be placed, not null
	 * @param location  the location where the switch will happen, not null
	 * @throws IllegalArgumentException if faction is null
	 * @throws IllegalArgumentException if nextToken is null
	 * @throws IllegalArgumentException if location is null
	 * @throws IllegalStateException if not in the sight power state
	 * @throws IllegalStateException if faction is not the owner of location
	 * @throws IllegalStateException if faction cannot place token
	 * @throws IllegalStateException if faction is not allowed to switch
	 * @return whether the switch occurred
	 */
	public boolean switchToken(Faction faction, AbstractActionToken nextToken,
			Location location) {
		if (!roundState.getRoundPhase().getCanSwitchToken()) {
			throw new IllegalStateException("Can't call when not in sight "
					+ "power state");
		}
		
		if (gameState.getFactionInSpecialTokenOrderPosition(0) != faction ||
				gameState.getHasSightPowerBeenUsed()) {
			throw new IllegalStateException("Faction cannot use power");
		}
		
		boolean switchResult = false;
		
		boolean removeResult = removeTokenHelper(faction, location);
		
		if (removeResult) {
			switchResult = placeTokenHelper(faction, nextToken, location);
			assert switchResult;
		}
		
		if (switchResult) {
			gameState.setHasSightPowerBeenUsed(true);
		}
		
		return switchResult;
	}
	
	/**
	 * Returns the faction who may use tokens right now
	 * @throws IllegalStateException if not in the token using state
	 * @return the faction who may use tokens right now
	 */
	public Faction getCurrentFaction() {
		if (actionLocationsQueue.isEmpty()) {
			throw new IllegalStateException("Not using tokens right now.");
		}
		
		return actionLocationsQueue.peek().getFaction();
	}
	
	/**
	 * Returns the set of locations whose tokens may be used
	 * @throws IllegalStateException if not in the token using state
	 * @return the set of locations whose tokens may be used
	 */
	public Set<Location> getActionLocations() {
		if (actionLocationsQueue.isEmpty()) {
			throw new IllegalStateException("Not using tokens right now.");
		}
		
		return actionLocationsQueue.peek().getLocations();
	}
	
	public Set<Location> findValidLocationTargets(Faction faction, 
			Location sourceLocation) {
		throw new UnsupportedOperationException();
	}
	
	public void chooseUnitforAction(AbstractUnit unit) {
		throw new UnsupportedOperationException();
	}
	
	public void useToken(Faction faction, AbstractActionToken token, 
			Location tokenLocation, Location target) {
		throw new UnsupportedOperationException();
	}
	
	public void resetToken(Faction faction, AbstractActionToken token) {
		throw new UnsupportedOperationException();
	}
	
	public void support(Faction supportingFaction, Location supportingLocation, 
			Faction supportedFaction) {
		throw new UnsupportedOperationException();
	}
	
	public void useCombatCard(Faction faction, AbstractCombatCard combatCard) {
		throw new UnsupportedOperationException();
	}
	
	public void useCombatBonus(Faction faction) {
		throw new UnsupportedOperationException();
	}
	
	public void retreat(Faction defeatedFaction, AbstractUnit retreatingUnit, 
			Location target) {
		throw new UnsupportedOperationException();
	}
	
	public void spawnUnit(Faction faction, Location location, AbstractUnit unit) {
		throw new UnsupportedOperationException();
	}
	
	public void bid(Faction faction, int bid) {
		throw new UnsupportedOperationException();
	}
	
	public void breakTie(Faction loser, Faction winner) {
		throw new UnsupportedOperationException();
	}
	
	public void setUserReady(Faction faction, boolean isReady) {
		throw new UnsupportedOperationException();
	}
	
	public int getNumPlayers() {
		throw new UnsupportedOperationException();
	}
	
	public int getState() {
		throw new UnsupportedOperationException();
	}

	public String getSnapshotForPlayer(Faction faction) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @return the object with integer to object mappings
	 */
	public IntegersToObjects getIntegersToObjects() {
		return integersToObjects;
	}
	
	/**
	 * Contains the mappings of integers to the appropriate objects
	 *
	 */
	public class IntegersToObjects {
		private final OneToOneMap<Integer, Faction> integersToFactions;
		private final OneToOneMap<Integer, Location> integersToLocations;
		private final OneToOneMap<Integer, AbstractActionToken> integersToTokens;
		private final OneToOneMap<Integer, AbstractUnit> integersToUnits;
		
		IntegersToObjects() {
			assert gameState != null;
			
			integersToFactions = new OneToOneMap<>();
			integersToLocations = makeLocationsMap();
			integersToTokens = makeTokensMap();
			integersToUnits = makeUnitsMap();
		}
		
		OneToOneMap<Integer, Location> makeLocationsMap() {
			return listToMap(gameState.getLocations());
		}
		
		OneToOneMap<Integer, AbstractActionToken> makeTokensMap() {
			Collection<AbstractActionToken> allTokens =
					gameState.getTokenStringsToTokens().values();
			return listToMap(new ArrayList<>(allTokens));
		}
		
		OneToOneMap<Integer, AbstractUnit> makeUnitsMap() {
			Collection<AbstractUnit> allUnits = 
					gameState.getUnitStringsToUnits().values();
			return listToMap(new ArrayList<>(allUnits));
		}
		
		private <E> OneToOneMap<Integer, E> listToMap(List<E> list) {
			OneToOneMap<Integer, E> integersToElements = new OneToOneMap<>();
			
			for (int i = 0; i < list.size(); i++) {
				integersToElements.put(i, list.get(i));
			}
			
			return integersToElements;
		}
		
		/**
		 * Returns the faction associated with the given integer id
		 * @param factionId  the integer id of a certain faction
		 * @throws IllegalArgumentException if factionId is not a valid id
		 * @return the faction associated with the given integer id
		 */
		public Faction getFaction(int factionId) {
			if (!integersToFactions.containsKey(factionId)) {
				throw new IllegalArgumentException("Invalid faction ID");
			}
			
			Faction result = integersToFactions.getValue(factionId);
			
			assert result != null;
			
			return result;
		}
		
		int getFactionId(Faction faction) {
			assert faction != null;
			assert integersToFactions.containsValue(faction);
			
			return integersToFactions.getKey(faction);
		}
		
		/**
		 * Returns the location associated with the given location id
		 * @param locationId  the location id of a certain location
		 * @throw IllegalArgumentException if locationId is not a valid id
		 * @return the location associated with the given location id
		 */
		public Location getLocation(int locationId) {
			if (!integersToLocations.containsKey(locationId)) {
				throw new IllegalArgumentException("Invalid faction ID");
			}
			
			Location result = integersToLocations.getValue(locationId);
			
			assert result != null;
			
			return result;
		}
		
		int getLocationId(Location location) {
			assert location != null;
			assert integersToLocations.containsValue(location);
			
			return integersToLocations.getKey(location);
		}
		
		/**
		 * Returns the token associated with the given token id
		 * @param tokenId  the token id of a certain token
		 * @throw IllegalArgumentException if tokenId is not a valid id
		 * @return the token associated with the given token id
		 */
		public AbstractActionToken getActionToken(int tokenId) {
			if (!integersToTokens.containsKey(tokenId)) {
				throw new IllegalArgumentException("Invalid token ID");
			}
			
			AbstractActionToken result = integersToTokens.getValue(tokenId);
			
			assert result != null;
			
			return result;
		}
		
		int getTokenId(AbstractActionToken token) {
			assert token != null;
			assert integersToTokens.containsValue(token);
			
			return integersToTokens.getKey(token);
		}
		
		/**
		 * Returns the unit associated with the given unit id
		 * @param unitId  the unit id of a certain unit
		 * @throw IllegalArgumentException if unitId is not a valid id
		 * @return the unit associated with the given unit id
		 */
		public AbstractUnit getUnit(int unitId) {
			if (!integersToUnits.containsKey(unitId)) {
				throw new IllegalArgumentException("Invalid unit ID");
			}
			
			AbstractUnit result = integersToUnits.getValue(unitId);
			
			assert result != null;
			
			return result;
		}
		
		int getUnitId(AbstractUnit unit) {
			assert unit != null;
			assert integersToUnits.containsValue(unit);
			
			return integersToUnits.getKey(unit);
		}
	}

	public void buildActionLocationsQueue() {
		assert actionLocationsQueue.isEmpty();
		
		Map<Faction, Map<Integer, ActionLocations>> 
			factionsToTokenPrioritiesToActionLocations = new HashMap<>();
		
		for (Faction faction : gameState.getFactions()) {
			factionsToTokenPrioritiesToActionLocations.put(faction, new HashMap<>());
		}
		
		for (Location location : gameState.getLocations()) {
			if (location.hasToken() && 
					location.getActionToken().getPriority() != null) {
				Faction faction = location.getOwner();
				AbstractActionToken token = location.getActionToken();
				
				Map<Integer, ActionLocations> tokenPrioritiesToActionLocations =
						factionsToTokenPrioritiesToActionLocations.get(faction);
				
				int tokenPriority = token.getPriority();
				
				if (!tokenPrioritiesToActionLocations.containsKey(tokenPriority)) {
					int factionPriority = gameState.getTurnOrderPosition(faction);
					ActionLocations actionLocations = 
							new ActionLocations(faction, factionPriority, location);
					tokenPrioritiesToActionLocations.put(tokenPriority, actionLocations);
				}
				
				ActionLocations actionLocations = 
						tokenPrioritiesToActionLocations.get(tokenPriority);
				
				actionLocations.addLocation(location);
			}
		}
		
		for (Map<Integer, ActionLocations> tokenPrioritiesToActionLocations : 
			factionsToTokenPrioritiesToActionLocations.values()) {
			for (ActionLocations actionLocations : 
				tokenPrioritiesToActionLocations.values()) {
				actionLocationsQueue.add(actionLocations);
			}			
		}
	}
}
