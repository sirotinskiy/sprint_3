package model;

import lombok.Builder;

@Builder
public class Courier {
    private String login;
    private String password;
    private String firstName;

    public Courier() {
    }

    public Courier(String loginCourier, String passwordCourier, String firstNameCourier) {
        this.login = loginCourier;
        this.password = passwordCourier;
        this.firstName = firstNameCourier;
    }

    public Courier(String login, String password) {
        this.login = login;
        this.password = password;
    }




    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
