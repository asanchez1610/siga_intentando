package com.udep.siga.bean.enumeration;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.util.JsonEstadoPagoCuotaSerializer;

/**
 *
 * @author Administrador
 */
@JsonSerialize(using = JsonEstadoPagoCuotaSerializer.class)
public enum EstadoPagoCuota {

    PENDIENTE(1, "Pendiente"),
    CANCELADO(2, "Cancelado"),
    NO_PENDIENTE(3, "No Pendiente");
    private final int id;
    private final String nombre;

    EstadoPagoCuota(int id, String nombre) {
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

    public static EstadoPagoCuota parse(int id) {
        EstadoPagoCuota estadoPagoCuota = null; // Default
        for (EstadoPagoCuota item : EstadoPagoCuota.values()) {
            if (item.getId() == id) {
                estadoPagoCuota = item;
                break;
            }
        }
        return estadoPagoCuota;
    }
}
