package org.example;

// Prepare the Bunnies’ Escape
// ====================================
// You're awfully close to destroying the LAMBCHOP doomsday device and freeing Commander Lambda's
// bunny workers, but once they're free of the work duties the bunnies are going to need to escape
// Lambda's space station via the escape pods as quickly as possible. Unfortunately, the halls of
// the space station are a maze of corridors and dead ends that will be a deathtrap for the
// escaping bunnies. Fortunately, Commander Lambda has put you in charge of a remodeling project that
// will give you the opportunity to make things a little easier for the bunnies. Unfortunately (again),
// you can't just remove all obstacles between the bunnies and the escape pods - at most you can
// remove one wall per escape pod path, both to maintain structural integrity of the station and
// to avoid arousing Commander Lambda's suspicions.
//
// You have maps of parts of the space station, each starting at a work area exit and ending at
// the door to an escape pod. The map is represented as a matrix of 0s and 1s, where 0s are passable
// space and 1s are impassable walls. The door out of the station is at the top left (0,0) and the
// door into an escape pod is at the bottom right (w-1,h-1).
//
// Write a function solution(map) that generates the length of the shortest path from the station door
// to the escape pod, where you are allowed to remove one wall as part of your remodeling plans.
// The path length is the total number of nodes you pass through, counting both the entrance and exit nodes.
// The starting and ending positions are always passable (0). The map will always be solvable, though
// you may or may not need to remove a wall. The height and width of the map can be from 2 to 20. Moves
// can only be made in cardinal directions; no diagonal moves are allowed.
//
// Test cases
// ==========
// Input:
// Solution.solution({
// {0, 1, 1, 0},
// {0, 0, 0, 1},
// {1, 1, 0, 0},
// {1, 1, 1, 0}
// })
// Output:
// 7
//
// Input:
// Solution.solution({
// {0, 0, 0, 0, 0, 0},
// {1, 1, 1, 1, 1, 0},
// {0, 0, 0, 0, 0, 0},
// {0, 1, 1, 1, 1, 1},
// {0, 1, 1, 1, 1, 1},
// {0, 0, 0, 0, 0, 0}
// })
// Output:
// 11

// {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
// {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
// {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
// {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
// {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
// {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
// {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
// {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
// {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
// {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
// {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
// {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
// {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
// {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
// {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
// {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
// {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
// {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
// {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
// {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
// program's output: 153


import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        int[][] map = new int[][] {
                {0,0,0,0,0,0,1},
                {0,0,0,0,0,1,0},
                {0,0,0,0,1,0,0},
                {0,0,0,1,0,0,0},
                {0,0,1,0,0,0,0},
                {0,1,0,0,0,0,0},
                {1,0,0,0,0,0,0},
        };

        long start = System.nanoTime();
        int shortestPathLength = solution(map);
        long end = System.nanoTime();

        double execT = (end - start) * 1e-9;

        System.out.println(shortestPathLength);
        System.out.println("Execution time in seconds: " + execT);
    }

    private static int solution(int[][] map) {
        int mapW = map[0].length;
        int mapH = map.length;

        Queue<Tile> tilesToVisit = new LinkedList<>();

        tilesToVisit.add(new Tile(0, 0, 1, false));

        while (!tilesToVisit.isEmpty()) {
            Tile currentTile = tilesToVisit.poll();

            map[currentTile.x][currentTile.y] = currentTile.value;

            if (currentTile.getX() == mapH -1 && currentTile.getY() == mapW -1) {
                continue;
            }

            checkTile(map, currentTile.x + 1, currentTile.y, currentTile.getValue(), currentTile.hasWallBeenBroken(), tilesToVisit);
            checkTile(map, currentTile.x, currentTile.y + 1, currentTile.getValue(), currentTile.hasWallBeenBroken(), tilesToVisit);
            checkTile(map, currentTile.x - 1, currentTile.y, currentTile.getValue(), currentTile.hasWallBeenBroken(), tilesToVisit);
            checkTile(map, currentTile.x, currentTile.y -1, currentTile.getValue(), currentTile.hasWallBeenBroken(), tilesToVisit);
        }

        return map[mapH-1][mapW-1];
    }

    private static void checkTile(int[][] map, int x, int y, int prevTileValue, boolean hasWallBeenBroken, Queue<Tile> tilesToVisit) {
        int mapW = map[0].length;
        int mapH = map.length;

        if (x == -1 || x == mapH || y == -1 || y == mapW) return;

        if (map[x][y] == 0) {
            tilesToVisit.add(new Tile(x, y, prevTileValue + 1, hasWallBeenBroken));
        } else if (map[x][y] == 1 && !(x == 0 && y == 0) && !hasWallBeenBroken) {
            tilesToVisit.add(new Tile(x, y, prevTileValue + 1, true));
        } else if (map[x][y] >= prevTileValue) {
            tilesToVisit.add(new Tile(x, y, prevTileValue + 1, hasWallBeenBroken));
        }
    }

    public static class Tile {
        private final int x;
        private final int y;
        private final int value;
        private final boolean hasWallBeenBroken;

        public Tile(int x, int y, int value, boolean hasWallBeenBroken) {
            this.x = x;
            this.y = y;
            this.value = value;
            this.hasWallBeenBroken = hasWallBeenBroken;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public int getValue() {
            return this.value;
        }

        public boolean hasWallBeenBroken() {
            return this.hasWallBeenBroken;
        }
    }
}