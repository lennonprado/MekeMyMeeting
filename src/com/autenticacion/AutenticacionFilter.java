package com.autenticacion;

import com.autenticacion.Secured;
import com.autenticacion.TokenHelper;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AutenticacionFilter implements ContainerRequestFilter {

    public void filter(ContainerRequestContext ctx) throws IOException {
        String authHeader = ctx.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader == null) throw new NotAuthorizedException("Bearer");

        String token = parseToken(authHeader);

        if (!TokenHelper.tokenValido(token)) {
            throw new NotAuthorizedException("Bearer error=\"invalid_token\"");
        }

    }

    private String parseToken(String header) {
        return header.substring("Bearer".length()+1).trim();
    }

}