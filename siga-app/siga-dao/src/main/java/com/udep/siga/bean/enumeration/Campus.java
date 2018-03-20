package com.udep.siga.bean.enumeration;

/**
 *
 * @author Wilfredo Atoche
 */
public enum Campus {
    Piura(1),
    Lima(2);
    
    private final int id; 
    
    Campus(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
    
    public static Campus parse(int id) {
        Campus campus = null; // Default
        for (Campus item : Campus.values()) {
            if (item.getId() == id) {
                campus = item;
                break;
            }
        }
        return campus;
    }
}
