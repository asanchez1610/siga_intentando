package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class CapacitacionCV implements Serializable{

    private int id;
    private String anio;
    private String evento;
    private String lugar;
    private String institucion;
    private String horas;

    public CapacitacionCV() {
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
     * @return the evento
     */
    public String getEvento() {
        return evento;
    }

    /**
     * @param evento the evento to set
     */
    public void setEvento(String evento) {
        this.evento = evento;
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
     * @return the institucion
     */
    public String getInstitucion() {
        if (institucion == null) {
            return "";
        }
        return institucion;
    }

    /**
     * @param institucion the institucion to set
     */
    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    /**
     * @return the horas
     */
    public String getHoras() {
        if (horas == null || horas.equals("")) {
            return "";
        } else {
            return horas + " Horas.";
        }
    }

    /**
     * @param horas the horas to set
     */
    public void setHoras(String horas) {
        this.horas = horas;
    }
}
