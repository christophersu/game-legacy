package com.boardgame.game;

final class InternalGame {
	InternalGame(int numPlayers) {
		throw new UnsupportedOperationException();
	}
	
	void startGame() {
		throw new UnsupportedOperationException();
	}
	
	void addPlayer(int player, int faction) {
		throw new UnsupportedOperationException();
	}
	
	void removePlayer(int player) {
		throw new UnsupportedOperationException();
	}
	
	void placeToken(Player player, Token token, Location location) {
		throw new UnsupportedOperationException();
	}
	
	void removeToken(Player player, Location location) {
		throw new UnsupportedOperationException();
	}
	
	void switchToken(Player player, Location location, Token nextToken) {
		throw new UnsupportedOperationException();
	}
	
	TokenChoices getTokenChoices() {
		throw new UnsupportedOperationException();
	}
	
	Set<Location> getValidLocationTargets(User user, Token token) {
		throw new UnsupportedOperationException();
	}
	
	void chooseUnitforAction(Unit unit) {
		throw new UnsupportedOperationException();
	}
	
	void useToken(Player player, Token token, Location tokenLocation, Location target) {
		throw new UnsupportedOperationException();
	}
	
	void resetToken(Player player, Token token) {
		throw new UnsupportedOperationException();
	}
	
	void support(Player player, Location supportingLocation, Player player) {
		throw new UnsupportedOperationException();
	}
	
	void useCombatCard(Player player, CombatCard combatCard) {
		throw new UnsupportedOperationException();
	}
	
	void useCombatBonus(Player player) {
		throw new UnsupportedOperationException();
	}
	
	void retreat(Player player defeatedPlayer, Unit retreatingUnit, Location target) {
		throw new UnsupportedOperationException();
	}
	
	void spawnUnit(Player player, Location location, Unit unit) {
		throw new UnsupportedOperationException();
	}
	
	void bid(Player player, int bid) {
		throw new UnsupportedOperationException();
	}
	
	void breakTie(Player player, User winner) {
		throw new UnsupportedOperationException();
	}
	
	void setUserReady(Player player, boolean isReady) {
		throw new UnsupportedOperationException();
	}

	Snapshot getSnapshotForUser(Player player) {
		throw new UnsupportedOperationException();
	}
}
