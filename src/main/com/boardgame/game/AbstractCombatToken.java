package com.boardgame.game;

abstract class AbstractCombatToken extends AbstractActionToken {
	private final int strength;
	
	AbstractCombatToken(boolean isSpecial, int strength, int priority) {
		super(isSpecial, priority);
		
		this.strength = strength;
	}
	
	int getStrength() {
		return strength;
	}
	
	@Override
	int getCombatBonus() {
		return getStrength();
	}
}
