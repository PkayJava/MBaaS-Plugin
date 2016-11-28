package com.angkorteam.mbaas.plugin;

import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by socheat on 11/28/16.
 */
public class MBaaSPage extends Task {

    @TaskAction
    public void mbaasService() throws IOException, SQLException {
        getProject().hasProperty("");
        Object code = getProject().property("code");
        Object title = getProject().property("title");
        Object path = getProject().property("path");
        Object layout = getProject().property("layout");
        Object description = getProject().property("description");
    }
}
