package org.shortest_path_problem.omsu;

import org.shortest_path_problem.omsu.data_types.PathAndDistance;
import org.shortest_path_problem.omsu.utilities.GraphInput;
import org.shortest_path_problem.omsu.utilities.VertexExistenceCheck;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static java.lang.System.exit;

public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Shortest Path Finder!");
        int appMode = -1;
        Scanner sc = new Scanner(System.in);
        boolean correctInput = false;
        while (appMode != 0) {
            appMode = -1;
            while (!(correctInput) || !(appMode == 1 || appMode == 2 || appMode == 0)) {
                System.out.println();
                System.out.println("1 - start program");
                System.out.println("2 - open Input.txt");
                System.out.println("0 - exit");
                System.out.print("Select a mode: ");
                if (sc.hasNextInt()) {
                    appMode = sc.nextInt();
                    correctInput = true;
                } else {
                    sc.nextLine();
                }
            }
            File file = new File("Input.txt");
            if (!file.exists()) { file.createNewFile(); }
            switch (appMode) {
                case 1:
                    int inputMode = 0;
                    int s = -1, t = -1;
                    int algorithm = 0;
                    double[][] graphWeightMatrix = new double[0][0];
                    PathAndDistance output;
                    System.out.println();
                    correctInput = false;
                    while (!(correctInput) || !(inputMode == 1 || inputMode == 2)) {
                        System.out.print("How to input weight matrix of graph? (1 - manually, 2 - randomly): ");
                        if (sc.hasNextInt()) {
                            inputMode = sc.nextInt();
                            correctInput = true;
                        } else {
                            sc.nextLine();
                        }
                    }
                    if (inputMode == 1) {
                        file.setWritable(true);
                        java.awt.Desktop.getDesktop().edit(file);
                        System.out.print("Confirm your input by pressing Enter key...");
                        System.in.read();
                        graphWeightMatrix = GraphInput.fileInput();
                    } else {
                        correctInput = false;
                        while (!(correctInput)) {
                            System.out.print("How many vertices are there in the graph? : ");
                            if (sc.hasNextInt()) {
                                file.setWritable(true);
                                graphWeightMatrix = GraphInput.randomInput(sc.nextInt());
                                correctInput = true;
                                file.setWritable(false);
                                java.awt.Desktop.getDesktop().open(file);
                            } else {
                                sc.nextLine();
                            }
                        }
                    }
                    while (!VertexExistenceCheck.graphContains(graphWeightMatrix, s)) {
                        correctInput = false;
                        while (!(correctInput)) {
                            System.out.print("Input start of the path (number of vertices 0 to n-1): ");
                            if (sc.hasNextInt()) {
                                s = sc.nextInt();
                                correctInput = true;
                            } else {
                                sc.nextLine();
                            }
                        }
                    }
                    while (!VertexExistenceCheck.graphContains(graphWeightMatrix, t)) {
                        correctInput = false;
                        while (!(correctInput)) {
                            System.out.print("Input finish of the path (number of vertices 0 to n-1): ");
                            if (sc.hasNextInt()) {
                                t = sc.nextInt();
                                correctInput = true;
                            } else {
                                sc.nextLine();
                            }
                        }
                    }
                    correctInput = false;
                    while (!(correctInput) || !(algorithm == 1 || algorithm == 2 || algorithm == 3)) {
                        System.out.print("Choose the algorithm (1 - Dijkstra, 2 - Bellman-Ford, 3 - Floyd-Warshall): ");
                        if (sc.hasNextInt()) {
                            algorithm = sc.nextInt();
                            correctInput = true;
                        } else {
                            sc.nextLine();
                        }
                    }
                    System.out.println("Processing...");
                    try {
                        output = switch (algorithm) {
                            case 1 -> Path.pathForDijkstra(s, t, graphWeightMatrix);
                            case 2 -> Path.pathForBellmanFord(s, t, graphWeightMatrix);
                            case 3 -> Path.pathForFloydWarshall(s, t, graphWeightMatrix);
                            default -> throw new IllegalStateException("Unexpected value: " + algorithm);
                        };
                        System.out.println("Path: " + output.getPath().toString() + ", distance: " + output.getDistance());
                    }
                    catch (Exception e) {
                        System.out.println("Error occurred. Try again, please");
                    }
                    break;
                case 2:
                    file.setWritable(false);
                    java.awt.Desktop.getDesktop().open(file);
                    break;
            }
        }
        exit(0);
    }
}
