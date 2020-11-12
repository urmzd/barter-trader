package ca.dal.bartertrader.domain.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class NewUserModel implements Serializable {
    private String displayName;
    private String email;
    private String password;
    private Boolean role;

    public NewUserModel() {

    }

    public NewUserModel(String firstName, String lastName, String email, String password, Boolean role) {
        this.displayName = String.format("%s %s", firstName, lastName);
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getDisplayName() {
        return email;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getRole() {
        return role;
    }
}
