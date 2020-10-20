package ca.dal.bartertrader.dto;

import androidx.databinding.BaseObservable;

public class RegistrationFormDTO {
    private String firstName;
    private String lastName;
    private String password;
    private String confirmedPassword;
    private boolean isProvider;

    public RegistrationFormDTO(String firstName, String lastName, String password, String confirmedPassword, Boolean isProvider) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.confirmedPassword = confirmedPassword;
        this.isProvider = isProvider;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

    public Boolean getProvider() {
        return isProvider;
    }

    public void setProvider(Boolean provider) {
        isProvider = provider;
    }
}
