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
    private boolean alerta;

    @ManyToOne
    private Usuario duenio;
    @ManyToOne (cascade = CascadeType.PERSIST)
    private Sala lugar;

    @OneToMany
    private List<Usuario> invitados;

    @ManyToMany
    private List<Calendario> calendarios;

    public Reunion() {
    }

    public Reunion(Date fechaInicio, int duracion, Sala lugar, Usuario duenio) {
        super();
        this.fechaInicio = fechaInicio;
        this.calendarios = new ArrayList<>();
        this.invitados = new ArrayList<>();
		this.setLugar(lugar);
        this.fechaFin = getFechaFin(duracion);
        this.duenio = duenio;
        this.alerta = false;
    }

    /**
     * Se obtiene la fecha fin dada la fecha de inicio de la reunion, agregandole la duracion (en horas) asignada
     *
     * @return La fecha fin
     */
    public Date getFechaFin(int duracion) {
        if (fechaFin != null) return fechaFin;
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaInicio);
        Date fin = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY) + duracion, cal.get(Calendar.MINUTE)).getTime();
        return fin;
    }

    public Date getFechaFin() {
        return fechaFin;
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

    public Usuario getDuenio() {
        return duenio;
    }

    public int getIdReunion() {
        return idReunion;
    }

    public boolean getAlerta(){
        return alerta;
    }

    public void setAlerta(boolean alerta) {
        this.alerta = alerta;
    }

    public Sala getLugar() {
		return lugar;
	}

	public void setLugar(Sala lugar) {
        if(!lugar.ocupado(this)) this.lugar = lugar;
	}

    public List<Usuario> getInvitados() {
        return invitados;
    }

    public void addInvitado(Usuario u) {
	   if(!u.equals(duenio)) {
           u.notificar(new Notificacion(this, u));
           invitados.add(u);
       }
    }

    public void agregarACalendario(Calendario c){
        calendarios.add(c);
    }

    /**
     * Comprueba si dos reuniones se superponen entre si
     * @param r1 Reunion 1
     * @param r2 Reunion 2
     * @return Si se superponen
     */
    public static boolean seSuperponen(Reunion r1, Reunion r2){
        return r1.getFechaInicio().compareTo(r2.getFechaInicio()) > 0 && r1.getFechaFin().compareTo(r2.getFechaFin()) < 0;
    }


}
