package com.udep.siga.bean;

import com.udep.siga.bean.enumeration.TipoTesis;
import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class Tesis implements Serializable{

    private int id;
    private TipoTesis tipoTesis;
    private String asesor;
    private String gradoObtenido;
    private String universidad;
    private String fechaInicio;
    private String fechaFin;
    private String fechaSustentacion;
    private String isbn;
    private int paginas;
    private InvestigacionGenerica investigacionGenerica;
    
    //utiles
    private String anio;

    public Tesis() {
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
     * @return the tipoTesis
     */
    public TipoTesis getTipoTesis() {
        return tipoTesis;
    }

    /**
     * @param tipoTesis the tipoTesis to set
     */
    public void setTipoTesis(TipoTesis tipoTesis) {
        this.tipoTesis = tipoTesis;
    }

    /**
     * @return the asesor
     */
    public String getAsesor() {
        return asesor;
    }

    /**
     * @param asesor the asesor to set
     */
    public void setAsesor(String asesor) {
        this.asesor = asesor;
    }

    /**
     * @return the gradoObtenido
     */
    public String getGradoObtenido() {
        return gradoObtenido;
    }

    /**
     * @param gradoObtenido the gradoObtenido to set
     */
    public void setGradoObtenido(String gradoObtenido) {
        this.gradoObtenido = gradoObtenido;
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
     * @return the fechaInicio
     */
    public String getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public String getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the fechaSustentacion
     */
    public String getFechaSustentacion() {
        return fechaSustentacion;
    }

    /**
     * @param fechaSustentacion the fechaSustentacion to set
     */
    public void setFechaSustentacion(String fechaSustentacion) {
        this.fechaSustentacion = fechaSustentacion;
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
     * @return the paginas
     */
    public int getPaginas() {
        return paginas;
    }

    /**
     * @param paginas the paginas to set
     */
    public void setPaginas(int paginas) {
        this.paginas = paginas;
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
}
