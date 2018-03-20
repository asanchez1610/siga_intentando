package com.udep.siga.bean.enumeration;

/**
 *
 * @author Wilfredo Atoche
 */
public enum TipoExalumno {
    Abandono(1, "Abandono"),
    Retiro_voluntario(2, "Retiro voluntario"),
    Separacion_academica(3, "Separacion academica"),
    Separacion_disciplinaria(4, "Separacion disciplinaria");
    
    private final int id;
    private final String descripcion;
    
    TipoExalumno(int id, String descripcion){
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
    
    public static TipoExalumno parse(int id) {
        TipoExalumno tipoExalumno = null; // Default
        for (TipoExalumno item : TipoExalumno.values()) {
            if (item.getId() == id) {
                tipoExalumno = item;
                break;
            }
        }
        return tipoExalumno;
    }
}
