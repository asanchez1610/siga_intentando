package com.udep.siga.bean.enumeration;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.util.JsonTipoPagoEspecialSerializer;

/**
 *
 * @author Administrador
 */
@JsonSerialize(using = JsonTipoPagoEspecialSerializer.class)
public enum TipoPagoEspecial { 
    ORDINARIO(1, "los primeros días del mes"),
    ESPECIAL_QUINCENA(2, "los días quince del mes"),
    ESPECIAL_FIN_DE_MES(3, "los días treinta del mes");
    
    private final int id;
    private final String nombre;

    TipoPagoEspecial(int id, String nombre) {
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
    
    public static TipoPagoEspecial parse(int id) {
        TipoPagoEspecial tipoPagoEspecial = null; // Default
        for (TipoPagoEspecial item : TipoPagoEspecial.values()) {
            if (item.getId() == id) {
                tipoPagoEspecial = item;
                break;
            }
        }
        return tipoPagoEspecial;
    }
}
