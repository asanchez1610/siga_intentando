package com.udep.siga.dao;

import com.udep.siga.bean.Evaluacion;
import com.udep.siga.bean.EvaluacionAlumno;
import com.udep.siga.bean.Nota;
import com.udep.siga.bean.TipoEvaluacion;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Wilfredo Atoche
 */
public interface EvaluacionDAO {
    public List<TipoEvaluacion> getTipoEvaluacionList(int idAsignaturaDictada,
            int idSeccion);
    
    public List<Evaluacion> getEvaluacionList(int idAsignaturaDictada,
            int idSeccion, TipoEvaluacion tipoEvaluacion);
    
    public EvaluacionAlumno getEvaluacionAlumno(int idEvaluacion, int idAlumno);
    public List<Evaluacion> getEvaluacionesByAsignaturaDictada
            (int idAsignatura, int idSeccion);
    
    public Nota getNota(int idNota);
    
    public Map<String, Object> getNombreEvaluacionAsignatura(int idEvaluacion);
    
    public String notifyEvaluacion(int idEvaluacion);
}
