package com.udep.siga.bean.enumeration;

/**
 *
 * @author Wilfredo Atoche
 */
/* enum TipoClase */
public enum TipoClase {
    Clase_regular(1),
    Seminario(2),
    Laboratorio(3);
    
    private int id; 
    
    TipoClase(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
    
    public static TipoClase parse(int id) {
        TipoClase tipoClase = null; // Default
        for (TipoClase item : TipoClase.values()) {
            if (item.getId() == id) {
                tipoClase = item;
                break;
            }
        }
        return tipoClase;
    }
}