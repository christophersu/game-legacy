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
	
	public void switchToken(User user, Location location, Token nextToken) {
		throw new UnsupportedOperationException();
	}
	
	public TokenChoices getTokenChoices() {
		throw new UnsupportedOperationException();
	}
	
	public Set<Location> getValidLocationTargets(User user, Token token) {
		throw new UnsupportedOperationException();
	}
	
	public void chooseUnitforAction(Unit unit) {
		throw new UnsupportedOperationException();
	}
	
	public void useToken(User user, Token token, Location tokenLocation, Location target) {
		throw new UnsupportedOperationException();
	}
	
	public void resetToken(User user, Token token) {
		throw new UnsupportedOperationException();
	}
	
	public void support(User supportingUser, Location supportingLocation, User supportedUser) {
		throw new UnsupportedOperationException();
	}
	
	public void useCombatCard(User user, CombatCard combatCard) {
		throw new UnsupportedOperationException();
	}
	
	public void useCombatBonus(User user) {
		throw new UnsupportedOperationException();
	}
	
	public void retreat(User defeatedUser, Unit retreatingUnit, Location target) {
		throw new UnsupportedOperationException();
	}
	
	public void spawnUnit(User user, Location location, Unit unit) {
		throw new UnsupportedOperationException();
	}
	
	public void bid(User user, int bid) {
		throw new UnsupportedOperationException();
	}
	
	public void breakTie(User loser, User winner) {
		throw new UnsupportedOperationException();
	}
	
	public void setUserReady(User user, boolean isReady) {
		throw new UnsupportedOperationException();
	}

	public Snapshot getSnapshotForUser(User user) {
		throw new UnsupportedOperationException();
	}
}
