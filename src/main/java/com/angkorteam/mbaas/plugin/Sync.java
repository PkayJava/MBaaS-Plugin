package com.angkorteam.mbaas.plugin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by socheat on 11/17/16.
 */
public class Sync {

    @Expose
    @SerializedName("pages")
    private List<Page> pages = new ArrayList<>();

    @Expose
    @SerializedName("rests")
    private List<Rest> rests = new ArrayList<>();

    public List<Page> getPages() {
        return pages;
    }

    public void addPage(Page page) {
        this.pages.add(page);
    }

    public List<Rest> getRests() {
        return rests;
    }

    public void addRest(Rest rest) {
        this.rests.add(rest);
    }

}
