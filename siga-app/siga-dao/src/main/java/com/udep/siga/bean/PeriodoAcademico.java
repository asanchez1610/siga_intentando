package com.udep.siga.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.enumeration.PeriodicidadPlanEstudio;
import com.udep.siga.bean.enumeration.TipoPeriodoAcademico;
import com.udep.siga.bean.util.JsonDateSerializer;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Wilfredo Atoche
 */
public class PeriodoAcademico implements Serializable{
    private int id;
    private String nombre;
    private boolean vigente;
    private boolean enPreparacion;
    private boolean activo;
    private boolean ofertavisible;
    private int orden;
    private Date fechaInicio;
    private Date fechaFin;
    private TipoPeriodoAcademico tipoPeriodoAcademico;
    private PeriodicidadPlanEstudio periodicidadPlanEstudio;
    
    public PeriodoAcademico(){   
    }
    
    public PeriodoAcademico(int id){ 
        this.id = id;
    }
    
    public PeriodoAcademico(int id,String nombre){ 
        this.id = id;
        this.nombre = nombre;
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
        if(this.nombre == null){
            return "";
        }
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
     public boolean isOfertaVisible() {
        return ofertavisible;
    }

    /**
     * @param vigente the vigente to set
     */
    public void setOfertaVisible(boolean vigente) {
        this.ofertavisible = vigente;
    }
    /**
     * @return the enPreparacion
     */
    public boolean isEnPreparacion() {
        return enPreparacion;
    }

    /**
     * @param enPreparacion the enPreparacion to set
     */
    public void setEnPreparacion(boolean enPreparacion) {
        this.enPreparacion = enPreparacion;
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
     * @return the orden
     */
    public int getOrden() {
        return orden;
    }

    /**
     * @param orden the orden to set
     */
    public void setOrden(int orden) {
        this.orden = orden;
    }

    /**
     * @return the tipoPeriodoAcademico
     */
    public TipoPeriodoAcademico getTipoPeriodoAcademico() {
        return tipoPeriodoAcademico;
    }

    /**
     * @param tipoPeriodoAcademico the tipoPeriodoAcademico to set
     */
    public void setTipoPeriodoAcademico(TipoPeriodoAcademico tipoPeriodoAcademico) {
        this.tipoPeriodoAcademico = tipoPeriodoAcademico;
    }

    /**
     * @return the periodicidadPlanEstudio
     */
    public PeriodicidadPlanEstudio getPeriodicidadPlanEstudio() {
        return periodicidadPlanEstudio;
    }

    /**
     * @param periodicidadPlanEstudio the periodicidadPlanEstudio to set
     */
    public void setPeriodicidadPlanEstudio(PeriodicidadPlanEstudio periodicidadPlanEstudio) {
        this.periodicidadPlanEstudio = periodicidadPlanEstudio;
    }

    /**
     * @return the fechaInicio
     */
    @JsonSerialize(using = JsonDateSerializer.class)
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
    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    
}
