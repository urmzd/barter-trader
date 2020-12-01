package ca.dal.bartertrader.domain.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class LoginModel implements Serializable {
    private String email;
    private String password;

    public LoginModel() {
    }

    public LoginModel(String email, String password, Boolean rememberMe) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
