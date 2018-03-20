package com.udep.siga.bean.enumeration;

/**
 *
 * @author Wilfredo Atoche
 */
public enum TipoDocIdentidad {
    DNI(1,"Documento Nacional de Identidad (DNI)"),
    LIBRETA_MILITAR(2,"Libreta Militar"),
    CARNE_EXTRANJERIA(3,"Carné de Extranjería"),
    CARNE_IDENTIF_POLICIAL(4,"Carné de Identificación Policial"),
    PASAPORTE(5,"Pasaporte");
    
    private final int id;
    private final String descripcion;
    
    TipoDocIdentidad(int id, String descripcion){
        this.id = id;
        this.descripcion = descripcion;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    public static TipoDocIdentidad parse(int id) {
        TipoDocIdentidad tipoDocIdentidad = null; // Default
        for (TipoDocIdentidad item : TipoDocIdentidad.values()) {
            if (item.getId() == id) {
                tipoDocIdentidad = item;
                break;
            }
        }
        return tipoDocIdentidad;
    }
}
