package org.example;

// RE-ID
// ========
// You need to generate an ID for a worker. From a string of only prime numbers “235711131719…”,
// given a number n, return the digit from the position n and the next 4 digits.
// Test cases
// ============
// Input:
// n=0
// Output:
// “23571”

// Input:
// n=3
// Output:
// “71113”
public class Main {
    public static void main(String[] args) {
        int n = 10000;
        System.out.println(solution(n));
    }

    private static String solution(int i) {
        StringBuilder stringOfPrimesBuilder = new StringBuilder();

        int n = 2;

        while(stringOfPrimesBuilder.length() <= i + 5) {
            if (isPrime(n)) {
                stringOfPrimesBuilder.append(n);
            }
            n++;
        }

        return stringOfPrimesBuilder.substring(i, i + 5);
    }

    private static boolean isPrime(int n) {
        for (int c = 2; c <= Math.sqrt(n); c++) {
            if(n % c == 0) {
                return false;
            }
        }

        return true;
    }
}