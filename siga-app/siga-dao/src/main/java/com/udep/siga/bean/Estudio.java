package com.udep.siga.bean;

import com.udep.siga.bean.enumeration.TipoEstudio;
import java.io.Serializable;

/**
 *
 * @author Wilfredo Atoche
 */
public class Estudio implements Serializable{

    private int id;
    private String sigla;
    private String nombre;
    private boolean activo;
    private CentroAcademico centroAcademico;
    private TipoEstudio tipoEstudio;

    public Estudio() {
    }

    public Estudio(int id) {
        this.id = id;
    }

    public Estudio(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
        if (nombre == null) {
            return "";
        }
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
     * @return the centroAcademico
     */
    public CentroAcademico getCentroAcademico() {
        return centroAcademico;
    }

    /**
     * @param centroAcademico the centroAcademico to set
     */
    public void setCentroAcademico(CentroAcademico centroAcademico) {
        this.centroAcademico = centroAcademico;
    }

    /**
     * @return the tipoEstudio
     */
    public TipoEstudio getTipoEstudio() {
        return tipoEstudio;
    }

    /**
     * @param tipoEstudio the tipoEstudio to set
     */
    public void setTipoEstudio(TipoEstudio tipoEstudio) {
        this.tipoEstudio = tipoEstudio;
    }
}
