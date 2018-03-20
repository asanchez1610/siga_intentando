package com.udep.siga.bean;

import com.udep.siga.bean.enumeration.TipoAsignatura;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Wilfredo Atoche
 */
public class PlanEstudioAsignatura implements Serializable{
    private int id;
    private int numPeriodo;    
    private TipoAsignatura tipoAsignatura;
    private String seccionAcademica;
    private int seccionAcademicaId;
    private Asignatura asignatura;
    /* Variable Auxiliar */
    private int estadoEnMalla;
    private List<InfoRequisito> requisitosList;
    private int notaHistorial;
    private int nivel;

    public PlanEstudioAsignatura(){}
        
    public PlanEstudioAsignatura(int id, int asignaturaId){
        this.id = id;
        this.asignatura = new Asignatura(asignaturaId);
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
     * @return the numPeriodo
     */
    public int getNumPeriodo() {
        return numPeriodo;
    }

    /**
     * @param numPeriodo the numPeriodo to set
     */
    public void setNumPeriodo(int numPeriodo) {
        this.numPeriodo = numPeriodo;
    }   

    /**
     * @return the tipoAsignatura
     */
    public TipoAsignatura getTipoAsignatura() {
        return tipoAsignatura;
    }

    /**
     * @param tipoAsignatura the tipoAsignatura to set
     */
    public void setTipoAsignatura(TipoAsignatura tipoAsignatura) {
        this.tipoAsignatura = tipoAsignatura;
    }

    /**
     * @return the seccionAcademica
     */
    public String getSeccionAcademica() {
        return seccionAcademica;
    }

    /**
     * @param seccionAcademica the seccionAcademica to set
     */
    public void setSeccionAcademica(String seccionAcademica) {
        this.seccionAcademica = seccionAcademica;
    }

    /**
     * @return the seccionAcademicaId
     */
    public int getSeccionAcademicaId() {
        return seccionAcademicaId;
    }

    /**
     * @param seccionAcademicaId the seccionAcademicaId to set
     */
    public void setSeccionAcademicaId(int seccionAcademicaId) {
        this.seccionAcademicaId = seccionAcademicaId;
    }

    /**
     * @return the asignatura
     */
    public Asignatura getAsignatura() {
        return asignatura;
    }

    /**
     * @param asignatura the asignatura to set
     */
    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    /**
     * @return the estadoEnMalla
     */
    public int getEstadoEnMalla() {
        return estadoEnMalla;
    }

    /**
     * @param estadoEnMalla the estadoEnMalla to set
     */
    public void setEstadoEnMalla(int estadoEnMalla) {
        this.estadoEnMalla = estadoEnMalla;
    }

    /**
     * @return the requisitosList
     */
    public List<InfoRequisito> getRequisitosList() {
        return requisitosList;
    }

    /**
     * @param requisitosList the requisitosList to set
     */
    public void setRequisitosList(List<InfoRequisito> requisitosList) {
        this.requisitosList = requisitosList;
    }

    /**
     * @return the notaHistorial
     */
    public int getNotaHistorial() {
        return notaHistorial;
    }

    /**
     * @param notaHistorial the notaHistorial to set
     */
    public void setNotaHistorial(int notaHistorial) {
        this.notaHistorial = notaHistorial;
    }

    public int getNivel() {
        return Math.round((float)this.getNumPeriodo() / 2);
    }    
}
