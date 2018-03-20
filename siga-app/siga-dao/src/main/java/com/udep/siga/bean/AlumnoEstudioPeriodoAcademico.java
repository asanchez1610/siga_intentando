package com.udep.siga.bean;

import com.udep.siga.bean.enumeration.Campus;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Wilfredo Atoche
 */
public class AlumnoEstudioPeriodoAcademico implements Serializable{
    private Alumno alumno;
    private Edicionestudio edicionestudio;
    private PeriodoAcademico periodoAcademico;
    private Campus campus;
    private int creditosTotalesMatriculados;
    private int creditosTotalesConvalidados;
    private int creditosTotalesAprobados;
    private int creditosTotalesDesaprobados;
    private int creditosTotalesCumplidos;    
    private int creditosPeriodoMatriculados;
    private int creditosPeriodoConvalidados;
    private int creditosPeriodoAprobados;
    private int creditosPeriodoDesaprobados;
    private int creditosPeriodoCumplidos;
    private float indiceAcumulado;
    private float indicePeriodo;
    private float indiceBiperiodo;
    private boolean tercio;
    private boolean quinto;
    private int ordenMerito;
    private int ordenMeritoTotal;
    private int ciclo;
    private int nivel;
    private boolean anulaCiclo;
    
    /* Utiles */
    private String ordenMeritoShow;
    private String rendimientoShow;
    private String biperiodoTituloShow;
    private List<Observacion> observacionList;
    private List<AsignaturaHistorial> asignaturaDictadaList;
    
    public AlumnoEstudioPeriodoAcademico(){
        
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
     * @return the creditosTotalesMatriculados
     */
    public int getCreditosTotalesMatriculados() {
        return creditosTotalesMatriculados;
    }

    /**
     * @param creditosTotalesMatriculados the creditosTotalesMatriculados to set
     */
    public void setCreditosTotalesMatriculados(int creditosTotalesMatriculados) {
        this.creditosTotalesMatriculados = creditosTotalesMatriculados;
    }

    /**
     * @return the creditosTotalesConvalidados
     */
    public int getCreditosTotalesConvalidados() {
        return creditosTotalesConvalidados;
    }

    /**
     * @param creditosTotalesConvalidados the creditosTotalesConvalidados to set
     */
    public void setCreditosTotalesConvalidados(int creditosTotalesConvalidados) {
        this.creditosTotalesConvalidados = creditosTotalesConvalidados;
    }

    /**
     * @return the creditosTotalesAprobados
     */
    public int getCreditosTotalesAprobados() {
        return creditosTotalesAprobados;
    }

    /**
     * @param creditosTotalesAprobados the creditosTotalesAprobados to set
     */
    public void setCreditosTotalesAprobados(int creditosTotalesAprobados) {
        this.creditosTotalesAprobados = creditosTotalesAprobados;
    }

    /**
     * @return the creditosTotalesDesaprobados
     */
    public int getCreditosTotalesDesaprobados() {
        return creditosTotalesDesaprobados;
    }

    /**
     * @param creditosTotalesDesaprobados the creditosTotalesDesaprobados to set
     */
    public void setCreditosTotalesDesaprobados(int creditosTotalesDesaprobados) {
        this.creditosTotalesDesaprobados = creditosTotalesDesaprobados;
    }

    /**
     * @return the creditosTotalesCumplidos
     */
    public int getCreditosTotalesCumplidos() {
        return creditosTotalesCumplidos;
    }

    /**
     * @param creditosTotalesCumplidos the creditosTotalesCumplidos to set
     */
    public void setCreditosTotalesCumplidos(int creditosTotalesCumplidos) {
        this.creditosTotalesCumplidos = creditosTotalesCumplidos;
    }

    /**
     * @return the creditosPeriodoMatriculados
     */
    public int getCreditosPeriodoMatriculados() {
        return creditosPeriodoMatriculados;
    }

    /**
     * @param creditosPeriodoMatriculados the creditosPeriodoMatriculados to set
     */
    public void setCreditosPeriodoMatriculados(int creditosPeriodoMatriculados) {
        this.creditosPeriodoMatriculados = creditosPeriodoMatriculados;
    }

    /**
     * @return the creditosPeriodoConvalidados
     */
    public int getCreditosPeriodoConvalidados() {
        return creditosPeriodoConvalidados;
    }

    /**
     * @param creditosPeriodoConvalidados the creditosPeriodoConvalidados to set
     */
    public void setCreditosPeriodoConvalidados(int creditosPeriodoConvalidados) {
        this.creditosPeriodoConvalidados = creditosPeriodoConvalidados;
    }

    /**
     * @return the creditosPeriodoAprobados
     */
    public int getCreditosPeriodoAprobados() {
        return creditosPeriodoAprobados;
    }

    /**
     * @param creditosPeriodoAprobados the creditosPeriodoAprobados to set
     */
    public void setCreditosPeriodoAprobados(int creditosPeriodoAprobados) {
        this.creditosPeriodoAprobados = creditosPeriodoAprobados;
    }

    /**
     * @return the creditosPeriodoDesaprobados
     */
    public int getCreditosPeriodoDesaprobados() {
        return creditosPeriodoDesaprobados;
    }

    /**
     * @param creditosPeriodoDesaprobados the creditosPeriodoDesaprobados to set
     */
    public void setCreditosPeriodoDesaprobados(int creditosPeriodoDesaprobados) {
        this.creditosPeriodoDesaprobados = creditosPeriodoDesaprobados;
    }

    /**
     * @return the creditosPeriodoCumplidos
     */
    public int getCreditosPeriodoCumplidos() {
        return creditosPeriodoCumplidos;
    }

    /**
     * @param creditosPeriodoCumplidos the creditosPeriodoCumplidos to set
     */
    public void setCreditosPeriodoCumplidos(int creditosPeriodoCumplidos) {
        this.creditosPeriodoCumplidos = creditosPeriodoCumplidos;
    }

    /**
     * @return the indiceAcumulado
     */
    public float getIndiceAcumulado() {
        return indiceAcumulado;
    }

    /**
     * @param indiceAcumulado the indiceAcumulado to set
     */
    public void setIndiceAcumulado(float indiceAcumulado) {
        this.indiceAcumulado = indiceAcumulado;
    }

    /**
     * @return the indicePeriodo
     */
    public float getIndicePeriodo() {
        return indicePeriodo;
    }

    /**
     * @param indicePeriodo the indicePeriodo to set
     */
    public void setIndicePeriodo(float indicePeriodo) {
        this.indicePeriodo = indicePeriodo;
    }

    /**
     * @return the indiceBiperiodo
     */
    public float getIndiceBiperiodo() {
        return indiceBiperiodo;
    }

    /**
     * @param indiceBiperiodo the indiceBiperiodo to set
     */
    public void setIndiceBiperiodo(float indiceBiperiodo) {
        this.indiceBiperiodo = indiceBiperiodo;
    }

    /**
     * @return the tercio
     */
    public boolean isTercio() {
        return tercio;
    }

    /**
     * @param tercio the tercio to set
     */
    public void setTercio(boolean tercio) {
        this.tercio = tercio;
    }

    /**
     * @return the quinto
     */
    public boolean isQuinto() {
        return quinto;
    }

    /**
     * @param quinto the quinto to set
     */
    public void setQuinto(boolean quinto) {
        this.quinto = quinto;
    }

    /**
     * @return the ordenMerito
     */
    public int getOrdenMerito() {
        return ordenMerito;
    }

    /**
     * @param ordenMerito the ordenMerito to set
     */
    public void setOrdenMerito(int ordenMerito) {
        this.ordenMerito = ordenMerito;
    }

    /**
     * @return the ordenMeritoTotal
     */
    public int getOrdenMeritoTotal() {
        return ordenMeritoTotal;
    }

    /**
     * @param ordenMeritoTotal the ordenMeritoTotal to set
     */
    public void setOrdenMeritoTotal(int ordenMeritoTotal) {
        this.ordenMeritoTotal = ordenMeritoTotal;
    }

    /**
     * @return the ciclo
     */
    public int getCiclo() {
        return ciclo;
    }

    /**
     * @param ciclo the ciclo to set
     */
    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

    /**
     * @return the nivel
     */
    public int getNivel() {
        return nivel;
    }

    /**
     * @param nivel the nivel to set
     */
    public void setNivel(int nivel) {
        this.nivel = nivel;
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
     * @return the ordenMeritoShow
     */
    public String getOrdenMeritoShow() {
        return ordenMeritoShow;
    }

    /**
     * @param ordenMeritoShow the ordenMeritoShow to set
     */
    public void setOrdenMeritoShow(String ordenMeritoShow) {
        this.ordenMeritoShow = ordenMeritoShow;
    }

    /**
     * @return the rendimientoShow
     */
    public String getRendimientoShow() {
        return rendimientoShow;
    }

    /**
     * @param rendimientoShow the rendimientoShow to set
     */
    public void setRendimientoShow(String rendimientoShow) {
        this.rendimientoShow = rendimientoShow;
    }

    /**
     * @return the biperiodoTituloShow
     */
    public String getBiperiodoTituloShow() {
        return biperiodoTituloShow;
    }

    /**
     * @param biperiodoTituloShow the biperiodoTituloShow to set
     */
    public void setBiperiodoTituloShow(String biperiodoTituloShow) {
        this.biperiodoTituloShow = biperiodoTituloShow;
    }

    /**
     * @return the observacionList
     */
    public List<Observacion> getObservacionList() {
        return observacionList;
    }

    /**
     * @param observacionList the observacionList to set
     */
    public void setObservacionList(List<Observacion> observacionList) {
        this.observacionList = observacionList;
    }

    /**
     * @return the asignaturaDictadaList
     */
    public List<AsignaturaHistorial> getAsignaturaDictadaList() {
        return asignaturaDictadaList;
    }

    /**
     * @param asignaturaDictadaList the asignaturaDictadaList to set
     */
    public void setAsignaturaDictadaList(List<AsignaturaHistorial> asignaturaDictadaList) {
        this.asignaturaDictadaList = asignaturaDictadaList;
    }
}
