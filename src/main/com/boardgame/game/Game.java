package com.boardgame.game;

import java.io.IOException;
import java.util.Set;

import org.json.simple.parser.ParseException;

/**
 * A game object that holds the state of the game and has methods that can 
 * manipulate that state when used appropriately.
 *
 */
public final class Game {
	private final IntegersToObjects integersToObjects;
	private final GameState gameState;
	
	public Game(GameState gameState) {
		if (gameState == null) {
			throw new IllegalArgumentException("Game state was null.");
		}
		
		this.integersToObjects = new IntegersToObjects(gameState);
		this.gameState = gameState;
	}
	
	public void addPlayer(int playerId, Faction faction) {
		
	}
	
	public void removePlayer(int playerId) {
		throw new UnsupportedOperationException();
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
}
