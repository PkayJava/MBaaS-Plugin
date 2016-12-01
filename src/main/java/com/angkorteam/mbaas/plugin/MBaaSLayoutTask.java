package com.angkorteam.mbaas.plugin;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.gradle.api.tasks.TaskAction;
import org.sql2o.Sql2o;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by socheat on 11/28/16.
 */
public class MBaaSLayoutTask extends Task {

    @TaskAction
    public void mbaasLayout() throws IOException, SQLException {
        String className = (String) getProject().property("className");
        String layoutTitle = getProject().hasProperty("layoutTitle") ? (String) getProject().property("layoutTitle") : null;
        if (Strings.isNullOrEmpty(layoutTitle)) {
            layoutTitle = className;
        }
        String layoutDescription = getProject().hasProperty("layoutDescription") ? (String) getProject().property("layoutDescription") : null;
        if (Strings.isNullOrEmpty(layoutDescription)) {
            layoutDescription = layoutTitle;
        }
        Layout layout = new Layout();
        layout.setClassName(className);
        layout.setTitle(layoutTitle);
        layout.setDescription(layoutDescription);

        MBaaSExtension extension = getExtension();
        String sqlite = lookupDatabase(extension.getDatabase());
        ensureDatabase(sqlite);
        Gson gson = new Gson();
        File source = lookupSource();
        Sql2o sql2o = new Sql2o("jdbc:sqlite:" + sqlite, "", "");

        String api = getServer() + "/api/system/layout";
        HttpRequestWithBody request = Unirest.post(api);
        request = request.basicAuth(extension.getLogin(), extension.getPassword()).header("Content-Type", "application/json");

        try {
            HttpResponse<String> response = request.body(gson.toJson(layout)).asString();
            if (response.getStatus() == 200) {
                Response temp = gson.fromJson(response.getBody(), Response.class);
                syncPage(source, sql2o, temp.getData());
                syncRest(source, sql2o, temp.getData());
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
}
