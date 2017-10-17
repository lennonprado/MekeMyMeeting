package com.servicios;

import com.autenticacion.Credencial;
import com.autenticacion.TokenHelper;
import com.clases.Usuario;
import com.servicios.EMF;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/autenticacion")
public class AutenticacionService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response autenticarUser(Credencial credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();


        if (usuarioValido(username, password)) {
            String token = TokenHelper.generarToken(username);
            return Response.ok(token).build();
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();

    }

    private boolean usuarioValido(String username, String password) {
        Usuario resultado;
        Usuario user = new Usuario();
        user.setNombreUsuario(username);
        user.setPassword(password);

        try {
            resultado = EMF.getEntityManager().find(Usuario.class, username);
        }
        catch (Exception e){
            return false;
        }

        return resultado.equals(user);

    }

}
