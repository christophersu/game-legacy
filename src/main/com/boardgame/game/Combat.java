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
		assert attackingUnits != null;
		assert source != null;
		assert target != null;
		
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
	
	Location getSource() {
		return source;
	}
	
	Location getTarget() {
		return target;
	}
	
	Faction getAttacker() {
		return source.getOwner();
	}
	
	Faction getDefender() {
		return target.getOwner();
	}
	
	void assist(int assistStrength, boolean isAssistingAttacker) {
		getStrategy(isAssistingAttacker).assistStrength += assistStrength;
	}
	
	void useCombatCard(AbstractCombatCard combatCard, boolean isAttacker) {
		assert combatCard != null;
		
		getStrategy(isAttacker).combatCard = combatCard;
		
		if (attackStrategy.combatCard != null && 
				defenseStrategy.combatCard != null) {
			nextPhase();
		}
	}
	
	void useCombatBonus(boolean onAttacker) {
		getStrategy(onAttacker).combatBonus = 1;
	}
	
	void nextPhase() {
		phase = phase.nextPhase;
	}
	
	private CombatStrategy getStrategy(boolean isAttacker) {
		if (isAttacker) {
			return attackStrategy;
		}
		else {
			return defenseStrategy;
		}
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
