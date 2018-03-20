package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class IdiomasCV implements Serializable{

    private int id;
    private String nombre;
    private String lugar;
    private int anioInicio;
    private String nivelC;
    private String nivelL;
    private String nivelE;

    public IdiomasCV() {
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
     * @return the lugar
     */
    public String getLugar() {
        return lugar;
    }

    /**
     * @param lugar the lugar to set
     */
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    /**
     * @return the anioInicio
     */
    public int getAnioInicio() {
        return anioInicio;
    }

    /**
     * @param anioInicio the anioInicio to set
     */
    public void setAnioInicio(int anioInicio) {
        this.anioInicio = anioInicio;
    }

    /**
     * @return the nivelC
     */
    public String getNivelC() {
        return nivelC;
    }

    /**
     * @param nivelC the nivelC to set
     */
    public void setNivelC(String nivelC) {
        this.nivelC = nivelC;
    }

    /**
     * @return the nivelL
     */
    public String getNivelL() {
        return nivelL;
    }

    /**
     * @param nivelL the nivelL to set
     */
    public void setNivelL(String nivelL) {
        this.nivelL = nivelL;
    }

    /**
     * @return the nivelE
     */
    public String getNivelE() {
        return nivelE;
    }

    /**
     * @param nivelE the nivelE to set
     */
    public void setNivelE(String nivelE) {
        this.nivelE = nivelE;
    }
}
