package com.udep.siga.bean.enumeration;

/**
 *
 * @author Administrador
 */
public enum CategoriaSolicitud {
    MI_AVANCE(1, "Mi Avance"),
    MIS_CALIFICACIONES(2, "Mis Calificaciones"),
    MIS_ESTUDIOS(3, "Mis Estudios"),
    PERIODO_ACTUAL(4, "Periodo Actual");
    
    private final int id;
    private final String nombre;

    CategoriaSolicitud(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public static CategoriaSolicitud parse(int id) {
        CategoriaSolicitud categoriaSolicitud = null; // Default
        for (CategoriaSolicitud item : CategoriaSolicitud.values()) {
            if (item.getId() == id) {
                categoriaSolicitud = item;
                break;
            }
        }
        return categoriaSolicitud;
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
}
