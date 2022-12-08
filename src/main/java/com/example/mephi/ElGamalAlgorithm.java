package com.example.mephi;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ElGamalAlgorithm {
    private final static int k = 4;

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();
        System.out.println("Please type as P one of this prime numbers: ");

        for (int n = 1; n < 100; n++) {
            if (isPrime(n, k)) {
                list.add(n);
                System.out.print(n + " ");
            }
        }

        Scanner sc = new Scanner(System.in);
        int userInputP = 0;
        String str = "\nP:";
        while(!isPrime(userInputP,k)) {
            System.out.println(str);
            userInputP = sc.nextInt();
            str = "Error not prime number";
        }

        System.out.println("Please type a message(M): ");
        int userInputM = Integer.MAX_VALUE;
        str = "M:";
        while(userInputM > userInputP) {
            System.out.println(str);
            userInputM = sc.nextInt();
            str = "Error M greater than P";
        }

        int userInputG = Integer.MAX_VALUE;
        str = "G should be less than P:";
        while(userInputG > userInputP) {
            System.out.println(str);
            userInputG = sc.nextInt();
            str = "Error g grater than p";
        }

        System.out.println("Enter X value should be grater than 1 and less than P - 1 ");
        int randomX = 0;
        str = "X:";
        while (randomX < 1
        || randomX > userInputP - 1) {
            System.out.println(str);
            randomX = sc.nextInt();
            str = "Error value should be grater than 1 and less than P - 1";
        }

        BigInteger p = new BigInteger(String.valueOf(userInputP));
        BigInteger g = new BigInteger(String.valueOf(userInputG));
        BigInteger x = new BigInteger(String.valueOf(randomX));
        BigInteger y = g.pow(x.intValue()).mod(p);
        System.out.println("Calculated value Y:\n" + y);

        System.out.println("Enter K value should be grater than 1 and less than P - 1 ");
        int randomK = 0;
        str = "K:";
        while (randomK < 1
            || randomK > userInputP - 1) {
            System.out.println(str);
            randomK = sc.nextInt();
            str = "Error value should be grater than 1 and less than P - 1";
        }

        BigInteger k = new BigInteger(String.valueOf(randomK));
        BigInteger M = new BigInteger(String.valueOf(userInputM));
        BigInteger a = g.pow(k.intValue()).mod(p);
        System.out.println("Calculated value a:\n" + a);
        BigInteger b = y.pow(k.intValue()).multiply(M).mod(p);
        System.out.println("Calculated value b:\n" + b);
        BigInteger decryptAAndB = b.multiply(a.pow(p.intValue() - 1 - x.intValue())).mod(p);
        System.out.println("So we decrypt a and b:\n" + decryptAAndB);

    }

    public static int getRandomNumberUsingInts(int min, int max) {
        Random random = new Random();
        return random.ints(min, max)
            .findFirst()
            .getAsInt();
    }

    static int power(int x, int y, int p) {

        int res = 1;
        x = x % p;

        while (y > 0) {

            if ((y & 1) == 1)
                res = (res * x) % p;

            y = y >> 1; // y = y/2
            x = (x * x) % p;
        }

        return res;
    }

    static boolean miillerTest(int d, int n) {

        int a = 2 + (int)(Math.random() % (n - 4));

        int x = power(a, d, n);

        if (x == 1 || x == n - 1)
            return true;

        while (d != n - 1) {
            x = (x * x) % n;
            d *= 2;

            if (x == 1)
                return false;
            if (x == n - 1)
                return true;
        }

        return false;
    }

    static boolean isPrime(int n, int k) {

        // Corner cases
        if (n <= 1 || n == 4)
            return false;
        if (n <= 3)
            return true;

        int d = n - 1;

        while (d % 2 == 0)
            d /= 2;

        for (int i = 0; i < k; i++)
            if (!miillerTest(d, n))
                return false;

        return true;
    }

}
