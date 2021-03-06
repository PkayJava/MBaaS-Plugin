package com.angkorteam.mbaas.server.spring;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by socheat on 11/4/16.
 */
public interface RestService {

    ResponseEntity<?> service(HttpServletRequest request, Map<String, String> pathVariables) throws Throwable;

    default String lookupUserRole(HttpServletRequest request, Map<String, String> pathVariables) {
        throw new UnsupportedOperationException();
    }

    String getRestUUID();

}
