package com.servicios;

import com.clases.Usuario;

import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/usuarios")
public class UsuarioREST{

    public UsuarioREST() {
    }

    public static void crearUsuario(){

        }

        public static void updateUsuario(){

        }

        /**
         * @return Se obtienen todos los usuarios existentes
         */
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public static List<Usuario> getUsuarios() {
            Query query = EMF.getEntityManager().createNamedQuery(Usuario.BUSCAR_USUARIOS);

            List<Usuario> aux;

            aux = query.getResultList();

            System.out.println(aux);

            return aux;
        }

        /**
         * Se obtiene los datos de un usuario
         *
         * @param u Usuario a buscar
         * @return Datos completos del usuario
         */
        public static Usuario getUsuario(Usuario u) {
            Query query = EMF.getEntityManager().createNamedQuery(Usuario.BUSCAR_USUARIO);
            query.setParameter("usuario", u);
            return (Usuario) query.getSingleResult();
        }

}
