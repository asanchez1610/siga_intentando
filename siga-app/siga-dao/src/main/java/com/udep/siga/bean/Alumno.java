package com.udep.siga.bean;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Wilfredo Atoche
 */
public class Alumno extends Persona implements Serializable{
    private String carne;
    private boolean actualizaClave;
    private boolean actualizaDatos;
    private boolean actualizaAsesor;
    
    /* Utiles */    
    private boolean llenarEdicionEstudio;
    private List<AlumnoEstudio> alumnoEstudioList;
    private List<AlumnoEstudio> alumnoEstudioInactivoList;
    private DatoPersonal datoPersonal;
    
    public Alumno(){
        this.llenarEdicionEstudio = true;
    }
    
    public Alumno(int id){
        this.setId(id);
        this.llenarEdicionEstudio = true;
    }
    
    /**
     * @return the carne
     */
    public String getCarne() {
        return carne;
    }

    /**
     * @param carne the carne to set
     */
    public void setCarne(String carne) {
        this.carne = carne;
    }

    /**
     * @return the actualizaClave
     */
    public boolean isActualizaClave() {
        return actualizaClave;
    }

    /**
     * @param actualizaClave the actualizaClave to set
     */
    public void setActualizaClave(boolean actualizaClave) {
        this.actualizaClave = actualizaClave;
    }

    /**
     * @return the actualizaDatos
     */
    public boolean isActualizaDatos() {
        return actualizaDatos;
    }

    /**
     * @param actualizaDatos the actualizaDatos to set
     */
    public void setActualizaDatos(boolean actualizaDatos) {
        this.actualizaDatos = actualizaDatos;
    }

    /**
     * @return the actualizaAsesor
     */
    public boolean isActualizaAsesor() {
        return actualizaAsesor;
    }

    /**
     * @param actualizaAsesor the actualizaAsesor to set
     */
    public void setActualizaAsesor(boolean actualizaAsesor) {
        this.actualizaAsesor = actualizaAsesor;
    }

    /**
     * @return the alumnoEstudioList
     */
    public List<AlumnoEstudio> getAlumnoEstudioList() {
        return alumnoEstudioList;
    }

    /**
     * @param alumnoEstudioList the alumnoEstudioList to set
     */
    public void setAlumnoEstudioList(List<AlumnoEstudio> alumnoEstudioList) {
        this.alumnoEstudioList = alumnoEstudioList;
    }

    /**
     * @return the llenarEdicionEstudio
     */
    public boolean isLlenarEdicionEstudio() {
        return llenarEdicionEstudio;
    }

    /**
     * @param llenarEdicionEstudio the llenarEdicionEstudio to set
     */
    public void setLlenarEdicionEstudio(boolean llenarEdicionEstudio) {
        this.llenarEdicionEstudio = llenarEdicionEstudio;
    }

    /**
     * @return the datoPersonal
     */
    public DatoPersonal getDatoPersonal() {
        return datoPersonal;
    }

    /**
     * @param datoPersonal the datoPersonal to set
     */
    public void setDatoPersonal(DatoPersonal datoPersonal) {
        this.datoPersonal = datoPersonal;
    }

    /**
     * @return the alumnoEstudioInactivoList
     */
    public List<AlumnoEstudio> getAlumnoEstudioInactivoList() {
        return alumnoEstudioInactivoList;
    }

    /**
     * @param alumnoEstudioInactivoList the alumnoEstudioInactivoList to set
     */
    public void setAlumnoEstudioInactivoList(List<AlumnoEstudio> alumnoEstudioInactivoList) {
        this.alumnoEstudioInactivoList = alumnoEstudioInactivoList;
    }
}
