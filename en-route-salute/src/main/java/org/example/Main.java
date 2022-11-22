package org.example;

public class Main {
    public static void main(String[] args) {
        String hallway = "<<>><";

        System.out.println(solution(hallway));
    }

    private static int solution(String s) {
        int encounters = 0;

        int rightWalkers = 0;

        for (int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '>') {
                rightWalkers++;
            } else if (s.charAt(i) == '<') {
                encounters += rightWalkers * 2;
            }
        }

        return encounters;
    }
}