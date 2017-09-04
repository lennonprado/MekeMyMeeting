package com.clases;

public class Notificacion {

	private Reunion reunion;
	
	private Estado estado;
	
	enum Estado{
		aceptda,rechazada,pendiente;
	}

	public Notificacion() {
	}

	public Notificacion(Reunion reunion) {
		this.reunion = reunion;
		this.estado = Estado.pendiente;
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
		this.estado = Estado.aceptda;
	}
	
	public void cancelar(){
		this.estado = Estado.rechazada;
	}
	
	public void esperar(){
		this.estado = Estado.pendiente;		
	}
	
}
