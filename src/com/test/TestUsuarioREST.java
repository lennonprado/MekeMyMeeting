package com.test;

import com.clases.Usuario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.HttpClient;
import org.junit.*;

import java.io.StringWriter;
import static org.junit.Assert.*;


public class TestUsuarioREST {

    String BASE_URL = "http://localhost:8081/api/";
    HttpClient client = HttpClientBuilder.create().build();
    String token;

    @Before
    public void setUp() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode auth = mapper.createObjectNode();
        auth.put("username", "julio34");
        auth.put("password", "12354qR");
        String authString = auth.toString();

        ObjectNode user = mapper.createObjectNode();
        user.put("nombreUsuario", "julio34");
        user.put("nombre", "Julio");
        user.put("apellido", "Oso");
        user.put("password", "12354qR");
        String userString = user.toString();

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

    @After
    public void tearDown() throws Exception {
        deleteUser("julio34");
    }

    private void deleteUser(String user) throws Exception {
        HttpDelete request = new HttpDelete(BASE_URL + "usuarios/" + user);
        request.addHeader("Authorization", "Bearer:" + token);
        client.execute(request);
    }

    @Test
    public void crearUsuario() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonObject = mapper.createObjectNode();

        jsonObject.put("nombreUsuario", "juan75");
        jsonObject.put("nombre", "Juan");
        jsonObject.put("apellido", "");
        jsonObject.put("password", "m75ma");
        String jsonString = jsonObject.toString();

        String url = BASE_URL + "usuarios";

        System.out.println("\nPOST " + url);
        HttpPost post = new HttpPost(url);
        post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
        assertEquals(outputResponse(post), 201);
        deleteUser("juan75");
    }

    @Test
    public void updateUsuario() throws Exception {

        String url = BASE_URL + "usuarios/julio34";
        System.out.println("\nPUT " + url);

        HttpPut request = new HttpPut(url);
        request.addHeader("Authorization", "Bearer:" + token);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode user = mapper.createObjectNode();
        user.put("nombreUsuario", "julio34");
        user.put("nombre", "OtroJulio");
        user.put("apellido", "Otro");
        user.put("password", "EdX35");
        String userString = user.toString();

        request.setEntity(new StringEntity(userString, ContentType.APPLICATION_JSON));

        assertEquals(outputResponse(request), 201);

    }

    @Test
    public void getUsuarios() throws Exception {
        String requestURL = BASE_URL + "usuarios";

        System.out.println("\nGET " + requestURL);
        HttpGet request = new HttpGet(requestURL);

        assertEquals(outputResponse(request), 200);
    }

    @Test
    public void getUsuario() throws Exception {
        String requestURL = BASE_URL + "usuarios/julio34";

        System.out.println("\nGET " + requestURL);
        HttpGet request = new HttpGet(requestURL);

        assertEquals(outputResponse(request), 200);
    }

    @Test
    public void deleteUsuario() throws Exception {
        String requestURL = BASE_URL + "usuarios/julio34";
        System.out.println("\nDELETE " + requestURL);
        HttpDelete request = new HttpDelete(requestURL);
        request.addHeader("Authorization", "Bearer:" + token);

        assertEquals(200, outputResponse(request));
    }

    private int outputResponse(HttpRequestBase request) throws Exception {
        HttpResponse response = client.execute(request);
        int responseCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + responseCode);

        StringWriter writer = new StringWriter();
        IOUtils.copy(response.getEntity().getContent(), writer);
        System.out.println("Response: " + writer);

        return responseCode;
    }

}