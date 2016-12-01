package com.angkorteam.mbaas.plugin;

import org.gradle.api.Project;

/**
 * Created by socheat on 4/12/16.
 */
public class Plugin implements org.gradle.api.Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getExtensions().create("mbaas", MBaaSExtension.class);

        project.getTasks().withType(MBaaSSyncTask.class);
        project.getTasks().create("mbaasSync", MBaaSSyncTask.class);

        project.getTasks().withType(MBaaSResetTask.class);
        project.getTasks().create("mbaasReset", MBaaSResetTask.class);

        project.getTasks().withType(MBaaSRestTask.class);
        project.getTasks().create("mbaasRest", MBaaSRestTask.class);
    }

}
