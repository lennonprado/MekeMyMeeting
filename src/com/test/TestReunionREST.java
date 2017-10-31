package com.test;

import com.entidades.Reunion;
import com.entidades.Sala;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.StringWriter;

import static org.junit.Assert.*;

public class TestReunionREST {

    String BASE_URL = "http://localhost:8081/api/";
    HttpClient client = HttpClientBuilder.create().build();
    String token;

    @Before
    public void setUp() throws Exception {

        String authString = TestUsuarioREST.createUserCredentialsJson();
        String userString = TestUsuarioREST.createUserJson();

        String url = BASE_URL + "usuarios";

        HttpPost post = new HttpPost(url);
        post.setEntity(new StringEntity(userString, ContentType.APPLICATION_JSON));
        client.execute(post);


        post = new HttpPost(BASE_URL + "autenticacion");
        post.setEntity(new StringEntity(authString, ContentType.APPLICATION_JSON));
        HttpResponse response = client.execute(post);

        StringWriter writer = new StringWriter();
        IOUtils.copy(response.getEntity().getContent(), writer);
        token = writer.toString();
    }

    public HttpResponse generarReunion() throws Exception{
        int idSala = crearSala();

        ObjectMapper mapper = new ObjectMapper();

        ObjectNode reunion = mapper.createObjectNode();
        reunion.put("lugar", Integer.toString(idSala));
        reunion.put("usuario", "julio34");
        reunion.put("fechaInicio", "20-10-2017-14:30");
        reunion.put("duracion", "3");
        String reunionString = reunion.toString();

        HttpPost post = new HttpPost(BASE_URL + "reuniones");
        post.addHeader("Authorization", "Bearer:" + token);
        post.setEntity(new StringEntity(reunionString, ContentType.APPLICATION_JSON));
        return client.execute(post);
    }

    // Crea una sala y me devuelve el ID de la sala
    public int crearSala() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode sala = mapper.createObjectNode();
        sala.put("cantPersonas", "6");
        sala.put("nombre", "Sala 4");
        sala.put("direccion", "Una dir");
        String salaString = sala.toString();

        HttpPost post = new HttpPost(BASE_URL + "salas");
        post.addHeader("Authorization", "Bearer:" + token);
        post.setEntity(new StringEntity(salaString, ContentType.APPLICATION_JSON));
        HttpResponse response = client.execute(post);

        StringWriter writer = new StringWriter();
        IOUtils.copy(response.getEntity().getContent(), writer);
        return mapper.readValue(writer.toString(), Sala.class).getIdSala();
    }


    @Test
    public void crearReunion() throws Exception {
        HttpResponse r = generarReunion();
        int responseCode = r.getStatusLine().getStatusCode();

        assertEquals(201, responseCode);
    }

    @Test
    public void getReuniones() throws Exception {
        HttpGet request = new HttpGet(BASE_URL + "reuniones");
        request.addHeader("Authorization", "Bearer:" + token);
        int responseCode = client.execute(request).getStatusLine().getStatusCode();

        assertEquals(200, responseCode);

    }


}