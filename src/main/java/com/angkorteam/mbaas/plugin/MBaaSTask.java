package com.angkorteam.mbaas.plugin;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.gradle.api.tasks.TaskAction;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by socheat on 11/17/16.
 */
public class MBaaSTask extends Task {

    private String database;

    @TaskAction
    public void mbaasSync() throws IOException {
        mbaasServer();
        File source = lookupSource();
        String sourcePath = source.getAbsolutePath();
        Collection<File> files = FileUtils.listFiles(source, new String[]{"groovy", "html"}, true);
        String sqlite = lookupDatabase(this.database);
        Sql2o sql2o = new Sql2o("jdbc:sqlite:" + sqlite, "", "");
        try (Connection connection = sql2o.open()) {
            for (File groovyFile : files) {
                if ("groovy".equals(FilenameUtils.getExtension(groovyFile.getName()))) {
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
                            "page_id as pageId, from page where groovyPath = :groovyPath");
                    query.addParameter("groovyPath", groovyPath);
                    Page page = query.executeScalar(Page.class);
                    if (page != null) {
                        File htmlFile = new File(groovyFile.getParent(), FilenameUtils.getBaseName(groovyFile.getName()) + ".html");
                        if (htmlFile.exists()) {
                            // server already has
                            String clientHtmlCrc32 = String.valueOf(FileUtils.checksumCRC32(htmlFile));
                            String clientGroovyCrc32 = String.valueOf(FileUtils.checksumCRC32(groovyFile));
                            // TODO : prepare page request (might update)
                        }
                    }
                } else {
                    // create new case is not allow, need to create from mbaas-server
                }
            }
            for (File groovyFile : files) {
                if ("groovy".equals(FilenameUtils.getExtension(groovyFile.getName()))) {
                    String groovyPath = groovyFile.getAbsolutePath().substring(sourcePath.length());
                    Query query = connection.createQuery("select " +
                            "_id as clientId, " +
                            "groovy_path as groovyPath, " +
                            "client_groovy as clientGroovy, " +
                            "client_groovy_crc32 as clientGroovyCrc32, " +
                            "server_groovy as serverGroovy, " +
                            "server_groovy_crc32 as serverGroovyCrc32, " +
                            "groovy_conflicted as groovyConflicted, " +
                            "rest_id as restId, from rest where groovyPath = :groovyPath");
                    query.addParameter("groovyPath", groovyPath);
                    Rest rest = query.executeScalar(Rest.class);
                    if (rest != null) {
                        // server already has
                        String clientGroovyCrc32 = String.valueOf(FileUtils.checksumCRC32(groovyFile));
                        // TODO : prepare rest request (might update)
                    } else {
                        // create new case is not allow, need to create from mbaas-server
                    }
                }
            }
        }

        // TODO : sync update request rest and page with server
        // TODO : save / update response into client database
        // TODO : create conflicted files and let you merge it
    }

    private static void ensureServer(String database) {
        Sql2o sql2o = new Sql2o("jdbc:sqlite:" + database, "", "");
        try (Connection connection = sql2o.open()) {
            if (connection.createQuery("select count(*) from server").executeScalar(Integer.class) <= 0) {
                while (true) {
                    Console console = System.console();
                    String server = console.readLine("> Please enter your server: ");
                    String api = null;
                    if (server.endsWith("/")) {
                        api = "api/system/monitor";
                    } else {
                        api = "/api/system/monitor";
                    }
                    String login = console.readLine("> Please enter your username: ");
                    String password = new String(console.readPassword("> Please enter your password: "));
                    HttpRequestWithBody request = Unirest.post(api);
                    request = request.basicAuth(login, password);
                    HttpResponse<JsonNode> response = null;
                    try {
                        response = request.asJson();
                        if (response.getStatus() == 200) {
                            Query query = connection.createQuery("insert into server(name, address, login, password) values(:name, :address, :login, :password)");
                            query.addParameter("name", "MBaaS");
                            query.addParameter("address", server);
                            query.addParameter("login", login);
                            query.addParameter("password", password);
                            query.executeUpdate();
                            break;
                        }
                    } catch (UnirestException e) {
                    }
                }
            }
        }
    }

    @TaskAction
    public void mbaasServer() {
        String sqlite = lookupDatabase(this.database);
        ensureDatabase(sqlite);
        ensureServer(sqlite);
    }

    private static void ensureDatabase(String database) {
        Sql2o sql2o = new Sql2o("jdbc:sqlite:" + database, "", "");
        try (org.sql2o.Connection connection = sql2o.open()) {
            // connection.createQuery("drop table if exists server").executeUpdate();
            connection.createQuery("create table IF NOT EXISTS server (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(100), address VARCHAR (255), login VARCHAR (255), password VARCHAR(255))").executeUpdate();
            connection.createQuery("CREATE UNIQUE INDEX index_server_name on server(name)").executeUpdate();
            // connection.createQuery("drop table if exists page").executeUpdate();
            connection.createQuery("create table IF NOT EXISTS page (_id INTEGER PRIMARY KEY AUTOINCREMENT, html_conflicted BOOLEAN, groovy_conflicted BOOLEAN, html_path VARCHAR(255), groovy_path VARCHAR(255), client_groovy TEXT, client_html TEXT, server_groovy TEXT, server_html TEXT, page_id VARCHAR(100), client_groovy_crc32 VARCHAR(100), server_groovy_crc32 VARCHAR(100), client_html_crc32 VARCHAR(100), server_html_crc32 VARCHAR(100))").executeUpdate();
            connection.createQuery("CREATE INDEX index_page_server_groovy_crc32 on page(server_groovy_crc32)").executeUpdate();
            connection.createQuery("CREATE INDEX index_page_server_html_crc32 on page(server_html_crc32)").executeUpdate();
            connection.createQuery("CREATE INDEX index_page_client_groovy_crc32 on page(client_groovy_crc32)").executeUpdate();
            connection.createQuery("CREATE INDEX index_page_client_html_crc32 on page(client_html_crc32)").executeUpdate();
            connection.createQuery("CREATE INDEX index_page_html_path on page(html_path)").executeUpdate();
            connection.createQuery("CREATE INDEX index_page_groovy_path on page(groovy_path)").executeUpdate();
            connection.createQuery("CREATE INDEX index_page_groovy_conflicted on page(groovy_conflicted)").executeUpdate();
            connection.createQuery("CREATE INDEX index_page_html_conflicted on page(html_conflicted)").executeUpdate();
            connection.createQuery("CREATE INDEX index_page_page_id on page(page_id)").executeUpdate();
            // connection.createQuery("drop table if exists rest").executeUpdate();
            connection.createQuery("create table IF NOT EXISTS rest (_id INTEGER PRIMARY KEY AUTOINCREMENT, groovy_conflicted BOOLEAN, groovy_path VARCHAR(255), client_groovy TEXT, server_groovy TEXT, rest_id VARCHAR(100), server_groovy_crc32 VARCHAR(100), client_groovy_crc32 VARCHAR(100))").executeUpdate();
            connection.createQuery("CREATE INDEX index_rest_server_groovy_crc32 on rest(server_groovy_crc32)").executeUpdate();
            connection.createQuery("CREATE INDEX index_rest_client_groovy_crc32 on rest(client_groovy_crc32)").executeUpdate();
            connection.createQuery("CREATE INDEX index_rest_groovy_path on rest(groovy_path)").executeUpdate();
            connection.createQuery("CREATE INDEX index_rest_groovy_conflicted on rest(groovy_conflicted)").executeUpdate();
            connection.createQuery("CREATE INDEX index_rest_page_id on rest(rest_id)").executeUpdate();
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

    public static void main(String[] args) {
        ensureDatabase("mbaas.db");
        Sql2o sql2o = new Sql2o("jdbc:sqlite:mbaas.db", "", "");
        try (org.sql2o.Connection connection = sql2o.open()) {
            {
                Query query = connection.createQuery("insert into server(name, address, login, password) values(:name, :address, :login, :password)");
                query.addParameter("name", "PROD");
                query.addParameter("address", "http://yp.angkorteam.com");
                query.addParameter("login", "service");
                query.addParameter("password", "service");
                query.executeUpdate();
            }
            {
                Query query = connection.createQuery("select * from server");
                List<Map<String, Object>> rows = query.executeAndFetchTable().asList();
                for (Map<String, Object> row : rows) {
                    System.out.println(row.get("name"));
                }
            }
        }
    }

}
