package com.example.mephi;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

import java.util.Scanner;

public class CryptoWithRationalNumbers {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter values:\na:");
        int a = Integer.parseInt(sc.nextLine());
        System.out.println("b:");
        int b = Integer.parseInt(sc.nextLine());
        System.out.println("r:");
        int r = Integer.parseInt(sc.nextLine());
        System.out.println("S:");
        String s = sc.nextLine();

        a = Integer.parseInt(baseConvert(String.valueOf(a), r));
        b = Integer.parseInt(baseConvert(String.valueOf(b), r));

        String expression = "solve("+ s + " - "+ r + ", x, -10000000, 10000000)";
        int x = (int)(new Expression(expression).calculate());

        String strA = String.valueOf(a);
        String strB = String.valueOf(b);
        String resultA = strA.charAt(0) + " * x * x + " + strA.charAt(1) +  " * x" + " + " + strA.charAt(2);
        String resultB = strB.charAt(0) + " * x * x + " + strB.charAt(1) +  " * x" + " + " + strB.charAt(2);
        String result = resultA + " + " + resultB;
        Argument argX = new Argument("x = " + x);
        System.out.println((int)new Expression(result.replaceAll("x", "(" + s + ")"), argX).calculate());
    }

    public static String baseConvert(String num, int destination){
        return Integer.toString(Integer.parseInt(num, 10), destination);
    }
}
