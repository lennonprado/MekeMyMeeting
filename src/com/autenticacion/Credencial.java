package com.autenticacion;

import java.io.Serializable;

public class Credencial implements Serializable {

    private String username;
    private String password;

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
