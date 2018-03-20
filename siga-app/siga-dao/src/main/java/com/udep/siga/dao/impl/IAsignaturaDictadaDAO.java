package com.udep.siga.dao.impl;

import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.AlumnoEstudioPeriodoAcademico;
import com.udep.siga.bean.Asignatura;
import com.udep.siga.bean.AsignaturaDictada;
import com.udep.siga.bean.AsignaturaHistorial;
import com.udep.siga.bean.Horario;
import com.udep.siga.bean.Nota;
import com.udep.siga.bean.Persona;
import com.udep.siga.bean.PracticaProgramada;
import com.udep.siga.dao.AsignaturaDictadaDAO;
import com.udep.siga.dao.EvaluacionDAO;
import com.udep.siga.dao.util.CustomizeJdbcDaoSupport;
import com.udep.siga.dao.util.UtilRowMapper;
import com.udep.siga.util.BDConstants;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Wilfredo Atoche
 */
@Repository("asignaturaDictadaDAO")
public class IAsignaturaDictadaDAO extends CustomizeJdbcDaoSupport implements AsignaturaDictadaDAO {

    @Autowired
    private EvaluacionDAO evaluacionDAO;

    @Override
    public List<AsignaturaDictada> getAsignaturaDictadaList(AlumnoEstudio alumnoEstudio) {  
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.getJdbcTemplate())
                .withProcedureName(BDConstants.SP_ASIGNATURASDICTADAS_BYALUMNO_PERIODO_EE)
                .returningResultSet("resulset", UtilRowMapper.getAsignaturaDictadaMapper());
        
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idPeriodoAcademico", alumnoEstudio.getPeriodoAcademicoVigente().getId());
        params.addValue("idAlumno", alumnoEstudio.getAlumno().getId());
        params.addValue("idEdicionEstudio", alumnoEstudio.getEdicionestudio().getId());
        
        List<AsignaturaDictada> list = (List<AsignaturaDictada>) simpleJdbcCall.execute(params).get("resulset");

        for (AsignaturaDictada asignatura : list) {
            if (asignatura.getPromedio().getId() != 0) {
                asignatura.setPromedio(
                        this.getPromedioAsignaturaDictadaByAlumno(asignatura.getId(),
                        asignatura.getAsignaturaSeccion().getIdSeccion(), alumnoEstudio.getAlumno().getId()));
            }
        }

        return list;
    }
    
    @Override
    public List<AsignaturaDictada> getAsignaturaDictadaPosgradoList(AlumnoEstudio alumnoEstudio) {        
        String sql = "SELECT AD.IDASIGNATURADICTADA, AD.SIGLA, AD.NOMBRE, "
                + "DBO.GETCREDITOSASIGNATURADICTADA(M.IDALUMNO,M.IDEDICIONESTUDIO,AD.IDASIGNATURADICTADA) AS 'CREDITOS', ASEC.IDSECCION, "
                + "ASEC.NOMBRESECCION, AD.IDPERIODOACADEMICO, AD.IDCAMPUS, AD.IDSECCIONACADEMICA, "
                + "SA.NOMBRE 'NOMBRESA',ISNULL(M.IDNOTA, 0) AS IDNOTA,M.RETIROCURSO, "
                + "ISNULL(ASEC.DESCRIPCION, '') 'DESCRIPCION', ASEC.VERPROMEDIO "
                + "FROM MATRICULA M, ASIGNATURASECCION ASEC, ASIGNATURADICTADA AD "
                + "LEFT OUTER JOIN SECCIONACADEMICA SA ON AD.IDSECCIONACADEMICA = SA.IDSECCIONACADEMICA, PERIODOACADEMICO P "
                + "WHERE M.IDASIGNATURADICTADA = ASEC.IDASIGNATURADICTADA AND M.IDSECCION = ASEC.IDSECCION AND "
                + "AD.IDASIGNATURADICTADA = ASEC.IDASIGNATURADICTADA AND P.IDPERIODOACADEMICO = AD.IDPERIODOACADEMICO AND P.VIGENTE = 1 "
                + "AND NOT EXISTS(SELECT AH.IDASIGNATURADICTADA FROM ALUMNOHISTORIAL AH WHERE AH.IDALUMNO = M.IDALUMNO AND "
                + "AH.IDEDICIONESTUDIO = M.IDEDICIONESTUDIO AND AH.IDASIGNATURADICTADA = M.IDASIGNATURADICTADA) AND "
                + "M.IDALUMNO = :idAlumno AND M.IDEDICIONESTUDIO = :idEdicionEstudio "
                + "ORDER BY AD.SIGLA";
        
        MapSqlParameterSource params = new MapSqlParameterSource();        
        params.addValue("idAlumno", alumnoEstudio.getAlumno().getId());
        params.addValue("idEdicionEstudio", alumnoEstudio.getEdicionestudio().getId());
        
        List<AsignaturaDictada> list = this.getNamedParameterJdbcTemplate().query(sql,
                params, UtilRowMapper.getAsignaturaDictadaMapper());

        for (AsignaturaDictada asignatura : list) {
            if (asignatura.getPromedio().getId() != 0) {
                asignatura.setPromedio(
                        this.getPromedioAsignaturaDictadaByAlumno(asignatura.getId(),
                        asignatura.getAsignaturaSeccion().getIdSeccion(), alumnoEstudio.getAlumno().getId()));
            }
        }

        return list;
    }

    public List<AsignaturaDictada> getAsignaturaDictadaList(int idAlumno) {
        String sql = "SELECT AD.IDASIGNATURADICTADA, AD.SIGLA, AD.NOMBRE, "
                + "DBO.GETCREDITOSASIGNATURADICTADA(M.IDALUMNO,M.IDEDICIONESTUDIO,AD.IDASIGNATURADICTADA)  AS 'CREDITOS', ASEC.IDSECCION, "
                + "ASEC.NOMBRESECCION, AD.IDPERIODOACADEMICO, AD.IDCAMPUS, AD.IDSECCIONACADEMICA, "
                + "SA.NOMBRE 'NOMBRESA', ISNULL(M.IDNOTA, 0) AS IDNOTA, M.RETIROCURSO, "
                + "ISNULL(ASEC.DESCRIPCION, '') 'DESCRIPCION', ASEC.VERPROMEDIO "
                + "FROM MATRICULA M, ASIGNATURASECCION ASEC, ASIGNATURADICTADA AD "
                + "LEFT OUTER JOIN SECCIONACADEMICA SA ON AD.IDSECCIONACADEMICA = SA.IDSECCIONACADEMICA "
                + "WHERE M.IDASIGNATURADICTADA = ASEC.IDASIGNATURADICTADA AND M.IDSECCION = ASEC.IDSECCION AND "
                + "AD.IDASIGNATURADICTADA = ASEC.IDASIGNATURADICTADA AND EXISTS (SELECT P.IDPERIODOACADEMICO "
                + "FROM PERIODOACADEMICO P WHERE P.VIGENTE = 1 AND P.IDPERIODOACADEMICO = AD.IDPERIODOACADEMICO) AND "
                + "M.IDALUMNO = :idAlumno ORDER BY AD.SIGLA";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", idAlumno);
        List<AsignaturaDictada> list = this.getNamedParameterJdbcTemplate().query(sql,
                params, UtilRowMapper.getAsignaturaDictadaMapper());

        for (AsignaturaDictada asignatura : list) {
            if (asignatura.isVerPromedio() && asignatura.getPromedio().getId() != 0) {
                asignatura.setPromedio(
                        this.getPromedioAsignaturaDictadaByAlumno(asignatura.getId(),
                        asignatura.getAsignaturaSeccion().getIdSeccion(), idAlumno));
            }
        }

        return list;
    }

    @Override
    public List<AsignaturaDictada> getAsignaturaDictadaProfesorList(int idProfesor) {
        String sql = "SELECT DISTINCT AD.IDASIGNATURADICTADA, AD.SIGLA, AD.NOMBRE, AD.CREDITOS, ASEC.IDSECCION, "
                + "ASEC.NOMBRESECCION, AD.IDPERIODOACADEMICO, AD.IDCAMPUS, AD.IDSECCIONACADEMICA, "
                + "SA.NOMBRE 'NOMBRESA', ISNULL( ASEC.DESCRIPCION, '') 'DESCRIPCION' "
                + "FROM ASIGNATURASECCION ASEC, ASIGNATURADICTADA AD LEFT OUTER JOIN SECCIONACADEMICA SA "
                + "ON AD.IDSECCIONACADEMICA = SA.IDSECCIONACADEMICA, PROFESORDICTA PD "
                + "WHERE AD.IDASIGNATURADICTADA = ASEC.IDASIGNATURADICTADA AND "
                + "AD.IDASIGNATURADICTADA = PD.IDASIGNATURADICTADA "
                + "AND ASEC.IDSECCION = PD.IDSECCION AND EXISTS (SELECT P.IDPERIODOACADEMICO "
                + "FROM PERIODOACADEMICO P WHERE P.VIGENTE = 1 AND P.OFERTAVISIBLE=1 AND P.IDPERIODOACADEMICO = AD.IDPERIODOACADEMICO) "
                + "AND PD.IDPROFESOR = ? ";
        List<AsignaturaDictada> list = this.getJdbcTemplate().query(sql,
                new Object[]{idProfesor}, UtilRowMapper.getAsignaturaDictadaIndepMapper());
        return list;
    }

    public AsignaturaDictada getAsignaturaDictada(int idAsignaturaDictada, int idSeccion) {
        String sql = "SELECT DISTINCT AD.IDASIGNATURADICTADA, AD.SIGLA, AD.NOMBRE, AD.CREDITOS, ASEC.IDSECCION, "
                + "ASEC.NOMBRESECCION, AD.IDPERIODOACADEMICO, AD.IDCAMPUS, AD.IDSECCIONACADEMICA, "
                + "SA.NOMBRE 'NOMBRESA', ISNULL( ASEC.DESCRIPCION, '') 'DESCRIPCION' "
                + "FROM ASIGNATURASECCION ASEC, ASIGNATURADICTADA AD LEFT OUTER JOIN SECCIONACADEMICA SA "
                + "ON AD.IDSECCIONACADEMICA = SA.IDSECCIONACADEMICA "
                + "WHERE AD.IDASIGNATURADICTADA = ASEC.IDASIGNATURADICTADA AND  "
                + "AD.IDASIGNATURADICTADA = :idAsignaturaDictada "
                + "AND ASEC.IDSECCION = :idSeccion";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAsignaturaDictada", idAsignaturaDictada);
        params.addValue("idSeccion", idSeccion);

        return (AsignaturaDictada) this.getNamedParameterJdbcTemplate().queryForObject(sql,
                params, UtilRowMapper.getAsignaturaDictadaIndepMapper());
    }

    public Nota getPromedioAsignaturaDictadaByAlumno(int idAsignaturaDictada, int idSeccion, int idAlumno) {
        String sql = "SELECT TOP 1 ISNULL(M.IDNOTA, 0) AS IDNOTA,CASE WHEN E.IDSISTEMANOTAS = 1 "
                + "THEN 0 ELSE 1 END 'LITERAL', ASEC.VERPROMEDIO "
                + "FROM MATRICULA M, ASIGNATURASECCION ASEC,EDICIONESTUDIONOTA E "
                + "WHERE M.IDASIGNATURADICTADA = ASEC.IDASIGNATURADICTADA AND M.IDEDICIONESTUDIO = E.IDEDICIONESTUDIO "
                + "AND M.IDSECCION = ASEC.IDSECCION AND ASEC.IDASIGNATURADICTADA = :idAsignaturaDictada "
                + "AND ASEC.IDSECCION = :idSeccion AND M.IDALUMNO = :idAlumno ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAsignaturaDictada", idAsignaturaDictada);
        params.addValue("idSeccion", idSeccion);
        params.addValue("idAlumno", idAlumno);

        List<Nota> list = this.getNamedParameterJdbcTemplate().query(
                sql, params, new RowMapper() {
            public Nota mapRow(ResultSet rs, int rowNum) throws SQLException {
                Nota item = new Nota();
                item.setId(rs.getInt("IDNOTA"));
                item.setLiteral(rs.getBoolean("LITERAL"));
                item.setVisible(rs.getBoolean("VERPROMEDIO"));
                return item;
            }
        });

        if (!list.isEmpty()) {
            Nota nota = evaluacionDAO.getNota(list.get(0).getId());
            if (nota != null) {
                if (nota.getId() != 0) {
                    nota.setLiteral(list.get(0).isLiteral());
                    nota.setVisible(list.get(0).isVisible());
                    if (!nota.isLiteral()) {
                        if (nota.isVisible()) {
                            return nota;
                        } else {
                            return null;
                        }
                    }
                    return nota;
                }
            }
        }
        return null;
    }

    public String getNotaLiteral(Nota nota, int idAsignaturaDictada) {
        if (nota == null) {
            return "";
        }
        String sql = "SELECT NL.DESCRIPCION FROM NOTALITERAL NL WHERE NL.NOTAINICIO >=:nota AND NL.NOTAFIN<=:nota "
                + "AND EXISTS (SELECT E.IDSISTEMANOTAS, E.IDEDICIONESTUDIO FROM EDICIONESTUDIONOTA E "
                + "WHERE E.IDSISTEMANOTAS = NL.IDSISTEMANOTAS AND E.IDEDICIONESTUDIO = NL.IDEDICIONESTUDIO "
                + "AND E.IDEDICIONESTUDIO IN (SELECT IDEDICIONESTUDIO FROM PLANESTUDIOS WHERE IDPLANESTUDIOS "
                + "IN (SELECT IDPLANESTUDIOS FROM PLANESTUDIOSASIGNATURAPERACAD "
                + "WHERE IDASIGNATURADICTADA = :idAsignaturaDictada))) and VIGENTE=1";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAsignaturaDictada", idAsignaturaDictada);
        params.addValue("nota", nota.getValor());
        List<String> list = this.getNamedParameterJdbcTemplate().queryForList(sql,
                params, String.class);

        if (!list.isEmpty()) {
            return list.get(0);
        }
        return "";
    }

    public int countAlumnosAsignaturaDictada(AsignaturaDictada asignaturaDictada) {
        String sql = "SELECT COUNT(*) FROM MATRICULA "
                + "WHERE IDASIGNATURADICTADA = ? AND IDSECCION = ?";

        return this.getJdbcTemplate().queryForObject(sql,
                new Object[]{asignaturaDictada.getId(), asignaturaDictada.getAsignaturaSeccion().getIdSeccion()},
                Integer.class);
    }

    public List<Persona> findProfesores(AsignaturaDictada asignaturaDictada) {
        String sql = "SELECT P.IDPERSONA, P.IDTIPODOCIDENTIDAD, P.IDSEXO, P.NOMBRES, "
                + "P.APELLIDOPATERNO, P.APELLIDOMATERNO, P.DNI, P.FOTO, P.ASESOR "
                + "FROM PERSONA P, PROFESORDICTA PD WHERE P.IDPERSONA = PD.IDPROFESOR "
                + "AND PD.IDASIGNATURADICTADA = ? AND PD.IDSECCION = ? "
                +"EXCEPT "
                +"SELECT P.IDPERSONA, P.IDTIPODOCIDENTIDAD, P.IDSEXO, P.NOMBRES, "
                +"P.APELLIDOPATERNO, P.APELLIDOMATERNO, P.DNI, P.FOTO, P.ASESOR "
                +"FROM PERSONA P,ASISTENTEPROFESOR S, PROFESORDICTA PD WHERE P.IDPERSONA = PD.IDPROFESOR "
                +"AND P.IDPERSONA = S.IDPERSONAASISTENTE "
                +"AND PD.IDASIGNATURADICTADA = ? AND PD.IDSECCION= ? ";
                

        return (List<Persona>) this.getJdbcTemplate().query(sql,
                new Object[]{asignaturaDictada.getId(), asignaturaDictada.getAsignaturaSeccion().getIdSeccion(),asignaturaDictada.getId(), asignaturaDictada.getAsignaturaSeccion().getIdSeccion()},
                UtilRowMapper.getPersonaMapper());
    }

    public Map<String, Object> getTipoPromedioAsignaturaDictada(int idAsignatura, int idSeccion) {
        String sql = "SELECT PROMEDIOESTANDAR, FORMULAPROMEDIO FROM ASIGNATURASECCION  "
                + "WHERE IDASIGNATURADICTADA = :idAsignatura AND IDSECCION = :idSeccion";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAsignatura", idAsignatura);
        params.addValue("idSeccion", idSeccion);

        Map<String, Object> item = (Map<String, Object>) this.getNamedParameterJdbcTemplate().queryForObject(
                sql, params, new RowMapper() {
            public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("promedioEstandar", rs.getBoolean("PROMEDIOESTANDAR"));
                String formula = rs.getString("FORMULAPROMEDIO");
                item.put("formula", "");
                if (formula != null) {
                    item.put("formula", formula);
                }
                return item;
            }
        });

        return item;
    }

    /*
     * Horario Pregrado
     */
    public List<Horario> getHorarioByAsignaturaSeccion(int idAsignaturaDictada, int idSeccion) {
        String sql = "SELECT H.IDASIGNATURADICTADA, H.IDSECCION, H.IDBLOQUEHORARIO, BH.DESCRIPCION, BH.ORDEN, ";
        sql = sql.concat("H.IDDIA, H.IDTIPOCLASE, H.IDAULA, A.NOMBRE, A.CAPACIDADREAL, A.CAPACIDADAFORO ");
        sql = sql.concat("FROM HORARIO H, BLOQUEHORARIO BH, AULA A ");
        sql = sql.concat("WHERE H.IDASIGNATURADICTADA = :idAsignaturaDictada AND H.IDSECCION = :idSeccion AND ");
        sql = sql.concat("H.IDBLOQUEHORARIO = BH.IDBLOQUEHORARIO AND A.IDAULA = H.IDAULA ");
        sql = sql.concat("ORDER BY H.IDDIA ASC, BH.ORDEN ASC");

        Map params = new HashMap();
        params.put("idAsignaturaDictada", idAsignaturaDictada);
        params.put("idSeccion", idSeccion);

        return this.getNamedParameterJdbcTemplate().query(sql, params, UtilRowMapper.getHorarioMapper());
    }

    public List<PracticaProgramada> getPracticaProgramadaByAsignaturaSeccion(int idAsignaturaDictada, int idSeccion) {
        String sql = "SELECT PP.IDASIGNATURADICTADA, PP.IDSECCION, PP.IDBLOQUEHORARIO, BH.DESCRIPCION, "
                + "BH.ORDEN, PP.IDDIA, GP.IDGRUPOPRACTICAS, GP.NOMBRE, GP.COMENTARIO, GP.IDCAMPUS "
                + "FROM PRACTICAPROGRAMADA PP, BLOQUEHORARIO BH, GRUPOPRACTICA GP "
                + "WHERE PP.IDASIGNATURADICTADA = :idAsignaturaDictada AND PP.IDSECCION = :idSeccion AND "
                + "PP.IDBLOQUEHORARIO = BH.IDBLOQUEHORARIO AND GP.IDGRUPOPRACTICAS = PP.IDGRUPOPRACTICAS "
                + "ORDER BY PP.IDDIA ASC, BH.ORDEN ASC";

        Map params = new HashMap();
        params.put("idAsignaturaDictada", idAsignaturaDictada);
        params.put("idSeccion", idSeccion);

        return this.getNamedParameterJdbcTemplate().query(sql, params, UtilRowMapper.getPracticaProgramadaMapper());

    }

    /*
     * Horario Posgrado
     */
    @Override
    public List<Date> getFechasHorarioPosgrado(int idAlumno, boolean horarioRegular) {
        String sql = "SELECT H.FECHA FROM HORARIOPOSTPRACTICAS H JOIN ASIGNATURADICTADA AD ON "
                + "H.IDASIGNATURADICTADA = AD.IDASIGNATURADICTADA JOIN MATRICULA MAT ON "
                + "AD.IDASIGNATURADICTADA = MAT.IDASIGNATURADICTADA JOIN ESTUDIO E ON "
                + "MAT.IDEDICIONESTUDIO = E.IDESTUDIO WHERE EXISTS (SELECT P.IDPERIODOACADEMICO "
                + "FROM PERIODOACADEMICO P WHERE P.VIGENTE = 1 AND P.IDPERIODOACADEMICO = AD.IDPERIODOACADEMICO) "
                + "AND MAT.IDALUMNO = ? AND E.IDTIPOESTUDIO IN (2,8) AND H.FECHA >= GETDATE() "
                + "GROUP BY H.FECHA ORDER BY H.FECHA";

        if (horarioRegular) {
            sql = "SELECT H.FECHA FROM HORARIOPOST H JOIN ASIGNATURADICTADA AD ON "
                    + "H.IDASIGNATURADICTADA = AD.IDASIGNATURADICTADA JOIN MATRICULA MAT ON "
                    + "AD.IDASIGNATURADICTADA = MAT.IDASIGNATURADICTADA JOIN ESTUDIO E ON "
                    + "MAT.IDEDICIONESTUDIO = E.IDESTUDIO WHERE MAT.IDALUMNO = ? "
                    + "AND E.IDTIPOESTUDIO IN (2,8) AND EXISTS (SELECT P.IDPERIODOACADEMICO "
                    + "FROM PERIODOACADEMICO P WHERE P.VIGENTE = 1 AND "
                    + "P.IDPERIODOACADEMICO = AD.IDPERIODOACADEMICO) AND H.FECHA >= convert(datetime,CONVERT(varchar(10), GETDATE(), 102),102) "
                   // + " GETDATE() "
                    + "GROUP BY H.FECHA ORDER BY H.FECHA";
        }

        List<Date> list = this.getJdbcTemplate().query(
                sql, new Object[]{idAlumno}, UtilRowMapper.getDateMapper());
        return list;
    }

    @Override
    public List<Horario> getHorarioAsignaturaPosgradoByFecha(int idAlumno, Date fecha, boolean horarioRegular) {
        String sql = "SELECT DISTINCT H.IDSECCION, H.IDASIGNATURADICTADA, H.IDBLOQUEHORARIO, "
                + "BH.DESCRIPCION, BH.ORDEN, H.IDDIA, H.IDTIPOCLASE, H.IDAULA, A.NOMBRE, A.CAPACIDADREAL, "
                + "A.CAPACIDADAFORO FROM HORARIOPOSTPRACTICAS H JOIN ASIGNATURADICTADA AD ON "
                + "H.IDASIGNATURADICTADA = AD.IDASIGNATURADICTADA JOIN MATRICULA MAT ON "
                + "AD.IDASIGNATURADICTADA = MAT.IDASIGNATURADICTADA JOIN ESTUDIO E ON "
                + "MAT.IDEDICIONESTUDIO = E.IDESTUDIO LEFT OUTER JOIN AULA A ON H.IDAULA = A.IDAULA, BLOQUEHORARIO BH "
                + "WHERE H.IDBLOQUEHORARIO = BH.IDBLOQUEHORARIO AND MAT.IDALUMNO = ? AND EXISTS (SELECT P.IDPERIODOACADEMICO FROM PERIODOACADEMICO P "
                + "WHERE P.VIGENTE = 1 AND P.IDPERIODOACADEMICO = AD.IDPERIODOACADEMICO) AND "
                + "E.IDTIPOESTUDIO IN (2,8) AND H.FECHA  = ?";

        if (horarioRegular) {
            sql = "SELECT DISTINCT H.IDSECCION, H.IDASIGNATURADICTADA, H.IDBLOQUEHORARIO, BH.DESCRIPCION, BH.ORDEN, "
                    + "H.IDDIA, H.IDTIPOCLASE, H.IDAULA, A.NOMBRE, A.CAPACIDADREAL, A.CAPACIDADAFORO "
                    + "FROM HORARIOPOST H JOIN ASIGNATURADICTADA AD ON H.IDASIGNATURADICTADA = AD.IDASIGNATURADICTADA "
                    + "JOIN MATRICULA MAT ON AD.IDASIGNATURADICTADA = MAT.IDASIGNATURADICTADA JOIN ESTUDIO E ON "
                    + "MAT.IDEDICIONESTUDIO = E.IDESTUDIO LEFT OUTER JOIN AULA A ON H.IDAULA = A.IDAULA, BLOQUEHORARIO BH "
                    + "WHERE H.IDBLOQUEHORARIO = BH.IDBLOQUEHORARIO AND MAT.IDALUMNO = ? AND E.IDTIPOESTUDIO IN (2,8) "
                    + "AND EXISTS (SELECT P.IDPERIODOACADEMICO FROM PERIODOACADEMICO P WHERE P.VIGENTE = 1 AND "
                    + "P.IDPERIODOACADEMICO = AD.IDPERIODOACADEMICO) AND H.FECHA = ?";
        }
        List<Horario> list = this.getJdbcTemplate().query(sql, new Object[]{idAlumno, fecha},
                UtilRowMapper.getHorarioMapper());

        for (Horario horario : list) {
            horario.setAsignaturaDictada(getAsignaturaDictada(
                    horario.getAsignaturaDictada().getId(),
                    horario.getAsignaturaDictada().getAsignaturaSeccion().getIdSeccion()));
        }

        return list;
    }

    /*
     * Historial por Periodo Academico
     */
    public List<AsignaturaHistorial> getAsignaturaDictadaHistorial(AlumnoEstudioPeriodoAcademico aepa) {
        String sql = "SELECT A.IDASIGNATURA, A.NOMBRE, A.CODIGO,A.CREDITOS, PEA.IDTIPOASIGNATURA, "
                + "H.IDNOTA, H.RETIROCURSO,PE.NOMBRE 'PLAN', E.SIGLA 'ESTUDIO', AEPA.ANULACICLO "
                + "FROM ALUMNOHISTORIAL H, PERIODOACADEMICO P, PLANESTUDIOSASIGNATURA PEA, "
                + "ASIGNATURA A,PLANESTUDIOS PE,EDICIONESTUDIO EE, ESTUDIO E, "
                + "ALUMNOESTUDIOPERIODOACADEMICO AEPA WHERE H.IDPLANESTUDIOS = PEA.IDPLANESTUDIOS AND "
                + "H.IDASIGNATURA = PEA.IDASIGNATURA AND PEA.IDPLANESTUDIOS = PE.IDPLANESTUDIOS "
                + "AND PEA.IDASIGNATURA = A.IDASIGNATURA AND H.IDPERIODOACADEMICO = P.IDPERIODOACADEMICO AND "
                + "H.IDALUMNO = AEPA.IDALUMNO AND H.IDEDICIONESTUDIO = AEPA.IDEDICIONESTUDIO AND "
                + "H.IDPERIODOACADEMICO = AEPA.IDPERIODOACADEMICO AND AEPA.IDEDICIONESTUDIO = EE.IDEDICIONESTUDIO "
                + "AND EE.IDESTUDIO = E.IDESTUDIO AND H.IDALUMNO = :idAlumno "
                + "AND H.IDEDICIONESTUDIO = :idEdicionEstudio AND H.IDPERIODOACADEMICO = :idPeriodo "
                + "AND ((((H.CLC = 0 AND P.ORDEN >= 102) OR P.ORDEN < 102) "
                + "AND H.IDEDICIONESTUDIO IN (4,5,6,7)) OR H.IDEDICIONESTUDIO NOT IN (4,5,6,7))";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", aepa.getAlumno().getId());
        params.addValue("idEdicionEstudio", aepa.getEdicionestudio().getId());
        params.addValue("idPeriodo", aepa.getPeriodoAcademico().getId());

        List<AsignaturaHistorial> list = this.getNamedParameterJdbcTemplate().query(
                sql, params, UtilRowMapper.getAsignaturaHistorialMapper());
        for (AsignaturaHistorial asignatura : list) {
            if (asignatura.getPromedio().getId() != 0) {
                asignatura.setPromedio(
                        evaluacionDAO.getNota(asignatura.getPromedio().getId()));
            }
        }
        return list;
    }

    public List<AsignaturaDictada> findAsignaturaDictadaByCampusCAcadPeriodoAcademico(int idCampus,
            int idCentroAcademico, int idPeriodoAcademico, String sigla, String nombre) {
        String sql = "SELECT DISTINCT AD.IDASIGNATURADICTADA, AD.SIGLA, AD.NOMBRE, AD.CREDITOS, ASEC.IDSECCION, "
                + "ASEC.NOMBRESECCION, AD.IDPERIODOACADEMICO, AD.IDCAMPUS, AD.IDSECCIONACADEMICA, "
                + "SA.NOMBRE 'NOMBRESA', ISNULL( ASEC.DESCRIPCION, '') 'DESCRIPCION' FROM ASIGNATURASECCION ASEC, "
                + "ASIGNATURADICTADA AD, SECCIONACADEMICA SA WHERE AD.IDASIGNATURADICTADA = ASEC.IDASIGNATURADICTADA "
                + "AND AD.IDSECCIONACADEMICA = SA.IDSECCIONACADEMICA AND AD.IDCAMPUS = :idCampus AND "
                + "EXISTS(SELECT PA1.IDPERIODOACADEMICO FROM PERIODOACADEMICO PA1 WHERE PA1.ENPREPARACION = 0 "
                + "AND PA1.IDPERIODOACADEMICO = AD.IDPERIODOACADEMICO) ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idCampus", idCampus);

        if (idCentroAcademico != 0) {
            sql += "AND EXISTS(SELECT PEAPA.IDASIGNATURADICTADA, PEAPA.IDCAMPUS, "
                    + "PEAPA.IDPERIODOACADEMICO FROM PLANESTUDIOSASIGNATURAPERACAD PEAPA, PLANESTUDIOS PEA, "
                    + "EDICIONESTUDIO EE, ESTUDIO E WHERE PEAPA.IDPLANESTUDIOS = PEA.IDPLANESTUDIOS AND "
                    + "PEA.IDEDICIONESTUDIO = EE.IDEDICIONESTUDIO AND EE.IDESTUDIO = E.IDESTUDIO AND "
                    + "PEAPA.IDASIGNATURADICTADA = AD.IDASIGNATURADICTADA AND PEAPA.IDCAMPUS = AD.IDCAMPUS AND "
                    + "PEAPA.IDPERIODOACADEMICO = AD.IDPERIODOACADEMICO AND E.IDCENTROACADEMICO = :idCentroAcademico) ";
            params.addValue("idCentroAcademico", idCentroAcademico);
        }

        if (idPeriodoAcademico != 0) {
            sql += "AND AD.IDPERIODOACADEMICO = :idPeriodoAcademico ";
            params.addValue("idPeriodoAcademico", idPeriodoAcademico);
        }

        if (!sigla.isEmpty()) {
            sql += "AND AD.SIGLA = :sigla ";
            params.addValue("sigla", sigla);
        }

        if (!nombre.isEmpty()) {
            sql += "AND AD.NOMBRE LIKE :nombre ";
            params.addValue("nombre", "%" + nombre + "%");
        }

        sql += "ORDER BY AD.NOMBRE";

        return this.getNamedParameterJdbcTemplate().query(sql,
                params, UtilRowMapper.getAsignaturaDictadaIndepMapper());
    }

    /*
     * Nombre del programa de una Asignatura Dictada
     */
    public String getNombreDeEstudio(AsignaturaDictada asignaturaDictada) {
        String sql = "SELECT DISTINCT TOP 1 E.NOMBRE FROM PLANESTUDIOSASIGNATURAPERACAD PEAPA, PLANESTUDIOS PEA, "
                + "EDICIONESTUDIO EE, ESTUDIO E WHERE PEAPA.IDPLANESTUDIOS = PEA.IDPLANESTUDIOS AND "
                + "PEA.IDEDICIONESTUDIO = EE.IDEDICIONESTUDIO AND EE.IDESTUDIO = E.IDESTUDIO AND "
                + "PEAPA.IDASIGNATURADICTADA = ? AND PEAPA.IDCAMPUS = ? AND PEAPA.IDPERIODOACADEMICO = ? ";
        return this.getJdbcTemplate().queryForObject(sql,
                new Object[]{asignaturaDictada.getId(), asignaturaDictada.getCampus().getId(),
            asignaturaDictada.getPeriodoAcademico().getId()}, String.class);
    }

    public Asignatura getAsignaturaById(int id) {
        String sql = "SELECT IDASIGNATURA, NOMBRE, CODIGO, CREDITOS "
                + "FROM ASIGNATURA WHERE IDASIGNATURA = ?";
        Asignatura asignatura = (Asignatura) this.getJdbcTemplate().queryForObject(sql,
                new Object[]{id}, UtilRowMapper.getAsignaturaMapper());

        return asignatura;
    }

    public boolean isAlumnoAsignatura(int idAsignatura, int idSeccion, int idAlumno) {
        String sql = "SELECT ISNULL(COUNT(*),0) FROM MATRICULA WHERE IDALUMNO = ? "
                + "AND IDASIGNATURADICTADA = ? AND IDSECCION = ?";
        List<Integer> count = this.getJdbcTemplate().queryForList(sql,
                new Object[]{idAlumno, idAsignatura, idSeccion}, Integer.class);
        if (!count.isEmpty()) {
            if (count.get(0) > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<String, Boolean> statusNotificacion(int idAlumno, int idEdicionestudio) {
        Map<String, Boolean> result = new HashMap<String, Boolean>();
        result.put("AVISO", Boolean.TRUE);
        result.put("MATERIAL", Boolean.TRUE);
        result.put("EVALUACION", Boolean.TRUE);
        
        String sql = "SELECT COUNT(*) FROM AVISOASIGNATURA A WHERE EXISTS(SELECT M.IDASIGNATURADICTADA "
                + "FROM MATRICULA M INNER JOIN ASIGNATURADICTADA AD ON M.IDASIGNATURADICTADA = AD.IDASIGNATURADICTADA "
                + "INNER JOIN PERIODOACADEMICO P ON AD.IDPERIODOACADEMICO = P.IDPERIODOACADEMICO "
                + "WHERE M.IDALUMNO = ? AND M.IDEDICIONESTUDIO = ? AND P.VIGENTE = 1 "
                + "AND M.IDASIGNATURADICTADA = A.IDASIGNATURADICTADA AND (M.IDSECCION = A.IDSECCION OR A.PUBLICO = 1)) "
                + "AND A.FECHA BETWEEN DATEADD(dd,-2,GETDATE()) AND DATEADD(dd,2,GETDATE())";
        
        Integer count = this.getJdbcTemplate().queryForObject(sql, 
                new Object[]{idAlumno, idEdicionestudio}, Integer.class);
        
        if(count == 0){
            result.put("AVISO", Boolean.FALSE);
        }
        
        sql = "SELECT COUNT(*) FROM MATERIALASIGNATURA M1 WHERE EXISTS(SELECT M.IDASIGNATURADICTADA FROM "
                + "MATRICULA M INNER JOIN ASIGNATURADICTADA AD ON M.IDASIGNATURADICTADA = AD.IDASIGNATURADICTADA "
                + "INNER JOIN PERIODOACADEMICO P ON AD.IDPERIODOACADEMICO = P.IDPERIODOACADEMICO "
                + "WHERE M.IDALUMNO = ? AND M.IDEDICIONESTUDIO = ? AND P.VIGENTE = 1 "
                + "AND M.IDASIGNATURADICTADA = M1.IDASIGNATURADICTADA AND (M.IDSECCION = M1.IDSECCION "
                + "OR M1.PUBLICO = 1)) AND M1.FECHA BETWEEN DATEADD(dd,-2,GETDATE()) AND DATEADD(dd,2,GETDATE())";
        
        count = this.getJdbcTemplate().queryForObject(sql, 
                new Object[]{idAlumno, idEdicionestudio}, Integer.class);
        
        if(count == 0){
            result.put("MATERIAL", Boolean.FALSE);
        }
        
        sql = "SELECT COUNT(*) FROM EVALUACION E WHERE EXISTS(SELECT M.IDASIGNATURADICTADA FROM MATRICULA M "
                + "INNER JOIN ASIGNATURADICTADA AD ON M.IDASIGNATURADICTADA = AD.IDASIGNATURADICTADA "
                + "INNER JOIN PERIODOACADEMICO P ON AD.IDPERIODOACADEMICO = P.IDPERIODOACADEMICO "
                + "WHERE M.IDALUMNO = ? AND M.IDEDICIONESTUDIO = ? AND P.VIGENTE = 1 "
                + "AND M.IDASIGNATURADICTADA = E.IDASIGNATURADICTADA AND M.IDSECCION = E.IDSECCION) "
                + "AND ((E.FECHAENTREGA BETWEEN DATEADD(dd,-2,GETDATE()) AND DATEADD(dd,2,GETDATE()) AND E.IDESTADOEVALUACION = 4) OR "
                + "(E.FECHAENTREGAREC BETWEEN DATEADD(dd,-2,GETDATE()) AND DATEADD(dd,2,GETDATE()) AND E.IDESTADOEVALUACION = 6))";
        
        count = this.getJdbcTemplate().queryForObject(sql, 
                new Object[]{idAlumno, idEdicionestudio}, Integer.class);
        
        if(count == 0){
            result.put("EVALUACION", Boolean.FALSE);
        }
        
        return result;
    }
    
    @Override
    public Map<String, Boolean> statusNotificacion(int idAlumno, int idEdicionestudio, int idAsignaturaDictada,
            int idSeccion) {
        Map<String, Boolean> result = new HashMap<String, Boolean>();
        result.put("AVISO", Boolean.TRUE);
        result.put("MATERIAL", Boolean.TRUE);
        result.put("EVALUACION", Boolean.TRUE);
        
        String sql = "SELECT COUNT(*) FROM AVISOASIGNATURA A WHERE EXISTS(SELECT M.IDASIGNATURADICTADA "
                + "FROM MATRICULA M INNER JOIN ASIGNATURADICTADA AD ON M.IDASIGNATURADICTADA = AD.IDASIGNATURADICTADA "
                + "INNER JOIN PERIODOACADEMICO P ON AD.IDPERIODOACADEMICO = P.IDPERIODOACADEMICO "
                + "WHERE M.IDALUMNO = ? AND M.IDEDICIONESTUDIO = ? AND P.VIGENTE = 1 "
                + "AND M.IDASIGNATURADICTADA = A.IDASIGNATURADICTADA AND (M.IDSECCION = A.IDSECCION OR A.PUBLICO = 1)) "
                + "AND A.IDASIGNATURADICTADA = ? AND A.IDSECCION = ? "
                + "AND A.FECHA BETWEEN DATEADD(dd,-2,GETDATE()) AND DATEADD(dd,2,GETDATE())";
        
        Integer count = this.getJdbcTemplate().queryForObject(sql, 
                new Object[]{idAlumno, idEdicionestudio, idAsignaturaDictada, idSeccion}, Integer.class);
        
        if(count == 0){
            result.put("AVISO", Boolean.FALSE);
        }
        
        sql = "SELECT COUNT(*) FROM MATERIALASIGNATURA M1 WHERE EXISTS(SELECT M.IDASIGNATURADICTADA FROM "
                + "MATRICULA M INNER JOIN ASIGNATURADICTADA AD ON M.IDASIGNATURADICTADA = AD.IDASIGNATURADICTADA "
                + "INNER JOIN PERIODOACADEMICO P ON AD.IDPERIODOACADEMICO = P.IDPERIODOACADEMICO "
                + "WHERE M.IDALUMNO = ? AND M.IDEDICIONESTUDIO = ? AND P.VIGENTE = 1 "
                + "AND M.IDASIGNATURADICTADA = M1.IDASIGNATURADICTADA AND (M.IDSECCION = M1.IDSECCION OR M1.PUBLICO = 1)) "
                + "AND M1.IDASIGNATURADICTADA = ? AND M1.IDSECCION = ? "
                + "AND M1.FECHA BETWEEN DATEADD(dd,-2,GETDATE()) AND DATEADD(dd,2,GETDATE())";
        
        count = this.getJdbcTemplate().queryForObject(sql, 
                new Object[]{idAlumno, idEdicionestudio, idAsignaturaDictada, idSeccion}, Integer.class);
        
        if(count == 0){
            result.put("MATERIAL", Boolean.FALSE);
        }
        
        sql = "SELECT COUNT(*) FROM EVALUACION E WHERE EXISTS(SELECT M.IDASIGNATURADICTADA FROM MATRICULA M "
                + "INNER JOIN ASIGNATURADICTADA AD ON M.IDASIGNATURADICTADA = AD.IDASIGNATURADICTADA "
                + "INNER JOIN PERIODOACADEMICO P ON AD.IDPERIODOACADEMICO = P.IDPERIODOACADEMICO "
                + "WHERE M.IDALUMNO = ? AND M.IDEDICIONESTUDIO = ? AND P.VIGENTE = 1 "
                + "AND M.IDASIGNATURADICTADA = E.IDASIGNATURADICTADA AND M.IDSECCION = E.IDSECCION) "
                + "AND E.IDASIGNATURADICTADA = ? AND E.IDSECCION = ? "
                + "AND ((E.FECHAENTREGA BETWEEN DATEADD(dd,-2,GETDATE()) AND DATEADD(dd,2,GETDATE()) AND E.IDESTADOEVALUACION = 4) OR "
                + "(E.FECHAENTREGAREC BETWEEN DATEADD(dd,-2,GETDATE()) AND DATEADD(dd,2,GETDATE()) AND E.IDESTADOEVALUACION = 6))";
        
        count = this.getJdbcTemplate().queryForObject(sql, 
                new Object[]{idAlumno, idEdicionestudio, idAsignaturaDictada, idSeccion}, Integer.class);
        
        if(count == 0){
            result.put("EVALUACION", Boolean.FALSE);
        }
        
        return result;
    }
}
