package com.angkorteam.mbaas.plugin;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.gradle.initialization.DefaultGradleLauncher;
import org.gradle.initialization.DefaultGradleLauncherFactory;
import org.gradle.initialization.GradleLauncher;
import org.sql2o.Sql2o;
import org.sqlite.JDBC;

import java.io.File;
import java.sql.DriverManager;

/**
 * Created by socheat on 12/1/16.
 */
public class Test {
    public static void main(String[] args) throws Throwable {
        JDBC jdbc = new JDBC();
        DriverManager.registerDriver(jdbc);
        String sqlite = "/opt/home/socheat/Documents/git/PkayJava/MBaaS-Demo/mbaas.db";
        MBaaSSyncTask.ensureDatabase(sqlite);

        File source = new File("/opt/home/socheat/Documents/git/PkayJava/MBaaS-Demo");
        Sql2o sql2o = new Sql2o("jdbc:sqlite:" + sqlite, "", "");
        Sync sync = new Sync();
        // layout to sync, html + groovy
        MBaaSSyncTask.layoutForSync(source, sql2o, sync);
        // page to sync, html + groovy
        MBaaSSyncTask.pageForSync(source, sql2o, sync);
        // rest to sync, groovy
        MBaaSSyncTask.restForSync(source, sql2o, sync);
        // layout to delete sync, html + groovy
        MBaaSSyncTask.layoutForDeleteSync(source, sql2o, sync);
        // page to delete sync, html + groovy
        MBaaSSyncTask.pageForDeleteSync(source, sql2o, sync);
        // rest to delete sync, groovy
        MBaaSSyncTask.restForDeleteSync(source, sql2o, sync);

        Gson gson = new Gson();

        String api = "http://localhost:9080" + "/api/system/sync";
        HttpRequestWithBody request = Unirest.post(api);
        request = request.basicAuth("service", "service").header("Content-Type", "application/json");

        try {
            HttpResponse<String> response = request.body(gson.toJson(sync)).asString();
            if (response.getStatus() == 200) {
                Response temp = gson.fromJson(response.getBody(), Response.class);
                MBaaSSyncTask.syncPage(source, sql2o, temp.getData());
                MBaaSSyncTask.syncRest(source, sql2o, temp.getData());
                MBaaSSyncTask.syncLayout(source, sql2o, temp.getData());
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        DriverManager.deregisterDriver(jdbc);
    }
}
