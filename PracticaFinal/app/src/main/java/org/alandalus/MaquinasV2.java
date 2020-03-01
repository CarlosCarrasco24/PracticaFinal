package org.alandalus;

import android.graphics.Bitmap;

public class MaquinasV2 {
    private String nombre, uid;
    private String imagen;
    private int minutos;

    public MaquinasV2() {
    }

    public MaquinasV2(String uid, String nombre, String imagen, int minutos) {
        this.nombre = nombre;
        this.uid = uid;
        this.imagen = imagen;
        this.minutos = minutos;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUid() {
        return uid;
    }

    public String getImagen() {
        return imagen;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }
}
