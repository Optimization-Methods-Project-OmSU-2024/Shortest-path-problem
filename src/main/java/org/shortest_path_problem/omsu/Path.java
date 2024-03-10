package org.shortest_path_problem.omsu;

import org.shortest_path_problem.omsu.data_types.PathAndDistance;
import org.shortest_path_problem.omsu.data_types.PointersAndDistances;
import org.shortest_path_problem.omsu.data_types.PointersAndDistancesMatrices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.shortest_path_problem.omsu.Algorithms.bellmanFord;
import static org.shortest_path_problem.omsu.Algorithms.dijkstra;

public class Path {
    public static PathAndDistance pathForDijkstra(int s, int t, double[][] graphWeightMatrix) {
        List<Integer> pathList = new ArrayList<>(graphWeightMatrix[0].length);
        PointersAndDistances pointersAndDistances = dijkstra(s, graphWeightMatrix);
        int v = t;
        pathList.add(v);
        while(v!=s) {
            int u = pointersAndDistances.getPointers()[v];
            pathList.add(u);
            v = u;
        }
        Collections.reverse(pathList);
        double distance = pointersAndDistances.getDistances()[t];
        return new PathAndDistance(pathList, distance);
    }

    public static PathAndDistance pathForBellmanFord(int s, int t, double[][] graphWeightMatrix) {
        List<Integer> pathList = new ArrayList<>(graphWeightMatrix[0].length);
        PointersAndDistances pointersAndDistances = bellmanFord(s, graphWeightMatrix);
        int v = t;
        pathList.add(v);
        while(v!=s) {
            int u = pointersAndDistances.getPointers()[v];
            pathList.add(u);
            v = u;
        }
        Collections.reverse(pathList);
        double distance = pointersAndDistances.getDistances()[t];
        return new PathAndDistance(pathList, distance);
    }

    public static PathAndDistance pathForFloydWarshall(int s, int t, double[][] graphWeightMatrix) {
        PointersAndDistancesMatrices matrices = Algorithms.floydWarshall(graphWeightMatrix);
        List<Integer> path = new ArrayList<>(graphWeightMatrix[0].length);
        double distance = matrices.getDistances()[s][t];
        path.add(t);
        while(s != t) {
            t = matrices.getPointers()[s][t];
            path.add(t);
        }
        Collections.reverse(path);
        return new PathAndDistance(path, distance);
    }

}
