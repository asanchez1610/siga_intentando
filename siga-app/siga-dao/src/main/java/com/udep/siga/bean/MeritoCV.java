package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class MeritoCV implements Serializable{

    private int id;
    private String anio;
    private String merito;

    public MeritoCV() {
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
     * @return the anio
     */
    public String getAnio() {
        return anio;
    }

    /**
     * @param anio the anio to set
     */
    public void setAnio(String anio) {
        this.anio = anio;
    }

    /**
     * @return the merito
     */
    public String getMerito() {
        return merito;
    }

    /**
     * @param merito the merito to set
     */
    public void setMerito(String merito) {
        this.merito = merito;
    }

}
