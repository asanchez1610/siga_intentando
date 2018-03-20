package com.udep.siga.dao;

import com.udep.siga.bean.EstrategiaSilabo;
import com.udep.siga.bean.ObjetivoSilabo;
import com.udep.siga.bean.RequisitoAsignatura;
import com.udep.siga.bean.Silabo;
import com.udep.siga.bean.TemaSilabo;
import com.udep.siga.bean.UnidadSilabo;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrador
 */
public interface SilaboDAO {

    public Silabo getSilaboById(int id);
    
    public Silabo getSilaboByAsignaturaDictada(int idAsignatura, int idSeccion);
    public Map<String, Object> getDetallesSilaboAsignatura(int idAsignaturaDictada, int idPlan);    
    public List<ObjetivoSilabo> getObjetivosList(int idSilabo);
    public List<UnidadSilabo> getUnidadesList(int idSilabo);    
    public List<TemaSilabo> getTemasByUnidad(int idUnidad);    
    public List<Date> getFechaSessionListByTema(int idTema);
    public int getGrupoRequisitoAsignatura(int idAsignaturaDictada, int idPlan);
    public List<RequisitoAsignatura> getRequisitoAsignatura(int idRequisito);    
    public List<UnidadSilabo> getUnidadesToEvaluacion(int idEvaluacion);    
    public List<TemaSilabo> getTemasUnidadToEvaluacion(int idEvaluacion, int idUnidad);
    
    public List<EstrategiaSilabo> getEstrategiasSilaboDictadaList(int idSilabo);
    public EstrategiaSilabo getEstrategiaSilabo(int idEstrategia);
    public List<String> getBibliografiaSilaboByTipo(int idSilabo, int idTipo);
    
    public List<String> getCentrosBySilabo(int idSilabo);
    public List<String> getProgramasBySilabo(int idSilabo);
}
