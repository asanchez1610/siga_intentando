package com.udep.siga.bean;

import com.udep.siga.bean.enumeration.Campus;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Wilfredo Atoche
 */
public class AsignaturaDictada extends Asignatura implements Serializable {

    private PeriodoAcademico periodoAcademico;
    private Campus campus;
    private String seccionAcademica;
    private int seccionAcademicaId;
    private AsignaturaSeccion asignaturaSeccion;
    /* Utiles */
    private Nota promedio;
    private List<Persona> profesorList;
    private String tipoAsignatura;
    private List<Evaluacion> evaluacionList;
    private List<Horario> horarioList;
    private List<PracticaProgramada> practicaProgramadaList;
    private boolean retiroCurso;
    private String estudio;
    private int totalMatriculado;
    private boolean verPromedio;

    public AsignaturaDictada() {
    }

    public AsignaturaDictada(int id, int idSeccion) {
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
     * @return the campus
     */
    public Campus getCampus() {
        return campus;
    }

    /**
     * @param campus the campus to set
     */
    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    /**
     * @return the seccionAcademica
     */
    public String getSeccionAcademica() {
        if (this.seccionAcademica == null) {
            return "";
        }
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
     * @return the profesorList
     */
    public List<Persona> getProfesorList() {
        return profesorList;
    }

    /**
     * @param profesorList the profesorList to set
     */
    public void setProfesorList(List<Persona> profesorList) {
        this.profesorList = profesorList;
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
     * @return the evaluacionList
     */
    public List<Evaluacion> getEvaluacionList() {
        return evaluacionList;
    }

    /**
     * @param evaluacionList the evaluacionList to set
     */
    public void setEvaluacionList(List<Evaluacion> evaluacionList) {
        this.evaluacionList = evaluacionList;
    }

    /**
     * @return the horarioList
     */
    public List<Horario> getHorarioList() {
        return horarioList;
    }

    /**
     * @param horarioList the horarioList to set
     */
    public void setHorarioList(List<Horario> horarioList) {
        this.horarioList = horarioList;
    }

    /**
     * @return the practicaProgramadaList
     */
    public List<PracticaProgramada> getPracticaProgramadaList() {
        return practicaProgramadaList;
    }

    /**
     * @param practicaProgramadaList the practicaProgramadaList to set
     */
    public void setPracticaProgramadaList(List<PracticaProgramada> practicaProgramadaList) {
        this.practicaProgramadaList = practicaProgramadaList;
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

    /**
     * @return the totalMatriculado
     */
    public int getTotalMatriculado() {
        return totalMatriculado;
    }

    /**
     * @param totalMatriculado the totalMatriculado to set
     */
    public void setTotalMatriculado(int totalMatriculado) {
        this.totalMatriculado = totalMatriculado;
    }

    /**
     * @return the verPromedio
     */
    public boolean isVerPromedio() {
        return verPromedio;
    }

    /**
     * @param verPromedio the verPromedio to set
     */
    public void setVerPromedio(boolean verPromedio) {
        this.verPromedio = verPromedio;
    }
}
