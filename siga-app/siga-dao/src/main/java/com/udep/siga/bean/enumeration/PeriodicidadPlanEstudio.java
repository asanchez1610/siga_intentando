package com.udep.siga.bean.enumeration;

/**
 *
 * @author Wilfredo Atoche
 */
/* enum PeriodicidadPlanEstudio */
public enum PeriodicidadPlanEstudio {
    Semestral_Pregrado(1),
    Anual_Diexe(2),
    Trimestral_EJE_L(3),
    Anual_ETS(4),
    Anual_ETSas(5),
    Anual_ETSme(6),
    Cuatrimestral_EJE_P(7),
    Postgrado(8);
    
    private final int id; 
    
    PeriodicidadPlanEstudio(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
    
    public static PeriodicidadPlanEstudio parse(int id) {
        PeriodicidadPlanEstudio periodicidadPlanEstudio = null; // Default
        for (PeriodicidadPlanEstudio item : PeriodicidadPlanEstudio.values()) {
            if (item.getId() == id) {
                periodicidadPlanEstudio = item;
                break;
            }
        }
        return periodicidadPlanEstudio;
    }
}
