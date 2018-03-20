package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class AlumnoHistorial implements Serializable{

    private Alumno alumno;
    private PeriodoAcademico periodoAcademico;
    private AsignaturaDictada asignaturaDictada;
    private Edicionestudio edicionestudio;

    public AlumnoHistorial() {
    }

    /**
     * @return the alumno
     */
    public Alumno getAlumno() {
        return alumno;
    }

    /**
     * @param alumno the alumno to set
     */
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    /**
     * @return the periodoAcademico
     */
    public PeriodoAcademico getPeriodoAcademico() {
        return periodoAcademico;
    }

    /**
     * @param periodoAcademico the periodoAcademico to set
     */
    public void setPeriodoAcademico(PeriodoAcademico periodoAcademico) {
        this.periodoAcademico = periodoAcademico;
    }

    /**
     * @return the edicionestudio
     */
    public Edicionestudio getEdicionestudio() {
        return edicionestudio;
    }

    /**
     * @param edicionestudio the edicionestudio to set
     */
    public void setEdicionestudio(Edicionestudio edicionestudio) {
        this.edicionestudio = edicionestudio;
    }

    /**
     * @return the asignaturaDictada
     */
    public AsignaturaDictada getAsignaturaDictada() {
        return asignaturaDictada;
    }

    /**
     * @param asignaturaDictada the asignaturaDictada to set
     */
    public void setAsignaturaDictada(AsignaturaDictada asignaturaDictada) {
        this.asignaturaDictada = asignaturaDictada;
    }
}
