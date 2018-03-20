package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Wilfredo Atoche
 */
public class Profesor extends Persona implements Serializable{
    private boolean activo;
    private String nombreCorto;
    private boolean mostrarNombreCorto;
    private String oficina;
    
    /* Utiles */
    private String centroAcademicoNombre;
    private String departamentoNombre;
    
    public Profesor(){
        
    }
    
    public Profesor(int id){
        this.setId(id);
    }

    /**
     * @return the activo
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * @return the nombreCorto
     */
    public String getNombreCorto() {
        return nombreCorto;
    }

    /**
     * @param nombreCorto the nombreCorto to set
     */
    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    /**
     * @return the mostrarNombreCorto
     */
    public boolean isMostrarNombreCorto() {
        return mostrarNombreCorto;
    }

    /**
     * @param mostrarNombreCorto the mostrarNombreCorto to set
     */
    public void setMostrarNombreCorto(boolean mostrarNombreCorto) {
        this.mostrarNombreCorto = mostrarNombreCorto;
    }

    /**
     * @return the oficina
     */
    public String getOficina() {
        return oficina;
    }

    /**
     * @param oficina the oficina to set
     */
    public void setOficina(String oficina) {
        this.oficina = oficina;
    }

    /**
     * @return the centroAcademicoNombre
     */
    public String getCentroAcademicoNombre() {
        return centroAcademicoNombre;
    }

    /**
     * @param centroAcademicoNombre the centroAcademicoNombre to set
     */
    public void setCentroAcademicoNombre(String centroAcademicoNombre) {
        this.centroAcademicoNombre = centroAcademicoNombre;
    }

    /**
     * @return the departamentoNombre
     */
    public String getDepartamentoNombre() {
        return departamentoNombre;
    }

    /**
     * @param departamentoNombre the departamentoNombre to set
     */
    public void setDepartamentoNombre(String departamentoNombre) {
        this.departamentoNombre = departamentoNombre;
    }
    
    
}
