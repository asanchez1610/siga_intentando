package com.udep.siga.bean.enumeration;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.util.JsonTipoEmailSerializer;

/**
 *
 * @author Wilfredo Atoche
 */
@JsonSerialize(using = JsonTipoEmailSerializer.class)
public enum TipoEmail {
    OFICIAL(1,  "Correo UDEP"),
    PERSONAL(2, "Correo Personal");
    
    private final int id;
    private final String nombre;
    
    TipoEmail(int id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }
    
    public static TipoEmail parse(int id) {
        TipoEmail tipoEmail = null; // Default
        for (TipoEmail item : TipoEmail.values()) {
            if (item.getId() == id) {
                tipoEmail = item;
                break;
            }
        }
        return tipoEmail;
    }
}
