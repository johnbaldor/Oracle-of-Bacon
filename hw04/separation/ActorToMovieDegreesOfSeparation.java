/*
 * ActorToMovieDegreeOfSeparation.java
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


/**
 * Creates an Actor-Movie graph where:
 * <ul>
 * <li>Vertices: Actor and Movie names
 * <li>Edges: an Actor is connected to a Movie, iff he or she appeared in that movie
 * </ul>
 */
public class ActorToMovieDegreesOfSeparation extends AbstractDegreesOfSeparation {

    /**
     * This allows us to compute the degrees of separation for actors/movies in Hollywood using "Kevin Bacon" as the
     * source vertex.
     */
    public ActorToMovieDegreesOfSeparation() {
        super();
    }

    /**
     * This allows us to compute the degrees of separation for actors/movies in Hollywood using the specified source
     * vertex.
     *
     * @param sourceVertex
     *            Actor/Movie that will be serving as the source of our search.
     */
    public ActorToMovieDegreesOfSeparation(String sourceVertex) {
        super(sourceVertex);
    }

    /**
     * Using the actors and movies maps, create an Actor-Movie graph where:
     * <ul>
     * <li>Vertices: Actor and Movie names
     * <li>Edges: an Actor is connected to a Movie, iff he or she appeared in that movie
     * </ul>
     */
    @Override
    public void createGraph() {
        for (String actorName : myActors.keys()) {
            Actor actor = myActors.get(actorName);

            myG.addVertex(actorName);

            Iterable<Movie> filmography = actor.getMovies();

            for (Movie movie : filmography) {
                String movieTitle = movie.name;

                myG.addVertex(movieTitle);

                myG.addEdge(actorName, movieTitle);
            }
        }}

    /**
     * Creates a frequency chart containing statistics about the source's distance number.
     */
    public void createFrequencyChart() {
        for (Vertex current : myG.getVertices()) {
            if (myActors.contains(current.name)) {
                if (current.distance == Integer.MAX_VALUE) {
                    myHistogram.record(current.distance);
                }
                else {
                    myHistogram.record(current.distance - current.distance / 2);
                }
            }
        }
    }

    /**
     * Computes the Hollywood number for the source.
     *
     * @return Average degrees of separation of all the actors / movies.
     */
    @Override
    public double computeHollywoodNumber() {
        int sum = 0;
        int count = 0;

        // Sum the distances and count the number of reachable actors
        for (Vertex current : myG.getVertices()) {
            if (myActors.contains(current.name)) {
                if (current.distance != Integer.MAX_VALUE && current.distance != Integer.MIN_VALUE) {
                    sum += current.distance / 2;
                    count++;
                }
            }
        }
        return (double) sum/count;
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
                    sb.append(dest.distance / 2).append(".\n\n");

                    int counter = 1;
                    while (dest != start) {
                        sb.append(counter).append(". ").append(dest.name).append(" was in \"").append(dest.predecessor)
                                .append("\" with ").append(dest.predecessor.predecessor).append(".\n");
                        dest = dest.predecessor.predecessor;
                        counter++;
                    }
                }
            }
        }

        return sb.toString();
    }
}