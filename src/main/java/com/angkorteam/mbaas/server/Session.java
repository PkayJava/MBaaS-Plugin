package com.angkorteam.mbaas.server;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

/**
 * Created by socheat on 12/21/16.
 */
public class Session extends AuthenticatedWebSession {

    public Session(Request request) {
        super(request);
    }

    @Override
    protected boolean authenticate(String username, String password) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Roles getRoles() {
        throw new UnsupportedOperationException();
    }

    public final String getUserId() {
        throw new UnsupportedOperationException();
    }
}
