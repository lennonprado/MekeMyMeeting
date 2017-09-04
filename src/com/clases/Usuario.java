package com.clases;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Usuario {
	
	private String nombre;
	private List<Notificacion> notificaciones;
	private List<Calendario> calendarios;
	
	public Usuario(String nombre) {
		this.nombre = nombre;
		notificaciones = new ArrayList<>();
		calendarios = new ArrayList<>();
		crearCalendario(); // Se crea un calendario por default
	}


	/**
	 * Agrega una reunion a un determinado calendario perteneciente al usuario
	 * @param fechaInicio Fecha inicial de la reunion
	 * @param duracion Duracion de la reunion
	 * @param lugar Lugar donde se realizara la reunion
	 * @param c Calendario donde se agregara la reunion
	 */
	public Reunion nuevaReunion(Date fechaInicio, int duracion, Sala lugar, Calendario c){
		Reunion r1 = new Reunion(fechaInicio, duracion, lugar, this);
		if(calendarios.contains(c)) {
			c.agregarReunion(r1);
		}
		return r1;
	}
	
	
	
	public void crearCalendario() {
		calendarios.add(new Calendario());
	}

	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public List<Notificacion> getNotificaciones() {
		return notificaciones;
	}


	public List<Calendario> getCalendarios() {
		return calendarios;
	}
	
	
}
