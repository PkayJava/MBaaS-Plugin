package com.angkorteam.mbaas.plugin;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.apache.commons.io.FileUtils;
import org.gradle.api.tasks.TaskAction;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by socheat on 11/17/16.
 */
public class MBaaSSyncTask extends Task {

    @TaskAction
    public void mbaasSync() throws IOException {
        MBaaSExtension extension = getExtension();
        String sqlite = lookupDatabase(extension.getDatabase());
        ensureDatabase(sqlite);
        ensureServer(sqlite);

        File source = lookupSource();
        String sourcePath = source.getAbsolutePath();
        Collection<File> files = FileUtils.listFiles(source, new String[]{"groovy", "html"}, true);
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

        Server server;
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("select _id as clientId, name, address, login, password from server limit 0,1");
            server = query.executeAndFetchFirst(Server.class);
        }

        String api = server.getAddress() + "/api/system/sync";
        HttpRequestWithBody request = Unirest.post(api);
        request = request.basicAuth(server.getLogin(), server.getPassword());
        try {
            HttpResponse<Sync> response = request.asObject(Sync.class);
            if (response.getStatus() == 200) {
                syncPage(source, sql2o, response.getBody());
                syncRest(source, sql2o, response.getBody());
            }
        } catch (UnirestException e) {
        }
    }

}
