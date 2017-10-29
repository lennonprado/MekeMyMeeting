package com.servicios;

import com.autenticacion.Secured;
import com.entidades.*;

import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Path("/reuniones")
public class ReunionREST {


    /*
    {
	"lugar": "3",
	"usuario": "Pablito",
	"fechaInicio": "28-10-2017-14:30",
	"duracion": "3"
    }
    * */
    @POST
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public static Response crearReunion(Map<String, String> rmap){
        Sala sala = EMF.getEntityManager().find(Sala.class, Integer.parseInt(rmap.get("lugar")));
        Usuario usuario = EMF.getEntityManager().find(Usuario.class, rmap.get("usuario"));

        Date fechaInicio = null;

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH:mm");
        try {
            fechaInicio = formatter.parse(rmap.get("fechaInicio"));  // dd-MM-yyyy-HH:mm
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        int duracion = Integer.parseInt(rmap.get("duracion"));
        Reunion r = new Reunion(fechaInicio, duracion, sala, usuario);


        boolean reunionValida = EMF.persist(r);
        if (reunionValida) return Response.status(201).entity(r).build();
        else return Response.status(Response.Status.CONFLICT).entity("Hubo un error al crear la reunion").build();
    }

//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public static Response crearSala(Map<String, String> rmap){
//
//
//
//        boolean reunionValida = EMF.persist(r);
//        if (reunionValida) return Response.status(201).entity(r).build();
//        else return Response.status(Response.Status.CONFLICT).entity("Hubo un error al crear la reunion").build();
//    }


    /**
     * @return Se obtienen todas las reuniones existentes
     */
    @GET
    @Secured
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
    @Secured
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public static List<Reunion> getReuniones(@PathParam("id") String username,
                                             @QueryParam("dia") String dia,
                                             @QueryParam("fechaInicio") String sFechaInicio,
                                             @QueryParam("fechaFin") String sFechaFin,
                                             @QueryParam("duracion") String duracion) {

        if (dia != null){
            Query query = EMF.getEntityManager().createNamedQuery(Usuario.REUNIONES_USUARIO);
            query.setParameter("usuario", username);
            query.setParameter("dia", Integer.parseInt(dia));
            return query.getResultList();
        }
        else if(duracion != null) return getReuniones(username, sFechaInicio, Integer.parseInt(duracion));
        else return getReuniones(username, sFechaInicio, sFechaFin);
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
     * @param fechaInicio Fecha de inicio de la nueva reunion para comprobar superpocion
     * @param duracion Duracion de la nueva reunion a comprobar.
     * @return Todas las reuniones que se superponen con la nueva.
     */
    public static List<Reunion> getReuniones(String u, String fechaInicio, int duracion) {
        Query query = EMF.getEntityManager().createNamedQuery(Usuario.REUNIONES_USUARIO_SUPERPONGAN);
        Reunion r = new Reunion();

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH:mm");
            r.setFechaInicio(formatter.parse(fechaInicio));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //http://localhost:8081/api/reuniones/pablito?fechaInicio=22-09-2017-18:00&duracion=2

        query.setParameter("usuario", u);
        query.setParameter("fechaInicio", r.getFechaInicio());
        query.setParameter("fechaFin", r.getFechaFin(duracion));
        return query.getResultList();
    }

}
