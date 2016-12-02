package com.angkorteam.mbaas.plugin;

import com.angkorteam.mbaas.plain.response.RestResponse;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by socheat on 11/28/16.
 */
public class MBaaSRestTask extends Task {

    @TaskAction
    public void mbaasRest() throws IOException, SQLException {
        String className = (String) getProject().property("className");
        String mountPath = (String) getProject().property("mountPath");
        String name = getProject().hasProperty("name") ? (String) getProject().property("name") : null;
        String method = (String) getProject().property("method");
        if (Strings.isNullOrEmpty(name)) {
            name = className;
        }
        String description = getProject().hasProperty("description") ? (String) getProject().property("description") : null;
        if (Strings.isNullOrEmpty(description)) {
            description = name;
        }

        Rest rest = new Rest();
        rest.setClassName(className);
        rest.setPath(mountPath);
        rest.setName(name);
        rest.setMethod(method);
        rest.setDescription(description);

        MBaaSExtension extension = getExtension();
        Gson gson = new Gson();

        String api = getServer() + "/api/system/rest";
        HttpRequestWithBody request = Unirest.post(api);
        request = request.basicAuth(extension.getLogin(), extension.getPassword()).header("Content-Type", "application/json");

        try {
            
            HttpResponse<String> response = request.body(gson.toJson(rest)).asString();
            if (response.getStatus() != 200) {
                RestResponse temp = gson.fromJson(response.getBody(), RestResponse.class);
                if (temp.getResultCode() != 200) {
                    System.out.println(temp.getDebugMessage());
                }
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
}
