package agh.edu.pl.tai.lineup.infrastructure;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RandomIdGenerator {

    private static SecureRandom random = new SecureRandom();

    public static String next() {
        return new BigInteger(128, random).toString();
    }
}
