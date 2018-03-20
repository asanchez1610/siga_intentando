package com.udep.siga.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class CLCCategoriaIdioma implements Serializable{

    private int id;
    private Date fecha;
    private String idioma;
    private String numero;
    private String nivel;
    private int credReconocido;

    public CLCCategoriaIdioma() {
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
     * @return the idioma
     */
    public String getIdioma() {
        return idioma;
    }

    /**
     * @param idioma the idioma to set
     */
    public void setIdioma(String idioma) {
        this.idioma = idioma;
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
     * @return the nivel
     */
    public String getNivel() {
        return nivel;
    }

    /**
     * @param nivel the nivel to set
     */
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    /**
     * @return the credReconocido
     */
    public int getCredReconocido() {
        return credReconocido;
    }

    /**
     * @param credReconocido the credReconocido to set
     */
    public void setCredReconocido(int credReconocido) {
        this.credReconocido = credReconocido;
    }
}
