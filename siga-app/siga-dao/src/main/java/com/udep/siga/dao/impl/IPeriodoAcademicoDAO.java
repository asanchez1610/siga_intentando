package com.udep.siga.dao.impl;

import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.PeriodoAcademico;
import com.udep.siga.bean.enumeration.PeriodicidadPlanEstudio;
import com.udep.siga.dao.PeriodoAcademicoDAO;
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
 * @author Wilfredo Atoche
 */
@Repository("periodoAcademicoDAO")
public class IPeriodoAcademicoDAO extends CustomizeJdbcDaoSupport implements PeriodoAcademicoDAO {

    @Override
    public PeriodoAcademico getPA(int id) {
        String sql = "SELECT IDPERIODOACADEMICO, IDPERIODICIDADPLANESTUDIOS, NOMBRE, FECHAINICIO, "
                + "FECHAFIN, VIGENTE, ENPREPARACION, ACTIVO, ORDEN, IDTIPOPERIODOACADEMICO "
                + "FROM PERIODOACADEMICO WHERE IDPERIODOACADEMICO = ?";

        return (PeriodoAcademico) this.getJdbcTemplate().queryForObject(sql,
                new Object[]{id}, UtilRowMapper.getPeriodoAcademicoMapper());
    }

    @Override
    public PeriodoAcademico getPAVigente(PeriodicidadPlanEstudio periodicidadPlanEstudio) {
        String sql = "SELECT TOP 1 IDPERIODOACADEMICO, IDPERIODICIDADPLANESTUDIOS, NOMBRE, FECHAINICIO, "
                + "FECHAFIN, VIGENTE, ENPREPARACION, ACTIVO, ORDEN, IDTIPOPERIODOACADEMICO "
                + "FROM PERIODOACADEMICO WHERE VIGENTE = 1 AND "
                + "IDPERIODICIDADPLANESTUDIOS = :idPeriodicidad ORDER BY FECHAINICIO DESC";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idPeriodicidad", periodicidadPlanEstudio.getId());
        return (PeriodoAcademico) this.getNamedParameterJdbcTemplate().queryForObject(sql,
                params, UtilRowMapper.getPeriodoAcademicoMapper());
    }

    @Override
    public PeriodoAcademico getPAVigenteByAlumnoEstudio(AlumnoEstudio alumnoEstudio) {
        String sql = "SELECT TOP 1 P.IDPERIODOACADEMICO, P.IDPERIODICIDADPLANESTUDIOS, P.NOMBRE, P.FECHAINICIO, "
                + "P.FECHAFIN, P.VIGENTE, P.ENPREPARACION, P.ACTIVO, P.ORDEN, P.IDTIPOPERIODOACADEMICO "
                + "FROM EDICIONESTUDIOPERACAD EEP, PERIODOACADEMICO P "
                + "WHERE EEP.IDPERIODOACADEMICO = P.IDPERIODOACADEMICO AND P.ACTIVO = 1 AND "
                + "P.VIGENTE = 1 AND P.IDPERIODICIDADPLANESTUDIOS = :idPeriodicidad AND "
                + "EEP.IDCAMPUS = :idCampus AND EEP.IDEDICIONESTUDIO = :idEdicionEstudio "
                + "AND EXISTS (SELECT * FROM ALUMNOESTUDIOPERIODOACADEMICO AEPA "
                + "WHERE AEPA.IDPERIODOACADEMICO = P.IDPERIODOACADEMICO "
                + "AND AEPA.IDEDICIONESTUDIO = EEP.IDEDICIONESTUDIO AND AEPA.IDALUMNO = :idAlumno) "
                + "ORDER BY FECHAINICIO DESC,NOMBRE DESC ";
        
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idPeriodicidad", alumnoEstudio.getEdicionestudio().getPeriodicidadPlanEstudio().getId());
        params.addValue("idCampus", alumnoEstudio.getCampus().getId());
        params.addValue("idEdicionEstudio", alumnoEstudio.getEdicionestudio().getId());
        params.addValue("idAlumno", alumnoEstudio.getAlumno().getId());

        List<PeriodoAcademico> periodoList = this.getNamedParameterJdbcTemplate().query(sql,
                params, UtilRowMapper.getPeriodoAcademicoMapper());
        if (periodoList.isEmpty()) {
            return null;
        } else {
            return periodoList.get(0);
        }
    }
    
    @Override
    public PeriodoAcademico getPeriodoAnterior2(int idAlumno, int idEdicionEstudio, int numero,
            int ordenPeriodoActual) {
        //para informacion de cierre de periodo -> numero = 1
        String periodoRegular;
        //if(numero == 1){
            periodoRegular = " p.IDTIPOPERIODOACADEMICO = 1 AND ";
        //}
        String sql = "SELECT TOP 1 A.NOMBRE,A.IDPERIODOACADEMICO,A.IDPERIODICIDADPLANESTUDIOS,"
                + "A.ENPREPARACION,A.VIGENTE, A.ACTIVO,A.ORDEN,A.FECHAINICIO, A.FECHAFIN, "
                + "A.IDTIPOPERIODOACADEMICO FROM (SELECT p.IDPERIODOACADEMICO,p.NOMBRE, "
                + "p.IDPERIODICIDADPLANESTUDIOS,p.ENPREPARACION,p.VIGENTE,p.ACTIVO,p.ORDEN, "
                + "p.FECHAINICIO,p.FECHAFIN,p.IDTIPOPERIODOACADEMICO, ROW_NUMBER() OVER "
                + "(order by p.ORDEN desc) AS ROW FROM ALUMNOESTUDIOPERIODOACADEMICO a, "
                + "PERIODOACADEMICO p WHERE " + periodoRegular +  " a.IDPERIODOACADEMICO = p.IDPERIODOACADEMICO "
                + "and a.idalumno = :idAlumno and a.IDEDICIONESTUDIO = :idEdicionEstudio "
                + "and (a.ANULACICLO = 0 OR a.ANULACICLO is null) AND p.ORDEN <= :ordenPeriodoActual) A WHERE A.ROW > :numero order by ROW ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idEdicionEstudio", idEdicionEstudio);
        params.addValue("idAlumno", idAlumno);
        params.addValue("numero", numero);
        params.addValue("ordenPeriodoActual", ordenPeriodoActual);

        List<PeriodoAcademico> periodoList = this.getNamedParameterJdbcTemplate()
                .query(sql, params, UtilRowMapper.getPeriodoAcademicoMapper());
        if (periodoList.isEmpty()) {
            return null;
        } else {
            return periodoList.get(0);
        }
    }
    
    @Override
    public PeriodoAcademico getPeriodoAnterior(int idAlumno, int idEdicionEstudio, int numero) {
        //para informacion de cierre de periodo -> numero = 1
        String periodoRegular;
        //if(numero == 1){
            periodoRegular = " p.IDTIPOPERIODOACADEMICO IN(1,2) AND ";
        //}
        String sql = "SELECT TOP 2 A.NOMBRE,A.IDPERIODOACADEMICO,A.IDPERIODICIDADPLANESTUDIOS,"
                + "A.ENPREPARACION,A.VIGENTE, A.ACTIVO,A.ORDEN,A.FECHAINICIO, A.FECHAFIN, "
                + "A.IDTIPOPERIODOACADEMICO FROM (SELECT p.IDPERIODOACADEMICO,p.NOMBRE, "
                + "p.IDPERIODICIDADPLANESTUDIOS,p.ENPREPARACION,p.VIGENTE,p.ACTIVO,p.ORDEN, "
                + "p.FECHAINICIO,p.FECHAFIN,p.IDTIPOPERIODOACADEMICO, ROW_NUMBER() OVER "
                + "(order by p.ORDEN desc) AS ROW FROM ALUMNOESTUDIOPERIODOACADEMICO a, "
                + "PERIODOACADEMICO p WHERE " + periodoRegular +  " a.IDPERIODOACADEMICO = p.IDPERIODOACADEMICO "
                + "and a.idalumno = :idAlumno and a.IDEDICIONESTUDIO = :idEdicionEstudio "
                + "and (a.ANULACICLO = 0 OR a.ANULACICLO is null) ) A WHERE A.ROW > :numero order by ROW ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idEdicionEstudio", idEdicionEstudio);
        params.addValue("idAlumno", idAlumno);
        params.addValue("numero", numero);

        List<PeriodoAcademico> periodoList = this.getNamedParameterJdbcTemplate()
                .query(sql, params, UtilRowMapper.getPeriodoAcademicoMapper());
        if (periodoList.isEmpty()) {
            return null;
        } else {
            if(periodoList.get(0).getTipoPeriodoAcademico().getId()==1)
            {
                return periodoList.get(0);
            }
            else 
            {
                return periodoList.get(1);
            }
        }
    }

    public String getBiPeriodoAnterior(int idAlumno, int idEdicionEstudio, int numero, int ordenPeriodoActual) {
        //para informacion de biPeriodo en periodo actual -> numero = 0
        //para informacion de cierre de periodo -> numero = 1
        String periodoRegular;
        //if(numero == 1){
            periodoRegular = " p.IDTIPOPERIODOACADEMICO = 1 AND ";
        //}
        String biPeriodo;
        PeriodoAcademico periodoAnt = getPeriodoAnterior(idAlumno, idEdicionEstudio, numero);
        if (periodoAnt != null) {
            biPeriodo = "(" + periodoAnt.getNombre();
            String sql = "SELECT TOP 1 P.NOMBRE FROM ALUMNOESTUDIOPERIODOACADEMICO AEP, PERIODOACADEMICO P "
                    + "WHERE " + periodoRegular + " P.IDPERIODOACADEMICO = AEP.IDPERIODOACADEMICO  AND AEP.IDALUMNO = :idAlumno "
                    + "AND AEP.IDEDICIONESTUDIO = :idEdicionEstudio AND EXISTS "
                    + "(SELECT AEP2.IDALUMNO,AEP2.IDEDICIONESTUDIO,AEP2.IDPERIODOACADEMICO "
                    + "FROM ALUMNOESTUDIOPERIODOACADEMICO AEP2 "
                    + "WHERE AEP2.IDALUMNO = AEP.IDALUMNO AND AEP2.IDEDICIONESTUDIO = AEP.IDEDICIONESTUDIO "
                    + "AND AEP2.IDPERIODOACADEMICO = AEP.IDPERIODOACADEMICO "
                    + "AND (AEP2.ANULACICLO IS NULL OR AEP2.ANULACICLO = 0)) AND P.ORDEN < :orden "
                    + "ORDER BY P.ORDEN DESC ";

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("idEdicionEstudio", idEdicionEstudio);
            params.addValue("idAlumno", idAlumno);
            params.addValue("orden", periodoAnt.getOrden());
            
            List<String> biPeriodoList = this.getNamedParameterJdbcTemplate()
                    .queryForList(sql, params, String.class);

            if (biPeriodoList.isEmpty()) {
                return biPeriodo + ")";
            } else {
                return biPeriodo + "-" + biPeriodoList.get(0) + ")";
            }
        } else {
            return "(-)";
        }
    }

    public List<Map<String, Object>> getPeriodosAcademicosAlumnoList(AlumnoEstudio alumnoEstudio) {
        String sql = "SELECT P.NOMBRE, AEP.INDICEACUMULADO "
                + "FROM ALUMNOESTUDIOPERIODOACADEMICO AEP, PERIODOACADEMICO P "
                + "WHERE AEP.IDALUMNO = :idAlumno AND AEP.IDEDICIONESTUDIO = :idEdicionEstudio "
                + "AND P.IDPERIODOACADEMICO = AEP.IDPERIODOACADEMICO AND EXISTS "
                + "(SELECT AEP2.IDALUMNO, AEP2.IDEDICIONESTUDIO, AEP2.IDPERIODOACADEMICO "
                + "FROM ALUMNOESTUDIOPERIODOACADEMICO AEP2 WHERE AEP2.IDALUMNO = AEP.IDALUMNO "
                + "AND AEP2.IDEDICIONESTUDIO = AEP.IDEDICIONESTUDIO "
                + "AND AEP2.IDPERIODOACADEMICO = AEP.IDPERIODOACADEMICO "
                + "AND AEP2.ANULACICLO IS NULL OR AEP2.ANULACICLO = 0)  ";
                

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idEdicionEstudio", alumnoEstudio.getEdicionestudio().getId());
        params.addValue("idAlumno", alumnoEstudio.getAlumno().getId());
        if (alumnoEstudio.getPeriodoAcademicoVigente() != null) {
            sql += "AND P.ORDEN < :orden ";
            params.addValue("orden", alumnoEstudio.getPeriodoAcademicoVigente().getOrden());
        } else {
            sql += "AND P.ORDEN <= :orden ";
            PeriodoAcademico periodoAcademico = this.getUltimoPAByAlumnoEstudioInactivo(alumnoEstudio);
            params.addValue("orden", periodoAcademico.getOrden());
        }
        sql += "ORDER BY P.ORDEN ASC";
        
        List<Map<String, Object>> datos = this.getNamedParameterJdbcTemplate().query(
                sql, params, new RowMapper() {
            public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("periodo", rs.getString("NOMBRE"));
                item.put("indice", rs.getFloat("INDICEACUMULADO"));
                return item;
            }
        });

        return datos;
    }

    @Override
    public List<Map<String, Object>> getPeriodoAcademicoByCentroAcademicoList(int idCentroAcademico) {
        String sql = "SELECT PA.IDPERIODOACADEMICO, PA.NOMBRE FROM PERIODOACADEMICO PA "
                + "WHERE PA.ACTIVO = 1 AND PA.ENPREPARACION = 0 AND OFERTAVISIBLE=1 AND EXISTS(SELECT E.IDESTUDIO FROM ESTUDIO E, EDICIONESTUDIO EE "
                + "WHERE E.IDESTUDIO = EE.IDESTUDIO AND EE.IDPERIODICIDADPLANESTUDIOS = PA.IDPERIODICIDADPLANESTUDIOS "
                + "AND E.IDCENTROACADEMICO = ?) ORDER BY PA.ORDEN DESC";

        List<Map<String, Object>> list = this.getJdbcTemplate().query(
                sql, new Object[]{idCentroAcademico}, new RowMapper() {
            public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("id", rs.getInt("IDPERIODOACADEMICO"));
                item.put("nombre", rs.getString("NOMBRE"));
                return item;
            }
        });
        return list;
    }

    @Override
    public boolean anuloCicloByAlumnoEstudioPeriodoAcademico(int idAlumno, int idEstudio, int idPeriodoAcademico) {
        String sql = "SELECT A.ANULACICLO FROM ALUMNOESTUDIOPERIODOACADEMICO A "
                + "WHERE A.IDALUMNO = ? AND A.IDEDICIONESTUDIO = ? AND A.IDPERIODOACADEMICO = ?";
        
        List<Boolean> anulaCiclo = this.getJdbcTemplate().queryForList(sql, 
                new Object[]{idAlumno, idEstudio, idPeriodoAcademico}, 
                Boolean.class);
        
        if(anulaCiclo.isEmpty()){
            return false;
        }
        return anulaCiclo.get(0);
    }

    @Override
    public PeriodoAcademico getUltimoPAByAlumnoEstudioInactivo(AlumnoEstudio alumnoEstudio) {
        String sql = "SELECT TOP 1 P.IDPERIODOACADEMICO, P.IDPERIODICIDADPLANESTUDIOS, P.NOMBRE, P.FECHAINICIO, "
                + "P.FECHAFIN, P.VIGENTE, P.ENPREPARACION, P.ACTIVO, P.ORDEN, P.IDTIPOPERIODOACADEMICO "
                + "FROM ALUMNOESTUDIOPERIODOACADEMICO AEPA, PERIODOACADEMICO P "
                + "WHERE AEPA.IDPERIODOACADEMICO = P.IDPERIODOACADEMICO AND AEPA.ANULACICLO != 1 AND "
                + "AEPA.IDALUMNO = ? AND AEPA.IDEDICIONESTUDIO = ? ORDER BY P.ORDEN DESC";
        List<PeriodoAcademico> periodoList = this.getJdbcTemplate()
                .query(sql, new Object[]{alumnoEstudio.getAlumno().getId(),
                alumnoEstudio.getEdicionestudio().getId()}, UtilRowMapper.getPeriodoAcademicoMapper());
        if (periodoList.isEmpty()) {
            return null;
        } else {
            return periodoList.get(0);
        }
    }    
    }
