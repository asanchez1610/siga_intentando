package com.udep.siga.bean.enumeration;

/**
 *
 * @author Wilfredo Atoche
 */
public enum SistemaHorario {
    Pregrado(1, "Horarios de Pregrado"),
    Posgrado_PE(2, "Horarios de Posgrado - Programas especiales");
    
    private final int id;
    private final String descripcion;

    SistemaHorario(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return this.id;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    public static SistemaHorario parse(int id) {
        SistemaHorario campus = null; // Default
        for (SistemaHorario item : SistemaHorario.values()) {
            if (item.getId() == id) {
                campus = item;
                break;
            }
        }
        return campus;
    }
}