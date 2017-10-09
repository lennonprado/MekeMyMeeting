package com.app;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.EntityManager;

import com.clases.*;
import com.servicios.EMF;
import com.servicios.ReunionREST;

public class Main {

    public static void main(String[] args) {
        EntityManager manager = EMF.getEntityManager();

        manager.getTransaction().begin();

        Usuario u1 = new Usuario("Pablito");
        Usuario u2 = new Usuario("Roberto");
        Usuario u3 = new Usuario("Julio");
        Usuario u4 = new Usuario("Ezequiel");
        Usuario u5 = new Usuario("Mariano");
        Usuario u6 = new Usuario("Marcos");
        Usuario u7 = new Usuario("Pedro");
        Usuario u8 = new Usuario("Lucia");
        Usuario u9 = new Usuario("Gustavo");
        Usuario u10 = new Usuario("Liliana");
        Usuario u11 = new Usuario("Santiago");


        Sala sala1 = new Sala(5, "Sala 1", "Direccion 1");
        Sala sala2 = new Sala(4, "Sala 2", "Direccion 2");
        Sala sala3 = new Sala(9, "Sala 3", "Direccion 3");

        // 19 de noviembre de 2017 12:30hs
        Date fechaR1 = new GregorianCalendar(2017, Calendar.NOVEMBER, 30, 19, 30).getTime();

        // 20 de noviembre de 2017 19:30hs
        Date fechaR2 = new GregorianCalendar(2017, Calendar.NOVEMBER, 22, 19, 30).getTime();

        // 13 de octubre de 2017 14:00hs
        Date fechaR3 = new GregorianCalendar(2017, Calendar.OCTOBER, 13, 14, 0).getTime();

        // 22 de septiembre de 2017 15:00hs
        Date fechaR4 = new GregorianCalendar(2017, Calendar.SEPTEMBER, 22, 15, 0).getTime();

        // 11 de enero de 2017 9:00hs
        Date fechaR5 = new GregorianCalendar(2017, Calendar.JANUARY, 11, 9, 0).getTime();

        // 30 de diciembre de 2017 11:30hs
        Date fechaR6 = new GregorianCalendar(2017, Calendar.DECEMBER, 30, 11, 30).getTime();

        // 5 de diciembre de 2017 18:00hs
        Date fechaR7 = new GregorianCalendar(2017, Calendar.DECEMBER, 5, 18, 0).getTime();

        // 20 de agosto de 2017 14:00hs
        Date fechaR8 = new GregorianCalendar(2017, Calendar.AUGUST, 20, 14, 0).getTime();

        // 7 de octubre de 2017 19:00hs
        Date fechaR9 = new GregorianCalendar(2017, Calendar.OCTOBER, 7, 19, 0).getTime();

        // 30 de diciembre de 2017 11:00hs
        Date fechaR10 = new GregorianCalendar(2017, Calendar.DECEMBER, 30, 11, 0).getTime();


        Reunion r1 = new Reunion(fechaR1, 2, sala3, u1);
        Reunion r2 = new Reunion(fechaR2, 3, sala2, u2);
        Reunion r3 = new Reunion(fechaR3, 1, sala1, u9);
        Reunion r4 = new Reunion(fechaR4, 5, sala1, u8);
        Reunion r5 = new Reunion(fechaR5, 4, sala3, u10);
        Reunion r6 = new Reunion(fechaR6, 3, sala3, u1);
        Reunion r7 = new Reunion(fechaR7, 1, sala2, u2);
        Reunion r8 = new Reunion(fechaR8, 1, sala2, u4);
        Reunion r9 = new Reunion(fechaR9, 2, sala1, u3);
        Reunion r10 = new Reunion(fechaR10, 3, sala2, u11);


        // Reunion 1
        r1.addInvitado(u2);
        r1.addInvitado(u3);
        r1.addInvitado(u4);

        aceptarNotificacion(u2);
        aceptarNotificacion(u3);
        aceptarNotificacion(u4);

        // Reunion 2
        r2.addInvitado(u3);
        r2.addInvitado(u4);

        aceptarNotificacion(u3);
        aceptarNotificacion(u4);

        // Reunion 3
        r3.addInvitado(u3);
        r3.addInvitado(u4);
        r3.addInvitado(u6);
        r3.addInvitado(u7);

        aceptarNotificacion(u3);
        aceptarNotificacion(u4);
        aceptarNotificacion(u7);

        // Reunion 4
        r4.addInvitado(u1);
        r4.addInvitado(u7);
        r4.addInvitado(u2);
        r4.addInvitado(u3);
        r4.addInvitado(u9);

        aceptarNotificacion(u1);
        aceptarNotificacion(u7);
        aceptarNotificacion(u2);

        // Reunion 5

        r5.addInvitado(u1);
        r5.addInvitado(u4);
        aceptarNotificacion(u1);

        // Reunion 6

        r6.addInvitado(u2);
        r6.addInvitado(u3);
        r6.addInvitado(u4);
        r6.addInvitado(u5);

        aceptarNotificacion(u2);
        aceptarNotificacion(u3);
        aceptarNotificacion(u4);
        aceptarNotificacion(u5);

        // Reunion 7

        r7.addInvitado(u1);
        r7.addInvitado(u10);
        r7.addInvitado(u9);

        aceptarNotificacion(u1);
        aceptarNotificacion(u10);

        // Reunion 8
        r8.addInvitado(u9);
        r8.addInvitado(u8);
        r8.addInvitado(u10);
        r8.addInvitado(u7);

        aceptarNotificacion(u9);

        // Reunion 9

        r9.addInvitado(u5);
        r9.addInvitado(u1);
        r9.addInvitado(u7);

        aceptarNotificacion(u5);

        // Reunion 10

        r10.addInvitado(u5);
        r10.addInvitado(u1);
        r10.addInvitado(u2);
        r10.addInvitado(u3);
        r10.addInvitado(u4);
        r10.addInvitado(u6);

        aceptarNotificacion(u5); // No puede aceptar, tiene la reunion 6 al mismo horario
        aceptarNotificacion(u1);
        aceptarNotificacion(u2); // No puede aceptar, tiene la reunion 6 al mismo horario
        aceptarNotificacion(u3);
        aceptarNotificacion(u4);

        manager.persist(u1);
        manager.persist(u2);
        manager.persist(u3);
        manager.persist(u4);
        manager.persist(u5);
        manager.persist(u6);
        manager.persist(u7);
        manager.persist(u8);
        manager.persist(u9);
        manager.persist(u10);
        manager.persist(u11);

        manager.getTransaction().commit();

        System.out.println(ReunionREST.getReuniones());
        System.out.println("-----------------------");
        System.out.println(ReunionREST.getReuniones(u1, 30));
        System.out.println("-----------------------");
        System.out.println(ReunionREST.getReuniones(u1, new GregorianCalendar(2017, Calendar.DECEMBER, 1).getTime(), new GregorianCalendar(2017, Calendar.DECEMBER, 31).getTime()));

        // ReunionREST.clearDatabase();

        EMF.close();
    }

    public static void aceptarNotificacion(Usuario u) {
        if (!u.getNotificaciones().isEmpty()) {
            Notificacion n = u.getNotificaciones().get(0);
            u.aceptar(n);
        }
    }

}
