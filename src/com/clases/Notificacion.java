package com.clases;

import javax.persistence.*;

@Entity
public class Notificacion {

    @Id
    @GeneratedValue
    private int id;
	@ManyToOne
	private Usuario usuarioNotificado;
	@ManyToOne (cascade = CascadeType.PERSIST)
    private Reunion reunion;

	public Notificacion() {}

	public Notificacion(Reunion reunion, Usuario usuario) {
		this.reunion = reunion;
		this.usuarioNotificado = usuario;
	}

	public int getId() {
		return id;
	}

	public Usuario getUsuarioNotificado() {
		return usuarioNotificado;
	}

	public void setUsuarioNotificado(Usuario usuarioNotificado) {
		this.usuarioNotificado = usuarioNotificado;
	}

	public Reunion getReunion() {
		return reunion;
	}

	public void setReunion(Reunion reunion) {
		this.reunion = reunion;
	}

	public boolean equals(Object obj) {
	    Notificacion n = (Notificacion) obj;
		return n.id == id && n.reunion.equals(reunion) && n.usuarioNotificado.equals(usuarioNotificado);
	}
}
