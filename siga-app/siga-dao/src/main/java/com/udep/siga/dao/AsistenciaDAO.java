package com.udep.siga.dao;

import com.udep.siga.bean.Asistencia;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrador
 */
public interface AsistenciaDAO {

    public int getCountInasistencia(int idAsignatura, int idSeccion, int idAlumno);

    public Map<String, Object> getDescuentoInasistencias(int idAsignatura, int idSeccion, int idAlumno);

    public Map<String, Object> getAsistenciaAsignatura(int idAsignatura, int idSeccion);

    public List<Asistencia> getAsistenciaAlumnoAsignaturaList(
            int idAsignatura, int idSeccion, int idAlumno);   
}
