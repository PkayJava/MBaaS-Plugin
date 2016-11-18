package com.angkorteam.mbaas.plugin;

import org.gradle.api.DefaultTask;

/**
 * Created by socheat on 4/12/16.
 */
public abstract class Task extends DefaultTask {

    @Override
    public final String getGroup() {
        return "MBaaS";
    }

    @Override
    public final String getDescription() {
        return "AngkorTeam";
    }
}
