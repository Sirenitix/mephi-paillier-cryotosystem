package com.example.mephi;

import java.math.BigInteger;
import java.util.Scanner;

public class DiffiHellman {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter p:");
        BigInteger p = new BigInteger(sc.next());
        System.out.println("Please enter g:");
        BigInteger g = new BigInteger(sc.next());
        System.out.println("Alice(A):");
        BigInteger a = new BigInteger(sc.next());
        System.out.println("Bob(B):");
        BigInteger b = new BigInteger(sc.next());
        BigInteger aliceA = g.pow(a.intValue()).mod(p);
        BigInteger bobB = g.pow(b.intValue()).mod(p);
        System.out.println("Alice A " + aliceA.intValue());
        System.out.println("Bob B " + bobB.intValue());
        BigInteger s1 = aliceA.pow(b.intValue()).mod(p);
        BigInteger s2 = bobB.pow(a.intValue()).mod(p);
        System.out.println("sAlice " + s1.intValue());
        System.out.println("sBob " + s2.intValue());
    }
}
