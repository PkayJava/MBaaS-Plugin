package com.angkorteam.mbaas.server.bean;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.ServletContext;
import java.io.File;

/**
 * Created by socheat on 11/19/16.
 */
public class System {

    public System(DSLContext context, JdbcTemplate jdbcTemplate, ServletContext servletContext) {
        throw new UnsupportedOperationException();
    }

    public Configuration getConfiguration() {
        throw new UnsupportedOperationException();
    }

    public synchronized String randomUUID() {
        throw new UnsupportedOperationException();
    }

    public String saveFile(File file) {
        throw new UnsupportedOperationException();
    }


    public String parseMimeType(String filename) {
        throw new UnsupportedOperationException();
    }

}
