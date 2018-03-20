package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class ProyectoCV extends InvestigacionAcademica implements Serializable{

    
    private String nombre;
    private String participacion;
    private String area;
    private String financiamiento;
    private boolean hastaLaFecha;

    public ProyectoCV() {
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
     * @return the participacion
     */
    public String getParticipacion() {
        return participacion;
    }

    /**
     * @param participacion the participacion to set
     */
    public void setParticipacion(String participacion) {
        this.participacion = participacion;
    }

    /**
     * @return the area
     */
    public String getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * @return the hastaLaFecha
     */
    public boolean isHastaLaFecha() {
        return hastaLaFecha;
    }

    /**
     * @param hastaLaFecha the hastaLaFecha to set
     */
    public void setHastaLaFecha(boolean hastaLaFecha) {
        this.hastaLaFecha = hastaLaFecha;
    }

    /**
     * @return the financiamiento
     */
    public String getFinanciamiento() {
        return financiamiento;
    }

    /**
     * @param financiamiento the financiamiento to set
     */
    public void setFinanciamiento(String financiamiento) {
        this.financiamiento = financiamiento;
    }
}
