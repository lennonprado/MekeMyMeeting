package com.clases;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue
    private int id;
    private String nombre;

    @OneToMany(mappedBy = "usuarioNotificado")
    private List<Notificacion> notificaciones;

    @OneToMany(mappedBy = "usuario")
    private List<Calendario> calendarios;

    // Lista de reuniones en la cual el usuario fue invitado
    @ManyToMany(mappedBy = "invitados")
    private List<Reunion> invitaciones;

    public Usuario(String nombre) {
        this.nombre = nombre;
        notificaciones = new ArrayList<>();
        calendarios = new ArrayList<>();
        crearCalendario(); // Se crea un calendario por default
    }

    /**
     * Agrega una reunion a un determinado calendario perteneciente al usuario
     *
     * @param fechaInicio Fecha inicial de la reunion
     * @param duracion    Duracion de la reunion
     * @param lugar       Lugar donde se realizara la reunion
     * @param c           Calendario donde se agregara la reunion
     */
    public Reunion nuevaReunion(Date fechaInicio, int duracion, Sala lugar, Calendario c) {
        Reunion r1 = new Reunion(fechaInicio, duracion, lugar, this);
        if (calendarios.contains(c)) {
            c.agregarReunion(r1);
        }
        return r1;
    }


    public void notificar(Notificacion n) {
        notificaciones.add(n);
    }



    /**
     * Comparte un calendario con otros usuarios
     */
    public void compartir() {

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

    public boolean equals(Object obj) {
        return ((Usuario) obj).getId() == id;
    }

    public int getId() {
        return id;
    }
}
