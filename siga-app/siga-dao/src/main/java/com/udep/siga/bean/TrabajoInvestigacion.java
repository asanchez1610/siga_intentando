package com.udep.siga.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class TrabajoInvestigacion implements Serializable{

    private int id;
    private String titulo;
    private Profesor investigador;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private String resultado;
    private String autores;
    private LineaInvestigacion campoInvestigacion;

    public TrabajoInvestigacion() {
    }

    public TrabajoInvestigacion(int id) {
        this.id = id;
    }

    public TrabajoInvestigacion(int id, String titulo) {
        this.id = id;
        this.titulo = titulo;
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
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the investigador
     */
    public Profesor getInvestigador() {
        return investigador;
    }

    /**
     * @param investigador the investigador to set
     */
    public void setInvestigador(Profesor investigador) {
        this.investigador = investigador;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the autores
     */
    public String getAutores() {
        return autores;
    }

    /**
     * @param autores the autores to set
     */
    public void setAutores(String autores) {
        this.autores = autores;
    }

    /**
     * @return the resultado
     */
    public String getResultado() {
        return resultado;
    }

    /**
     * @param resultado the resultado to set
     */
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    /**
     * @return the campoInvestigacion
     */
    public LineaInvestigacion getCampoInvestigacion() {
        return campoInvestigacion;
    }

    /**
     * @param campoInvestigacion the campoInvestigacion to set
     */
    public void setCampoInvestigacion(LineaInvestigacion campoInvestigacion) {
        this.campoInvestigacion = campoInvestigacion;
    }
}
