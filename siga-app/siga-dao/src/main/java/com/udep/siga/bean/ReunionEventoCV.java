package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class ReunionEventoCV implements Serializable{

    private int id;
    private String nombre;
    private int anio;
    private String ciudad;
    private String pais;
    private String organizadoPor;
    private String descripcion;
    private String ambito;

    public ReunionEventoCV() {
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
}
