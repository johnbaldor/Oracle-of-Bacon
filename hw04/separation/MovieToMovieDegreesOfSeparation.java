/*
 * MovieToMovieDegreeOfSeparation.java
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

import edu.princeton.cs.algs4.SET;
import wpialgs.sixdegrees.graphs.Vertex;
import wpialgs.sixdegrees.utils.Actor;
import wpialgs.sixdegrees.utils.Movie;

/**
 * Creates a Movie-Movie graph where:
 * <ul>
 * <li>Vertices: Movie names
 * <li>Edges: two Movies are connected iff they share an Actor
 * </ul>
 */
public class MovieToMovieDegreesOfSeparation extends AbstractDegreesOfSeparation {

    /**
     * This allows us to compute the degrees of separation for movies.
     */
    public MovieToMovieDegreesOfSeparation() {
        this("X-Men: First Class");
    }

    /**
     * This allows us to compute the degrees of separation for movies in Hollywood using the specified source movie.
     *
     * @param sourceMovie
     *            Movie that will be serving as the source of our search.
     */
    public MovieToMovieDegreesOfSeparation(String sourceMovie) {
        super(sourceMovie);
    }

    /**
     * Using the actors and movies maps, create Movie graph where:
     * <ul>
     * <li>Vertices: Movie names
     * <li>Edges: two Movies are connected iff they share an Actor
     * </ul>
     */
    @Override
    public void createGraph() {
        for (String movieKey1 : myMovies.keys()) {
            Movie movie1 = myMovies.get(movieKey1);
            myG.addVertex(movie1.name);

            // Compare with all other movies to find shared actors
            for (String movieKey2 : myMovies.keys()) {
                if (!movieKey1.equals(movieKey2)) { // Avoid self loop
                    Movie movie2 = myMovies.get(movieKey2);


                    SET<Actor> movie2Actors = (SET<Actor>) movie2.getActors();

                    // Check if movie1 and movie2 share at least one actor
                    boolean shareActor = false;
                    for (Actor actor : movie1.getActors()) {
                        if (movie2Actors.contains(actor)) {
                            shareActor = true;
                            break; // Exit the loop if a shared actor is found
                        }
                    }

                    // If they share at least one actor, add an edge between the movies
                    if (shareActor) {
                        myG.addVertex(movie2.name);
                        myG.addEdge(movie1.name, movie2.name); // Add edge between the two movies
                    }
                }
            }
        }}

    /**
     * Creates a frequency chart containing statistics about the source's distance number.
     */
    public void createFrequencyChart() {
        for (Vertex v : myG.getVertices()) {
            int distance = v.distance;
            if (distance == Vertex.INFINITY) {

                myHistogram.record(Vertex.INFINITY);
            } else {

                myHistogram.record(distance);
            }
        }
    }

    /**
     * Computes the Hollywood number for the source.
     *
     * @return Average degrees of separation of all the movies.
     */
    @Override
    public double computeHollywoodNumber() {
        int totalDegrees = 0;
        int reachableMovies = 0;


        for (Vertex v : myG.getVertices()) {
            if (v.distance != Vertex.INFINITY) { // Only consider reachable movies
                totalDegrees += v.distance; // Add the degree of separation
                reachableMovies++;
            }
        }


        if (reachableMovies == 0) {
            return Double.POSITIVE_INFINITY;
        }
        return (double) totalDegrees / reachableMovies;
    }

    /**
     * Create a string with the chain from source to specified movie. If no such movie, it will generate the appropriate
     * error message as a string.
     *
     * @param name
     *            for movie.
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
            sb.append("Source movie: ").append(mySource).append(" does not exist in our graph.\n");
        } else {
            if (dest == null) {
                sb.append("Destination movie: ").append(name).append(" does not exist in our graph.\n");
            } else {
                sb.append(start.name).append(" and ").append(dest.name).append(" have a distance of ");
                if (dest.distance == Vertex.INFINITY) {
                    sb.append("infinity.\n");
                } else {
                    sb.append(dest.distance / 2).append(".\n\n");

                    int counter = 1;
                    while (dest != start) {
                        sb.append(counter).append(". ").append(dest.name).append(" was in \"").append(dest.predecessor)
                                .append("\" with ").append(dest.predecessor).append(".\n");
                        dest = dest.predecessor;
                        counter++;
                    }
                }
            }
        }

        return sb.toString();
    }
}