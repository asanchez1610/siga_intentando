package com.udep.siga.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class CLCCategAsigIntercambioEstud implements Serializable{

    private int id;
    private Date fecha;
    private String asignatura;
    private String institucion;
    private String semestre;
    private int credReconocido;

    public CLCCategAsigIntercambioEstud() {
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
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
     * @return the semestre
     */
    public String getSemestre() {
        return semestre;
    }

    /**
     * @param semestre the semestre to set
     */
    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    /**
     * @return the credReconocido
     */
    public int getCredReconocido() {
        return credReconocido;
    }

    /**
     * @param credReconocido the credReconocido to set
     */
    public void setCredReconocido(int credReconocido) {
        this.credReconocido = credReconocido;
    }
}
