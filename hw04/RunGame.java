/*
 * RunGame.java
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
package wpialgs.hw04;

import edu.princeton.cs.algs4.StopwatchCPU;
import java.io.File;
import java.util.Date;
import java.util.Scanner;
import wpialgs.hw04.separation.ActorToMovieDegreesOfSeparation;
import wpialgs.sixdegrees.graphs.UndirectedGraph;
import wpialgs.sixdegrees.separation.DegreesOfSeparation;

/**
 * Runs the Oracle of Bacon game using the {@link ActorToMovieDegreesOfSeparation} symbol graph.
 */
public class RunGame {

    /**
     * The main entry point to the program.
     *
     * @param args
     *            Command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("USAGE: java RunGame <filename>");
        } else {
            // Welcome message
            Date today = new Date();
            System.out.println("Welcome to CS 2223 - Oracle of Bacon!");
            System.out.printf("\tTODAY: %s (%d)\n", today, today.getTime());
            System.out.printf("\tInput File: %s\n", args[0]);
            System.out.println("------------------------------------");

            // Run the game until we stop
            Scanner sc = new Scanner(System.in);
            String stop = "n";
            do {
                // Prompt for the source actor
                System.out.print("Enter the source actor: ");
                String source = sc.nextLine().replaceAll("\n", "");
                System.out.println();

                // Use the Actor-Movie Degrees of Separation
                DegreesOfSeparation symbolGraph = new ActorToMovieDegreesOfSeparation(source);
                symbolGraph.readFile(new File(args[0]), "\t");
                symbolGraph.createGraph();
                UndirectedGraph g = symbolGraph.getGraph();

                StopwatchCPU timer = new StopwatchCPU();
                symbolGraph.traverseBFS(source);
                double currElapsed = timer.elapsedTime();
                System.out.printf("Graph with %d vertices & %d edges traversed using BFS.\n", g.numVertices(),
                        g.numEdges());
                System.out.printf("Time elapsed: %.5f seconds\n", currElapsed);
                System.out.println("------------------------------------\n");

                // Print the histogram
                symbolGraph.createFrequencyChart();
                symbolGraph.getHistogram().report();
                System.out.println();

                // Print the Hollywood number
                System.out.printf("Hollywood number: %.5f\n\n", symbolGraph.computeHollywoodNumber());

                // Prompt for new destination actor until we quit
                String dest;
                do {
                    System.out.print("Enter the destination actor or type quit to finish: ");
                    dest = sc.nextLine().replaceAll("\n", "");
                    System.out.println();

                    if (!dest.equalsIgnoreCase("quit")) {
                        System.out.println(symbolGraph.chainAsString(dest));
                    }
                } while (!dest.equalsIgnoreCase("quit"));

                // Check to see if we want to keep playing
                System.out.print("Continue playing (y/n)?: ");
                stop = sc.nextLine().replaceAll("\n", "");
                System.out.println("\n------------------------------------");
            } while (!stop.equalsIgnoreCase("n"));
        }
    }
}