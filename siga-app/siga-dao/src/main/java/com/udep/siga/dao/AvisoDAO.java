package com.udep.siga.dao;

import com.udep.siga.bean.Aviso;
import com.udep.siga.bean.Destacado;
import java.util.List;

/**
 *
 * @author Wilfredo Atoche
 */
public interface AvisoDAO {
    public List<Aviso> getAvisos(int idalumno);    
    public Aviso getAviso(int id);
    public int getTotalAvisosNuevos(String arrayEstudioCampus, String arrayCampus);    
    public List<Aviso> getAvisoAsignaturaList(int idAsignatura, int idSeccion);
    public int getTotalAvisosAsignaturaNuevos(int idAsignatura, int idSeccion);
    public List<Destacado> getDestacado();
}
