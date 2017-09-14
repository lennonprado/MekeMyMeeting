package com.tests;

import com.clases.Usuario;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UsuarioTest {

    String nombre;
    Usuario u;

    @Before
    public void setUp() throws Exception {

         nombre = "Marcelo";
         u = new Usuario("Marcelo");

    }

    @After
    public void tearDown() throws Exception {

        assertEquals(u.getNombre(),nombre);

    }



    @Test
    public void estoyOcupado() throws Exception {

    }

    @Test
    public void testNombre() throws Exception {



    }

}