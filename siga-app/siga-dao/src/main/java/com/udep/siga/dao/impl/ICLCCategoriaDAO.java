package com.udep.siga.dao.impl;

import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.AsignaturaHistorial;
import com.udep.siga.bean.AsignaturaSeccion;
import com.udep.siga.bean.CLCCategActividadDeportivaDest;
import com.udep.siga.bean.CLCCategActividadDisney;
import com.udep.siga.bean.CLCCategActividadInvestigacion;
import com.udep.siga.bean.CLCCategActividadProyectoSocial;
import com.udep.siga.bean.CLCCategAsigIntercambioEstud;
import com.udep.siga.bean.CLCCategCapacitacionProfesional;
import com.udep.siga.bean.CLCCategEstudioDirigido;
import com.udep.siga.bean.CLCCategPractPreProfesional;
import com.udep.siga.bean.CLCCategoriaActividad;
import com.udep.siga.bean.CLCCategoriaAlumno;
import com.udep.siga.bean.CLCCategoriaIdioma;
import com.udep.siga.bean.Nota;
import com.udep.siga.bean.PeriodoAcademico;
import com.udep.siga.dao.CLCCategoriaDAO;
import com.udep.siga.dao.EvaluacionDAO;
import com.udep.siga.dao.util.CustomizeJdbcDaoSupport;
import com.udep.siga.dao.util.UtilRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrador
 */
@Repository("clcCategoriaDAO")
public class ICLCCategoriaDAO extends CustomizeJdbcDaoSupport implements CLCCategoriaDAO {

    @Autowired
    private EvaluacionDAO evaluacionDAO;

    public CLCCategoriaAlumno getCategoriaCLC(int idCategoria) {
        String sql = "SELECT C.NOMBRE, C.MAXVALOR, M.NOMBRE 'MODALIDAD' "
                + "FROM CLC_CATEGORIA C, CLC_MODALIDAD M "
                + "WHERE C.IDMODALIDAD = M.ID AND C.ID = :idCategoria";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idCategoria", idCategoria);

        List<CLCCategoriaAlumno> clcCategoria = this.getNamedParameterJdbcTemplate()
                .query(sql, params, UtilRowMapper.getCLCCategoriaAlumnoMapper());

        if (clcCategoria.isEmpty()) {
            return null;
        } else {
            return clcCategoria.get(0);
        }
    }

    public List<CLCCategoriaActividad> getCategActividadArtisticaDestacada(int idAlumno, int idEdicionesEstudio) {
        String sql = "SELECT A.ID,A.FECHA, AC.NOMBRE 'ACTIVIDAD', I.NOMBRE 'INSTITUCION',"
                + "A.HORASDEDICADA,A.TASACREDITO,A.TASAHORA,A.TASATOTALCRED "
                + "FROM CLC_CAT_ACTARTDEST A, CLC_ACTIVIDAD AC, CLC_INSTITUCION I "
                + "WHERE A.ACTIVIDAD_ID = AC.ID AND A.INSTITUCION_ID = I.ID "
                + "AND A.ALUMNO_ID = :idAlumno AND A.EDICIONESTUDIO_ID = :idEdicionesEstudio";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", idAlumno);
        params.addValue("idEdicionesEstudio", idEdicionesEstudio);

        List<CLCCategoriaActividad> list = this.getNamedParameterJdbcTemplate().query(sql,
                params, UtilRowMapper.getCLCCategoriaActividadMapper());
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    public List<CLCCategActividadInvestigacion> getCategActividadInvestigacion(int idAlumno, int idEdicionesEstudio) {
        String sql = "SELECT A.ID, A.FECHA, P.APELLIDOPATERNO,P.APELLIDOMATERNO, P.NOMBRES, "
                + "A.NOMBREINVESTIGACION,A.HORASRECONOCIDA,A.TASACREDITO,A.TASAHORA,"
                + "A.TASATOTALCRED FROM CLC_CAT_ACTINVEST A, PROFESORDEPARTAMENTO PD, PERSONA P "
                + "WHERE A.PROFESORDEPARTAMENTO_ID = PD.IDPROFESORDEPARTAMENTO "
                + "AND PD.IDPROFESOR = P.IDPERSONA "
                + "AND A.ALUMNO_ID = :idAlumno AND A.EDICIONESTUDIO_ID = :idEdicionesEstudio";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", idAlumno);
        params.addValue("idEdicionesEstudio", idEdicionesEstudio);

        List<CLCCategActividadInvestigacion> list = this.getNamedParameterJdbcTemplate().query(sql,
                params, UtilRowMapper.getCLCCategActividadInvestigacionMapper());

        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    public List<CLCCategActividadProyectoSocial> getCategActividadProyeSocial(int idAlumno, int idEdicionesEstudio, int tipoVoluntariado) {
        String sql = "SELECT A.ID, A.FECHA, I.NOMBRE 'ORGANIZADORA', I2.NOMBRE 'BENEFICIADA',"
                + "A.HORASDEDICADA,A.TASACREDITO,A.TASAHORA,A.TASATOTALCRED "
                + "FROM CLC_CAT_ACTPROYSOCIAL A, CLC_INSTITUCION I, CLC_INSTITUCION I2 "
                + "WHERE A.INSTITUCIONORG_ID = I.ID AND A.INSTITUCIONBENEF_ID = I2.ID  "
                + "AND A.ALUMNO_ID = :idAlumno AND A.EDICIONESTUDIO_ID = :idEdicionesEstudio "
                + "AND A.TIPOVOLUNTARIADO_ID = :tipoVoluntariado";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", idAlumno);
        params.addValue("idEdicionesEstudio", idEdicionesEstudio);
        params.addValue("tipoVoluntariado", tipoVoluntariado);

        List<CLCCategActividadProyectoSocial> list = this.getNamedParameterJdbcTemplate().query(sql,
                params, UtilRowMapper.getCLCCategActividadProyectoSocialMapper());

        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    public List<CLCCategActividadDeportivaDest> getCategActividadDeportivaDest(int idAlumno, int idEdicionesEstudio) {
        String sql = "SELECT A.ID,A.FECHA, D.NOMBRE 'DEPORTE', I.NOMBRE 'INSTITUCION', "
                + "P.NOMBRE 'SEMESTRE', A.CREDRECONOCIDO FROM CLC_CAT_ACTDEPORTDEST A, "
                + "CLC_DEPORTE D, CLC_INSTITUCION I, PERIODOACADEMICO P "
                + "WHERE A.DEPORTE_ID = D.ID AND A.INSTITUCION_ID = I.ID "
                + "AND A.IDPERIODOACADEMICO = P.IDPERIODOACADEMICO  "
                + "AND A.IDALUMNO = :idAlumno AND A.IDEDICIONESTUDIO = :idEdicionesEstudio ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", idAlumno);
        params.addValue("idEdicionesEstudio", idEdicionesEstudio);

        List<CLCCategActividadDeportivaDest> list = this.getNamedParameterJdbcTemplate().query(sql,
                params, UtilRowMapper.getCLCCategActividadDeportivaDestMapper());

        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    public List<CLCCategoriaActividad> getCategActividadExtraConvenio(int idAlumno, int idEdicionesEstudio) {
        String sql = "SELECT C.ID,C.FECHA,A.NOMBRE 'ACTIVIDAD',I.NOMBRE 'INSTITUCION',"
                + "C.HORASDEDICADA,C.TASACREDITO,C.TASAHORA,C.TASATOTALCRED "
                + "FROM CLC_CAT_ACTEXTRACONVENIO C, CLC_ACTIVIDAD A, CLC_INSTITUCION I "
                + "WHERE C.ACTIVIDAD_ID = A.ID AND C.INSTITUCION_ID = I.ID "
                + "AND C.ALUMNO_ID = :idAlumno AND C.EDICIONESTUDIO_ID = :idEdicionesEstudio ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", idAlumno);
        params.addValue("idEdicionesEstudio", idEdicionesEstudio);

        List<CLCCategoriaActividad> list = this.getNamedParameterJdbcTemplate().query(sql,
                params, UtilRowMapper.getCLCCategoriaActividadMapper());

        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    public List<CLCCategActividadDisney> getCategActividadDisney(int idAlumno, int idEdicionesEstudio) {
        String sql = "SELECT C.ID,C.FECHA, A.NOMBRE 'ASIGNATURA',C.HORASRECONOCIDO,"
                + "C.TASACREDITO,C.TASAHORA,C.TASATOTALCRED "
                + "FROM CLC_CAT_PROGDISNEY C, CLC_ASIGNATURA A WHERE C.ASIGNATURA_ID = A.ID "
                + "AND C.ALUMNO_ID = :idAlumno AND C.EDICIONESTUDIO_ID = :idEdicionesEstudio ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", idAlumno);
        params.addValue("idEdicionesEstudio", idEdicionesEstudio);

        List<CLCCategActividadDisney> list = this.getNamedParameterJdbcTemplate().query(sql,
                params, UtilRowMapper.getCLCCategActividadDisneyMapper());

        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    public List<CLCCategAsigIntercambioEstud> getCategAsigIntercambioEstud(int idAlumno, int idEdicionesEstudio) {
        String sql = "SELECT C.ID,C.FECHA, A.NOMBRE 'ASIGNATURA', I.NOMBRE 'INSTITUCION', "
                + "P.NOMBRE 'SEMESTRE', C.CREDRECONOCIDO FROM CLC_CAT_ASIGINTERESTUDIANTIL C, "
                + "CLC_ASIGNATURA A, CLC_INSTITUCION I, PERIODOACADEMICO P "
                + "WHERE C.ASIGNATURA_ID = A.ID AND C.INSTITUCION_ID = I.ID "
                + "AND C.IDPERIODOACADEMICO = P.IDPERIODOACADEMICO "
                + "AND C.ALUMNO_ID = :idAlumno AND C.EDICIONESTUDIO_ID = :idEdicionesEstudio ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", idAlumno);
        params.addValue("idEdicionesEstudio", idEdicionesEstudio);

        List<CLCCategAsigIntercambioEstud> list = this.getNamedParameterJdbcTemplate().query(sql,
                params, UtilRowMapper.getCLCCategAsigIntercambioEstudMapper());

        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    public List<CLCCategCapacitacionProfesional> getCategCapacitacionProfesional(int idAlumno, int idEdicionesEstudio) {
        String sql = "SELECT C.ID,C.FECHA, TE.NOMBRE 'TIPO', C.NOMBREEVENTO,"
                + "C.HORASRECONOCIDA,C.TASACREDITO,C.TASAHORA,C.TASATOTALCRED "
                + "FROM CLC_CAT_HORASCAPACPROF C, CLC_TIPOEVENTO TE WHERE C.TIPOEVENTO_ID = TE.ID "
                + "AND C.ALUMNO_ID = :idAlumno AND C.EDICIONESTUDIO_ID = :idEdicionesEstudio ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", idAlumno);
        params.addValue("idEdicionesEstudio", idEdicionesEstudio);

        List<CLCCategCapacitacionProfesional> list = this.getNamedParameterJdbcTemplate().query(sql,
                params, UtilRowMapper.getCLCCategCapacitacionProfesionalMapper());

        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    public List<CLCCategEstudioDirigido> getCategEstudioDirigido(int idAlumno, int idEdicionesEstudio) {
        String sql = "SELECT C.ID,C.FECHA,A.NOMBRE 'ASIGNATURA', P.APELLIDOPATERNO,"
                + "P.APELLIDOMATERNO, P.NOMBRES,C.HORASRECONOCIDA,C.TASACREDITO,C.TASAHORA,"
                + "C.TASATOTALCRED FROM CLC_CAT_HORASESTDIRIGIDO C, ASIGNATURA A,"
                + "PROFESORDEPARTAMENTO PD, PERSONA P WHERE C.ASIGNATURA_ID = A.IDASIGNATURA "
                + "AND C.PROFESORDEPARTAMENTO_ID = PD.IDPROFESORDEPARTAMENTO "
                + "AND PD.IDPROFESOR = P.IDPERSONA "
                + "AND C.ALUMNO_ID = :idAlumno AND C.EDICIONESTUDIO_ID = :idEdicionesEstudio ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", idAlumno);
        params.addValue("idEdicionesEstudio", idEdicionesEstudio);

        List<CLCCategEstudioDirigido> list = this.getNamedParameterJdbcTemplate().query(sql,
                params, UtilRowMapper.getCLCCategEstudioDirigidoMapper());

        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    public List<CLCCategoriaIdioma> getCategoriaIdioma(int idAlumno, int idEdicionesEstudio) {
        String sql = "SELECT C.ID,C.FECHA, I.NOMBRE 'IDIOMA', CASE WHEN (C.CATEGORIA_ID = 12) "
                + "THEN 'CUARTO IDIOMA' ELSE 'TERCER IDIOMA' END 'NUMERO', "
                + "CASE WHEN (C.CATEGORIA_ID = 11) THEN 'Nivel Intermedio' ELSE 'Nivel Basico' "
                + "END 'NIVEL',C.CREDRECONOCIDO FROM CLC_CAT_IDIOMA C, CLC_IDIOMA I "
                + "WHERE C.IDIOMA_ID = I.ID "
                + "AND C.ALUMNO_ID = :idAlumno AND C.EDICIONESTUDIO_ID = :idEdicionesEstudio ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", idAlumno);
        params.addValue("idEdicionesEstudio", idEdicionesEstudio);

        List<CLCCategoriaIdioma> list = this.getNamedParameterJdbcTemplate().query(sql,
                params, UtilRowMapper.getCLCCategoriaIdiomaMapper());

        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    public List<CLCCategPractPreProfesional> getCategHorasPractProfesionales(int idAlumno, int idEdicionesEstudio) {
        String sql = "SELECT C.ID,C .FECHA, E.NOMBRE 'EMPRESA', C.PERIODODESDE,"
                + "C.PERIODOHASTA ,C.HORASRECONOCIDA,C.TASACREDITO,C.TASAHORA,C.TASATOTALCRED "
                + "FROM CLC_CAT_PRACTPREPROFESIONAL C, CLC_EMPRESA E WHERE C.EMPRESA_ID = E.ID "
                + "AND C.ALUMNO_ID = :idAlumno AND C.EDICIONESTUDIO_ID = :idEdicionesEstudio ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", idAlumno);
        params.addValue("idEdicionesEstudio", idEdicionesEstudio);

        List<CLCCategPractPreProfesional> list = this.getNamedParameterJdbcTemplate().query(sql,
                params, UtilRowMapper.getCLCCategPractPreProfesionalMapper());

        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    public List<AsignaturaHistorial> getClcOtrosPlanesDeEstudio(int idAlumno, int idEdicionesEstudio) {
        String sql = "SELECT P.NOMBRE 'PERIODO',A.CODIGO, A.NOMBRE 'ASIGNATURA',"
                + "ASE.DESCRIPCION 'SECCION', N.IDNOTA, E.SIGLA, A.CREDITOS "
                + "FROM ALUMNOHISTORIAL AH LEFT OUTER JOIN ASIGNATURASECCION ASE "
                + "ON AH.IDSECCION = ASE.IDSECCION AND AH.IDASIGNATURADICTADA = ASE.IDASIGNATURADICTADA, "
                + "NOTA N, ASIGNATURA A, PERIODOACADEMICO P, EDICIONESTUDIO EE, ESTUDIO E WHERE AH.CLC = 1 "
                + "AND AH.RETIROCURSO = 0 AND N.IDNOTA = AH.IDNOTA "
                + "AND AH.IDASIGNATURA = A.IDASIGNATURA "
                + "AND AH.IDPERIODOACADEMICO = P.IDPERIODOACADEMICO "
                + "AND AH.IDEDICIONESTUDIO = EE.IDEDICIONESTUDIO AND EE.IDESTUDIO = E.IDESTUDIO "
                + "AND AH.IDALUMNO = :idAlumno AND AH.IDEDICIONESTUDIO = :idEdicionesEstudio ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", idAlumno);
        params.addValue("idEdicionesEstudio", idEdicionesEstudio);

        List<AsignaturaHistorial> list = this.getNamedParameterJdbcTemplate().query(
                sql, params, new RowMapper() {
            public AsignaturaHistorial mapRow(ResultSet rs, int rowNum) throws SQLException {
                AsignaturaHistorial asignaturaHistorial = new AsignaturaHistorial();

                asignaturaHistorial.setSigla(rs.getString("CODIGO"));
                asignaturaHistorial.setNombre(rs.getString("ASIGNATURA"));
                asignaturaHistorial.setCreditos(rs.getInt("CREDITOS"));
                asignaturaHistorial.setPromedio(new Nota(rs.getInt("IDNOTA")));
                if (asignaturaHistorial.getPromedio().getId() != 0) {
                    asignaturaHistorial.setPromedio(
                            evaluacionDAO.getNota(asignaturaHistorial.getPromedio().getId()));
                }
                PeriodoAcademico periodoAcademico = new PeriodoAcademico();
                periodoAcademico.setNombre(rs.getString("PERIODO"));
                asignaturaHistorial.setPeriodoAcademico(periodoAcademico);
                asignaturaHistorial.setEstudio(rs.getString("SIGLA"));
                AsignaturaSeccion asignaturaSeccion = new AsignaturaSeccion();
                asignaturaSeccion.setDescripcion(rs.getString("SECCION"));
                asignaturaHistorial.setAsignaturaSeccion(asignaturaSeccion);

                return asignaturaHistorial;
            }
        });

        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    /* 
     * CLCObtenido de cada Categoria 
     */
    public float getCLCObtenidoByAlumnoCateg(AlumnoEstudio alumnoEstudio, String tablaCLC) {
        String sql = "SELECT ISNULL(SUM(TASATOTALCRED),0) 'TOTAL' FROM " + tablaCLC + " "
                + "WHERE ALUMNO_ID = :idAlumno AND EDICIONESTUDIO_ID = :idEdicionesEstudio";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", alumnoEstudio.getAlumno().getId());
        params.addValue("idEdicionesEstudio", alumnoEstudio.getEdicionestudio().getId());

        List<Float> maxList = this.getNamedParameterJdbcTemplate().queryForList(sql, params, Float.class);
        if (maxList.isEmpty()) {
            return 0;
        } else {
            return maxList.get(0);
        }
    }

    public int getCLCObtenidoByAlumnoIntercambioEst(AlumnoEstudio alumnoEstudio) {
        String sql = "SELECT ISNULL(SUM(CREDRECONOCIDO),0) 'TOTAL' FROM CLC_CAT_ASIGINTERESTUDIANTIL "
                + "WHERE ALUMNO_ID = :idAlumno AND EDICIONESTUDIO_ID = :idEdicionesEstudio";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", alumnoEstudio.getAlumno().getId());
        params.addValue("idEdicionesEstudio", alumnoEstudio.getEdicionestudio().getId());

        List<Integer> maxList = this.getNamedParameterJdbcTemplate().queryForList(sql, params, Integer.class);
        if (maxList.isEmpty()) {
            return 0;
        } else {
            return maxList.get(0);
        }
    }

    public float getCLCObtenidoByAlumnoProySocial(AlumnoEstudio alumnoEstudio, int tipoVoluntariado) {
        String sql = "SELECT ISNULL(SUM(TASATOTALCRED),0) 'TOTAL' FROM CLC_CAT_ACTPROYSOCIAL "
                + "WHERE ALUMNO_ID = :idAlumno AND EDICIONESTUDIO_ID = :idEdicionesEstudio "
                + "AND TIPOVOLUNTARIADO_ID = :tipoVoluntariado ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", alumnoEstudio.getAlumno().getId());
        params.addValue("idEdicionesEstudio", alumnoEstudio.getEdicionestudio().getId());
        params.addValue("tipoVoluntariado", tipoVoluntariado);

        List<Float> maxList = this.getNamedParameterJdbcTemplate().queryForList(sql, params, Float.class);
        if (maxList.isEmpty()) {
            return 0;
        } else {
            return maxList.get(0);
        }
    }

    public int getCLCObtenidoByAlumnoDeportivaDest(AlumnoEstudio alumnoEstudio, int maxValor) {
        String sql = "SELECT CASE WHEN ISNULL(SUM(CREDRECONOCIDO),0) > :maxValor THEN :maxValor "
                + "ELSE ISNULL(SUM(CREDRECONOCIDO),0) END 'TOTAL' FROM CLC_CAT_ACTDEPORTDEST "
                + "WHERE IDALUMNO = :idAlumno AND IDEDICIONESTUDIO = :idEdicionesEstudio ";
                //+ "GROUP BY IDPERIODOACADEMICO ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", alumnoEstudio.getAlumno().getId());
        params.addValue("idEdicionesEstudio", alumnoEstudio.getEdicionestudio().getId());
        params.addValue("maxValor", maxValor);

        List<Integer> maxList = this.getNamedParameterJdbcTemplate().queryForList(sql, params, Integer.class);
        if (maxList.isEmpty()) {
            return 0;
        } else {
            return maxList.get(0);
        }
    }

    public int getCLCObtenidoByOtrosPlanesDeEstudio(AlumnoEstudio alumnoEstudio) {
        String sql = "SELECT ISNULL(SUM(A.CREDITOS),0) 'TOTAL' FROM ALUMNOHISTORIAL AH, NOTA N, ASIGNATURA A "
                + "WHERE AH.CLC = 1 AND AH.RETIROCURSO = 0 AND N.IDNOTA = AH.IDNOTA "
                + "AND	AH.IDALUMNO = :idAlumno AND AH.IDEDICIONESTUDIO = :idEdicionesEstudio "
                + "AND N.APROBADA = 1 AND AH.IDASIGNATURA = A.IDASIGNATURA ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", alumnoEstudio.getAlumno().getId());
        params.addValue("idEdicionesEstudio", alumnoEstudio.getEdicionestudio().getId());

        List<Integer> maxList = this.getNamedParameterJdbcTemplate().queryForList(sql, params, Integer.class);
        if (maxList.isEmpty()) {
            return 0;
        } else {
            return maxList.get(0);
        }
    }

    public int getCLCObtenidoByIdiomas(AlumnoEstudio alumnoEstudio, int maxValor) {
        String sql = "SELECT CASE WHEN ISNULL(SUM(CREDRECONOCIDO),0) > :maxValor THEN :maxValor "
                + "ELSE 0 END 'TOTAL' FROM CLC_CAT_IDIOMA "
                + "WHERE ALUMNO_ID = :idAlumno AND EDICIONESTUDIO_ID = :idEdicionesEstudio";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAlumno", alumnoEstudio.getAlumno().getId());
        params.addValue("idEdicionesEstudio", alumnoEstudio.getEdicionestudio().getId());
        params.addValue("maxValor", maxValor);

        List<Integer> maxList = this.getNamedParameterJdbcTemplate().queryForList(sql, params, Integer.class);
        if (maxList.isEmpty()) {
            return 0;
        } else {
            return maxList.get(0);
        }
    }
}
