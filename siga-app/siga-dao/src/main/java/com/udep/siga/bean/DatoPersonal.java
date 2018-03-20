package com.udep.siga.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.util.JsonDateSerializer;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Wilfredo Atoche
 */
public class DatoPersonal implements Serializable {

    private Date fechaNacimiento;
    private Distrito distrito;
    private String direccion;
    private String telefono;
    private String telefonoMovil;
    private String telefonoEmergencia;
    private String contactoEmergencia;
    private Distrito distritoPension;
    private String direccionPension;
    private String telefonoPension;
    private String googleScholar;
    private String blog;
    private String paginaPersonal;
    private String twitter;

    public DatoPersonal() {
    }

    /**
     * @return the fechaNacimiento
     */
    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * @param fechaNacimiento the fechaNacimiento to set
     */
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * @return the distrito
     */
    public Distrito getDistrito() {
        return distrito;
    }

    /**
     * @param distrito the distrito to set
     */
    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        if (this.direccion == null) {
            return "";
        }
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        if (this.telefono == null) {
            return "";
        }
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the telefonoEmergencia
     */
    public String getTelefonoEmergencia() {
        if (this.telefonoEmergencia == null) {
            return "";
        }
        return telefonoEmergencia;
    }

    /**
     * @param telefonoEmergencia the telefonoEmergencia to set
     */
    public void setTelefonoEmergencia(String telefonoEmergencia) {
        this.telefonoEmergencia = telefonoEmergencia;
    }

    /**
     * @return the contactoEmergencia
     */
    public String getContactoEmergencia() {
        if (this.contactoEmergencia == null) {
            return "";
        }
        return contactoEmergencia;
    }

    /**
     * @param contactoEmergencia the contactoEmergencia to set
     */
    public void setContactoEmergencia(String contactoEmergencia) {
        this.contactoEmergencia = contactoEmergencia;
    }

    /**
     * @return the distritoPension
     */
    public Distrito getDistritoPension() {
        return distritoPension;
    }

    /**
     * @param distritoPension the distritoPension to set
     */
    public void setDistritoPension(Distrito distritoPension) {
        this.distritoPension = distritoPension;
    }

    /**
     * @return the direccionPension
     */
    public String getDireccionPension() {
        if (this.direccionPension == null) {
            return "";
        }
        return direccionPension;
    }

    /**
     * @param direccionPension the direccionPension to set
     */
    public void setDireccionPension(String direccionPension) {
        this.direccionPension = direccionPension;
    }

    /**
     * @return the telefonoPension
     */
    public String getTelefonoPension() {
        if (this.telefonoPension == null) {
            return "";
        }
        return telefonoPension;
    }

    /**
     * @param telefonoPension the telefonoPension to set
     */
    public void setTelefonoPension(String telefonoPension) {
        this.telefonoPension = telefonoPension;
    }

    /**
     * @return the telefonoMovil
     */
    public String getTelefonoMovil() {
        if (this.telefonoMovil == null) {
            return "";
        }
        return telefonoMovil;
    }

    /**
     * @param telefonoMovil the telefonoMovil to set
     */
    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    /**
     * @return the googleScholar
     */
    public String getGoogleScholar() {
        return googleScholar;
    }

    /**
     * @param googleScholar the googleScholar to set
     */
    public void setGoogleScholar(String googleScholar) {
        this.googleScholar = googleScholar;
    }

    /**
     * @return the blog
     */
    public String getBlog() {
        return blog;
    }

    /**
     * @param blog the blog to set
     */
    public void setBlog(String blog) {
        this.blog = blog;
    }

    /**
     * @return the paginaPersonal
     */
    public String getPaginaPersonal() {
        return paginaPersonal;
    }

    /**
     * @param paginaPersonal the paginaPersonal to set
     */
    public void setPaginaPersonal(String paginaPersonal) {
        this.paginaPersonal = paginaPersonal;
    }

    /**
     * @return the twitter
     */
    public String getTwitter() {
        return twitter;
    }

    /**
     * @param twitter the twitter to set
     */
    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }
}
