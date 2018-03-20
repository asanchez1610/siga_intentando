package com.udep.siga.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Wilfredo Atoche
 */
public class PlanEstudio implements Serializable{
    
    private int id;
    private String nombre;
    private Date fechaInicio;
    private Date fechaFin;
    private boolean activo;
    private int numPeriodos;
    private boolean vigente;
    private int creditosTotales;
    private boolean bloqueado;        
    
    public PlanEstudio(){}
    
    public PlanEstudio(int id){
        this.id = id;
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
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the activo
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * @return the numPeriodos
     */
    public int getNumPeriodos() {
        return numPeriodos;
    }

    /**
     * @param numPeriodos the numPeriodos to set
     */
    public void setNumPeriodos(int numPeriodos) {
        this.numPeriodos = numPeriodos;
    }

    /**
     * @return the vigente
     */
    public boolean isVigente() {
        return vigente;
    }

    /**
     * @param vigente the vigente to set
     */
    public void setVigente(boolean vigente) {
        this.vigente = vigente;
    }

    /**
     * @return the creditosTotales
     */
    public int getCreditosTotales() {
        return creditosTotales;
    }

    /**
     * @param creditosTotales the creditosTotales to set
     */
    public void setCreditosTotales(int creditosTotales) {
        this.creditosTotales = creditosTotales;
    }

    /**
     * @return the bloqueado
     */
    public boolean isBloqueado() {
        return bloqueado;
    }

    /**
     * @param bloqueado the bloqueado to set
     */
    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }
}
