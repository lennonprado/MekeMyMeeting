package com.clases;

import javax.persistence.*;

@Entity
public class Notificacion {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
	private Usuario usuarioNotificado;

    @ManyToOne
	private Reunion reunion;
	
	private Estado estado;
	
	enum Estado{
		ACEPTADA,
        RECHAZADA,
        PENDIENTE
	}

	public Notificacion() {
	}

	public Notificacion(Reunion reunion) {
		this.reunion = reunion;
		this.estado = Estado.PENDIENTE;
	}

	public Reunion getReunion() {
		return reunion;
	}

	public void setReunion(Reunion reunion) {
		this.reunion = reunion;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public void aceptar(){
		this.estado = Estado.ACEPTADA;
		// Agregamos la reunion al calendario
	}
	
	public void cancelar(){
		this.estado = Estado.RECHAZADA;
		// Borramos esta notificaon de la lista del usuario
	}
	
	public void esperar(){
		this.estado = Estado.PENDIENTE;
	}
	
}
