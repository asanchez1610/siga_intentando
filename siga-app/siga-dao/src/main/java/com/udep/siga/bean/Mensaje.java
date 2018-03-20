package com.udep.siga.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.util.JsonDateSerializer;
import com.udep.siga.bean.util.JsonDateTimeSerializer;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Lucinda Silva
 */
public class Mensaje implements Serializable{

    private int id;
    private Persona personaPara;
    private Persona personaDe;
    private String asunto;
    private String mensaje;
    private Date fechaEnvio;
    private Date fechaLeido;
    private boolean estadoLeido;
    private boolean visible;
    private int idTipoMensaje;
    private Date fechaEliminado;
    private int idTipoEnvio;
    private int correlativo;
    private PeriodoAcademico periodoAcademico;

    public Mensaje() {
    }

    public Mensaje(Persona personaPara, String asunto, String mensaje) {
        this.personaPara = personaPara;
        this.asunto = asunto;
        this.mensaje = mensaje;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the personaPara
     */
    public Persona getPersonaPara() {
        return personaPara;
    }

    /**
     * @param personaPara the personaPara to set
     */
    public void setPersonaPara(Persona personaPara) {
        this.personaPara = personaPara;
    }

    /**
     * @return the personaDe
     */
    public Persona getPersonaDe() {
        return personaDe;
    }

    /**
     * @param personaDe the personaDe to set
     */
    public void setPersonaDe(Persona personaDe) {
        this.personaDe = personaDe;
    }

    /**
     * @return the asunto
     */
    public String getAsunto() {
        return asunto;
    }

    /**
     * @param asunto the asunto to set
     */
    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the fechaEnvio
     */
    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    /**
     * @param fechaEnvio the fechaEnvio to set
     */
    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    /**
     * @return the fechaLeido
     */
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    public Date getFechaLeido() {
        return fechaLeido;
    }

    /**
     * @param fechaLeido the fechaLeido to set
     */
    public void setFechaLeido(Date fechaLeido) {
        this.fechaLeido = fechaLeido;
    }

    /**
     * @return the estadoLeido
     */
    public boolean isEstadoLeido() {
        return estadoLeido;
    }

    /**
     * @param estadoLeido the estadoLeido to set
     */
    public void setEstadoLeido(boolean estadoLeido) {
        this.estadoLeido = estadoLeido;
    }

    /**
     * @return the visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * @param visible the visible to set
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * @return the idTipoMensaje
     */
    public Integer getIdTipoMensaje() {
        return idTipoMensaje;
    }

    /**
     * @param idTipoMensaje the idTipoMensaje to set
     */
    public void setIdTipoMensaje(Integer idTipoMensaje) {
        this.idTipoMensaje = idTipoMensaje;
    }

    /**
     * @return the fechaEliminado
     */
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    public Date getFechaEliminado() {
        return fechaEliminado;
    }

    /**
     * @param fechaEliminado the fechaEliminado to set
     */
    public void setFechaEliminado(Date fechaEliminado) {
        this.fechaEliminado = fechaEliminado;
    }

    /**
     * @return the idTipoEnvio
     */
    public Integer getIdTipoEnvio() {
        return idTipoEnvio;
    }

    /**
     * @param idTipoEnvio the idTipoEnvio to set
     */
    public void setIdTipoEnvio(Integer idTipoEnvio) {
        this.idTipoEnvio = idTipoEnvio;
    }

    /**
     * @return the correlativo
     */
    public Integer getCorrelativo() {
        return correlativo;
    }

    /**
     * @param correlativo the correlativo to set
     */
    public void setCorrelativo(Integer correlativo) {
        this.correlativo = correlativo;
    }

    /**
     * @return the periodoAcademico
     */
    public PeriodoAcademico getPeriodoAcademico() {
        return periodoAcademico;
    }

    /**
     * @param periodoAcademico the periodoAcademico to set
     */
    public void setPeriodoAcademico(PeriodoAcademico periodoAcademico) {
        this.periodoAcademico = periodoAcademico;
    }
}
