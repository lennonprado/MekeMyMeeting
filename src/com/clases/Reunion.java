package com.clases;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
public class Reunion {
	
	@Id
	@GeneratedValue
	int idReunion;
	Date fechaInicio;
	Date fechaFin;
	int duracion;
	Sala lugar;
	Usuario dueno;
	List<Usuario> invitados;
	
	public Reunion() {
	}

	public Reunion(Date fechaInicio, int duracion, Sala lugar, Usuario dueno) {
		super();
		this.fechaInicio = fechaInicio;
		this.duracion = duracion;
		this.lugar = lugar;
		this.dueno = dueno;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
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
