package com.boardgame.game;

import java.util.Collection;

final class Combat {
	private final CombatStrategy attackStrategy;
	private final CombatStrategy defenseStrategy;
	private final Location source;
	private final Location target;
	
	private Phase phase;
	
	enum Phase {
		RESOLUTION(null),
		BONUS(RESOLUTION),
		CARD(BONUS),
		ASSIST(CARD);
		
		private Phase nextPhase;
		
		Phase(Phase next) {
			this.nextPhase = next;
		}
	}
	
	Combat(Collection<AbstractUnit> attackingUnits, Location source, 
			Location target) {
		assert source.getActionToken() != null;
		
		int attackingTokenBonus = source.getActionToken().getCombatBonus();
		int defendingTokenBonus = 0;
		
		if (target.getActionToken() != null) {
			defendingTokenBonus += target.getActionToken().getCombatBonus();
		}
		
		Base targetBase = target.getBaseStrength();
		
		if (targetBase != null && targetBase.getIsDefenseActive()) {
			defendingTokenBonus += target.getBaseStrength().getDefense();
		}
		
		attackStrategy = new CombatStrategy(attackingTokenBonus);
		defenseStrategy = new CombatStrategy(defendingTokenBonus);
		this.source = source;
		this.target = target;
		
		this.phase = Phase.ASSIST;
	}
	
	Phase getPhase() {
		return phase;
	}
	
	void assist(int assistStrength, boolean isAssistingAttacker) {
		CombatStrategy strat;
		
		if (isAssistingAttacker) {
			strat = attackStrategy;
		}
		else {
			strat = defenseStrategy;
		}
		
		strat.assistStrength += assistStrength;
	}
	
	void nextPhase() {
		phase = phase.nextPhase;
	}
	
	private class CombatStrategy {
		private int tokenBonus;
		private int strength;
		private int assistStrength;
		private int combatBonus;
		private AbstractCombatCard combatCard;
		
		CombatStrategy(int tokenBonus) {
			this.tokenBonus = tokenBonus;
			this.strength = 0;
			this.assistStrength = 0;
			this.combatBonus = 0;
			this.combatCard = null;
		}
	}
}
