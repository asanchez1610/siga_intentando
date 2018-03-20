/*
 * Copyright (c) 2014, Universidad de Piura. All rights reserved.
 * UDEP PROPRIETARY/CONFIDENTIAL.
 *
 */
package com.udep.siga.bean;

/**
 *
 * @author AndySanti
 */
public class AsignaturaEncuesta {

    private int id;
    private int idasignatura;
    private String nombreasignatura;
    private int idestudio;
    private Boolean activo;
    private String sigla;
    

    public AsignaturaEncuesta() {
    }
    
    public AsignaturaEncuesta(int id,int idasignatura, String nombreasignatura, int idestudio, Boolean activo,String sigla) {
        this.id=id;
        this.idasignatura = idasignatura;
        this.nombreasignatura = nombreasignatura;
        this.idestudio = idestudio;
        this.activo = activo;
        this.sigla=sigla;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getIdasignatura() {
        return idasignatura;
    }

    public void setIdasignatura(int idasignatura) {
        this.idasignatura = idasignatura;
    }

    public String getNombreasignatura() {
        return nombreasignatura;
    }

    public void setNombreasignatura(String nombreasignatura) {
        this.nombreasignatura = nombreasignatura;
    }

    public int getIdestudio() {
        return idestudio;
    }

    public void setIdestudio(int idestudio) {
        this.idestudio = idestudio;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
    
    
}
