package com.servicios;

import com.autenticacion.Secured;
import com.entidades.Sala;
import com.entidades.Usuario;

import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/salas")
public class SalaREST {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> getSalas() {
        Query query = EMF.getEntityManager().createNamedQuery(Sala.BUSCAR_SALAS);
        return query.getResultList();
    }


    /*
        {
	    "cantPersonas": 56,
	    "nombre": "Sala 4",
	    "direccion": "Direccion 4"
        }
     */
    @POST
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearSala(Sala s) {
        boolean salaValida = EMF.persist(s);
        if (salaValida) return Response.status(201).entity(s).build();
        else throw new RecursoDuplicado(s.getNombre());
    }

    @DELETE
    @Secured
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSala(@PathParam("id") int id) {
        Query query = EMF.getEntityManager().createNamedQuery(Sala.BUSCAR_SALA);
        query.setParameter("id", id);
        try {
            Sala s = (Sala) query.getSingleResult();
            if (EMF.delete(s)) return Response.status(200).build();
        }
        catch (Exception e) {
            throw new RecursoNoExiste(id);
        }

        throw new RecursoNoExiste(id);
    }


}
