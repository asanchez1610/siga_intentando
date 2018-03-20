package com.udep.siga.bean;

import com.udep.siga.bean.enumeration.TipoArticulo;
import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class Articulo implements Serializable{

    private int id;
    private TipoArticulo tipoArticulo;
    private String colaboradores;
    private InvestigacionGenerica investigacionGenerica;
    private String medioPublicacion;
    private String proceso;
    private String nombreMedio;
    private String indexada;
    private boolean publicadoActa;
    private String isbn;
    private String acta;
    private String issn;
    private String edicion;
    private String tomoVolumen;
    private String numeroFasciculo;
    private String inicio;
    private String fin;
    private String doi;
    private String fecha;
    private int dia;
    private int mes;
    private int anio;
    private String ciudadPublica;
    private String paisPublica;
    private String otraVersion;

    public Articulo() {
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
     * @return the tipoArticulo
     */
    public TipoArticulo getTipoArticulo() {
        return tipoArticulo;
    }

    /**
     * @param tipoArticulo the tipoArticulo to set
     */
    public void setTipoArticulo(TipoArticulo tipoArticulo) {
        this.tipoArticulo = tipoArticulo;
    }

    /**
     * @return the colaboradores
     */
    public String getColaboradores() {
        return colaboradores;
    }

    /**
     * @param colaboradores the colaboradores to set
     */
    public void setColaboradores(String colaboradores) {
        this.colaboradores = colaboradores;
    }

    /**
     * @return the investigacionGenerica
     */
    public InvestigacionGenerica getInvestigacionGenerica() {
        return investigacionGenerica;
    }

    /**
     * @param investigacionGenerica the investigacionGenerica to set
     */
    public void setInvestigacionGenerica(InvestigacionGenerica investigacionGenerica) {
        this.investigacionGenerica = investigacionGenerica;
    }

    /**
     * @return the medioPublicacion
     */
    public String getMedioPublicacion() {
        return medioPublicacion;
    }

    /**
     * @param medioPublicacion the medioPublicacion to set
     */
    public void setMedioPublicacion(String medioPublicacion) {
        this.medioPublicacion = medioPublicacion;
    }

    /**
     * @return the nombreMedio
     */
    public String getNombreMedio() {
        return nombreMedio;
    }

    /**
     * @param nombreMedio the nombreMedio to set
     */
    public void setNombreMedio(String nombreMedio) {
        this.nombreMedio = nombreMedio;
    }

    /**
     * @return the indexada
     */
    public String getIndexada() {
        return indexada;
    }

    /**
     * @param indexada the indexada to set
     */
    public void setIndexada(String indexada) {
        this.indexada = indexada;
    }

    /**
     * @return the publicadoActa
     */
    public boolean isPublicadoActa() {
        return publicadoActa;
    }

    /**
     * @param publicadoActa the publicadoActa to set
     */
    public void setPublicadoActa(boolean publicadoActa) {
        this.publicadoActa = publicadoActa;
    }

    /**
     * @return the isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @param isbn the isbn to set
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * @return the acta
     */
    public String getActa() {
        return acta;
    }

    /**
     * @param acta the acta to set
     */
    public void setActa(String acta) {
        this.acta = acta;
    }

    /**
     * @return the issn
     */
    public String getIssn() {
        return issn;
    }

    /**
     * @param issn the issn to set
     */
    public void setIssn(String issn) {
        this.issn = issn;
    }

    /**
     * @return the edicion
     */
    public String getEdicion() {
        return edicion;
    }

    /**
     * @param edicion the edicion to set
     */
    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }

    /**
     * @return the tomoVolumen
     */
    public String getTomoVolumen() {
        return tomoVolumen;
    }

    /**
     * @param tomoVolumen the tomoVolumen to set
     */
    public void setTomoVolumen(String tomoVolumen) {
        this.tomoVolumen = tomoVolumen;
    }

    /**
     * @return the numeroFasciculo
     */
    public String getNumeroFasciculo() {
        return numeroFasciculo;
    }

    /**
     * @param numeroFasciculo the numeroFasciculo to set
     */
    public void setNumeroFasciculo(String numeroFasciculo) {
        this.numeroFasciculo = numeroFasciculo;
    }

    /**
     * @return the inicio
     */
    public String getInicio() {
        return inicio;
    }

    /**
     * @param inicio the inicio to set
     */
    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    /**
     * @return the fin
     */
    public String getFin() {
        return fin;
    }

    /**
     * @param fin the fin to set
     */
    public void setFin(String fin) {
        this.fin = fin;
    }

    /**
     * @return the doi
     */
    public String getDoi() {
        return doi;
    }

    /**
     * @param doi the doi to set
     */
    public void setDoi(String doi) {
        this.doi = doi;
    }

    /**
     * @return the dia
     */
    public int getDia() {
        return dia;
    }

    /**
     * @param dia the dia to set
     */
    public void setDia(int dia) {
        this.dia = dia;
    }

    /**
     * @return the mes
     */
    public int getMes() {
        return mes;
    }

    /**
     * @param mes the mes to set
     */
    public void setMes(int mes) {
        this.mes = mes;
    }

    /**
     * @return the anio
     */
    public int getAnio() {
        return anio;
    }

    /**
     * @param anio the anio to set
     */
    public void setAnio(int anio) {
        this.anio = anio;
    }

    /**
     * @return the ciudadPublica
     */
    public String getCiudadPublica() {
        return ciudadPublica;
    }

    /**
     * @param ciudadPublica the ciudadPublica to set
     */
    public void setCiudadPublica(String ciudadPublica) {
        this.ciudadPublica = ciudadPublica;
    }

    /**
     * @return the paisPublica
     */
    public String getPaisPublica() {
        return paisPublica;
    }

    /**
     * @param paisPublica the paisPublica to set
     */
    public void setPaisPublica(String paisPublica) {
        this.paisPublica = paisPublica;
    }

    /**
     * @return the otraVersion
     */
    public String getOtraVersion() {
        return otraVersion;
    }

    /**
     * @param otraVersion the otraVersion to set
     */
    public void setOtraVersion(String otraVersion) {
        this.otraVersion = otraVersion;
    }

    /**
     * @return the proceso
     */
    public String getProceso() {
        return proceso;
    }

    /**
     * @param proceso the proceso to set
     */
    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
