package wpialgs.sixdegrees.utils;

import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.SET;

/**
 * Datatype that holds information on {@link Actor}s.
 * <p>
 * This {@link Actor} class was adapted from
 * <a href="https://courses.cs.duke.edu/cps100e/spring09/class/12_Bacon/code/Actor.html">Actor</a>
 */
public class Actor implements Comparable<Actor> {

    /**
     * Name of the actor - assumed to be unique
     */
    public String name;

    /**
     * Movies that this actor has been in.
     */
    private final SET<Movie> myMovies;

    /**
     * A mapping from co-star actors to the movies they were both in.
     */
    private LinearProbingHashST<Actor, Movie> myCoStars;

    /**
     * Creates a new actor with the specified name.
     *
     * @param person
     *            Actor/Actress name
     */
    public Actor(String person) {
        name = person;
        myMovies = new SET<>();
        myCoStars = null;
    }

    /**
     * The name of the {@link Actor} is assumed to be unique, so it is used to compare with another one.
     *
     * @see Comparable#compareTo(Object)
     */
    public int compareTo(Actor other) {
        return name.compareTo(other.name);
    }

    /**
     * Returns the name of the Actor
     *
     * @return the String name of the Actor
     */
    public String getName() {
        return name;
    }

    /**
     * Returns an iterator of all movies that this actor has appeared in
     *
     * @return the {@link Iterable} of {@link Movie}s that this actor has appeared in
     */
    public Iterable<Movie> getMovies() {
        return myMovies;
    }

    /**
     * Determines all the co-stars that this {@link Actor} has had. Stores the list of co-stars in a map where each
     * co-star is mapped to the movie in which the actors appeared together.
     * <p>
     * Notes:
     * <ul>
     * <li>All {@link Actor}s and {@link Movie}s must be initialized before this method is called.
     * <li>This method will compute the map of co-stars the first time it is called and cache the result. After that
     * point, the method should return the result in constant time.
     * <li>The map will not be updated if any {@link Actor} or {@link Movie} is added later.
     * </ul>
     *
     * @return a {@link LinearProbingHashST} of all the {@link Actor}s that this {@link Actor} has costarred, where each
     *         {@link Actor} is mapped to the {@link Movie} where they starred together
     */
    public LinearProbingHashST<Actor, Movie> coStars() {
        if (myCoStars != null)
            return myCoStars;
        myCoStars = new LinearProbingHashST<>();
        for (Movie m : myMovies) {
            for (Actor a : m.getActors()) {
                if (a != this) {
                    myCoStars.put(a, m);
                }
            }
        }

        return myCoStars;
    }

    /**
     * Adds {@code m} to the list of {@link Movie}s that this {@link Actor} has appeared in.
     *
     * @param m
     *            the {@link Movie} to be added
     */
    public void add(Movie m) {
        myMovies.add(m);
    }

    /**
     * This allows us to compare to {@link Actor}s.
     *
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Actor actor = (Actor) o;

        if (!name.equals(actor.name))
            return false;
        if (!myMovies.equals(actor.myMovies))
            return false;
        return myCoStars.equals(actor.myCoStars);
    }

    /**
     * The name of the {@link Actor} is assumed to be unique, so it is used as a {@code hashCode}.
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * This returns the name of the {@link Actor}.
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return name;
    }
}