package agh.edu.pl.tai.lineup.infrastructure.utils;

import org.springframework.security.crypto.codec.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {

    private static MessageDigest digest = createDigest();

    private static MessageDigest createDigest() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Exception during initalization of hashing algorithm", e);
        }
    }

    public static String encrypt(String value) {
        byte[] hash = digest.digest(
                value.getBytes(StandardCharsets.UTF_8));
        return new String(Hex.encode(hash));
    }

}
