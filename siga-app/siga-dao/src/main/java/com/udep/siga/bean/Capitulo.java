package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class Capitulo implements Serializable{

    private int id;
    private String numero;
    private String nombre;
    private String paginaInicio;
    private String paginaFin;

    public Capitulo() {
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
    public String getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(String numero) {
        this.numero = numero;
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
     * @return the paginaInicio
     */
    public String getPaginaInicio() {
        return paginaInicio;
    }

    /**
     * @param paginaInicio the paginaInicio to set
     */
    public void setPaginaInicio(String paginaInicio) {
        this.paginaInicio = paginaInicio;
    }

    /**
     * @return the paginaFin
     */
    public String getPaginaFin() {
        return paginaFin;
    }

    /**
     * @param paginaFin the paginaFin to set
     */
    public void setPaginaFin(String paginaFin) {
        this.paginaFin = paginaFin;
    }
}
