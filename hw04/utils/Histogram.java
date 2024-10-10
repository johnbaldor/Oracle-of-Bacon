/*
 * Histogram.java
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
package wpialgs.hw04.utils;

import edu.princeton.cs.algs4.StdOut;
import wpialgs.day18.TreeMap;


/**
 * <p>
 * This class requires you complete the {@link #record(int)}, {@link #isEmpty()}, {@link #minimum()}, {@link #maximum()}
 * and {@link #report()} methods by using a symbol table. The {@link #report(int)} method is complete and doesn't
 * require any change.
 * </p>
 *
 * @version 3.0
 */
public class Histogram {

    // Class attributes
    private final String myTitle;
    private final String myCol1Label;
    private final String myCol2Label;
    private final TreeMap<Integer, Integer> histogram;

    // You will need some symbol table that you can use to store the keys
    // in such a way that you can retrieve them in order.
    // NOTE 1: The book suggests a couple of implementations that would work!
    // NOTE 2: Use the version that is in the edu.princeton.cs.algs4 and not the ones in the wpialgs.daysXX

    /**
     * This creates an object for storing data that can later be displayed as a histogram.
     *
     * @param title
     *            Title for this histogram
     * @param col1Label
     *            Label for column 1
     * @param col2Label
     *            Label for column 2
     */
    public Histogram(String title, String col1Label, String col2Label) {
        myTitle = title;
        myCol1Label = col1Label;
        myCol2Label = col2Label;
        histogram = new TreeMap<>();


    }

    /**
     * Increase the count for the number of times {@code key} exists in the {@link Histogram}. Note that if the user
     * passes in {@link Integer#MIN_VALUE} and {@link Integer#MAX_VALUE}, we treat those as {@code -infinity} and
     * {@code infinity}.
     *
     * @param key
     *            A key value in the histogram.
     */
    public void record(int key) {
        histogram.put(key, histogram.getOrDefault(key, 0) + 1);
    }


    /**
     * Return whether histogram is empty.
     *
     * @return {@code true} if the histogram is empty, {@code false} otherwise.
     */
    public boolean isEmpty() {
        return histogram.isEmpty();
    }

    /**
     * Return the lowest integer key in the histogram.
     *
     * @return The minimum value key in the histogram
     */
    public int minimum() {
        return histogram.firstKey();
    }

    /**
     * Return the largest integer key in the histogram.
     *
     * @return The maximum value key in the histogram
     */
    public int maximum() {
        return histogram.lastKey();
    }

    /**
     * Return sum of counts for keys from lo (inclusive) to high (inclusive).
     *
     * @param lo
     *            Lowest key value
     * @param hi
     *            Highest key value
     *
     * @return Sum of counts
     */
    public int total(int lo, int hi) {
        int sum = 0;
        for (int key : histogram.subMap(lo, true, hi, true).keySet()) {
            sum += histogram.get(key);
        }
        return sum;
    }

    /**
     * Produce a report for all keys (and their counts) in ascending order of keys.
     */
    public void report() {
        // Histogram header
        StdOut.println(myTitle);
        StdOut.printf("%-20s\t\t%-20s\n", myCol1Label, myCol2Label);
        StdOut.println("---------------------\t\t--------------------");

        // Variables to be updated and used in printf
        int currCol1;
        int currCol2;

        // NOTE: Use the following printf to print a row with values from column 1 and column 2
        // StdOut.printf("%-20d\t\t%20d\n", currCol1, currCol2);

        for (int key : histogram.keySet()) {
            StdOut.printf("%-20d\t\t%20d\n", key, histogram.get(key));
        }
    }

    /**
     * Produce a report for all bins (with aggregate counts) in ascending order by range.
     * <p>
     * The first range label that is output should be "0 - (binSize-1)" since the report always starts from 0.
     * <p>
     * It is acceptable if the final range label includes values that exceed maximum().
     *
     * @param binSize
     *            The specified bin size for grouping keys
     */
    public void report(int binSize) {
        // USE AS-IS. NO CHANGE REQUIRED!
        // Histogram header
        StdOut.println(myTitle + " (binSize=" + binSize + ")");
        StdOut.printf("%-20s\t\t%-20s\n", myCol1Label, myCol2Label);
        StdOut.println("---------------------\t\t--------------------");

        // Prints the contents of the histogram
        for (int i = minimum(); i <= maximum(); i += binSize) {
            StdOut.printf("%-20s\t\t%20d\n", String.format("%d-%d", i, i + binSize - 1), total(i, i + binSize - 1));
        }
    }
}