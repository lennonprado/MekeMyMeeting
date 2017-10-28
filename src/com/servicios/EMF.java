package com.servicios;

import com.app.DataCreation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class EMF implements ServletContextListener{

    private static EntityManager manager;
    private static EntityManagerFactory emf;

    public static EntityManager getEntityManager() {
        return manager;
    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        emf = Persistence.createEntityManagerFactory("makemymeeting");
        manager = emf.createEntityManager();
        DataCreation.Initialize();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        emf.close();
    }

    public static boolean persist(Object o){
        try {
            manager.getTransaction().begin();
            manager.persist(o);
            manager.getTransaction().commit();
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    public static boolean delete(Object o){
        try {
            manager.getTransaction().begin();
            manager.remove(o);
            manager.getTransaction().commit();
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

}
