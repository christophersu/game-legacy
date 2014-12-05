package com.boardgame.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.boardgame.game.AbstractActionToken.TokenString;

/**
 * A game object that holds the state of the game and has methods that can 
 * manipulate that state when used appropriately.
 *
 */
public final class Game {
	private final GameState gameState;
	private final IntegersToObjects integersToObjects;
	private final List<AbstractActionToken> actionTokens; 
	
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
		this.actionTokens = findActionTokens();
		
		this.integersToObjects = new IntegersToObjects();
	}
	
	private static List<AbstractActionToken> findActionTokens() {
		List<AbstractActionToken> actionTokens = new ArrayList<>();

		actionTokens.add(new MoveToken(false, TokenString.BAD_MOVE, -1));
		actionTokens.add(new MoveToken(false, TokenString.NORMAL_MOVE, 0));
		actionTokens.add(new MoveToken(true, TokenString.MOVE_S, 1));
		actionTokens.add(new InvestToken(false, TokenString.INVEST));
		actionTokens.add(new InvestToken(false, TokenString.INVEST));
		actionTokens.add(new InvestToken(true, TokenString.INVEST_S));
		actionTokens.add(new BlitzToken(false, TokenString.BLITZ));
		actionTokens.add(new BlitzToken(false, TokenString.BLITZ));
		actionTokens.add(new BlitzToken(true, TokenString.BLITZ_S));
		actionTokens.add(new DefenseToken(false, TokenString.DEFENSE, 1));
		actionTokens.add(new DefenseToken(false, TokenString.DEFENSE, 1));
		actionTokens.add(new DefenseToken(true, TokenString.DEFENSE_S, 2));
		actionTokens.add(new AssistToken(false, TokenString.ASSIST, 1));
		actionTokens.add(new AssistToken(false, TokenString.ASSIST, 1));
		actionTokens.add(new AssistToken(true, TokenString.ASSIST_S, 2));
		
		return actionTokens;
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
	 * @return true if the player was successfully associated with the given 
	 * faction, false if another player is already associated with the faction
	 */
	public boolean putPlayer(int playerId, Faction faction) {
		OneToOneMap<Integer, Faction> integersToFactions = 
				integersToObjects.integersToFactions;
		if (faction == null) {
			throw new IllegalArgumentException("Null faction");
		}
		
		int expectedPlayers = gameState.getNumFactions();
		int currentPlayers = integersToFactions.size();
		
		assert currentPlayers <= expectedPlayers;
		
		if (!integersToFactions.containsKey(playerId) && 
				currentPlayers == expectedPlayers) {
			throw new IllegalStateException("Too many players");
		}
		
		boolean success = true;
		
		if (integersToFactions.containsValue(faction) &&
				integersToFactions.getKey(faction) != playerId) {
			success = false;
		}
		
		integersToObjects.integersToFactions.put(playerId, faction);
		
		return success;
	}
	
	public void startGame() {
		throw new UnsupportedOperationException();
	}
	
	public void placeToken(Faction faction, AbstractActionToken token, 
			Location location) {
		throw new UnsupportedOperationException();
	}
	
	public void removeToken(Faction faction, Location location) {
		throw new UnsupportedOperationException();
	}
	
	public void switchToken(Faction faction, Location location, 
			AbstractActionToken nextToken) {
		throw new UnsupportedOperationException();
	}
	
	public Player getCurrentPlayer() {
		throw new UnsupportedOperationException();
	}
	
	public Set<AbstractActionToken> getTokenChoices(Faction faction) {
		throw new UnsupportedOperationException();
	}
	
	public Set<Location> getValidLocationTargets(Faction faction, 
			AbstractActionToken token) {
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
		final OneToOneMap<Integer, Faction> integersToFactions;
		final OneToOneMap<Integer, Location> integersToLocations;
		final OneToOneMap<Integer, AbstractActionToken> integersToTokens;
		
		IntegersToObjects() {
			assert gameState != null;
			assert actionTokens != null;
			
			integersToFactions = new OneToOneMap<>();
			integersToLocations = makeLocationsMap();
			integersToTokens = makeTokensMap();
		}
		
		OneToOneMap<Integer, Location> makeLocationsMap() {
			return listToMap(gameState.getLocations());
		}
		
		OneToOneMap<Integer, AbstractActionToken> makeTokensMap() {
			return listToMap(actionTokens);
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
	}
}
