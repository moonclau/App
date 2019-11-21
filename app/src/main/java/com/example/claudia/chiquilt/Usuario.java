package com.example.claudia.chiquilt;

import java.sql.Date;

public class Usuario {
    String name ;
    String id;
    Date fecha;

    public Usuario(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
