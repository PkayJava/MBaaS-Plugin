package com.angkorteam.mbaas.plugin;

import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by socheat on 11/28/16.
 */
public class MBaaSService extends Task {

    @TaskAction
    public void mbaasService() throws IOException, SQLException {
        getProject().hasProperty("");
        Object name = getProject().property("name");
        Object path = getProject().property("path");
        Object method = getProject().property("method");
        Object description = getProject().property("description");
    }
}
