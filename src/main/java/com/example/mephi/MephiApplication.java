package com.example.mephi;

import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class MephiApplication {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        paillierCryotosystem(sc);
    }

    public static void paillierCryotosystem(Scanner sc){
        boolean check = false;
        System.out.println("Please enter values for calculation: \nMessage");
        int p = 0;
        int q = 0;
        while (!check) {
            System.out.println("p");
            p = Integer.parseInt(sc.nextLine());
            System.out.println("q");
            q = Integer.parseInt(sc.nextLine());
            if(nod(p * q, (p - 1) * (q - 1)) == 1 && isPrime(p) && isPrime(q)){
                check = true;
            }else {
                System.out.println("Not correct");
            }
        }
        int n = p * q;
        int a = nok(p - 1, q - 1);
        int g = new Random().nextInt((int)Math.pow(n,2));
        System.out.println("a - " + a);
        System.out.println("n - " + n);
        System.out.println("g - " + g);
        printResult(a, g, n, sc);
    }

    public static void printResult( int a, int g, int n, Scanner sc){
        BigInteger bigA = new BigInteger(String.valueOf(a));
        BigInteger bigG = new BigInteger(String.valueOf(g));
        BigInteger bigN = new BigInteger(String.valueOf(n));
        BigInteger bigU = bigG.pow(bigA.intValue()).mod(bigN.pow(2));
        BigInteger bigV = bigU.subtract(BigInteger.ONE).divide(bigN).modInverse(bigN);
        System.out.println("v - " + bigV);
        System.out.println("Public key - ("  + n + "," + g + ")");
        System.out.println("Secret key - ("  + a + "," + bigV + ")");
        calculateCipher(n, g, a, bigV, sc);
    }

    public static void calculateCipher(int n, int g, int a, BigInteger bigV, Scanner sc){
        boolean checker = false;
        System.out.println("Message:");
        int m = 0;
        while (!checker){
            m = Integer.parseInt(sc.nextLine());
            if(m <= n){
                checker = true;
            }else {
                System.out.println("Message should be less than n!");
            }
        }
        int r = new Random().nextInt((int)Math.pow(n,2));
        BigInteger bigR = new BigInteger(String.valueOf(r));
        BigInteger bigN = new BigInteger(String.valueOf(n));
        BigInteger bigG = new BigInteger(String.valueOf(g));
        BigInteger bigM = new BigInteger(String.valueOf(m));
        BigInteger cipherEncoded = bigG.pow(bigM.intValue()).multiply(bigR.pow(bigN.intValue())).mod(bigN.pow(2));
        System.out.println("r - " + r);
        System.out.println("Encoded message: " + cipherEncoded);
        BigInteger bigU = cipherEncoded.pow(a).mod(bigN.pow(2));
        BigInteger cipherDecoded = bigU.subtract(BigInteger.ONE)
                .divide(bigN)
                .multiply(bigV).mod(bigN);
        System.out.println("Decoded message: " + cipherDecoded);
    }

    public static boolean isPrime(int num){
        boolean flag = false;
        for (int i = 2; i <= num / 2; ++i) {
            if (num % i == 0) {
                flag = true;
                break;
            }
        }
        return !flag;
    }


    public static int nod(int a,int b){
        return b == 0 ? a : nod(b,a % b);
    }

    public static int nok(int a,int b){
        return a / nod(a,b) * b;
    }

}
