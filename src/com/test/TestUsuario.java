package com.test;

import com.clases.Calendario;
import com.clases.Reunion;
import com.clases.Sala;
import com.clases.Usuario;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class TestUsuario {

    Usuario user;
    Reunion reunion;
    Sala sala;

    @Before
    public void setUp() throws Exception {
        this.user = new Usuario("Juan");
        this.sala = new Sala(10,"La sala","Avellaneda 10");
        Date fechaR4 = new GregorianCalendar(2017, Calendar.SEPTEMBER, 22, 15, 0).getTime();
        this.reunion = new Reunion(fechaR4,4,this.sala,this.user);
    }

    @Test
    public void agregarReunion() throws Exception {
        this.user.agregarReunion(this.reunion,this.user.getDefaultCalendario());
        assertFalse(this.user.getDefaultCalendario().getReuniones().isEmpty());
    }

    @Test
    public void fueNotificado() throws Exception {
        assertFalse(this.user.fueNotificado(reunion));
    }

    @Test
    public void agregarCalendario() throws Exception {
        int calendarios = user.getCalendarios().size();
        user.agregarCalendario(new Calendario());
        assertEquals(user.getCalendarios().size(), calendarios + 1);
    }

    @Test
    public void estoyOcupado() throws Exception {
        // September 22nd 17:30
        Date fecha = new GregorianCalendar(2017, Calendar.SEPTEMBER, 22, 17, 30).getTime();
        Usuario u = new Usuario();
        Sala s = new Sala(5, "Sala", "");
        Reunion r = new Reunion(fecha, 2, s, u);
        assertTrue(user.estoyOcupado(r));
    }

}