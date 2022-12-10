package org.example;

// Find the Access Codes
// =====================
// In order to destroy Commander Lambda's LAMBCHOP doomsday device, you'll need access to it.
// But the only door leading to the LAMBCHOP chamber is secured with a unique lock system whose
// number of passcodes changes daily. Commander Lambda gets a report every day that includes the
// locks' access codes, but only the Commander knows how to figure out which of several lists
// contains the access codes. You need to find a way to determine which list contains the access
// codes once you're ready to go in.
//
// Fortunately, now that you're Commander Lambda's personal assistant, Lambda has confided to you
// that all the access codes are "lucky triples" in order to make it easier to find them in the lists.
// A "lucky triple" is a tuple (x, y, z) where x divides y and y divides z, such as (1, 2, 4).
// With that information, you can figure out which list contains the number of access codes that matches
// the number of locks on the door when you're ready to go in (for example, if there's 5 passcodes,
// you'd need to find a list with 5 "lucky triple" access codes).
//
// Write a function solution(int[] l) that takes a list of positive integers l and counts the number
// of "lucky triples" of (li, lj, lk) where the list indices meet the requirement i < j < k.  The length
// of l is between 2 and 2000 inclusive.  The elements of l are between 1 and 999999 inclusive.  The solution
// fits within a signed 32-bit integer. Some of the lists are purposely generated without any access codes
// to throw off spies, so if no triples are found, return 0.
//
// For example, [1, 2, 3, 4, 5, 6] has the triples: [1, 2, 4], [1, 2, 6], [1, 3, 6], making the solution 3 total.
//
// Test cases
// ==========
// Input:
// Solution.solution([1, 1, 1])
// Output:
// 1
//
// Input:
// Solution.solution([1, 2, 3, 4, 5, 6])
// Output:
// 3

public class Main {
    public static void main(String[] args) {
        int[] l = new int[] {1, 1, 1};

        System.out.println(n2Solution(l));
    }

    private static int n2Solution(int[] l) {
        int numberOfAccessCodes = 0;
        int[] numberOfPairs = new int[l.length];

        if (l.length == 2) return numberOfAccessCodes;

        for (int i = 0; i < l.length; i++) {
            for (int j = 0; j < i; j++) {
                if (l[i] % l[j] == 0) {
                    numberOfPairs[i]++;
                    numberOfAccessCodes += numberOfPairs[j];
                }
            }
        }

        return numberOfAccessCodes;
    }

    private static int n3Solution(int[] l) {
        int numberOfAccessCodes = 0;

        if (l.length == 2) return numberOfAccessCodes;

        for (int i = 0; i <= l.length - 3; i++) {
            for (int j = i + 1; j <= l.length - 2; j++) {
                if (l[j] % l[i] == 0) {
                    for (int k = j + 1; k <= l.length - 1; k++) {
                        if (l[k] % l[j] == 0) {
                            numberOfAccessCodes++;
                        }
                    }
                }
            }
        }

        return numberOfAccessCodes;
    }
}