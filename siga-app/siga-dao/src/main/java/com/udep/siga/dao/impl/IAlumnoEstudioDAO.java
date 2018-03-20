package com.udep.siga.dao.impl;

import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.AlumnoEstudioPeriodoAcademico;
import com.udep.siga.bean.Alumnocandidato;
import com.udep.siga.bean.AreaConocimiento;
import com.udep.siga.bean.Edicionestudio;
import com.udep.siga.bean.GradoAcademico;
import com.udep.siga.bean.InfoRequisito;
import com.udep.siga.bean.Observacion;
import com.udep.siga.bean.PEAEAsignaturaEstado;
import com.udep.siga.bean.PeriodoAcademico;
import com.udep.siga.bean.PlanEstudio;
import com.udep.siga.bean.PlanEstudioAsignatura;
import com.udep.siga.bean.RequisitoPlanEstudio;
import com.udep.siga.bean.RequisitoTipoAsignatura;
import com.udep.siga.bean.enumeration.EstadoAlumno;
import com.udep.siga.bean.enumeration.TipoAsignatura;
import com.udep.siga.bean.enumeration.TipoEstudio;
import com.udep.siga.dao.AlumnoEstudioDAO;
import com.udep.siga.dao.AsignaturaDictadaDAO;
import com.udep.siga.dao.util.CustomizeJdbcDaoSupport;
import com.udep.siga.dao.util.UtilRowMapper;
import com.udep.siga.util.AppConstants;
import com.udep.siga.util.BDConstants;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Wilfredo Atoche
 */
@Repository("alumnoEstudioDAO")
public class IAlumnoEstudioDAO extends CustomizeJdbcDaoSupport implements AlumnoEstudioDAO {

    @Autowired
    private AsignaturaDictadaDAO asignaturaDictadaDAO;

    @Override
    public List<AlumnoEstudio> getEstudiosInactivosByAlumno(int idAlumno) {
        String sql = "SELECT AE.IDALUMNO, AE.IDEDICIONESTUDIO, E.IDESTUDIO, AE.CARNE, AE.IDCAMPUS, AE.IDESTADOALUMNO, "
                + "AE.IDTIPOEXALUMNO, AE.CREDITOSTOTALESMATRICULADOS, AE.CREDITOSTOTALESCONVALIDADOS, AE.CREDITOSTOTALESAPROBADOS, "
                + "AE.CREDITOSTOTALESDESAPROBADOS, AE.CREDITOSPERIODOMATRICULADOS, AE.CREDITOSPERIODOCONVALIDADOS, AE.CREDITOSPERIODOAPROBADOS, "
                + "AE.CREDITOSPERIODODESAPROBADOS, AE.INDICEACUMULADO, AE.INDICEPERIODO, AE.INDICEBIPERIODO, AE.TERCIOSUPERIOR, AE.QUINTOSUPERIOR, "
                + "AE.ORDENMERITO, AE.CICLO, AE.NIVEL, AE.MOROSO, AE.DEBE3CUOTAS, AE.ANULAEXAMENES, AE.CREDITOSTOTALESCUMPLIDOS, "
                + "AE.CREDITOSPERIODOCUMPLIDOS, AE.OBSERVACIONGENERAL, AE.ORDENMERITOTOTAL, AE.IDPERIODOINGRESO "
                + "FROM ALUMNOESTUDIO AE, EDICIONESTUDIO E "
                + "WHERE AE.IDEDICIONESTUDIO = E.IDESTUDIO AND AE.IDESTADOALUMNO != :idEstadoAlumno AND AE.IDALUMNO = :idAlumno "
                + "ORDER BY AE.IDESTADOALUMNO ASC, AE.IDEDICIONESTUDIO ASC";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", idAlumno);
        params.addValue("idEstadoAlumno", EstadoAlumno.Alumno.getId());
        List<AlumnoEstudio> list = this.getNamedParameterJdbcTemplate().query(sql,
                params, UtilRowMapper.getAlumnoEstudioMapper());
        return list;
    }

    @Override
    public List<AlumnoEstudio> getEstudiosEgExByAlumno(int idAlumno) {
        String sql = "SELECT DISTINCT AE.IDALUMNO, AE.IDEDICIONESTUDIO, E.IDESTUDIO, AE.CARNE, AE.IDCAMPUS, AE.IDESTADOALUMNO, "
                + "AE.IDTIPOEXALUMNO, AE.CREDITOSTOTALESMATRICULADOS, AE.CREDITOSTOTALESCONVALIDADOS, AE.CREDITOSTOTALESAPROBADOS, "
                + "AE.CREDITOSTOTALESDESAPROBADOS, AE.CREDITOSPERIODOMATRICULADOS, AE.CREDITOSPERIODOCONVALIDADOS, AE.CREDITOSPERIODOAPROBADOS, "
                + "AE.CREDITOSPERIODODESAPROBADOS, AE.INDICEACUMULADO, AE.INDICEPERIODO, AE.INDICEBIPERIODO, AE.TERCIOSUPERIOR, AE.QUINTOSUPERIOR, "
                + "AE.ORDENMERITO, AE.CICLO, AE.NIVEL, AE.MOROSO, AE.DEBE3CUOTAS, AE.ANULAEXAMENES, AE.CREDITOSTOTALESCUMPLIDOS, "
                + "AE.CREDITOSPERIODOCUMPLIDOS, AE.OBSERVACIONGENERAL, AE.ORDENMERITOTOTAL, AE.IDPERIODOINGRESO "
                + "FROM ALUMNOESTUDIOPERIODOACADEMICO AEP, ALUMNOESTUDIO AE, PERIODOACADEMICO P, EDICIONESTUDIO ED, ESTUDIO E "
                + "WHERE AEP.IDPERIODOACADEMICO = P.IDPERIODOACADEMICO AND AE.IDALUMNO = AEP.IDALUMNO AND AE.IDEDICIONESTUDIO = AEP.IDEDICIONESTUDIO "
                + "AND AE.IDEDICIONESTUDIO = ED.IDEDICIONESTUDIO AND ED.IDESTUDIO = E.IDESTUDIO "
                + "AND AE.IDESTADOALUMNO IN (2,3) AND E.IDTIPOESTUDIO IN (1,2,8) AND AEP.IDALUMNO = :idAlumno AND "
                + "DATEDIFF(DAY, P.FECHAFIN, GETDATE()) <= :dias";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", idAlumno);
        params.addValue("dias", AppConstants.PLAZO_ACCESO_EG_EX_DIAS);
        List<AlumnoEstudio> list = this.getNamedParameterJdbcTemplate().query(sql,
                params, UtilRowMapper.getAlumnoEstudioMapper());
        return list;
    }

    @Override
    public List<AlumnoEstudio> getEstudiosByAlumno(int idAlumno, EstadoAlumno estadoAlumno) {
        String sql = "SELECT DISTINCT AE.IDALUMNO, AE.IDEDICIONESTUDIO, E.IDESTUDIO, AE.CARNE, AE.IDCAMPUS, AE.IDESTADOALUMNO, "
                + "AE.IDTIPOEXALUMNO, AE.CREDITOSTOTALESMATRICULADOS, AE.CREDITOSTOTALESCONVALIDADOS, AE.CREDITOSTOTALESAPROBADOS, "
                + "AE.CREDITOSTOTALESDESAPROBADOS, AE.CREDITOSPERIODOMATRICULADOS, AE.CREDITOSPERIODOCONVALIDADOS, AE.CREDITOSPERIODOAPROBADOS, "
                + "AE.CREDITOSPERIODODESAPROBADOS, AE.INDICEACUMULADO, AE.INDICEPERIODO, AE.INDICEBIPERIODO, AE.TERCIOSUPERIOR, AE.QUINTOSUPERIOR, "
                + "AE.ORDENMERITO, AE.CICLO, AE.NIVEL, AE.MOROSO, AE.DEBE3CUOTAS, AE.ANULAEXAMENES, AE.CREDITOSTOTALESCUMPLIDOS, "
                + "AE.CREDITOSPERIODOCUMPLIDOS, AE.OBSERVACIONGENERAL, AE.ORDENMERITOTOTAL, AE.IDPERIODOINGRESO "
                + "FROM ALUMNOESTUDIO AE, EDICIONESTUDIO E "
                + "WHERE AE.IDEDICIONESTUDIO = E.IDESTUDIO "
                + "AND AE.IDESTADOALUMNO = :idEstadoAlumno AND AE.IDALUMNO = :idAlumno";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", idAlumno);
        params.addValue("idEstadoAlumno", estadoAlumno.getId());
        List<AlumnoEstudio> list = this.getNamedParameterJdbcTemplate().query(sql,
                params, UtilRowMapper.getAlumnoEstudioMapper());
        return list;
    }

    @Override
    public List<AlumnoEstudio> getEstudiosByAlumno(int idAlumno, EstadoAlumno estadoAlumno, TipoEstudio tipoEstudio) {
        String sql = "SELECT AE.IDALUMNO, AE.IDEDICIONESTUDIO, E.IDESTUDIO, AE.CARNE, AE.IDCAMPUS, AE.IDESTADOALUMNO, "
                + "AE.IDTIPOEXALUMNO, AE.CREDITOSTOTALESMATRICULADOS, AE.CREDITOSTOTALESCONVALIDADOS, AE.CREDITOSTOTALESAPROBADOS, "
                + "AE.CREDITOSTOTALESDESAPROBADOS, AE.CREDITOSPERIODOMATRICULADOS, AE.CREDITOSPERIODOCONVALIDADOS, AE.CREDITOSPERIODOAPROBADOS, "
                + "AE.CREDITOSPERIODODESAPROBADOS, AE.INDICEACUMULADO, AE.INDICEPERIODO, AE.INDICEBIPERIODO, AE.TERCIOSUPERIOR, AE.QUINTOSUPERIOR, "
                + "AE.ORDENMERITO, AE.CICLO, AE.NIVEL, AE.MOROSO, AE.DEBE3CUOTAS, AE.ANULAEXAMENES, AE.CREDITOSTOTALESCUMPLIDOS, "
                + "AE.CREDITOSPERIODOCUMPLIDOS, AE.OBSERVACIONGENERAL, AE.ORDENMERITOTOTAL, AE.IDPERIODOINGRESO "
                + "FROM ALUMNOESTUDIO AE, EDICIONESTUDIO EE, ESTUDIO E "
                + "WHERE AE.IDEDICIONESTUDIO = EE.IDEDICIONESTUDIO AND EE.IDEDICIONESTUDIO = E.IDESTUDIO "
                + "AND AE.IDESTADOALUMNO = :idEstadoAlumno AND AE.IDALUMNO = :idAlumno AND E.IDTIPOESTUDIO = :idTipoEstudio";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idEstadoAlumno", estadoAlumno.getId());
        params.addValue("idAlumno", idAlumno);
        params.addValue("idTipoEstudio", tipoEstudio.getId());
        List<AlumnoEstudio> list = this.getNamedParameterJdbcTemplate().query(sql,
                params, UtilRowMapper.getAlumnoEstudioMapper());
        return list;
    }

    @Override
    public Edicionestudio getEdicionestudioById(int idEdicionEstudio) {
        String sql = "SELECT EE.IDEDICIONESTUDIO, EE.IDPERIODICIDADPLANESTUDIOS, "
                + "EE.IDESTUDIO, EE.IDSISTEMAHORARIO, E.SIGLA, E.NOMBRE, E.ACTIVO, E.IDCENTROACADEMICO, "
                + "CA.SIGLACENTRO, CA.NOMBRE 'CENTROACADEMICO',CA.ACTIVO 'CENTROACTIVO', E.IDTIPOESTUDIO "
                + "FROM EDICIONESTUDIO EE, ESTUDIO E, CENTROACADEMICO CA WHERE EE.IDESTUDIO = E.IDESTUDIO AND "
                + "E.IDCENTROACADEMICO = CA.IDCENTROACADEMICO AND EE.IDEDICIONESTUDIO = ?";

        Edicionestudio edicionestudio = (Edicionestudio) this.getJdbcTemplate().queryForObject(sql,
                new Object[]{idEdicionEstudio}, UtilRowMapper.getEdicionEstudioMapper());
        return edicionestudio;
    }

    @Override
    public PlanEstudio getPlanEstudioAlumnoEstudioActivoByAlumnoEstudio(int idAlumno, int idEdicionestudio) {
        String sql = "SELECT TOP 1 P.IDPLANESTUDIOS, P.IDEDICIONESTUDIO, P.NOMBRE, P.FECHAINICIO, P.FECHAFIN, P.ACTIVO, "
                + "P.NUMPERIODOS, P.VIGENTE, P.CREDITOSTOTALES, P.BLOQUEADO FROM PLANESTUDIOS P, PLANESTUDIOSALUMNOESTUDIO PEAE "
                + "WHERE P.IDPLANESTUDIOS = PEAE.IDPLANESTUDIOS AND PEAE.IDALUMNO = ? AND PEAE.IDEDICIONESTUDIO = ? AND PEAE.ACTIVO = 1 ";

        try {
            PlanEstudio planEstudio = (PlanEstudio) this.getJdbcTemplate().queryForObject(sql,
                    new Object[]{idAlumno, idEdicionestudio}, UtilRowMapper.getPlanEstudioMapper());

            return planEstudio;
        } catch (IncorrectResultSizeDataAccessException ex) {
            return null;
        }
    }

    @Override
    public String getTipoBeca(AlumnoEstudio alumnoEstudio) {
        String sql = "SELECT T.DESCRIPCION "
                + "FROM ALUMNOESTUDIOPERIODOACADEMICO AEP, TIPOBECA T "
                + "WHERE AEP.BECADO = T.IDBECA AND AEP.IDEDICIONESTUDIO = :idEstudio "
                + "AND AEP.IDALUMNO = :idAlumno AND AEP.IDPERIODOACADEMICO = :idPeriodoAcademico";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idEstudio", alumnoEstudio.getEdicionestudio().getId());
        params.addValue("idAlumno", alumnoEstudio.getAlumno().getId());
        params.addValue("idPeriodoAcademico", alumnoEstudio.getPeriodoAcademicoVigente().getId());

        List<String> becaList = this.getNamedParameterJdbcTemplate().queryForList(sql, params, String.class);
        if (becaList.isEmpty()) {
            return "No";
        } else {
            return "Si (" + becaList.get(0) + ")";
        }
    }

    public List<RequisitoPlanEstudio> getRequisitosPlanEstudioList(int idPlan) {
        String sql = "SELECT R.IDTIPOREQUISITOPLANESTUDIOS, R.VALOR "
                + "FROM REQUISITOPLANESTUDIOS R "
                + "WHERE R.IDPLANESTUDIOS = :idPlan";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idPlan", idPlan);

        List<RequisitoPlanEstudio> list = this.getNamedParameterJdbcTemplate().query(sql,
                params, UtilRowMapper.getRequisitoPlanEstudioMapper());

        return list;
    }

    public boolean isCumpleRequisitoPlanEstudio(int idPlan, AlumnoEstudio alumnoestudio,
            int idTipoRequisito) {
        String sql = "SELECT COUNT(*) FROM PLANESTUDIOSALUMNOESTUDIOCUMPLEREQUISITO  "
                + "WHERE IDALUMNO = :idAlumno AND IDPLANESTUDIOS = :idPlan "
                + "AND IDEDICIONESTUDIO = :idEdicionEstudio AND IDTIPOREQUISITOPLANESTUDIOS = :idTipoRequisito "
                + "AND CUMPLEREQPLANESTUDIOS = 1";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", alumnoestudio.getAlumno().getId());
        params.addValue("idEdicionEstudio", alumnoestudio.getEdicionestudio().getId());
        params.addValue("idPlan", idPlan);
        params.addValue("idTipoRequisito", idTipoRequisito);

        int count = this.getNamedParameterJdbcTemplate().queryForObject(sql,
                params, Integer.class);
        if (count == 0) {
            return false;
        } else {
            return true;
        }
    }

    public String getCumpleRequisitosPlanEstudio(AlumnoEstudio alumnoEstudio, int idPlan, int idTipoRequisito) {
        String sql = "SELECT TOP 1 R.VALOR FROM PLANESTUDIOSALUMNOESTUDIOCUMPLEREQPERACAD P,"
                + "REQUISITOPERIODOPLANESTUDIOS R "
                + "WHERE P.IDTIPOREQUISITOPLANESTUDIOS = R.IDTIPOREQUISITOPLANESTUDIOS AND "
                + "P.IDPLANESTUDIOSCUMPLEREQPERACAD = R.IDPLANESTUDIOS AND R.NUMPERIODO = P.NUMPERIODO "
                + "AND P.IDALUMNO = :idAlumno AND P.IDEDICIONESTUDIO = :idEdicionEstudio AND "
                + "P.IDPLANESTUDIOS = :idPlan AND P.ESTADO = 1 AND "
                + "P.IDTIPOREQUISITOPLANESTUDIOS = :idTipoRequisito ORDER BY P.NUMPERIODO DESC";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idEdicionEstudio", alumnoEstudio.getEdicionestudio().getId());
        params.addValue("idAlumno", alumnoEstudio.getAlumno().getId());
        params.addValue("idTipoRequisito", idTipoRequisito);
        params.addValue("idPlan", idPlan);

        List<String> list = this.getNamedParameterJdbcTemplate().queryForList(sql,
                params, String.class);

        if (list.isEmpty()) {
            if (isCumpleRequisitoPlanEstudio(idPlan, alumnoEstudio, idTipoRequisito)) {
                return "Cumplido";
            } else {
                return "No cumplido";
            }
        } else {
            return list.get(0);
        }
    }

    public List<RequisitoTipoAsignatura> getRequisitosTipoAsignaturaPlanEstudioList(int idPlan) {
        String sql = "SELECT IDTIPOASIGNATURA,VALOR FROM REQUISITOTIPOASIGNATURAPLANESTUDIOS "
                + "WHERE IDPLANESTUDIOS = :idPlan";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idPlan", idPlan);

        List<RequisitoTipoAsignatura> list = this.getNamedParameterJdbcTemplate().query(sql,
                params, UtilRowMapper.getRequisitoTipoAsignaturaMapper());

        return list;
    }

    public int getTotalCreditosCLC(AlumnoEstudio alumnoEstudio) {
        if (alumnoEstudio.getPeriodoAcademicoVigente() != null) {
            SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.getJdbcTemplate());
            simpleJdbcCall.withProcedureName("CALCULAR_CLC_EMPRESAS");
            simpleJdbcCall.declareParameters(new SqlParameter("IDA", Types.INTEGER));
            simpleJdbcCall.declareParameters(new SqlParameter("IDE", Types.INTEGER));
            simpleJdbcCall.declareParameters(new SqlParameter("IDPA", Types.INTEGER));
            simpleJdbcCall.declareParameters(new SqlInOutParameter("TOTAL", Types.INTEGER));

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("IDA", alumnoEstudio.getAlumno().getId());
            params.addValue("IDE", alumnoEstudio.getEdicionestudio().getId());
            params.addValue("IDPA", alumnoEstudio.getPeriodoAcademicoVigente().getId());
            params.addValue("TOTAL", 0);

            Map<String, Object> out = simpleJdbcCall.execute(params);

            return (Integer) out.get("TOTAL");
        } else {
            return 0;
        }
    }

    public String getCreditosActuales(AlumnoEstudio alumnoEstudio, int idTipoAsignatura) {
        int creditos = this.getTotalCreditosActuales(alumnoEstudio, idTipoAsignatura);
        if (creditos == 0) {
            return "-";
        } else {
            return creditos + "";
        }

    }

    public int getTotalCreditosActuales(AlumnoEstudio alumnoEstudio, int idTipoAsignatura) {
        int idEdicionEstudio = alumnoEstudio.getEdicionestudio().getId();

        if (idTipoAsignatura == TipoAsignatura.LIBRE_CONFIGURACION.getId()
                && (idEdicionEstudio == 4 || idEdicionEstudio == 5
                || idEdicionEstudio == 6 || idEdicionEstudio == 7)) {
            return getTotalCreditosCLC(alumnoEstudio);
        } else {

            String sql = "SELECT ISNULL(SUM(A.CREDITOS),0) FROM ALUMNOHISTORIAL AH "
                    + "JOIN ASIGNATURA A ON A.IDASIGNATURA=AH.IDASIGNATURA "
                    + "WHERE IDALUMNO = :idAlumno "
                    + "AND AH.IDEDICIONESTUDIO = :idEdicionEstudio AND AH.IDNOTA IN "
                    + "(SELECT IDNOTA FROM NOTA WHERE APROBADA = 1) AND EXISTS "
                    + "(SELECT IDASIGNATURA,IDPLANESTUDIOS FROM PLANESTUDIOSASIGNATURA "
                    + "WHERE IDASIGNATURA = AH.IDASIGNATURA AND IDPLANESTUDIOS = AH.IDPLANESTUDIOS "
                    + "AND IDTIPOASIGNATURA = :idTipoAsignatura) AND (AH.RETIROCURSO = 0 "
                    + "OR AH.RETIROCURSO IS NULL) AND CLC = 0";

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("idAlumno", alumnoEstudio.getAlumno().getId());
            params.addValue("idEdicionEstudio", alumnoEstudio.getEdicionestudio().getId());
            params.addValue("idTipoAsignatura", idTipoAsignatura);

            int creditos = this.getNamedParameterJdbcTemplate().queryForObject(sql,
                    params, Integer.class);

            return creditos;
        }

    }

    public boolean isCumpleRequisitoTipoAsignatura(int idPlan, AlumnoEstudio alumnoEstudio,
            int idTipoAsignatura) {
        String sql = "SELECT COUNT(*) FROM PLANESTUDIOSALUMNOESTUDIOREQTIPOASIGNATURACUMPLE  "
                + "WHERE IDALUMNO = :idAlumno AND IDPLANESTUDIOS = :idPlan "
                + "AND IDEDICIONESTUDIO = :idEdicionEstudio AND IDTIPOASIGNATURA = :idTipoAsignatura "
                + "AND ESTADO = 1 ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", alumnoEstudio.getAlumno().getId());
        params.addValue("idEdicionEstudio", alumnoEstudio.getEdicionestudio().getId());
        params.addValue("idPlan", idPlan);
        params.addValue("idTipoAsignatura", idTipoAsignatura);

        int count = this.getNamedParameterJdbcTemplate().queryForObject(sql,
                params, Integer.class);
        if (count == 0) {
            return false;
        } else {
            return true;
        }
    }

    public GradoAcademico getBachillerato(AlumnoEstudio alumnoEstudio) {

        String sql = "SELECT TOP 1 G.IDGRADOACADEMICO,M.NOMBRE AS MODOOBTENCION "
                + "FROM GRADOACADEMICOALUMNO G, MODOOBTENCION M "
                + "WHERE G.IDMODOOBTENCIONMO = M.IDMODOOBTENCION  "
                + "AND G.IDALUMNO = :idAlumno AND G.IDEDICIONESTUDIO = :idEdicionEstudio "
                + "AND G.IDGRADOACADEMICO IN (SELECT IDGRADOACADEMICO "
                + "FROM GRADOACADEMICO WHERE IDTIPOGRADOACADEMICO = 1)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", alumnoEstudio.getAlumno().getId());
        params.addValue("idEdicionEstudio", alumnoEstudio.getEdicionestudio().getId());

        List<GradoAcademico> list = this.getNamedParameterJdbcTemplate().query(sql,
                params, UtilRowMapper.getGradoAcademicoMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }

    }

    public GradoAcademico getTitulo(AlumnoEstudio alumnoEstudio) {

        String sql = "SELECT TOP 1 G.IDGRADOACADEMICO, M.NOMBRE AS MODOOBTENCION "
                + "FROM GRADOACADEMICOALUMNO G, MODOOBTENCION M "
                + "WHERE G.IDMODOOBTENCIONMO = M.IDMODOOBTENCION  "
                + "AND G.IDALUMNO = :idAlumno AND G.IDEDICIONESTUDIO = :idEdicionEstudio "
                + "AND G.IDGRADOACADEMICO IN (SELECT IDGRADOACADEMICO "
                + "FROM GRADOACADEMICO WHERE IDTIPOGRADOACADEMICO != 1)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", alumnoEstudio.getAlumno().getId());
        params.addValue("idEdicionEstudio", alumnoEstudio.getEdicionestudio().getId());

        List<GradoAcademico> list = this.getNamedParameterJdbcTemplate().query(sql,
                params, UtilRowMapper.getGradoAcademicoMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }

    }

    public List<AlumnoEstudioPeriodoAcademico> getHistorialPeriodoAcadByAlumnoEstudio(AlumnoEstudio alumnoEstudio) {
        String sql = "SELECT A.IDALUMNO, A.IDEDICIONESTUDIO,A.IDCAMPUS,"
                + "A.CREDITOSTOTALESMATRICULADOS, A.CREDITOSTOTALESCONVALIDADOS, "
                + "A.CREDITOSTOTALESAPROBADOS, A.CREDITOSTOTALESDESAPROBADOS, "
                + "A.CREDITOSPERIODOMATRICULADOS, A.CREDITOSPERIODOCONVALIDADOS, "
                + "A.CREDITOSPERIODOAPROBADOS, A.CREDITOSPERIODODESAPROBADOS, "
                + "A.INDICEACUMULADO, A.INDICEPERIODO, A.INDICEBIPERIODO, A.TERCIOSUPERIOR, "
                + "A.QUINTOSUPERIOR,A.ORDENMERITO, A.CICLO, A.NIVEL,A.CREDITOSTOTALESCUMPLIDOS, "
                + "A.CREDITOSPERIODOCUMPLIDOS, A.ORDENMERITOTOTAL, A.ANULACICLO, "
                + "A.IDPERIODOACADEMICO FROM ALUMNOESTUDIOPERIODOACADEMICO A,PERIODOACADEMICO P "
                + "WHERE A.IDPERIODOACADEMICO = P.IDPERIODOACADEMICO AND IDALUMNO = :idAlumno "
                + "AND IDEDICIONESTUDIO = :idEdicionEstudio "
                + "AND P.IDPERIODOACADEMICO NOT IN(SELECT EEP.IDPERIODOACADEMICO "
                + "FROM EDICIONESTUDIOPERACAD EEP WHERE  EEP.IDPERIODOACADEMICO = P.IDPERIODOACADEMICO "
                + "AND P.ACTIVO = 1 AND P.VIGENTE = 1 AND EEP.IDCAMPUS = A.IDCAMPUS "
                + "AND EEP.IDEDICIONESTUDIO = A.IDEDICIONESTUDIO "
                //+ "AND P.IDPERIODICIDADPLANESTUDIOS = 1
                + " ) "
                + "ORDER BY P.FECHAINICIO DESC";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", alumnoEstudio.getAlumno().getId());
        params.addValue("idEdicionEstudio", alumnoEstudio.getEdicionestudio().getId());

        List<AlumnoEstudioPeriodoAcademico> list = this.getNamedParameterJdbcTemplate().query(sql,
                params, UtilRowMapper.getAlumnoEstudioPeriodoAcademicoMapper());

        return list;
    }

    public AlumnoEstudioPeriodoAcademico getPeriodoAcadAlumnoEstudioByPeriodo(AlumnoEstudio alumnoEstudio, PeriodoAcademico periodo) {
        String sql = "SELECT A.IDALUMNO, A.IDEDICIONESTUDIO,A.IDCAMPUS,"
                + "A.CREDITOSTOTALESMATRICULADOS, A.CREDITOSTOTALESCONVALIDADOS, "
                + "A.CREDITOSTOTALESAPROBADOS, A.CREDITOSTOTALESDESAPROBADOS, "
                + "A.CREDITOSPERIODOMATRICULADOS, A.CREDITOSPERIODOCONVALIDADOS, "
                + "A.CREDITOSPERIODOAPROBADOS, A.CREDITOSPERIODODESAPROBADOS, "
                + "A.INDICEACUMULADO, A.INDICEPERIODO, A.INDICEBIPERIODO, A.TERCIOSUPERIOR, "
                + "A.QUINTOSUPERIOR,A.ORDENMERITO, A.CICLO, A.NIVEL,A.CREDITOSTOTALESCUMPLIDOS, "
                + "A.CREDITOSPERIODOCUMPLIDOS, A.ORDENMERITOTOTAL, A.ANULACICLO, "
                + "A.IDPERIODOACADEMICO FROM ALUMNOESTUDIOPERIODOACADEMICO A,PERIODOACADEMICO P "
                + "WHERE A.IDPERIODOACADEMICO = :periodo AND IDALUMNO = :idAlumno "
                + "AND IDEDICIONESTUDIO = :idEdicionEstudio ORDER BY P.FECHAINICIO DESC";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", alumnoEstudio.getAlumno().getId());
        params.addValue("idEdicionEstudio", alumnoEstudio.getEdicionestudio().getId());
        params.addValue("periodo", periodo.getId());

        List<AlumnoEstudioPeriodoAcademico> list = this.getNamedParameterJdbcTemplate().query(sql,
                params, UtilRowMapper.getAlumnoEstudioPeriodoAcademicoMapper());

        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<Observacion> getObservacionesByAlumnoEstudioHistorial(AlumnoEstudioPeriodoAcademico alumnoEstudioPA) {
        String sql = "SELECT O.DESCRIPCION, AO.VALOR FROM ALUMNOOBSERVACION AO, "
                + "OBSERVACION O  WHERE AO.IDOBSERVACION = O.IDOBSERVACION "
                + "AND AO.IDALUMNO = :idAlumno AND IDEDICIONESTUDIO = :idEdicionEstudio "
                + "AND IDPERIODOACADEMICO = :idPeriodo";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", alumnoEstudioPA.getAlumno().getId());
        params.addValue("idEdicionEstudio", alumnoEstudioPA.getEdicionestudio().getId());
        params.addValue("idPeriodo", alumnoEstudioPA.getPeriodoAcademico().getId());

        List<Observacion> list = this.getNamedParameterJdbcTemplate().query(sql,
                params, UtilRowMapper.getObservacionMapper());

        //verifica si es de tipo periodo verano
        if (alumnoEstudioPA.getPeriodoAcademico().getTipoPeriodoAcademico() != null) {
            if (alumnoEstudioPA.getPeriodoAcademico().getTipoPeriodoAcademico().getId() == 2) {
                list.add(new Observacion("", "En el período de verano no se "
                        + "calculan ordenes de mérito. Ref. RFAG v2 Art. 95º."));
            }
        }
        return list;
    }

    /*
     * Datos para Malla
     */
    public List<AreaConocimiento> findAreaConocimientosByPlanEstudioId(
            int idPlanEstudio, AlumnoEstudio alumnoEstudio) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.getJdbcTemplate())
                .withProcedureName(BDConstants.SP_GET_AREASCONOCIMIENTO_BYPLANESTUDIOS)
                .returningResultSet("resulset", UtilRowMapper.getAreaConocimientoMapper());

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("IDALUMNO", alumnoEstudio.getAlumno().getId())
                .addValue("IDPLANESTUDIOS", idPlanEstudio)
                .addValue("IDEDICIONESTUDIO", alumnoEstudio.getEdicionestudio().getId());

        List<AreaConocimiento> areaConocimientosList = (List<AreaConocimiento>) simpleJdbcCall.execute(params).get("resulset");

        for (AreaConocimiento areaConocimiento : areaConocimientosList) {
            areaConocimiento.setPlanEstudioAsignaturaList(
                    findAsignaturasByAreaConocimientoPlanEstudioId(areaConocimiento.getId(), idPlanEstudio,
                            alumnoEstudio));
        }

        return areaConocimientosList;
    }

    private List<PlanEstudioAsignatura> findAsignaturasByAreaConocimientoPlanEstudioId(int idAreaConocimiento,
            int idPlanEstudio, AlumnoEstudio alumnoEstudio) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.getJdbcTemplate())
                .withProcedureName(BDConstants.SP_GET_ASIGNATURAS_BYPLANESTUDIO_AC)
                .returningResultSet("resulset", UtilRowMapper.getPlanEstudioAsignaturaCustomEsp());

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("IDPLANESTUDIOS", idPlanEstudio)
                .addValue("IDEDICIONESTUDIO", alumnoEstudio.getEdicionestudio().getId())
                .addValue("IDAREACONOCIMIENTO", idAreaConocimiento)
                .addValue("IDALUMNO", alumnoEstudio.getAlumno().getId())
                .addValue("IDESPECIALIDAD", null, Types.NULL)
                .addValue("IDASIGNATURA", null, Types.NULL)
                .addValue("NUMPERIODO", null, Types.NULL)
                .addValue("SIGADOC", 0, Types.BIT)
                .addValue("SOLODESDEESP", 0, Types.BIT);

        List<PlanEstudioAsignatura> planEstudioAsignaturaList = (List<PlanEstudioAsignatura>) simpleJdbcCall.execute(params).get("resulset");

        for (PlanEstudioAsignatura planEstudioAsignatura : planEstudioAsignaturaList) {
            planEstudioAsignatura.setRequisitosList(
                    getRequisitosByPlanEstudioAsignatura(planEstudioAsignatura));
            planEstudioAsignatura.setEstadoEnMalla(getEstadoAsignaturaAlumnoPlanEstudio(
                    idPlanEstudio, alumnoEstudio, planEstudioAsignatura.getAsignatura().getId()));

            if (planEstudioAsignatura.getEstadoEnMalla() == 4 || planEstudioAsignatura.getEstadoEnMalla() == 9) {
                planEstudioAsignatura.setNotaHistorial(getNotaHistorial(alumnoEstudio,
                        planEstudioAsignatura.getAsignatura().getId(), idPlanEstudio));
            }
        }

        return planEstudioAsignaturaList;
    }

    public PlanEstudioAsignatura findAsignaturaById_AreaConocimientoPlanEstudioIdEspecialidad(int idAreaConocimiento,
            int idPlanEstudio, int idAsignatura, int idEspecialidad, AlumnoEstudio alumnoEstudio) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.getJdbcTemplate())
                .withProcedureName(BDConstants.SP_GET_ASIGNATURAS_BYPLANESTUDIO_AC)
                .returningResultSet("resulset", UtilRowMapper.getPlanEstudioAsignaturaCustomEsp());

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("IDPLANESTUDIOS", idPlanEstudio)
                .addValue("IDEDICIONESTUDIO", alumnoEstudio.getEdicionestudio().getId())
                .addValue("IDAREACONOCIMIENTO", idAreaConocimiento)
                .addValue("IDALUMNO", alumnoEstudio.getAlumno().getId())
                .addValue("IDESPECIALIDAD", idEspecialidad)
                .addValue("IDASIGNATURA", idAsignatura)
                .addValue("NUMPERIODO", null, Types.NULL)
                .addValue("SIGADOC", 0, Types.BIT)
                .addValue("SOLODESDEESP", 0, Types.BIT);

        List<PlanEstudioAsignatura> planEstudioAsignaturaList = (List<PlanEstudioAsignatura>) simpleJdbcCall.execute(params).get("resulset");

        for (PlanEstudioAsignatura planEstudioAsignatura : planEstudioAsignaturaList) {
            planEstudioAsignatura.setRequisitosList(
                    getRequisitosByPlanEstudioAsignatura(planEstudioAsignatura));
            planEstudioAsignatura.setEstadoEnMalla(getEstadoAsignaturaAlumnoPlanEstudio(
                    idPlanEstudio, alumnoEstudio, planEstudioAsignatura.getAsignatura().getId()));

            if (planEstudioAsignatura.getEstadoEnMalla() == 4 || planEstudioAsignatura.getEstadoEnMalla() == 9) {
                planEstudioAsignatura.setNotaHistorial(getNotaHistorial(alumnoEstudio,
                        planEstudioAsignatura.getAsignatura().getId(), idPlanEstudio));
            }
        }

        return (planEstudioAsignaturaList.isEmpty()) ? null : planEstudioAsignaturaList.get(0);
    }

    public List<InfoRequisito> getRequisitosByPlanEstudioAsignatura(PlanEstudioAsignatura planEstudioAsignatura) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.getJdbcTemplate())
                .withProcedureName(BDConstants.SP_REQUISITOPLANESTUDIOS_BYASIGNATURA)
                .returningResultSet("resulset", new RowMapper() {
                    public InfoRequisito mapRow(ResultSet rs, int rowNum) throws SQLException {
                        InfoRequisito infoRequisito = new InfoRequisito();
                        infoRequisito.setGrupo("Requisito " + rs.getInt("IDGRUPOREQUISITO"));
                        infoRequisito.setDescripcion(rs.getString("DESCRIPCION"));

                        if (rs.getString("CODIGO").compareTo("CODIGO") != 0) {
                            if (rs.getString("VALOR") != null) {
                                infoRequisito.setDescripcion(infoRequisito.getDescripcion() + " " + rs.getString("CODIGO") + " con nota >= " + rs.getString("VALOR"));
                            } else {
                                infoRequisito.setDescripcion(infoRequisito.getDescripcion() + " " + rs.getString("CODIGO"));
                            }
                        } else {
                            infoRequisito.setDescripcion(infoRequisito.getDescripcion() + " " + rs.getString("VALOR"));
                        }
                        return infoRequisito;
                    }
                });

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("IDPLANESTUDIO", planEstudioAsignatura.getId())
                .addValue("IDASIGNATURA", planEstudioAsignatura.getAsignatura().getId());
         
        List<InfoRequisito> requisitosList = (List<InfoRequisito>) simpleJdbcCall.execute(params).get("resulset");
        
        return requisitosList;
    }

    /*
     * 1 - No se dictará / No cumple requisitos.
     * 2 - No hay secciones disponibles
     * 3 - Puede llevar
     * 4 - Desaprobada
     * 5 - Aprobada / Convalidada
     * 6 - Exonerada
     * 7 - Asignado
     * 8 - Se Dicta pero no cumple requisitos, y no esta desparobada.
     * 9 - No se dictará y esta desaprobada.
     * 10 - Matriculado (cursando)
     */
    public int getEstadoAsignaturaAlumnoPlanEstudio(int planEstudioId, AlumnoEstudio alumnoEstudio, int asignaturaId) {
        return this.getJdbcTemplate().queryForObject(BDConstants.FN_GET_ESTADO_ASIGNATURA, 
                new Object[]{
                         alumnoEstudio.getAlumno().getId()
                        ,alumnoEstudio.getEdicionestudio().getId()
                        ,alumnoEstudio.getPeriodoAcademicoVigente().getId()
                        ,alumnoEstudio.getCampus().getId()
                        ,asignaturaId
                        ,planEstudioId}, Integer.class);
    }

    public int getNotaHistorial(AlumnoEstudio alumnoEstudio, int asignaturaId, int planEstudioId) {
        String sql = "SELECT TOP 1 CASE WHEN COUNT(*) = 0 THEN 0 ELSE N.VALOR END FROM ALUMNOHISTORIAL AH, PERIODOACADEMICO P, NOTA N "
                + "WHERE IDEDICIONESTUDIO = :estudioId AND IDALUMNO = :alumnoId AND IDASIGNATURA = :asignaturaId "
                + "AND IDPLANESTUDIOS = :planEstudioId AND P.IDPERIODOACADEMICO = AH.IDPERIODOACADEMICO "
                + "AND N.IDNOTA = AH.IDNOTA GROUP BY  N.VALOR,P.ORDEN ORDER BY P.ORDEN DESC ";

        Map params = new HashMap();
        params.put("estudioId", alumnoEstudio.getEdicionestudio().getId());
        params.put("alumnoId", alumnoEstudio.getAlumno().getId());
        params.put("asignaturaId", asignaturaId);
        params.put("planEstudioId", planEstudioId);

        try {
            int rpta = getNamedParameterJdbcTemplate().queryForObject(sql, params, Integer.class);
            return rpta;
        } catch (DataAccessException ex) {
            return 0;
        }
    }

    /*
     * Lista de Estado de asignaturas de acuerdo al Plan Activo del Alumno
     */
    public List<PEAEAsignaturaEstado> getPlanEstudioAsignaturaEstado(int idAlumno, int idPlanEstudio,
            TipoAsignatura tipoAsignatura) {
        String sql = "SELECT A.CODIGO, A.NOMBRE, A.CREDITOS, PEAAE.REGLA, PEAAE.OBSERVACION, PEAAE.ESTADO, "
                + "PEAAE.APROBADA,PEAAE.DESAPROBADA, PEAAE.CONVALIDADA, PEAAE.EQUIVALENTE, "
                + "PEAAE.CONVALIDADAEXTERNA, PEAAE.EXONERADA,(SELECT TOP 1 N.DESCRIPCION FROM ALUMNOHISTORIAL AH, "
                + "NOTA N, PERIODOACADEMICO P WHERE AH.IDNOTA = N.IDNOTA AND AH.IDASIGNATURA = A.IDASIGNATURA "
                + "AND AH.IDPERIODOACADEMICO = P.IDPERIODOACADEMICO AND AH.IDALUMNO = PEAAE.IDALUMNO "
                + "ORDER BY P.ORDEN DESC) 'NOTA' "
                + "FROM PLANESTUDIOSALUMNOASIGNATURAESTADO PEAAE, PLANESTUDIOSASIGNATURA PEA, ASIGNATURA A "
                + "WHERE PEAAE.IDASIGNATURA = PEA.IDASIGNATURA AND PEAAE.IDPLANESTUDIOS = PEA.IDPLANESTUDIOS AND "
                + "PEA.IDTIPOASIGNATURA = ? AND PEA.IDASIGNATURA = A.IDASIGNATURA AND PEAAE.IDALUMNO = ? AND "
                + "PEAAE.IDPLANESTUDIOS = ? AND PEA.ACTIVA = 1 AND PEA.APROBADOCONSEJOSUPERIOR = 1 AND PEAAE.ESTADO IS NOT NULL "
                + "ORDER BY A.NOMBRE";
        List<PEAEAsignaturaEstado> asignaturaEstadoList = this.getJdbcTemplate().query(sql,
                new Object[]{tipoAsignatura.getId(), idAlumno, idPlanEstudio},
                UtilRowMapper.getPEAEAsignaturaEstadoMapper());

        return asignaturaEstadoList;
    }

    /*
     * Creditos desaprobados segun tipo de asgiantura
     */
    public int countTotalCredDesaprobados(int idAlumno, int idPlanEstudio,
            TipoAsignatura tipoAsignatura) {
        String sql = "SELECT ISNULL(SUM(A.CREDITOS),0) FROM ALUMNOHISTORIAL H, PLANESTUDIOSASIGNATURA PEA, "
                + "NOTA N, ASIGNATURA A WHERE H.IDASIGNATURA = PEA.IDASIGNATURA AND "
                + "H.IDPLANESTUDIOS = PEA.IDPLANESTUDIOS AND PEA.IDTIPOASIGNATURA = ? AND "
                + "PEA.IDASIGNATURA = A.IDASIGNATURA AND H.IDALUMNO = ? AND H.IDPLANESTUDIOS = ? AND "
                + "H.IDNOTA = N.IDNOTA AND N.APROBADA = 0 AND H.RETIROCURSO = 0";
        return this.getJdbcTemplate().queryForObject(sql,
                new Object[]{tipoAsignatura.getId(), idAlumno, idPlanEstudio}, Integer.class);
    }

    /*
     * Obtener Valor de Requisito de Plan de Estudios por Tipo de Asignatura
     */
    public String getValorRequisitoPlanEstudio(int idPlanEstudio, TipoAsignatura tipoAsignatura) {
        String sql = "SELECT COUNT(*) FROM REQUISITOTIPOASIGNATURAPLANESTUDIOS R "
                + "WHERE R.IDPLANESTUDIOS = ? AND R.IDTIPOASIGNATURA = ?";
        int count = this.getJdbcTemplate().queryForObject(sql,
                new Object[]{idPlanEstudio, tipoAsignatura.getId()}, Integer.class);
        if (count > 0) {
            sql = "SELECT R.VALOR FROM REQUISITOTIPOASIGNATURAPLANESTUDIOS R "
                    + "WHERE R.IDPLANESTUDIOS = ? AND R.IDTIPOASIGNATURA = ?";
            return this.getJdbcTemplate().queryForObject(sql,
                    new Object[]{idPlanEstudio, tipoAsignatura.getId()}, String.class);
        }
        return "";
    }

    /*
     * Verifica la condición de reincorporado
     */
    public Map<String, Object> isReincorporado(AlumnoEstudio alumnoEstudio) {
        String sql = "SELECT TOP 1 AEPA.CONDICIONREINCORPORACION "
                + "FROM ALUMNOESTUDIOPERIODOACADEMICO AEPA WHERE AEPA.REINCORPORADO = 1 AND "
                + "IDPERIODOACADEMICO = ? AND AEPA.IDALUMNO = ? AND AEPA.IDEDICIONESTUDIO = ?";

        Map<String, Object> result = new HashMap<String, Object>();
        List<String> mapList = this.getJdbcTemplate().queryForList(
                sql, new Object[]{alumnoEstudio.getPeriodoAcademicoVigente().getId(),
                    alumnoEstudio.getAlumno().getId(), alumnoEstudio.getEdicionestudio().getId()},
                String.class);
        if (mapList.isEmpty()) {
            result.put("reincorporado", false);
            result.put("condicion", "");
        } else {
            result.put("reincorporado", true);

            if (mapList.get(0) != null) {
                result.put("condicion", mapList.get(0));
            } else {
                result.put("condicion", "");
            }
        }
        return result;
    }

    /*
     * is cachimbo
     */
    public boolean isCachimbo(AlumnoEstudio alumnoEstudio) {
        String sql = "SELECT CACHIMBO FROM ALUMNOESTUDIOPERIODOACADEMICO "
                + "WHERE IDALUMNO = ? AND IDEDICIONESTUDIO = ? AND IDPERIODOACADEMICO = ?";
        List<Boolean> list = this.getJdbcTemplate().queryForList(sql,
                new Object[]{alumnoEstudio.getAlumno().getId(),
                    alumnoEstudio.getEdicionestudio().getId(),
                    alumnoEstudio.getPeriodoAcademicoVigente().getId()}, Boolean.class);

        if (list.isEmpty()) {
            return false;
        }
        return list.get(0);
    }

    //antes verdelegados

    @Override
    public boolean verDelegadosGeneral(int edicionestudio, int campus, int periodo) {
        String sql = "SELECT COUNT(*) FROM ELECCIONDELEGADOS AS ED "
                + "WHERE ED.IDEDICIONESTUDIO = ? "
                + "AND ED.IDPERIODOACADEMICO IN (SELECT IDPERIODOACADEMICO FROM PERIODOACADEMICO WHERE VIGENTE = 1 AND IDPERIODICIDADPLANESTUDIOS = ?) "
                + "AND ED.IDCAMPUS = ? "
                + "AND ED.ACTIVO = 1";

        Integer count = this.getJdbcTemplate().queryForObject(sql,
                new Object[]{edicionestudio, periodo, campus}, Integer.class);
        return count <= 0;
    }

    // antes vernivel

    @Override
    public boolean verCandidatosporNivel(int edicionestudio, int campus, int periodo, int nivel) {
        String sql = "SELECT COUNT(*) FROM ALUMNOCANDIDATO AS AC "
                + "WHERE "
                + " AC.IDEDICIONESTUDIO = ? "
                + "AND AC.IDPERIODOACADEMICO = ? "
                + "AND AC.IDCAMPUS = ? ";
                //+ "AND AC.NIVEL = ? ";

        Integer count = this.getJdbcTemplate().queryForObject(sql,
                new Object[]{edicionestudio, periodo, campus}, Integer.class);
        
        return count <= 0;
    }

    @Override
    public int contarvotoscandidato(Alumnocandidato alumnocandidato) {
        String sql = "SELECT  COUNT(*) FROM ALUMNOVOTO "
                + "WHERE IDPERIODOACADEMICO = :idPeriodo AND IDCAMPUS = :idCampus "
                + " AND IDEDICIONESTUDIO = :idEdicionestudio "
                + "AND IDALUMNOCANDIDATO = :idCandidato";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idPeriodo", alumnocandidato.getIdperiodoacademico());
        params.addValue("idCampus", alumnocandidato.getIdcampus());
        params.addValue("idEdicionestudio", alumnocandidato.getIdedicionetudio());
        params.addValue("idCandidato", alumnocandidato.getIdalumnocandidato());

        int count = this.getNamedParameterJdbcTemplate().queryForObject(sql,
                params, Integer.class);
        return count;
    }

    @Override
    public boolean verFechaResultadosVotacion(int edicionestudio, int campus, int periodo) {
        String sql = "SELECT  COUNT(*) FROM ELECCIONDELEGADOS "
                + "WHERE IDPERIODOACADEMICO = :idPeriodo AND IDCAMPUS = :idCampus "
                + " AND IDEDICIONESTUDIO = :idedicionestudio "
                + "AND GETDATE() > FECHAFINELEGIRDELEGADOS";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idPeriodo", periodo);
        params.addValue("idCampus", campus);
        params.addValue("idedicionestudio", edicionestudio);
        int count = this.getNamedParameterJdbcTemplate().queryForObject(sql,
                params, Integer.class);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    public String getFechaActual(Date ahora) {
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        return formateador.format(ahora);
    }

    //Sumarle dias a una fecha determinada
    //@param fch La fecha para sumarle los dias
    //@param dias Numero de dias a agregar
    //@return La fecha agregando los dias

    public java.sql.Date sumarFechasDias(java.sql.Date fch, int dias) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(fch.getTime());
        cal.add(Calendar.DATE, dias);
        return new java.sql.Date(cal.getTimeInMillis());
    }

    @Override
    public String isFechaVotacion(int idPeriodoAcademico, int idCampus, int edicionEstudio) {
        String sql = "SELECT  FECHAFINVERDELEGADOS FROM ELECCIONDELEGADOS "
                + "WHERE IDPERIODOACADEMICO = :idPeriodo AND IDCAMPUS = :idCampus "
                + " AND IDEDICIONESTUDIO = :idedicionestudio "
                + "AND GETDATE() < FECHAFINVERDELEGADOS";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idPeriodo", idPeriodoAcademico);
        params.addValue("idCampus", idCampus);
        params.addValue("idedicionestudio", edicionEstudio);

        Date dia = this.getNamedParameterJdbcTemplate().queryForObject(sql, params, Date.class);
        // this.sumarFechasDias(dia,1);
        return getFechaActual(sumarFechasDias(dia, 1));
    }

    @Override
    public String isFechaResultados(int idPeriodoAcademico, int idCampus, int edicionEstudio) {
        String sql = "SELECT  FECHAFINELEGIRDELEGADOS FROM ELECCIONDELEGADOS "
                + "WHERE IDPERIODOACADEMICO = :idPeriodo AND IDCAMPUS = :idCampus "
                + " AND IDEDICIONESTUDIO = :idedicionestudio "
                + "AND GETDATE() < FECHAFINELEGIRDELEGADOS";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idPeriodo", idPeriodoAcademico);
        params.addValue("idCampus", idCampus);
        params.addValue("idedicionestudio", edicionEstudio);

        Date dia = this.getNamedParameterJdbcTemplate().queryForObject(sql, params, Date.class);
        // this.sumarFechasDias(dia,1);
        return getFechaActual(sumarFechasDias(dia, 1));
    }

    @Override
    public boolean getAlumnoVoto(int idPeriodoAcademico, int idCampus, int edicionEstudio, int idalumno) {
        String sql = "SELECT COUNT(*) FROM ALUMNOVOTO AS AV "
                + "WHERE AV.IDALUMNO = :idalumno "
                + "AND AV.IDEDICIONESTUDIO = :idedicionestudio "
                + "AND AV.IDPERIODOACADEMICO = :idperiodoacademico "
                + "AND AV.IDCAMPUS = :idcampus ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idperiodoacademico", idPeriodoAcademico);
        params.addValue("idcampus", idCampus);
        params.addValue("idedicionestudio", edicionEstudio);
        params.addValue("idalumno", idalumno);
        int activo = this.getNamedParameterJdbcTemplate().queryForObject(sql, params, Integer.class);
        if (activo > 0) {
            return true;
        } else {
            return false;
        }
    }

    //antes isver

    @Override
    public boolean isVerDelegados(int idPeriodoAcademico, int idCampus, int edicionEstudio) {
        String sql = "SELECT COUNT(*) FROM ELECCIONDELEGADOS "
                + "WHERE IDPERIODOACADEMICO = :idPeriodo AND IDCAMPUS = :idCampus "
                + " AND IDEDICIONESTUDIO = :idedicionestudio "
                + "AND GETDATE() < FECHAFINVERDELEGADOS";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idPeriodo", idPeriodoAcademico);
        params.addValue("idCampus", idCampus);
        params.addValue("idedicionestudio", edicionEstudio);

        int activo = this.getNamedParameterJdbcTemplate().queryForObject(sql, params, Integer.class);
        if (activo > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isVerBotonElegir(int idPeriodoAcademico, int idCampus, int edicionEstudio) {
        String sql = "SELECT COUNT(*) FROM ELECCIONDELEGADOS "
                + "WHERE IDPERIODOACADEMICO = :idPeriodo AND IDCAMPUS = :idCampus "
                + " AND IDEDICIONESTUDIO = :idedicionestudio "
                + "AND GETDATE() > FECHAFINVERDELEGADOS AND GETDATE() < FECHAFINELEGIRDELEGADOS ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idPeriodo", idPeriodoAcademico);
        params.addValue("idCampus", idCampus);
        params.addValue("idedicionestudio", edicionEstudio);

        int activo = this.getNamedParameterJdbcTemplate().queryForObject(sql, params, Integer.class);
        if (activo > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isVerNiveles(int edicionestudio, int campus, int periodo){
        String sql = "SELECT VERNIVELES FROM ELECCIONDELEGADOS "
                + "WHERE IDCAMPUS = ? AND IDEDICIONESTUDIO = ? AND IDPERIODOACADEMICO = ?";
        List<Boolean> list = this.getJdbcTemplate().queryForList(sql,
                new Object[]{campus,
                    edicionestudio,
                    periodo}, Boolean.class);

        if (list.isEmpty()) {
            return false;
        }
        return list.get(0);
    
    }
    @Override
    public List<Alumnocandidato> getAlumnoCandidatoLista(int edicionestudio, int campus, int periodo, int nivel) {
        boolean verniveles=this.isVerNiveles(edicionestudio, campus, periodo);
        String niveles="";
        if(!verniveles)niveles=" AND AC.NIVEL="+nivel;
        String sql = "SELECT * FROM ALUMNOCANDIDATO AS AC, PERSONA AS P "
                + "WHERE AC.IDALUMNO = P.IDPERSONA "
                + "AND AC.IDEDICIONESTUDIO = ? "
                + "AND AC.IDPERIODOACADEMICO = ? "
                + "AND AC.IDCAMPUS = ? "
                + niveles
                + " ORDER BY P.APELLIDOPATERNO, P.APELLIDOMATERNO, P.NOMBRES";
        List<Alumnocandidato> candidatos = this.getJdbcTemplate().query(sql,
                new Object[]{edicionestudio, periodo, campus},
                UtilRowMapper.getAlumnocandidatoMapper());
       
        return candidatos;
    }

    @Override
    public int guardarvotaciondelegado(int campus, int periodoacademico, int alumnocandidato, int idalumno, int idedicionestudio) {
        boolean yavoto=false;
        yavoto=this.getAlumnoVoto(periodoacademico, campus, idedicionestudio, idalumno);
        if(yavoto)
            return 0;
        else{
        String sql = "INSERT INTO ALUMNOVOTO (IDCAMPUS,IDPERIODOACADEMICO,IDALUMNOCANDIDATO,IDALUMNO,IDEDICIONESTUDIO) "
                + " VALUES(?,?,?,?,?)";
        int result = this.getJdbcTemplate().update(sql,
                new Object[]{campus, periodoacademico, alumnocandidato, idalumno, idedicionestudio});
        
        
        return result;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map> getListRequisitosPlanEstudio(int idAlumno, int idEdicionEstudio) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.getJdbcTemplate())
                .withProcedureName(BDConstants.SP_REQUISITOPLANESTUDIOS)
                .returningResultSet("resulset", new RowMapper() {

                    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Map data = new HashMap();
                        data.put("requisito", rs.getString("REQUISITO"));
                        data.put("valor", rs.getString("VALOR"));
                        data.put("cumple", rs.getBoolean("CUMPLIDO"));
                        data.put("actual", rs.getString("ACTUAL"));
                        return data;
                    }
                });

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("IDALUMNO", idAlumno)
                .addValue("IDEDICIONESTUDIO", idEdicionEstudio);

        List<Map> rs = (List<Map>) simpleJdbcCall.execute(params).get("resulset");
        return rs;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map> getListRequisitosTitulo(int idAlumno, int idEdicionEstudio) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.getJdbcTemplate())
                .withProcedureName(BDConstants.SP_REQUISITOTITULO)
                .returningResultSet("resulset", new RowMapper() {

                    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Map data = new HashMap();
                        data.put("requisito", rs.getString("VALOR"));
                        data.put("cumple", rs.getBoolean("CUMPLIDO"));
                        data.put("actual", rs.getString("ACTUAL"));
                        return data;
                    }
                });

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("IDALUMNO", idAlumno)
                .addValue("IDEDICIONESTUDIO", idEdicionEstudio);

        List<Map> rs = (List<Map>) simpleJdbcCall.execute(params).get("resulset");
        return rs;
    }
}
