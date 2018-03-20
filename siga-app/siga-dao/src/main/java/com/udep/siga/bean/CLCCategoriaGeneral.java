package com.udep.siga.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class CLCCategoriaGeneral implements Serializable{

    private int id;
    private Date fecha;
    private int tasaCredito;
    private int tasaHora;
    private float tasaTotalCred;

    public CLCCategoriaGeneral() {
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
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the tasaCredito
     */
    public int getTasaCredito() {
        return tasaCredito;
    }

    /**
     * @param tasaCredito the tasaCredito to set
     */
    public void setTasaCredito(int tasaCredito) {
        this.tasaCredito = tasaCredito;
    }

    /**
     * @return the tasaHora
     */
    public int getTasaHora() {
        return tasaHora;
    }

    /**
     * @param tasaHora the tasaHora to set
     */
    public void setTasaHora(int tasaHora) {
        this.tasaHora = tasaHora;
    }

    /**
     * @return the tasaTotalCred
     */
    public float getTasaTotalCred() {
        return tasaTotalCred;
    }

    /**
     * @param tasaTotalCred the tasaTotalCred to set
     */
    public void setTasaTotalCred(float tasaTotalCred) {
        this.tasaTotalCred = tasaTotalCred;
    }
}
