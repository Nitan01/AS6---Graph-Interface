package application;

/**
 * A road connecting two towns, representing an edge in a graph.
 * The class implements Comparable for comparison based on the road's weight.
 * This is an undirected graph, so a road from A to B is the same as a road from B to A.
 * 
 * @author Nitan
 */
public class Road implements Comparable<Road> {

    private Town source;
    private Town destination;
    private int weight;
    private String name;

    /**
     * Constructor to initialize all fields.
     * 
     * @param source      The source town of the road.
     * @param destination The destination town of the road.
     * @param degrees     The weight of the road (distance).
     * @param name        The name of the road.
     */
    public Road(Town source, Town destination, int degrees, String name) {
        this.source = source;
        this.destination = destination;
        this.weight = degrees;
        this.name = name;
    }

    /**
     * Constructor with a default weight of 1.
     * 
     * @param source      The source town of the road.
     * @param destination The destination town of the road.
     * @param name        The name of the road.
     */
    public Road(Town source, Town destination, String name) {
        this(source, destination, 1, name);
    }

    /**
     * Checks if the road contains a given town.
     * 
     * @param town The town to check.
     * @return True if the road connects to the given town, false otherwise.
     */
    public boolean contains(Town town) {
        return town != null && (source.equals(town) || destination.equals(town));
    }

    /**
     * Compares two Road objects based on their weight.
     * 
     * @param o The other Road to compare.
     * @return A positive number if this road's weight is greater, negative if less,
     *         and zero if equal.
     */
    @Override
    public int compareTo(Road o) {
        return Integer.compare(this.weight, o.weight);
    }

    /**
     * Returns the destination town of the road.
     * 
     * @return The destination town.
     */
    public Town getDestination() {
        return destination;
    }

    /**
     * Returns the name of the road.
     * 
     * @return The name of the road.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the source town of the road.
     * 
     * @return The source town.
     */
    public Town getSource() {
        return source;
    }

    /**
     * Returns the weight of the road.
     * 
     * @return The weight of the road.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Checks equality between two Road objects.
     * A road from A to B is considered equal to a road from B to A.
     * 
     * @param obj The object to compare.
     * @return True if the roads are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Road)) return false;
        Road other = (Road) obj;
        return (source.equals(other.source) && destination.equals(other.destination)) ||
               (source.equals(other.destination) && destination.equals(other.source));
    }

    /**
     * Returns a string representation of the road.
     * 
     * @return A descriptive string of the road.
     */
    @Override
    public String toString() {
        return name + " [" + source.getName() + " to " + destination.getName() + " (" + weight + " miles)]";
    }

    /**
     * Generates a hash code for the Road object.
     * 
     * @return A hash code value for the object.
     */
    @Override
    public int hashCode() {
        return source.hashCode() + destination.hashCode() + weight + (name != null ? name.hashCode() : 0);
    }
}