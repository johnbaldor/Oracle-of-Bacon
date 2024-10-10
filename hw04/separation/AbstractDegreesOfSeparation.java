/*
 * AbstractDegreesOfSeparation.java
 *
 * Author: John Baldor
 * Submitted on: 10/10/24
 *
 * Academic Honesty Declaration:
 *
 * The following code represents my own work and I have neither received nor given assistance
 * that violates the collaboration policy posted with this assignment. I have not copied or modified code
 * from any other source other than the homework assignment, course textbook, or course lecture slides.
 * Any unauthorized collaboration or use of materials not permitted will be subjected to academic integrity policies of
 * WPI and CS 2223.
 *
 * I acknowledge that this homework assignment is based upon an assignment created by WPI and that any publishing or
 * posting of this code is prohibited unless I receive written permission from WPI.
 */
package wpialgs.hw04.separation;

import wpialgs.sixdegrees.graphs.UndirectedGraph;
import wpialgs.sixdegrees.graphs.Vertex;
import wpialgs.sixdegrees.separation.DegreesOfSeparation;
import edu.princeton.cs.algs4.Queue;





/**
 * This abstract class implements the {@link #traverseBFS(UndirectedGraph, Vertex)} method.
 *
 * @version 1.0
 */
public abstract class AbstractDegreesOfSeparation extends DegreesOfSeparation {

    /**
     * This allows us to compute the degrees of separation for actors/movies in Hollywood using "Kevin Bacon" as the
     * source vertex.
     */
    public AbstractDegreesOfSeparation() {
        super();
    }

    /**
     * This allows us to compute the degrees of separation for actors/movies in Hollywood using the specified source
     * vertex.
     *
     * @param sourceVertex
     *            Actor/Movie that will be serving as the source of our search.
     */
    public AbstractDegreesOfSeparation(String sourceVertex) {
        super(sourceVertex);
    }

    /**
     * Traverse the graph using breadth-first search on {@code g} from {@code source}
     * <p>
     * If source is not in the {@code UndirectedGraph}, then the traversal will do nothing.
     *
     * @param g
     *            {@code UndirectedGraph} that should be initialized and all vertices must have distance set to
     *            {@link Vertex#INFINITY}. After traversal, {@code distance} and {@code predecessor} fields will be set.
     * @param source
     *            the {@link Vertex} from which to begin the traversal
     */
    public final void traverseBFS(UndirectedGraph g, Vertex source) {
        if (g.getVertex(source.name) == null) {
            return;
        }
        for (Vertex v : g.getVertices()) {
            v.distance = Vertex.INFINITY;  // Set infinity
            v.predecessor = null;          // Set null
        }
        Queue<Vertex> queue = new Queue<>();
        source.distance = 0;               // Distance to itself is zero
        queue.enqueue(source);

        while (!queue.isEmpty()) {
            Vertex current = queue.dequeue(); // Dequeue the current vertex


            // Explore each next vertex of the current
            for (Vertex neighbor : g.adjacentTo(current.name)) {
                if (neighbor.distance == Vertex.INFINITY) { // If not visited yet
                    // Set the distance from the source
                    neighbor.distance = current.distance + 1;
                    // Set the predecessor to previous
                    neighbor.predecessor = current;

                    // Add the neighbor to the queue
                    queue.enqueue(neighbor);
                }
            }
        }

    }
}