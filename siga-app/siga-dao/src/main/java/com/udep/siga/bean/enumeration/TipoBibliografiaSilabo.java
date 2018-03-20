package com.udep.siga.bean.enumeration;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.udep.siga.bean.util.JsonTipoBibliografiaSilaboSerializer;

/**
 *
 * @author Administrador
 */
@JsonSerialize(using = JsonTipoBibliografiaSilaboSerializer.class)
public enum TipoBibliografiaSilabo {

    BASICA(1, "Bibliografía básica"),
    AVANZADA(2, "Bibliografía avanzada");
    private final int id;
    private final String nombre;

    TipoBibliografiaSilabo(int id, String nombre) {
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
    
    public static TipoBibliografiaSilabo parse(int id) {
        TipoBibliografiaSilabo tipoBibliografiaSilabo = null; // Default
        for (TipoBibliografiaSilabo item : TipoBibliografiaSilabo.values()) {
            if (item.getId() == id) {
                tipoBibliografiaSilabo = item;
                break;
            }
        }
        return tipoBibliografiaSilabo;
    }
}
