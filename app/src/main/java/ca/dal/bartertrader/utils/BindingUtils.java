package ca.dal.bartertrader.utils;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class BindingUtils {

    public static void setImageUrOnImageView(ImageView view, Uri imageUri) {
        view.setImageURI(imageUri);
        view.setVisibility(View.VISIBLE);
    }

    public static void setErrorOnTextInputLayout(TextInputLayout view, Boolean validity, String errorMessage) {
        view.setError(validity ? null : errorMessage);
        view.setErrorEnabled(!validity);
    }
}
