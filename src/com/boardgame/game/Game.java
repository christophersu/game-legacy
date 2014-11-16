package com.boardgame.game;

public class Game {
	public Game() {
		throw new UnsupportedOperationException();
	}
	
	public void addUser(User user) {
		throw new UnsupportedOperationException();
	}
	
	public void startGame() {
		throw new UnsupportedOperationException();
	}
	
	public void placeToken(User user, Token token, Location location) {
		throw new UnsupportedOperationException();
	}
	
	public void removeToken(User user, Location location) {
		throw new UnsupportedOperationException();
	}
	
	public void flipTokens() {
		throw new UnsupportedOperationException();
	}
	
	public void switchToken(User user, Location location, Token nextToken) {
		throw new UnsupportedOperationException();
	}
	
	public TokenChoices getTokenChoices() {
		throw new UnsupportedOperationException();
	}
	
	public Set<Location> getValidLocationTargets(User user, Token token) {
		throw new UnsupportedOperationException();
	}
	
	public void useToken(User user, Token token, Location target) {
		throw new UnsupportedOperationException();
	}
	
	public void attackWith(Set<Unit> units) {
		throw new UnsupportedOperationException();
	}
	
	public void support(User supportingUser, Token token, User supportedUser) {
		throw new UnsupportedOperationException();
	}
	
	public void useCombatCard(User user, CombatCard combatCard) {
		throw new UnsupportedOperationException();
	}
	
	public void useCombatBonus(User user) {
		throw new UnsupportedOperationException();
	}
	
	public void flipCombatCards() {
		throw new UnsupportedOperationException();
	}
	
	//1 unit at a time?
	public void retreat(User defeatedUser, Set<Unit> units, Location location) {
		throw new UnsupportedOperationException();
	}
	
	public void doneRetreating() {
		throw new UnsupportedOperationException();
	}
	
	//1 unit at a time?
	public void spawnUnits(User user, Location location, Set<Unit> units) {
		throw new UnsupportedOperationException();
	}
	
	public void bid(User user, int bid) {
		throw new UnsupportedOperationException();
	}
	
	public void revealBids() {
		throw new UnsupportedOperationException();
	}

	public GameState getGameState() {
		throw new UnsupportedOperationException();
	}
}
