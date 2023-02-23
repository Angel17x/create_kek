package com.example.create_kek;

import java.util.Random;

public class RandomID {
    public enum Type {
        ALPHANUMERIC, HEXADECIMAL, NUMERIC, CHARS
    }


    private static final int DEFAULT_LENGTH = 6;
    private static final char[] CHARS = "abcdefghjkmnpqrstuvwxyzABCDEFGHIJKMNPQRSTUVWXYZ".toCharArray();
    private static final char[] ALPHANUMERIC = "abcdefghjkmnpqrstuvwxyzABCDEFGHIJKMNPQRSTUVWXYZ0123456789".toCharArray();
    private static final char[] NUMERIC = "1234567890".toCharArray();
    private static final char[] HEXADECIMAL = "0123456789ABCDEF".toCharArray();
    public static Random RANDOM = generateRandom();

    public static String getID() {
        return getID(DEFAULT_LENGTH, Type.ALPHANUMERIC);
    }

    public static String getID(int length, Type type) {
        switch (type) {
            case CHARS:
                return generate(length, CHARS);
            case NUMERIC:
                return generate(length, NUMERIC);
            case ALPHANUMERIC:
                return generate(length, ALPHANUMERIC);
            case HEXADECIMAL:
            default:
                return generate(length, HEXADECIMAL);
        }
    }

    public static String generateIdAlphanumeric(int length) {
        return generate(length, ALPHANUMERIC);
    }

    public static String getRandNumb(int length) {
        return generate(length, NUMERIC);
    }

    public static String generate(int length, char[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = array[RANDOM.nextInt(array.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    public static Random generateRandom() {
        if (RANDOM == null) {
            RANDOM = new Random();
        }
        return RANDOM;
    }


}
