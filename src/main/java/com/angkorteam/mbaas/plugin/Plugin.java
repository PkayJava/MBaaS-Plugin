package com.angkorteam.mbaas.plugin;

import org.gradle.api.Project;

/**
 * Created by socheat on 4/12/16.
 */
public class Plugin implements org.gradle.api.Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getTasks().withType(MBaaSTask.class);
        project.getTasks().create("mbaas", MBaaSTask.class);
    }

}
