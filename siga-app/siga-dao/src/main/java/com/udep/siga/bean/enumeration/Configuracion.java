package com.udep.siga.bean.enumeration;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.util.JsonConfiguracionSerializer;

/**
 *
 * @author Administrador
 */
@JsonSerialize(using = JsonConfiguracionSerializer.class)
public enum Configuracion {
    ASESOR_SUGERIDO_ACTIVO(1),
    ACTUALIZA_DATOS_PERSONALES(2),
    BLOQUEO_MATRICULA(3),
    LLENARENCUESTAECONOMIA(4);
    
    private final int id;
    
    Configuracion(int id) {
        this.id = id;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    public static Configuracion parse(int id) {
        Configuracion configuracion = null; // Default
        for (Configuracion item : Configuracion.values()) {
            if (item.getId() == id) {
                configuracion = item;
                break;
            }
        }
        return configuracion;
    }
}
