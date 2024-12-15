package application;

public class Graph_STUDENT_Test import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Graph_Test_Simple {

    Graph townGraph;

    Town townA;
    Town townB;
    Town townC;
    Town townD;
    Town townE;

    @Before
    public void setUp() throws Exception {
        townGraph = new Graph();

        townA = new Town("Alpha");
        townB = new Town("Beta");
        townC = new Town("Gamma");
        townD = new Town("Delta");
        townE = new Town("Epsilon");

        townGraph.addVertex(townA);
        townGraph.addVertex(townB);
        townGraph.addVertex(townC);
        townGraph.addVertex(townD);
        townGraph.addVertex(townE);

        townGraph.addEdge(townA, townB, 4, "Road AB");
        townGraph.addEdge(townB, townC, 7, "Road BC");
        townGraph.addEdge(townC, townD, 5, "Road CD");
        townGraph.addEdge(townD, townE, 6, "Road DE");
        townGraph.addEdge(townA, townC, 10, "Road AC");
    }

    @After
    public void tearDown() throws Exception {
        townA = townB = townC = townD = townE = null;
        townGraph = null;
    }

    @Test
    public void testGetEdge() {
        assertEquals("Road AB", townGraph.getEdge(townA, townB).getName());
        assertEquals("Road CD", townGraph.getEdge(townC, townD).getName());
    }

    @Test
    public void testAddEdge() {
        townGraph.addEdge(townB, townE, 12, "Road BE");
        assertEquals("Road BE", townGraph.getEdge(townB, townE).getName());
    }

    @Test
    public void testAddVertex() {
        Town newTown = new Town("Zeta");
        townGraph.addVertex(newTown);
        assertTrue(townGraph.containsVertex(newTown));
    }

    @Test
    public void testContainsEdge() {
        assertTrue(townGraph.containsEdge(townA, townB));
        assertFalse(townGraph.containsEdge(townA, townE));
    }

    @Test
    public void testContainsVertex() {
        assertTrue(townGraph.containsVertex(townA));
        assertFalse(townGraph.containsVertex(new Town("Theta")));
    }

    @Test
    public void testEdgeSet() {
        Set<String> roads = new HashSet<>();
        for (Road road : townGraph.edgeSet()) {
            roads.add(road.getName());
        }
        assertTrue(roads.contains("Road AB"));
        assertTrue(roads.contains("Road DE"));
    }

    @Test
    public void testEdgesOf() {
        Set<String> roads = new HashSet<>();
        for (Road road : townGraph.edgesOf(townC)) {
            roads.add(road.getName());
        }
        assertTrue(roads.contains("Road BC"));
        assertTrue(roads.contains("Road CD"));
    }

    @Test
    public void testRemoveEdge() {
        townGraph.removeEdge(townA, townB, 4, "Road AB");
        assertFalse(townGraph.containsEdge(townA, townB));
    }

    @Test
    public void testRemoveVertex() {
        townGraph.removeVertex(townD);
        assertFalse(townGraph.containsVertex(townD));
    }

    @Test
    public void testVertexSet() {
        Set<Town> towns = townGraph.vertexSet();
        assertTrue(towns.contains(townA));
        assertTrue(towns.contains(townE));
    }

    @Test
    public void testShortestPath() {
        ArrayList<String> path = townGraph.shortestPath(townA, townE);
        assertEquals("Alpha via Road AB to Beta 4 mi", path.get(0));
        assertEquals("Beta via Road BC to Gamma 7 mi", path.get(1));
        assertEquals("Gamma via Road CD to Delta 5 mi", path.get(2));
        assertEquals("Delta via Road DE to Epsilon 6 mi", path.get(3));
    }
}
