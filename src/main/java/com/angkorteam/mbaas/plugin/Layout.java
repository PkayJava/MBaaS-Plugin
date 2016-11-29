package com.angkorteam.mbaas.plugin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by socheat on 11/18/16.
 */
public class Layout {

    private Integer clientId;

    @Expose
    @SerializedName("class")
    private String clazz;

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("description")
    private String description;

    @Expose
    @SerializedName("htmlPath")
    private String htmlPath;

    @Expose
    @SerializedName("groovyPath")
    private String groovyPath;

    @Expose
    @SerializedName("pageId")
    private String layoutId;

    @Expose
    @SerializedName("clientHtml")
    private String clientHtml;

    @Expose
    @SerializedName("clientHtmlCrc32")
    private String clientHtmlCrc32;

    @Expose
    @SerializedName("clientGroovy")
    private String clientGroovy;

    @Expose
    @SerializedName("clientGroovyCrc32")
    private String clientGroovyCrc32;

    @Expose
    @SerializedName("htmlConflicted")
    private Boolean htmlConflicted;

    @Expose
    @SerializedName("serverHtml")
    private String serverHtml;

    @Expose
    @SerializedName("serverHtmlCrc32")
    private String serverHtmlCrc32;

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

    public String getHtmlPath() {
        return htmlPath;
    }

    public void setHtmlPath(String htmlPath) {
        this.htmlPath = htmlPath;
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

    public String getClientHtml() {
        return clientHtml;
    }

    public void setClientHtml(String clientHtml) {
        this.clientHtml = clientHtml;
    }

    public String getClientHtmlCrc32() {
        return clientHtmlCrc32;
    }

    public void setClientHtmlCrc32(String clientHtmlCrc32) {
        this.clientHtmlCrc32 = clientHtmlCrc32;
    }

    public String getServerHtml() {
        return serverHtml;
    }

    public void setServerHtml(String serverHtml) {
        this.serverHtml = serverHtml;
    }

    public String getServerHtmlCrc32() {
        return serverHtmlCrc32;
    }

    public void setServerHtmlCrc32(String serverHtmlCrc32) {
        this.serverHtmlCrc32 = serverHtmlCrc32;
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

    public String getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(String layoutId) {
        this.layoutId = layoutId;
    }

    public boolean isGroovyConflicted() {
        return groovyConflicted != null && groovyConflicted;
    }

    public void setGroovyConflicted(boolean groovyConflicted) {
        this.groovyConflicted = groovyConflicted;
    }

    public boolean isHtmlConflicted() {
        return htmlConflicted != null && htmlConflicted;
    }

    public void setHtmlConflicted(boolean htmlConflicted) {
        this.htmlConflicted = htmlConflicted;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
