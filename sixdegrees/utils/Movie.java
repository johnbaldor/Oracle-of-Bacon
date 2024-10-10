package wpialgs.sixdegrees.utils;

import edu.princeton.cs.algs4.SET;

/**
 * Datatype for {@link Movie}s. Each movie has a name and a list of {@link Actor}s.
 * <p>
 * This {@link Movie} class was adapted from
 * <a href="https://courses.cs.duke.edu/cps100e/spring09/class/12_Bacon/code/Movie.html">Movie</a>
 */
public class Movie implements Comparable<Movie> {

    /**
     * Name of the Movie -- assumed to be unique
     */
    public String name;

    /**
     * A list of actors in this movie.
     */
    private final SET<Actor> myActors;

    /**
     * Creates a new movie with the specified name.
     *
     * @param title
     *            Movie title
     */
    public Movie(String title) {
        name = title;
        myActors = new SET<>();
    }

    /**
     * Add an {@link Actor} to our list of cast members
     *
     * @param person
     *            Actor that appeared in this Movie
     */
    public void addActor(Actor person) {
        myActors.add(person);
    }

    /**
     * The name of the {@link Movie} is assumed to be unique, so it is used to compare with another one.
     *
     * @see Comparable#compareTo(Object)
     */
    public int compareTo(Movie other) {
        return name.compareTo(other.name);
    }

    /**
     * Return an iterator over all {@link Actor}s in this {@link Movie}
     *
     * @return an {@link Iterable} over all {@link Actor}s in this {@link Movie}
     */
    public Iterable<Actor> getActors() {
        return myActors;
    }

    /**
     * This allows us to compare to {@link Movie}s.
     *
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Movie movie = (Movie) o;

        if (!name.equals(movie.name))
            return false;
        return myActors.equals(movie.myActors);
    }

    /**
     * The name of the {@link Movie} is assumed to be unique, so it is used as a {@code hashCode}.
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * This returns the name of the {@link Movie}.
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return name;
    }
}