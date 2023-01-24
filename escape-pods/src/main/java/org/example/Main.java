package org.example;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.ArrayList;

// Escape Pods
// ===========
// You've blown up the LAMBCHOP doomsday device and relieved the bunnies of their work duties --
// and now you need to escape from the space station as quickly and as orderly as possible!
// The bunnies have all gathered in various locations throughout the station, and need to make
// their way towards the seemingly endless amount of escape pods positioned in other parts of the station.
// You need to get the numerous bunnies through the various rooms to the escape pods. Unfortunately,
// the corridors between the rooms can only fit so many bunnies at a time. What's more, many of the
// corridors were resized to accommodate the LAMBCHOP, so they vary in how many bunnies can move
// through them at a time.
//
// Given the starting room numbers of the groups of bunnies, the room numbers of the escape pods,
// and how many bunnies can fit through at a time in each direction of every corridor in between,
// figure out how many bunnies can safely make it to the escape pods at a time at peak.
//
// Write a function solution(entrances, exits, path) that takes an array of integers denoting where
// the groups of gathered bunnies are, an array of integers denoting where the escape pods are located,
// and an array of an array of integers of the corridors, returning the total number of bunnies that
// can get through at each time step as an int. The entrances and exits are disjoint and thus will
// never overlap. The path element path[A][B] = C describes that the corridor going from A to B can
// fit C bunnies at each time step.  There are at most 50 rooms connected by the corridors and at
// most 2000000 bunnies that will fit at a time.
//
// For example, if you have:
// entrances = [0, 1]
// exits = [4, 5]
// path = [
//  [0, 0, 4, 6, 0, 0],  # Room 0: Bunnies
//  [0, 0, 5, 2, 0, 0],  # Room 1: Bunnies
//  [0, 0, 0, 0, 4, 4],  # Room 2: Intermediate room
//  [0, 0, 0, 0, 6, 6],  # Room 3: Intermediate room
//  [0, 0, 0, 0, 0, 0],  # Room 4: Escape pods
//  [0, 0, 0, 0, 0, 0],  # Room 5: Escape pods
// ]
//
// Then in each time step, the following might happen:
// 0 sends 4/4 bunnies to 2 and 6/6 bunnies to 3
// 1 sends 4/5 bunnies to 2 and 2/2 bunnies to 3
// 2 sends 4/4 bunnies to 4 and 4/4 bunnies to 5
// 3 sends 4/6 bunnies to 4 and 4/6 bunnies to 5
//
// So, in total, 16 bunnies could make it to the escape pods at 4 and 5 at each time step.
// (Note that in this example, room 3 could have sent any variation of 8 bunnies to 4 and 5,
// such as 2/6 and 6/6, but the final solution remains the same.)
//
// Test cases
// ==========
// Input:
// Solution.solution({0, 1}, {4, 5}, {
//  {0, 0, 4, 6, 0, 0},
//  {0, 0, 5, 2, 0, 0},
//  {0, 0, 0, 0, 4, 4},
//  {0, 0, 0, 0, 6, 6},
//  {0, 0, 0, 0, 0, 0},
//  {0, 0, 0, 0, 0, 0}
// })
// Output:
//    16
//
// Input:
// Solution.solution({0}, {3}, {
//  {0, 7, 0, 0},
//  {0, 0, 6, 0},
//  {0, 0, 0, 8},
//  {9, 0, 0, 0}
// })
// Output:
//    6
// Input:
// Solution.solution({3}, {1}, {
//  {0, 0, 0, 0, 0, 0},
//  {0, 0, 5, 2, 0, 0},
//  {0, 0, 0, 0, 4, 4},
//  {0, 12, 0, 0, 0, 0},
//  {0, 0, 0, 0, 0, 0},
//  {0, 0, 0, 0, 0, 0}
// })
// Output:
//    12
// Input:
// Solution.solution({0}, {3}, {
//  {0, 7, 0, 4},
//  {0, 0, 6, 0},
//  {0, 0, 0, 8},
//  {9, 0, 0, 0}
// })
// Output:
//    10
public class Main {

    private static final int INFINITY = Integer.MAX_VALUE;

    public static void main(String[] args) {
        int[] entrances = new int[] {0,1};
        int[] exits = new int[] {4,5};
        int[][] path = new int[][] {
          {0, 0, 4, 6, 0, 0},
          {0, 0, 5, 2, 0, 0},
          {0, 0, 0, 0, 4, 4},
          {0, 0, 0, 0, 6, 6},
          {0, 0, 0, 0, 0, 0},
          {0, 0, 0, 0, 0, 0}
        };

        System.out.println(applyEdmondsKarp(createFlowNetwork(entrances, exits, path)));
    }

    private static int[][] createFlowNetwork(int[] entrances, int[] exits, int[][] path) {
        int pathDimension = path.length;
        int flowNetDimension = pathDimension + 2;
        int[][] flowNetwork = new int[flowNetDimension][flowNetDimension];

        for (int i = 0; i < pathDimension; i++) {
            for (int j = 0; j < pathDimension; j++) {
                flowNetwork[i + 1][j + 1] = path[i][j];
            }
        }

        for (int entrance : entrances) {
            flowNetwork[0][entrance + 1] = INFINITY;
        }

        for (int exit : exits) {
            flowNetwork[exit + 1][flowNetDimension - 1] = INFINITY;
        }

        return flowNetwork;
    }

    private static int applyEdmondsKarp(int[][] flowNetwork) {
        int maxFlow = 0;
        List<Integer> augPath;

        while ((augPath = bfs(flowNetwork)) != null) {
            int bottleneck = INFINITY;
            int x = 0;

            for (int y : augPath) {
                bottleneck = Math.min(bottleneck, flowNetwork[x][y]);
                x = y;
            }

            maxFlow += bottleneck;
            x = 0;

            for (int y : augPath) {
                flowNetwork[x][y] -= bottleneck;
                x = y;
            }
        }

        return maxFlow;
    }

    private static List<Integer> bfs(int[][] residualNetwork) {
        int[] parentNodes = new int[residualNetwork.length];

        for (int i = 0; i < parentNodes.length; i++) {
            parentNodes[i] = -1;
        }

        Queue<Integer> q = new LinkedList<>();
        q.add(0);

        while (!q.isEmpty()) {
            Integer x = q.poll();

            for (int y = 0; y < residualNetwork.length; y++) {
                if (residualNetwork[x][y] > 0 && parentNodes[y] == -1) {
                    q.add(y);
                    parentNodes[y] = x;
                }
            }
        }

        List<Integer> augmentedPath = new ArrayList<>(parentNodes.length);
        int node = parentNodes[parentNodes.length - 1];

        while (node != 0) {
            if (node == -1) return null;

            augmentedPath.add(node);
            node = parentNodes[node];
        }

        Collections.reverse(augmentedPath);

        return augmentedPath;
    }
}
