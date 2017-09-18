package com.clases;

import javax.persistence.*;
import java.util.*;

@Entity
public class Calendario {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Usuario usuario;

    @ManyToMany(mappedBy="calendarios",cascade = CascadeType.PERSIST)
	private List<Reunion> reuniones;

    public Calendario() {}

	public Calendario(Usuario u) {
	    this.usuario = u;
		reuniones = new ArrayList<>();

	}

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void agregarReunion(Reunion r) {
		this.reuniones.add(r);
		r.agregarACalendario(this);
    }

    public int getId() {
        return id;
    }

    public boolean equals(Object obj) {
        return ((Calendario)obj).getId() == id;
    }

    public List<Reunion> getReuniones() {
        return reuniones;
    }
}
