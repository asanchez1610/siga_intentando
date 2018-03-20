package com.udep.siga.bean;

import com.udep.siga.bean.enumeration.EstadoConsulta;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class Consulta implements Serializable{

    private int id;
    private Date fecha;
    private EstadoConsulta estadoConsulta;
    private CategoriaConsulta categoria;
    private boolean positivo;
    private String consulta;
    private String respuesta;
    private Date fechaRespuesta;

    public Consulta() {
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
     * @return the estadoConsulta
     */
    public EstadoConsulta getEstadoConsulta() {
        return estadoConsulta;
    }

    /**
     * @param estadoConsulta the estadoConsulta to set
     */
    public void setEstadoConsulta(EstadoConsulta estadoConsulta) {
        this.estadoConsulta = estadoConsulta;
    }

    /**
     * @return the categoria
     */
    public CategoriaConsulta getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(CategoriaConsulta categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the positivo
     */
    public boolean isPositivo() {
        return positivo;
    }

    /**
     * @param positivo the positivo to set
     */
    public void setPositivo(boolean positivo) {
        this.positivo = positivo;
    }

    /**
     * @return the consulta
     */
    public String getConsulta() {
        return consulta;
    }

    /**
     * @param consulta the consulta to set
     */
    public void setConsulta(String consulta) {
        this.consulta = consulta;
    }

    /**
     * @return the respuesta
     */
    public String getRespuesta() {
        return respuesta;
    }

    /**
     * @param respuesta the respuesta to set
     */
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    /**
     * @return the fechaRespuesta
     */
    public Date getFechaRespuesta() {
        return fechaRespuesta;
    }

    /**
     * @param fechaRespuesta the fechaRespuesta to set
     */
    public void setFechaRespuesta(Date fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }
}
