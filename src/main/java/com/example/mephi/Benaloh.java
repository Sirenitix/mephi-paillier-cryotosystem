package com.example.mephi;


import java.math.BigInteger;

public class Benaloh {

//    key generation

    public class PublicKey {
        private BigInteger n, y;

        public PublicKey(BigInteger n, BigInteger y) {
            this.n = n;
            this.y = y;
        }

        public BigInteger getN() {
            return n;
        }

        public BigInteger getY() {
            return y;
        }
    }
//private key

    public class PrivateKey {
        private BigInteger x, phi;

        public PrivateKey(BigInteger phi, BigInteger x) {
            this.phi = phi;
            this.x = x;
        }

        public BigInteger getPhi() {
            return phi;
        }

        public BigInteger getX() {
            return x;
        }
    }

    private PublicKey pubkey;
    private PrivateKey prikey;
    public BigInteger R;


    public void keyGeneration() {

        BigInteger p, q, p_minus_one, q_minus_one, R;
        R = BigInteger.valueOf(29);
        this.R = R;

        do {
            p = BigInteger.valueOf(59);
            p_minus_one = p.subtract(BigInteger.ONE);
        } while (p_minus_one.mod(this.R).intValue() != 0
            || p_minus_one.divide(R).gcd(R).intValue() != 1);

        do {
            q = BigInteger.valueOf(17);
            q_minus_one = q.subtract(BigInteger.ONE);
        } while (p.compareTo(q) == 0 || q_minus_one.gcd(R).intValue() != 1);

        BigInteger n = p.multiply(q);
        BigInteger phi = p_minus_one.multiply(q_minus_one);

//        Now we have p and q and r with all conditions
        BigInteger y = itemZStarN(n, phi, R);
        prikey = new PrivateKey(phi, y.modPow(phi.divide(R), n));
        pubkey = new PublicKey(n, y);

    }

    public BigInteger encrypt(int m) {
        BigInteger u = randomZStarN(this.pubkey.getN());
        BigInteger cipher1 = pubkey.getY().modPow(BigInteger.valueOf(m), pubkey.getN());
        BigInteger cipher2 = u.modPow(this.R, pubkey.getN());
        return cipher1.multiply(cipher2).mod(pubkey.getN());
    }

    public int decrypt(BigInteger cipher) {
        BigInteger a = cipher.modPow(this.prikey.getPhi().divide(this.R), this.pubkey.getN());

        BigInteger c;
        for (int i = 0; i < R.intValue(); i++) {
            c = this.prikey.getX().modPow(BigInteger.valueOf(i), this.pubkey.getN());
            if (a.equals(c)) {
                return i;
            }
        }
        return -1;
    }

    //generating big integer between from z*n
    public static BigInteger itemZStarN(BigInteger n, BigInteger phi, BigInteger R) {
        BigInteger r;
        do {
            r = BigInteger.valueOf(29);
        } while (r.compareTo(n) >= 0 || r.gcd(n).intValue()
            != 1 || r.modPow(phi.divide(R), n).intValue() == 1);
        return r;
    }

    public static BigInteger randomZStarN(BigInteger n) {
        BigInteger r;
        do {
            r = BigInteger.valueOf(29);
        } while (r.compareTo(n) >= 0 || r.gcd(n).intValue()
            != 1);
        return r;
    }

    public BigInteger add(BigInteger c1, BigInteger c2) {
        return c1.multiply(c2).mod(this.pubkey.getN());
    }

    public BigInteger sub(BigInteger c1, BigInteger c2) {
        BigInteger c2_inverse = c2.modInverse(this.pubkey.getN());
        return c1.multiply(c2_inverse).mod(this.pubkey.getN());
    }

    public static void main(String[] args) {
        Benaloh b = new Benaloh();
        b.keyGeneration();
        int message = 3;
        BigInteger c = b.encrypt(message);
        System.out.println("Encrypted :" + c.toString());
        int m = b.decrypt(c);
        System.out.println("Decrypted :" + m);
        if (m == message) {
            System.out.println("encryption-decryption is ok");
        } else {
            System.out.println("no");
        }

//        Test for homomorphic features
//        Test for addition feature E(message1+message2%r)=cipher1.cipher2
        int message1 = 10;
        BigInteger cipher1 = b.encrypt(message1);

        int message2 = 20;
        BigInteger cipher2 = b.encrypt(message2);

        BigInteger result_cipher_add = b.add(cipher1, cipher2);
        int result_addition = b.decrypt(result_cipher_add);
        if ((message1 + message2) % b.R.intValue() == result_addition) {
            System.out.println("addition feature is working correctly!");
        }
//
// Test for Subtraction E(message1-message2%r)=cipher1.(cipher2)^-1
        BigInteger result_cipher_sub = b.sub(cipher2, cipher1);
        int result_sub = b.decrypt(result_cipher_sub);
        if ((message2 - message1) % b.R.intValue() == result_sub) {
                System.out.println("subtraction feature is working correctly!");
        }

    }

}