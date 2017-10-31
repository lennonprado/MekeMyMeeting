package com.test;

import com.app.DataCreation;
import com.entidades.Usuario;
import com.servicios.EMF;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import static org.junit.Assert.*;

public class TestUsuarioDB {

    static EntityManager manager;

    @BeforeClass
    public static void init() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("makemymeeting");
        manager = emf.createEntityManager();
    }

    public void persist(Object o) {
        manager.getTransaction().begin();
        manager.persist(o);
        manager.getTransaction().commit();
    }

    public void delete(Object o) {
        manager.getTransaction().begin();
        manager.remove(o);
        manager.getTransaction().commit();
    }

    @Test
    public void persistUsuario() throws Exception {

        Usuario user = new Usuario("ricard56", "Ricardo", "Lopez", "py55gc");
        persist(user);
        Usuario u = manager.find(Usuario.class, user.getNombreUsuario());
        assertNotNull(u);
        delete(user);
    }

    @Test
    public void deleteUsuario() throws Exception {
        Usuario user = new Usuario("marc35", "Mark", "", "yr34r");
        persist(user);

        delete(user);
        Usuario u = manager.find(Usuario.class, user.getNombreUsuario());

        assertNull(u);

    }


    @Test
    public void getUsuario() throws Exception {
        Usuario user = new Usuario("sant75", "Santiago", "", "ey45");
        persist(user);

        Query query = manager.createNamedQuery(Usuario.BUSCAR_USUARIO);
        query.setParameter("usuario", user.getNombreUsuario());
        Usuario u = null;

        try {
            u = (Usuario) query.getSingleResult();
        } catch (Exception e) {

        }

        assertNotNull(u);

    }


}