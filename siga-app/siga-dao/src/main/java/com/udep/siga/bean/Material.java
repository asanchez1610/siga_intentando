package com.udep.siga.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class Material implements Serializable {

    private int id;
    private String titulo;
    private String descripcion;
    private Date fecha;
    private Persona publicador;
    private String rutaArchivo;
    private String tamanio;
    private boolean toCentroCopiado;
    private String nombreArchivo;

    public Material() {
    }

    public Material(int id) {
        this.id = id;
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
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
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
     * @return the rutaArchivo
     */
    public String getRutaArchivo() {
        return rutaArchivo;
    }

    /**
     * @param rutaArchivo the rutaArchivo to set
     */
    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    /**
     * @return the publicador
     */
    public Persona getPublicador() {
        return publicador;
    }

    /**
     * @param publicador the publicador to set
     */
    public void setPublicador(Persona publicador) {
        this.publicador = publicador;
    }

    /**
     * @return the tamanio
     */
    public String getTamanio() {
        /*File archivo = new File(getRutaArchivo());

         float tam = archivo.length() / 1024;

         if (archivo.length() / 1024 > 1024) {
         tam = tam / 1024;

         int s = Math.round(tam * 100);
         tam = s / 100;

         setTamanio(tam + " Mb");
         } else {

         int s = Math.round(tam * 100);
         tam = s / 100;

         setTamanio(tam + " Kb");
         }*/
        if (this.tamanio == null) {
            this.tamanio = "0";
        }
        return tamanio + " Mb";
    }

    /**
     * @param tamanio the tamanio to set
     */
    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }

    /**
     * @return the toCentroCopiado
     */
    public boolean isToCentroCopiado() {
        return toCentroCopiado;
    }

    /**
     * @param toCentroCopiado the toCentroCopiado to set
     */
    public void setToCentroCopiado(boolean toCentroCopiado) {
        this.toCentroCopiado = toCentroCopiado;
    }

    /**
     * @return the nombreArchivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * @param nombreArchivo the nombreArchivo to set
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
}
