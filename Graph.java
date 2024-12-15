package application;

import java.util.*;

/**
 * Graph data structure with towns as vertices and roads as edges connecting them.
 *
 * @param <Town> Vertex representing a town.
 * @param <Road> Edge representing a road.
 * @author Nitan
 */
public class Graph implements GraphInterface<Town, Road> {
    private Set<Town> towns = new HashSet<>();
    private Set<Road> roads = new HashSet<>();

    /**
     * Gets the road connecting two towns.
     */
    @Override
    public Road getEdge(Town sourceVertex, Town destinationVertex) {
        return roads.stream()
                .filter(road -> road.contains(sourceVertex) && road.contains(destinationVertex))
                .findFirst()
                .orElse(null);
    }

    /**
     * Adds a road between two towns.
     */
    @Override
    public Road addEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
        if (!towns.contains(sourceVertex) || !towns.contains(destinationVertex)) {
            throw new IllegalArgumentException("Towns must exist in the graph.");
        }
        Road road = new Road(sourceVertex, destinationVertex, weight, description);
        roads.add(road);
        return road;
    }

    /**
     * Adds a town to the graph.
     */
    @Override
    public boolean addVertex(Town v) {
        return towns.add(v);
    }

    /**
     * Checks if a road exists between two towns.
     */
    @Override
    public boolean containsEdge(Town sourceVertex, Town destinationVertex) {
        return getEdge(sourceVertex, destinationVertex) != null;
    }

    /**
     * Checks if a town exists in the graph.
     */
    @Override
    public boolean containsVertex(Town v) {
        return towns.contains(v);
    }

    /**
     * Returns a set of all roads in the graph.
     */
    @Override
    public Set<Road> edgeSet() {
        return new HashSet<>(roads);
    }

    /**
     * Returns all roads connected to a town.
     */
    @Override
    public Set<Road> edgesOf(Town vertex) {
        if (!towns.contains(vertex)) {
            throw new IllegalArgumentException("Town does not exist in the graph.");
        }
        Set<Road> connectedRoads = new HashSet<>();
        for (Road road : roads) {
            if (road.contains(vertex)) {
                connectedRoads.add(road);
            }
        }
        return connectedRoads;
    }

    /**
     * Removes a road between two towns.
     */
    @Override
    public Road removeEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
        Road targetRoad = new Road(sourceVertex, destinationVertex, weight, description);
        if (roads.remove(targetRoad)) {
            return targetRoad;
        }
        return null;
    }

    /**
     * Removes a town and its connected roads from the graph.
     */
    @Override
    public boolean removeVertex(Town v) {
        if (!towns.remove(v)) return false;
        roads.removeIf(road -> road.contains(v));
        return true;
    }

    /**
     * Returns a set of all towns in the graph.
     */
    @Override
    public Set<Town> vertexSet() {
        return new HashSet<>(towns);
    }

    /**
     * Finds the shortest path from the source to the destination using Dijkstra's algorithm.
     */
    @Override
    public ArrayList<String> shortestPath(Town sourceVertex, Town destinationVertex) {
        Map<Town, Town> previous = new HashMap<>();
        Map<Town, Integer> distances = new HashMap<>();
        PriorityQueue<Town> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        // Initialize distances and previous nodes
        towns.forEach(town -> {
            distances.put(town, Integer.MAX_VALUE);
            previous.put(town, null);
        });
        distances.put(sourceVertex, 0);
        priorityQueue.add(sourceVertex);

        // Process the graph
        while (!priorityQueue.isEmpty()) {
            Town current = priorityQueue.poll();

            for (Road road : edgesOf(current)) {
                Town neighbor = road.getSource().equals(current) ? road.getDestination() : road.getSource();
                int newDist = distances.get(current) + road.getWeight();
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    previous.put(neighbor, current);
                    priorityQueue.add(neighbor);
                }
            }
        }

        // Build the path
        ArrayList<String> path = new ArrayList<>();
        for (Town at = destinationVertex; at != null && previous.get(at) != null; at = previous.get(at)) {
            Town from = previous.get(at);
            Road road = getEdge(from, at);
            path.add(from.getName() + " via " + road.getName() + " to " + at.getName() + " " + road.getWeight() + " mi");
        }

        Collections.reverse(path);
        return path;
    }

    /**
     * Placeholder for the Dijkstra's algorithm implementation.
     */
    @Override
    public void dijkstraShortestPath(Town sourceVertex) {
        // Not used directly; handled by shortestPath.
    }
}
