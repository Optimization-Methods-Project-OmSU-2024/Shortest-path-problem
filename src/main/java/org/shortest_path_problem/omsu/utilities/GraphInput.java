package org.shortest_path_problem.omsu.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GraphInput {
    public static double[][] randomInput(int size) {
        try(FileWriter writer = new FileWriter("Input.txt")){
            double[][] graph = new double[size][size];
            Random rand = new Random();
            double probability = 0.15;

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    double realEvent = rand.nextDouble();
                    if(i==j){
                        graph[i][j] = 0;
                        writer.write(graph[i][j] + " ");
                    }
                    else if(realEvent <= probability){
                        graph[i][j] = Double.POSITIVE_INFINITY;
                        writer.write("inf ");
                    }
                    else{
                        graph[i][j] = (double) Math.round((-100 + rand.nextDouble() * 200) * 10) / 10;
                        writer.write(graph[i][j] + " ");
                    }
                }
                writer.write('\n');
            }
            writer.close();
            return graph;
        }
        catch (IOException e) {
            System.out.println("File doesn't exist");
            return new double[0][0];
        }
    }

    public static double[][] fileInput() {
        Scanner scanner = null;

        class NotZeroWeightsException extends Exception{}

        try{
            scanner = new Scanner(new File("Input.txt"));
            ArrayList<String> lines = new ArrayList<>();
            int size = 0;
            String line;

            if(scanner.hasNextLine()){
                line = scanner.nextLine();
                lines.add(line);
                size = line.split(" ").length;
            }
            while(scanner.hasNextLine()){
                line = scanner.nextLine();
                lines.add(line);
                if(line.split(" ").length != size){
                    throw new ArrayIndexOutOfBoundsException();
                }
            }
            if(lines.size() != size){ //check is the size NxN
                throw new ArrayIndexOutOfBoundsException();
            }

            int i = 0;
            double[][] graph = new double[size][size];
            for(String l: lines){
                String[] el= l.split(" ");
                for (int j = 0; j < size; j++) {
                    if(!el[j].equals("inf")){
                        graph[i][j] = Double.parseDouble(el[j]);
                        if(i==j && graph[i][j]!=0){
                            throw new NotZeroWeightsException();
                        }
                    }
                    else{
                        graph[i][j] = Double.POSITIVE_INFINITY;
                    }
                }
                i++;
            }

            scanner.close();
            return graph;
        }
        catch (FileNotFoundException e){
            System.out.println("File doesn't exist");
            return null;
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("You should enter the square matrix");
            assert scanner != null;
            scanner.close();
            return null;
        }
        catch (NumberFormatException e) {
            System.out.println("You entered the wrong type of data. You possible to enter the numbers or inf (for infinite)");
            assert scanner != null;
            scanner.close();
            return null;
        }
        catch (NotZeroWeightsException e){
            System.out.println("There should be only zero weights for edges that have the start and the end in one point");
            scanner.close();
            return null;
        }
    }
}
