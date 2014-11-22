package com.boardgame.game;

import java.util.Set;

public final class Game {
	public Game(int numPlayers) {
		throw new UnsupportedOperationException();
	}
	
	public void addPlayer(int player, int faction) {
		throw new UnsupportedOperationException();
	}
	
	public void removePlayer(int player) {
		throw new UnsupportedOperationException();
	}
	
	public void startGame() {
		throw new UnsupportedOperationException();
	}
	
	public void placeToken(int player, int token, int location) {
		throw new UnsupportedOperationException();
	}
	
	public void removeToken(int player, int location) {
		throw new UnsupportedOperationException();
	}
	
	public void switchToken(int player, int location, int nextToken) {
		throw new UnsupportedOperationException();
	}
	
	public int getCurrentPlayer() {
		throw new UnsupportedOperationException();
	}
	
	public Set<Integer> getTokenChoices(int player) {
		throw new UnsupportedOperationException();
	}
	
	public Set<Integer> getValidLocationTargets(int user, int token) {
		throw new UnsupportedOperationException();
	}
	
	public void chooseUnitforAction(int unit) {
		throw new UnsupportedOperationException();
	}
	
	public void useToken(int player, int token, int tokenLocation, int target) {
		throw new UnsupportedOperationException();
	}
	
	public void resetToken(int player, int token) {
		throw new UnsupportedOperationException();
	}
	
	public void support(int supportingPlayer, int supportingLocation, int supportedPlayer) {
		throw new UnsupportedOperationException();
	}
	
	public void useCombatCard(int player, int combatCard) {
		throw new UnsupportedOperationException();
	}
	
	public void useCombatBonus(int player) {
		throw new UnsupportedOperationException();
	}
	
	public void retreat(int defeatedPlayer, int retreatingUnit, int target) {
		throw new UnsupportedOperationException();
	}
	
	public void spawnUnit(int player, int location, int unit) {
		throw new UnsupportedOperationException();
	}
	
	public void bid(int player, int bid) {
		throw new UnsupportedOperationException();
	}
	
	public void breakTie(int player, int winner) {
		throw new UnsupportedOperationException();
	}
	
	public void setUserReady(int player, boolean isReady) {
		throw new UnsupportedOperationException();
	}
	
	public int getState() {
		throw new UnsupportedOperationException();
	}

	public String getSnapshotForUser(int player) {
		throw new UnsupportedOperationException();
	}
}
