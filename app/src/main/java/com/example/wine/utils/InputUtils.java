package com.example.wine.utils;

import android.text.TextUtils;
import android.util.Patterns;

public class InputUtils {

    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidCpf(String cpf) {
        if (cpf == null) return false;
        cpf = cpf.replaceAll("[^\\d]", "");
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) return false;

        int sum = 0, check1, check2;
        for (int i = 0; i < 9; i++) sum += (cpf.charAt(i) - '0') * (10 - i);
        check1 = 11 - (sum % 11); if (check1 >= 10) check1 = 0;
        if (check1 != cpf.charAt(9) - '0') return false;
        sum = 0;
        for (int i = 0; i < 10; i++) sum += (cpf.charAt(i) - '0') * (11 - i);
        check2 = 11 - (sum % 11); if (check2 >= 10) check2 = 0;
        return check2 == cpf.charAt(10) - '0';
    }

    public static boolean isValidLatitude(String latitude) {
        try {
            double lat = Double.parseDouble(latitude);
            return lat >= -90 && lat <= 90;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidLongitude(String longitude) {
        try {
            double lng = Double.parseDouble(longitude);
            return lng >= -180 && lng <= 180;
        } catch (Exception e) {
            return false;
        }
    }

    public static int safeParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }

    public static double safeParseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return 0.0;
        }
    }
}
