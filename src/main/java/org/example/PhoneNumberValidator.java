package org.example;

import java.util.regex.Pattern;

public class PhoneNumberValidator {
    public static boolean isValid(String phoneNumber) {
        String regex = "^\\(\\d{3}\\) \\d{3}-\\d{4}$|^\\d{3}-\\d{3}-\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(phoneNumber).matches();
    }
}
