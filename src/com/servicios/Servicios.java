package com.servicios;

import com.clases.*;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class Servicios {

    /**
     * @return Se obtienen todos los usuarios existentes
     */
    public static List<Usuario> getUsuarios() {
        Query query = EMF.getEntityManager().createNamedQuery(Usuario.BUSCAR_USUARIOS);
        return query.getResultList();
    }

    /**
     * Se obtiene los datos de un usuario
     *
     * @param u Usuario a buscar
     * @return Datos completos del usuario
     */
    public static Usuario getUsuario(Usuario u) {
        Query query = EMF.getEntityManager().createNamedQuery(Usuario.BUSCAR_USUARIO);
        query.setParameter("usuario", u);
        return (Usuario) query.getSingleResult();
    }


    /**
     * @return Se obtienen todas las reuniones existentes
     */
    public static List<Reunion> getReuniones() {
        Query query = EMF.getEntityManager().createNamedQuery(Reunion.BUSCAR_REUNIONES);
        return query.getResultList();
    }

    /**
     * Dado un usuario y un día se obtienen todas sus reuniones en su calendario
     *
     * @param u   Usuario a buscar las reuniones
     * @param dia Dia en el cual se realizara la reunion
     * @return Reuniones del usuario en cierto dia
     */
    public static List<Reunion> getReuniones(Usuario u, int dia) {
        Query query = EMF.getEntityManager().createNamedQuery(Usuario.REUNIONES_USUARIO);
        query.setParameter("usuario", u);
        query.setParameter("dia", dia);
        return query.getResultList();
    }

    /**
     * Dado un usuario se obtienen todas las reuniones en un rango de fechas
     *
     * @param u           Usuario a buscar las reuniones
     * @param fechaInicio Fecha inicial
     * @param fechaFin    Fecha final
     * @return Reuniones del usuario que esten entre la fecha inicio y fin
     */
    public static List<Reunion> getReuniones(Usuario u, Date fechaInicio, Date fechaFin) {
        Query query = EMF.getEntityManager().createNamedQuery(Usuario.REUNIONES_USUARIO_RANGO);
        query.setParameter("usuario", u);
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

    public static void clearDatabase(){
        EMF.clear();
    }
}
