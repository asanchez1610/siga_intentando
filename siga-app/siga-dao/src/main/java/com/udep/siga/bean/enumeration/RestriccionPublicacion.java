package com.udep.siga.bean.enumeration;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.util.JsonPublicacionSerializer;

/**
 *
 * @author Administrador
 */
@JsonSerialize(using = JsonPublicacionSerializer.class)
public enum RestriccionPublicacion {
    
    EMBARGO_COMERCIAL(2, "embargo comercial"),
    EMBARGO_PERSONAL(3, "embargo personal");
    
    private final int id;
    private final String nombre;

    RestriccionPublicacion(int id, String nombre) {
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
    
    public static RestriccionPublicacion parse(int id) {
        RestriccionPublicacion restriccionPublicacion = null; // Default
        for (RestriccionPublicacion item : RestriccionPublicacion.values()) {
            if (item.getId() == id) {
                restriccionPublicacion = item;
                break;
            }
        }
        return restriccionPublicacion;
    }
}
