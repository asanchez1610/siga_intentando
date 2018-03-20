package com.udep.siga.bean;

import java.io.Serializable;

/**
 * Plan Estudio Alumno Asignatura Estado
 * @author Wilfredo Atoche
 */
public class PEAEAsignaturaEstado implements Serializable{
    private String sigla;
    private String nombre;
    private int creditos;
    private String regla;
    private String observacion;
    private String estado;
    private boolean aprobada;
    private boolean desaprobada;
    private boolean convalidada;
    private boolean equivalente;
    private boolean convalidadaexterna;
    private boolean exonerada;
    private String nota;
    private String color;
    
    public PEAEAsignaturaEstado(){
        
    }

    /**
     * @return the sigla
     */
    public String getSigla() {
        return sigla;
    }

    /**
     * @param sigla the sigla to set
     */
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the creditos
     */
    public int getCreditos() {
        return creditos;
    }

    /**
     * @param creditos the creditos to set
     */
    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    /**
     * @return the regla
     */
    public String getRegla() {
        return regla;
    }

    /**
     * @param regla the regla to set
     */
    public void setRegla(String regla) {
        this.regla = regla;
    }

    /**
     * @return the observacion
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * @param observacion the observacion to set
     */
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the aprobada
     */
    public boolean isAprobada() {
        return aprobada;
    }

    /**
     * @param aprobada the aprobada to set
     */
    public void setAprobada(boolean aprobada) {
        this.aprobada = aprobada;
    }

    /**
     * @return the desaprobada
     */
    public boolean isDesaprobada() {
        return desaprobada;
    }

    /**
     * @param desaprobada the desaprobada to set
     */
    public void setDesaprobada(boolean desaprobada) {
        this.desaprobada = desaprobada;
    }

    /**
     * @return the convalidada
     */
    public boolean isConvalidada() {
        return convalidada;
    }

    /**
     * @param convalidada the convalidada to set
     */
    public void setConvalidada(boolean convalidada) {
        this.convalidada = convalidada;
    }

    /**
     * @return the equivalente
     */
    public boolean isEquivalente() {
        return equivalente;
    }

    /**
     * @param equivalente the equivalente to set
     */
    public void setEquivalente(boolean equivalente) {
        this.equivalente = equivalente;
    }

    /**
     * @return the convalidadaexterna
     */
    public boolean isConvalidadaexterna() {
        return convalidadaexterna;
    }

    /**
     * @param convalidadaexterna the convalidadaexterna to set
     */
    public void setConvalidadaexterna(boolean convalidadaexterna) {
        this.convalidadaexterna = convalidadaexterna;
    }

    /**
     * @return the exonerada
     */
    public boolean isExonerada() {
        return exonerada;
    }

    /**
     * @param exonerada the exonerada to set
     */
    public void setExonerada(boolean exonerada) {
        this.exonerada = exonerada;
    }

    /**
     * @return the nota
     */
    public String getNota() {
        return nota;
    }

    /**
     * @param nota the nota to set
     */
    public void setNota(String nota) {
        this.nota = nota;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }
}
