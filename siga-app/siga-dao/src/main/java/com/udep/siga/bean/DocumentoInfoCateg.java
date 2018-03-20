package com.udep.siga.bean;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Wilfredo Atoche
 */
public class DocumentoInfoCateg implements Serializable{
    private int id;
    private String nombre;
    private List<DocumentoInfo> documentoInfoList;
    
    public DocumentoInfoCateg(){
        
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
     * @return the documentoInfoList
     */
    public List<DocumentoInfo> getDocumentoInfoList() {
        return documentoInfoList;
    }

    /**
     * @param documentoInfoList the documentoInfoList to set
     */
    public void setDocumentoInfoList(List<DocumentoInfo> documentoInfoList) {
        this.documentoInfoList = documentoInfoList;
    }
}
