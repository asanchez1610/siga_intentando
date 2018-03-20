package com.udep.siga.dao.impl;

import com.udep.siga.bean.EstrategiaSilabo;
import com.udep.siga.bean.ObjetivoSilabo;
import com.udep.siga.bean.RequisitoAsignatura;
import com.udep.siga.bean.Silabo;
import com.udep.siga.bean.TemaSilabo;
import com.udep.siga.bean.UnidadSilabo;
import com.udep.siga.dao.SilaboDAO;
import com.udep.siga.dao.util.CustomizeJdbcDaoSupport;
import com.udep.siga.dao.util.UtilRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrador
 */
@Repository("silaboDAO")
public class ISilaboDAO extends CustomizeJdbcDaoSupport implements SilaboDAO {

    @Override
    public Silabo getSilaboById(int id) {
        String sql = "SELECT A.IDASIGNATURADICTADA, A.NOMBRE, A.SIGLA, A.CREDITOS, "
                + "A.IDPERIODOACADEMICO, P.NOMBRE AS PERIODO, ASE.IDSECCION, "
                + "ASE.NOMBRESECCION, S.IDSILABO, S.SUMILLA, S.FUNDAMENTACION, "
                + "S.DESCRIPCIONEVALUACION FROM SILABO S, "
                + "ASIGNATURADICTADA A, ASIGNATURASECCION ASE, PERIODOACADEMICO P "
                + "WHERE S.IDASIGNATURADICTADA = A.IDASIGNATURADICTADA AND "
                + "S.IDSECCION = ASE.IDSECCION AND S.IDASIGNATURADICTADA = ASE.IDASIGNATURADICTADA "
                + "AND A.IDPERIODOACADEMICO = P.IDPERIODOACADEMICO "
                + "AND S.IDSILABO = ?";     

        List<Silabo> silaboList = this.getJdbcTemplate()
                .query(sql, new Object[]{id}, UtilRowMapper.getSilaboMapper());

        if (silaboList.isEmpty()) {
            return null;
        } else {
            return silaboList.get(0);
        }
    }
    
    @Override
    public Silabo getSilaboByAsignaturaDictada(int idAsignatura, int idSeccion) {
        String sql = "SELECT A.IDASIGNATURADICTADA, A.NOMBRE, A.SIGLA, A.CREDITOS, "
                + "A.IDPERIODOACADEMICO, P.NOMBRE AS PERIODO, ASE.IDSECCION, "
                + "ASE.NOMBRESECCION, S.IDSILABO, S.SUMILLA, S.FUNDAMENTACION, "
                + "S.DESCRIPCIONEVALUACION FROM SILABO S, "
                + "ASIGNATURADICTADA A, ASIGNATURASECCION ASE, PERIODOACADEMICO P "
                + "WHERE S.IDASIGNATURADICTADA = A.IDASIGNATURADICTADA AND "
                + "S.IDSECCION = ASE.IDSECCION AND S.IDASIGNATURADICTADA = ASE.IDASIGNATURADICTADA "
                + "AND A.IDPERIODOACADEMICO = P.IDPERIODOACADEMICO "
                + "AND S.IDASIGNATURADICTADA = :idAsignatura AND S.IDSECCION = :idSeccion";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idSeccion", idSeccion);
        params.addValue("idAsignatura", idAsignatura);

        List<Silabo> silaboList = this.getNamedParameterJdbcTemplate()
                .query(sql, params, UtilRowMapper.getSilaboMapper());

        if (silaboList.isEmpty()) {
            return null;
        } else {
            return silaboList.get(0);
        }
    }

    @Override
    public Map<String, Object> getDetallesSilaboAsignatura(int idAsignaturaDictada, int idPlan) {
        String sql = "SELECT DISTINCT TOP 1 T.DESCRIPCION,PEA.NIVEL,PEA.NUMPERIODO "
                + "FROM PLANESTUDIOSASIGNATURAPERACAD PEAP,PLANESTUDIOSASIGNATURA PEA,"
                + "TIPOASIGNATURA T WHERE PEAP.IDASIGNATURA = PEA.IDASIGNATURA "
                + "AND PEAP.IDASIGNATURADICTADA = :idAsignaturaDictada "
                + "AND PEA.IDTIPOASIGNATURA = T.IDTIPOASIGNATURA "
                //+ "AND "
               // + "PEA.IDPLANESTUDIOS = :idPlan "
                + " AND PEAP.IDPLANESTUDIOS = PEA.IDPLANESTUDIOS "
                + "AND PEA.ACTIVA = 1 ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idPlan", idPlan);
        params.addValue("idAsignaturaDictada", idAsignaturaDictada);

        Map<String, Object> item = (Map<String, Object>) this.getNamedParameterJdbcTemplate().queryForObject(
                sql, params, new RowMapper() {
            public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("tipoAsignatura", rs.getString("DESCRIPCION"));
                item.put("nivel", rs.getInt("NIVEL"));
                item.put("ciclo", rs.getString("NUMPERIODO"));

                return item;
            }
        });
        return item;
    }    

    @Override
    public List<ObjetivoSilabo> getObjetivosList(int idSilabo) {

        String sql = "SELECT IDOBJETIVOSILABO,NUMERO,DESCRIPCION FROM OBJETIVOSILABO "
                + "WHERE IDSILABO = :idSilabo ORDER BY NUMERO";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idSilabo", idSilabo);

        List<ObjetivoSilabo> objetivos = this.getNamedParameterJdbcTemplate()
                .query(sql, params, UtilRowMapper.getObjetivoSilaboMapper());

        return objetivos;

    }

    @Override
    public List<UnidadSilabo> getUnidadesList(int idSilabo) {

        String sql = "SELECT IDUNIDADSILABO,NUMERO,DESCRIPCION FROM UNIDADSILABO "
                + "WHERE IDSILABO = :idSilabo ORDER BY NUMERO";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idSilabo", idSilabo);

        List<UnidadSilabo> unidades = this.getNamedParameterJdbcTemplate()
                .query(sql, params, UtilRowMapper.getUnidadSilaboMapper());

        return unidades;

    }
    
    @Override
    public List<TemaSilabo> getTemasByUnidad(int idUnidad) {
        String sql = "SELECT TS.IDTEMASILABO,TS.NUMERO,TS.TEMA,TS.SEMANA,TS.HORASTEORICAS, "
                + "TS.HORASPRACTICAS FROM TEMASILABO TS WHERE IDUNIDADSILABO = ? "
                + "ORDER BY CAST(NUMERO AS DECIMAL)";
        List<TemaSilabo> temaList = this.getJdbcTemplate()
                .query(sql, new Object[]{idUnidad},UtilRowMapper.getTemaSilaboMapper());
        return temaList;
    }
    
    @Override
    public List<Date> getFechaSessionListByTema(int idTema) {
        String sql = "SELECT FECHASESION FROM SESIONSILABO WHERE IDTEMASILABO = ? "
                + "ORDER BY FECHASESION DESC";
        List<Date> fechas = this.getJdbcTemplate()
                .query(sql, new Object[]{idTema}, new RowMapper() {
                    public Date mapRow(ResultSet rs, int rowNum) throws SQLException {                        
                        return rs.getDate("FECHASESION");
                    }
                });
        return fechas;
    }

    public int getGrupoRequisitoAsignatura(int idAsignaturaDictada, int idPlan) {

        String sql = "SELECT G.IDGRUPOREQUISITO FROM GRUPOREQUISITO G "
                + "WHERE G.IDASIGNATURA = :idAsignaturaDictada AND G.IDPLANESTUDIOS = idPlan ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idPlan", idPlan);
        params.addValue("idAsignaturaDictada", idAsignaturaDictada);

        return this.getNamedParameterJdbcTemplate().queryForObject(
                sql, params, Integer.class);
    }

    public List<RequisitoAsignatura> getRequisitoAsignatura(int idRequisito) {

        String sql = "SELECT R.DESCRIPCION, A.NOMBRE,P.VALOR FROM PLANESTUDIOASIGNATURAREQ P, "
                + "ASIGNATURA A, REQPLANESTUDIOSASIGASIG R "
                + "WHERE P.IDASIGNATURAREQ = A.IDASIGNATURA "
                + "AND P.IDREQPLANESTUDIOSASIGASIG = R.IDREQPLANESTUDIOSASIGASIG "
                + "AND P.IDGRUPOREQUISITO = :idRequisito "
                + "UNION "
                + "SELECT R.DESCRIPCION,'' AS 'NOMBRE',P.VALOR "
                + "FROM PLANESTUDIOSASIGNATURAREQGEN P, REQPLANESTUDIOSASIGGEN R "
                + "WHERE P.IDREQPLANESTUDIOSASIGGEN = R.IDREQPLANESTUDIOSASIGGEN "
                + "AND P.IDGRUPOREQUISITO = :idRequisito ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idRequisito", idRequisito);

        List<RequisitoAsignatura> requisitos = this.getNamedParameterJdbcTemplate()
                .query(sql, params, UtilRowMapper.getRequisitoAsignaturaMapper());

        return requisitos;
    }

    public List<UnidadSilabo> getUnidadesToEvaluacion(int idEvaluacion) {
        String sql = "SELECT US.IDUNIDADSILABO, US.NUMERO, US.DESCRIPCION FROM UNIDADSILABO US "
                + "WHERE EXISTS (SELECT TS.IDUNIDADSILABO FROM TEMASILABO TS WHERE EXISTS "
                + "(SELECT ETS.IDTEMASILABO FROM EVALUACIONTEMASILABO ETS "
                + "WHERE ETS.IDTEMASILABO = TS.IDTEMASILABO AND ETS.IDEVALUACION = ?) AND "
                + "TS.IDUNIDADSILABO = US.IDUNIDADSILABO) ORDER BY US.NUMERO";
       List<UnidadSilabo> unidadList = this.getJdbcTemplate()
                .query(sql, new Object[]{idEvaluacion},
                UtilRowMapper.getUnidadSilaboMapper());
        return unidadList;
    }
    
    @Override
    public List<TemaSilabo> getTemasUnidadToEvaluacion(int idEvaluacion, int idUnidad) {
        String sql = "SELECT TS.IDTEMASILABO,TS.NUMERO,TS.TEMA,TS.SEMANA,TS.HORASTEORICAS, "
                + "TS.HORASPRACTICAS FROM TEMASILABO TS WHERE EXISTS (SELECT ETS.IDTEMASILABO "
                + "FROM EVALUACIONTEMASILABO ETS WHERE TS.IDTEMASILABO = ETS.IDTEMASILABO AND "
                + "ETS.IDEVALUACION = ?) AND TS.IDUNIDADSILABO = ? ORDER BY TS.NUMERO";
        List<TemaSilabo> temaList = this.getJdbcTemplate()
                .query(sql, new Object[]{idEvaluacion, idUnidad},UtilRowMapper.getTemaSilaboMapper());
        return temaList;
    }
    
    public List<EstrategiaSilabo> getEstrategiasSilaboDictadaList(int idSilabo) {
        String sql = "SELECT ISNULL(ESD.IDESTRATEGIASILABO,0) 'IDESTRATEGIASILABO', ESD.TITULO, "
                + "ESD.DESCRIPCION FROM ESTRATEGIASILABODICTADA ESD LEFT JOIN ESTRATEGIASILABO ES "
                + "ON ES.IDESTRATEGIASILABO=ESD.IDESTRATEGIASILABO WHERE IDSILABO = ? "
                + "ORDER BY ESD.TITULO,ES.TITULO";
        List<EstrategiaSilabo> list = this.getJdbcTemplate()
                .query(sql, new Object[]{idSilabo},UtilRowMapper.getEstrategiaSilaboMapper());
        return list;
    }
    
    public EstrategiaSilabo getEstrategiaSilabo(int idEstrategia) {
        String sql = "SELECT IDESTRATEGIASILABO,TITULO,DESCRIPCION FROM ESTRATEGIASILABO "
                + "WHERE IDESTRATEGIASILABO = ?";
        List<EstrategiaSilabo> list = this.getJdbcTemplate()
                .query(sql, new Object[]{idEstrategia},UtilRowMapper.getEstrategiaSilaboMapper());
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }
    
    public List<String> getBibliografiaSilaboByTipo(int idSilabo, int idTipo) {
        String sql = "SELECT APA FROM BIBLIOGRAFIASILABO WHERE IDSILABO = ? "
                + "AND TIPO = ? ORDER BY NUMERO";
        List<String> list = this.getJdbcTemplate().queryForList
                (sql, new Object[]{idSilabo,idTipo},String.class);        
        return list;
    }
    
    public List<String> getCentrosBySilabo(int idSilabo) {
        String sql = "SELECT DISTINCT CA.NOMBRE FROM CENTROACADEMICO CA LEFT JOIN ESTUDIO E ON "
                + "E.IDCENTROACADEMICO = CA.IDCENTROACADEMICO WHERE IDESTUDIO "
                + "IN(select IDESTUDIO FROM EDICIONESTUDIO WHERE IDEDICIONESTUDIO "
                + "IN (SELECT IDEDICIONESTUDIO FROM ASIGNATURA WHERE IDASIGNATURA "
                + "IN (select IDASIGNATURA from PLANESTUDIOSASIGNATURAPERACAD WHERE "
                + "IDASIGNATURADICTADA = (SELECT IDASIGNATURADICTADA FROM SILABO "
                + "WHERE IDSILABO = ? )))) ";
        List<String> list = this.getJdbcTemplate().queryForList
                (sql, new Object[]{idSilabo},String.class);        
        return list;
    }
    
    public List<String> getProgramasBySilabo(int idSilabo) {
        String sql = "SELECT DISTINCT E.NOMBRE FROM EDICIONESTUDIO EE, ESTUDIO E WHERE EE.IDESTUDIO = E.IDESTUDIO "
                + "AND EE.IDEDICIONESTUDIO IN (SELECT IDEDICIONESTUDIO FROM ASIGNATURA WHERE IDASIGNATURA "
                + "IN (SELECT IDASIGNATURA FROM PLANESTUDIOSASIGNATURAPERACAD "
                + "WHERE IDASIGNATURADICTADA = (SELECT IDASIGNATURADICTADA FROM SILABO WHERE IDSILABO = ?)))";
        List<String> list = this.getJdbcTemplate().queryForList
                (sql, new Object[]{idSilabo},String.class);        
        return list;
    }
}
