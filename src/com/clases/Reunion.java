package com.clases;

import java.util.*;

import javax.persistence.*;

@Entity
public class Reunion {

    @Id
    @GeneratedValue
    private int idReunion;
    private Date fechaInicio;
    private Date fechaFin;
    private int duracion;
    //private Sala lugar;

    // Calendarios en los cuales la reunion fue agregada
    @ManyToMany
    private List<Calendario> calendarios;

    @ManyToMany
    private List<Usuario> invitados;

    // Notificaciones enviadas a los usuarios de esta reunion
    @OneToMany
    private List<Notificacion> reuniones;

    public Reunion() {
    }

    public Reunion(Date fechaInicio, int duracion, Sala lugar, Usuario dueno) {
        super();
        this.fechaInicio = fechaInicio;
        this.invitados = new ArrayList<>();
        this.reuniones = new ArrayList<>();
        this.calendarios = new ArrayList<>();
        this.duracion = duracion;
//		this.lugar = lugar;
        this.fechaFin = getFechaFin();
    }

    /**
     * Se obtiene la fecha fin dada la fecha de inicio de la reunion, agregandole la duracion (en horas) asignada
     *
     * @return La fecha fin
     */
    public Date getFechaFin() {
        if (fechaFin != null) return fechaFin;
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaInicio);
        Date fin = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY) + duracion, cal.get(Calendar.MINUTE)).getTime();
        return fin;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

//	public Sala getLugar() {
//		return lugar;
//	}
//
//	public void setLugar(Sala lugar) {
//		this.lugar = lugar;
//	}
//

    public List<Usuario> getInvitados() {
        return invitados;
    }

    public void addInvitado(Usuario u) {
	    // Comprobar que el usuario que envio las invitaciones no se invite a si mismo
        this.invitados.add(u);
        u.notificar(new Notificacion(this));

    }

    public void deleteInvitado(Usuario i) {

    }


}
