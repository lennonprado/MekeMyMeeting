package com.clases;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NamedQueries({

// Se obtienen todos los usuarios
@NamedQuery(name = Usuario.BUSCAR_USUARIOS, query = "SELECT u FROM Usuario u"),
// Consulta de todos los datos de un usuario.
@NamedQuery(name = Usuario.BUSCAR_USUARIO, query = "SELECT u FROM Usuario u WHERE u.nombreUsuario = :usuario"),
// Dado un usuario y un día, retornar todas sus reuniones.
@NamedQuery(name = Usuario.REUNIONES_USUARIO, query = "SELECT r FROM Usuario u JOIN u.calendarios c JOIN c.reuniones r WHERE u.nombreUsuario = :usuario AND DAY(r.fechaInicio) = :dia"),
// Dado un usuario y un rango de fechas, retornar todas sus reuniones.
@NamedQuery(name = Usuario.REUNIONES_USUARIO_RANGO, query = "SELECT r FROM Usuario u JOIN u.calendarios c JOIN c.reuniones r WHERE u.nombreUsuario = :usuario AND r.fechaInicio BETWEEN :fechaInicio AND :fechaFin"),
// Dado un usuario y una nueva reunión, retornar todas las reuniones que se superpongan con la nueva reunión.
@NamedQuery(name = Usuario.REUNIONES_USUARIO_SUPERPONGAN, query = "SELECT r FROM Usuario u JOIN u.calendarios c JOIN c.reuniones r WHERE u.nombreUsuario = :usuario AND :fechaInicio <= r.fechaFin AND :fechaFin >= r.fechaInicio")

})
@JsonIgnoreProperties({ "notificaciones", "calendarios", "defaultCalendario", "password" })
@Entity
public class Usuario {

    public static final String BUSCAR_USUARIO = "Usuario.buscarUsuario";
    public static final String BUSCAR_USUARIOS = "Usuario.buscarUsuarios";
    public static final String REUNIONES_USUARIO = "Usuario.reuniones";
    public static final String REUNIONES_USUARIO_RANGO = "Usuario.reunionesRango";
    public static final String REUNIONES_USUARIO_SUPERPONGAN = "Usuario.reunionesSuperpongan";

    @Id
    private String nombreUsuario;

    private String nombre;
    private String apellido;

    @OneToMany(mappedBy = "usuarioNotificado", cascade = CascadeType.ALL)
    private List<Notificacion> notificaciones;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Calendario> calendarios;

    private String password;

    public Usuario() {
        notificaciones = new ArrayList<>();
        calendarios = new ArrayList<>();
        crearCalendario(); // Se crea un calendario por default
        this.password = "";
        this.nombreUsuario = "";
    }

    public Usuario(String nombre) {
        this();
        this.nombreUsuario = nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getApellido() {
        return apellido;
    }

    /**
     * Se agrega una reunion al un calendario especifico del usuario,
     * se comprueba que el usuario no tenga superposiciones de reuniones y que ese calendario pertenezca a el.
     *
     * @param r Reunion a agregar
     * @param c Calendario donde agregar la reunion
     */
    public void agregarReunion(Reunion r, Calendario c) {
        if (!estoyOcupado(r)) {
            if (c.getUsuario().equals(this)) {
                c.agregarReunion(r);
            }
        }
    }

    /**
     * Se notifica al usuario acerca de una reunion
     *
     * @param n La notificacion de la reunion
     */
    public void notificar(Notificacion n) {
        notificaciones.add(n);
    }

    /**
     * Comprueba si este usuario ya fue notificado (le llego una notificacion pero todavia no la acepto/rechazo)
     *
     * @param r Reunion a la comprobar que fue notificiado
     * @return Si fue notificado o no a la reunion
     */
    public boolean fueNotificado(Reunion r) {
        for (Notificacion n : notificaciones) {
            if (r.equals(n.getReunion())) return true;
        }
        return false;
    }

    /**
     * Comparte un calendario con otros usuarios
     *
     * @param u Usuario a compartirle el calendario
     * @param c Calendario a compartir
     */
    public void compartir(Usuario u, Calendario c) {
        u.agregarCalendario(c);
    }

    /**
     * Se agrega un calendario a la lista de calendarios del usuario
     *
     * @param c Calendario a agregar
     */
    public void agregarCalendario(Calendario c) {
        this.calendarios.add(c);
    }

    /**
     * Se crea un calendario y se agrega a la lista de calendarios.
     */
    public void crearCalendario() {
        Calendario c = new Calendario(this);
        calendarios.add(c);
    }

    /**
     * Comprueba que el usuario no tenga superposicion de reuniones en sus calendarios
     *
     * @param r Reunion a comprobar que este libre para el usuario
     * @return Si el usuario estara disponible para la reunion o no
     */
    public boolean estoyOcupado(Reunion r) {
        for (Calendario c : this.calendarios) {
            for (Reunion reunion : c.getReuniones()) {
                if (Reunion.seSuperponen(r, reunion)) return true;
            }
        }
        return false;
    }

    /**
     * Acepta una notificacion recibida, si estaba ocupado, al aceptar se elimina la notificacion
     *
     * @param n Notificacion a aceptar
     * @param c Calendario a agregar la reunion notificada
     */
    public void aceptar(Notificacion n, Calendario c) {
        if (!estoyOcupado(n.getReunion())) {
            c.agregarReunion(n.getReunion());
        } else System.out.println(this + "No pudo aceptar");
        this.notificaciones.remove(n);
    }

    /**
     * Acepta una notificacion recibida y se agrega al calendario por defecto del usuario
     *
     * @param n Notificaion a aceptar
     */
    public void aceptar(Notificacion n) {
        aceptar(n, getDefaultCalendario());
    }

    /**
     * Rechaza una notifiacion recibida
     *
     * @param n Notificacion a rechazar
     */
    public void cancelar(Notificacion n) {
        this.notificaciones.remove(n);
    }

    public Calendario getDefaultCalendario() {
        return calendarios.get(0);
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
        return  u.nombreUsuario.equals(nombreUsuario) && u.password.equals(password);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String toString() {
        return "Usuario{" +
                "nombre='" + nombreUsuario + '\'' +
                '}' + System.getProperty("line.separator");
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
