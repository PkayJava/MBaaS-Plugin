package com.angkorteam.mbaas.plugin;

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
public class MBaaSResetTask extends Task {

    @TaskAction
    public void mbaasReset() throws IOException, SQLException {
        JDBC jdbc = new JDBC();
        DriverManager.registerDriver(jdbc);
        MBaaSExtension extension = getExtension();
        String sqlite = lookupDatabase(extension.getDatabase());
        ensureDatabase(sqlite);
        File source = lookupSource();
        Sql2o sql2o = new Sql2o("jdbc:sqlite:" + sqlite, "", "");
        resetPage(source, sql2o);
        resetRest(source, sql2o);
        DriverManager.deregisterDriver(jdbc);
    }

}
