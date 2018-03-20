package com.udep.siga.bean;

import com.udep.siga.bean.enumeration.TipoEmail;
import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class Email implements Serializable{
    private TipoEmail tipoEmail;    
    private String email;

    public Email(){    
    }
        

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the tipoEmail
     */
    public TipoEmail getTipoEmail() {
        return tipoEmail;
    }

    /**
     * @param tipoEmail the tipoEmail to set
     */
    public void setTipoEmail(TipoEmail tipoEmail) {
        this.tipoEmail = tipoEmail;
    }
}
