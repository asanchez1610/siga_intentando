package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Wilfredo Atoche
 */
public class CentroAcademico implements Serializable{
    private int id;
    private String sigla;
    private String nombre;
    private boolean activo;
    
    public CentroAcademico(){  
    }
    
    public CentroAcademico(int id){  
        this.id = id;
    }
    
    public CentroAcademico(int id,String nombre){  
        this.id = id;
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
     * @return the activo
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
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
}
