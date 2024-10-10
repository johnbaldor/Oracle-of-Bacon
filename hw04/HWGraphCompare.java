/*
 * HWGraphCompare.java
 *
 *  * Author: John Baldor
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
package wpialgs.hw04;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StopwatchCPU;
import java.io.File;
import java.util.Date;
import wpialgs.hw04.separation.ActorToActorDegreesOfSeparation;
import wpialgs.hw04.separation.ActorToMovieDegreesOfSeparation;
import wpialgs.hw04.separation.MovieToMovieDegreesOfSeparation;
import wpialgs.sixdegrees.graphs.UndirectedGraph;
import wpialgs.sixdegrees.separation.DegreesOfSeparation;

/**
 * This compares the {@link ActorToMovieDegreesOfSeparation}, {@link ActorToActorDegreesOfSeparation} and
 * {@link MovieToMovieDegreesOfSeparation} symbol graphs using the various datasets.
 *
 * @author Yu-Shan Sun
 *
 * @version 1.0
 */
public class HWGraphCompare {

    /**
     * The main entry point to the program.
     *
     * @param args
     *            Command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("USAGE: java HWSTCompare <filename> <sourceActor> <sourceMovie>");
        } else {
            String filename = args[0];
            // Welcome message
            Date today = new Date();
            StdOut.println("Welcome to CS 2223 - Symbol Graphs Compare!");
            StdOut.printf("\tTODAY: %s (%d)\n", today, today.getTime());
            StdOut.printf("\tRunning symbol graph experiments on file: %s.\n", filename);
            StdOut.println("------------------------------------");

            // Run experiment
            final String sourceActor = args[1];
            final String sourceMovie = args[2];

            // Use the Actor-Movie Degrees of Separation
            DegreesOfSeparation symbolGraph1 = new ActorToMovieDegreesOfSeparation(sourceActor);
            symbolGraph1.readFile(new File(args[0]), "\t");
            StdOut.println("SYMBOL GRAPH IMPLEMENTATION: ActorToMovie\n\n");
            runExperiment(symbolGraph1, sourceActor);

            // Use the Actor-Actor Degrees of Separation
            DegreesOfSeparation symbolGraph2 = new ActorToActorDegreesOfSeparation(sourceActor);
            symbolGraph2.readFile(new File(args[0]), "\t");
            StdOut.println("SYMBOL GRAPH IMPLEMENTATION: ActorToActor\n\n");
            runExperiment(symbolGraph2, sourceActor);

            // Use the Movie-Movie Degrees of Separation
            DegreesOfSeparation symbolGraph3 = new MovieToMovieDegreesOfSeparation(sourceMovie);
            symbolGraph3.readFile(new File(args[0]), "\t");
            StdOut.println("SYMBOL GRAPH IMPLEMENTATION: MovieToMovie\n\n");
            runExperiment(symbolGraph3, sourceMovie);
        }
    }

    /**
     * A helper method to help us run the experiment.
     *
     * @param symbolGraph
     *            A symbol graph implementation.
     */
    private static void runExperiment(DegreesOfSeparation symbolGraph, String source) {
        // Build the graph and time it
        StopwatchCPU timer = new StopwatchCPU();
        symbolGraph.createGraph();
        double currElapsed = timer.elapsedTime();
        System.out.printf("Graph created. Time elapsed: %.5f seconds\n", currElapsed);

        // Traverse the graph using BFS
        UndirectedGraph g = symbolGraph.getGraph();
        timer = new StopwatchCPU();
        symbolGraph.traverseBFS(source);
        currElapsed = timer.elapsedTime();
        System.out.printf("Graph with %d vertices & %d edges traversed using BFS.\n", g.numVertices(), g.numEdges());
        System.out.printf("Time elapsed: %.5f seconds\n", currElapsed);
        System.out.println("------------------------------------\n");

        // Build a histogram of the data
        timer = new StopwatchCPU();
        symbolGraph.createFrequencyChart();
        symbolGraph.getHistogram().report();
        currElapsed = timer.elapsedTime();
        System.out.printf("Histogram printed. Time elapsed: %.5f seconds\n\n", currElapsed);
    }
}