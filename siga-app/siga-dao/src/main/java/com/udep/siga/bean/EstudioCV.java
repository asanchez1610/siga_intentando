package com.udep.siga.bean;

import com.udep.siga.bean.enumeration.TipoGrado;
import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class EstudioCV implements Serializable {

    private int id;
    private String anio;
    private String universidad;
    private String gradoTitulo;
    private String tituloTesis;
    private String pais;
    private TipoGrado tipoGrado;

    public EstudioCV() {
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
     * @return the anio
     */
    public String getAnio() {
        return anio;
    }

    /**
     * @param anio the anio to set
     */
    public void setAnio(String anio) {
        this.anio = anio;
    }

    /**
     * @return the universidad
     */
    public String getUniversidad() {
        return universidad;
    }

    /**
     * @param universidad the universidad to set
     */
    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    /**
     * @return the gradoTitulo
     */
    public String getGradoTitulo() {
        return gradoTitulo;
    }

    /**
     * @param gradoTitulo the gradoTitulo to set
     */
    public void setGradoTitulo(String gradoTitulo) {
        this.gradoTitulo = gradoTitulo;
    }

    /**
     * @return the tituloTesis
     */
    public String getTituloTesis() {
        if (tituloTesis == null || tituloTesis.equals(".")) {
            return "";
        }
        return tituloTesis;
    }

    /**
     * @param tituloTesis the tituloTesis to set
     */
    public void setTituloTesis(String tituloTesis) {
        this.tituloTesis = tituloTesis;
    }

    /**
     * @return the pais
     */
    public String getPais() {
        if (this.pais == null) {
            return "";
        }
        return pais;
    }

    /**
     * @param pais the pais to set
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * @return the tipoGrado
     */
    public TipoGrado getTipoGrado() {
        return tipoGrado;
    }

    /**
     * @param tipoGrado the tipoGrado to set
     */
    public void setTipoGrado(TipoGrado tipoGrado) {
        this.tipoGrado = tipoGrado;
    }
}
