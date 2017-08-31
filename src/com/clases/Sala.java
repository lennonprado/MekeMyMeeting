package com.clases;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Sala {
	
	@Id
	@GeneratedValue
	int idSala;
	int cantPersonas;
	String nombre;
	String direccion;	
	List<Reunion> reuniones;
		
	public Sala() {
	}
	
	public Sala(int cantPersonas, String nombre, String direccion) {
		super();
		this.cantPersonas = cantPersonas;
		this.nombre = nombre;
		this.direccion = direccion;
		this.reuniones = new ArrayList<Reunion>();
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

	public void addReunion(Reunion r) {
		this.reuniones.add(r);
	}	
	
	public void deleteReunion(Reunion r){
		
	}
	
	public boolean ocupado(Date d){
		// for para recorrer reuniones y comprar que el date sera menor a la fecha o mayor a la fecha mas la duracion
		return true;
	}
	
}
