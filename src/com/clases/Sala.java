package com.clases;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
@NamedQueries({

@NamedQuery(name = Sala.BUSCAR_SALAS, query = "SELECT s FROM Sala s"),
@NamedQuery(name = Sala.BUSCAR_SALA, query = "SELECT s FROM Sala s WHERE s.idSala = :id"),

})
@Entity
@JsonIgnoreProperties({ "reuniones" })
public class Sala {

    public static final String BUSCAR_SALAS = "Sala.buscarSalas";
    public static final String BUSCAR_SALA = "Sala.buscar";

    @Id
    @GeneratedValue
    private int idSala;
    private int cantPersonas;
    private String nombre;
    private String direccion;
    @OneToMany(mappedBy = "lugar", cascade = CascadeType.PERSIST)
    private List<Reunion> reuniones; // Reuniones que se realizaran en esta sala

    public Sala() {
    }

    public Sala(int cantPersonas, String nombre, String direccion) {
        this.cantPersonas = cantPersonas;
        this.nombre = nombre;
        this.direccion = direccion;
        this.reuniones = new ArrayList<>();
    }

    /**
     * Comprueba si la sala esta ocupada para una nueva reunion
     *
     * @param r Reunion a comprobar
     * @return Si la reunion esta ocupada o no.
     */
    public boolean ocupado(Reunion r) {
        for (Reunion reunion : reuniones) {
            if (Reunion.seSuperponen(r, reunion)) return true;
        }
        return false;
    }

    public int getCantPersonas() {
        return cantPersonas;
    }

    public void setCantPersonas(int cantPersonas) {
        this.cantPersonas = cantPersonas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Reunion> getReuniones() {
        return reuniones;
    }

    public void setReuniones(List<Reunion> reuniones) {
        this.reuniones = reuniones;
    }


    public String toString() {
        return "Sala{" +
                "cantPersonas=" + cantPersonas +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                '}' + System.getProperty("line.separator");
    }


    public void addReunion(Reunion r) {
        reuniones.add(r);
    }

    public int getIdSala() {
        return idSala;
    }
}