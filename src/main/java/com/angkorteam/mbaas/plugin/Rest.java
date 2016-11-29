package com.angkorteam.mbaas.plugin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by socheat on 11/18/16.
 */
public class Rest {

    private Integer clientId;

    @Expose
    @SerializedName("class")
    private String clazz;

    @Expose
    @SerializedName("path")
    private String path;

    @Expose
    @SerializedName("method")
    private String method;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("description")
    private String description;

    @Expose
    @SerializedName("groovyPath")
    private String groovyPath;

    @Expose
    @SerializedName("restId")
    private String restId;

    @Expose
    @SerializedName("clientGroovy")
    private String clientGroovy;

    @Expose
    @SerializedName("clientGroovyCrc32")
    private String clientGroovyCrc32;

    @Expose
    @SerializedName("serverGroovy")
    private String serverGroovy;

    @Expose
    @SerializedName("serverGroovyCrc32")
    private String serverGroovyCrc32;

    @Expose
    @SerializedName("groovyConflicted")
    private Boolean groovyConflicted;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getGroovyPath() {
        return groovyPath;
    }

    public void setGroovyPath(String groovyPath) {
        this.groovyPath = groovyPath;
    }

    public String getClientGroovy() {
        return clientGroovy;
    }

    public void setClientGroovy(String clientGroovy) {
        this.clientGroovy = clientGroovy;
    }

    public String getClientGroovyCrc32() {
        return clientGroovyCrc32;
    }

    public void setClientGroovyCrc32(String clientGroovyCrc32) {
        this.clientGroovyCrc32 = clientGroovyCrc32;
    }

    public String getServerGroovy() {
        return serverGroovy;
    }

    public void setServerGroovy(String serverGroovy) {
        this.serverGroovy = serverGroovy;
    }

    public String getServerGroovyCrc32() {
        return serverGroovyCrc32;
    }

    public void setServerGroovyCrc32(String serverGroovyCrc32) {
        this.serverGroovyCrc32 = serverGroovyCrc32;
    }

    public boolean isGroovyConflicted() {
        return groovyConflicted != null && groovyConflicted;
    }

    public void setGroovyConflicted(boolean groovyConflicted) {
        this.groovyConflicted = groovyConflicted;
    }

    public String getRestId() {
        return restId;
    }

    public void setRestId(String restId) {
        this.restId = restId;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
