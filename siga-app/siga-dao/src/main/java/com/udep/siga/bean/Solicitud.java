package com.udep.siga.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.enumeration.Campus;
import com.udep.siga.bean.enumeration.EstadoDocumentoOficial;
import com.udep.siga.bean.enumeration.EstadoSolicitud;
import com.udep.siga.bean.enumeration.Idioma;
import com.udep.siga.bean.util.JsonDateSerializer;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class Solicitud implements Serializable{

    private int id;
    private Date fecha;
    private EstadoSolicitud estadoSolicitud;
    private TipoSolicitud tipoSolicitud;
    private boolean aprobada;
    private String obsAlumno;
    private String respuesta;
    private Date fechaConfirmacion;
    //Docs Oficiales
    private EstadoDocumentoOficial estadoDocOficial;
    private boolean voucher;
    private String nroSolicitud;
    private Idioma idioma;
    private Campus campusEntrega;
    private String codSeguridad; 
    private Alumno alumno;
    private Edicionestudio edicionestudio;
    private String urlVoucher;
    
    /*
     * Utiles
     */
    private String idRandom;

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
    @JsonSerialize(using = JsonDateSerializer.class)
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
     * @return the estadoSolicitud
     */
    public EstadoSolicitud getEstadoSolicitud() {
        return estadoSolicitud;
    }

    /**
     * @param estadoSolicitud the estadoSolicitud to set
     */
    public void setEstadoSolicitud(EstadoSolicitud estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    /**
     * @return the tipoSolicitud
     */
    public TipoSolicitud getTipoSolicitud() {
        return tipoSolicitud;
    }

    /**
     * @param tipoSolicitud the tipoSolicitud to set
     */
    public void setTipoSolicitud(TipoSolicitud tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    /**
     * @return the aprobada
     */
    public boolean isAprobada() {
        return aprobada;
    }

    /**
     * @param aprobada the aprobada to set
     */
    public void setAprobada(boolean aprobada) {
        this.aprobada = aprobada;
    }

    /**
     * @return the obsAlumno
     */
    public String getObsAlumno() {
        return obsAlumno;
    }

    /**
     * @param obsAlumno the obsAlumno to set
     */
    public void setObsAlumno(String obsAlumno) {
        this.obsAlumno = obsAlumno;
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
     * @return the fechaConfirmacion
     */
    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getFechaConfirmacion() {
        return fechaConfirmacion;
    }

    /**
     * @param fechaConfirmacion the fechaConfirmacion to set
     */
    public void setFechaConfirmacion(Date fechaConfirmacion) {
        this.fechaConfirmacion = fechaConfirmacion;
    }

    /**
     * @return the estadoDocOficial
     */
    public EstadoDocumentoOficial getEstadoDocOficial() {
        return estadoDocOficial;
    }

    /**
     * @param estadoDocOficial the estadoDocOficial to set
     */
    public void setEstadoDocOficial(EstadoDocumentoOficial estadoDocOficial) {
        this.estadoDocOficial = estadoDocOficial;
    }

    /**
     * @return the voucher
     */
    public boolean isVoucher() {
        return voucher;
    }

    /**
     * @param voucher the voucher to set
     */
    public void setVoucher(boolean voucher) {
        this.voucher = voucher;
    }

    /**
     * @return the nroSolicitud
     */
    public String getNroSolicitud() {
        return nroSolicitud;
    }

    /**
     * @param nroSolicitud the nroSolicitud to set
     */
    public void setNroSolicitud(String nroSolicitud) {
        this.nroSolicitud = nroSolicitud;
    }

    /**
     * @return the idioma
     */
    public Idioma getIdioma() {
        return idioma;
    }

    /**
     * @param idioma the idioma to set
     */
    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    /**
     * @return the campusEntrega
     */
    public Campus getCampusEntrega() {
        return campusEntrega;
    }

    /**
     * @param campusEntrega the campusEntrega to set
     */
    public void setCampusEntrega(Campus campusEntrega) {
        this.campusEntrega = campusEntrega;
    }

    /**
     * @return the alumno
     */
    public Alumno getAlumno() {
        return alumno;
    }

    /**
     * @param alumno the alumno to set
     */
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    /**
     * @return the edicionestudio
     */
    public Edicionestudio getEdicionestudio() {
        return edicionestudio;
    }

    /**
     * @param edicionestudio the edicionestudio to set
     */
    public void setEdicionestudio(Edicionestudio edicionestudio) {
        this.edicionestudio = edicionestudio;
    }

    /**
     * @return the codSeguridad
     */
    public String getCodSeguridad() {
        return codSeguridad;
    }

    /**
     * @param codSeguridad the codSeguridad to set
     */
    public void setCodSeguridad(String codSeguridad) {
        this.codSeguridad = codSeguridad;
    }

    /**
     * @return the urlVoucher
     */
    public String getUrlVoucher() {
        return urlVoucher;
    }

    /**
     * @param urlVoucher the urlVoucher to set
     */
    public void setUrlVoucher(String urlVoucher) {
        this.urlVoucher = urlVoucher;
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
}
