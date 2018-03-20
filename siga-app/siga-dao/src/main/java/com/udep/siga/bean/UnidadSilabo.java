package com.udep.siga.bean;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class UnidadSilabo implements Serializable {

    private int id;
    private int numero;
    private String descripcion;
    /*
     * Utiles
     */
    private List<TemaSilabo> temaList;

    public UnidadSilabo() {
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
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the temaList
     */
    public List<TemaSilabo> getTemaList() {
        return temaList;
    }

    /**
     * @param temaList the temaList to set
     */
    public void setTemaList(List<TemaSilabo> temaList) {
        this.temaList = temaList;
    }
}
