package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class TrabajoCV implements Serializable{

    private int id;
    private boolean hastaLaFecha;
    private int diaInicio;
    private int mesInicio;
    private int anioInicio;
    private int diaFin;
    private int mesFin;
    private int anioFin;
    private String desde;
    private String hasta;


    public TrabajoCV() {
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
     * @return the diaInicio
     */
    public int getDiaInicio() {
        return diaInicio;
    }

    /**
     * @param diaInicio the diaInicio to set
     */
    public void setDiaInicio(int diaInicio) {
        this.diaInicio = diaInicio;
    }

    /**
     * @return the mesInicio
     */
    public int getMesInicio() {
        return mesInicio;
    }

    /**
     * @param mesInicio the mesInicio to set
     */
    public void setMesInicio(int mesInicio) {
        this.mesInicio = mesInicio;
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
     * @return the diaFin
     */
    public int getDiaFin() {
        return diaFin;
    }

    /**
     * @param diaFin the diaFin to set
     */
    public void setDiaFin(int diaFin) {
        this.diaFin = diaFin;
    }

    /**
     * @return the mesFin
     */
    public int getMesFin() {
        return mesFin;
    }

    /**
     * @param mesFin the mesFin to set
     */
    public void setMesFin(int mesFin) {
        this.mesFin = mesFin;
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
     * @return the desde
     */
    public String getDesde() {
        return desde;
    }

    /**
     * @param desde the desde to set
     */
    public void setDesde(String desde) {
        this.desde = desde;
    }

    /**
     * @return the hasta
     */
    public String getHasta() {
        return hasta;
    }

    /**
     * @param hasta the hasta to set
     */
    public void setHasta(String hasta) {
        this.hasta = hasta;
    }
}
