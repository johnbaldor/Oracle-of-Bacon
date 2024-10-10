package wpialgs.sixdegrees.graphs;

import java.util.Objects;

/**
 * A C-style struct definition of a {@link Vertex} to be used with the {@link UndirectedGraph} class.
 * <p>
 * The distance field is designed to hold the length of the shortest unweighted path from the source of the traversal
 * <p>
 * The predecessor field refers to the previous field on the shortest path from the source (i.e. the vertex one edge
 * closer to the source).
 * <p>
 * This {@link Vertex} class was adapted from
 * <a href="https://courses.cs.duke.edu/cps100e/spring09/class/12_Bacon/code/Vertex.html">Vertex</a>
 */
public class Vertex implements Comparable<Vertex> {

    /**
     * label for Vertex
     */
    public String name;

    /**
     * length of shortest path from source
     */
    public int distance;

    /**
     * previous vertex on path from source
     */
    public Vertex predecessor; // previous vertex

    /**
     * Infinite distance indicates that there is no path from the source to this vertex
     */
    public static final int INFINITY = Integer.MAX_VALUE;

    /**
     * Creates a new vertex with the specified name.
     *
     * @param v
     *            Name associated with the vertex.
     */
    public Vertex(String v) {
        name = v;
        distance = INFINITY; // start as infinity away
        predecessor = null;
    }

    /**
     * Compare on the basis of distance from source first and then lexicographically.
     *
     * @see Comparable#compareTo(Object)
     */
    public int compareTo(Vertex other) {
        int diff = distance - other.distance;
        if (diff != 0)
            return diff;
        else
            return name.compareTo(other.name);
    }

    /**
     * This allows us to compare to {@link Vertex}s.
     *
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Vertex vertex = (Vertex) o;

        if (distance != vertex.distance)
            return false;
        if (!name.equals(vertex.name))
            return false;
        return Objects.equals(predecessor, vertex.predecessor);
    }

    /**
     * The name of the {@link Vertex} is assumed to be unique, so it is used as a {@code hashCode}.
     *
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * This returns the name of the {@link Vertex}.
     *
     * @see Object#toString()
     */
    public String toString() {
        return name;
    }
}