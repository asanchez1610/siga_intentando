package com.udep.siga.dao.util;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import com.udep.siga.bean.*;
import com.udep.siga.bean.enumeration.Campus;
import com.udep.siga.bean.enumeration.Dia;
import com.udep.siga.bean.enumeration.EstadoAlumno;
import com.udep.siga.bean.enumeration.EstadoConsulta;
import com.udep.siga.bean.enumeration.EstadoDocumentoOficial;
import com.udep.siga.bean.enumeration.EstadoEvaluacion;
import com.udep.siga.bean.enumeration.EstadoInvestigacion;
import com.udep.siga.bean.enumeration.EstadoPagoCuota;
import com.udep.siga.bean.enumeration.EstadoSolicitud;
import com.udep.siga.bean.enumeration.Idioma;
import com.udep.siga.bean.enumeration.PeriodicidadPlanEstudio;
import com.udep.siga.bean.enumeration.RestriccionPublicacion;
import com.udep.siga.bean.enumeration.Sexo;
import com.udep.siga.bean.enumeration.SistemaHorario;
import com.udep.siga.bean.enumeration.TipoArticulo;
import com.udep.siga.bean.enumeration.TipoAsignatura;
import com.udep.siga.bean.enumeration.TipoClase;
import com.udep.siga.bean.enumeration.TipoDocIdentidad;
import com.udep.siga.bean.enumeration.TipoEmail;
import com.udep.siga.bean.enumeration.TipoEstudio;
import com.udep.siga.bean.enumeration.TipoExalumno;
import com.udep.siga.bean.enumeration.TipoGrado;
import com.udep.siga.bean.enumeration.TipoPagoEspecial;
import com.udep.siga.bean.enumeration.TipoPeriodoAcademico;
import com.udep.siga.bean.enumeration.TipoRequisitoPlan;
import com.udep.siga.bean.enumeration.TipoTesis;

/**
 *
 * @author Wilfredo Atoche
 */
public class UtilRowMapper {

    /*
     * Util para Entidad Usuario
     */
    public static RowMapper getUsuarioMapper() {
        return new UsuarioRowMapperImpl();
    }

    private static final class UsuarioRowMapperImpl implements RowMapper {

        public UsuarioRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Usuario usuario = new Usuario();
            usuario.setId(rs.getInt("IDPERSONA"));
            usuario.setLogin(rs.getString("LOGIN"));
//            usuario.setPassword(rs.getString("PASS"));
            usuario.setActivo(rs.getBoolean("ACTIVO"));

            return usuario;
        }
    }

    /*
     * Util para Entidad Alumno
     */
    public static RowMapper getAlumnoMapper() {
        return new AlumnoRowMapperImpl();
    }

    private static final class AlumnoRowMapperImpl implements RowMapper {

        public AlumnoRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Alumno alumno = new Alumno();
            alumno.setId(rs.getInt("IDPERSONA"));
            alumno.setTipoDocIdentidad(TipoDocIdentidad.parse(rs.getInt("IDTIPODOCIDENTIDAD")));
            alumno.setSexo(Sexo.parse(rs.getInt("IDSEXO")));
            alumno.setNombres(rs.getString("NOMBRES"));
            alumno.setApellidoPaterno(rs.getString("APELLIDOPATERNO"));
            alumno.setApellidoMaterno(rs.getString("APELLIDOMATERNO"));
            alumno.setDni(rs.getString("DNI"));
            alumno.setFoto(rs.getString("FOTO"));
            alumno.setAsesor(rs.getBoolean("ASESOR"));
            alumno.setCarne(rs.getString("CARNE"));
            alumno.setActualizaClave(rs.getBoolean("ACTUALIZACLAVE"));
            alumno.setActualizaDatos(rs.getBoolean("ACTUALIZADATOS"));
            alumno.setActualizaAsesor(rs.getBoolean("ACTUALIZAASESORES"));

            return alumno;
        }
    }

    /*
     * Util para Entidad Persona
     */
    public static RowMapper getPersonaMapper() {
        return new PersonaRowMapperImpl();
    }

    private static final class PersonaRowMapperImpl implements RowMapper {

        public PersonaRowMapperImpl() {
        }

        public Persona mapRow(ResultSet rs, int rowNum) throws SQLException {
            Persona persona = new Persona();
            persona.setId(rs.getInt("IDPERSONA"));
            persona.setTipoDocIdentidad(TipoDocIdentidad.parse(rs.getInt("IDTIPODOCIDENTIDAD")));
            persona.setSexo(Sexo.parse(rs.getInt("IDSEXO")));
            persona.setNombres(rs.getString("NOMBRES"));
            persona.setApellidoPaterno(rs.getString("APELLIDOPATERNO"));
            persona.setApellidoMaterno(rs.getString("APELLIDOMATERNO"));
            persona.setDni(rs.getString("DNI"));
            persona.setFoto(rs.getString("FOTO"));
            persona.setAsesor(rs.getBoolean("ASESOR"));

            return persona;
        }
    }

    /*
     * Util para Entidad Profesor
     */
    public static RowMapper getProfesorMapper() {
        return new ProfesorRowMapperImpl();
    }

    private static final class ProfesorRowMapperImpl implements RowMapper {

        public ProfesorRowMapperImpl() {
        }

        public Persona mapRow(ResultSet rs, int rowNum) throws SQLException {
            Profesor profesor = new Profesor();
            profesor.setId(rs.getInt("IDPERSONA"));
            profesor.setTipoDocIdentidad(TipoDocIdentidad.parse(rs.getInt("IDTIPODOCIDENTIDAD")));
            profesor.setSexo(Sexo.parse(rs.getInt("IDSEXO")));
            profesor.setNombres(rs.getString("NOMBRES"));
            profesor.setApellidoPaterno(rs.getString("APELLIDOPATERNO"));
            profesor.setApellidoMaterno(rs.getString("APELLIDOMATERNO"));
            profesor.setDni(rs.getString("DNI"));
            profesor.setFoto(rs.getString("FOTO"));
            profesor.setAsesor(rs.getBoolean("ASESOR"));

            return profesor;
        }
    }

    /*
     * Util para Entidad Asesor < Persona
     */
    public static RowMapper getAsesorMapper() {
        return new AsesorRowMapperImpl();
    }

    private static final class AsesorRowMapperImpl implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

            Persona persona = new PersonaRowMapperImpl().mapRow(rs, rowNum);
            persona.setPeriodoAcademicoAsesor(
                    new PeriodoAcademico(rs.getInt("IDPERIODOACADEMICO")));
            return persona;
        }
    }

    /*
     * Util para Entidad AlumnoEstudio
     */
    public static RowMapper getAlumnoEstudioMapper() {
        return new AlumnoEstudioRowMapperImpl();
    }

    private static final class AlumnoEstudioRowMapperImpl implements RowMapper {

        public AlumnoEstudioRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            AlumnoEstudio alumnoEstudio = new AlumnoEstudio();
            alumnoEstudio.setAlumno(new Alumno(rs.getInt("IDALUMNO")));
            alumnoEstudio.setEdicionestudio(new Edicionestudio(rs.getInt("IDEDICIONESTUDIO"), rs.getInt("IDESTUDIO")));
            alumnoEstudio.setCampus(Campus.parse(rs.getInt("IDCAMPUS")));
            alumnoEstudio.setCarne(rs.getString("CARNE"));
            alumnoEstudio.setEstadoAlumno(EstadoAlumno.parse(rs.getInt("IDESTADOALUMNO")));
            alumnoEstudio.setTipoExalumno(TipoExalumno.parse(rs.getInt("IDTIPOEXALUMNO")));
            alumnoEstudio.setCreditosTotalesMatriculados(rs.getInt("CREDITOSTOTALESMATRICULADOS"));
            alumnoEstudio.setCreditosTotalesConvalidados(rs.getInt("CREDITOSTOTALESCONVALIDADOS"));
            alumnoEstudio.setCreditosTotalesAprobados(rs.getInt("CREDITOSTOTALESAPROBADOS"));
            alumnoEstudio.setCreditosTotalesDesaprobados(rs.getInt("CREDITOSTOTALESDESAPROBADOS"));
            alumnoEstudio.setCreditosTotalesCumplidos(rs.getInt("CREDITOSTOTALESCUMPLIDOS"));
            alumnoEstudio.setCreditosPeriodoMatriculados(rs.getInt("CREDITOSPERIODOMATRICULADOS"));
            alumnoEstudio.setCreditosPeriodoConvalidados(rs.getInt("CREDITOSPERIODOCONVALIDADOS"));
            alumnoEstudio.setCreditosPeriodoAprobados(rs.getInt("CREDITOSPERIODOAPROBADOS"));
            alumnoEstudio.setCreditosPeriodoDesaprobados(rs.getInt("CREDITOSPERIODODESAPROBADOS"));
            alumnoEstudio.setCreditosPeriodoCumplidos(rs.getInt("CREDITOSPERIODOCUMPLIDOS"));
            alumnoEstudio.setIndiceAcumulado(rs.getFloat("INDICEACUMULADO"));
            alumnoEstudio.setIndicePeriodo(rs.getFloat("INDICEPERIODO"));
            alumnoEstudio.setIndiceBiperiodo(rs.getFloat("INDICEBIPERIODO"));
            alumnoEstudio.setTercio(rs.getBoolean("TERCIOSUPERIOR"));
            alumnoEstudio.setQuinto(rs.getBoolean("QUINTOSUPERIOR"));
            alumnoEstudio.setOrdenMerito(rs.getInt("ORDENMERITO"));
            alumnoEstudio.setCiclo(rs.getInt("CICLO"));
            alumnoEstudio.setNivel(rs.getInt("NIVEL"));
            alumnoEstudio.setMoroso(rs.getBoolean("MOROSO"));
            alumnoEstudio.setDebe3Cuotas(rs.getBoolean("DEBE3CUOTAS"));
            alumnoEstudio.setAnulaExamen(rs.getBoolean("ANULAEXAMENES"));
            alumnoEstudio.setObservacionGeneral(rs.getString("OBSERVACIONGENERAL"));
            alumnoEstudio.setOrdenMeritoTotal(rs.getInt("ORDENMERITOTOTAL"));
            alumnoEstudio.setPeriodoAcademicoIngreso(new PeriodoAcademico(rs.getInt("IDPERIODOINGRESO")));

            return alumnoEstudio;
        }
    }

    /*
     * Util para Entidad AlumnoEstudioPeriodoAcademico
     */
    public static RowMapper getAlumnoEstudioPeriodoAcademicoMapper() {
        return new AlumnoEstudioPeriodoAcademicoRowMapperImpl();
    }

    private static final class AlumnoEstudioPeriodoAcademicoRowMapperImpl implements RowMapper {

        public AlumnoEstudioPeriodoAcademicoRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            AlumnoEstudioPeriodoAcademico alumnoEstudio = new AlumnoEstudioPeriodoAcademico();
            alumnoEstudio.setAlumno(new Alumno(rs.getInt("IDALUMNO")));
            alumnoEstudio.setEdicionestudio(new Edicionestudio(rs.getInt("IDEDICIONESTUDIO")));
            alumnoEstudio.setCampus(Campus.parse(rs.getInt("IDCAMPUS")));
            alumnoEstudio.setCreditosTotalesMatriculados(rs.getInt("CREDITOSTOTALESMATRICULADOS"));
            alumnoEstudio.setCreditosTotalesConvalidados(rs.getInt("CREDITOSTOTALESCONVALIDADOS"));
            alumnoEstudio.setCreditosTotalesAprobados(rs.getInt("CREDITOSTOTALESAPROBADOS"));
            alumnoEstudio.setCreditosTotalesDesaprobados(rs.getInt("CREDITOSTOTALESDESAPROBADOS"));
            alumnoEstudio.setCreditosTotalesCumplidos(rs.getInt("CREDITOSTOTALESCUMPLIDOS"));
            alumnoEstudio.setCreditosPeriodoMatriculados(rs.getInt("CREDITOSPERIODOMATRICULADOS"));
            alumnoEstudio.setCreditosPeriodoConvalidados(rs.getInt("CREDITOSPERIODOCONVALIDADOS"));
            alumnoEstudio.setCreditosPeriodoAprobados(rs.getInt("CREDITOSPERIODOAPROBADOS"));
            alumnoEstudio.setCreditosPeriodoDesaprobados(rs.getInt("CREDITOSPERIODODESAPROBADOS"));
            alumnoEstudio.setCreditosPeriodoCumplidos(rs.getInt("CREDITOSPERIODOCUMPLIDOS"));
            alumnoEstudio.setIndiceAcumulado(rs.getFloat("INDICEACUMULADO"));
            alumnoEstudio.setIndicePeriodo(rs.getFloat("INDICEPERIODO"));
            alumnoEstudio.setIndiceBiperiodo(rs.getFloat("INDICEBIPERIODO"));
            alumnoEstudio.setTercio(rs.getBoolean("TERCIOSUPERIOR"));
            alumnoEstudio.setQuinto(rs.getBoolean("QUINTOSUPERIOR"));
            alumnoEstudio.setOrdenMerito(rs.getInt("ORDENMERITO"));                        
            alumnoEstudio.setCiclo(rs.getInt("CICLO"));
            alumnoEstudio.setNivel(rs.getInt("NIVEL"));
            alumnoEstudio.setOrdenMeritoTotal(rs.getInt("ORDENMERITOTOTAL"));
            alumnoEstudio.setAnulaCiclo(rs.getBoolean("ANULACICLO"));
            alumnoEstudio.setPeriodoAcademico(new PeriodoAcademico(rs.getInt("IDPERIODOACADEMICO")));

            return alumnoEstudio;
        }
    }

    /*
     * Util para Entidad PeriodoAcademico
     */
    public static RowMapper getPeriodoAcademicoMapper() {
        return new PeriodoAcademicoRowMapperImpl();
    }

    private static final class PeriodoAcademicoRowMapperImpl implements RowMapper {

        public PeriodoAcademicoRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            PeriodoAcademico periodoAcademico = new PeriodoAcademico();
            periodoAcademico.setId(rs.getInt("IDPERIODOACADEMICO"));
            periodoAcademico.setNombre(rs.getString("NOMBRE"));
            periodoAcademico.setPeriodicidadPlanEstudio(
                    PeriodicidadPlanEstudio.parse(rs.getInt("IDPERIODICIDADPLANESTUDIOS")));
            periodoAcademico.setVigente(rs.getBoolean("VIGENTE"));
            periodoAcademico.setEnPreparacion(rs.getBoolean("ENPREPARACION"));
            periodoAcademico.setActivo(rs.getBoolean("ACTIVO"));
            periodoAcademico.setOrden(rs.getInt("ORDEN"));
            periodoAcademico.setFechaInicio(rs.getDate("FECHAINICIO"));
            periodoAcademico.setFechaFin(rs.getDate("FECHAFIN"));
            periodoAcademico.setTipoPeriodoAcademico(TipoPeriodoAcademico.parse(rs.getInt("IDTIPOPERIODOACADEMICO")));

            return periodoAcademico;
        }
    }
    
    
     /*
     * Util para Entidad HorarioEvento
     */
    public static RowMapper getHorarioEventoMapper() {
        return new getHorarioEventoMapperImpl();
    }

    private static final class getHorarioEventoMapperImpl implements RowMapper {

        public getHorarioEventoMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            HorarioEvento horarioevento=new HorarioEvento();
            horarioevento.setIdasignatura(rs.getInt("IDASIGNATURA"));
            horarioevento.setIddia(rs.getInt("IDDIA"));
            horarioevento.setIdhora(rs.getInt("IDHORA"));
            horarioevento.setIdseccion(rs.getInt("IDSECCION"));
            horarioevento.setRetirocurso(rs.getBoolean("RETIROCURSO"));
            horarioevento.setAsignatura(rs.getString("ASIGNATURA"));
            horarioevento.setSigla(rs.getString("SIGLA"));
            horarioevento.setSeccion(rs.getString("SECCION"));
            horarioevento.setAmbiente(rs.getString("AMBIENTE"));
            
           

            return horarioevento;
        }
    }
    

    /*
     * Util para Entidad Aviso
     */
    public static RowMapper getAvisoMapper() {
        return new AvisoRowMapperImpl();
    }

    private static final class AvisoRowMapperImpl implements RowMapper {

        public AvisoRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Aviso aviso = new Aviso();
            aviso.setId(rs.getInt("IDAVISO"));
            aviso.setPersona(new Persona(rs.getInt("IDPERSONA")));
            aviso.setTitulo(rs.getString("TITULO"));
            aviso.setDescripcion(rs.getString("DESCRIPCION"));
            aviso.setFecha(rs.getDate("FECHA"));
            aviso.setCaducidad(rs.getDate("CADUCIDAD"));
            aviso.setGeneral(rs.getBoolean("GENERAL"));

            aviso.setRutaArchivo("");
            if (rs.getString("RUTAARCHIVO") != null) {
                aviso.setRutaArchivo(rs.getString("RUTAARCHIVO"));
            }

            aviso.setParaAlumnos(rs.getBoolean("PARAALUMNOS"));
            aviso.setParaProfesores(rs.getBoolean("PARAPROFESORES"));
            aviso.setStick(rs.getBoolean("STICK"));

            return aviso;
        }
    }

    /*
     * Util para Entidad EdicionEstudio
     */
    public static RowMapper getEdicionEstudioMapper() {
        return new EdicionEstudioRowMapperImpl();
    }

    private static final class EdicionEstudioRowMapperImpl implements RowMapper {

        public EdicionEstudioRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Edicionestudio edicionEstudio = new Edicionestudio();
            edicionEstudio.setId(rs.getInt("IDEDICIONESTUDIO"));
            edicionEstudio.setPeriodicidadPlanEstudio(PeriodicidadPlanEstudio
                    .parse(rs.getInt("IDPERIODICIDADPLANESTUDIOS")));
            edicionEstudio.setEstudio(new Estudio(rs.getInt("IDESTUDIO")));
            edicionEstudio.setSistemaHorario(SistemaHorario.parse(rs.getInt("IDSISTEMAHORARIO")));
            edicionEstudio.getEstudio().setSigla(rs.getString("SIGLA"));
            edicionEstudio.getEstudio().setNombre(rs.getString("NOMBRE"));
            edicionEstudio.getEstudio().setActivo(rs.getBoolean("ACTIVO"));
            edicionEstudio.getEstudio().setCentroAcademico(
                    new CentroAcademico(rs.getInt("IDCENTROACADEMICO")));
            edicionEstudio.getEstudio().getCentroAcademico()
                    .setSigla(rs.getString("SIGLACENTRO"));
            edicionEstudio.getEstudio().getCentroAcademico()
                    .setNombre(rs.getString("CENTROACADEMICO"));
            edicionEstudio.getEstudio().getCentroAcademico()
                    .setActivo(rs.getBoolean("CENTROACTIVO"));
            edicionEstudio.getEstudio().setTipoEstudio(TipoEstudio.parse(rs.getInt("IDTIPOESTUDIO")));

            return edicionEstudio;
        }
    }

    /*
     * Util para Entidad AsignaturaDictada
     */
    public static RowMapper getAsignaturaDictadaMapper() {
        return new AsignaturaDictadaRowMapperImpl();
    }

    private static final class AsignaturaDictadaRowMapperImpl implements RowMapper {

        public AsignaturaDictadaRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            AsignaturaDictada asignaturaDictada = new AsignaturaDictada();
            asignaturaDictada.setId(rs.getInt("IDASIGNATURADICTADA"));
            asignaturaDictada.setSigla(rs.getString("SIGLA"));
            asignaturaDictada.setNombre(rs.getString("NOMBRE"));
            asignaturaDictada.setCreditos(rs.getInt("CREDITOS"));
            asignaturaDictada.setAsignaturaSeccion(new AsignaturaSeccion(rs.getInt("IDSECCION"),
                    rs.getString("NOMBRESECCION"), rs.getString("DESCRIPCION")));
            asignaturaDictada.setPeriodoAcademico(new PeriodoAcademico(rs.getInt("IDPERIODOACADEMICO")));
            asignaturaDictada.setCampus(Campus.parse(rs.getInt("IDCAMPUS")));
            asignaturaDictada.setSeccionAcademicaId(rs.getInt("IDSECCIONACADEMICA"));
            asignaturaDictada.setSeccionAcademica(rs.getString("NOMBRESA"));
            asignaturaDictada.setPromedio(new Nota(rs.getInt("IDNOTA")));
            asignaturaDictada.setRetiroCurso(rs.getBoolean("RETIROCURSO"));
            asignaturaDictada.setVerPromedio(rs.getBoolean("VERPROMEDIO"));
            return asignaturaDictada;
        }
    }

    /*
     * Util para Entidad AsignaturaDictada - Independiente
     */
    public static RowMapper getAsignaturaDictadaIndepMapper() {
        return new AsignaturaDictadaIndepRowMapperImpl();
    }

    private static final class AsignaturaDictadaIndepRowMapperImpl implements RowMapper {

        public AsignaturaDictadaIndepRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            AsignaturaDictada asignaturaDictada = new AsignaturaDictada();
            asignaturaDictada.setId(rs.getInt("IDASIGNATURADICTADA"));
            asignaturaDictada.setSigla(rs.getString("SIGLA"));
            asignaturaDictada.setNombre(rs.getString("NOMBRE"));
            asignaturaDictada.setCreditos(rs.getInt("CREDITOS"));
            asignaturaDictada.setAsignaturaSeccion(new AsignaturaSeccion(rs.getInt("IDSECCION"),
                    rs.getString("NOMBRESECCION"), rs.getString("DESCRIPCION")));
            asignaturaDictada.setPeriodoAcademico(new PeriodoAcademico(rs.getInt("IDPERIODOACADEMICO")));
            asignaturaDictada.setCampus(Campus.parse(rs.getInt("IDCAMPUS")));
            asignaturaDictada.setSeccionAcademicaId(rs.getInt("IDSECCIONACADEMICA"));
            asignaturaDictada.setSeccionAcademica(rs.getString("NOMBRESA"));
            return asignaturaDictada;
        }
    }
    /*
     * Util para Entidad AsignaturaHistorial 
     */

    public static RowMapper getAsignaturaHistorialMapper() {
        return new AsignaturaHistorialRowMapperImpl();
    }

    private static final class AsignaturaHistorialRowMapperImpl implements RowMapper {

        public AsignaturaHistorialRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            AsignaturaHistorial asignaturaHistorial = new AsignaturaHistorial();

            asignaturaHistorial.setId(rs.getInt("IDASIGNATURA"));
            asignaturaHistorial.setSigla(rs.getString("CODIGO"));
            asignaturaHistorial.setNombre(rs.getString("NOMBRE"));
            asignaturaHistorial.setCreditos(rs.getInt("CREDITOS"));
            asignaturaHistorial.setPromedio(new Nota(rs.getInt("IDNOTA")));
            asignaturaHistorial.setRetiroCurso(rs.getBoolean("RETIROCURSO"));
            asignaturaHistorial.setTipoAsignatura(TipoAsignatura.parse(rs.getInt("IDTIPOASIGNATURA")).getSigla());
            asignaturaHistorial.setAnulaCiclo(rs.getBoolean("ANULACICLO"));
            asignaturaHistorial.setPlanEstudio(rs.getString("PLAN"));
            asignaturaHistorial.setEstudio(rs.getString("ESTUDIO"));

            return asignaturaHistorial;
        }
    }
    /*
     * Util para Entidad Mensaje
     */

    public static RowMapper getMensajeMapper() {
        return new MensajeRowMapperImpl();
    }

    private static final class MensajeRowMapperImpl implements RowMapper {

        public MensajeRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Mensaje mensaje = new Mensaje();
            mensaje.setId(rs.getInt("IDMENSAJERIA"));
            mensaje.setPersonaDe(new Persona(rs.getInt("DEIDPERSONA")));
            mensaje.setPersonaPara(new Persona(rs.getInt("PARAIDPERSONA")));
            mensaje.setAsunto(rs.getString("ASUNTO"));
            mensaje.setMensaje(rs.getString("MENSAJE"));
            mensaje.setFechaEnvio(rs.getDate("FECHAENVIO"));
            mensaje.setFechaLeido(rs.getDate("FECHALEIDO"));
            mensaje.setEstadoLeido(rs.getBoolean("ESTADOLEIDO"));
            mensaje.setVisible(rs.getBoolean("VISIBLE"));
            mensaje.setIdTipoMensaje(rs.getInt("IDTIPOMENSAJE"));
            mensaje.setPeriodoAcademico(new PeriodoAcademico(rs.getInt("IDPERIODOACADEMICO")));
            mensaje.setCorrelativo(rs.getInt("CORRELATIVO"));
            mensaje.setFechaEliminado(rs.getDate("FECHAELIMINADO"));
            mensaje.setIdTipoEnvio(rs.getInt("IDTIPOENVIO"));

            return mensaje;
        }
    }

    /*
     * Util para Entidad TipoEvaluacion
     */
    public static RowMapper getTipoEvaluacionMapper() {
        return new TipoEvaluacionRowMapperImpl();
    }

    private static final class TipoEvaluacionRowMapperImpl implements RowMapper {

        public TipoEvaluacionRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            TipoEvaluacion tipoEvaluacion = new TipoEvaluacion();
            tipoEvaluacion.setId(rs.getInt("IDTIPOEVALUACION"));
            tipoEvaluacion.setNombre(rs.getString("DESCRIPCION"));
            return tipoEvaluacion;
        }
    }

    /*
     * Util para Entidad Evaluacion
     */
    public static RowMapper getEvaluacionMapper() {
        return new EvaluacionRowMapperImpl();
    }

    private static final class EvaluacionRowMapperImpl implements RowMapper {

        public EvaluacionRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Evaluacion evaluacion = new Evaluacion();
            evaluacion.setId(rs.getInt("IDEVALUACION"));
            evaluacion.setEstadoEvaluacion(EstadoEvaluacion.parse(rs.getInt("IDESTADOEVALUACION")));
            evaluacion.setTipoEvaluacion(new TipoEvaluacion(rs.getInt("IDTIPOEVALUACION"), rs.getString("TIPOEVALUACION")));
            evaluacion.setAsignaturaDictada(new AsignaturaDictada(rs.getInt("IDASIGNATURADICTADA"), rs.getInt("IDSECCION")));
            evaluacion.setNombre(rs.getString("NOMBRE"));
            evaluacion.setDescripcion(rs.getString("DESCRIPCION"));
            evaluacion.setFechaEvaluacion(rs.getDate("FECHAEVALUACION"));
            evaluacion.setFechaRecojo(rs.getDate("FECHARECOJO"));
            evaluacion.setFechaCalificacion(rs.getDate("FECHACALIFICACION"));
            evaluacion.setFechaEntregaSec(rs.getDate("FECHAENTREGASEC"));
            evaluacion.setFechaEntrega(rs.getDate("FECHAENTREGA"));
            evaluacion.setFechaRecojoRec(rs.getDate("FECHARECOJOREC"));
            evaluacion.setFechaCalificacionRec(rs.getDate("FECHACALIFICACIONREC"));
            evaluacion.setFechaEntregaSecRec(rs.getDate("FECHAENTREGASECREC"));
            evaluacion.setFechaEntregaRec(rs.getDate("FECHAENTREGAREC"));
            evaluacion.setInformativa(rs.getBoolean("INFORMATIVA"));
            evaluacion.setAnulable(rs.getBoolean("ANULABLE"));
            evaluacion.setPeso(rs.getInt("PESO"));
            evaluacion.setConfirmada(rs.getBoolean("CONFIRMADA"));
            evaluacion.setCancelada(rs.getBoolean("CANCELADA"));
            evaluacion.setFechaEntregaRecSec(rs.getDate("FECHAENTREGARECSEC"));
            return evaluacion;
        }
    }

    /*
     * Util para Entidad EvaluacionAlumno
     */
    public static RowMapper getEvaluacionAlumnoMapper() {
        return new EvaluacionAlumnoRowMapperImpl();
    }

    private static final class EvaluacionAlumnoRowMapperImpl implements RowMapper {

        public EvaluacionAlumnoRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            EvaluacionAlumno evaluacionAlumno = new EvaluacionAlumno();
            evaluacionAlumno.setId(rs.getInt("IDEVALUACION"));
            evaluacionAlumno.setNotaInformativa(rs.getString("NOTAINFORMATIVA"));
            evaluacionAlumno.setAnulada(rs.getBoolean("ANULADA"));
            evaluacionAlumno.setNota(null);
            if (rs.getInt("IDNOTA") != 0) {
                evaluacionAlumno.setNota(new Nota(rs.getInt("IDNOTA")));
                evaluacionAlumno.getNota().setDescripcion(rs.getString("DESCRIPCION"));
                evaluacionAlumno.getNota().setColor(rs.getString("COLOR"));
                evaluacionAlumno.getNota().setLiteral(rs.getBoolean("LITERAL"));
                evaluacionAlumno.getNota().setValor(rs.getInt("VALOR"));
            }
            return evaluacionAlumno;
        }
    }

    /*
     * Util para Entidad Nota
     */
    public static RowMapper getNotaMapper() {
        return new NotaRowMapperImpl();
    }

    private static final class NotaRowMapperImpl implements RowMapper {

        public NotaRowMapperImpl() {
        }

        public Nota mapRow(ResultSet rs, int rowNum) throws SQLException {
            Nota nota = new Nota(rs.getInt("IDNOTA"));
            nota.setDescripcion(rs.getString("DESCRIPCION"));
            nota.setColor(rs.getString("COLOR"));
            nota.setAprobada(rs.getBoolean("APROBADA"));
            nota.setValor(rs.getInt("VALOR"));
            return nota;
        }
    }

    /*
     * Util para Entidad EvaluacionAlumno
     */
    public static RowMapper getMaterialMapper() {
        return new MaterialRowMapperImpl();
    }

    private static final class MaterialRowMapperImpl implements RowMapper {

        public MaterialRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Material material = new Material();
            material.setId(rs.getInt("IDMATERIALASIGNATURA"));
            material.setTitulo(rs.getString("NOMBRE"));
            material.setDescripcion(rs.getString("DESCRIPCION"));
            material.setFecha(rs.getTimestamp("FECHA"));
            material.setPublicador(new Persona(rs.getInt("IDPROFESOR")));
            material.setRutaArchivo(rs.getString("RUTAARCHIVO"));
            material.setTamanio(rs.getString("PESO"));
            material.setToCentroCopiado(rs.getBoolean("TOCENTROCOPIADO"));
            material.setNombreArchivo(rs.getString("NOMBREARCHIVO"));

            return material;
        }
    }

    /*
     * Util para Entidad Email
     */
    public static RowMapper getEmailMapper() {
        return new EmailRowMapperImpl();
    }

    private static final class EmailRowMapperImpl implements RowMapper {

        public EmailRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Email email = new Email();
            email.setTipoEmail(TipoEmail.parse(rs.getInt("IDTIPOEMAIL")));
            email.setEmail(rs.getString("EMAIL"));

            return email;
        }
    }
    /*
     * Util para Entidad TramiteAcademico
     */

    public static RowMapper getTramiteAcademicoMapper() {
        return new TramiteAcademicoRowMapperImpl();
    }

    private static final class TramiteAcademicoRowMapperImpl implements RowMapper {

        public TramiteAcademicoRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Solicitud solicitud = new Solicitud();
            solicitud.setId(rs.getInt("IDSOLICITUDACTUALIZACION"));
            solicitud.setFecha(rs.getDate("FECHA"));
            solicitud.setEstadoSolicitud(EstadoSolicitud.parse(rs.getInt("IDESTADOSOLICITUDACTUALIZACION")));
            solicitud.setTipoSolicitud(new TipoSolicitud(rs.getInt("IDTIPOSOLICITUDACTUALIZACION"), rs.getString("NOMBRE")));
            solicitud.setAprobada(rs.getBoolean("APROBADA"));
            solicitud.setObsAlumno(rs.getString("OBSALUMNO"));
            solicitud.setRespuesta(rs.getString("RESPUESTA"));
            solicitud.setFechaConfirmacion(rs.getDate("FECHACONFIRMACION"));

            return solicitud;
        }
    }
    /*
     * Util para Entidad TipoSolicitud
     */

    public static RowMapper getTipoSolicitudMapper() {
        return new TipoSolicitudRowMapperImpl();
    }

    private static final class TipoSolicitudRowMapperImpl implements RowMapper {

        public TipoSolicitudRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            TipoSolicitud tipoSolicitud = new TipoSolicitud();
            tipoSolicitud.setId(rs.getInt("IDTIPOSOLICITUDACTUALIZACION"));
            tipoSolicitud.setNombre(rs.getString("NOMBRE"));

            return tipoSolicitud;
        }
    }
    /*
     * Util para Entidad DocumentoOficial
     */

    public static RowMapper getDocumentoOficialMapper() {
        return new DocumentoOficialRowMapperImpl();
    }

    private static final class DocumentoOficialRowMapperImpl implements RowMapper {

        public DocumentoOficialRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Solicitud solicitud = new Solicitud();
            solicitud.setId(rs.getInt("IDSOLICITUD"));
            solicitud.setFecha(rs.getDate("FECHASOLICITUD"));
            solicitud.setEstadoDocOficial(EstadoDocumentoOficial.parse(rs.getInt("IDESTADOSOLICITUD")));
            solicitud.setTipoSolicitud(new TipoSolicitud(rs.getInt("IDTIPODOCOFICIAL"), rs.getString("NOMBRE")));
            solicitud.setVoucher(rs.getBoolean("VOUCHER"));
            solicitud.setObsAlumno(rs.getString("OBSSOLICITUD"));
            solicitud.setRespuesta(rs.getString("OBSSECRETARIA"));
            solicitud.setNroSolicitud(rs.getString("NROSOLICITUD"));
            solicitud.setIdioma(Idioma.parse(rs.getInt("IDIOMA")));
            solicitud.setCampusEntrega(Campus.parse(rs.getInt("IDCAMPUSENTREGA")));

            return solicitud;
        }
    }
    /*
     * Util para Entidad TipoDocOficial
     */

    public static RowMapper getTipoDocOficialMapper() {
        return new TipoDocOficialRowMapperImpl();
    }

    private static final class TipoDocOficialRowMapperImpl implements RowMapper {

        public TipoDocOficialRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            TipoSolicitud tipoSolicitud = new TipoSolicitud();
            tipoSolicitud.setId(rs.getInt("IDTIPODOCOFICIAL"));
            tipoSolicitud.setNombre(rs.getString("NOMBRE"));

            return tipoSolicitud;
        }
    }
    /*
     * Util para Entidad Silabo
     */

    public static RowMapper getSilaboMapper() {
        return new SilaboRowMapperImpl();
    }

    private static final class SilaboRowMapperImpl implements RowMapper {

        public SilaboRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Silabo silabo = new Silabo();
            silabo.setId(rs.getInt("IDSILABO"));
            silabo.setSumilla(rs.getString("SUMILLA"));
            silabo.setFundamentacion(rs.getString("FUNDAMENTACION"));
            silabo.setAsignaturaDictada(new AsignaturaDictada(rs.getInt("IDASIGNATURADICTADA"), rs.getInt("IDSECCION")));
            silabo.getAsignaturaDictada().setNombre(rs.getString("NOMBRE"));
            silabo.getAsignaturaDictada().setSigla(rs.getString("SIGLA"));
            silabo.getAsignaturaDictada().setCreditos(rs.getInt("CREDITOS"));
            silabo.getAsignaturaDictada().setPeriodoAcademico(new PeriodoAcademico(rs.getInt("IDPERIODOACADEMICO"),
                    rs.getString("PERIODO")));
            silabo.getAsignaturaDictada().getAsignaturaSeccion()
                    .setNombreSeccion(rs.getString("NOMBRESECCION"));
            silabo.setDescripcionEvaluacion(rs.getString("DESCRIPCIONEVALUACION"));

            return silabo;
        }
    }

    /*
     * Util para Entidad PlanEstudio
     */
    public static RowMapper getPlanEstudioMapper() {
        return new PlanEstudioRowMapperImpl();
    }

    private static final class PlanEstudioRowMapperImpl implements RowMapper {

        public PlanEstudioRowMapperImpl() {
        }

        public PlanEstudio mapRow(ResultSet rs, int rowNum) throws SQLException {
            PlanEstudio planEstudio = new PlanEstudio();
            planEstudio.setId(rs.getInt("IDPLANESTUDIOS"));
            planEstudio.setNombre(rs.getString("NOMBRE"));
            planEstudio.setFechaInicio(rs.getDate("FECHAINICIO"));
            planEstudio.setFechaFin(rs.getDate("FECHAFIN"));
            planEstudio.setActivo(rs.getBoolean("ACTIVO"));
            planEstudio.setNumPeriodos(rs.getInt("NUMPERIODOS"));
            planEstudio.setVigente(rs.getBoolean("VIGENTE"));
            planEstudio.setCreditosTotales(rs.getInt("CREDITOSTOTALES"));
            planEstudio.setBloqueado(rs.getBoolean("BLOQUEADO"));

            return planEstudio;
        }
    }
    /*
     * Util para Entidad ObjetivoSilabo
     */

    public static RowMapper getObjetivoSilaboMapper() {
        return new ObjetivoSilaboRowMapperImpl();
    }

    private static final class ObjetivoSilaboRowMapperImpl implements RowMapper {

        public ObjetivoSilaboRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            ObjetivoSilabo objetivoSilabo = new ObjetivoSilabo();
            objetivoSilabo.setId(rs.getInt("IDOBJETIVOSILABO"));
            objetivoSilabo.setNumero(rs.getInt("NUMERO"));
            objetivoSilabo.setDescripcion(rs.getString("DESCRIPCION"));

            return objetivoSilabo;
        }
    }
    /*
     * Util para Entidad UnidadSilabo
     */

    public static RowMapper getUnidadSilaboMapper() {
        return new UnidadSilaboRowMapperImpl();
    }

    private static final class UnidadSilaboRowMapperImpl implements RowMapper {

        public UnidadSilaboRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            UnidadSilabo unidadSilabo = new UnidadSilabo();
            unidadSilabo.setId(rs.getInt("IDUNIDADSILABO"));
            unidadSilabo.setNumero(rs.getInt("NUMERO"));
            unidadSilabo.setDescripcion(rs.getString("DESCRIPCION"));

            return unidadSilabo;
        }
    }
    
    /*
     * Util para Entidad UnidadSilabo
     */

    public static RowMapper getTemaSilaboMapper() {
        return new TemaSilaboRowMapperImpl();
    }

    private static final class TemaSilaboRowMapperImpl implements RowMapper {

        public TemaSilaboRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            TemaSilabo temaSilabo = new TemaSilabo();
            temaSilabo.setId(rs.getInt("IDTEMASILABO"));
            temaSilabo.setNumero(rs.getString("NUMERO"));
            temaSilabo.setTema(rs.getString("TEMA"));
            temaSilabo.setSemana(rs.getString("SEMANA"));
            temaSilabo.setHorasTeoricas(rs.getFloat("HORASTEORICAS"));
            temaSilabo.setHorasPracticas(rs.getFloat("HORASPRACTICAS"));

            return temaSilabo;
        }
    }
    
    /*
     * Util para Entidad UnidadSilabo
     */

    public static RowMapper getEstrategiaSilaboMapper() {
        return new EstrategiaSilaboRowMapperImpl();
    }

    private static final class EstrategiaSilaboRowMapperImpl implements RowMapper {

        public EstrategiaSilaboRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            EstrategiaSilabo estrategiaSilabo = new EstrategiaSilabo();
            estrategiaSilabo.setId(rs.getInt("IDESTRATEGIASILABO"));
            estrategiaSilabo.setTitulo(rs.getString("TITULO"));
            estrategiaSilabo.setDescripcion(rs.getString("DESCRIPCION"));

            return estrategiaSilabo;
        }
    }
    
    /*
     * Util para Entidad RequisitoAsignatura
     */

    public static RowMapper getRequisitoAsignaturaMapper() {
        return new RequisitoAsignaturaRowMapperImpl();
    }

    private static final class RequisitoAsignaturaRowMapperImpl implements RowMapper {

        public RequisitoAsignaturaRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            RequisitoAsignatura requisitoAsignatura = new RequisitoAsignatura();
            requisitoAsignatura.setDescripcion(rs.getString("DESCRIPCION"));
            requisitoAsignatura.setAsignatura(new Asignatura(rs.getString("NOMBRE")));
            requisitoAsignatura.setValor(rs.getInt("VALOR"));

            return requisitoAsignatura;
        }
    }

    /*
     * Util para Entidad RequisitoPlanEstudio
     */
    public static RowMapper getRequisitoPlanEstudioMapper() {
        return new RequisitoPlanEstudioRowMapperImpl();
    }

    private static final class RequisitoPlanEstudioRowMapperImpl implements RowMapper {

        public RequisitoPlanEstudioRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            RequisitoPlanEstudio requisitoPlanEstudio = new RequisitoPlanEstudio();
            requisitoPlanEstudio.setTipoRequisitoPlan(TipoRequisitoPlan.parse(rs.getInt("IDTIPOREQUISITOPLANESTUDIOS")));
            requisitoPlanEstudio.setValor(rs.getString("VALOR"));

            return requisitoPlanEstudio;
        }
    }

    /*
     * Util para Entidad RequisitoTipoAsignatura
     */
    public static RowMapper getRequisitoTipoAsignaturaMapper() {
        return new RequisitoTipoAsignaturaRowMapperImpl();
    }

    private static final class RequisitoTipoAsignaturaRowMapperImpl implements RowMapper {

        public RequisitoTipoAsignaturaRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            RequisitoTipoAsignatura requisitoTipoAsignatura = new RequisitoTipoAsignatura();
            requisitoTipoAsignatura.setTipoAsignatura(TipoAsignatura.parse(rs.getInt("IDTIPOASIGNATURA")));
            requisitoTipoAsignatura.setValor(rs.getString("VALOR"));

            return requisitoTipoAsignatura;
        }
    }

    /*
     * Util para Entidad GradoAcademico
     */
    public static RowMapper getGradoAcademicoMapper() {
        return new GradoAcademicoRowMapperImpl();
    }

    private static final class GradoAcademicoRowMapperImpl implements RowMapper {

        public GradoAcademicoRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            GradoAcademico gradoAcademico = new GradoAcademico();
            gradoAcademico.setId(rs.getInt("IDGRADOACADEMICO"));
            gradoAcademico.setModoObtencion(rs.getString("MODOOBTENCION"));

            return gradoAcademico;
        }
    }

    /*
     * Util para Entidad Horario
     */
    public static RowMapper getHorarioMapper() {
        return new HorarioRowMapperImpl();
    }

    private static final class HorarioRowMapperImpl implements RowMapper {

        public HorarioRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Horario horario = new Horario();
            horario.setAsignaturaDictada(new AsignaturaDictada(
                    rs.getInt("IDASIGNATURADICTADA"), rs.getInt("IDSECCION")));
            horario.setBloqueHorario(new BloqueHorario(rs.getInt("IDBLOQUEHORARIO"),
                    rs.getString("DESCRIPCION"), rs.getInt("ORDEN")));
            horario.setDia(Dia.parse(rs.getInt("IDDIA")));
            horario.setTipoClase(TipoClase.parse(rs.getInt("IDTIPOCLASE")));
            horario.setAula(new Aula(rs.getInt("IDAULA"), rs.getString("NOMBRE"),
                    rs.getInt("CAPACIDADREAL"), rs.getInt("CAPACIDADAFORO")));

            return horario;
        }
    }

    /*
     * Util para Entidad Horario
     */
    public static RowMapper getPracticaProgramadaMapper() {
        return new PracticaProgramadaRowMapperImpl();
    }

    private static final class PracticaProgramadaRowMapperImpl implements RowMapper {

        public PracticaProgramadaRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            PracticaProgramada practicaProgramada = new PracticaProgramada();
            practicaProgramada.setAsignaturaDictada(new AsignaturaDictada(
                    rs.getInt("IDASIGNATURADICTADA"), rs.getInt("IDSECCION")));
            practicaProgramada.setBloqueHorario(new BloqueHorario(rs.getInt("IDBLOQUEHORARIO"),
                    rs.getString("DESCRIPCION"), rs.getInt("ORDEN")));
            practicaProgramada.setDia(Dia.parse(rs.getInt("IDDIA")));
            practicaProgramada.setGrupoPractica(new GrupoPractica(rs.getInt("IDGRUPOPRACTICAS"),
                    rs.getString("NOMBRE"), rs.getString("COMENTARIO"),
                    Campus.parse(rs.getInt("IDCAMPUS"))));

            return practicaProgramada;
        }
    }
    /*
     * Util para Entidad Observacion
     */

    public static RowMapper getObservacionMapper() {
        return new ObservacionRowMapperImpl();
    }

    private static final class ObservacionRowMapperImpl implements RowMapper {

        public ObservacionRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Observacion observacion = new Observacion();
            observacion.setDescripcion(rs.getString("DESCRIPCION"));
            observacion.setValor(rs.getString("VALOR"));
            return observacion;
        }
    }

    /*
     * Util para Entidad DatoPersonal
     */
    public static RowMapper getDatoPersonalMapper() {
        return new DatoPersonalRowMapperImpl();
    }

    private static final class DatoPersonalRowMapperImpl implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

            DatoPersonal datoPersonal = new DatoPersonal();
            datoPersonal.setFechaNacimiento(rs.getDate("FECHANACIMIENTO"));
            datoPersonal.setTelefonoMovil(rs.getString("TELEFONOMOVIL"));
            datoPersonal.setDireccion(rs.getString("DIRECCIONCASA"));
            datoPersonal.setTelefono(rs.getString("TELEFONOCASA"));
            datoPersonal.setDireccionPension(rs.getString("DIRECCIONPENSION"));
            datoPersonal.setTelefonoPension(rs.getString("TELEFONOPENSION"));
            datoPersonal.setTelefonoEmergencia(rs.getString("TELEFONOEMERGENCIA"));
            datoPersonal.setContactoEmergencia(rs.getString("PERSONACONTACTOEMERGENCIA"));
            datoPersonal.setDistrito(new Distrito(rs.getInt("IDDISTRITOCASA")));
            datoPersonal.setDistritoPension(new Distrito(rs.getInt("IDDISTRITOPENSION")));
            datoPersonal.setBlog(rs.getString("BLOG"));
            datoPersonal.setPaginaPersonal(rs.getString("PAGINAPERSONAL"));
            datoPersonal.setTwitter(rs.getString("TWITTER"));
            datoPersonal.setGoogleScholar(rs.getString("GOOGLESCHOLAR"));
            return datoPersonal;
        }
    }

    /*
     * Util para Entidad Distrito Full
     */
    public static RowMapper getDistritoFullMapper() {
        return new DistritoFullRowMapperImpl();
    }

    private static final class DistritoFullRowMapperImpl implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

            Distrito distrito = new Distrito();
            distrito.setId(rs.getInt("IDDISTRITO"));
            distrito.setNombre(rs.getString("DISTRITO"));
            distrito.setProvincia(new Provincia(
                    rs.getInt("IDPROVINCIA"), rs.getString("PROVINCIA")));
            distrito.getProvincia().setDepartamento(new Departamento(
                    rs.getInt("IDDEPARTAMENTO"), rs.getString("DEPARTAMENTO")));
            return distrito;
        }
    }

    /*
     * Util para Entidad Distrito
     */
    public static RowMapper getDistritoMapper() {
        return new DistritoRowMapperImpl();
    }

    private static final class DistritoRowMapperImpl implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

            Distrito distrito = new Distrito();
            distrito.setId(rs.getInt("IDDISTRITO"));
            distrito.setNombre(rs.getString("NOMBRE"));
            return distrito;
        }
    }

    /*
     * Util para Entidad Provincia
     */
    public static RowMapper getProvinciaMapper() {
        return new ProvinciaRowMapperImpl();
    }

    private static final class ProvinciaRowMapperImpl implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

            Provincia provincia = new Provincia();
            provincia.setId(rs.getInt("IDPROVINCIA"));
            provincia.setNombre(rs.getString("NOMBRE"));
            return provincia;
        }
    }

    /*
     * Util para Entidad Departamento
     */
    public static RowMapper getDepartamentoMapper() {
        return new DepartamentoRowMapperImpl();
    }

    private static final class DepartamentoRowMapperImpl implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

            Departamento departamento = new Departamento();
            departamento.setId(rs.getInt("IDDEPARTAMENTO"));
            departamento.setNombre(rs.getString("NOMBRE"));
            return departamento;
        }
    }

    /*
     * Util para Entidad CLCCategoriaAlumno
     */
    public static RowMapper getCLCCategoriaAlumnoMapper() {
        return new CLCCategoriaAlumnoRowMapperImpl();
    }

    private static final class CLCCategoriaAlumnoRowMapperImpl implements RowMapper {

        public CLCCategoriaAlumnoRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            CLCCategoriaAlumno clcCategoria = new CLCCategoriaAlumno();
            clcCategoria.setNombre(rs.getString("NOMBRE"));
            clcCategoria.setModalidad(rs.getString("MODALIDAD"));
            clcCategoria.setMaxValor(rs.getInt("MAXVALOR"));
            return clcCategoria;
        }
    }

    /*
     * Util para Entidad CLCCategoriaActividad
     */
    public static RowMapper getCLCCategoriaActividadMapper() {
        return new CLCCategoriaActividadRowMapperImpl();
    }

    private static final class CLCCategoriaActividadRowMapperImpl implements RowMapper {

        public CLCCategoriaActividadRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            CLCCategoriaActividad clcCategoriaActividad = new CLCCategoriaActividad();
            clcCategoriaActividad.setId(rs.getInt("ID"));
            clcCategoriaActividad.setFecha(rs.getDate("FECHA"));
            clcCategoriaActividad.setActividad(rs.getString("ACTIVIDAD"));
            clcCategoriaActividad.setInstitucion(rs.getString("INSTITUCION"));
            clcCategoriaActividad.setHorasDedicadas(rs.getInt("HORASDEDICADA"));
            clcCategoriaActividad.setTasaCredito(rs.getInt("TASACREDITO"));
            clcCategoriaActividad.setTasaHora(rs.getInt("TASAHORA"));
            clcCategoriaActividad.setTasaTotalCred(rs.getFloat("TASATOTALCRED"));

            return clcCategoriaActividad;
        }
    }

    /*
     * Util para Entidad CLCCategActividadInvestigacion
     */
    public static RowMapper getCLCCategActividadInvestigacionMapper() {
        return new CLCCategActividadInvestigacionRowMapperImpl();
    }

    private static final class CLCCategActividadInvestigacionRowMapperImpl implements RowMapper {

        public CLCCategActividadInvestigacionRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            CLCCategActividadInvestigacion clcCategoriaActividad = new CLCCategActividadInvestigacion();
            clcCategoriaActividad.setId(rs.getInt("ID"));
            clcCategoriaActividad.setFecha(rs.getDate("FECHA"));
            clcCategoriaActividad.setNombre(rs.getString("NOMBREINVESTIGACION"));
            Persona persona = new Persona();
            persona.setApellidoPaterno(rs.getString("APELLIDOPATERNO"));
            persona.setApellidoMaterno(rs.getString("APELLIDOMATERNO"));
            persona.setNombres(rs.getString("NOMBRES"));
            clcCategoriaActividad.setProfesor(persona);
            clcCategoriaActividad.setHorasReconocidas(rs.getInt("HORASRECONOCIDA"));
            clcCategoriaActividad.setTasaCredito(rs.getInt("TASACREDITO"));
            clcCategoriaActividad.setTasaHora(rs.getInt("TASAHORA"));
            clcCategoriaActividad.setTasaTotalCred(rs.getFloat("TASATOTALCRED"));

            return clcCategoriaActividad;
        }
    }

    /*
     * Util para Entidad CLCCategActividadProyectoSocial
     */
    public static RowMapper getCLCCategActividadProyectoSocialMapper() {
        return new CLCCategActividadProyectoSocialRowMapperImpl();
    }

    private static final class CLCCategActividadProyectoSocialRowMapperImpl implements RowMapper {

        public CLCCategActividadProyectoSocialRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            CLCCategActividadProyectoSocial clcCategoriaActividad = new CLCCategActividadProyectoSocial();
            clcCategoriaActividad.setId(rs.getInt("ID"));
            clcCategoriaActividad.setFecha(rs.getDate("FECHA"));
            clcCategoriaActividad.setOrganizadora(rs.getString("ORGANIZADORA"));
            clcCategoriaActividad.setBeneficiada(rs.getString("BENEFICIADA"));
            clcCategoriaActividad.setHorasDedicadas(rs.getInt("HORASDEDICADA"));
            clcCategoriaActividad.setTasaCredito(rs.getInt("TASACREDITO"));
            clcCategoriaActividad.setTasaHora(rs.getInt("TASAHORA"));
            clcCategoriaActividad.setTasaTotalCred(rs.getFloat("TASATOTALCRED"));

            return clcCategoriaActividad;
        }
    }

    /*
     * Util para Entidad CLCCategActividadDeportivaDest
     */
    public static RowMapper getCLCCategActividadDeportivaDestMapper() {
        return new CLCCategActividadDeportivaDestRowMapperImpl();
    }

    private static final class CLCCategActividadDeportivaDestRowMapperImpl implements RowMapper {

        public CLCCategActividadDeportivaDestRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            CLCCategActividadDeportivaDest clcCategoriaActividad = new CLCCategActividadDeportivaDest();
            clcCategoriaActividad.setId(rs.getInt("ID"));
            clcCategoriaActividad.setFecha(rs.getDate("FECHA"));
            clcCategoriaActividad.setDeporte(rs.getString("DEPORTE"));
            clcCategoriaActividad.setInstitucion(rs.getString("INSTITUCION"));
            clcCategoriaActividad.setSemestre(rs.getString("SEMESTRE"));
            clcCategoriaActividad.setCredReconocido(rs.getInt("CREDRECONOCIDO"));

            return clcCategoriaActividad;
        }
    }

    /*
     * Util para Entidad CLCCategActividadDisney
     */
    public static RowMapper getCLCCategActividadDisneyMapper() {
        return new CLCCategActividadDisneyRowMapperImpl();
    }

    private static final class CLCCategActividadDisneyRowMapperImpl implements RowMapper {

        public CLCCategActividadDisneyRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            CLCCategActividadDisney clcCategoriaActividad = new CLCCategActividadDisney();
            clcCategoriaActividad.setId(rs.getInt("ID"));
            clcCategoriaActividad.setFecha(rs.getDate("FECHA"));
            clcCategoriaActividad.setAsignatura(rs.getString("ASIGNATURA"));
            clcCategoriaActividad.setHorasReconocidas(rs.getInt("HORASRECONOCIDO"));
            clcCategoriaActividad.setTasaCredito(rs.getInt("TASACREDITO"));
            clcCategoriaActividad.setTasaHora(rs.getInt("TASAHORA"));
            clcCategoriaActividad.setTasaTotalCred(rs.getFloat("TASATOTALCRED"));

            return clcCategoriaActividad;
        }
    }

    /*
     * Util para Entidad CLCCategAsigIntercambioEstud
     */
    public static RowMapper getCLCCategAsigIntercambioEstudMapper() {
        return new CLCCategAsigIntercambioEstudRowMapperImpl();
    }

    private static final class CLCCategAsigIntercambioEstudRowMapperImpl implements RowMapper {

        public CLCCategAsigIntercambioEstudRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            CLCCategAsigIntercambioEstud clcCategoriaActividad = new CLCCategAsigIntercambioEstud();
            clcCategoriaActividad.setId(rs.getInt("ID"));
            clcCategoriaActividad.setFecha(rs.getDate("FECHA"));
            clcCategoriaActividad.setAsignatura(rs.getString("ASIGNATURA"));
            clcCategoriaActividad.setInstitucion(rs.getString("INSTITUCION"));
            clcCategoriaActividad.setSemestre(rs.getString("SEMESTRE"));
            clcCategoriaActividad.setCredReconocido(rs.getInt("CREDRECONOCIDO"));

            return clcCategoriaActividad;
        }
    }

    /*
     * Util para Entidad CLCCategCapacitacionProfesional
     */
    public static RowMapper getCLCCategCapacitacionProfesionalMapper() {
        return new CLCCategCapacitacionProfesionalRowMapperImpl();
    }

    private static final class CLCCategCapacitacionProfesionalRowMapperImpl implements RowMapper {

        public CLCCategCapacitacionProfesionalRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            CLCCategCapacitacionProfesional clcCategoriaActividad = new CLCCategCapacitacionProfesional();
            clcCategoriaActividad.setId(rs.getInt("ID"));
            clcCategoriaActividad.setFecha(rs.getDate("FECHA"));
            clcCategoriaActividad.setTipoEvento(rs.getString("TIPO"));
            clcCategoriaActividad.setNombreEvento(rs.getString("NOMBREEVENTO"));
            clcCategoriaActividad.setHorasReconocidas(rs.getInt("HORASRECONOCIDA"));
            clcCategoriaActividad.setTasaCredito(rs.getInt("TASACREDITO"));
            clcCategoriaActividad.setTasaHora(rs.getInt("TASAHORA"));
            clcCategoriaActividad.setTasaTotalCred(rs.getFloat("TASATOTALCRED"));

            return clcCategoriaActividad;
        }
    }

    /*
     * Util para Entidad CLCCategEstudioDirigido
     */
    public static RowMapper getCLCCategEstudioDirigidoMapper() {
        return new CLCCategEstudioDirigidoRowMapperImpl();
    }

    private static final class CLCCategEstudioDirigidoRowMapperImpl implements RowMapper {

        public CLCCategEstudioDirigidoRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            CLCCategEstudioDirigido clcCategoriaActividad = new CLCCategEstudioDirigido();
            clcCategoriaActividad.setId(rs.getInt("ID"));
            clcCategoriaActividad.setFecha(rs.getDate("FECHA"));
            clcCategoriaActividad.setAsignatura(rs.getString("ASIGNATURA"));
            Persona persona = new Persona();
            persona.setApellidoPaterno(rs.getString("APELLIDOPATERNO"));
            persona.setApellidoMaterno(rs.getString("APELLIDOMATERNO"));
            persona.setNombres(rs.getString("NOMBRES"));
            clcCategoriaActividad.setProfesor(persona);
            clcCategoriaActividad.setHorasReconocidas(rs.getInt("HORASRECONOCIDA"));
            clcCategoriaActividad.setTasaCredito(rs.getInt("TASACREDITO"));
            clcCategoriaActividad.setTasaHora(rs.getInt("TASAHORA"));
            clcCategoriaActividad.setTasaTotalCred(rs.getFloat("TASATOTALCRED"));

            return clcCategoriaActividad;
        }
    }

    /*
     * Util para Entidad CLCCategoriaIdioma
     */
    public static RowMapper getCLCCategoriaIdiomaMapper() {
        return new CLCCategoriaIdiomaRowMapperImpl();
    }

    private static final class CLCCategoriaIdiomaRowMapperImpl implements RowMapper {

        public CLCCategoriaIdiomaRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            CLCCategoriaIdioma clcCategoriaActividad = new CLCCategoriaIdioma();
            clcCategoriaActividad.setId(rs.getInt("ID"));
            clcCategoriaActividad.setFecha(rs.getDate("FECHA"));
            clcCategoriaActividad.setIdioma(rs.getString("IDIOMA"));
            clcCategoriaActividad.setNumero(rs.getString("NUMERO"));
            clcCategoriaActividad.setNivel(rs.getString("NIVEL"));
            clcCategoriaActividad.setCredReconocido(rs.getInt("CREDRECONOCIDO"));

            return clcCategoriaActividad;
        }
    }

    /*
     * Util para Entidad CLCCategPractPreProfesional
     */
    public static RowMapper getCLCCategPractPreProfesionalMapper() {
        return new CLCCategPractPreProfesionalRowMapperImpl();
    }

    private static final class CLCCategPractPreProfesionalRowMapperImpl implements RowMapper {

        public CLCCategPractPreProfesionalRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            CLCCategPractPreProfesional clcCategoriaActividad = new CLCCategPractPreProfesional();
            clcCategoriaActividad.setId(rs.getInt("ID"));
            clcCategoriaActividad.setFecha(rs.getDate("FECHA"));
            clcCategoriaActividad.setEmpresa(rs.getString("EMPRESA"));
            clcCategoriaActividad.setDesde(rs.getDate("PERIODODESDE"));
            clcCategoriaActividad.setHasta(rs.getDate("PERIODOHASTA"));
            clcCategoriaActividad.setHorasReconocidas(rs.getInt("HORASRECONOCIDA"));
            clcCategoriaActividad.setTasaCredito(rs.getInt("TASACREDITO"));
            clcCategoriaActividad.setTasaHora(rs.getInt("TASAHORA"));
            clcCategoriaActividad.setTasaTotalCred(rs.getFloat("TASATOTALCRED"));

            return clcCategoriaActividad;
        }
    }

    /*
     * Los datos de una fila la inserta en una HashMap
     */
    public static RowMapper getHashMapMapper() {
        return new HashMapRowMapperImpl();
    }

    private static final class HashMapRowMapperImpl implements RowMapper {

        public HashMapRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Map<String, Object> fila = new HashMap<String, Object>();
            ResultSetMetaData rsmd = rs.getMetaData();
            int numColumns = rsmd.getColumnCount();

            for (int i = 1; i < numColumns + 1; i++) {
                String columnName = rsmd.getColumnLabel(i).toLowerCase();
                fila.put(columnName, rs.getObject(columnName));
            }
            return fila;
        }
    }

    /*
     * Util para Entidad HorarioAsesoria
     */
    public static RowMapper getHorarioAsesoriaMapper() {
        return new HorarioAsesoriaRowMapperImpl();
    }

    private static final class HorarioAsesoriaRowMapperImpl implements RowMapper {

        public HorarioAsesoriaRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            HorarioAsesoria horarioAsesoria = new HorarioAsesoria();
            horarioAsesoria.setTitulo(rs.getString("TITULO"));
            horarioAsesoria.setDia(Dia.parse(rs.getInt("IDDIA")));
            horarioAsesoria.setBloqueHorario(new BloqueHorario(rs.getInt("IDBLOQUEHORARIO"),
                    rs.getString("DESCRIPCION"), rs.getInt("ORDEN")));
            horarioAsesoria.setOficina(rs.getString("OFICINA"));
            horarioAsesoria.setEdificio(rs.getString("EDIFICIO"));
            return horarioAsesoria;
        }
    }

    /*
     * Util para Entidad PensionesPagosCuotas
     */
    public static RowMapper getPensionesPagosCuotasMapper() {
        return new PensionesPagosCuotasRowMapperImpl();
    }

    private static final class PensionesPagosCuotasRowMapperImpl implements RowMapper {

        public PensionesPagosCuotasRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            PagoCuota pensionesPagosCuotas = new PagoCuota();
            pensionesPagosCuotas.setId(rs.getInt("ID"));
            pensionesPagosCuotas.setCuota(rs.getInt("CUOTA"));
            pensionesPagosCuotas.setFecha(rs.getDate("FECHA"));
            pensionesPagosCuotas.setImporte(rs.getFloat("IMPORTE"));
            pensionesPagosCuotas.setEstadoCuota(EstadoPagoCuota.parse(rs.getInt("ESTADO")));
            return pensionesPagosCuotas;
        }
    }
    
    /*
     * Util para Entidad FechaCuotaPagoEspecial
     */
    public static RowMapper getFechaCuotaPagoEspecialMapper() {
        return new FechaCuotaPagoEspecialRowMapperImpl();
    }

    private static final class FechaCuotaPagoEspecialRowMapperImpl implements RowMapper {

        public FechaCuotaPagoEspecialRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            FechaCuotaPagoEspecial fechaCuotaPagoEspecial = new FechaCuotaPagoEspecial();
            fechaCuotaPagoEspecial.setCuota(rs.getInt("IDCUOTA"));
            fechaCuotaPagoEspecial.setFecha(rs.getDate("FECHAPAGO"));
            fechaCuotaPagoEspecial.setPagoEspecial(new PagoEspecial(rs.getInt("IDPAGOSESPECIALES")));
            fechaCuotaPagoEspecial.setTipoPagoEspecial(TipoPagoEspecial.parse(rs.getInt("IDTIPOPAGOSESPECIALES")));
            return fechaCuotaPagoEspecial;
        }
    }

    /*
     * Util para Entidad Interaccion
     */
    public static RowMapper getInteraccionMapper() {
        return new InteraccionRowMapperImpl();
    }

    private static final class InteraccionRowMapperImpl implements RowMapper {

        public InteraccionRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Interaccion interaccion = new Interaccion();
            interaccion.setFecha(rs.getDate("FECHA"));
            interaccion.setDuracion(rs.getInt("DURACION"));
            interaccion.setTemaInteraccion(rs.getString("TEMA"));
            return interaccion;
        }
    }

    /*
     * Util para Entidad Date
     */
    public static RowMapper getDateMapper() {
        return new DateRowMapperImpl();
    }

    private static final class DateRowMapperImpl implements RowMapper {

        public DateRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getDate(1);
        }
    }

    /*
     * Util para Entidad Asistencia
     */
    public static RowMapper getAsistenciaMapper() {
        return new AsistenciaRowMapperImpl();
    }

    private static final class AsistenciaRowMapperImpl implements RowMapper {

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd 'de' MMMM 'de' yyyy", new Locale("ES"));

        public AsistenciaRowMapperImpl() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Asistencia asistencia = new Asistencia();
            asistencia.setFecha(dateFormat.format(rs.getDate("FECHA")));
            asistencia.setHora(rs.getString("HORA"));
            asistencia.setAsistio(rs.getBoolean("PRESENTE"));
            return asistencia;
        }
    }

    /*
     * Util para Entidad CentroAcademico
     */
    public static RowMapper getCentroAcademicoMapper() {
        return new CentroAcademicoRowMapperImpl();
    }

    private static final class CentroAcademicoRowMapperImpl implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

            CentroAcademico centroAcademico = new CentroAcademico();
            centroAcademico.setId(rs.getInt("IDCENTROACADEMICO"));
            centroAcademico.setSigla(rs.getString("SIGLACENTRO"));
            centroAcademico.setNombre(rs.getString("NOMBRE"));
            centroAcademico.setActivo(rs.getBoolean("ACTIVO"));
            return centroAcademico;
        }
    }

    /*
     * Util para Entidad TrabajoInvestigacion
     */
    public static RowMapper getTrabajoInvestigacionMapper() {
        return new TrabajoInvestigacionRowMapperImpl();
    }

    private static final class TrabajoInvestigacionRowMapperImpl implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

            TrabajoInvestigacion trabajoInvestigacion = new TrabajoInvestigacion();
            trabajoInvestigacion.setId(rs.getInt("IDTRABAJOINVESTIGACION"));
            trabajoInvestigacion.setTitulo(rs.getString("TITULO"));
            trabajoInvestigacion.setInvestigador(new Profesor(rs.getInt("IDPROFESOR")));
            trabajoInvestigacion.setDescripcion(rs.getString("DESCRIPCION"));
            trabajoInvestigacion.setFechaInicio(rs.getDate("FECHAINICIO"));
            trabajoInvestigacion.setFechaFin(rs.getDate("FECHAFIN"));
            trabajoInvestigacion.setResultado(rs.getString("RESULTADO"));
            trabajoInvestigacion.setAutores(rs.getString("AUTORES"));
            trabajoInvestigacion.setCampoInvestigacion(new LineaInvestigacion(rs.getInt("IDCAMPOINVESTIGACION"), rs.getString("NOMBRE")));

            return trabajoInvestigacion;
        }
    }

    /*
     * Util para Entidad InvestigacionGenerica
     */
    public static RowMapper getInvestigacionGenericaMapper() {
        return new InvestigacionGenericaRowMapperImpl();
    }

    private static final class InvestigacionGenericaRowMapperImpl implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

            InvestigacionGenerica investigacionGenerica = new InvestigacionGenerica();
            investigacionGenerica.setId(rs.getInt("IDINVESTIGACIONGENERICA"));
            investigacionGenerica.setTitulo(rs.getString("TITULO"));
            investigacionGenerica.setEstado(EstadoInvestigacion.parse(rs.getInt("ESTADO")));
            investigacionGenerica.setLineaInvestigacion(new LineaInvestigacion(rs.getInt("IDLINEAINVESTIGACION")));
            investigacionGenerica.setCampoInvestigacion(new LineaInvestigacion(rs.getInt("IDCAMPOINVESTIGACION")));
            investigacionGenerica.setEstudio(new Estudio(rs.getInt("IDESTUDIO")));
            investigacionGenerica.setCentroAcademico(new CentroAcademico(rs.getInt("IDCENTROACADEMICO"), rs.getString("CENTRO")));
            investigacionGenerica.setDepartamento(new Departamento(rs.getInt("IDDEPAACADEMICO"), rs.getString("DEPARTAMENTO")));
            investigacionGenerica.setAreaInvestigacion(new AreaInvestigacion(rs.getInt("IDAREAINVESTIGACION"), rs.getString("AREA")));
            investigacionGenerica.setAutor(rs.getString("AUTOR"));
            investigacionGenerica.setDepositoLegal(rs.getString("NUMDEPOSITOLEGAL"));
            investigacionGenerica.setResumen(rs.getString("RESUMEN"));
            investigacionGenerica.setEnglishResumen(rs.getString("ABSTRACT"));
            investigacionGenerica.setPalabraClave(rs.getString("PALABRACLAVE"));
            investigacionGenerica.setRestriccionPublicacion(RestriccionPublicacion.parse(rs.getInt("RESTRICCIONPUBLICACION")));
            investigacionGenerica.setVencEmbargoComerc(rs.getDate("EMBARGOCOMERCIALVENC"));
            investigacionGenerica.setEmbargoPersonal(rs.getBoolean("EMBARGOPERSONAL"));
            investigacionGenerica.setTitularDerechos(rs.getString("TITULARDERECHOS"));
            investigacionGenerica.setDerechos(rs.getString("DERECHOS"));
            investigacionGenerica.setUrlLicenciaDerecho(rs.getString("URLLICENCIADERECHO"));
            investigacionGenerica.setTituloAlt(rs.getString("TITULOALT"));

            return investigacionGenerica;
        }
    }

    /*
     * Util para Entidad Articulo
     */
    public static RowMapper getArticuloMapper() {
        return new ArticuloRowMapperImpl();
    }

    private static final class ArticuloRowMapperImpl implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

            Articulo articulo = new Articulo();
            articulo.setId(rs.getInt("IDARTICULO"));
            articulo.setTipoArticulo(TipoArticulo.parse(rs.getInt("IDTIPOARTICULO")));
            articulo.setColaboradores(rs.getString("COLABORADORES"));
            articulo.setMedioPublicacion(rs.getString("MEDIOPUBLICACION"));
            articulo.setNombreMedio(rs.getString("NOMBREMEDIO"));
            articulo.setIndexada(rs.getString("INDEXADA"));
            articulo.setPublicadoActa(rs.getBoolean("PUBLICADOACTA"));
            articulo.setIsbn(rs.getString("ISBN"));
            articulo.setActa(rs.getString("ACTA"));
            articulo.setIssn(rs.getString("ISSN"));
            articulo.setEdicion(rs.getString("EDICION"));
            articulo.setTomoVolumen(rs.getString("TOMOVOLUMEN"));
            articulo.setNumeroFasciculo(rs.getString("NUMEROFASCICULO"));
            articulo.setInicio(rs.getString("PAGINAINICIO"));
            articulo.setFin(rs.getString("PAGINAFIN"));
            articulo.setDoi(rs.getString("DOI"));
            articulo.setFecha(rs.getString("FECHAPUBLICACION"));
            articulo.setCiudadPublica(rs.getString("CIUDADPUBLICACION"));
            articulo.setPaisPublica(rs.getString("PAISPUBLICACION"));
            articulo.setOtraVersion(rs.getString("OTRAVERSIONPUBLICADA"));
            articulo.setInvestigacionGenerica(new InvestigacionGenerica(rs.getInt("IDINVESTIGACIONGENERICA")));
            articulo.setProceso(rs.getString("PROCESO"));

            return articulo;
        }
    }

    /*
     * Util para Entidad Evento
     */
    public static RowMapper getEventoMapper() {
        return new EventoRowMapperImpl();
    }

    private static final class EventoRowMapperImpl implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

            Evento evento = new Evento();
            evento.setId(rs.getInt("IDEVENTO"));
            evento.setTituloPonencia(rs.getString("TITULOPONENCIA"));
            evento.setNumEvento(rs.getString("NUMEVENTO"));
            evento.setNombre(rs.getString("NOMBRE"));
            evento.setOrganizadoPor(rs.getString("ORGANIZADOPOR"));
            evento.setAmbito(rs.getString("AMBITO"));
            evento.setDescripcion(rs.getString("DESCRIPCION"));
            evento.setFecha(rs.getString("FECHA"));
            evento.setCiudad(rs.getString("CIUDAD"));
            evento.setPais(rs.getString("PAIS"));

            return evento;
        }
    }

    /*
     * Util para Entidad AreaConocimiento
     */
    public static RowMapper getAreaConocimientoMapper() {
        return new AreaConocimientoMapper();
    }

    private static final class AreaConocimientoMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            AreaConocimiento areaConocimiento = new AreaConocimiento();

            areaConocimiento.setId(rs.getInt("IDAREACONOCIMIENTO"));
            areaConocimiento.setNombre(rs.getString("NOMBRE"));

            return areaConocimiento;
        }
    }

    /*
     * Util para Entidad Asignatura
     */
    public static RowMapper getAsignaturaMapper() {
        return new AsignaturaRowMapperImpl();
    }

    private static final class AsignaturaRowMapperImpl implements RowMapper {

        public AsignaturaRowMapperImpl() {
        }

        public Asignatura mapRow(ResultSet rs, int rowNum) throws SQLException {
            Asignatura asignatura = new Asignatura();
            asignatura.setId(rs.getInt("IDASIGNATURA"));
            asignatura.setNombre(rs.getString("NOMBRE"));
            asignatura.setSigla(rs.getString("CODIGO"));
            asignatura.setCreditos(rs.getInt("CREDITOS"));

            return asignatura;
        }
    }

    /*
     * Util para Entidad PlanEstudioAsignatura
     */
    public static RowMapper getPlanEstudioAsignaturaMapper() {
        return new PlanEstudioAsignaturaMapper();
    }

    private static final class PlanEstudioAsignaturaMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            PlanEstudioAsignatura planEstudioAsignatura = new PlanEstudioAsignatura();

            planEstudioAsignatura.setId(rs.getInt("IDPLANESTUDIOS"));
            planEstudioAsignatura.setAsignatura(new Asignatura(rs.getInt("IDASIGNATURA")));
            planEstudioAsignatura.setSeccionAcademicaId(rs.getInt("IDSECCIONACADEMICA"));
            planEstudioAsignatura.setSeccionAcademica(rs.getString("NOMBRE"));
            planEstudioAsignatura.setTipoAsignatura(TipoAsignatura.parse(rs.getInt("IDTIPOASIGNATURA")));
            planEstudioAsignatura.setNumPeriodo(rs.getInt("NUMPERIODO"));

            return planEstudioAsignatura;
        }
    }
    
    public static RowMapper getPlanEstudioAsignaturaCustomMapper(){
        return new PlanEstudioAsignaturaCustomMapper();
    }
    
    private static final class PlanEstudioAsignaturaCustomMapper implements RowMapper<PlanEstudioAsignatura>{

        public PlanEstudioAsignatura mapRow(ResultSet rs, int rowNum) throws SQLException {
            PlanEstudioAsignatura planEstudioAsignatura = new PlanEstudioAsignatura();
            planEstudioAsignatura.setId(rs.getInt("IDPLANESTUDIO"));
            
            Asignatura asignatura = new Asignatura(rs.getInt("IDASIGNATURA"));
            asignatura.setSigla(rs.getString("CODIGO"));
            asignatura.setNombre(rs.getString("NOMBRE"));
            asignatura.setDescripcion(rs.getString("DESCRIPCION"));
            asignatura.setCreditos(rs.getInt("CREDITOS"));
            
            planEstudioAsignatura.setAsignatura(asignatura);
            
            planEstudioAsignatura.setSeccionAcademicaId(rs.getInt("IDSECCIONACADEMICA"));
            planEstudioAsignatura.setSeccionAcademica(rs.getString("SECCIONACADEMICA"));
            planEstudioAsignatura.setTipoAsignatura(TipoAsignatura.parse(rs.getInt("IDTIPOASIGNATURA")));
            planEstudioAsignatura.setNumPeriodo(rs.getInt("NUMPERIODO"));
            
            return planEstudioAsignatura;
        }
    }
    
    public static RowMapper getPlanEstudioAsignaturaCustomEsp(){
        return new PlanEstudioAsignaturaCustomEsp();
    }
    
    private static final class PlanEstudioAsignaturaCustomEsp implements RowMapper<PlanEstudioAsignatura>{

        public PlanEstudioAsignatura mapRow(ResultSet rs, int rowNum) throws SQLException {
            PlanEstudioAsignatura planEstudioAsignatura = new PlanEstudioAsignatura();
            planEstudioAsignatura.setId(rs.getInt("IDPLANESTUDIOS"));
            
            Asignatura asignatura = new Asignatura(rs.getInt("IDASIGNATURA"));
            asignatura.setSigla(rs.getString("CODIGO"));
            asignatura.setNombre(rs.getString("NOMBRE"));
            asignatura.setDescripcion(rs.getString("DESCRIPCION"));
            asignatura.setCreditos(rs.getInt("CREDITOS"));
            asignatura.setDesdeEspecialidad( rs.getInt("IDESPECIALIDADASIGNATURA") > 0 );
            
            planEstudioAsignatura.setAsignatura(asignatura);
            
            planEstudioAsignatura.setSeccionAcademicaId(rs.getInt("IDSECCIONACADEMICA"));
            planEstudioAsignatura.setSeccionAcademica(rs.getString("SECCIONACADEMICA"));
            planEstudioAsignatura.setTipoAsignatura(TipoAsignatura.parse(rs.getInt("IDTIPOASIGNATURA")));
            planEstudioAsignatura.setNumPeriodo(rs.getInt("NUMPERIODO"));
            
            return planEstudioAsignatura;
        }
        
    }

    /*
     * Util para Entidad Tesis
     */
    public static RowMapper getTesisMapper() {
        return new TesisMapper();
    }

    private static final class TesisMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            Tesis tesis = new Tesis();

            tesis.setId(rs.getInt("IDTESIS"));
            tesis.setTipoTesis(TipoTesis.parse(rs.getInt("TIPO")));
            tesis.setAsesor(rs.getString("ASESOR"));
            tesis.setGradoObtenido(rs.getString("GRADOOBTENIDO"));
            tesis.setUniversidad(rs.getString("UNIVERSIDAD"));
            tesis.setFechaInicio(rs.getString("FECHAINICIO"));
            tesis.setFechaFin(rs.getString("FECHAFIN"));
            tesis.setFechaSustentacion(rs.getString("FECHASUSTENTACION"));
            tesis.setIsbn(rs.getString("ISBN"));
            tesis.setPaginas(rs.getInt("PAGINAS"));
            tesis.setInvestigacionGenerica(new InvestigacionGenerica(rs.getInt("IDINVESTIGACIONGENERICA")));

            return tesis;
        }
    }

    /*
     * Util para Entidad Libro
     */
    public static RowMapper getLibroMapper() {
        return new LibroMapper();
    }

    private static final class LibroMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            Libro libro = new Libro();

            libro.setId(rs.getInt("IDLIBRO"));
            libro.setColectivo(rs.getBoolean("LIBROCOLECTIVO"));
            libro.setInvestigacionGenerica(new InvestigacionGenerica(rs.getInt("IDINVESTIGACIONGENERICA")));
            libro.setCoordinador(rs.getString("COORDINADOR"));
            libro.setColaboradores(rs.getString("COLABORADORES"));
            libro.setSerieColeccion(rs.getString("SERIECOLECCION"));
            libro.setEditorial(rs.getString("EDITORIAL"));
            libro.setIsbn(rs.getString("ISBN"));
            libro.setEdicion(rs.getString("EDICION"));
            libro.setPaginas(rs.getInt("PAGINAS"));
            libro.setFechaPublica(rs.getString("FECHAPUBLICACION"));
            libro.setCiudad(rs.getString("CIUDAD"));
            libro.setPais(rs.getString("PAIS"));
            libro.setOtrasVersiones(rs.getString("OTRASVERSIONES"));

            return libro;
        }
    }

    /*
     * Util para Entidad Capitulo
     */
    public static RowMapper getCapituloMapper() {
        return new CapituloMapper();
    }

    private static final class CapituloMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            Capitulo capitulo = new Capitulo();

            capitulo.setNumero(rs.getString("NUMERO"));
            capitulo.setNombre(rs.getString("NOMBRE"));
            capitulo.setPaginaInicio(rs.getString("PAGINAINICIO"));
            capitulo.setPaginaFin(rs.getString("PAGINAFIN"));

            return capitulo;
        }
    }

    /*
     * Util para Entidad PEAEAsignaturaEstado
     */
    public static RowMapper getPEAEAsignaturaEstadoMapper() {
        return new PEAEAsignaturaEstadoRowMapperImpl();
    }

    private static final class PEAEAsignaturaEstadoRowMapperImpl implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

            PEAEAsignaturaEstado peaeae = new PEAEAsignaturaEstado();
            peaeae.setSigla(rs.getString("CODIGO"));
            peaeae.setNombre(rs.getString("NOMBRE"));
            peaeae.setCreditos(rs.getInt("CREDITOS"));
            peaeae.setRegla(rs.getString("REGLA"));
            peaeae.setObservacion(rs.getString("OBSERVACION"));
            peaeae.setEstado(rs.getString("ESTADO"));
            peaeae.setAprobada(rs.getBoolean("APROBADA"));
            peaeae.setDesaprobada(rs.getBoolean("DESAPROBADA"));
            peaeae.setConvalidada(rs.getBoolean("CONVALIDADA"));
            peaeae.setEquivalente(rs.getBoolean("EQUIVALENTE"));
            peaeae.setConvalidadaexterna(rs.getBoolean("CONVALIDADAEXTERNA"));
            peaeae.setExonerada(rs.getBoolean("EXONERADA"));
            peaeae.setNota(rs.getString("NOTA"));
            return peaeae;
        }
    }
    /*
     * Util para Entidad Alumnocandidato
     */
    public static RowMapper getAlumnocandidatoMapper() {
        return new AlumnocandidatoRowMapperImpl();
    }

    private static final class AlumnocandidatoRowMapperImpl implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

            Alumnocandidato peaeae = new Alumnocandidato();
            peaeae.setIdalumnocandidato(rs.getInt("IDALUMNOCANDIDATO"));
           // Alumno alumno=AlumnoEstudioService.getById(rs.getInt("IDALUMNO"));
            peaeae.setIdalumno(rs.getInt("IDALUMNO"));
            peaeae.setIdedicionetudio(rs.getInt("IDEDICIONESTUDIO"));
            peaeae.setIdperiodoacademico(rs.getInt("IDPERIODOACADEMICO"));
            peaeae.setIdcampus(rs.getInt("IDCAMPUS"));
            peaeae.setNivel(rs.getInt("NIVEL"));
            return peaeae;
        }
    }
    /*
     * Util para Entidad DocumentosInvestigacion
     */
    public static RowMapper getDocumentosInvestigacionMapper() {
        return new DocumentosInvestigacionMapper();
    }

    private static final class DocumentosInvestigacionMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            DocumentosInvestigacion documentosInvestigacion = new DocumentosInvestigacion();

            documentosInvestigacion.setNombre(rs.getString("NOMBRE"));
            documentosInvestigacion.setPeso(rs.getString("IDIOMA"));
            documentosInvestigacion.setFormato(rs.getString("FORMATO"));
            documentosInvestigacion.setPrograma(rs.getString("PROGRAMA"));
            documentosInvestigacion.setComentario(rs.getString("COMENTARIO"));

            return documentosInvestigacion;
        }
    }

    /*
     * Util para Entidad ArchivoInvestigacion
     */
    public static RowMapper getArchivoInvestigacionMapper() {
        return new ArchivoInvestigacionMapper();
    }

    private static final class ArchivoInvestigacionMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            ArchivoInvestigacion archivoInvestigacion = new ArchivoInvestigacion();

            archivoInvestigacion.setNombre(rs.getString("NOMBRE"));
            archivoInvestigacion.setDescripcion(rs.getString("DESCRIPCION"));
            archivoInvestigacion.setFecha(rs.getDate("FECHASUBIDA"));

            return archivoInvestigacion;
        }
    }
    /*
     * Util para Entidad EstudioCV
     */

    public static RowMapper getEstudioCVMapper() {
        return new EstudioCVMapper();
    }

    private static final class EstudioCVMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            EstudioCV estudioCV = new EstudioCV();

            estudioCV.setAnio(rs.getString("AÑO"));
            estudioCV.setGradoTitulo(rs.getString("GRADOTITULO"));
            estudioCV.setPais(rs.getString("PAIS"));
            estudioCV.setUniversidad(rs.getString("UNIVERSIDAD"));
            estudioCV.setTituloTesis(rs.getString("TITULOTESIS"));
            estudioCV.setTipoGrado(TipoGrado.parse(rs.getInt("TIPO")));


            return estudioCV;
        }
    }

    /*
     * Util para Entidad CapacitacionCV
     */
    public static RowMapper getCapacitacionCVMapper() {
        return new CapacitacionCVMapper();
    }

    private static final class CapacitacionCVMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            CapacitacionCV capacitacionCV = new CapacitacionCV();

            capacitacionCV.setAnio(rs.getString("AÑO"));
            capacitacionCV.setEvento(rs.getString("EVENTO"));
            capacitacionCV.setHoras(rs.getString("HORAS"));
            capacitacionCV.setInstitucion(rs.getString("INSTITUCION"));
            capacitacionCV.setLugar(rs.getString("LUGAR"));          

            return capacitacionCV;
        }
    }
    
    /*
     * Util para Entidad LineaInvestigacion
     */
    public static RowMapper getLineaInvestigacionMapper() {
        return new LineaInvestigacionMapper();
    }

    private static final class LineaInvestigacionMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            LineaInvestigacion lineaInvestigacion = new LineaInvestigacion();

            lineaInvestigacion.setNombre(rs.getString("NOMBRE"));
            lineaInvestigacion.setDescripcion(rs.getString("DESCRIPCION"));         

            return lineaInvestigacion;
        }
    }
    
    /*
     * Util para Entidad EstanciaInvestigacionCV
     */
    public static RowMapper getEstanciaInvestigacionCVMapper() {
        return new EstanciaInvestigacionCVMapper();
    }

    private static final class EstanciaInvestigacionCVMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            EstanciaInvestigacionCV estanciaInvestigacionCV = new EstanciaInvestigacionCV();

            estanciaInvestigacionCV.setDescripcion(rs.getString("DESCRIPCION"));  
            estanciaInvestigacionCV.setCentroVisitado(rs.getString("CENTROVISITADO"));       
            estanciaInvestigacionCV.setDesde(rs.getString("DESDE"));  
            estanciaInvestigacionCV.setHasta(rs.getString("HASTA"));  
            estanciaInvestigacionCV.setCiudad(rs.getString("CIUDAD"));  
            estanciaInvestigacionCV.setPais(rs.getString("PAIS"));  
            estanciaInvestigacionCV.setTema(rs.getString("TEMA"));  

            return estanciaInvestigacionCV;
        }
    }
    
    /*
     * Util para Entidad ProyectoCV
     */
    public static RowMapper getProyectoCVMapper() {
        return new ProyectoCVMapper();
    }

    private static final class ProyectoCVMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            ProyectoCV proyectoCV = new ProyectoCV();

            proyectoCV.setDescripcion(rs.getString("DESCRIPCION"));  
            proyectoCV.setNombre(rs.getString("NOMBRE"));       
            proyectoCV.setDesde(rs.getString("DESDE"));  
            proyectoCV.setHasta(rs.getString("HASTA"));  
            proyectoCV.setCiudad(rs.getString("CIUDAD"));  
            proyectoCV.setPais(rs.getString("PAIS"));  
            proyectoCV.setArea(rs.getString("AREA"));  
            proyectoCV.setFinanciamiento(rs.getString("FINANCIAMIENTO"));  
            proyectoCV.setParticipacion(rs.getString("PARTICIPACION"));   
            proyectoCV.setHastaLaFecha(rs.getBoolean("HASTALAFECHA"));  

            return proyectoCV;
        }
    }
    
    /*
     * Util para Entidad ConsultoriaCv
     */
    public static RowMapper getConsultoriaCvMapper() {
        return new ConsultoriaCvMapper();
    }

    private static final class ConsultoriaCvMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            ConsultoriaCv consultoriaCv = new ConsultoriaCv();

            consultoriaCv.setDescripcion(rs.getString("DESCRIPCION"));  
            consultoriaCv.setConsultoriaEn(rs.getString("CONSULTORIAEN"));       
            consultoriaCv.setDesde(rs.getString("DESDE"));  
            consultoriaCv.setHasta(rs.getString("HASTA"));  
            consultoriaCv.setCiudad(rs.getString("CIUDAD"));  
            consultoriaCv.setPais(rs.getString("PAIS"));  
            consultoriaCv.setCliente(rs.getString("CLIENTE"));  
            consultoriaCv.setParticipante(rs.getString("PARTICIPANTE")); 
            
            return consultoriaCv;
        }
    }
    
    /*
     * Util para Entidad ReunionEventoCV
     */
    public static RowMapper getReunionEventoCVMapper() {
        return new ReunionEventoCVMapper();
    }

    private static final class ReunionEventoCVMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            ReunionEventoCV reunionEventoCV = new ReunionEventoCV();

            reunionEventoCV.setDescripcion(rs.getString("DESCRIPCION"));
            reunionEventoCV.setCiudad(rs.getString("CIUDAD"));  
            reunionEventoCV.setPais(rs.getString("PAIS")); 
            reunionEventoCV.setAmbito(rs.getString("AMBITO"));
            reunionEventoCV.setAnio(rs.getInt("ANIO"));
            reunionEventoCV.setNombre(rs.getString("NOMBRE"));
            reunionEventoCV.setOrganizadoPor(rs.getString("ORGANIZADOPOR"));
            
            return reunionEventoCV;
        }
    }
    
    /*
     * Util para Entidad DocumentoInfoCateg
     */
    public static RowMapper getDocumentoInfoCategMapper() {
        return new DocumentoInfoCategMapper();
    }

    private static final class DocumentoInfoCategMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            DocumentoInfoCateg documentoInfoCateg = new DocumentoInfoCateg();

            documentoInfoCateg.setId(rs.getInt("IDCATEGORIADOCUMENTOS"));
            documentoInfoCateg.setNombre(rs.getString("NOMBRE"));     

            return documentoInfoCateg;
        }
    }
    
    /*
     * Util para Entidad DocumentoInfo
     */
    public static RowMapper getDocumentoInfoMapper() {
        return new DocumentoInfoMapper();
    }

    private static final class DocumentoInfoMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            DocumentoInfo documentoInfo = new DocumentoInfo();

            documentoInfo.setNombre(rs.getString("NOMBRE"));     
            documentoInfo.setDescripcion(rs.getString("DESCRIPCION")); 
            documentoInfo.setUrl(rs.getString("URLDOCUMENTO")); 

            return documentoInfo;
        }
    }
    
    /*
     * Util para Entidad ArticuloCV
     */
    public static RowMapper getArticuloCVMapper() {
        return new ArticuloCVMapper();
    }

    private static final class ArticuloCVMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            ArticuloCV articuloCV = new ArticuloCV();

            articuloCV.setAnio(rs.getInt("ANIO"));     
            articuloCV.setMedioPublicacion(rs.getString("MEDIOPUBLICACION")); 
            articuloCV.setTitulo(rs.getString("TITULO")); 
            articuloCV.setPagFin(rs.getString("PAGINAINICIO")); 
            articuloCV.setPagInicio(rs.getString("PAGINAFIN")); 

            return articuloCV;
        }
    }
    
    /*
     * Util para Entidad DocenciaCV
     */
    public static RowMapper getDocenciaCVMapper() {
        return new DocenciaCVMapper();
    }

    private static final class DocenciaCVMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            DocenciaCV docenciaCV = new DocenciaCV();
   
            docenciaCV.setId(rs.getInt("IDDOCENCIACV")); 
            docenciaCV.setCentroEstudio(rs.getString("CENTROESTUDIOS"));     
            docenciaCV.setAnioInicio(rs.getInt("AÑOI")); 
            docenciaCV.setAnioFin(rs.getInt("AÑOF")); 
            docenciaCV.setHastaLaFecha(rs.getBoolean("HASTALAFECHA")); 

            return docenciaCV;
        }
    }
    
    /*
     * Util para Entidad CursoDictadoCV
     */
    public static RowMapper getCursoDictadoCVMapper() {
        return new CursoDictadoCVMapper();
    }

    private static final class CursoDictadoCVMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            CursoDictadoCV cursoDictadoCV = new CursoDictadoCV();
   
            cursoDictadoCV.setAsignatura(rs.getString("ASIGNATURA"));     
            cursoDictadoCV.setCategoria(rs.getString("CATEGORIA")); 
            cursoDictadoCV.setEstudio(rs.getString("ESTUDIO")); 
            cursoDictadoCV.setPeriodo(rs.getString("PERIODO")); 

            return cursoDictadoCV;
        }
    }
    
    /*
     * Util para Entidad OtroTrabajoCV
     */
    public static RowMapper getOtroTrabajoCVMapper() {
        return new OtroTrabajoCVMapper();
    }

    private static final class OtroTrabajoCVMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            OtroTrabajoCV otroTrabajoCV = new OtroTrabajoCV();
   
            otroTrabajoCV.setNombre(rs.getString("NOMBRE"));     
            otroTrabajoCV.setEmpresa(rs.getString("EMPRESA"));   
            otroTrabajoCV.setHastaLaFecha(rs.getBoolean("HASTALAFECHA"));   
            otroTrabajoCV.setDiaInicio(rs.getInt("DIAI"));   
            otroTrabajoCV.setDiaFin(rs.getInt("DIAF"));   
            otroTrabajoCV.setMesInicio(rs.getInt("MESI"));   
            otroTrabajoCV.setMesFin(rs.getInt("MESF")); 
            otroTrabajoCV.setAnioInicio(rs.getInt("AÑOI")); 
            otroTrabajoCV.setAnioFin(rs.getInt("AÑOF")); 

            return otroTrabajoCV;
        }
    }
    
    /*
     * Util para Entidad CargoDirectivoCV
     */
    public static RowMapper getCargoDirectivoCVMapper() {
        return new CargoDirectivoCVMapper();
    }

    private static final class CargoDirectivoCVMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            CargoDirectivoCV cargoDirectivoCV = new CargoDirectivoCV();
   
            cargoDirectivoCV.setCargo(rs.getString("CARGO"));     
            cargoDirectivoCV.setInstitucion(rs.getString("INSTITUCION"));   
            cargoDirectivoCV.setHastaLaFecha(rs.getBoolean("HASTALAFECHA"));   
            cargoDirectivoCV.setDiaInicio(rs.getInt("DIAI"));   
            cargoDirectivoCV.setDiaFin(rs.getInt("DIAF"));   
            cargoDirectivoCV.setMesInicio(rs.getInt("MESI"));   
            cargoDirectivoCV.setMesFin(rs.getInt("MESF")); 
            cargoDirectivoCV.setAnioInicio(rs.getInt("AÑOI")); 
            cargoDirectivoCV.setAnioFin(rs.getInt("AÑOF")); 

            return cargoDirectivoCV;
        }
    }
    
    /*
     * Util para Entidad EmpresaCV
     */
    public static RowMapper getEmpresaCVMapper() {
        return new EmpresaCVMapper();
    }

    private static final class EmpresaCVMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            EmpresaCV empresaCV = new EmpresaCV();
   
            empresaCV.setCargo(rs.getString("CARGO"));     
            empresaCV.setCentroTrabajo(rs.getString("CENTRODETRABAJO"));   
            empresaCV.setHastaLaFecha(rs.getBoolean("HASTALAFECHA"));   
            empresaCV.setDiaInicio(rs.getInt("DIAI"));   
            empresaCV.setDiaFin(rs.getInt("DIAF"));   
            empresaCV.setMesInicio(rs.getInt("MESI"));   
            empresaCV.setMesFin(rs.getInt("MESF")); 
            empresaCV.setAnioInicio(rs.getInt("AÑOI")); 
            empresaCV.setAnioFin(rs.getInt("AÑOF")); 

            return empresaCV;
        }
    }
    
    /*
     * Util para Entidad RedAcademicaCV
     */
    public static RowMapper getRedAcademicaCVMapper() {
        return new RedAcademicaCVMapper();
    }

    private static final class RedAcademicaCVMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            RedAcademicaCV redAcademicaCV = new RedAcademicaCV();
   
            redAcademicaCV.setNombre(rs.getString("NOMBRE"));     
            redAcademicaCV.setUrl(rs.getString("URL"));   
            redAcademicaCV.setEspecializacion(rs.getString("ESPECIALIZACION"));

            return redAcademicaCV;
        }
    }
    
    /*
     * Util para Entidad AsociacionProfesionalCV
     */
    public static RowMapper getAsociacionProfesionalCVMapper() {
        return new AsociacionProfesionalCVMapper();
    }

    private static final class AsociacionProfesionalCVMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            AsociacionProfesionalCV asociacionProfesionalCV = new AsociacionProfesionalCV();
   
            asociacionProfesionalCV.setNombre(rs.getString("NOMBRE"));     
            asociacionProfesionalCV.setPuesto(rs.getString("PUESTO"));   
            asociacionProfesionalCV.setHastaLaFecha(rs.getBoolean("HASTALAFECHA"));   
            asociacionProfesionalCV.setDiaInicio(rs.getInt("DIAI"));   
            asociacionProfesionalCV.setDiaFin(rs.getInt("DIAF"));   
            asociacionProfesionalCV.setMesInicio(rs.getInt("MESI"));   
            asociacionProfesionalCV.setMesFin(rs.getInt("MESF")); 
            asociacionProfesionalCV.setAnioInicio(rs.getInt("AÑOI")); 
            asociacionProfesionalCV.setAnioFin(rs.getInt("AÑOF")); 

            return asociacionProfesionalCV;
        }
    }
    
    /*
     * Util para Entidad IdiomasCV
     */
    public static RowMapper getIdiomasCVMapper() {
        return new IdiomasCVMapper();
    }

    private static final class IdiomasCVMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            IdiomasCV idiomasCV = new IdiomasCV();
   
            idiomasCV.setNombre(rs.getString("NOMBRE"));     
            idiomasCV.setLugar(rs.getString("LUGAR"));    
            idiomasCV.setAnioInicio(rs.getInt("AÑOI"));   
            idiomasCV.setNivelC(rs.getString("NIVELC"));   
            idiomasCV.setNivelL(rs.getString("NIVELL"));   
            idiomasCV.setNivelE(rs.getString("NIVELE"));   

            return idiomasCV;
        }
    }
    
    /*
     * Util para Entidad PatenteCV
     */
    public static RowMapper getPatenteCVMapper() {
        return new PatenteCVMapper();
    }

    private static final class PatenteCVMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            PatenteCV patenteCV = new PatenteCV();
   
            patenteCV.setAnio(rs.getString("AÑO"));     
            patenteCV.setTema(rs.getString("TEMA"));   

            return patenteCV;
        }
    }
    
    /*
     * Util para Entidad PremioCV
     */
    public static RowMapper getPremioCVMapper() {
        return new PremioCVMapper();
    }

    private static final class PremioCVMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            PremioCV premioCV = new PremioCV();
   
            premioCV.setAnio(rs.getString("AÑO"));     
            premioCV.setEntidad(rs.getString("ENTIDAD"));   
            premioCV.setPremio(rs.getString("PREMIO"));   

            return premioCV;
        }
    }
    
    /*
     * Util para Entidad MeritoCV
     */
    public static RowMapper getMeritoCVMapper() {
        return new MeritoCVMapper();
    }

    private static final class MeritoCVMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            MeritoCV meritoCV = new MeritoCV();
   
            meritoCV.setAnio(rs.getString("AÑO"));     
            meritoCV.setMerito(rs.getString("MERITO"));  

            return meritoCV;
        }
    }
    
    /*
     * Util para Entidad Consulta
     */
    public static RowMapper getConsultaMapper() {
        return new ConsultaMapper();
    }

    private static final class ConsultaMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            Consulta consulta = new Consulta();
   
            consulta.setId(rs.getInt("IDCONSULTASUGERENCIA"));
            consulta.setFecha(rs.getDate("FECHA"));     
            consulta.setConsulta(rs.getString("TEXTOCONSULTASUGERENCIA"));
            consulta.setEstadoConsulta(EstadoConsulta.parse(rs.getInt("IDESTADOCONSULTAS"))); 
            consulta.setFechaRespuesta(rs.getDate("FECHARESPUESTA")); 
            consulta.setRespuesta(rs.getString("TEXTORESPUESTA"));   
            consulta.setPositivo(rs.getBoolean("POSITIVO"));             
            consulta.setCategoria(new CategoriaConsulta (rs.getInt("IDCATEGORIACONSULTAS"),rs.getString("CATEGORIA")));

            return consulta;
        }
    }
    
    /*
     * Util para Entidad CategoriaConsulta
     */
    public static RowMapper getCategoriaConsultaMapper() {
        return new CategoriaConsultaMapper();
    }

    private static final class CategoriaConsultaMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            CategoriaConsulta CategoriaConsulta = new CategoriaConsulta();
   
            CategoriaConsulta.setId(rs.getInt("IDCATEGORIACONSULTAS"));
            CategoriaConsulta.setNombre(rs.getString("NOMBRE"));   

            return CategoriaConsulta;
        }
    }
    
    /*
     * Util para Entidad TipoPagoEspecial
     */
    public static RowMapper getTipoPagoEspecialMapper() {
        return new TipoPagoEspecialMapper();
    }

    private static final class TipoPagoEspecialMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
   
            return TipoPagoEspecial.parse(rs.getInt("IDTIPOPAGOSESPECIALES"));
        }
    }
    
    /*
     * Util para Entidad PensionAlumno
     */
    public static RowMapper getPensionAlumnoMapper() {
        return new PensionAlumnoMapper();
    }

    private static final class PensionAlumnoMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            PensionAlumno pensionAlumno = new PensionAlumno();
   
            pensionAlumno.setCalu(rs.getInt("CALU"));
            pensionAlumno.setEspecial(rs.getBoolean("LALUESP"));
            pensionAlumno.setPagoQuincena(rs.getBoolean("LALUPQU"));

            return pensionAlumno;
        }
    }
    
    /*
     * Util para Entidad AsesorSugerido
     */
    public static RowMapper getAsesorSugeridoMapper() {
        return new AsesorSugeridoMapper();
    }

    private static final class AsesorSugeridoMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            AsesorSugerido asesorSugerido = new AsesorSugerido();
   
            asesorSugerido.setId(rs.getInt("IDALUMNOASESORSUGERIDO"));
            asesorSugerido.setAsesor_sugerido_1(new Persona (rs.getInt("IDPERSONAASESORSUGERIDO1")));
            asesorSugerido.setAsesor_sugerido_2(new Persona (rs.getInt("IDPERSONAASESORSUGERIDO2")));

            return asesorSugerido;
        }
    }
    
    /*
     * Util ara Entidad Especialidad
     */
    public static RowMapper getEspecialidadMapper(){
        return new EspecialidadMapper();
    }
    
    private static final class EspecialidadMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Especialidad especialidad = new Especialidad(rs.getInt("IDESPECIALIDAD"));
            especialidad.setNombre(rs.getString("NOMBRE"));
            especialidad.setDescripcion(rs.getString("DESCRIPCION"));
            especialidad.setPlanEstudio(new PlanEstudio(rs.getInt("IDPLANESTUDIO")));
            especialidad.setActivo(rs.getBoolean("ACTIVO"));
            especialidad.setFechaRegistro(rs.getDate("FECHAREGISTRO"));
            especialidad.setPersonaRegistro(new Persona(rs.getInt("PERSONAREGISTRO")));                        
            
            return especialidad;
        }
        
    }
    
    /*
     * Util ara Entidad Especialidad
     */
    public static RowMapper getAsignaturaEncuestaMapper(){
        return new AsignaturaEncuestaMapper();
    }
    
    private static final class AsignaturaEncuestaMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            AsignaturaEncuesta asignatura = new AsignaturaEncuesta();
            asignatura.setId(rs.getInt("ID"));
            asignatura.setNombreasignatura(rs.getString("NOMBRE_ASIGNATURA"));
            asignatura.setIdasignatura(rs.getInt("IDASIGNATURA"));
            asignatura.setIdestudio(rs.getInt("IDESTUDIO"));
            asignatura.setActivo(rs.getBoolean("ACTIVO"));
            asignatura.setSigla(rs.getString("SIGLA"));
            return asignatura;
        }
        
    }
    
    /*
     * Util para Entidad Ambiente
     */
    
    public static RowMapper getAmbienteMapper(){
        return new AmbienteMapper();
    }
    
    private static final class AmbienteMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Ambiente ambiente = new Ambiente();
            ambiente.setAforo(rs.getInt("AFORO"));
            ambiente.setAreaM2(new BigDecimal(rs.getString("AREAM2")));
            ambiente.setCapacidadReal(rs.getInt("CAPACIDADREAL"));
            ambiente.setDescripcion(rs.getString("DESCRIPCION"));
            ambiente.setIdAmbiente(rs.getInt("IDAMBIENTE"));
            Piso piso=new Piso();
            piso.setIdPiso(rs.getInt("IDPISO"));
            ambiente.setPiso(piso);
            SedeInfraestructura sedeInfraestructura=new SedeInfraestructura();
            sedeInfraestructura.setIdSedeInfraestructura(rs.getInt("IDSEDEINFRAESTRUCTURA"));
            ambiente.setSedeInfraestructura(sedeInfraestructura);
            TipoAmbiente tipoAmbiente=new TipoAmbiente();
            tipoAmbiente.setIdTipoAmbiente(rs.getInt("IDTIPOAMBIENTE"));
            ambiente.setTipoAmbiente(tipoAmbiente);
            Unidad unidad=new Unidad();
            unidad.setIdUnidad(rs.getInt("IDUNIDAD"));
            unidad.setNombre(rs.getString("UNIDAD"));
            ambiente.setUnidad(unidad);
            ambiente.setNombre(rs.getString("NOMBRE"));
            ambiente.setReservable(rs.getString("RESERVABLE").charAt(0));
            return ambiente;

        }
        
    }
    
    
    public static RowMapper getUnidadAllMapper(){
        return new UnidadAllMapper();
    }
    
    private static final class UnidadAllMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Unidad unidad = new Unidad();
            unidad.setNombre(rs.getString("NOMBRE"));
            unidad.setIdUnidad(rs.getInt("IDUNIDAD"));
          return unidad;
        }
    }
    
    public static RowMapper getEventosPorAmbienteAllMapper(){
        return new EventosPorAmbienteAllMapper();
    }
    
    private static final class EventosPorAmbienteAllMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            FechaEvento fechaEvento = new FechaEvento();
            fechaEvento.setId(rs.getInt("IDFECHAEVENTO"));
            fechaEvento.setIdEvento(rs.getInt("IDEVENTO"));
            fechaEvento.setFechaHoraInicio(rs.getTimestamp("FECHAHORAINICIO"));
            fechaEvento.setFechaHoraFin(rs.getTimestamp("FECHAHORAFIN"));
            fechaEvento.setIdAmbiente(rs.getInt("IDAMBIENTE"));
            fechaEvento.setHoraInicio(rs.getInt("HORA_INICIO"));
            fechaEvento.setHoraFin(rs.getInt("HORA_FIN"));
          return fechaEvento;
        }
    }
}
