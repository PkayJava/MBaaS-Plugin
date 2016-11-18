package com.angkorteam.mbaas.plugin;

/**
 * Created by socheat on 11/18/16.
 */
public class Server {

    private Integer clientId;

    private String name;

    private String address;

    private String login;

    private String password;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}
