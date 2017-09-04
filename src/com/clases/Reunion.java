package com.clases;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.*;

//@Entity
public class Reunion {
	
	//@Id
	//@GeneratedValue
	private int idReunion;
	private Date fechaInicio;
	private Date fechaFin;
	private int duracion;
	private Sala lugar;
	private Usuario dueno;
	private List<Usuario> invitados;
	
	public Reunion() {
	}

	public Reunion(Date fechaInicio, int duracion, Sala lugar, Usuario dueno) {
		super();
		this.fechaInicio = fechaInicio;
		this.duracion = duracion;
		this.lugar = lugar;
		this.dueno = dueno;
		this.fechaFin = getFechaFin();
	}
	
	/**
	 * Se obtiene la fecha fin dada la fecha de inicio de la reunion, agregandole la duracion (en horas) asignada
	 * @return La fecha fin
	 */
	public Date getFechaFin() {
		if(fechaFin != null) return fechaFin;
		Calendar cal = Calendar.getInstance();
	    cal.setTime(fechaInicio);
		Date fin = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY) + duracion, cal.get(Calendar.MINUTE)).getTime();
		return fin;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public Sala getLugar() {
		return lugar;
	}

	public void setLugar(Sala lugar) {
		this.lugar = lugar;
	}

	public Usuario getDueno() {
		return dueno;
	}

	public void setDueno(Usuario dueno) {
		this.dueno = dueno;
	}

	public List<Usuario> getInvitados() {
		return invitados;
	}

	public void addInvitado(Usuario i) {
		this.invitados.add(i);
	}
	
	public void deleteInvitado(Usuario i) {
		//this.invitados.add(i);
	}
	
	public void notificar(){
		// vemos
	}
	
}
