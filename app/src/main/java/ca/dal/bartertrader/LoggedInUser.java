package ca.dal.bartertrader;

public class LoggedInUser {
    private String email;
    private String password;

    public LoggedInUser(String email, String password) {
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
