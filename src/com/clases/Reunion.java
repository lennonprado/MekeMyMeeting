package com.clases;

import java.util.*;

import javax.persistence.*;


@NamedQueries({

// Consulta todas las reuniones existentes.
@NamedQuery(name=Reunion.BUSCAR_REUNIONES, query="SELECT r FROM Reunion r"),

})

@Entity
public class Reunion {

    public static final String BUSCAR_REUNIONES = "Reunion.buscarReuniones";

    @Id
    @GeneratedValue
    private int idReunion;
    private Date fechaInicio;
    private Date fechaFin;
    private boolean recordar; // Si la reunion fue marcada con recordatorios

    @ManyToOne
    private Usuario duenio; // Usuario organizador de la reunion

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Sala lugar;  // Lugar donde se realizara la reunion

    @ManyToMany
    private List<Usuario> invitados; // Usuarios invitados a la reunion

    @ManyToMany
    private List<Calendario> calendarios; // Calendarios en los cuales esta la reunion

    public Reunion() { }

    public Reunion(Date fechaInicio, int duracion, Sala lugar, Usuario duenio) {
        super();
        this.fechaInicio = fechaInicio;
        this.calendarios = new ArrayList<>();
        this.invitados = new ArrayList<>();
        this.fechaFin = getFechaFin(duracion);
        this.setLugar(lugar);
        this.duenio = duenio;
        this.addInvitado(duenio);
        this.recordar = false;
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

    /**
     * Se les vuelve a enviar la notificacion a los usuarios invitados
     * para recordarles de la reunion
     */
    public void recordarReunion() {
        if (recordar) {
            for (Usuario u : invitados) {
                if (!u.fueNotificado(this)) {
                    u.notificar(new Notificacion(this, u));
                }
            }
        }
    }

    /**
     * Agrega un invitado a la reunion
     * @param u Usuario a invitar
     */
    public void addInvitado(Usuario u) {
        if (lugar != null) {
            u.notificar(new Notificacion(this, u));
            invitados.add(u);
        }
    }

    /**
     * Agrega esta reunion a un calendario
     * @param c Calendario a donde agregar la reunion
     */
    public void agregarACalendario(Calendario c) {
        calendarios.add(c);
    }

    /**
     * Comprueba si dos reuniones se superponen entre si
     * @param r1 Reunion 1
     * @param r2 Reunion 2
     * @return Si se superponen
     */
    public static boolean seSuperponen(Reunion r1, Reunion r2) {
        return r1.getFechaInicio().getTime() <= r2.getFechaFin().getTime() && r1.getFechaFin().getTime() >= r2.getFechaInicio().getTime();
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

    public boolean isRecordar() {
        return recordar;
    }

    public void setRecordar(boolean recordar) {
        this.recordar = recordar;
    }

    public Sala getLugar() {
        return lugar;
    }

    public void setLugar(Sala lugar) {
        if (!lugar.ocupado(this)) {
            this.lugar = lugar;
            lugar.addReunion(this);
        }
    }

    public List<Usuario> getInvitados() {
        return invitados;
    }

    public boolean equals(Object obj) {
        Reunion r = (Reunion) obj;
        return r.idReunion == idReunion && r.fechaInicio.equals(fechaInicio) && r.fechaFin.equals(fechaFin);
    }

    public List<Calendario> getCalendarios() {
        return calendarios;
    }

    public String toString() {
        return "Reunion{" +
                "fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", recordar=" + recordar +
                ", duenio=" + duenio +
                ", lugar=" + lugar +
                ", invitados=" + invitados +
                '}' + System.getProperty("line.separator");
    }
}
