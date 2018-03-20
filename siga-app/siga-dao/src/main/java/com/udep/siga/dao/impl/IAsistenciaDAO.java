package com.udep.siga.dao.impl;

import com.udep.siga.bean.Asistencia;
import com.udep.siga.bean.HorarioAsesoria;
import com.udep.siga.dao.AsistenciaDAO;
import com.udep.siga.dao.util.CustomizeJdbcDaoSupport;
import com.udep.siga.dao.util.UtilRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
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
@Repository("asistenciaDAO")
public class IAsistenciaDAO extends CustomizeJdbcDaoSupport implements AsistenciaDAO{
    
    public int getCountInasistencia(int idAsignatura,int idSeccion,int idAlumno) {
        String sql = "SELECT COUNT(*) FROM ASISTENCIA "
                + "WHERE IDASIGNATURADICTADA = :idAsignatura AND IDSECCION = :idSeccion "
                + "AND IDALUMNO = :idAlumno AND PRESENTE = 0";
        
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAsignatura", idAsignatura);
        params.addValue("idSeccion", idSeccion);
        params.addValue("idAlumno", idAlumno);
        
        return this.getNamedParameterJdbcTemplate().queryForInt(sql, params);

    }
    
    /*
     * Obtiene DescuentoInasistencia y el total de numero de faltas por alumno 
     * de una determinada asignatura 
     */
    public Map<String, Object> getDescuentoInasistencias(int idAsignatura,int idSeccion,int idAlumno) {
        String sql = "SELECT TOP 1 DESCUENTOINASISTENCIAS, NUMFALTAS,ISNULL(IDNOTADCTO,0) AS IDNOTADCTO "
                + "FROM MATRICULA WHERE IDALUMNO = :idAlumno "
                + "AND IDASIGNATURADICTADA = :idAsignatura AND IDSECCION = :idSeccion";
        
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAsignatura", idAsignatura);
        params.addValue("idSeccion", idSeccion);
        params.addValue("idAlumno", idAlumno);
        
        Map<String, Object> item = (Map<String, Object>) 
                this.getNamedParameterJdbcTemplate().queryForObject(
                sql, params, new RowMapper() {
            public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("descuentoInasistencia", rs.getInt("DESCUENTOINASISTENCIAS"));
                item.put("numFaltas", rs.getInt("NUMFALTAS"));
                item.put("notaSinDcto", rs.getInt("IDNOTADCTO"));
               
                return item;
            }
        });
        return item;
    }
    
    /*
     * Obtiene datos de una Asignatura, si se aplican descuento por falta,
     * el modo de asistencia y el numero de clases totales.
     */
    public Map<String, Object> getAsistenciaAsignatura(int idAsignatura,int idSeccion) {
        String sql = "SELECT DESCUENTOFALTAS, MODOASISTENCIAS, NUMCLASES "
                + "FROM ASIGNATURASECCION WHERE IDASIGNATURADICTADA = :idAsignatura "
                + "AND IDSECCION = :idSeccion";
        
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAsignatura", idAsignatura);
        params.addValue("idSeccion", idSeccion);
        
        Map<String, Object> item = (Map<String, Object>)
                this.getNamedParameterJdbcTemplate().queryForObject(
                sql, params, new RowMapper() {
            public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("descuentoFaltas", rs.getBoolean("DESCUENTOFALTAS"));
                item.put("modoAsistencia", rs.getInt("MODOASISTENCIAS"));
                item.put("numClases", rs.getInt("NUMCLASES"));
               
                return item;
            }
        });
        return item;
    }
    
    public List<Asistencia> getAsistenciaAlumnoAsignaturaList(int idAsignatura, int idSeccion, int idAlumno) {
        String sql = "SELECT C.FECHA, BH.DESCRIPCION 'HORA', A.PRESENTE FROM CLASE C, ASISTENCIA A, "
                + "BLOQUEHORARIO BH WHERE C.IDCLASE = A.IDCLASE AND C.IDASIGNATURADICTADA = ? AND "
                + "C.IDSECCION = ? AND A.IDALUMNO = ? AND C.IDBLOQUEHORARIO = BH.IDBLOQUEHORARIO "
                + "ORDER BY C.FECHA DESC";
        List<Asistencia> list = this.getJdbcTemplate().query(sql,
                new Object[]{idAsignatura, idSeccion, idAlumno}, UtilRowMapper.getAsistenciaMapper());
        return list;
    }
    
}
