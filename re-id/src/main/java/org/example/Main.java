package org.example;

public class Main {
    public static void main(String[] args) {
        //n 0-10000
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