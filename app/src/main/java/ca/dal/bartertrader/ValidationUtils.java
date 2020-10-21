package ca.dal.bartertrader;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Pattern;

public class ValidationUtils {
    public static final String EMAIL_PATTERN = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

    public static Boolean emailIsValid(String email) {

        return email != null && email.length() != 0 && email.matches(EMAIL_PATTERN);
    }

}
