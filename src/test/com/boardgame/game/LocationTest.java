package com.boardgame.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.boardgame.game.Location.Terrain;

public class LocationTest {
	private static Player player;
	private Location location;
	
	@BeforeClass
	public static void initialize() {
		player = new Player(new HashSet<>(), new HashSet<>(), 0, 20);
	}
	
	@Before
	public void initializeTest() {
		location = new Location("location name", Terrain.LAND, null, 1, 2);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testNullName() {
		new Location(null, Terrain.LAND, null, 1, 2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullTerrain() {
		new Location("location name", null, null, 1, 2);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testNegativeSupply() {
		new Location("location name", Terrain.LAND, null, -1, 2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNegativeInvest() {             
		new Location("location name", Terrain.LAND, null, 1, -2);
	}                                           
	
	@Test(expected=IllegalArgumentException.class)
	public void testBaseOnNonLand() {
		new Location("location name", Terrain.SEA, new Base(1), 1, 2);
	}
	                                               
	@Test                                          
	public void testZeroSupply() {                 
		new Location("location name", Terrain.LAND, null, 0, 2);
	}                                              
	                                               
	@Test                                          
	public void testZeroInvest() {                 
		new Location("location name", Terrain.LAND, null, 1, 0);
	}
	
	@Test
	public void testNoUnits() {
		assertFalse(location.hasUnits());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddNullUnit() {
		location.addUnit(null);
	}
	
	@Test
	public void testNullOwner() {
		assertNull(location.getOwner());
	}
	
	@Test(expected=IllegalStateException.class) 
	public void testAddUnitNoOwner() {
		location.addUnit(new InfantryUnit());
	}
	
	@Test
	public void testAddOneUnit() {
		location.changeOwner(player);
		location.addUnit(new InfantryUnit());
		assertTrue(location.hasUnits());
	}
	
	@Test(expected=IllegalStateException.class)
	public void testChangeOwnerWithUnitsLeft() {
		location.changeOwner(player);
		location.addUnit(new InfantryUnit());
		location.changeOwner(null);
	}
	
	@Test
	public void testRemoveNonExistentUnit() {
		location.changeOwner(player);
		assertFalse(location.removeUnit(new InfantryUnit()));
	}
	
	@Test
	public void testRemoveAddedUnit() {
		AbstractUnit unit = new InfantryUnit();
		location.changeOwner(player);
		location.addUnit(unit);
		assertTrue(location.removeUnit(unit));
		assertFalse(location.hasUnits());
		assertNull(location.getOwner());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testPlaceNullToken() {
		location.placeActionToken(null);
	}
	
	@Test
	public void testPlaceToken() {
		assertNull(location.getActionToken());
		location.changeOwner(player);
		AbstractActionToken token = new BlitzToken(true);
		location.placeActionToken(token);
		assertEquals(token, location.getActionToken());
	}
}
