package com.boardgame.game;

final class Base {
	private static final int DEFAULT_DEFENSE = 0;
	private static final boolean DEFAULT_IS_DEFENSE_ACTIVE = true;
	
	private final int size;
	private final int defense;
	
	private boolean isDefenseActive;
	
	Base(int size) {
		assert size > 0 : "Non-positive size";
		
		this.size = size;
		this.defense = DEFAULT_DEFENSE;
		this.isDefenseActive = DEFAULT_IS_DEFENSE_ACTIVE;
		
		checkRep();
	}

	Base(Base base, int defense) {
		assert base != null : "Null base";
		
		this.size = base.size;
		this.defense = defense;
		this.isDefenseActive = base.isDefenseActive;
		
		checkRep();
	}
	
	void setIsDefenseActive(boolean active) {
		checkRep();
		this.isDefenseActive = active;
		checkRep();
	}
	
	int getSize() {
		checkRep();
		return size;
	}
	
	int getDefense() {
		checkRep();
		return defense;
	}
	
	boolean getIsDefenseActive() {
		checkRep();
		return isDefenseActive;
	}
	
	void checkRep() {
		assert size > 0 : "Nonpositive size";
		assert defense >= 0 : "Negative defense";
	}
}
