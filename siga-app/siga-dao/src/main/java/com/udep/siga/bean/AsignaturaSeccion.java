package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Wilfredo Atoche
 */
public class AsignaturaSeccion implements Serializable {

    private int idSeccion;
    private String nombreSeccion;
    private String descripcion;

    public AsignaturaSeccion() {
    }

    public AsignaturaSeccion(int idSeccion) {
        this.idSeccion = idSeccion;
    }

    public AsignaturaSeccion(int idSeccion, String nombreSeccion) {
        this.idSeccion = idSeccion;
        this.nombreSeccion = nombreSeccion;
    }

    public AsignaturaSeccion(int idSeccion, String nombreSeccion, String descripcion) {
        this.idSeccion = idSeccion;
        this.nombreSeccion = nombreSeccion;
        this.descripcion = descripcion;
    }

    /**
     * @return the idSeccion
     */
    public int getIdSeccion() {
        return idSeccion;
    }

    /**
     * @param idSeccion the idSeccion to set
     */
    public void setIdSeccion(int idSeccion) {
        this.idSeccion = idSeccion;
    }

    /**
     * @return the nombreSeccion
     */
    public String getNombreSeccion() {
        return nombreSeccion;
    }

    /**
     * @param nombreSeccion the nombreSeccion to set
     */
    public void setNombreSeccion(String nombreSeccion) {
        this.nombreSeccion = nombreSeccion;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        if (descripcion == null) {
            return "";
        }
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
