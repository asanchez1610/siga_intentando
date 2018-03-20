package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class ArticuloCV implements Serializable{

    private int id;
    private int anio;
    private String titulo;
    private String medioPublicacion;
    private String pagInicio;
    private String pagFin;

    public ArticuloCV() {
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
    public int getAnio() {
        return anio;
    }

    /**
     * @param anio the anio to set
     */
    public void setAnio(int anio) {
        this.anio = anio;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the medioPublicacion
     */
    public String getMedioPublicacion() {
        return medioPublicacion;
    }

    /**
     * @param medioPublicacion the medioPublicacion to set
     */
    public void setMedioPublicacion(String medioPublicacion) {
        this.medioPublicacion = medioPublicacion;
    }

    /**
     * @return the pagInicio
     */
    public String getPagInicio() {
        return pagInicio;
    }

    /**
     * @param pagInicio the pagInicio to set
     */
    public void setPagInicio(String pagInicio) {
        this.pagInicio = pagInicio;
    }

    /**
     * @return the pagFin
     */
    public String getPagFin() {
        return pagFin;
    }

    /**
     * @param pagFin the pagFin to set
     */
    public void setPagFin(String pagFin) {
        this.pagFin = pagFin;
    }
}
