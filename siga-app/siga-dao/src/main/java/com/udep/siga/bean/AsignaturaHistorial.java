package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class AsignaturaHistorial extends Asignatura implements Serializable{

    private PeriodoAcademico periodoAcademico;
    private Nota promedio;
    private AsignaturaSeccion asignaturaSeccion;
    private String tipoAsignatura;
    /* Utiles */
    private boolean retiroCurso;
    private boolean anulaCiclo;
    private String planEstudio;
    private String estudio;
    
    public AsignaturaHistorial() {
    }

    public AsignaturaHistorial(int id, int idSeccion) {
        this.setId(id);
        this.asignaturaSeccion = new AsignaturaSeccion(idSeccion);
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
     * @return the promedio
     */
    public Nota getPromedio() {
        return promedio;
    }

    /**
     * @param promedio the promedio to set
     */
    public void setPromedio(Nota promedio) {
        this.promedio = promedio;
    }

    /**
     * @return the asignaturaSeccion
     */
    public AsignaturaSeccion getAsignaturaSeccion() {
        return asignaturaSeccion;
    }

    /**
     * @param asignaturaSeccion the asignaturaSeccion to set
     */
    public void setAsignaturaSeccion(AsignaturaSeccion asignaturaSeccion) {
        this.asignaturaSeccion = asignaturaSeccion;
    }

    /**
     * @return the tipoAsignatura
     */
    public String getTipoAsignatura() {
        return tipoAsignatura;
    }

    /**
     * @param tipoAsignatura the tipoAsignatura to set
     */
    public void setTipoAsignatura(String tipoAsignatura) {
        this.tipoAsignatura = tipoAsignatura;
    }

    /**
     * @return the retiroCurso
     */
    public boolean isRetiroCurso() {
        return retiroCurso;
    }

    /**
     * @param retiroCurso the retiroCurso to set
     */
    public void setRetiroCurso(boolean retiroCurso) {
        this.retiroCurso = retiroCurso;
    }

    /**
     * @return the anulaCiclo
     */
    public boolean isAnulaCiclo() {
        return anulaCiclo;
    }

    /**
     * @param anulaCiclo the anulaCiclo to set
     */
    public void setAnulaCiclo(boolean anulaCiclo) {
        this.anulaCiclo = anulaCiclo;
    }

    /**
     * @return the planEstudio
     */
    public String getPlanEstudio() {
        return planEstudio;
    }

    /**
     * @param planEstudio the planEstudio to set
     */
    public void setPlanEstudio(String planEstudio) {
        this.planEstudio = planEstudio;
    }

    /**
     * @return the estudio
     */
    public String getEstudio() {
        return estudio;
    }

    /**
     * @param estudio the estudio to set
     */
    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

}
