package com.angkorteam.mbaas.plugin;

/**
 * Created by socheat on 11/19/16.
 */
public class MBaaSExtension {

    private String database = "mbaas.db";

    private String server;

    private String login;

    private String password;

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
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
