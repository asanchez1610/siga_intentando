package com.udep.siga.bean;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class DocenciaCV implements Serializable{

    private int id;
    private String centroEstudio;
    private boolean hastaLaFecha;
    private int anioInicio;
    private int anioFin;
    //utiles
    private List<CursoDictadoCV> cursos;

    public DocenciaCV() {
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
     * @return the centroEstudio
     */
    public String getCentroEstudio() {
        return centroEstudio;
    }

    /**
     * @param centroEstudio the centroEstudio to set
     */
    public void setCentroEstudio(String centroEstudio) {
        this.centroEstudio = centroEstudio;
    }

    /**
     * @return the hastaLaFecha
     */
    public boolean isHastaLaFecha() {
        return hastaLaFecha;
    }

    /**
     * @param hastaLaFecha the hastaLaFecha to set
     */
    public void setHastaLaFecha(boolean hastaLaFecha) {
        this.hastaLaFecha = hastaLaFecha;
    }

    /**
     * @return the anioInicio
     */
    public int getAnioInicio() {
        return anioInicio;
    }

    /**
     * @param anioInicio the anioInicio to set
     */
    public void setAnioInicio(int anioInicio) {
        this.anioInicio = anioInicio;
    }

    /**
     * @return the anioFin
     */
    public int getAnioFin() {
        return anioFin;
    }

    /**
     * @param anioFin the anioFin to set
     */
    public void setAnioFin(int anioFin) {
        this.anioFin = anioFin;
    }

    /**
     * @return the cursos
     */
    public List<CursoDictadoCV> getCursos() {
        return cursos;
    }

    /**
     * @param cursos the cursos to set
     */
    public void setCursos(List<CursoDictadoCV> cursos) {
        this.cursos = cursos;
    }
}
