package org.example;

//Gearing Up for Destruction
//==========================
//
//As Commander Lambda's personal assistant, you've been assigned the task of configuring the LAMBCHOP
//doomsday device's axial orientation gears. It should be pretty simple -- just add gears to create
//the appropriate rotation ratio. But the problem is, due to the layout of the LAMBCHOP and the complicated
//system of beams and pipes supporting it, the pegs that will support the gears are fixed in place.
//
//The LAMBCHOP's engineers have given you lists identifying the placement of groups of pegs along various
//support beams. You need to place a gear on each peg (otherwise the gears will collide with unoccupied pegs).
//The engineers have plenty of gears in all different sizes stocked up, so you can choose gears of any size,
//from a radius of 1 on up. Your goal is to build a system where the last gear rotates at twice the rate
//(in revolutions per minute, or rpm) of the first gear, no matter the direction. Each gear (except the last)
//touches and turns the gear on the next peg to the right.
//
//Given a list of distinct positive integers named pegs representing the location of each peg along the support
//beam, write a function solution(pegs) which, if there is a solution, returns a list of two positive integers
//a and b representing the numerator and denominator of the first gear's radius in its simplest form in order
//to achieve the goal above, such that radius = a/b. The ratio a/b should be greater than or equal to 1.
//Not all support configurations will necessarily be capable of creating the proper rotation ratio, so if the task
//is impossible, the function solution(pegs) should return the list [-1, -1].
//
//For example, if the pegs are placed at [4, 30, 50], then the first gear could have a radius of 12, the second
//gear could have a radius of 14, and the last one a radius of 6. Thus, the last gear would rotate twice as fast
//as the first one. In this case, pegs would be [4, 30, 50] and solution(pegs) should return [12, 1].
//
//The list pegs will be given sorted in ascending order and will contain at least 2 and no more than 20 distinct
//positive integers, all between 1 and 10000 inclusive.
//
//Test cases
//==========
//
//-- Java cases --
//Input:
//Solution.solution({4, 17, 50})
//Output:
//-1,-1
//
//Input:
//Solution.solution({4, 30, 50})
//Output:
//12,1
public class Main {
    private static int[] SOLUTION_DNE = new int[]{-1,-1};

    public static void main(String[] args) {
        //2, 40, 77, 100

        int[] pegs = new int[]{3333,6666};

        long start = System.nanoTime();

        int[] solution = correctSolution(pegs);

        long end = System.nanoTime();
        double executionTime = (end - start) * 1e-9;

        System.out.println("Execution time in seconds: " + executionTime);
        System.out.println(solution[0] + "," + solution[1]);
    }

    private static int[] bruteForceSolution(int[] pegs) {
        int[] solution = new int[] {-1,-1};

        int maxFirstGearRadius = pegs[1] - pegs[0];

        for (double a = 2; a <= maxFirstGearRadius*2; a++) {
            for (double b = 1; b < a; b++) {

                double radius = a/b;
                if (placeGears(radius, pegs)) {
                 solution[0] = (int)a;
                 solution[1] = (int)b;
                 return solution;
                }
            }
        }

        return solution;
    }
    private static boolean placeGears(double firstGearRadius, int[] pegs) {

        double radius = firstGearRadius;

        for (int i = 0; i < pegs.length - 1; i++) {
            double placedGear = (pegs[i] + radius);

            if (placedGear < pegs[i+1]) {
                radius = pegs[i+1] - placedGear;
            } else {
                return false;
            }
        }

        return Math.abs((firstGearRadius / 2) - radius) < 0.000000001;
    }

    private static int[] correctSolution(int[] pegs) {
        int[] solution = new int[2];
        int numberOfPegs = pegs.length;

        if (numberOfPegs < 2)
            return SOLUTION_DNE;

        boolean isEven = pegs.length % 2 == 0;

        int sum;

        if (isEven)
            sum = -pegs[0] + pegs[numberOfPegs - 1];
        else
            sum = -pegs[0] - pegs[numberOfPegs - 1];

        if (numberOfPegs > 2) {
            for (int i = 1; i < numberOfPegs - 1; i++) {
                sum += Math.pow(-1, i+1) * 2 * pegs[i];
            }
        }

        solution[0] = sum * 2;

        if (isEven) {
            if (solution[0] % 3 == 0) {
                solution[0] /= 3;
                solution[1] = 1;
            } else {
                solution[1] = 3;
            }
        } else {
            solution[1] = 1;
        }

        double firstGearRadius = solution[0]/solution[1];

        // last gear's radius has to be >= 1, which means
        // first gear's radius has to be >= 2
        if (firstGearRadius < 2)
            return SOLUTION_DNE;

        double currentRadius = firstGearRadius;

        for (int i = 0; i < numberOfPegs - 2; i++) {
            double nextRadius = pegs[i+1] - (pegs[i] + currentRadius);
            if (currentRadius < 1 || nextRadius < 1) {
                return SOLUTION_DNE;
            } else {
                currentRadius = nextRadius;
            }
        }

        return solution;
    }
}