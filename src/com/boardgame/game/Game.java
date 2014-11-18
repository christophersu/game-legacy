package com.boardgame.game;

public class Game {
	public Game(int numPlayers) {
		throw new UnsupportedOperationException();
	}
	
	public void startGame() {
		throw new UnsupportedOperationException();
	}
	
	public void placeToken(Player player, Token token, Location location) {
		throw new UnsupportedOperationException();
	}
	
	public void removeToken(Player player, Location location) {
		throw new UnsupportedOperationException();
	}
	
	public void switchToken(Player player, Location location, Token nextToken) {
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
	
	public void useToken(Player player, Token token, Location tokenLocation, Location target) {
		throw new UnsupportedOperationException();
	}
	
	public void resetToken(Player player, Token token) {
		throw new UnsupportedOperationException();
	}
	
	public void support(Player player, Location supportingLocation, Player player) {
		throw new UnsupportedOperationException();
	}
	
	public void useCombatCard(Player player, CombatCard combatCard) {
		throw new UnsupportedOperationException();
	}
	
	public void useCombatBonus(Player player) {
		throw new UnsupportedOperationException();
	}
	
	public void retreat(Player player defeatedPlayer, Unit retreatingUnit, Location target) {
		throw new UnsupportedOperationException();
	}
	
	public void spawnUnit(Player player, Location location, Unit unit) {
		throw new UnsupportedOperationException();
	}
	
	public void bid(Player player, int bid) {
		throw new UnsupportedOperationException();
	}
	
	public void breakTie(Player player, User winner) {
		throw new UnsupportedOperationException();
	}
	
	public void setUserReady(Player player, boolean isReady) {
		throw new UnsupportedOperationException();
	}

	public Snapshot getSnapshotForUser(Player player) {
		throw new UnsupportedOperationException();
	}
}
