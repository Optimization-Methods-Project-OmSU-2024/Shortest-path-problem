package org.shortest_path_problem.omsu;

import org.shortest_path_problem.omsu.data_types.PointersAndDistances;
import org.shortest_path_problem.omsu.data_types.PointersAndDistancesMatrices;

import java.util.HashSet;

public class Algorithms {
    public static PointersAndDistances dijkstra(int s, double[][] graphWeightMatrix) {
        class NegativeWeightException extends Exception{}
        try {
            int size = graphWeightMatrix[0].length;

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if(graphWeightMatrix[i][j]<0){
                        throw new NegativeWeightException();
                    }
                }
            }

            double[] d = new double[size];
            int[] p = new int[size];
            HashSet<Integer> usedPoints = new HashSet<>(size);

            for (int i = 0; i < size; i++) {
                d[i] = graphWeightMatrix[s][i];
                p[i] = s;
            }
            d[s]=0;
            usedPoints.add(s);

            int u = 0;
            for (int i = 0; i < size - 1; i++) {
                double min = 0;
                for (int j = 0; j < size; j++) {
                    if(!usedPoints.contains(j)){
                        min = d[j];
                        u=j;
                    }
                }
                for (int j = 0; j < size; j++) {
                    if (d[j] < min && !usedPoints.contains(j)) {
                        min = d[j];
                        u = j;
                    }
                }

                usedPoints.add(u);

                for (int v = 0; v < size; v++) {
                    if(d[u]+graphWeightMatrix[u][v]<d[v] && !usedPoints.contains(v)){
                        d[v] = d[u]+graphWeightMatrix[u][v];
                        p[v] = u;
                    }
                }
            }

            return new PointersAndDistances(p,d);
        }
        catch (NegativeWeightException e){
            System.out.println("ERROR: Dijkstra algorithm only works with positive-weight graphs");
            return null;
        }
    }

    public static PointersAndDistances bellmanFord(int s, double[][] graphWeightMatrix) {
        int n = graphWeightMatrix[0].length;
        int[] p = new int[n];
        double[] d = new double[n];
        d[s] = 0;
        p[s] = s;
        for (int v = 0; v < n; v++) {
            if (v != s) {
                d[v] = graphWeightMatrix[s][v];
                p[v] = s;
            }
        }
        for (int k = 0; k < n-2; k++) {
            for (int v = 0; v < n; v++) {
                for (int u = 0; u < n; u++) {
                    if (d[u] + graphWeightMatrix[u][v] < d[v]) {
                        d[v] = d[u] + graphWeightMatrix[u][v];
                        p[v] = u;
                    }
                }
            }
        }
        return new PointersAndDistances(p, d);
    }

    public static PointersAndDistancesMatrices floydWarshall(double[][] graphWeightMatrix) {
            double[][] matrixWeight= new double [graphWeightMatrix.length][graphWeightMatrix[0].length];
            int[][] matrixIndex= new int [matrixWeight.length][matrixWeight[0].length];
            for (int i = 0; i < matrixWeight.length; i++){
                for (int j = 0; j < matrixWeight[0].length; j++){
                    matrixWeight[i][j] = graphWeightMatrix[i][j];
                    matrixIndex[i][j] = i;
                }
            }
            for (int i = 0; i < matrixWeight.length; i++){
                matrixWeight[i][i] = 0;
            }
            for (int k = 0; k < matrixWeight.length; k++){
                for (int i = 0; i < matrixWeight.length; i++){
                    for (int j = 0; j < matrixWeight.length; j++) {
                        if (matrixWeight[i][j] > matrixWeight[i][k] + matrixWeight[k][j]){
                            matrixWeight[i][j] = matrixWeight[i][k] + matrixWeight[k][j];
                            if(matrixIndex[k][j] == i) matrixIndex[i][j] = k;
                            else matrixIndex[i][j] = matrixIndex[k][j];
                        }
                    }
                }
            }

            return new PointersAndDistancesMatrices(matrixIndex, matrixWeight);
    }
}
