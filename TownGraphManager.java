package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Manages a graph and works with it by adding, removing, and querying towns and roads.
 * Effectively serves as a driver for the graph operations.
 * 
 * @author Nitan
 */
public class TownGraphManager implements TownGraphManagerInterface {

    private Graph townGraph;

    /**
     * Constructor that creates a new graph to work with.
     */
    public TownGraphManager() {
        townGraph = new Graph();
    }

    @Override
    public boolean addRoad(String town1, String town2, int weight, String roadName) {
        Town t1 = getTown(town1);
        Town t2 = getTown(town2);

        if (t1 == null || t2 == null || townGraph.containsEdge(t1, t2)) {
            return false;
        }

        townGraph.addEdge(t1, t2, weight, roadName);
        return true;
    }

    @Override
    public String getRoad(String town1, String town2) {
        Town t1 = getTown(town1);
        Town t2 = getTown(town2);

        for (Road road : townGraph.edgeSet()) {
            if (road.contains(t1) && road.contains(t2)) {
                return road.getName();
            }
        }

        return null; // Return null if no road is found
    }

    private Road findRoad(String town1, String town2) {
        Town t1 = getTown(town1);
        Town t2 = getTown(town2);

        for (Road road : townGraph.edgeSet()) {
            if (road.contains(t1) && road.contains(t2)) {
                return road;
            }
        }

        return null;
    }

    @Override
    public boolean addTown(String name) {
        return townGraph.addVertex(new Town(name));
    }

    @Override
    public Town getTown(String name) {
        for (Town town : townGraph.vertexSet()) {
            if (town.getName().equals(name)) {
                return town;
            }
        }
        return null;
    }

    @Override
    public boolean containsTown(String name) {
        return getTown(name) != null;
    }

    @Override
    public boolean containsRoadConnection(String town1, String town2) {
        return townGraph.containsEdge(getTown(town1), getTown(town2));
    }

    @Override
    public ArrayList<String> allRoads() {
        ArrayList<String> roads = new ArrayList<>();
        for (Road road : townGraph.edgeSet()) {
            roads.add(road.getName());
        }
        roads.sort(String.CASE_INSENSITIVE_ORDER);
        return roads;
    }

    @Override
    public boolean deleteRoadConnection(String town1, String town2, String roadName) {
        Road road = findRoad(town1, town2);
        if (road == null || !road.getName().equals(roadName)) {
            return false;
        }

        townGraph.removeEdge(getTown(town1), getTown(town2), road.getWeight(), road.getName());
        return true;
    }

    @Override
    public boolean deleteTown(String name) {
        return townGraph.removeVertex(getTown(name));
    }

    @Override
    public ArrayList<String> allTowns() {
        ArrayList<String> towns = new ArrayList<>();
        for (Town town : townGraph.vertexSet()) {
            towns.add(town.getName());
        }
        towns.sort(String.CASE_INSENSITIVE_ORDER);
        return towns;
    }

    @Override
    public ArrayList<String> getPath(String town1, String town2) {
        return townGraph.shortestPath(getTown(town1), getTown(town2));
    }

    public void populateTownGraph(File file) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String[] parts = scanner.nextLine().split(";");
            String[] roadInfo = parts[0].split(",");
            String roadName = roadInfo[0];
            int weight = Integer.parseInt(roadInfo[1]);
            String source = parts[1];
            String destination = parts[2];

            addTown(source);
            addTown(destination);
            addRoad(source, destination, weight, roadName);
        }
        scanner.close();
    }
}