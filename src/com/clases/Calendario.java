package com.clases;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Calendario {

    @Id
    @GeneratedValue
    private int id;

    @ManyToMany(mappedBy="calendarios")
	private List<Reunion> reuniones;

    // Usuario propietario del calendario
    @ManyToOne
	private Usuario usuario;
	
	public Calendario() {
		reuniones = new ArrayList<>();
	}
	
	public void agregarReunion(Reunion r) {
		
	}

    public int getId() {
        return id;
    }


    public boolean equals(Object obj) {
        return ((Calendario)obj).getId() == id;
    }
}
