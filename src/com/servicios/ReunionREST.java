package com.servicios;

import com.clases.*;

import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Path("/reuniones")
public class ReunionREST {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public static Response crearReunion(){
        return null;
//        boolean usuarioValido = EMF.persist(u);
//        if (usuarioValido) return Response.status(201).entity(u).build();
//        else throw new UsuarioREST.RecursoDuplicado(u.getNombre());
    }


    /**
     * @return Se obtienen todas las reuniones existentes
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public static List<Reunion> getReuniones() {
        Query query = EMF.getEntityManager().createNamedQuery(Reunion.BUSCAR_REUNIONES);
        return query.getResultList();
    }

    /**
     * Dado un usuario y un día se obtienen todas sus reuniones en su calendario
     *
     * @param username Nombre de usuario a buscar las reuniones
     * @param dia      Dia en el cual se realizara la reunion
     * @return Reuniones del usuario en cierto dia
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public static List<Reunion> getReuniones(@PathParam("id") String username,
                                             @QueryParam("dia") String dia,
                                             @QueryParam("fechaInicio") String sFechaInicio,
                                             @QueryParam("fechaFin") String sFechaFin) {

        if (dia == null)  return getReuniones(username, sFechaInicio, sFechaFin);
        else {
            Query query = EMF.getEntityManager().createNamedQuery(Usuario.REUNIONES_USUARIO);
            query.setParameter("usuario", username);
            query.setParameter("dia", Integer.parseInt(dia));
            return query.getResultList();
        }

    }

    /**
     * Dado un usuario se obtienen todas las reuniones en un rango de fechas
     *
     * @param username     Nombre de usuario a buscar las reuniones
     * @param sFechaInicio Fecha inicial (dd-MM-yyyy)
     * @param sFechaFin    Fecha final (dd-MM-yyyy)
     * @return Reuniones del usuario que esten entre la fecha inicio y fin
     */
    public static List<Reunion> getReuniones(String username, String sFechaInicio, String sFechaFin) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        Date fechaInicio = null;
        Date fechaFin = null;
        try {
            fechaInicio = formatter.parse(sFechaInicio);
            fechaFin = formatter.parse(sFechaFin);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // http://localhost:8081/api/reuniones/pablito?fechaInicio=10-05-2010&fechaFin=05-01-2019

        Query query = EMF.getEntityManager().createNamedQuery(Usuario.REUNIONES_USUARIO_RANGO);
        query.setParameter("usuario", username);
        query.setParameter("fechaInicio", fechaInicio);
        query.setParameter("fechaFin", fechaFin);
        return query.getResultList();
    }

    /**
     * Dado un usuario y una nueva reunión se obtienen todas las reuniones que superpongan
     * con una nueva reunion
     *
     * @param u Usuario a buscar las reuniones
     * @param r Nueva reunion para comprobar superpocion
     * @return Todas las reuniones que se superponen con la nueva.
     */
    public static List<Reunion> getReuniones(Usuario u, Reunion r) {
        Query query = EMF.getEntityManager().createNamedQuery(Usuario.REUNIONES_USUARIO_SUPERPONGAN);
        query.setParameter("usuario", u);
        query.setParameter("fechaInicio", r.getFechaInicio());
        query.setParameter("fechaFin", r.getFechaFin());
        return query.getResultList();
    }

}
