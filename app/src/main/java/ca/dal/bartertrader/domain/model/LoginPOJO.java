package ca.dal.bartertrader.domain.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class LoginPOJO implements Serializable {
    private String email;
    private String password;

    public LoginPOJO() {
    }

    public LoginPOJO(String email, String password, Boolean rememberMe) {
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
