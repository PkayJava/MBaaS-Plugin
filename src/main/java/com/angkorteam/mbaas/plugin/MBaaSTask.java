package com.angkorteam.mbaas.plugin;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.gradle.api.tasks.TaskAction;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import java.io.Console;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by socheat on 11/17/16.
 */
public class MBaaSTask extends Task {

    private String database;

    @TaskAction
    public void mbaasSync() {
        mbaasServer();
    }

    private static void ensureServer(String database) {
        Sql2o sql2o = new Sql2o("jdbc:sqlite:" + database, "", "");
        try (Connection connection = sql2o.open()) {
            if (connection.createQuery("select count(*) from server").executeScalar(Integer.class) <= 0) {
                while (true) {
                    Console console = System.console();
                    String server = console.readLine("> Please enter your server: ");
                    String api = null;
                    if (server.endsWith("/")) {
                        api = "api/system/monitor";
                    } else {
                        api = "/api/system/monitor";
                    }
                    String login = console.readLine("> Please enter your username: ");
                    String password = new String(console.readPassword("> Please enter your password: "));
                    HttpRequestWithBody request = Unirest.post(api);
                    request = request.basicAuth(login, password);
                    HttpResponse<JsonNode> response = null;
                    try {
                        response = request.asJson();
                        if (response.getStatus() == 200) {
                            Query query = connection.createQuery("insert into server(name, address, login, password) values(:name, :address, :login, :password)");
                            query.addParameter("name", "MBaaS");
                            query.addParameter("address", server);
                            query.addParameter("login", login);
                            query.addParameter("password", password);
                            query.executeUpdate();
                            break;
                        }
                    } catch (UnirestException e) {
                    }
                }
            }
        }
    }

    @TaskAction
    public void mbaasServer() {
        String sqlite = lookupDatabase(this.database);
        ensureDatabase(sqlite);
        ensureServer(sqlite);
    }

    private static void ensureDatabase(String database) {
        Sql2o sql2o = new Sql2o("jdbc:sqlite:" + database, "", "");
        try (org.sql2o.Connection connection = sql2o.open()) {
            // connection.createQuery("drop table if exists server").executeUpdate();
            connection.createQuery("create table IF NOT EXISTS server (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(100), address VARCHAR (255), login VARCHAR (255), password VARCHAR(255))").executeUpdate();
            connection.createQuery("CREATE UNIQUE INDEX index_server_name on server(name)").executeUpdate();
            // connection.createQuery("drop table if exists page").executeUpdate();
            connection.createQuery("create table IF NOT EXISTS page (_id INTEGER PRIMARY KEY AUTOINCREMENT, groovy TEXT, html TEXT, page_id VARCHAR(100), groovy_crc32 VARCHAR(100), html_crc32 VARCHAR(100))").executeUpdate();
            connection.createQuery("CREATE INDEX index_page_groovy_crc32 on page(groovy_crc32)").executeUpdate();
            connection.createQuery("CREATE INDEX index_page_html_crc32 on page(html_crc32)").executeUpdate();
            connection.createQuery("CREATE INDEX index_page_page_id on page(page_id)").executeUpdate();
            // connection.createQuery("drop table if exists rest").executeUpdate();
            connection.createQuery("create table IF NOT EXISTS rest (_id INTEGER PRIMARY KEY AUTOINCREMENT, groovy TEXT, rest_id VARCHAR(100), groovy_crc32 VARCHAR(100))").executeUpdate();
            connection.createQuery("CREATE INDEX index_rest_groovy_crc32 on rest(groovy_crc32)").executeUpdate();
            connection.createQuery("CREATE INDEX index_rest_page_id on rest(rest_id)").executeUpdate();
        }
    }

    protected String lookupDatabase(String database) {
        String sqlite = new File(getProject().getProjectDir(), database).getAbsolutePath();
        return sqlite;
    }

    public static void main(String[] args) {
        ensureDatabase("mbaas.db");
        Sql2o sql2o = new Sql2o("jdbc:sqlite:mbaas.db", "", "");
        try (org.sql2o.Connection connection = sql2o.open()) {
            {
                Query query = connection.createQuery("insert into server(name, address, login, password) values(:name, :address, :login, :password)");
                query.addParameter("name", "PROD");
                query.addParameter("address", "http://yp.angkorteam.com");
                query.addParameter("login", "service");
                query.addParameter("password", "service");
                query.executeUpdate();
            }
            {
                Query query = connection.createQuery("select * from server");
                List<Map<String, Object>> rows = query.executeAndFetchTable().asList();
                for (Map<String, Object> row : rows) {
                    System.out.println(row.get("name"));
                }
            }
        }
    }

}
