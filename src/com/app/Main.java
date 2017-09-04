package com.app;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.clases.*;

public class Main {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("makemymeeting");
		EntityManager manager = emf.createEntityManager();
		
		
		Usuario u1 = new Usuario("Pablito");
		Usuario u2 = new Usuario("Roberto");
		Usuario u3 = new Usuario("Julio");
		Usuario u4 = new Usuario("Ezequiel");
		Usuario u5 = new Usuario("Mariano");
		Usuario u6 = new Usuario("Marcos");
		
		
		Sala sala1 = new Sala(5, "Sala 1", "Direccion 1");
		Sala sala2 = new Sala(7, "Sala 2", "Direccion 2");
		
		
		// 19 de noviembre de 2017 12:30 hs
		Date fechaR1 = new GregorianCalendar(2017, Calendar.NOVEMBER, 19, 12, 30).getTime();
		
		
		Calendario c = u1.getCalendarios().get(0);
		u1.nuevaReunion(fechaR1, 2, sala1, c);
		
		
		manager.close();
		emf.close();

	}

}
