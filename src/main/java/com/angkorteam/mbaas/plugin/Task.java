package com.angkorteam.mbaas.plugin;

import com.google.common.base.Strings;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.gradle.api.DefaultTask;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

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

    protected MBaaSExtension getExtension() {
        return getProject().getExtensions().findByType(MBaaSExtension.class);
    }

    protected static void ensureDatabase(String database) {
        Sql2o sql2o = new Sql2o("jdbc:sqlite:" + database, "", "");
        try (org.sql2o.Connection connection = sql2o.open()) {
            // connection.createQuery("drop table if exists page").executeUpdate();
            connection.createQuery("create table IF NOT EXISTS page (_id INTEGER PRIMARY KEY AUTOINCREMENT, html_conflicted BOOLEAN, groovy_conflicted BOOLEAN, html_path VARCHAR(255), groovy_path VARCHAR(255), client_groovy TEXT, client_html TEXT, server_groovy TEXT, server_html TEXT, page_id VARCHAR(100), client_groovy_crc32 VARCHAR(100), server_groovy_crc32 VARCHAR(100), client_html_crc32 VARCHAR(100), server_html_crc32 VARCHAR(100))").executeUpdate();
            connection.createQuery("CREATE INDEX IF NOT EXISTS index_page_server_groovy_crc32 on page(server_groovy_crc32)").executeUpdate();
            connection.createQuery("CREATE INDEX IF NOT EXISTS index_page_server_html_crc32 on page(server_html_crc32)").executeUpdate();
            connection.createQuery("CREATE INDEX IF NOT EXISTS index_page_client_groovy_crc32 on page(client_groovy_crc32)").executeUpdate();
            connection.createQuery("CREATE INDEX IF NOT EXISTS index_page_client_html_crc32 on page(client_html_crc32)").executeUpdate();
            connection.createQuery("CREATE INDEX IF NOT EXISTS index_page_html_path on page(html_path)").executeUpdate();
            connection.createQuery("CREATE INDEX IF NOT EXISTS index_page_groovy_path on page(groovy_path)").executeUpdate();
            connection.createQuery("CREATE INDEX IF NOT EXISTS index_page_groovy_conflicted on page(groovy_conflicted)").executeUpdate();
            connection.createQuery("CREATE INDEX IF NOT EXISTS index_page_html_conflicted on page(html_conflicted)").executeUpdate();
            connection.createQuery("CREATE INDEX IF NOT EXISTS index_page_page_id on page(page_id)").executeUpdate();
            // connection.createQuery("drop table if exists rest").executeUpdate();
            connection.createQuery("create table IF NOT EXISTS rest (_id INTEGER PRIMARY KEY AUTOINCREMENT, groovy_conflicted BOOLEAN, groovy_path VARCHAR(255), client_groovy TEXT, server_groovy TEXT, rest_id VARCHAR(100), server_groovy_crc32 VARCHAR(100), client_groovy_crc32 VARCHAR(100))").executeUpdate();
            connection.createQuery("CREATE INDEX IF NOT EXISTS index_rest_server_groovy_crc32 on rest(server_groovy_crc32)").executeUpdate();
            connection.createQuery("CREATE INDEX IF NOT EXISTS index_rest_client_groovy_crc32 on rest(client_groovy_crc32)").executeUpdate();
            connection.createQuery("CREATE INDEX IF NOT EXISTS index_rest_groovy_path on rest(groovy_path)").executeUpdate();
            connection.createQuery("CREATE INDEX IF NOT EXISTS index_rest_groovy_conflicted on rest(groovy_conflicted)").executeUpdate();
            connection.createQuery("CREATE INDEX IF NOT EXISTS index_rest_page_id on rest(rest_id)").executeUpdate();
        }
    }

    protected String lookupDatabase(String database) {
        return new File(getProject().getProjectDir(), database).getAbsolutePath();
    }

    protected File lookupSource() {
        File source = new File(getProject().getProjectDir(), "src/main/java");
        if (!source.exists()) {
            source.mkdirs();
        }
        return source;
    }

    protected static void resetRest(File source, Sql2o sql2o) throws IOException {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("select " +
                    "_id as clientId, " +
                    "groovy_path as groovyPath, " +
                    "client_groovy as clientGroovy, " +
                    "client_groovy_crc32 as clientGroovyCrc32, " +
                    "server_groovy as serverGroovy, " +
                    "server_groovy_crc32 as serverGroovyCrc32, " +
                    "groovy_conflicted as groovyConflicted, " +
                    "rest_id as restId from rest");
            List<Rest> rests = query.executeAndFetch(Rest.class);
            if (rests != null && !rests.isEmpty()) {
                for (Rest rest : rests) {
                    File groovyFile = new File(source, rest.getGroovyPath());
                    groovyFile.getParentFile().mkdirs();
                    FileUtils.write(groovyFile, rest.getClientGroovy(), "UTF-8");
                    if (rest.isGroovyConflicted()) {
                        File groovyFileServer = new File(source, rest.getGroovyPath() + ".server");
                        FileUtils.write(groovyFileServer, rest.getServerGroovy(), "UTF-8");
                    }
                }
            }
        }
    }

    protected static void resetPage(File source, Sql2o sql2o) throws IOException {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("select " +
                    "_id as clientId, " +
                    "html_path as htmlPath, " +
                    "client_html as clientHtml, " +
                    "client_html_crc32 as clientHtmlCrc32, " +
                    "server_html as serverHtml, " +
                    "server_html_crc32 as serverHtmlCrc32, " +
                    "html_conflicted as htmlConflicted, " +
                    "groovy_path as groovyPath, " +
                    "client_groovy as clientGroovy, " +
                    "client_groovy_crc32 as clientGroovyCrc32, " +
                    "server_groovy as serverGroovy, " +
                    "server_groovy_crc32 as serverGroovyCrc32, " +
                    "groovy_conflicted as groovyConflicted, " +
                    "page_id as pageId from page");
            List<Page> pages = query.executeAndFetch(Page.class);
            if (pages != null && !pages.isEmpty()) {
                for (Page page : pages) {
                    File groovyFile = new File(source, page.getGroovyPath());
                    groovyFile.getParentFile().mkdirs();
                    FileUtils.write(groovyFile, page.getClientGroovy(), "UTF-8");
                    File htmlFile = new File(source, page.getHtmlPath());
                    htmlFile.getParentFile().mkdirs();
                    FileUtils.write(htmlFile, page.getClientHtml(), "UTF-8");
                    if (page.isHtmlConflicted()) {
                        File htmlFileServer = new File(source, page.getHtmlPath() + ".server");
                        FileUtils.write(htmlFileServer, page.getServerHtml(), "UTF-8");
                    }
                    if (page.isGroovyConflicted()) {
                        File groovyFileServer = new File(source, page.getGroovyPath() + ".server");
                        FileUtils.write(groovyFileServer, page.getServerGroovy(), "UTF-8");
                    }
                }
            }
        }
    }

    protected static void syncPage(File source, Sql2o sql2o, Sync sync) throws IOException {
        if (sync != null && sync.getPages() != null && !sync.getPages().isEmpty()) {
            try (Connection connection = sql2o.open()) {
                for (Page serverPage : sync.getPages()) {
                    if (!serverPage.isGroovyConflicted() && !serverPage.isHtmlConflicted() && Strings.isNullOrEmpty(serverPage.getServerGroovyCrc32()) && Strings.isNullOrEmpty(serverPage.getServerHtmlCrc32())) {
                        Query query = connection.createQuery("delete from page where page_id = :page_id");
                        query.addParameter("page_id", serverPage.getPageId());
                        query.executeUpdate();
                        continue;
                    }
                    Page clientPage;
                    {
                        Query query = connection.createQuery("select " +
                                "_id as clientId, " +
                                "html_path as htmlPath, " +
                                "client_html as clientHtml, " +
                                "client_html_crc32 as clientHtmlCrc32, " +
                                "server_html as serverHtml, " +
                                "server_html_crc32 as serverHtmlCrc32, " +
                                "html_conflicted as htmlConflicted, " +
                                "groovy_path as groovyPath, " +
                                "client_groovy as clientGroovy, " +
                                "client_groovy_crc32 as clientGroovyCrc32, " +
                                "server_groovy as serverGroovy, " +
                                "server_groovy_crc32 as serverGroovyCrc32, " +
                                "groovy_conflicted as groovyConflicted, " +
                                "page_id as pageId from page where pageId = :pageId");
                        query.addParameter("pageId", serverPage.getPageId());
                        clientPage = query.executeAndFetchFirst(Page.class);
                    }
                    if (clientPage == null) {
                        Query query = connection.createQuery("insert into page(page_id, html_path, groovy_path) values(:page_id, :html_path, :groovy_path)");
                        query.addParameter("page_id", serverPage.getPageId());
                        query.addParameter("groovy_path", serverPage.getGroovyPath());
                        query.addParameter("html_path", serverPage.getHtmlPath());
                        query.executeUpdate();
                    }
                    {
                        Query query = connection.createQuery("update page set groovy_conflicted = :groovy_conflicted, html_conflicted = :html_conflicted where page_id = :page_id");
                        query.addParameter("groovy_conflicted", serverPage.isGroovyConflicted());
                        query.addParameter("html_conflicted", serverPage.isHtmlConflicted());
                        query.addParameter("page_id", serverPage.getPageId());
                        query.executeUpdate();
                    }
                    if (!serverPage.isGroovyConflicted()) {
                        Query query = connection.createQuery("update page set client_groovy = :client_groovy, client_groovy_crc32 = :client_groovy_crc32, server_groovy = :server_groovy, server_groovy_crc32 = :server_groovy_crc32 where page_id = :page_id");
                        File groovyFile = new File(source, serverPage.getGroovyPath());
                        if (groovyFile.exists()) {
                            groovyFile.delete();
                        } else {
                            groovyFile.getParentFile().mkdirs();
                        }
                        FileUtils.write(groovyFile, serverPage.getServerGroovy(), "UTF-8");
                        query.addParameter("client_groovy", serverPage.getServerGroovy());
                        query.addParameter("client_groovy_crc32", serverPage.getServerGroovyCrc32());
                        query.addParameter("server_groovy", serverPage.getServerGroovy());
                        query.addParameter("server_groovy_crc32", serverPage.getServerGroovyCrc32());
                        query.addParameter("page_id", serverPage.getPageId());
                        query.executeUpdate();
                    } else {
                        File groovyFile = new File(source, serverPage.getGroovyPath() + ".server");
                        if (groovyFile.exists()) {
                            groovyFile.delete();
                        } else {
                            groovyFile.getParentFile().mkdirs();
                        }
                        Query query = connection.createQuery("update page set server_groovy = :server_groovy, server_groovy_crc32 = :server_groovy_crc32 where page_id = :page_id");
                        query.addParameter("server_groovy", serverPage.getServerGroovy());
                        query.addParameter("server_groovy_crc32", serverPage.getServerGroovyCrc32());
                        query.addParameter("page_id", serverPage.getPageId());
                        query.executeUpdate();
                    }

                    if (!serverPage.isHtmlConflicted()) {
                        Query query = connection.createQuery("update page set client_html = :client_html, client_html_crc32 = :client_html_crc32, server_html = :server_html, server_html_crc32 = :server_html_crc32 where page_id = :page_id");
                        File htmlFile = new File(source, serverPage.getHtmlPath());
                        if (htmlFile.exists()) {
                            htmlFile.delete();
                        } else {
                            htmlFile.getParentFile().mkdirs();
                        }
                        FileUtils.write(htmlFile, serverPage.getServerHtml(), "UTF-8");
                        query.addParameter("client_html", serverPage.getServerHtml());
                        query.addParameter("client_html_crc32", serverPage.getServerHtmlCrc32());
                        query.addParameter("server_html", serverPage.getServerHtml());
                        query.addParameter("server_html_crc32", serverPage.getServerHtmlCrc32());
                        query.addParameter("page_id", serverPage.getPageId());
                        query.executeUpdate();
                    } else {
                        File htmlFile = new File(source, serverPage.getHtmlPath() + ".server");
                        if (htmlFile.exists()) {
                            htmlFile.delete();
                        } else {
                            htmlFile.getParentFile().mkdirs();
                        }
                        Query query = connection.createQuery("update page set server_html = :server_html, server_html_crc32 = :server_html_crc32 where page_id = :page_id");
                        query.addParameter("server_html", serverPage.getServerHtml());
                        query.addParameter("server_html_crc32", serverPage.getServerHtmlCrc32());
                        query.addParameter("page_id", serverPage.getPageId());
                        query.executeUpdate();
                    }
                }
            }
        }
    }

    protected static void syncRest(File source, Sql2o sql2o, Sync sync) throws IOException {
        if (sync != null && sync.getRests() != null && !sync.getRests().isEmpty()) {
            try (Connection connection = sql2o.open()) {
                for (Rest serverRest : sync.getRests()) {
                    if (!serverRest.isGroovyConflicted() && Strings.isNullOrEmpty(serverRest.getServerGroovyCrc32())) {
                        Query query = connection.createQuery("delete from rest where rest_id = :rest_id");
                        query.addParameter("rest_id", serverRest.getRestId());
                        query.executeUpdate();
                        continue;
                    }
                    Rest clientRest;
                    {
                        Query query = connection.createQuery("select " +
                                "_id as clientId, " +
                                "groovy_path as groovyPath, " +
                                "client_groovy as clientGroovy, " +
                                "client_groovy_crc32 as clientGroovyCrc32, " +
                                "server_groovy as serverGroovy, " +
                                "server_groovy_crc32 as serverGroovyCrc32, " +
                                "groovy_conflicted as groovyConflicted, " +
                                "rest_id as restId from rest where restId = :restId");
                        query.addParameter("restId", serverRest.getRestId());
                        clientRest = query.executeAndFetchFirst(Rest.class);
                    }
                    if (clientRest == null) {
                        Query query = connection.createQuery("insert into rest(rest_id, groovy_path) values(:rest_id, :groovy_path)");
                        query.addParameter("rest_id", serverRest.getRestId());
                        query.addParameter("groovy_path", serverRest.getGroovyPath());
                        query.executeUpdate();
                    }
                    {
                        Query query = connection.createQuery("update rest set groovy_conflicted = :groovy_conflicted where rest_id = :rest_id");
                        query.addParameter("groovy_conflicted", serverRest.isGroovyConflicted());
                        query.addParameter("rest_id", serverRest.getRestId());
                        query.executeUpdate();
                    }
                    if (!serverRest.isGroovyConflicted()) {
                        Query query = connection.createQuery("update rest set client_groovy = :client_groovy, client_groovy_crc32 = :client_groovy_crc32, server_groovy = :server_groovy, server_groovy_crc32 = :server_groovy_crc32 where rest_id = :rest_id");
                        File groovyFile = new File(source, serverRest.getGroovyPath());
                        if (groovyFile.exists()) {
                            groovyFile.delete();
                        } else {
                            groovyFile.getParentFile().mkdirs();
                        }
                        FileUtils.write(groovyFile, serverRest.getServerGroovy(), "UTF-8");
                        query.addParameter("client_groovy", serverRest.getServerGroovy());
                        query.addParameter("client_groovy_crc32", serverRest.getServerGroovyCrc32());
                        query.addParameter("server_groovy", serverRest.getServerGroovy());
                        query.addParameter("server_groovy_crc32", serverRest.getServerGroovyCrc32());
                        query.addParameter("rest_id", serverRest.getRestId());
                        query.executeUpdate();
                    } else {
                        File groovyFile = new File(source, serverRest.getGroovyPath() + ".server");
                        if (groovyFile.exists()) {
                            groovyFile.delete();
                        } else {
                            groovyFile.getParentFile().mkdirs();
                        }
                        FileUtils.write(groovyFile, serverRest.getServerGroovy(), "UTF-8");
                        Query query = connection.createQuery("update rest set server_groovy = :server_groovy, server_groovy_crc32 = :server_groovy_crc32 where rest_id = :rest_id");
                        query.addParameter("server_groovy", serverRest.getServerGroovy());
                        query.addParameter("server_groovy_crc32", serverRest.getServerGroovyCrc32());
                        query.addParameter("rest_id", serverRest.getRestId());
                        query.executeUpdate();
                    }
                }
            }
        }
    }

    protected static void restForDeleteSync(File source, Sql2o sql2o, Sync sync) throws IOException {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("select " +
                    "_id as clientId, " +
                    "groovy_path as groovyPath, " +
                    "client_groovy as clientGroovy, " +
                    "client_groovy_crc32 as clientGroovyCrc32, " +
                    "server_groovy as serverGroovy, " +
                    "server_groovy_crc32 as serverGroovyCrc32, " +
                    "groovy_conflicted as groovyConflicted, " +
                    "rest_id as restId from rest");
            List<Rest> rests = query.executeAndFetch(Rest.class);
            for (Rest rest : rests) {
                String groovyPath = rest.getGroovyPath();
                File groovyFile = new File(source, groovyPath);
                File groovyFileServer = new File(source, groovyPath + ".server");
                if (!groovyFile.exists() && !groovyFileServer.exists()) {
                    Rest restGson = new Rest();
                    restGson.setRestId(restGson.getRestId());
                    restGson.setServerGroovyCrc32(rest.getServerGroovyCrc32());
                    sync.addRest(restGson);
                }
            }
        }
    }

    protected static void pageForDeleteSync(File source, Sql2o sql2o, Sync sync) throws IOException {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("select " +
                    "_id as clientId, " +
                    "html_path as htmlPath, " +
                    "client_html as clientHtml, " +
                    "client_html_crc32 as clientHtmlCrc32, " +
                    "server_html as serverHtml, " +
                    "server_html_crc32 as serverHtmlCrc32, " +
                    "html_conflicted as htmlConflicted, " +
                    "groovy_path as groovyPath, " +
                    "client_groovy as clientGroovy, " +
                    "client_groovy_crc32 as clientGroovyCrc32, " +
                    "server_groovy as serverGroovy, " +
                    "server_groovy_crc32 as serverGroovyCrc32, " +
                    "groovy_conflicted as groovyConflicted, " +
                    "page_id as pageId from page");
            List<Page> pages = query.executeAndFetch(Page.class);
            for (Page page : pages) {
                String htmlPath = page.getHtmlPath();
                String groovyPath = page.getGroovyPath();
                File htmlFile = new File(source, htmlPath);
                File groovyFile = new File(source, groovyPath);
                File htmlFileServer = new File(source, htmlPath + ".server");
                File groovyFileServer = new File(source, groovyPath + ".server");
                if (!htmlFile.exists() && !groovyFile.exists() && !htmlFileServer.exists() && !groovyFileServer.exists()) {
                    Page pageGson = new Page();
                    pageGson.setPageId(page.getPageId());
                    pageGson.setServerGroovyCrc32(page.getServerGroovyCrc32());
                    sync.addPage(pageGson);
                }
            }
        }
    }

    protected static void restForSync(File source, Sql2o sql2o, Sync sync) throws IOException {
        String sourcePath = source.getAbsolutePath();
        Collection<File> files = FileUtils.listFiles(source, new String[]{"groovy"}, true);
        try (Connection connection = sql2o.open()) {
            for (File groovyFile : files) {
                String groovyPath = groovyFile.getAbsolutePath().substring(sourcePath.length());
                Query query = connection.createQuery("select " +
                        "_id as clientId, " +
                        "groovy_path as groovyPath, " +
                        "client_groovy as clientGroovy, " +
                        "client_groovy_crc32 as clientGroovyCrc32, " +
                        "server_groovy as serverGroovy, " +
                        "server_groovy_crc32 as serverGroovyCrc32, " +
                        "groovy_conflicted as groovyConflicted, " +
                        "rest_id as restId from rest where groovyPath = :groovyPath");
                query.addParameter("groovyPath", groovyPath);
                Rest rest = query.executeAndFetchFirst(Rest.class);
                if (rest != null) {
                    // server already has
                    File groovyFileServer = new File(groovyFile.getParent(), groovyFile.getName() + ".server");
                    if (!rest.isGroovyConflicted() || !groovyFileServer.exists()) {
                        String clientGroovyCrc32 = String.valueOf(FileUtils.checksumCRC32(groovyFile));
                        Rest restGson = new Rest();
                        restGson.setRestId(restGson.getRestId());
                        restGson.setClientGroovy(rest.getClientGroovy());
                        restGson.setClientGroovyCrc32(clientGroovyCrc32);
                        restGson.setServerGroovyCrc32(rest.getServerGroovyCrc32());
                        sync.addRest(restGson);
                    }
                } else {
                    // create new case is not allow, need to create from mbaas-server
                }
            }
        }
    }

    protected static void pageForSync(File source, Sql2o sql2o, Sync sync) throws IOException {
        String sourcePath = source.getAbsolutePath();
        Collection<File> files = FileUtils.listFiles(source, new String[]{"groovy"}, true);

        try (Connection connection = sql2o.open()) {
            // page to sync, html + groovy
            for (File groovyFile : files) {
                String groovyPath = groovyFile.getAbsolutePath().substring(sourcePath.length());
                Query query = connection.createQuery("select " +
                        "_id as clientId, " +
                        "html_path as htmlPath, " +
                        "client_html as clientHtml, " +
                        "client_html_crc32 as clientHtmlCrc32, " +
                        "server_html as serverHtml, " +
                        "server_html_crc32 as serverHtmlCrc32, " +
                        "html_conflicted as htmlConflicted, " +
                        "groovy_path as groovyPath, " +
                        "client_groovy as clientGroovy, " +
                        "client_groovy_crc32 as clientGroovyCrc32, " +
                        "server_groovy as serverGroovy, " +
                        "server_groovy_crc32 as serverGroovyCrc32, " +
                        "groovy_conflicted as groovyConflicted, " +
                        "page_id as pageId from page where groovyPath = :groovyPath");
                query.addParameter("groovyPath", groovyPath);
                Page page = query.executeAndFetchFirst(Page.class);
                if (page != null) {
                    File htmlFile = new File(groovyFile.getParent(), FilenameUtils.getBaseName(groovyFile.getName()) + ".html");
                    if (htmlFile.exists()) {
                        // server already has
                        File groovyFileServer = new File(groovyFile.getParent(), groovyFile.getName() + ".server");
                        File htmlFileServer = new File(htmlFile.getParent(), htmlFile.getName() + ".server");
                        if ((!page.isGroovyConflicted() && !page.isHtmlConflicted()) || (!groovyFileServer.exists() && !htmlFileServer.exists())) {
                            String clientHtmlCrc32 = String.valueOf(FileUtils.checksumCRC32(htmlFile));
                            String clientGroovyCrc32 = String.valueOf(FileUtils.checksumCRC32(groovyFile));
                            Page pageGson = new Page();
                            pageGson.setPageId(page.getPageId());
                            pageGson.setClientGroovy(page.getClientGroovy());
                            pageGson.setClientGroovyCrc32(clientGroovyCrc32);
                            pageGson.setClientHtml(page.getClientHtml());
                            pageGson.setClientHtmlCrc32(clientHtmlCrc32);
                            pageGson.setServerGroovyCrc32(page.getServerGroovyCrc32());
                            sync.addPage(pageGson);
                        }
                    }
                } else {
                    // create new case is not allow, need to create from mbaas-server
                }
            }
        }
    }

}
