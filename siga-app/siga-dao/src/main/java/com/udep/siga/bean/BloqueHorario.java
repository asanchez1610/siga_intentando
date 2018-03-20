package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Wilfredo Atoche
 */
public class BloqueHorario implements Serializable{
    private int id;
    private GrupoPractica grupoPractica;
    private String descripcion;
    private int orden;
    
    public BloqueHorario(){}
    
    public BloqueHorario(int id, String descripcion, int orden){
        this.id = id;
        this.descripcion = descripcion;
        this.orden = orden;
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
     * @return the Descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param Descripcion the Descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the orden
     */
    public int getOrden() {
        return orden;
    }

    /**
     * @param orden the orden to set
     */
    public void setOrden(int orden) {
        this.orden = orden;
    }

    /**
     * @return the grupoPractica
     */
    public GrupoPractica getGrupoPractica() {
        return grupoPractica;
    }

    /**
     * @param grupoPractica the grupoPractica to set
     */
    public void setGrupoPractica(GrupoPractica grupoPractica) {
        this.grupoPractica = grupoPractica;
    }
    
}
