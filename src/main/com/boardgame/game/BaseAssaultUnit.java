package com.boardgame.game;

final class BaseAssaultUnit extends AbstractUnit {
	BaseAssaultUnit() {
		super(2, UnitString.INFANTRY);
	}
	
	@Override
	int getAttackStrength(boolean isAttackingBase) {
		return isAttackingBase ? 4 : 0;
	}
	
	@Override
	int getDefenseStrength() {
		return 0;
	}
}
