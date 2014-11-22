package com.boardgame.game;

import java.util.Set;

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
	
	void placeToken(Player player, AbstractActionToken token, AbstractLocation location) {
		throw new UnsupportedOperationException();
	}
	
	void removeToken(Player player, AbstractLocation location) {
		throw new UnsupportedOperationException();
	}
	
	void switchToken(Player player, AbstractLocation location, AbstractActionToken nextToken) {
		throw new UnsupportedOperationException();
	}
	
	int getCurrentPlayer() {
		throw new UnsupportedOperationException();
	}
	
	Set<Integer> getTokenChoices(Player player) {
		throw new UnsupportedOperationException();
	}
	
	Set<AbstractLocation> getValidLocationTargets(Player player, AbstractActionToken token) {
		throw new UnsupportedOperationException();
	}
	
	void chooseUnitforAction(AbstractUnit unit) {
		throw new UnsupportedOperationException();
	}
	
	void useToken(Player player, AbstractActionToken token, AbstractLocation tokenLocation, AbstractLocation target) {
		throw new UnsupportedOperationException();
	}
	
	void resetToken(Player player, AbstractActionToken token) {
		throw new UnsupportedOperationException();
	}

	public void support(Player supportingPlayer, AbstractLocation supportingLocation, Player supportedPlayer) {
		throw new UnsupportedOperationException();
	}
	
	void useCombatCard(Player player, AbstractCombatCard combatCard) {
		throw new UnsupportedOperationException();
	}
	
	void useCombatBonus(Player player) {
		throw new UnsupportedOperationException();
	}
	
	void retreat(Player defeatedPlayer, AbstractUnit retreatingUnit, AbstractLocation target) {
		throw new UnsupportedOperationException();
	}
	
	void spawnUnit(Player player, AbstractLocation location, AbstractUnit unit) {
		throw new UnsupportedOperationException();
	}
	
	void bid(Player player, int bid) {
		throw new UnsupportedOperationException();
	}
	
	void breakTie(Player loser, Player winner) {
		throw new UnsupportedOperationException();
	}
	
	void setUserReady(Player player, boolean isReady) {
		throw new UnsupportedOperationException();
	}
}
