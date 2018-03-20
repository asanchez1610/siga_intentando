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
public class EspecialidadAlumnoEstudio implements Serializable{
    private Especialidad especialidad;
    private AlumnoEstudio alumnoEstudio;
    private boolean activo;
    private Date fechaRegistro;
    private Persona personaRegistro;

    public EspecialidadAlumnoEstudio() {
    }

    public EspecialidadAlumnoEstudio(Especialidad especialidad, AlumnoEstudio alumnoEstudio) {
        this.especialidad = especialidad;
        this.alumnoEstudio = alumnoEstudio;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public AlumnoEstudio getAlumnoEstudio() {
        return alumnoEstudio;
    }

    public void setAlumnoEstudio(AlumnoEstudio alumnoEstudio) {
        this.alumnoEstudio = alumnoEstudio;
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
