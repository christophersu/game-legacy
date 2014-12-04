package com.boardgame.game;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

final class Player {
	private final Set<AbstractCombatCard> combatCardsInHand;
	private final Set<AbstractCombatCard> combatCardsDiscard;
	private final Collection<AbstractUnit> unitsInHand;
	
	private int cashInHand;
	private int cashPool;
	
	Player(Set<AbstractCombatCard> combatCards, Collection<AbstractUnit> units, 
			int cashPool, int cashInHand) {
		this.combatCardsInHand = new HashSet<>(combatCards);
		this.combatCardsDiscard = new HashSet<>();
		this.unitsInHand = new HashSet<>(units);
		this.cashInHand = cashInHand;
		this.cashPool = cashPool;
		
		checkRep();
	}
	
	void moveCashFromPoolToHand(int amount) {
		checkRep();
		assert amount >= 0 : "Negative amount";
		
		int initialTotal = cashInHand + cashPool;
		
		if (cashPool >= amount) {
			cashInHand += amount;
			cashPool -= amount;
		}
		else {
			cashInHand -= cashPool;
			cashPool = 0;
		}
		
		int finalTotal = cashInHand + cashPool;
		
		assert initialTotal == finalTotal : "Lost money";

		checkRep();
	}
	
	void moveCashFromHandToPool(int amount) {
		checkRep();
		assert amount >= 0 : "Negative amount";
		
		int initialTotal = cashInHand + cashPool;
		
		if (cashInHand >= amount) {
			cashInHand -= amount;
			cashPool += amount;
		}
		else {
			cashPool += cashInHand;
			cashInHand = 0;
		}
		
		int finalTotal = cashInHand + cashPool;
		
		assert initialTotal == finalTotal : "Lost money";

		checkRep();
	}
	
	boolean removeCashFromHand(int amount) {
		checkRep();
		assert amount >= 0 : "Negative amount";
		
		boolean result = false;
		
		if (cashInHand >= amount) {
			cashInHand -= amount;
			result = true;
		}
		
		checkRep();
		return result;
	}
	
	void addCashToPool(int amount) {
		checkRep();
		assert amount >= 0 : "Negative amount";
		
		cashPool += amount;
		
		checkRep();
	}
	
	boolean moveCombatCardToDiscard(AbstractCombatCard card) {
		checkRep();
		assert card != null : "Null combat card.";
		
		boolean removeResult = combatCardsInHand.remove(card);
		
		if (removeResult) {
			boolean addResult = combatCardsDiscard.add(card);
			assert(addResult);
		}
		
		checkRep();
		return removeResult;
	}
	
	void putDiscardPileIntoHand() {
		checkRep();
		
		combatCardsInHand.addAll(combatCardsDiscard);
		combatCardsDiscard.clear();
		
		checkRep();
	}
	
	boolean removeUnit(AbstractUnit unit) {
		checkRep();
		assert unit != null : "Null unit";
		
		boolean result = unitsInHand.remove(unit);
		
		checkRep();
		return result;
	}
	
	void addUnit(AbstractUnit unit) {
		checkRep();
		assert unit != null : "Null unit";
		
		boolean result = unitsInHand.add(unit);
		
		assert(result);
		checkRep();
	}
	
	Set<AbstractCombatCard> getCombatCardsInHand() {
		checkRep();
		return Collections.unmodifiableSet(combatCardsInHand);
	}
	
	int getNumCombatCardsInHand() {
		checkRep();
		return combatCardsInHand.size();
	}
	
	Set<AbstractCombatCard> getCombatCardsDiscard() {
		checkRep();
		return Collections.unmodifiableSet(combatCardsDiscard);
	}
	
	Collection<AbstractUnit> getUnitsInHand() {
		checkRep();
		return Collections.unmodifiableCollection(unitsInHand);
	}
	
	int getCashInHand() {
		checkRep();
		return cashInHand;
	}
	
	int getCashPool() {
		checkRep();
		return cashPool;
	}
	
	private void checkRep() {
		assert combatCardsInHand != null : "Null combat cards in hand";
		assert combatCardsDiscard != null : "Null combat cards discard";
		assert unitsInHand != null : "Null units in hand";
		assert cashInHand >= 0 : "Negative cash in hand";
		assert cashPool >= 0 : "Negative cash pool";
		
		for (AbstractCombatCard card : combatCardsInHand) {
			assert card != null : "Null combat card";
		}
		
		for (AbstractCombatCard card : combatCardsDiscard) {
			assert card != null : "Null combat card";
		}
		
		for (AbstractUnit unit : unitsInHand) {
			assert unit != null : "Null unit";
		}
	}
}
