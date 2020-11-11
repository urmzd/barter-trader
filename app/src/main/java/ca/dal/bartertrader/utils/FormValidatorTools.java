package ca.dal.bartertrader.utils;

import androidx.core.util.PatternsCompat;

import com.google.common.base.Strings;

public class FormValidatorTools {

    public static Boolean isEmailValid(String email) {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static Boolean doPasswordsMatch(String password, String confirmedPassword) {
        return password.equals(confirmedPassword);
    }

    public static Boolean isTextAlphaNumeric(String text) {
        return text.matches("^[a-zA-Z0-9]*$");
    }

    public static Boolean isNameValid(String name) {
        return name.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");
    }

    public static Boolean isPasswordStrong(String password) {
        return password.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!_?$%]).{8,})");
    }

    public static Boolean isTextValid(String text) {
        return !Strings.isNullOrEmpty(text);
    }

}
