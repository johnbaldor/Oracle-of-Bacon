package wpialgs.sixdegrees.separation;

import edu.princeton.cs.algs4.*;
import java.io.File;
import java.util.Objects;
import wpialgs.hw04.utils.Histogram;
import wpialgs.sixdegrees.graphs.UndirectedGraph;
import wpialgs.sixdegrees.graphs.Vertex;
import wpialgs.sixdegrees.utils.Actor;
import wpialgs.sixdegrees.utils.Movie;

/**
 * Traverses a graph from a single source using breadth-first search algorithm from source {@code s} on a graph
 * {@code G}. After preprocessing the graph, can process shortest path queries from {@code s} to any vertex.
 * <p>
 * This {@link DegreesOfSeparation} class was adapted from
 * <a href="https://courses.cs.duke.edu/cps100e/spring09/class/12_Bacon/code/Bacon.html">Bacon</a>
 */
public abstract class DegreesOfSeparation {

    // name -> Actor object
    protected final LinearProbingHashST<String, Actor> myActors;
    // name -> Movies that actor has been in
    protected final LinearProbingHashST<String, Movie> myMovies;
    protected final UndirectedGraph myG;
    // source node to start our search
    protected final String mySource;
    protected final Histogram myHistogram;

    /**
     * This allows us to compute the degrees of separation for actors/movies in Hollywood using "Kevin Bacon" as the
     * source vertex.
     */
    public DegreesOfSeparation() {
        this("Bacon, Kevin");
    }

    /**
     * This allows us to compute the degrees of separation for actors/movies in Hollywood using the specified source
     * vertex.
     *
     * @param sourceVertex
     *            Actor/Movie that will be serving as the source of our search.
     */
    public DegreesOfSeparation(String sourceVertex) {
        myG = new UndirectedGraph();
        myActors = new LinearProbingHashST<>();
        myMovies = new LinearProbingHashST<>();
        mySource = sourceVertex;
        myHistogram = new Histogram("Frequency Chart for: " + sourceVertex, "Degrees of Separation", "Frequency");
    }

    /**
     * Create a graph from the provided information.
     *
     */
    public abstract void createGraph();

    /**
     * Create a string with the chain from source to specified actor or actress / movie. If no such actor or actress /
     * movie, it will generate the appropriate error message as a string.
     *
     * @param name
     *            for actor or actress / movie.
     *
     * @return A string representation of the chain
     */
    public abstract String chainAsString(String name);

    /**
     * Computes the Hollywood number for the source.
     *
     * @return Average degrees of separation of all the actors / movies.
     */
    public abstract double computeHollywoodNumber();

    /**
     * Creates a frequency chart containing statistics about the source's distance number.
     */
    public abstract void createFrequencyChart();

    /**
     * Overrides the default {@link Object#equals(Object)} method.
     *
     * @param o
     *            Object to compare
     *
     * @return {@code true} if the two objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        DegreesOfSeparation that = (DegreesOfSeparation) o;

        if (!Objects.equals(myActors, that.myActors))
            return false;
        if (!Objects.equals(myMovies, that.myMovies))
            return false;
        if (!Objects.equals(myG, that.myG))
            return false;
        if (!Objects.equals(mySource, that.mySource))
            return false;
        return Objects.equals(myHistogram, that.myHistogram);
    }

    /**
     * Returns the graph associated with this object.
     *
     * @return A {@link UndirectedGraph} object
     */
    public UndirectedGraph getGraph() {
        return myG;
    }

    /**
     * Returns the histogram associated with this object.
     *
     * @return A {@link Histogram} object
     */
    public Histogram getHistogram() {
        return myHistogram;
    }

    /**
     * Overrides the default {@link Object#hashCode()} method.
     *
     * @return Hash code value for this object.
     */
    @Override
    public int hashCode() {
        int result = myActors != null ? myActors.hashCode() : 0;
        result = 31 * result + (myMovies != null ? myMovies.hashCode() : 0);
        result = 31 * result + (myG != null ? myG.hashCode() : 0);
        result = 31 * result + (mySource != null ? mySource.hashCode() : 0);
        result = 31 * result + (myHistogram != null ? myHistogram.hashCode() : 0);
        return result;
    }

    /**
     * Reads in the {@link Actor}s and {@link Movie}s from a {@link File}. Each line in the data file consists of a
     * movie title, followed by a list of actors and actresses that appeared in that movie, delimited by delimiter
     *
     * @param f
     *            the {@link File} to be read, does not do anything if the file cannot be read for any reason
     * @param delimiter
     *            the string that appears between {@link Movie} and {@link Actor} names on each line
     */
    public final void readFile(File f, String delimiter) {
        // Read every line
        In in = new In(f);
        while (in.hasNextLine()) {
            String line = in.readLine();

            // Ignore lines with bad input
            String[] elems = line.split(delimiter);
            if (elems.length != 0) {
                // create movie
                Movie movie = new Movie(elems[0]);
                myMovies.put(elems[0], movie); // add movie to movie map

                // loop through elems
                for (int k = 1; k < elems.length; k++) {
                    // create an actor and add to actors map and
                    // to movie's list of actors
                    Actor person = myActors.get(elems[k]);
                    if (person == null) { // new actor
                        person = new Actor(elems[k]);
                        myActors.put(elems[k], person);
                    }

                    person.add(movie);
                    movie.addActor(person);
                }
            }
        }
    }

    /**
     * Overrides the default {@link Object#toString()} method.
     *
     * @return A string representation of this object.
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "myActors=" + myActors + ", myMovies=" + myMovies + ", myG=" + myG
                + ", mySource='" + mySource + '\'' + ", myHistogram=" + myHistogram + '}';
    }

    /**
     * Traverse the graph from node named source using BST
     *
     * @param source
     *            the string name of a vertex
     */
    public void traverseBFS(String source) {
        if (myG.hasVertex(source)) {
            traverseBFS(myG, myG.getVertex(source));
        }
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
    public abstract void traverseBFS(UndirectedGraph g, Vertex source);
}