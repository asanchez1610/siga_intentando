package com.udep.siga.bean.enumeration;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.util.JsonTipoGradoSerializer;

/**
 *
 * @author Administrador
 */
@JsonSerialize(using = JsonTipoGradoSerializer.class)
public enum TipoGrado {

    LICENCIADO(1, "Licenciado"),
    MASTER(2, "Master"),
    DOCTOR(3, "doctor");
    private final int id;
    private final String nombre;

    TipoGrado(int id, String nombre) {
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
    
    public static TipoGrado parse(int id) {
        TipoGrado tipoGrado = null; // Default
        for (TipoGrado item : TipoGrado.values()) {
            if (item.getId() == id) {
                tipoGrado = item;
                break;
            }
        }
        return tipoGrado;
    }
}
