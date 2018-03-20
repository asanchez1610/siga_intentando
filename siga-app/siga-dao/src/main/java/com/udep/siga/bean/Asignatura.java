package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Wilfredo Atoche
 */
public class Asignatura implements Serializable{
    private int id;
    private String sigla;
    private String nombre;
    private int creditos;
    private String descripcion;
    
    //Util
    private boolean desdeEspecialidad;
    
    public Asignatura(){
    }
    
    public Asignatura(int id){
        this.id = id;
    }
    
    public Asignatura(String nombre){
        this.nombre = nombre;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the sigla
     */
    public String getSigla() {
        return sigla;
    }

    /**
     * @param sigla the sigla to set
     */
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the creditos
     */
    public int getCreditos() {
        return creditos;
    }

    /**
     * @param creditos the creditos to set
     */
    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isDesdeEspecialidad() {
        return desdeEspecialidad;
    }

    public void setDesdeEspecialidad(boolean desdeEspecialidad) {
        this.desdeEspecialidad = desdeEspecialidad;
    }
}
