package com.boardgame.game;

abstract class AbstractUnit {
	private int cost;
	
	private UnitString unitString;
	
	enum UnitString {
		INFANTRY,
		ADVANCED,
		SHIP,
		BASE_ASSAULT
	}
	
	AbstractUnit(int cost, UnitString unitString) {
		this.cost = cost;
		this.unitString = unitString;
	}
	
	int getAttackStrength(boolean isAttackingBase) {
		return cost;
	}
	
	int getDefenseStrength() {
		return cost;
	}
	
	int getCost() {
		return cost;
	}
	
	@Override
	public String toString() {
		return unitString.toString();
	}
}
