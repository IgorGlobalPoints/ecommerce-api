package ecommerce.utils;

import java.util.UUID;

public class Validator {

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static boolean isNullOrEmpty(UUID uuid) {
        return uuid == null || uuid.toString().isEmpty();
    }

}