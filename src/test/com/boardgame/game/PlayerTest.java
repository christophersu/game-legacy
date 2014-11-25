package com.boardgame.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
	private Player player;
	
	@Before
	public void initializeTest() {
		player = new Player(new HashSet<>(), new HashSet<>(), 0, 20);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullCards() {
		new Player(null, new HashSet<>(), 0, 20);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullUnits() {
		new Player(new HashSet<>(), null, 0, 20);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNegativeCash() {
		new Player(new HashSet<>(), new HashSet<>(), -1, 20);
	}
	
	@Test
	public void testNullCombatCardElement() {
		Set<AbstractCombatCard> cards = new HashSet<>();
		cards.add(null);
		new Player(cards, new HashSet<>(), 0, 20);
	}
	
	@Test
	public void testNullUnitElement() {
		Set<AbstractUnit> cards = new HashSet<>();
		cards.add(null);
		new Player(new HashSet<>(), cards, 0, 20);
	}
	
	@Test
	public void testCashDoesNotExceedMax() {
		player.putCashInHand(21);
		assertEquals(player.getCashInHand(), 20);
		assertEquals(player.getCashPool(), 0);
	}
	
	@Test
	public void testCashDoesNotGoNegative() {
		player.removeCashFromHand(1);
		assertTrue(player.getCashInHand() >= 0);
		assertEquals(player.getCashPool(), 20);
	}
	
	@Test
	public void testMoveCardToDiscard() {
		throw new UnsupportedOperationException();
	}
	
	@Test
	public void testMoveDiscardedCardToDiscard() {
		throw new UnsupportedOperationException();
	}
	
	@Test
	public void testUseUnit() {
		throw new UnsupportedOperationException();
	}
	
	@Test
	public void testUseUsedUnit() {
		throw new UnsupportedOperationException();
	}
	
	@Test
	public void testAddUnit() {
		throw new UnsupportedOperationException();
	}
}
