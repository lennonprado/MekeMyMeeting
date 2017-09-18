package com.clases;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue
    private int id;
    private String nombre;

    @OneToMany(mappedBy = "usuarioNotificado", cascade = CascadeType.PERSIST)
    private List<Notificacion> notificaciones;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.PERSIST)
    private List<Calendario> calendarios;

    public Usuario(){}

    public Usuario(String nombre) {
        this.nombre = nombre;
        notificaciones = new ArrayList<>();
        calendarios = new ArrayList<>();
        crearCalendario(); // Se crea un calendario por default
    }

    public void agregarReunion(Reunion r, Calendario c) {
        if(!estoyOcupado(r)) {
            if (calendarios.contains(c)) {
                c.agregarReunion(r);
            }
        }
    }

    public void notificar(Notificacion n) {
        notificaciones.add(n);

    }

    /**
     * Comparte un calendario con otros usuarios
     */
    public void compartir(Usuario u, Calendario c ) {
        u.agregarCalendario(c);
    }

    public void agregarCalendario(Calendario c) {
        this.calendarios.add(c);
    }

    public void crearCalendario() {
        Calendario c = new Calendario(this);
        calendarios.add(c);
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
        Usuario u = (Usuario) obj;
        return u.id == id && u.nombre.equals(nombre);
    }

    public int getId() {
        return id;
    }

    public void aceptar(Notificacion n, Calendario c){
        if(!estoyOcupado(n.getReunion())) {
            c.agregarReunion(n.getReunion());
            this.notificaciones.remove(n);
        }
    }

    public void aceptar(Notificacion n){
        aceptar(n, getDefaultCalendario());
    }

    public void cancelar(Notificacion n){
        this.notificaciones.remove(n);
    }

    public Calendario getDefaultCalendario(){
        return calendarios.get(0);
    }

    public boolean estoyOcupado(Reunion r){
         for (Calendario c : this.calendarios) {
             for (Reunion reunion : c.getReuniones()) {
                 if (Reunion.seSuperponen(r, reunion)) return true;
             }
         }
         return false;
    }

}
