package org.alandalus;

public class MaquinasNoConexion {
    private int rowid,codigo,minutos;
    private String nombre;

    public MaquinasNoConexion(int rowid, int codigo, int minutos, String nombre) {
        this.rowid = rowid;
        this.codigo = codigo;
        this.minutos = minutos;
        this.nombre = nombre;
    }

    public MaquinasNoConexion() {
    }

    public int getRowid() {
        return rowid;
    }

    public int getCodigo() {
        return codigo;
    }

    public int getMinutos() {
        return minutos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setRowid(int rowid) {
        this.rowid = rowid;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
