package com.angkorteam.mbaas.plugin;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.gradle.api.tasks.TaskAction;
import org.sql2o.Sql2o;
import org.sqlite.JDBC;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by socheat on 11/17/16.
 */
public class MBaaSSyncTask extends Task {

    @TaskAction
    public void mbaasSync() throws IOException, SQLException {
        JDBC jdbc = new JDBC();
        DriverManager.registerDriver(jdbc);
        MBaaSExtension extension = getExtension();
        String sqlite = lookupDatabase(extension.getDatabase());
        ensureDatabase(sqlite);

        File source = lookupSource();
        Sql2o sql2o = new Sql2o("jdbc:sqlite:" + sqlite, "", "");
        Sync sync = new Sync();
        // page to sync, html + groovy
        pageForSync(source, sql2o, sync);
        // rest to sync, groovy
        restForSync(source, sql2o, sync);
        // page to delete sync, html + groovy
        pageForDeleteSync(source, sql2o, sync);
        // rest to delete sync, groovy
        restForDeleteSync(source, sql2o, sync);

        String server = null;
        if (extension.getServer().endsWith("/")) {
            server = extension.getServer().substring(0, extension.getServer().length() - 1);
        } else {
            server = extension.getServer();
        }

        Gson gson = new Gson();

        String api = server + "/api/system/sync";
        HttpRequestWithBody request = Unirest.post(api);
        request = request.basicAuth(extension.getLogin(), extension.getPassword()).header("Content-Type", "application/json");

        try {
            HttpResponse<String> response = request.body(gson.toJson(sync)).asString();
            if (response.getStatus() == 200) {
                Response temp = gson.fromJson(response.getBody(), Response.class);
                syncPage(source, sql2o, temp.getData());
                syncRest(source, sql2o, temp.getData());
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        DriverManager.deregisterDriver(jdbc);
    }

}
