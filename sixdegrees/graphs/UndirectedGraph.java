package wpialgs.sixdegrees.graphs;

import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;

/**
 * Undirected, unweighted simple graph data type
 * <p>
 * Notes:
 * <ul>
 * <li>Parallel edges are not allowed
 * <li>Self loops are allowed
 * </ul>
 * <p>
 * This {@link UndirectedGraph} class was adapted from
 * <a href="https://courses.cs.duke.edu/cps100e/spring09/class/12_Bacon/code/Graph.html">Graph</a>
 */
public class UndirectedGraph {

    // Class attributes
    private final LinearProbingHashST<Vertex, SET<Vertex>> myAdjList;
    private final LinearProbingHashST<String, Vertex> myVertices;
    private static final SET<Vertex> EMPTY_SET = new SET<>();
    private int myNumVertices;
    private int myNumEdges;

    /**
     * Construct an empty graph.
     */
    public UndirectedGraph() {
        myAdjList = new LinearProbingHashST<>();
        myVertices = new LinearProbingHashST<>();
        myNumVertices = myNumEdges = 0;
    }

    // -----------------------------------------------------------
    // Vertices
    // -----------------------------------------------------------

    /**
     * Add a new vertex name with no neighbors (if vertex does not yet exist)
     *
     * @param name
     *            vertex to be added
     */
    public Vertex addVertex(String name) {
        Vertex v;
        v = myVertices.get(name);
        if (v == null) {
            v = new Vertex(name);
            myVertices.put(name, v);
            myAdjList.put(v, new SET<>());
            myNumVertices += 1;
        }

        return v;
    }

    /**
     * Return an iterator over the neighbors of {@link Vertex} named {@code v}
     *
     * @param name
     *            the String name of a {@link Vertex}
     *
     * @return an {@link Iterable} over {@link Vertex Vertices} that are adjacent to the {@link Vertex} named {@code v},
     *         empty set if {@code v} is not in {@link UndirectedGraph}.
     */
    public Iterable<Vertex> adjacentTo(String name) {
        if (!hasVertex(name))
            return EMPTY_SET;
        return adjacentTo(getVertex(name));
    }

    /**
     * Returns the vertex matching {@code v}
     *
     * @param name
     *            a string name of a {@link Vertex} that may be in this {@link UndirectedGraph}
     *
     * @return the {@link Vertex} with a name that matches {@code v} or {@code null} if no such {@link Vertex} exists in
     *         this {@link UndirectedGraph}
     */
    public Vertex getVertex(String name) {
        return myVertices.get(name);
    }

    /**
     * Returns an {@link Vertex} over all vertices in this {@link UndirectedGraph}.
     *
     * @return an {@link Iterable} over all vertices in this {@link UndirectedGraph}.
     */
    public Iterable<Vertex> getVertices() {
        Queue<Vertex> vertices = new Queue<>();
        for (String key : myVertices.keys()) {
            vertices.enqueue(myVertices.get(key));
        }

        return vertices;
    }

    /**
     * Returns {@code true} iff {@code v} is in this {@link UndirectedGraph}, {@code false} otherwise
     *
     * @param name
     *            a string name of a {@link Vertex} that may be in this {@link UndirectedGraph}
     *
     * @return {@code true} iff v is in this {@link UndirectedGraph}
     */
    public boolean hasVertex(String name) {
        return myVertices.contains(name);
    }

    /**
     * Returns the number of vertices in this {@link UndirectedGraph}.
     *
     * @return number of vertices
     */
    public int numVertices() {
        return myNumVertices;
    }

    // -----------------------------------------------------------
    // Edge
    // -----------------------------------------------------------

    /**
     * Add {@code to} to {@code from}'s set of neighbors, and add {@code from} to {@code to}'s set of neighbors. Does
     * not add an edge if another edge already exists
     *
     * @param from
     *            the name of the first {@link Vertex}
     * @param to
     *            the name of the second {@link Vertex}
     */
    public void addEdge(String from, String to) {
        Vertex v, w;
        if (hasEdge(from, to))
            return;
        myNumEdges += 1;
        if ((v = getVertex(from)) == null)
            v = addVertex(from);
        if ((w = getVertex(to)) == null)
            w = addVertex(to);
        myAdjList.get(v).add(w);
        myAdjList.get(w).add(v);
    }

    /**
     * Is {@code from}-{@code to}, an edge in this {@link UndirectedGraph}. The graph is undirected so the order of from
     * and to does not matter.
     *
     * @param from
     *            the name of the first {@link Vertex}
     * @param to
     *            the name of the second {@link Vertex}
     *
     * @return {@code true} iff from-to exists in this {@link UndirectedGraph}
     */
    public boolean hasEdge(String from, String to) {
        if (!hasVertex(from) || !hasVertex(to))
            return false;
        return myAdjList.get(myVertices.get(from)).contains(myVertices.get(to));
    }

    /**
     * Returns the number of edges in this {@link UndirectedGraph}.
     *
     * @return number of edges
     */
    public int numEdges() {
        return myNumEdges;
    }

    // -----------------------------------------------------------
    // Override Methods
    // -----------------------------------------------------------

    /**
     * Compares two graphs to see if they are equal.
     *
     * @return {@code true} if the graphs are the same, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        UndirectedGraph that = (UndirectedGraph) o;
        return myNumVertices == that.myNumVertices && myNumEdges == that.myNumEdges && myAdjList.equals(that.myAdjList)
                && myVertices.equals(that.myVertices);
    }

    /**
     * Returns a hash code for this graph.
     *
     * @return an integer
     */
    @Override
    public int hashCode() {
        int result = myAdjList.hashCode();
        result = 31 * result + myVertices.hashCode();
        result = 31 * result + myNumVertices;
        result = 31 * result + myNumEdges;
        return result;
    }

    /**
     * Returns a string representation of this graph.
     *
     * @return a string representation
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Vertex v : getVertices()) {
            s.append(v).append(": ");
            for (Vertex w : myAdjList.get(v)) {
                s.append(w).append(" ");
            }
            s.append("\n");
        }

        return s.toString();
    }

    // -----------------------------------------------------------
    // Helper methods
    // -----------------------------------------------------------

    /**
     * Return an iterator over the neighbors of {@link Vertex} {@code v}
     *
     * @param v
     *            the {@link Vertex}
     *
     * @return an {@link Iterable} over {@link Vertex Vertices} that are adjacent to the {@link Vertex} {@code v}, empty
     *         set if {@code v} is not in {@link UndirectedGraph}.
     */
    private Iterable<Vertex> adjacentTo(Vertex v) {
        if (!myAdjList.contains(v))
            return EMPTY_SET;
        return myAdjList.get(v);
    }
}