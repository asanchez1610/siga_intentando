package com.udep.siga.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.enumeration.EstadoEvaluacion;
import com.udep.siga.bean.util.JsonDateSerializer;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author Wilfredo Atoche
 */
public class Evaluacion implements Serializable{
    private int id;
    private EstadoEvaluacion estadoEvaluacion;
    private TipoEvaluacion tipoEvaluacion;
    private AsignaturaDictada asignaturaDictada;
    private String nombre;
    private String descripcion;
    private Date fechaEvaluacion; 
    private Date fechaRecojo;
    private Date fechaCalificacion; 
    private Date fechaEntregaSec;
    private Date fechaEntrega;
    private Date fechaRecojoRec;
    private Date fechaCalificacionRec;
    private Date fechaEntregaSecRec; 
    private Date fechaEntregaRec;
    private boolean informativa;
    private boolean anulable;
    private int peso;
    private boolean confirmada;
    private boolean cancelada;
    private Date fechaEntregaRecSec;
    
    /* Utiles */
    private EvaluacionAlumno evaluacionAlumno;
    private String idRandom;
    private Map<String, Object> temaEvaluacionList;
    private String notifyTooltip;
    
    public Evaluacion(){
        
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
     * @return the estadoEvaluacion
     */
    public EstadoEvaluacion getEstadoEvaluacion() {
        return estadoEvaluacion;
    }

    /**
     * @param estadoEvaluacion the estadoEvaluacion to set
     */
    public void setEstadoEvaluacion(EstadoEvaluacion estadoEvaluacion) {
        this.estadoEvaluacion = estadoEvaluacion;
    }

    /**
     * @return the tipoEvaluacion
     */
    public TipoEvaluacion getTipoEvaluacion() {
        return tipoEvaluacion;
    }

    /**
     * @param tipoEvaluacion the tipoEvaluacion to set
     */
    public void setTipoEvaluacion(TipoEvaluacion tipoEvaluacion) {
        this.tipoEvaluacion = tipoEvaluacion;
    }

    /**
     * @return the asignaturaDictada
     */
    public AsignaturaDictada getAsignaturaDictada() {
        return asignaturaDictada;
    }

    /**
     * @param asignaturaDictada the asignaturaDictada to set
     */
    public void setAsignaturaDictada(AsignaturaDictada asignaturaDictada) {
        this.asignaturaDictada = asignaturaDictada;
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
     * @return the fechaEvaluacion
     */
    @JsonSerialize(using=JsonDateSerializer.class)
    public Date getFechaEvaluacion() {
        return fechaEvaluacion;
    }

    /**
     * @param fechaEvaluacion the fechaEvaluacion to set
     */
    public void setFechaEvaluacion(Date fechaEvaluacion) {
        this.fechaEvaluacion = fechaEvaluacion;
    }

    /**
     * @return the fechaRecojo
     */
    @JsonSerialize(using=JsonDateSerializer.class)
    public Date getFechaRecojo() {
        return fechaRecojo;
    }

    /**
     * @param fechaRecojo the fechaRecojo to set
     */
    public void setFechaRecojo(Date fechaRecojo) {
        this.fechaRecojo = fechaRecojo;
    }

    /**
     * @return the fechaCalificacion
     */
    @JsonSerialize(using=JsonDateSerializer.class)
    public Date getFechaCalificacion() {
        return fechaCalificacion;
    }

    /**
     * @param fechaCalificacion the fechaCalificacion to set
     */
    public void setFechaCalificacion(Date fechaCalificacion) {
        this.fechaCalificacion = fechaCalificacion;
    }

    /**
     * @return the fechaEntregaSec
     */
    @JsonSerialize(using=JsonDateSerializer.class)
    public Date getFechaEntregaSec() {
        return fechaEntregaSec;
    }

    /**
     * @param fechaEntregaSec the fechaEntregaSec to set
     */
    public void setFechaEntregaSec(Date fechaEntregaSec) {
        this.fechaEntregaSec = fechaEntregaSec;
    }

    /**
     * @return the fechaEntrega
     */
    @JsonSerialize(using=JsonDateSerializer.class)
    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    /**
     * @param fechaEntrega the fechaEntrega to set
     */
    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    /**
     * @return the fechaRecojoRec
     */
    @JsonSerialize(using=JsonDateSerializer.class)
    public Date getFechaRecojoRec() {
        return fechaRecojoRec;
    }

    /**
     * @param fechaRecojoRec the fechaRecojoRec to set
     */
    public void setFechaRecojoRec(Date fechaRecojoRec) {
        this.fechaRecojoRec = fechaRecojoRec;
    }

    /**
     * @return the fechaCalificacionRec
     */
    @JsonSerialize(using=JsonDateSerializer.class)
    public Date getFechaCalificacionRec() {
        return fechaCalificacionRec;
    }

    /**
     * @param fechaCalificacionRec the fechaCalificacionRec to set
     */
    public void setFechaCalificacionRec(Date fechaCalificacionRec) {
        this.fechaCalificacionRec = fechaCalificacionRec;
    }

    /**
     * @return the fechaEntregaSecRec
     */
    @JsonSerialize(using=JsonDateSerializer.class)
    public Date getFechaEntregaSecRec() {
        return fechaEntregaSecRec;
    }

    /**
     * @param fechaEntregaSecRec the fechaEntregaSecRec to set
     */
    public void setFechaEntregaSecRec(Date fechaEntregaSecRec) {
        this.fechaEntregaSecRec = fechaEntregaSecRec;
    }

    /**
     * @return the fechaEntregaRec
     */
    @JsonSerialize(using=JsonDateSerializer.class)
    public Date getFechaEntregaRec() {
        return fechaEntregaRec;
    }

    /**
     * @param fechaEntregaRec the fechaEntregaRec to set
     */
    public void setFechaEntregaRec(Date fechaEntregaRec) {
        this.fechaEntregaRec = fechaEntregaRec;
    }

    /**
     * @return the informativa
     */
    public boolean isInformativa() {
        return informativa;
    }

    /**
     * @param informativa the informativa to set
     */
    public void setInformativa(boolean informativa) {
        this.informativa = informativa;
    }

    /**
     * @return the anulable
     */
    public boolean isAnulable() {
        return anulable;
    }

    /**
     * @param anulable the anulable to set
     */
    public void setAnulable(boolean anulable) {
        this.anulable = anulable;
    }

    /**
     * @return the peso
     */
    public int getPeso() {
        return peso;
    }

    /**
     * @param peso the peso to set
     */
    public void setPeso(int peso) {
        this.peso = peso;
    }

    /**
     * @return the confirmada
     */
    public boolean isConfirmada() {
        return confirmada;
    }

    /**
     * @param confirmada the confirmada to set
     */
    public void setConfirmada(boolean confirmada) {
        this.confirmada = confirmada;
    }

    /**
     * @return the cancelada
     */
    public boolean isCancelada() {
        return cancelada;
    }

    /**
     * @param cancelada the cancelada to set
     */
    public void setCancelada(boolean cancelada) {
        this.cancelada = cancelada;
    }

    /**
     * @return the fechaEntregaRecSec
     */
    @JsonSerialize(using=JsonDateSerializer.class)
    public Date getFechaEntregaRecSec() {
        return fechaEntregaRecSec;
    }

    /**
     * @param fechaEntregaRecSec the fechaEntregaRecSec to set
     */
    public void setFechaEntregaRecSec(Date fechaEntregaRecSec) {
        this.fechaEntregaRecSec = fechaEntregaRecSec;
    }

    /**
     * @return the evaluacionAlumno
     */
    public EvaluacionAlumno getEvaluacionAlumno() {
        return evaluacionAlumno;
    }

    /**
     * @param evaluacionAlumno the evaluacionAlumno to set
     */
    public void setEvaluacionAlumno(EvaluacionAlumno evaluacionAlumno) {
        this.evaluacionAlumno = evaluacionAlumno;
    }

    /**
     * @return the idRandom
     */
    public String getIdRandom() {
        return idRandom;
    }

    /**
     * @param idRandom the idRandom to set
     */
    public void setIdRandom(String idRandom) {
        this.idRandom = idRandom;
    }

    /**
     * @return the temaEvaluacionList
     */
    public Map<String, Object> getTemaEvaluacionList() {
        return temaEvaluacionList;
    }

    /**
     * @param temaEvaluacionList the temaEvaluacionList to set
     */
    public void setTemaEvaluacionList(Map<String, Object> temaEvaluacionList) {
        this.temaEvaluacionList = temaEvaluacionList;
    }

    /**
     * @return the notifyTooltip
     */
    public String getNotifyTooltip() {
        return notifyTooltip;
    }

    /**
     * @param notifyTooltip the notifyTooltip to set
     */
    public void setNotifyTooltip(String notifyTooltip) {
        this.notifyTooltip = notifyTooltip;
    }
}
