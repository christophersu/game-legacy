package com.boardgame.game;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

final class Player {
	private final Set<AbstractCombatCard> combatCardsInHand;
	private final Set<AbstractCombatCard> combatCardsDiscard;
	private final Collection<AbstractUnit> unitsInHand;
	
	private final Set<AbstractActionToken> tokens;
	
	private int numSpecialTokensUsed;
	
	private int cashInHand;
	private int cashPool;
	
	Player(Set<AbstractCombatCard> combatCardsInHand, 
			Set<AbstractCombatCard> combatCardsDiscard, 
			Collection<AbstractUnit> unitsInHand, 
			int cashPool, int cashInHand) {
		this.combatCardsInHand = new HashSet<>(combatCardsInHand);
		this.combatCardsDiscard = new HashSet<>(combatCardsDiscard);
		this.unitsInHand = new HashSet<>(unitsInHand);
		
		this.tokens = new HashSet<>();
		this.numSpecialTokensUsed = 0;
		
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
		
		assert result;
		checkRep();
	}
	
	boolean hasToken(AbstractActionToken token) {
		checkRep();
		return tokens.contains(token);
	}
	
	void resetTokens(Set<AbstractActionToken> otherTokens) {
		checkRep();
		
		tokens.clear();
		tokens.addAll(otherTokens);
		numSpecialTokensUsed = 0;
		
		checkRep();
	}
	
	boolean useToken(AbstractActionToken token) {
		checkRep();
		assert token != null;
		
		boolean result = tokens.remove(token);

		if (token.isSpecial) {
			numSpecialTokensUsed++;
		}
		
		checkRep();
		
		return result;
	}
	
	void unUseToken(AbstractActionToken token) {
		checkRep();
		assert token != null;
		
		boolean result = tokens.add(token);
		assert result;
		
		if (token.isSpecial) {
			numSpecialTokensUsed--;
		}
		
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
	
	int getNumSpecialTokensUsed() {
		return numSpecialTokensUsed;
	}
	
	private void checkRep() {
		assert combatCardsInHand != null : "Null combat cards in hand";
		assert combatCardsDiscard != null : "Null combat cards discard";
		assert unitsInHand != null : "Null units in hand";
		
		assert tokens != null : "Null tokens";
		assert numSpecialTokensUsed >= 0 : "Negative special tokens used";
		
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
		
		for (AbstractActionToken token : tokens) {
			assert token != null : "Null token";
		}
	}
}
