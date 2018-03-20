/*
 * Copyright (c) 2014, Universidad de Piura. All rights reserved.
 * UDEP PROPRIETARY/CONFIDENTIAL.
 *
 */
package com.udep.siga.bean;

/**
 *
 * @author AndySanti
 * clase para traer los eventos tipo horario pregrado
 */
public class HorarioEvento {
    private String asignatura;
    private String sigla;
    private String seccion;
    private String ambiente;
    private Integer iddia;
    private Integer idhora;
    private Integer idseccion;
    private Integer idasignatura;
    private boolean retirocurso;

    public HorarioEvento() {
    }

    
    /**
     * @return the asignatura
     */
    public String getAsignatura() {
        return asignatura;
    }

    /**
     * @param asignatura the asignatura to set
     */
    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    /**
     * @return the sigla
     */
    public String getSigla() {
        return sigla;
    }

    /**
     * @param sigla the sigla to set
     */
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    /**
     * @return the seccion
     */
    public String getSeccion() {
        return seccion;
    }

    /**
     * @param seccion the seccion to set
     */
    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    /**
     * @return the ambiente
     */
    public String getAmbiente() {
        return ambiente;
    }

    /**
     * @param ambiente the ambiente to set
     */
    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    /**
     * @return the iddia
     */
    public Integer getIddia() {
        return iddia;
    }

    /**
     * @param iddia the iddia to set
     */
    public void setIddia(Integer iddia) {
        this.iddia = iddia;
    }

    /**
     * @return the idhora
     */
    public Integer getIdhora() {
        return idhora;
    }

    /**
     * @param idhora the idhora to set
     */
    public void setIdhora(Integer idhora) {
        this.idhora = idhora;
    }

    /**
     * @return the idseccion
     */
    public Integer getIdseccion() {
        return idseccion;
    }

    /**
     * @param idseccion the idseccion to set
     */
    public void setIdseccion(Integer idseccion) {
        this.idseccion = idseccion;
    }

    /**
     * @return the retirocurso
     */
    public boolean isRetirocurso() {
        return retirocurso;
    }

    /**
     * @param retirocurso the retirocurso to set
     */
    public void setRetirocurso(boolean retirocurso) {
        this.retirocurso = retirocurso;
    }

    /**
     * @return the idasignatura
     */
    public Integer getIdasignatura() {
        return idasignatura;
    }

    /**
     * @param idasignatura the idasignatura to set
     */
    public void setIdasignatura(Integer idasignatura) {
        this.idasignatura = idasignatura;
    }

    @Override
    public String toString() {
        return "HorarioEvento{" + "asignatura=" + asignatura + ", sigla=" + sigla + ", seccion=" + seccion + ", ambiente=" + ambiente + ", iddia=" + iddia + ", idhora=" + idhora + ", idseccion=" + idseccion + ", idasignatura=" + idasignatura + ", retirocurso=" + retirocurso + '}';
    }
    
}
