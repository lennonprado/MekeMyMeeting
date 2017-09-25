package com.servicios;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMF {

    private static EntityManager manager;
    private static EntityManagerFactory emf;

    public static EntityManager getEntityManager() {
        if(manager == null){
            emf = Persistence.createEntityManagerFactory("makemymeeting");
            manager = emf.createEntityManager();
        }
        return manager;
    }

    public static void close(){
        if(manager != null && emf != null){
            manager.close();
            emf.close();
        }
    }

    public static void clear(){
        close();
        manager = null;
        emf = null;
        getEntityManager();
    }
}
