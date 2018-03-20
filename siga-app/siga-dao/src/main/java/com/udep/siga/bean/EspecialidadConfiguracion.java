/*
 * Copyright (c) 2014, Universidad de Piura. All rights reserved.
 * UDEP PROPRIETARY/CONFIDENTIAL.
 *
 */
package com.udep.siga.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Jose Chero Sojo
 */
public class EspecialidadConfiguracion implements Serializable{
    private int id;
    private PlanEstudio planEstudio;
    private int desdeCred;
    private int hastaCred;
    private boolean activo;
    private Date fechaRegistro;
    private Persona personaRegistro;

    public EspecialidadConfiguracion() {
    }

    public EspecialidadConfiguracion(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PlanEstudio getPlanEstudio() {
        return planEstudio;
    }

    public void setPlanEstudio(PlanEstudio planEstudio) {
        this.planEstudio = planEstudio;
    }

    public int getDesdeCred() {
        return desdeCred;
    }

    public void setDesdeCred(int desdeCred) {
        this.desdeCred = desdeCred;
    }

    public int getHastaCred() {
        return hastaCred;
    }

    public void setHastaCred(int hastaCred) {
        this.hastaCred = hastaCred;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Persona getPersonaRegistro() {
        return personaRegistro;
    }

    public void setPersonaRegistro(Persona personaRegistro) {
        this.personaRegistro = personaRegistro;
    }
}
