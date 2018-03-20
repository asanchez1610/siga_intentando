package com.udep.siga.bean.enumeration;

/**
 *
 * @author Wilfredo Atoche
 */
/* enum TipoPeriodoAcademico */
public enum TipoPeriodoAcademico {
    Regular(1),
    Verano(2);
    
    private final int id; 
    
    TipoPeriodoAcademico(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
    
    public static TipoPeriodoAcademico parse(int id) {
        TipoPeriodoAcademico tipoPeriodoAcademico = null; // Default
        for (TipoPeriodoAcademico item : TipoPeriodoAcademico.values()) {
            if (item.getId() == id) {
                tipoPeriodoAcademico = item;
                break;
            }
        }
        return tipoPeriodoAcademico;
    }
}
