package com.servicios;

import com.autenticacion.Secured;
import com.clases.Usuario;

import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/usuarios")
public class UsuarioREST {

    public UsuarioREST() {
    }

    private Usuario createUser(Map<String, String> umap){
        Usuario u = new Usuario();
        u.setNombreUsuario(umap.get("nombreUsuario"));
        u.setNombre(umap.get("nombre"));
        u.setApellido(umap.get("apellido"));
        u.setPassword(umap.get("password"));
        return u;
    }

    /*
    {
	"nombreUsuario":"julio12",
	"nombre": "Julio",
	"apellido": "Sas",
	"password": "213"
    }
     */

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearUsuario(Map<String, String> umap) {
        Usuario u = createUser(umap);
        boolean usuarioValido = EMF.persist(u);
        if (usuarioValido) return Response.status(201).entity(u).build();
        else throw new RecursoDuplicado(u.getNombreUsuario());
    }

    @PUT
    @Secured
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUsuario(@PathParam("id") String id, Map<String, String> umap) {
        Usuario user = getUsuario(id);  // TODO controlar usuario inexistente
        Usuario newUser = createUser(umap);

        user.setNombre(newUser.getNombre());
        user.setPassword(newUser.getPassword());
        user.setApellido(newUser.getApellido());
        EMF.persist(user);
        return Response.status(201).entity(user).build();
    }

    /**
     * @return Se obtienen todos los usuarios existentes
     */
    @GET
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
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario getUsuario(@PathParam("id") String u) {// TODO controlar usuario inexistente
        Query query = EMF.getEntityManager().createNamedQuery(Usuario.BUSCAR_USUARIO);
        query.setParameter("usuario", u);
        return (Usuario)query.getSingleResult();
    }


    @DELETE
    @Secured
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUsuario(@PathParam("id") String username) {
        Query query = EMF.getEntityManager().createNamedQuery(Usuario.BUSCAR_USUARIO);
        query.setParameter("usuario", username);

        try {
            Usuario s = (Usuario) query.getSingleResult();
            if (EMF.delete(s)) return Response.status(200).build();
        }
        catch (Exception e) {
            throw new RecursoNoExiste(username);
        }

        throw new RecursoNoExiste(username);
    }




}
