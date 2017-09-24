package com.app;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.EntityManager;

import com.clases.*;
import com.servicios.EMF;
import com.servicios.Servicios;

public class Main {

	public static void main(String[] args) {
		EntityManager manager = EMF.getEntityManager();

        manager.getTransaction( ).begin( );

		Usuario u1 = new Usuario("Pablito");
		Usuario u2 = new Usuario("Roberto");
		Usuario u3 = new Usuario("Julio");
		Usuario u4 = new Usuario("Ezequiel");
		Usuario u5 = new Usuario("Mariano");
		Usuario u6 = new Usuario("Marcos");

		Sala sala1 = new Sala(5, "Sala 1", "Direccion 1");
		Sala sala2 = new Sala(7, "Sala 2", "Direccion 2");

		// 19 de noviembre de 2017 12:30pm
		Date fechaR1 = new GregorianCalendar(2017, Calendar.NOVEMBER, 19, 12, 30).getTime();

        // 20 de noviembre de 2017 19:30pm
        Date fechaR2 = new GregorianCalendar(2017, Calendar.NOVEMBER, 20, 19, 30).getTime();

        Reunion r1 = new Reunion(fechaR1, 2, sala1, u1);
        Reunion r2 = new Reunion(fechaR2, 2, sala2, u2);

        r1.addInvitado(u2);
        r1.addInvitado(u3);
        r1.addInvitado(u4);

        // Aceptamos la notificacion de la primer reunion
        Notificacion n = u2.getNotificaciones().get(0);
        u2.aceptar(n);

		n = u3.getNotificaciones().get(0);
		u3.aceptar(n);

		n = u4.getNotificaciones().get(0);
		u4.aceptar(n);

        r2.addInvitado(u3);
  //      r2.addInvitado(u4);

        // Aceptamos la notificacion de la segunda reunion
        n = u3.getNotificaciones().get(0);
        u3.aceptar(n);


		manager.persist(u1);
		manager.persist(u2);
		manager.persist(u3);
		manager.persist(u4);
		manager.persist(u5);
		manager.persist(u6);

		manager.getTransaction( ).commit( );

        System.out.println(Servicios.getUsuarios());



		EMF.close();
	}

}
