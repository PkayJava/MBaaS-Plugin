package com.angkorteam.mbaas.plugin;

import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by socheat on 11/28/16.
 */
public class MBaaSLayout extends Task {

    @TaskAction
    public void mbaasService() throws IOException, SQLException {
        getProject().hasProperty("");
        String clazz = (String) getProject().property("class");
        String title = (String) getProject().property("title");
        String description = (String) getProject().property("description");
    }
}
