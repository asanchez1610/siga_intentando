package com.udep.siga.util;

/**
 *
 * @author Wilfredo Atoche
 */
public enum Rol {
    ALUMNO_PREGRADO("ROLE_APREGRADO"),
    ALUMNO_POSGRADO("ROLE_APOSGRADO"),
    EX_EG_PREGRADO("ROLE_EEPREGRADO"),
    EX_EG_POSGRADO("ROLE_EEPOSGRADO");
    
    private final String rol;
    
    Rol(String rol){
        this.rol = rol;
    }

    /**
     * @return the rol
     */
    public String getRol() {
        return rol;
    }
    
    public static Rol parse(String rolName) {
        Rol rol = null; // Default
        for (Rol item : Rol.values()) {
            if (item.getRol().equals(rolName)) {
                rol = item;
                break;
            }
        }
        return rol;
    }
}
