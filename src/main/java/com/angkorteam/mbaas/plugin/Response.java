package com.angkorteam.mbaas.plugin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by socheat on 11/18/16.
 */
public class Response {

    @Expose
    @SerializedName("data")
    private Sync data;

    public Sync getData() {
        return data;
    }

    public void setData(Sync data) {
        this.data = data;
    }
}
