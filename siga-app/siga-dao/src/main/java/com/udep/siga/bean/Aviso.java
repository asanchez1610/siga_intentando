package com.udep.siga.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.util.JsonDateSerializer;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Wilfredo Atoche
 */
public class Aviso implements Serializable{
    private int id;
    private Persona persona;
    private String titulo;
    private String descripcion;
    private Date fecha;
    private Date caducidad;
    private boolean general;
    private String rutaArchivo;
    private boolean paraAlumnos;
    private boolean paraProfesores;
    private boolean stick;
    
    public Aviso(){}

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
     * @return the persona
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(Persona persona) {
        this.persona = persona;
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
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the caducidad
     */
    @JsonSerialize(using=JsonDateSerializer.class)
    public Date getCaducidad() {
        return caducidad;
    }

    /**
     * @param caducidad the caducidad to set
     */
    public void setCaducidad(Date caducidad) {
        this.caducidad = caducidad;
    }

    /**
     * @return the general
     */
    public boolean isGeneral() {
        return general;
    }

    /**
     * @param general the general to set
     */
    public void setGeneral(boolean general) {
        this.general = general;
    }

    /**
     * @return the rutaArchivo
     */
    public String getRutaArchivo() {
        return rutaArchivo;
    }

    /**
     * @param rutaArchivo the rutaArchivo to set
     */
    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    /**
     * @return the paraAlumnos
     */
    public boolean isParaAlumnos() {
        return paraAlumnos;
    }

    /**
     * @param paraAlumnos the paraAlumnos to set
     */
    public void setParaAlumnos(boolean paraAlumnos) {
        this.paraAlumnos = paraAlumnos;
    }

    /**
     * @return the paraProfesores
     */
    public boolean isParaProfesores() {
        return paraProfesores;
    }

    /**
     * @param paraProfesores the paraProfesores to set
     */
    public void setParaProfesores(boolean paraProfesores) {
        this.paraProfesores = paraProfesores;
    }

    /**
     * @return the stick
     */
    public boolean isStick() {
        return stick;
    }

    /**
     * @param stick the stick to set
     */
    public void setStick(boolean stick) {
        this.stick = stick;
    }
    
    
}
