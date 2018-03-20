package com.udep.siga.bean;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class Silabo implements Serializable {

    private int id;
    private AsignaturaDictada asignaturaDictada;
    private String sumilla;
    private String fundamentacion;
    private String descripcionEvaluacion;
    private List<ObjetivoSilabo> objetivoList;
    private List<UnidadSilabo> unidadList;
    private List<EstrategiaSilabo> estrategiaList;
    private List<String> bibliografiaBasica;
    private List<String> bibliografiaavanzada;
    private List<TipoEvaluacion> tipoEvaluacionList;
    
    //utiles
    private String centros;
    private String programas;

    public Silabo() {
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

    /**
     * @return the sumilla
     */
    public String getSumilla() {
        return sumilla;
    }

    /**
     * @param sumilla the sumilla to set
     */
    public void setSumilla(String sumilla) {
        this.sumilla = sumilla;
    }

    public String getFundamentacion() {
        return fundamentacion;
    }

    public void setFundamentacion(String fundamentacion) {
        this.fundamentacion = fundamentacion;
    }

    public List<ObjetivoSilabo> getObjetivoList() {
        return this.objetivoList;
    }

    public void setObjetivoList(List<ObjetivoSilabo> objetivoList) {
        this.objetivoList = objetivoList;
    }

    /**
     * @return the descripcionEvaluacion
     */
    public String getDescripcionEvaluacion() {
        return descripcionEvaluacion;
    }

    /**
    /**
     * @param descripcionEvaluacion the descripcionEvaluacion to set
     */
    public void setDescripcionEvaluacion(String descripcionEvaluacion) {
        this.descripcionEvaluacion = descripcionEvaluacion;
    }
 /**
     * @return the unidadList
     */
    public List<UnidadSilabo> getUnidadList() {
        return unidadList;
    }

    /**
     * @param unidadList the unidadList to set
     */
    public void setUnidadList(List<UnidadSilabo> unidadList) {
        this.unidadList = unidadList;
    }
     /**
     * @return the bibliografiaBasica
     */
    public List<String> getBibliografiaBasica() {
        return bibliografiaBasica;
    }

    /**
     * @param bibliografiaBasica the bibliografiaBasica to set
     */
    public void setBibliografiaBasica(List<String> bibliografiaBasica) {
        this.bibliografiaBasica = bibliografiaBasica;
    }

    /**
     * @return the bibliografiaavanzada
     */
    public List<String> getBibliografiaavanzada() {
        return bibliografiaavanzada;
    }

    /**
     * @param bibliografiaavanzada the bibliografiaavanzada to set
     */
    public void setBibliografiaavanzada(List<String> bibliografiaavanzada) {
        this.bibliografiaavanzada = bibliografiaavanzada;
    }

    /**
     * @return the estrategiaList
     */
    public List<EstrategiaSilabo> getEstrategiaList() {
        return estrategiaList;
    }

    /**
     * @param estrategiaList the estrategiaList to set
     */
    public void setEstrategiaList(List<EstrategiaSilabo> estrategiaList) {
        this.estrategiaList = estrategiaList;
    }

    /**
     * @return the tipoEvaluacionList
     */
    public List<TipoEvaluacion> getTipoEvaluacionList() {
        return tipoEvaluacionList;
    }

    /**
     * @param tipoEvaluacionList the tipoEvaluacionList to set
     */
    public void setTipoEvaluacionList(List<TipoEvaluacion> tipoEvaluacionList) {
        this.tipoEvaluacionList = tipoEvaluacionList;
    }

    /**
     * @return the centros
     */
    public String getCentros() {
        return centros;
    }

    /**
     * @param centros the centros to set
     */
    public void setCentros(String centros) {
        this.centros = centros;
    }

    /**
     * @return the programas
     */
    public String getProgramas() {
        return programas;
    }

    /**
     * @param programas the programas to set
     */
    public void setProgramas(String programas) {
        this.programas = programas;
    }
}   