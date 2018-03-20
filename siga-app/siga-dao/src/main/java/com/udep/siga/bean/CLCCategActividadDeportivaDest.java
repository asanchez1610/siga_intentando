package com.udep.siga.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class CLCCategActividadDeportivaDest implements Serializable{

    private int id;
    private Date fecha;
    private String deporte;
    private String institucion;
    private String semestre;
    private int credReconocido;

    public CLCCategActividadDeportivaDest() {
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
     * @return the deporte
     */
    public String getDeporte() {
        return deporte;
    }

    /**
     * @param deporte the deporte to set
     */
    public void setDeporte(String deporte) {
        this.deporte = deporte;
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
