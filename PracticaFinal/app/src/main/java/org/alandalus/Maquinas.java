package org.alandalus;


import java.io.Serializable;

public class Maquinas implements Serializable {
    private String nombre;
    private int foto;

    public Maquinas(String nombre, int foto) {
        if(nombre.trim().equals("")){
            nombre="Sin Nombre";
        }
        this.nombre = nombre;
        this.foto = foto;
    }
    public Maquinas() {
    }


    public int getFoto() {
        return foto;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
