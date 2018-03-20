package com.udep.siga.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class Destacado implements Serializable{

    private String html;
    private String enlace;
    private String verEn;

    public Destacado() {
    }

    /**
     * @return the html
     */
    public String getHtml() {
        return html;
    }

    /**
     * @param html the html to set
     */
    public void setHtml(String html) {
        this.html = html;
    }

    /**
     * @return the enlace
     */
    public String getEnlace() {
        if (this.enlace == null) {
            return "";
        }
        return enlace;
    }

    /**
     * @param enlace the enlace to set
     */
    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    /**
     * @return the verEn
     */
    public String getVerEn() {
        if (this.verEn == null) {
            return "";
        }
        return verEn;
    }

    /**
     * @param verEn the verEn to set
     */
    public void setVerEn(String verEn) {
        this.verEn = verEn;
    }
}
