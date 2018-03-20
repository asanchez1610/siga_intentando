package com.udep.siga.bean;

import com.udep.siga.bean.enumeration.EstadoInvestigacion;
import com.udep.siga.bean.enumeration.RestriccionPublicacion;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class InvestigacionGenerica implements Serializable{

    private int id;
    private String titulo;
    private String tituloAlt;
    private EstadoInvestigacion estado;
    private LineaInvestigacion campoInvestigacion;
    private LineaInvestigacion lineaInvestigacion;
    private Estudio estudio;
    private Departamento departamento;
    private AreaInvestigacion areaInvestigacion;
    private String autor;
    private String depositoLegal;
    private String resumen;
    private String englishResumen;
    private String palabraClave;
    private RestriccionPublicacion restriccionPublicacion;
    private Date VencEmbargoComerc;
    private boolean embargoPersonal;
    private String titularDerechos;
    private String derechos;
    private String urlLicenciaDerecho;
    private CentroAcademico centroAcademico;
    //Utiles
    private Evento evento;
    private List<String> restriciones;
    private List<DocumentosInvestigacion> documentos;

    public InvestigacionGenerica() {
    }

    public InvestigacionGenerica(int id) {
        this.id = id;
    }

    public InvestigacionGenerica(int id, String titulo) {
        this.id = id;
        this.titulo = titulo;
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
     * @return the estado
     */
    public EstadoInvestigacion getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(EstadoInvestigacion estado) {
        this.estado = estado;
    }

    /**
     * @return the campoInvestigacion
     */
    public LineaInvestigacion getCampoInvestigacion() {
        return campoInvestigacion;
    }

    /**
     * @param campoInvestigacion the campoInvestigacion to set
     */
    public void setCampoInvestigacion(LineaInvestigacion campoInvestigacion) {
        this.campoInvestigacion = campoInvestigacion;
    }

    /**
     * @return the lineaInvestigacion
     */
    public LineaInvestigacion getLineaInvestigacion() {
        return lineaInvestigacion;
    }

    /**
     * @param lineaInvestigacion the lineaInvestigacion to set
     */
    public void setLineaInvestigacion(LineaInvestigacion lineaInvestigacion) {
        this.lineaInvestigacion = lineaInvestigacion;
    }

    /**
     * @return the estudio
     */
    public Estudio getEstudio() {
        return estudio;
    }

    /**
     * @param estudio the estudio to set
     */
    public void setEstudio(Estudio estudio) {
        this.estudio = estudio;
    }

    /**
     * @return the departamento
     */
    public Departamento getDepartamento() {
        return departamento;
    }

    /**
     * @param departamento the departamento to set
     */
    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    /**
     * @return the areaInvestigacion
     */
    public AreaInvestigacion getAreaInvestigacion() {
        return areaInvestigacion;
    }

    /**
     * @param areaInvestigacion the areaInvestigacion to set
     */
    public void setAreaInvestigacion(AreaInvestigacion areaInvestigacion) {
        this.areaInvestigacion = areaInvestigacion;
    }

    /**
     * @return the autor
     */
    public String getAutor() {
        return autor;
    }

    /**
     * @param autor the autor to set
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     * @return the depositoLegal
     */
    public String getDepositoLegal() {
        if (this.depositoLegal == null) {
            return "";
        }
        return depositoLegal;
    }

    /**
     * @param depositoLegal the depositoLegal to set
     */
    public void setDepositoLegal(String depositoLegal) {
        this.depositoLegal = depositoLegal;
    }

    /**
     * @return the resumen
     */
    public String getResumen() {
        if (this.resumen == null) {
            return "";
        } else {
            if (this.resumen.equals("Ninguno") || this.resumen.equals(".")) {
                return "";
            }
        }
        return resumen;
    }

    /**
     * @param resumen the resumen to set
     */
    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    /**
     * @return the englishResumen
     */
    public String getEnglishResumen() {
        if (this.englishResumen == null) {
            return "";
        }
        return englishResumen;
    }

    /**
     * @param englishResumen the englishResumen to set
     */
    public void setEnglishResumen(String englishResumen) {
        this.englishResumen = englishResumen;
    }

    /**
     * @return the palabraClave
     */
    public String getPalabraClave() {
        if (this.palabraClave == null) {
            return "";
        }
        return palabraClave;
    }

    /**
     * @param palabraClave the palabraClave to set
     */
    public void setPalabraClave(String palabraClave) {
        this.palabraClave = palabraClave;
    }

    /**
     * @return the evento
     */
    public Evento getEvento() {
        return evento;
    }

    /**
     * @param evento the evento to set
     */
    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    /**
     * @return the restriccionPublicacion
     */
    public RestriccionPublicacion getRestriccionPublicacion() {
        return restriccionPublicacion;
    }

    /**
     * @param restriccionPublicacion the restriccionPublicacion to set
     */
    public void setRestriccionPublicacion(RestriccionPublicacion restriccionPublicacion) {
        this.restriccionPublicacion = restriccionPublicacion;
    }

    /**
     * @return the VencEmbargoComerc
     */
    public Date getVencEmbargoComerc() {
        return VencEmbargoComerc;
    }

    /**
     * @param VencEmbargoComerc the VencEmbargoComerc to set
     */
    public void setVencEmbargoComerc(Date VencEmbargoComerc) {
        this.VencEmbargoComerc = VencEmbargoComerc;
    }

    /**
     * @return the embargoPersonal
     */
    public boolean isEmbargoPersonal() {
        return embargoPersonal;
    }

    /**
     * @param embargoPersonal the embargoPersonal to set
     */
    public void setEmbargoPersonal(boolean embargoPersonal) {
        this.embargoPersonal = embargoPersonal;
    }

    /**
     * @return the titularDerechos
     */
    public String getTitularDerechos() {
        if (this.titularDerechos == null) {
            return "";
        }
        return titularDerechos;
    }

    /**
     * @param titularDerechos the titularDerechos to set
     */
    public void setTitularDerechos(String titularDerechos) {
        this.titularDerechos = titularDerechos;
    }

    /**
     * @return the derechos
     */
    public String getDerechos() {
        if (this.derechos == null) {
            return "";
        }
        return derechos;
    }

    /**
     * @param derechos the derechos to set
     */
    public void setDerechos(String derechos) {
        this.derechos = derechos;
    }

    /**
     * @return the urlLicenciaDerecho
     */
    public String getUrlLicenciaDerecho() {
        if (this.urlLicenciaDerecho == null) {
            return "";
        }
        return urlLicenciaDerecho;
    }

    /**
     * @param urlLicenciaDerecho the urlLicenciaDerecho to set
     */
    public void setUrlLicenciaDerecho(String urlLicenciaDerecho) {
        this.urlLicenciaDerecho = urlLicenciaDerecho;
    }

    /**
     * @return the restriciones
     */
    public List<String> getRestriciones() {
        return restriciones;
    }

    /**
     * @param restriciones the restriciones to set
     */
    public void setRestriciones(List<String> restriciones) {
        this.restriciones = restriciones;
    }

    /**
     * @return the centroAcademico
     */
    public CentroAcademico getCentroAcademico() {
        return centroAcademico;
    }

    /**
     * @param centroAcademico the centroAcademico to set
     */
    public void setCentroAcademico(CentroAcademico centroAcademico) {
        this.centroAcademico = centroAcademico;
    }

    /**
     * @return the tituloAlt
     */
    public String getTituloAlt() {
        return tituloAlt;
    }

    /**
     * @param tituloAlt the tituloAlt to set
     */
    public void setTituloAlt(String tituloAlt) {
        this.tituloAlt = tituloAlt;
    }

    /**
     * @return the documentos
     */
    public List<DocumentosInvestigacion> getDocumentos() {
        return documentos;
    }

    /**
     * @param documentos the documentos to set
     */
    public void setDocumentos(List<DocumentosInvestigacion> documentos) {
        this.documentos = documentos;
    }
}
