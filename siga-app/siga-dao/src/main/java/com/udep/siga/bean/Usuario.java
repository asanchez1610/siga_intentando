package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Wilfredo Atoche
 */
public class Usuario implements Serializable{
    private int id;
    private String login;
    private String password;
    private boolean activo;    
    
    /* Utiles */
    private boolean pregrado;
    private boolean posgrado;
    private boolean exEgPregrado;
    private boolean exEgPosgrado;
    
    public Usuario(){
        
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
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the activo
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }   

    /**
     * @return the pregrado
     */
    public boolean isPregrado() {
        return pregrado;
    }

    /**
     * @param pregrado the pregrado to set
     */
    public void setPregrado(boolean pregrado) {
        this.pregrado = pregrado;
    }

    /**
     * @return the posgrado
     */
    public boolean isPosgrado() {
        return posgrado;
    }

    /**
     * @param posgrado the posgrado to set
     */
    public void setPosgrado(boolean posgrado) {
        this.posgrado = posgrado;
    }

    /**
     * @return the exEgPregrado
     */
    public boolean isExEgPregrado() {
        return exEgPregrado;
    }

    /**
     * @param exEgPregrado the exEgPregrado to set
     */
    public void setExEgPregrado(boolean exEgPregrado) {
        this.exEgPregrado = exEgPregrado;
    }

    /**
     * @return the exEgPosgrado
     */
    public boolean isExEgPosgrado() {
        return exEgPosgrado;
    }

    /**
     * @param exEgPosgrado the exEgPosgrado to set
     */
    public void setExEgPosgrado(boolean exEgPosgrado) {
        this.exEgPosgrado = exEgPosgrado;
    }
}
