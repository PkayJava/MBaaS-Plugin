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
public class MBaaSRestTask extends Task {

    @TaskAction
    public void mbaasService() throws IOException, SQLException {
        String clazz = (String) getProject().property("class");
        String path = (String) getProject().property("path");
        String name = getProject().hasProperty("name") ? (String) getProject().property("name") : null;
        String method = (String) getProject().property("method");
        if (Strings.isNullOrEmpty(name)) {
            name = clazz;
        }
        String description = getProject().hasProperty("description") ? (String) getProject().property("description") : null;
        if (Strings.isNullOrEmpty(description)) {
            description = name;
        }

        Rest rest = new Rest();
        rest.setClazz(clazz);
        rest.setPath(path);
        rest.setName(name);
        rest.setMethod(method);
        rest.setDescription(description);

        MBaaSExtension extension = getExtension();
        String sqlite = lookupDatabase(extension.getDatabase());
        ensureDatabase(sqlite);
        Gson gson = new Gson();
        File source = lookupSource();
        Sql2o sql2o = new Sql2o("jdbc:sqlite:" + sqlite, "", "");

        String api = getServer() + "/api/system/rest";
        HttpRequestWithBody request = Unirest.post(api);
        request = request.basicAuth(extension.getLogin(), extension.getPassword()).header("Content-Type", "application/json");

        try {
            HttpResponse<String> response = request.body(gson.toJson(rest)).asString();
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
