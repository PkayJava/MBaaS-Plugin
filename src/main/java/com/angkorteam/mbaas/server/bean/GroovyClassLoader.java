package com.angkorteam.mbaas.server.bean;

import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.File;
import java.security.CodeSource;

/**
 * Created by socheat on 12/12/16.
 */
public class GroovyClassLoader extends groovy.lang.GroovyClassLoader {

    public void removeClassCache(String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected CompilationUnit createCompilationUnit(CompilerConfiguration config, CodeSource source) {
        throw new UnsupportedOperationException();
    }

    public void removeSourceCache(String key) {
        throw new UnsupportedOperationException();
    }

    public File writeGroovy(String fullJavaClass, String script) {
        throw new UnsupportedOperationException();
    }

    public Class<?> compileGroovy(File groovyFile) {
        throw new UnsupportedOperationException();
    }

    public Class<?> compileGroovy(String fullJavaClass) {
        throw new UnsupportedOperationException();
    }
}
