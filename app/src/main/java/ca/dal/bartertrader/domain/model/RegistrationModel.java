package ca.dal.bartertrader.domain.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class RegistrationModel implements Serializable {

    private String displayName;
    private String email;
    private String password;
    private Boolean provider;

    public RegistrationModel() {

    }

    public RegistrationModel(String firstName, String lastName, String email, String password, Boolean provider) {
        this.displayName = String.format("%s %s", firstName, lastName);
        this.email = email;
        this.password = password;
        this.provider = provider;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Boolean isProvider() {
        return provider;
    }
}
