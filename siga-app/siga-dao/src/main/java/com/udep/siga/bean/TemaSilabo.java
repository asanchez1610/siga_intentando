package com.udep.siga.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class TemaSilabo implements Serializable {

    private int id;
    private String numero;
    private String tema;
    private String semana;
    private float horasTeoricas;
    private float horasPracticas;
    /*
     * utiles
     */
    private List<Date> fechasSesion;

    public TemaSilabo() {
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
     * @return the numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * @return the tema
     */
    public String getTema() {
        return tema;
    }

    /**
     * @param tema the tema to set
     */
    public void setTema(String tema) {
        this.tema = tema;
    }

    /**
     * @return the semana
     */
    public String getSemana() {
        return semana;
    }

    /**
     * @param semana the semana to set
     */
    public void setSemana(String semana) {
        this.semana = semana;
    }

    /**
     * @return the horasTeoricas
     */
    public float getHorasTeoricas() {
        return horasTeoricas;
    }

    /**
     * @param horasTeoricas the horasTeoricas to set
     */
    public void setHorasTeoricas(float horasTeoricas) {
        this.horasTeoricas = horasTeoricas;
    }

    /**
     * @return the horasPracticas
     */
    public float getHorasPracticas() {
        return horasPracticas;
    }

    /**
     * @param horasPracticas the horasPracticas to set
     */
    public void setHorasPracticas(float horasPracticas) {
        this.horasPracticas = horasPracticas;
    }

    /**
     * @return the fechasSesion
     */
    public List<Date> getFechasSesion() {
        return fechasSesion;
    }

    /**
     * @param fechasSesion the fechasSesion to set
     */
    public void setFechasSesion(List<Date> fechasSesion) {
        this.fechasSesion = fechasSesion;
    }
}
