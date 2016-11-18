package com.angkorteam.mbaas.plugin;

import org.gradle.api.tasks.TaskAction;

/**
 * Created by socheat on 11/17/16.
 */
public class MBaaSServerTask extends Task {

    @TaskAction
    public void mbaasServer() {
        MBaaSExtension extension = getExtension();
        String sqlite = lookupDatabase(extension.getDatabase());
        ensureDatabase(sqlite);
        ensureServer(sqlite);
    }

}
