package com.test;

import com.clases.Reunion;
import com.clases.Sala;
import com.clases.Usuario;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class UsuarioTest {

    Usuario user;
    Reunion reunion;
    Sala sala;

    @Before
    public void setUp() throws Exception {
        this.user = new Usuario("Eledu");
        this.sala = new Sala(10,"isi","rod");
        Date fechaR4 = new GregorianCalendar(2017, Calendar.SEPTEMBER, 22, 15, 0).getTime();
        this.reunion = new Reunion(fechaR4,2,this.sala,this.user);
    }

    @After
    public void tearDown() throws Exception {

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

    }

    @Test
    public void estoyOcupado() throws Exception {
    }

}