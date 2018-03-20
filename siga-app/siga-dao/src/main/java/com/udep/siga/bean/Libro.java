package com.udep.siga.bean;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class Libro implements Serializable{

    private int id;
    private boolean colectivo;
    private InvestigacionGenerica investigacionGenerica;
    private String coordinador;
    private String colaboradores;
    private String serieColeccion;
    private String editorial;
    private String isbn;
    private String edicion;
    private int paginas;
    private String fechaPublica;
    private String ciudad;
    private String pais;
    private String otrasVersiones;
    private List<Capitulo> capitulos;

    public Libro() {
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
     * @return the colectivo
     */
    public boolean isColectivo() {
        return colectivo;
    }

    /**
     * @param colectivo the colectivo to set
     */
    public void setColectivo(boolean colectivo) {
        this.colectivo = colectivo;
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
     * @return the coordinador
     */
    public String getCoordinador() {
        if (coordinador == null) {
            return "";
        }
        return coordinador;
    }

    /**
     * @param coordinador the coordinador to set
     */
    public void setCoordinador(String coordinador) {
        this.coordinador = coordinador;
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
     * @return the serieColeccion
     */
    public String getSerieColeccion() {
        return serieColeccion;
    }

    /**
     * @param serieColeccion the serieColeccion to set
     */
    public void setSerieColeccion(String serieColeccion) {
        this.serieColeccion = serieColeccion;
    }

    /**
     * @return the editorial
     */
    public String getEditorial() {
        return editorial;
    }

    /**
     * @param editorial the editorial to set
     */
    public void setEditorial(String editorial) {
        this.editorial = editorial;
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
     * @return the fechaPublica
     */
    public String getFechaPublica() {
        return fechaPublica;
    }

    /**
     * @param fechaPublica the fechaPublica to set
     */
    public void setFechaPublica(String fechaPublica) {
        this.fechaPublica = fechaPublica;
    }

    /**
     * @return the ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * @param ciudad the ciudad to set
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * @return the pais
     */
    public String getPais() {
        return pais;
    }

    /**
     * @param pais the pais to set
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * @return the otrasVersiones
     */
    public String getOtrasVersiones() {
        return otrasVersiones;
    }

    /**
     * @param otrasVersiones the otrasVersiones to set
     */
    public void setOtrasVersiones(String otrasVersiones) {
        this.otrasVersiones = otrasVersiones;
    }

    /**
     * @return the capitulos
     */
    public List<Capitulo> getCapitulos() {
        return capitulos;
    }

    /**
     * @param capitulos the capitulos to set
     */
    public void setCapitulos(List<Capitulo> capitulos) {
        this.capitulos = capitulos;
    }
}
