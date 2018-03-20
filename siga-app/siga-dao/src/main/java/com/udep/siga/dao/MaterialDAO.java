package com.udep.siga.dao;

import com.udep.siga.bean.Material;
import java.util.List;

/**
 *
 * @author Administrador
 */
public interface MaterialDAO {
    public List<Material> getMaterialByTipo(int idAsignatura, int idSeccion, int tipo);
    public Material getMaterialById(int id);
    public int getTotalMaterialNuevo(int idAsignatura, int idSeccion);
}
