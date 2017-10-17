package com.servicios;

import com.autenticacion.Secured;
import com.clases.Usuario;

import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/usuarios")
public class UsuarioREST {

    public UsuarioREST() {
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearUsuario(Usuario u) {
        boolean usuarioValido = EMF.persist(u);
        if (usuarioValido) {
            return Response.status(201).entity(u).build();
        } else {
            throw new RecursoDuplicado(u.getNombre());
        }
    }

    public void updateUsuario() {

    }

    /**
     * @return Se obtienen todos los usuarios existentes
     */
    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> getUsuarios() {
        Query query = EMF.getEntityManager().createNamedQuery(Usuario.BUSCAR_USUARIOS);
        return query.getResultList();
    }

    /**
     * Se obtiene los datos de un usuario
     *
     * @param u Usuario a buscar
     * @return Datos completos del usuario
     */
    public Usuario getUsuario(Usuario u) {
        Query query = EMF.getEntityManager().createNamedQuery(Usuario.BUSCAR_USUARIO);
        query.setParameter("usuario", u);
        return (Usuario) query.getSingleResult();
    }

    public class RecursoDuplicado extends WebApplicationException {
        public RecursoDuplicado(String id) {
            super(Response.status(Response.Status.CONFLICT)
                    .entity("El recurso con ID " + id + " ya existe").type(MediaType.TEXT_PLAIN).build());
        }
    }

    public class RecursoNoExiste extends WebApplicationException {
        public RecursoNoExiste(int id) {
            super(Response.status(Response.Status.NOT_FOUND)
                    .entity("El recurso con id " + id + " no fue encontrado").type(MediaType.TEXT_PLAIN).build());
        }
    }
}
