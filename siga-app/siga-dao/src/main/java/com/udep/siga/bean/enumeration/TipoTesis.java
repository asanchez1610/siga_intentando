package com.udep.siga.bean.enumeration;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.util.JsonTipoTesisSerializer;

/**
 *
 * @author Administrador
 */
@JsonSerialize(using = JsonTipoTesisSerializer.class)
public enum TipoTesis {

    PREGRADO(1, "PREGRADO"),
    MAESTRIA(2, "MAESTRIA"),
    DOCTORADO(3, "DOCTORADO");
    
    private final int id;
    private final String nombre;

    TipoTesis(int id, String nombre) {
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
    
     public static TipoTesis parse(int id) {
        TipoTesis tipoTesis = null; // Default
        for (TipoTesis item : TipoTesis.values()) {
            if (item.getId() == id) {
                tipoTesis = item;
                break;
            }
        }
        return tipoTesis;
    }
}
