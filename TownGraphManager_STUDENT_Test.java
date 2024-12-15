package application;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TownGraphManager_STUDENT_Test {

    TownGraphManager TGM;

    String town1;
    String town2;
    String town3;
    String town4;
    String town5;
    String town6;
    String town7;
    String town8;

    @Before
    public void setUp() throws Exception {
        TGM = new TownGraphManager();

        town1 = "Gaithersburg";
        town2 = "Rockville";
        town3 = "Silver Spring";
        town4 = "Clinton";
        town5 = "Waldorf";
        town6 = "Ashton";
        town7 = "White Oak";
        town8 = "Burtonsville";

        TGM.addTown(town1);
        TGM.addTown(town2);
        TGM.addTown(town3);
        TGM.addTown(town4);
        TGM.addTown(town5);
        TGM.addTown(town6);
        TGM.addTown(town7);
        TGM.addTown(town8);

        TGM.addRoad(town1, town2, 3, "Shady Grove Road");
        TGM.addRoad(town1, town3, 8, "ICC 200");
        TGM.addRoad(town2, town3, 5, "Randolph Road");
        TGM.addRoad(town3, town4, 10, "I-495");
        TGM.addRoad(town4, town5, 3, "Some road");
        TGM.addRoad(town3, town8, 2, "Spencerville Road");
        TGM.addRoad(town3, town7, 3, "New Hampshire Ave N");
        TGM.addRoad(town3, town6, 1, "New Hampshire Ave S");
        TGM.addRoad(town1, town8, 16, "ICC 201");
    }

    @After
    public void tearDown() throws Exception {
        town1 = town2 = town3 = town4 = town5 = town6 = town7 = town8 = null;
        TGM = null;
    }

    @Test
    public void testAddRoad() {
        TGM.addRoad(town4, town1, 200, "DNE");
        assertEquals("DNE", TGM.getRoad(town4, town1));

        TGM.addRoad(town1, town5, 100, "Random");
        assertEquals("Random", TGM.getRoad(town1, town5));

        TGM.addRoad(town2, town4, 30, "Wow");
        assertEquals("Wow", TGM.getRoad(town2, town4));
    }

    @Test
    public void testGetRoad() {
        assertEquals("Shady Grove Road", TGM.getRoad(town1, town2));
        assertEquals("ICC 200", TGM.getRoad(town1, town3));
        assertEquals("ICC 201", TGM.getRoad(town1, town8));
    }

    @Test
    public void testAddTown() {
        String newTown = "New Town";
        TGM.addTown(newTown);
        assertTrue(TGM.containsTown(newTown));

        String nonExistentTown = "Fake Town";
        assertFalse(TGM.containsTown(nonExistentTown));
    }

    @Test
    public void testContainsTown() {
        assertTrue(TGM.containsTown(town1));
        assertTrue(TGM.containsTown(town4));
        assertFalse(TGM.containsTown("Nonexistent Town"));
    }

    @Test
    public void testContainsRoadConnection() {
        assertTrue(TGM.containsRoadConnection(town1, town2));
        assertTrue(TGM.containsRoadConnection(town3, town4));
        assertFalse(TGM.containsRoadConnection(town1, town5));
    }

    @Test
    public void testAllRoads() {
        ArrayList<String> allRoads = TGM.allRoads();
        assertTrue(allRoads.contains("I-495"));
        assertTrue(allRoads.contains("ICC 200"));
        assertTrue(allRoads.contains("Shady Grove Road"));
    }

    @Test
    public void testDeleteRoadConnection() {
        TGM.deleteRoadConnection(town1, town2, "Shady Grove Road");
        assertFalse(TGM.containsRoadConnection(town1, town2));
    }

    @Test
    public void testDeleteTown() {
        TGM.deleteTown(town1);
        assertFalse(TGM.containsTown(town1));
        assertFalse(TGM.containsRoadConnection(town1, town2));
    }

    @Test
    public void testAllTowns() {
        ArrayList<String> allTowns = TGM.allTowns();
        assertTrue(allTowns.contains("Gaithersburg"));
        assertTrue(allTowns.contains("Rockville"));
        assertFalse(allTowns.contains("Nonexistent Town"));
    }

    @Test
    public void testGetPath() {
        ArrayList<String> path = TGM.getPath(town1, town7);
        assertEquals("Gaithersburg via ICC 200 to Silver Spring 8 mi", path.get(0));
        assertEquals("Silver Spring via New Hampshire Ave N to White Oak 3 mi", path.get(1));
    }

    @Test
    public void testPopulateTownGraph() throws FileNotFoundException {
        File testFile = new File("testFile.txt");

        try (PrintWriter writer = new PrintWriter(testFile)) {
            writer.println("Road1,10;TownA;TownB");
            writer.println("Road2,15;TownB;TownC");
            writer.println("Road3,20;TownC;TownD");
        }

        TGM.populateTownGraph(testFile);

        assertTrue(TGM.containsTown("TownA"));
        assertTrue(TGM.containsTown("TownD"));
        assertTrue(TGM.containsRoadConnection("TownA", "TownB"));

        testFile.delete();
    }

    @Test
    public void testEmptyGraph() {
        TownGraphManager emptyTGM = new TownGraphManager();
        assertTrue(emptyTGM.allTowns().isEmpty());
        assertTrue(emptyTGM.allRoads().isEmpty());
    }
}
