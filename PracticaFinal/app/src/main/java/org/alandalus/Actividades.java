package org.alandalus;

public class Actividades {
    private int rowid;
    private String nombre;
    private int minutos;
    private byte[] foto;

    public Actividades() {
    }

    public Actividades(int rowid, String nombre, int minutos, byte[] foto) {
        this.rowid = rowid;
        this.nombre = nombre;
        this.minutos = minutos;
        this.foto = foto;
    }

    public void setRowid(int rowid) {
        this.rowid = rowid;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public byte[] getFoto() {
        return foto;
    }

    public int getRowid() {
        return rowid;
    }

    public String getNombre() {
        return nombre;
    }

    public int getMinutos() {
        return minutos;
    }


}
