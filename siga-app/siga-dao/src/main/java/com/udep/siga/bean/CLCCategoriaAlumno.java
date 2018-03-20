package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class CLCCategoriaAlumno implements Serializable{

    private int id;
    private String nombre;
    private int maxValor;
    private String modalidad;
    private int clcObtenidos;
    private float totalHoras;

    public CLCCategoriaAlumno() {
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
     * @return the maxValor
     */
    public int getMaxValor() {
        return maxValor;
    }

    /**
     * @param maxValor the maxValor to set
     */
    public void setMaxValor(int maxValor) {
        this.maxValor = maxValor;
    }

    /**
     * @return the modalidad
     */
    public String getModalidad() {
        if (modalidad == null) {
            return "";
        } else {
            return modalidad;
        }
    }

    /**
     * @param modalidad the modalidad to set
     */
    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    /**
     * @return the clcObtenidos
     */
    public int getClcObtenidos() {
        return clcObtenidos;
    }

    /**
     * @param clcObtenidos the clcObtenidos to set
     */
    public void setClcObtenidos(int clcObtenidos) {
        this.clcObtenidos = clcObtenidos;
    }

    /**
     * @return the totalHoras
     */
    public float getTotalHoras() {
        return totalHoras;
    }

    /**
     * @param totalHoras the totalHoras to set
     */
    public void setTotalHoras(float totalHoras) {
        this.totalHoras = totalHoras;
    }
}
