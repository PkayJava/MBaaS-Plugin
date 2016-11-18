package com.angkorteam.mbaas.plugin;

/**
 * Created by socheat on 11/18/16.
 */
public class Page {

    private Integer clientId;

    private String htmlPath;

    private String clientHtml;

    private String clientHtmlCrc32;

    private String serverHtml;

    private String serverHtmlCrc32;

    private Boolean htmlConflicted;

    private String groovyPath;

    private String clientGroovy;

    private String clientGroovyCrc32;

    private String serverGroovy;

    private String serverGroovyCrc32;

    private Boolean groovyConflicted;

    private String pageId;

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

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
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
}
