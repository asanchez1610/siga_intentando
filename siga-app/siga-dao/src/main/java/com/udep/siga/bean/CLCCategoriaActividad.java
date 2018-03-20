package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class CLCCategoriaActividad extends CLCCategoriaGeneral implements Serializable{

    private String actividad;
    private String institucion;
    private int horasDedicadas;

    public CLCCategoriaActividad() {
    }

    /**
     * @return the actividad
     */
    public String getActividad() {
        return actividad;
    }

    /**
     * @param actividad the actividad to set
     */
    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    /**
     * @return the institucion
     */
    public String getInstitucion() {
        return institucion;
    }

    /**
     * @param institucion the institucion to set
     */
    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    /**
     * @return the horasDedicadas
     */
    public int getHorasDedicadas() {
        return horasDedicadas;
    }

    /**
     * @param horasDedicadas the horasDedicadas to set
     */
    public void setHorasDedicadas(int horasDedicadas) {
        this.horasDedicadas = horasDedicadas;
    }
}
