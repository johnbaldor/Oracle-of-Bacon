/*
 * ActorToActorDegreeOfSeparation.java
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

import wpialgs.sixdegrees.graphs.Vertex;
import wpialgs.sixdegrees.utils.Actor;
import wpialgs.sixdegrees.utils.Movie;

import java.util.HashSet;
import java.util.Set;

/**
 * Creates an Actor-Actor graph where:
 * <ul>
 * <li>Vertices: Actor names
 * <li>Edges: two Actors are connected iff they appeared in the same movie
 * </ul>
 */
public class ActorToActorDegreesOfSeparation extends AbstractDegreesOfSeparation {

    /**
     * This allows us to compute the degrees of separation for actors in Hollywood using "Kevin Bacon" as the source
     * vertex.
     */
    public ActorToActorDegreesOfSeparation() {
        super();
    }

    /**
     * This allows us to compute the degrees of separation for actors in Hollywood using the specified source vertex.
     *
     * @param sourceVertex
     *            Actor that will be serving as the source of our search.
     */
    public ActorToActorDegreesOfSeparation(String sourceVertex) {
        super(sourceVertex);
    }

    /**
     * Using the actors and movies maps, create an Actor graph where:
     * <ul>
     * <li>Vertices: Actor names
     * <li>Edges: two Actors are connected iff they appeared in the same movie
     * </ul>
     */
    @Override
    public void createGraph() {
        for (String movieKey : myMovies.keys()) {
            Movie movie = myMovies.get(movieKey);
            // make sure unique actors
            Set<Actor> actorsInMovie = new HashSet<>();
            for (Actor actor : movie.getActors()) {
                actorsInMovie.add(actor); // Add actor to the set
            }

            // Add an edge between actors in the set
            for (Actor actor1 : actorsInMovie) {
                myG.addVertex(actor1.name);

                for (Actor actor2 : actorsInMovie) {
                    if (!actor1.equals(actor2)) {

                        myG.addEdge(actor1.name, actor2.name);
                    }
                }
            }
        }
    }

    /**
     * Creates a frequency chart containing statistics about the source's distance number.
     */
    public void createFrequencyChart() {
        for (Vertex v : myG.getVertices()) {
            int distance = v.distance;

                myHistogram.record(distance);


        }
    }

    /**
     * Computes the Hollywood number for the source.
     *
     * @return Average degrees of separation of all the actors.
     */
    @Override
    public double computeHollywoodNumber() {
        int totalDegrees = 0;
        int Actors = 0;

        // go over  all vertices in the graph
        for (Vertex v : myG.getVertices()) {
            if (v.distance != Vertex.INFINITY) { // Only use reachable vertices
                totalDegrees += v.distance; // Add the degree of separation to total
                Actors++;
            }
        }


        if (Actors == 0) {
            return Double.POSITIVE_INFINITY;
        }
        return (double) totalDegrees / Actors; //cast type
    }

    /**
     * Create a string with the chain from source to specified actor or actress. If no such actor or actress, it will
     * generate the appropriate error message as a string.
     *
     * @param name
     *            for actor or actress.
     *
     * @return A string representation of the chain
     */
    @Override
    public String chainAsString(String name) {
        Vertex start = myG.getVertex(mySource);
        Vertex dest = myG.getVertex(name);

        // Check if source exists
        StringBuilder sb = new StringBuilder();
        if (start == null) {
            sb.append("Source actor/actress: ").append(mySource).append(" does not exist in our graph.\n");
        } else {
            if (dest == null) {
                sb.append("Destination actor/actress: ").append(name).append(" does not exist in our graph.\n");
            } else {
                sb.append(start.name).append(" and ").append(dest.name).append(" have a distance of ");
                if (dest.distance == Vertex.INFINITY) {
                    sb.append("infinity.\n");
                } else {
                    sb.append(dest.distance).append(".\n\n");

                    int counter = 1;
                    while (dest != start) {
                        sb.append(counter).append(". ").append(dest.name).append(" was in a movie with ")
                                .append(dest.predecessor).append(".\n");
                        dest = dest.predecessor;
                        counter++;
                    }
                }
            }
        }

        return sb.toString();
    }
}