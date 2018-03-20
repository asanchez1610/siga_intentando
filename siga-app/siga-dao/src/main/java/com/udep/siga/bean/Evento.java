package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class Evento implements Serializable{

    private int id;
    private String tituloPonencia;
    private String numEvento;
    private String nombre;
    private String organizadoPor;
    private String ambito;
    private String descripcion;
    private String fecha;
    private int dia;
    private int mes;
    private int anio;
    private String ciudad;
    private String pais;

    public Evento() {
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
     * @return the tituloPonencia
     */
    public String getTituloPonencia() {
        return tituloPonencia;
    }

    /**
     * @param tituloPonencia the tituloPonencia to set
     */
    public void setTituloPonencia(String tituloPonencia) {
        this.tituloPonencia = tituloPonencia;
    }

    /**
     * @return the numEvento
     */
    public String getNumEvento() {
        return numEvento;
    }

    /**
     * @param numEvento the numEvento to set
     */
    public void setNumEvento(String numEvento) {
        this.numEvento = numEvento;
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
     * @return the organizadoPor
     */
    public String getOrganizadoPor() {
        return organizadoPor;
    }

    /**
     * @param organizadoPor the organizadoPor to set
     */
    public void setOrganizadoPor(String organizadoPor) {
        this.organizadoPor = organizadoPor;
    }

    /**
     * @return the ambito
     */
    public String getAmbito() {
        return ambito;
    }

    /**
     * @param ambito the ambito to set
     */
    public void setAmbito(String ambito) {
        this.ambito = ambito;
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
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the dia
     */
    public int getDia() {
        return dia;
    }

    /**
     * @param dia the dia to set
     */
    public void setDia(int dia) {
        this.dia = dia;
    }

    /**
     * @return the mes
     */
    public int getMes() {
        return mes;
    }

    /**
     * @param mes the mes to set
     */
    public void setMes(int mes) {
        this.mes = mes;
    }

    /**
     * @return the anio
     */
    public int getAnio() {
        return anio;
    }

    /**
     * @param anio the anio to set
     */
    public void setAnio(int anio) {
        this.anio = anio;
    }

    /**
     * @return the ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * @param ciudad the ciudad to set
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * @return the pais
     */
    public String getPais() {
        return pais;
    }

    /**
     * @param pais the pais to set
     */
    public void setPais(String pais) {
        this.pais = pais;
    }
}
