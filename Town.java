package application;

/**
 * Represents a town as a node of a graph.
 * Implements Comparable for sorting and comparison based on the town's name.
 * 
 * @author Nitan
 */
public class Town implements Comparable<Town> {

    private String name;

    /**
     * Constructor. Creates a town with the given name.
     * 
     * @param name the name of the town.
     */
    public Town(String name) {
        this.name = name;
    }

    /**
     * Copy constructor. Creates a new town using the name of another town.
     * 
     * @param templateTown the town to copy from.
     */
    public Town(Town templateTown) {
        this.name = templateTown.name;
    }

    /**
     * Returns the name of the town.
     * 
     * @return the name of the town.
     */
    public String getName() {
        return name;
    }

    /**
     * Compares this town to another town based on their names.
     * 
     * @param other the town to compare with.
     * @return 0 if names are equal, a positive or negative number otherwise.
     */
    @Override
    public int compareTo(Town other) {
        return this.name.compareTo(other.name);
    }

    /**
     * Checks if this town is equal to another object. Towns are considered equal
     * if their names are the same.
     * 
     * @param obj the object to compare.
     * @return true if the towns are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Town)) {
            return false;
        }
        Town other = (Town) obj;
        return this.name.equals(other.name);
    }

    /**
     * Returns the hash code for the town, based on its name.
     * 
     * @return the hash code of the town.
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Returns the string representation of the town.
     * 
     * @return the name of the town.
     */
    @Override
    public String toString() {
        return name;
    }
}