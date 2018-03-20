package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class GradoAcademico implements Serializable{
    private int id;
    private String modoObtencion;

    public GradoAcademico(){}
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
     * @return the modoObtencion
     */
    public String getModoObtencion() {
        return modoObtencion;
    }

    /**
     * @param modoObtencion the modoObtencion to set
     */
    public void setModoObtencion(String modoObtencion) {
        this.modoObtencion = modoObtencion;
    }
}
