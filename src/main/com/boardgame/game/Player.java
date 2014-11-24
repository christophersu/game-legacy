package com.boardgame.game;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

final class Player {
	private final Faction faction;
	private final Set<AbstractCombatCard> combatCardsInHand;
	private final Set<AbstractCombatCard> combatCardsDiscard;
	private final Collection<AbstractUnit> unitsInHand;
	private final int maxCash;
	
	private int cash;
	
	enum Faction {
		RED,
		ORANGE,
		YELLOW,
		GREEN,
		WHITE,
		BLACK
	}
	
	/**
	 * Creates a new player who will control the given faction
	 * @param faction  the faction the player will control, not null
	 * @param combatcards  the combat cards the player starts with, not null, no
	 * null elements
	 * @param units  the units the player starts with, not null, no null 
	 * elements
	 * @param maxCash  the maximum amount of cash a player may have
	 * @param cash  starting cash for the player, nonnegative
	 * @throws IllegalArgumentException if faction is null
	 * @throws IllegalArgumentException if combatCards is null
	 * @throws IllegalArgumentException if units is null
	 * @throws IllegalArgumentException if maxCash is negative
	 * @throws IllegalArgumentException if cash is negative
	 * @throws IllegalArgumentException if cash > maxCash
	 */
	Player(Faction faction, Set<AbstractCombatCard> combatCards, 
			Collection<AbstractUnit> units, int cash, int maxCash) {
		if (faction == null) {
			throw new IllegalArgumentException("Faction was null.");
		}
		
		if (combatCards == null) {
			throw new IllegalArgumentException("Combat cards was null.");
		}
		
		if (units == null) {
			throw new IllegalArgumentException("Units was null.");
		}
		
		if (maxCash < 0) {
			throw new IllegalArgumentException("Max cash was negative.");
		}
		
		if (cash < 0) {
			throw new IllegalArgumentException("Cash was negative.");
		}
		
		if (cash > maxCash) {
			throw new IllegalArgumentException("Cash > maxCash");
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
				
		this.faction = faction;
		this.combatCardsInHand = combatCards;
		this.combatCardsDiscard = new HashSet<AbstractCombatCard>();
		this.unitsInHand = units;
		this.maxCash = maxCash;
		this.cash = cash;
	}
	
	/**
	 * Adds the given amount of cash to this player's total, where cash over
	 * the specified maximum amount of cash is ignored
	 * @param amount  the amount of cash to add
	 */
	public void addCash(int amount) {
		cash += amount;
		
		if (cash > maxCash) {
			cash = maxCash;
		}
	}
	
	/**
	 * Removes the given amount of cash from this player's total such that the
	 * total does not go below 0
	 * @param amount  the amount of cash to remove
	 */
	public void removeCash(int amount) {
		cash -= amount;
		
		if (cash < 0) {
			cash = 0;
		}
	}
	
	/**
	 * Moves the given combat card to the player's discard if the card was in 
	 * the player's hand, and returns whether or not it was in the player's hand
	 * @param card  the combat card to be moved, not null
	 * @throws IllegalArgumentException if card is null
	 * @return whether or not the card was in the player's hand
	 */
	public boolean moveCombatCardToDiscard(AbstractCombatCard card) {
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
	public void putDiscardPileIntoHand() {
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
	public boolean useUnit(AbstractUnit unit) {
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
	public void addUnit(AbstractUnit unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Unit was null.");
		}
		
		boolean result = unitsInHand.add(unit);
		
		assert(result);
	}
	
	/**
	 * Returns this player's faction
	 * @return this player's faction
	 */
	public Faction getFaction() {
		return faction;
	}
	
	/**
	 * Returns the combat cards in the player's hand
	 * @return the combat cards in the player's hand
	 */
	public Set<AbstractCombatCard> getCombatCardsInHand() {
		return combatCardsInHand;
	}
	
	/**
	 * Returns the number of combat cards in the player's hand
	 * @return the number of combat cards in the player's hand
	 */
	public int getNumCombatCardsInHand() {
		return combatCardsInHand.size();
	}
	
	/**
	 * Returns the combat cards in the player's discard
	 * @return the combat cards in the player's discard
	 */
	public Set<AbstractCombatCard> getCombatCardsDiscard() {
		return combatCardsDiscard;
	}
	
	/**
	 * Returns the units in the player's hand
	 * @return the units in the player's hand
	 */
	public Collection<AbstractUnit> getUnitsInHand() {
		return unitsInHand;
	}
	
	/**
	 * Returns the amount of cash this player has
	 * @return the amount of cash this player has
	 */
	public int getCash() {
		return cash;
	}
	
	/**
	 * Returns the maximum amount of cash this player may hold.
	 * @return the maximum amount of cash this player may hold
	 */
	public int getMaxCash() {
		return maxCash;
	}
}
