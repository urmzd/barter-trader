package ca.dal.bartertrader.utils;

import android.util.Log;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class BindingUtils {

    public static Boolean textInputEditTextHasChanged(TextInputEditText editText, String text) {
        return !editText.getText().toString().equals(text);
    }

    public static void setErrorOnTextInputLayout(TextInputLayout view, Boolean validity, String errorMessage) {
        view.setError(validity ? null : errorMessage);
        view.setErrorEnabled(!validity);
    }
}
