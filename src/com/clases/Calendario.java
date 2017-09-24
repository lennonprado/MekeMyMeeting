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
	private List<Reunion> reuniones; // Reuniones que existen en este calendario

    public Calendario() {}

	public Calendario(Usuario u) {
	    this.usuario = u;
		reuniones = new ArrayList<>();
	}

    /**
     * Agrega una reunion a el calendario
     * @param r Reunion a agregar
     */
    public void agregarReunion(Reunion r) {
        this.reuniones.add(r);
        r.agregarACalendario(this);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
