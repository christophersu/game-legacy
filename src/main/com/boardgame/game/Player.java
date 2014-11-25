package com.boardgame.game;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a player in the game.
 *
 */
final class Player {
	private final Set<AbstractCombatCard> combatCardsInHand;
	private final Set<AbstractCombatCard> combatCardsDiscard;
	private final Collection<AbstractUnit> unitsInHand;
	
	private int cashInHand;
	private int cashPool;
	
	/**
	 * Creates a new player who will control the given faction
	 * @param combatcards  the combat cards the player starts with, not null, no
	 * null elements
	 * @param units  the units the player starts with, not null, no null 
	 * elements
	 * @param cashInHand  the amount of cash in the player's hand, nonnegative
	 * @param cashPool  the amount of cash not in the player's hand, nonnegative
	 * @throws IllegalArgumentException if combatCards is null
	 * @throws IllegalArgumentException if units is null
	 * @throws IllegalArgumentException if cashInHand < 0
	 * @throws IllegalArgumentException if cashPool < 0
	 */
	Player(Set<AbstractCombatCard> combatCards, Collection<AbstractUnit> units, 
			int cashPool, int cashInHand) {
		if (combatCards == null) {
			throw new IllegalArgumentException("Combat cards was null.");
		}
		
		if (units == null) {
			throw new IllegalArgumentException("Units was null.");
		}
		
		if (cashInHand < 0) {
			throw new IllegalArgumentException("Cash in hand was negative.");
		}
		
		if (cashPool < 0) {
			throw new IllegalArgumentException("Cash pool was negative.");
		}
		
		for (AbstractCombatCard card : combatCards) {
			if (card == null) {
				throw new IllegalArgumentException("A combat card was null.");
			}
		}
		
		for (AbstractUnit unit : units) {
			if (unit == null) {
				throw new IllegalArgumentException("A unit was null.");
			}
		}
				
		this.combatCardsInHand = combatCards;
		this.combatCardsDiscard = new HashSet<AbstractCombatCard>();
		this.unitsInHand = units;
		this.cashInHand = cashInHand;
		this.cashPool = cashPool;
	}
	
	/**
	 * Puts the given amount of cash in the player's hand, limited by the amount
	 * in his cash pool.
	 * @param amount  the amount to be transferred, nonnegative
	 * @throws IllegalArgumentException if amount is negative
	 */
	void putCashInHand(int amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Amount is negative.");
		}
		
		if (cashPool >= amount) {
			cashInHand += amount;
			cashPool -= amount;
		}
		else {
			cashInHand -= cashPool;
			cashPool = 0;
		}
	}
	
	/**
	 * Removes the given amount of cash from the player's hand, such that the 
	 * amount of cash in the player's hand isn't negative
	 * @param amount  the amount to be transferred, nonnegative
	 * @throws IllegalArgumentException if amount is negative
	 */
	void removeCashFromHand(int amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Amount is negative.");
		}
		
		if (cashInHand >= amount) {
			cashInHand -= amount;
			cashPool += amount;
		}
		else {
			cashPool += cashInHand;
			cashInHand = 0;
		}
	}
	
	/**
	 * Moves the given combat card to the player's discard if the card was in 
	 * the player's hand, and returns whether or not it was in the player's hand
	 * @param card  the combat card to be moved, not null
	 * @throws IllegalArgumentException if card is null
	 * @return whether or not the card was in the player's hand
	 */
	boolean moveCombatCardToDiscard(AbstractCombatCard card) {
		if (card == null) {
			throw new IllegalArgumentException("Card was null.");
		}
		
		boolean removeResult = combatCardsInHand.remove(card);
		
		if (removeResult) {
			boolean addResult = combatCardsDiscard.add(card);
			assert(addResult);
		}
		
		return removeResult;
	}
	
	/**
	 * Moves the contents of the player's discard pile into his hand
	 */
	void putDiscardPileIntoHand() {
		combatCardsInHand.addAll(combatCardsDiscard);
		combatCardsDiscard.clear();
	}
	
	/**
	 * Uses the given unit by removing it from the player's hand, and returns
	 * whether or not the player actually had the given unit in his hand
	 * @param unit  the unit to be used, not null
	 * @throws IllegalArgumentException if unit is null
	 * @return whether or not the player had the given unit in his hand
	 */
	boolean useUnit(AbstractUnit unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Unit was null.");
		}
		
		return unitsInHand.remove(unit);
	}
	
	/**
	 * Adds the given unit to the player's hand
	 * @param unit  the unit to be added to the player's hand, not null
	 * @throws IllegalArgumentException if unit is null
	 */
	void addUnit(AbstractUnit unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Unit was null.");
		}
		
		boolean result = unitsInHand.add(unit);
		
		assert(result);
	}
	
	/**
	 * Returns the combat cards in the player's hand
	 * @return the combat cards in the player's hand, not null, no null elements
	 */
	Set<AbstractCombatCard> getCombatCardsInHand() {
		return combatCardsInHand;
	}
	
	/**
	 * Returns the number of combat cards in the player's hand
	 * @return the number of combat cards in the player's hand, nonnegative
	 */
	int getNumCombatCardsInHand() {
		return combatCardsInHand.size();
	}
	
	/**
	 * Returns the combat cards in the player's discard
	 * @return the combat cards in the player's discard, not null, no null 
	 * elements
	 */
	Set<AbstractCombatCard> getCombatCardsDiscard() {
		return combatCardsDiscard;
	}
	
	/**
	 * Returns the units in the player's hand
	 * @return the units in the player's hand, not null, no null elements
	 */
	Collection<AbstractUnit> getUnitsInHand() {
		return unitsInHand;
	}
	
	/**
	 * Returns the amount of cash in the player's hand
	 * @return the amount of cash in the player's hand, nonnegative, not greater than
	 * max cash
	 */
	int getCashInHand() {
		return cashInHand;
	}
	
	/**
	 * Returns the amount of cash in the player's cash pool
	 * @return the amount of cash in the player's cash pool, nonnegative
	 */
	int getCashPool() {
		return cashPool;
	}
}
