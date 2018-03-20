package com.udep.siga.dao.impl;

import com.udep.siga.bean.Evaluacion;
import com.udep.siga.bean.EvaluacionAlumno;
import com.udep.siga.bean.Nota;
import com.udep.siga.bean.TipoEvaluacion;
import com.udep.siga.dao.EvaluacionDAO;
import com.udep.siga.dao.util.CustomizeJdbcDaoSupport;
import com.udep.siga.dao.util.UtilRowMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Wilfredo Atoche
 */
@Repository("evaluacionDAO")
public class IEvaluacionDAO extends CustomizeJdbcDaoSupport implements EvaluacionDAO {

    public List<TipoEvaluacion> getTipoEvaluacionList(int idAsignaturaDictada,
            int idSeccion) {
        String sql = "SELECT DISTINCT TE.IDTIPOEVALUACION, TE.DESCRIPCION "
                + "FROM EVALUACION E, TIPOEVALUACION TE WHERE E.IDASIGNATURADICTADA = :idAsignaturaDictada "
                + "AND E.IDSECCION = :idSeccion AND E.IDTIPOEVALUACION = TE.IDTIPOEVALUACION";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAsignaturaDictada", idAsignaturaDictada);
        params.addValue("idSeccion", idSeccion);
        List<TipoEvaluacion> list = this.getNamedParameterJdbcTemplate().query(sql, params,
                UtilRowMapper.getTipoEvaluacionMapper());
        if (list.isEmpty()) {
            return new ArrayList<TipoEvaluacion>(0);
        } else {
            return list;
        }
    }

    public List<Evaluacion> getEvaluacionList(int idAsignaturaDictada,
            int idSeccion, TipoEvaluacion tipoEvaluacion) {
        String sql = "SELECT E.IDEVALUACION, E.IDESTADOEVALUACION, E.IDTIPOEVALUACION, TE.DESCRIPCION 'TIPOEVALUACION', E.IDSECCION, "
                + "E.IDASIGNATURADICTADA, E.NOMBRE, E.DESCRIPCION, E.FECHAEVALUACION, E.FECHARECOJO, "
                + "E.FECHACALIFICACION, E.FECHAENTREGASEC, E.FECHAENTREGA, E.FECHARECOJOREC, E.FECHACALIFICACIONREC, "
                + "E.FECHAENTREGASECREC, E.FECHAENTREGAREC, E.INFORMATIVA, E.ANULABLE, E.PESO, E.CONFIRMADA, "
                + "E.CANCELADA, E.FECHAENTREGARECSEC FROM EVALUACION E, TIPOEVALUACION TE "
                + "WHERE E.IDTIPOEVALUACION = TE.IDTIPOEVALUACION AND E.IDASIGNATURADICTADA = :idAsignaturaDictada "
                + "AND E.IDSECCION = :idSeccion AND E.IDTIPOEVALUACION = :idTipoEvaluacion ORDER BY E.FECHAEVALUACION ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAsignaturaDictada", idAsignaturaDictada);
        params.addValue("idSeccion", idSeccion);
        params.addValue("idTipoEvaluacion", tipoEvaluacion.getId());
        List<Evaluacion> list = this.getNamedParameterJdbcTemplate().query(sql, params,
                UtilRowMapper.getEvaluacionMapper());
        return list;
    }

    public EvaluacionAlumno getEvaluacionAlumno(int idEvaluacion, int idAlumno) {
        String sql = "SELECT TOP 1 EA.IDEVALUACION, EA.NOTAINFORMATIVA, EA.ANULADA, EA.IDNOTA, "
                + "N.DESCRIPCION, N.COLOR, N.VALOR,CASE WHEN E.IDSISTEMANOTAS = 1 THEN 0 ELSE 1 END 'LITERAL' "
                + "FROM EVALUACIONALUMNO EA LEFT OUTER JOIN NOTA N ON EA.IDNOTA = N.IDNOTA, "
                + "EDICIONESTUDIONOTA E WHERE EA.IDEDICIONESTUDIO = E.IDEDICIONESTUDIO "
                + "AND EA.IDALUMNO = :idAlumno AND EA.IDEVALUACION = :idEvaluacion";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", idAlumno);
        params.addValue("idEvaluacion", idEvaluacion);

        List<EvaluacionAlumno> list = this.getNamedParameterJdbcTemplate().query(sql, params,
                UtilRowMapper.getEvaluacionAlumnoMapper());

        if (list.size() > 0) {
            return list.get(0);
        }

        return null;
    }

    public List<Evaluacion> getEvaluacionesByAsignaturaDictada(int idAsignatura, int idSeccion) {

        String sql = "SELECT E.IDEVALUACION, E.IDESTADOEVALUACION, E.IDTIPOEVALUACION, TE.DESCRIPCION 'TIPOEVALUACION', E.IDSECCION, "
                + "E.IDASIGNATURADICTADA, E.NOMBRE, E.DESCRIPCION, E.FECHAEVALUACION, E.FECHARECOJO, "
                + "E.FECHACALIFICACION, E.FECHAENTREGASEC, E.FECHAENTREGA, E.FECHARECOJOREC, "
                + "E.FECHACALIFICACIONREC, E.FECHAENTREGASECREC, E.FECHAENTREGAREC, E.INFORMATIVA, "
                + "E.ANULABLE, E.PESO, E.CONFIRMADA, E.CANCELADA, E.FECHAENTREGARECSEC "
                + "FROM EVALUACION E, TIPOEVALUACION TE WHERE E.IDTIPOEVALUACION = TE.IDTIPOEVALUACION AND E.IDASIGNATURADICTADA = :idasignaturadictada "
                + "AND E.IDSECCION = :idseccion ORDER BY E.IDTIPOEVALUACION, E.FECHAEVALUACION ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idasignaturadictada", idAsignatura);
        params.addValue("idseccion", idSeccion);

        List<Evaluacion> evaluaciones = this.getNamedParameterJdbcTemplate()
                .query(sql, params, UtilRowMapper.getEvaluacionMapper());
        return evaluaciones;
    }

    public Nota getNota(int idNota) {
        if (idNota == 0) {
            return null;
        }
        String sql = "SELECT N.IDNOTA, N.DESCRIPCION, N.COLOR, N.APROBADA, N.VALOR "
                + "FROM NOTA N WHERE N.IDNOTA = ?";
        Nota nota = (Nota) this.getJdbcTemplate().queryForObject(sql, new Object[]{idNota},
                UtilRowMapper.getNotaMapper());
        return nota;
    }

    public Map<String, Object> getNombreEvaluacionAsignatura(int idEvaluacion) {
        String sql = "SELECT TOP 1 E.NOMBRE 'evaluacion', AD.NOMBRE 'asignatura' "
                + "FROM EVALUACION E, ASIGNATURASECCION ASEC, ASIGNATURADICTADA AD "
                + "WHERE E.IDASIGNATURADICTADA = ASEC.IDASIGNATURADICTADA AND E.IDSECCION = ASEC.IDSECCION AND "
                + "ASEC.IDASIGNATURADICTADA = AD.IDASIGNATURADICTADA AND E.IDEVALUACION = ?";
        Map<String, Object> item = (Map<String, Object>) this.getJdbcTemplate()
                .queryForObject(sql, new Object[]{idEvaluacion},
                UtilRowMapper.getHashMapMapper());
        return item;
    }
    
    @Override
    public String notifyEvaluacion(int idEvaluacion){
        String sql = "SELECT COUNT(*) FROM EVALUACION E WHERE E.IDEVALUACION = ? AND "
                + "E.FECHAENTREGA BETWEEN DATEADD(dd,-2,GETDATE()) AND DATEADD(dd,2,GETDATE()) AND E.IDESTADOEVALUACION = 4";
        
        Integer count = this.getJdbcTemplate().queryForObject(sql, 
                new Object[]{idEvaluacion}, Integer.class);
        
        if(count > 0){
            return "%s por recoger (fase de reclamos)";
        }
        
        sql = "SELECT COUNT(*) FROM EVALUACION E WHERE E.IDEVALUACION = ? AND "
                + "E.FECHAENTREGAREC BETWEEN DATEADD(dd,-2,GETDATE()) AND DATEADD(dd,2,GETDATE()) AND E.IDESTADOEVALUACION = 6";
        
        count = this.getJdbcTemplate().queryForObject(sql, 
                new Object[]{idEvaluacion}, Integer.class);
        
        if(count > 0){
            return "Reclamos de %s por recoger";
        }
        return null;
    }
}
