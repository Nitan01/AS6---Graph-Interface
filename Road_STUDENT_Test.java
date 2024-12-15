package application;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Road_STUDENT_Test {

    Town townA;
    Town townB;
    Town townC;
    Town townD;

    Road road1;
    Road road2;
    Road road3;

    @Before
    public void setUp() throws Exception {
        townA = new Town("Town A");
        townB = new Town("Town B");
        townC = new Town("Town C");
        townD = new Town("Town D");

        road1 = new Road(townA, townB, 5, "Road AB");
        road2 = new Road(townA, townC, 10, "Road AC");
        road3 = new Road(townB, townD, 8, "Road BD");
    }

    @After
    public void tearDown() throws Exception {
        townA = townB = townC = townD = null;
        road1 = road2 = road3 = null;
    }

    @Test
    public void testContains() {
        assertTrue(road1.contains(townA));
        assertTrue(road1.contains(townB));
        assertFalse(road1.contains(townC));
    }

    @Test
    public void testEquals() {
        Road roadDuplicate = new Road(townA, townB, 5, "Road AB");
        assertTrue(road1.equals(roadDuplicate));

        Road roadDifferent = new Road(townA, townD, 15, "Road AD");
        assertFalse(road1.equals(roadDifferent));
    }

    @Test
    public void testGetSource() {
        assertEquals("Town A", road1.getSource().getName());
        assertEquals("Town B", road3.getSource().getName());
    }

    @Test
    public void testGetDestination() {
        assertEquals("Town B", road1.getDestination().getName());
        assertEquals("Town D", road3.getDestination().getName());
    }

    @Test
    public void testGetName() {
        assertEquals("Road AB", road1.getName());
        assertEquals("Road AC", road2.getName());
    }

    @Test
    public void testGetWeight() {
        assertEquals(5, road1.getWeight());
        assertEquals(10, road2.getWeight());
        assertEquals(8, road3.getWeight());
    }

    @Test
    public void testCompareTo() {
        assertTrue(road1.compareTo(road2) < 0); // Road1 weight < Road2 weight
        assertTrue(road3.compareTo(road1) > 0); // Road3 weight > Road1 weight
        assertEquals(0, road1.compareTo(new Road(townA, townB, 5, "Duplicate Road AB")));
    }
}