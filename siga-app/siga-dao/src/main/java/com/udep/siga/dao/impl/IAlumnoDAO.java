package com.udep.siga.dao.impl;

import com.udep.siga.bean.Alumno;
import com.udep.siga.bean.AsesorSugerido;
import com.udep.siga.bean.CentroAcademico;
import com.udep.siga.bean.DatoPersonal;
import com.udep.siga.bean.Email;
import com.udep.siga.bean.Persona;
import com.udep.siga.bean.enumeration.Sexo;
import com.udep.siga.dao.AlumnoDAO;
import com.udep.siga.dao.util.CustomizeJdbcDaoSupport;
import com.udep.siga.dao.util.UtilRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Wilfredo Atoche
 */
@Repository("alumnoDAO")
public class IAlumnoDAO extends CustomizeJdbcDaoSupport implements AlumnoDAO {

     
    public Alumno getById(int id) {
        String sql = "SELECT P.IDPERSONA,P.IDTIPODOCIDENTIDAD,P.IDSEXO,P.NOMBRES,"
                + "P.APELLIDOPATERNO,P.APELLIDOMATERNO,P.DNI,P.FOTO,P.ASESOR,"
                + "A.CARNE, A.ACTUALIZACLAVE, A.ACTUALIZADATOS, A.ACTUALIZAASESORES "
                + "FROM PERSONA P, ALUMNO A WHERE P.IDPERSONA = A.IDALUMNO AND P.IDPERSONA = ?";

        Alumno alumno = (Alumno) this.getJdbcTemplate().queryForObject(sql,
                new Object[]{id}, UtilRowMapper.getAlumnoMapper());

        return alumno;
    }
    
     
    public String getCarne(int id){
        String sql =    "SELECT ALUMNO.CARNE " +
                        "FROM PERSONA INNER JOIN ALUMNO ON (PERSONA.IDPERSONA = ALUMNO.IDALUMNO) " +
                        "WHERE PERSONA.IDPERSONA = ?";
        
        return this.getJdbcTemplate().queryForObject(sql, new Object[]{id}, String.class);
    }
               
     
    public Persona getAsesorPeriodoAcademicoByIdAlumno(int id) {
        String sql = "SELECT TOP 1 P.IDPERSONA, P.IDTIPODOCIDENTIDAD, P.IDSEXO, P.NOMBRES,"
                + "P.APELLIDOPATERNO, P.APELLIDOMATERNO, P.DNI, P.FOTO, P.ASESOR, A.IDPERIODOACADEMICO "
                + "FROM ASESORIA A, PERSONA P, PERIODOACADEMICO PA WHERE A.IDPERSONAASESOR = P.IDPERSONA AND "
                + "A.IDALUMNO = ? AND A.VIGENTE = 1 AND PA.IDPERIODOACADEMICO = A.IDPERIODOACADEMICO "
                + "ORDER BY PA.ORDEN DESC";

        List<Persona> personaList = this.getJdbcTemplate().query(sql, new Object[]{id},
                UtilRowMapper.getAsesorMapper());

        if (personaList.isEmpty()) {
            return null;
        } else {
            return personaList.get(0);
        }
    }
    
     
    public AsesorSugerido getAsesoresSugeridosByPeriodo(int idAlumno,int idPeriodoAcademico) {
        String sql = "SELECT IDALUMNOASESORSUGERIDO,IDPERSONAASESORSUGERIDO1,IDPERSONAASESORSUGERIDO2 "
                + "FROM ALUMNOASESORSUGERIDO WHERE IDALUMNO = ? AND IDPERIODOACADEMICO = ?";

        List<AsesorSugerido> list = this.getJdbcTemplate().query(sql, new Object[]{idAlumno,idPeriodoAcademico},
                UtilRowMapper.getAsesorSugeridoMapper());

        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
    
     
    public List<Map<String, Object>> getCentroAcademicoListParaAsesores(int idAlumno) {
        String sql = "SELECT CAC.IDCENTROACADEMICO, CAC.IDCAMPUS, P.IDSEXO,AE.IDALUMNO,CA.NOMBRE "
                + "FROM ALUMNOESTUDIO AS AE, EDICIONESTUDIO AS EE, ESTUDIO AS E, CENTROACADEMICO AS CA, "
                + "CENTROACADEMICOCAMPUS AS CAC,PERSONA P WHERE AE.IDEDICIONESTUDIO = EE.IDEDICIONESTUDIO "
                + "AND EE.IDESTUDIO = E.IDESTUDIO AND E.IDCENTROACADEMICO = CA.IDCENTROACADEMICO "
                + "AND CA.IDCENTROACADEMICO = CAC.IDCENTROACADEMICO AND AE.IDCAMPUS = CAC.IDCAMPUS "
                + "AND AE.IDALUMNO = ? AND AE.IDESTADOALUMNO = 1 AND AE.IDALUMNO = P.IDPERSONA "
                + "ORDER BY CA.NOMBRE";

        List<Map<String, Object>> list = this.getJdbcTemplate().query(sql, new Object[]{idAlumno},
                new RowMapper() {
            public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                Map<String, Object> item = new HashMap<String, Object>();
                Alumno alumno = new Alumno(rs.getInt("IDALUMNO"));
                alumno.setSexo(Sexo.parse(rs.getInt("IDSEXO")));                
                CentroAcademico centroAcademico= new CentroAcademico
                        (rs.getInt("IDCENTROACADEMICO"),rs.getString("NOMBRE"));                    
                item.put("alumno", alumno);
                item.put("centroAcademico", centroAcademico);
                item.put("idCampus", rs.getInt("IDCAMPUS"));                
                return item;
            }
        });

        if (list.isEmpty()) {
            return null;
        } 
        return list;
    }
    
     
    public List<Persona> getAsesorListByCentro(int idCentroAcademico, int idCampus, int idSexo, int idPeriodo) {
        String sql = "SELECT P.IDPERSONA, P.IDTIPODOCIDENTIDAD, P.IDSEXO, P.NOMBRES,"
                + "P.APELLIDOPATERNO, P.APELLIDOMATERNO, P.DNI, P.FOTO, P.ASESOR "
                + "FROM PERSONAASESORCENTROACADEMICO AS PC, PERSONA AS P "
                + "WHERE PC.IDPERSONAASESOR = P.IDPERSONA AND PC.IDCENTROACADEMICO = ? "
                + "AND PC.IDCAMPUS = ? AND PC.IDPERIODOACADEMICO = ? AND P.IDSEXO = ? "
                + "ORDER BY P.APELLIDOPATERNO, P.APELLIDOMATERNO, P.NOMBRES";

        List<Persona> personaList = this.getJdbcTemplate().query(sql, 
                new Object[]{idCentroAcademico,idCampus,idPeriodo,idSexo},
                UtilRowMapper.getPersonaMapper());

        if (personaList.isEmpty()) {
            return null;
        } else {
            return personaList;
        }
    }
     
    @Transactional
    public void saveAsesorSugerido(int idAlumno, int idPeriodo, AsesorSugerido newAsesorSugerido) {
        AsesorSugerido asesorSugerido = this.getAsesoresSugeridosByPeriodo(idAlumno, idPeriodo);
        String sql;
        if(asesorSugerido != null){
            sql = "UPDATE ALUMNOASESORSUGERIDO SET IDPERSONAASESORSUGERIDO1 = ?, "
                    + "IDPERSONAASESORSUGERIDO2 = ? WHERE IDALUMNOASESORSUGERIDO = ?";
            this.getJdbcTemplate().update(sql, 
                new Object[]{newAsesorSugerido.getAsesor_sugerido_1().getId(),
                    newAsesorSugerido.getAsesor_sugerido_2().getId(),asesorSugerido.getId()});
        }else{
            sql = "INSERT INTO ALUMNOASESORSUGERIDO (IDALUMNO, IDPERSONAASESORSUGERIDO1, "
                    + "IDPERSONAASESORSUGERIDO2, IDPERIODOACADEMICO) VALUES (?, ?, ?, ?)";
            this.getJdbcTemplate().update(sql, 
                new Object[]{idAlumno,newAsesorSugerido.getAsesor_sugerido_1().getId(),
                    newAsesorSugerido.getAsesor_sugerido_2().getId(),idPeriodo});
        }
        
        sql = "UPDATE ALUMNO SET ACTUALIZAASESORES = 1 WHERE IDALUMNO = ?";
            this.getJdbcTemplate().update(sql,new Object[]{idAlumno});
    }
     
    public Boolean yaeligioasesor(int idalumno,int idperiodo)
    {
        String sql = "SELECT COUNT(*) FROM ALUMNOASESORSUGERIDO WHERE IDALUMNO = ? AND IDPERIODOACADEMICO = ?";
        Integer count = this.getJdbcTemplate().queryForObject(sql,
                new Object[]{idalumno,idperiodo}, Integer.class);
        return count > 0;
    }
    /*
     Obtiene lista de alumnos matriculados en una determinada asignatura-seccion.
     */

     
    public List<Persona> getAlumnosAsignaturaDictadaList(int idAsignatura, int idSeccion) {
        String sql = "SELECT P.APELLIDOPATERNO,P.APELLIDOMATERNO,P.NOMBRES "
                + "FROM MATRICULA M,PERSONA P WHERE M.IDALUMNO = P.IDPERSONA "
                + "AND M.IDASIGNATURADICTADA = :idAsignatura AND M.IDSECCION = :idSeccion "
                + "ORDER BY P.APELLIDOPATERNO,P.APELLIDOMATERNO,P.NOMBRES ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idAsignatura", idAsignatura);
        params.addValue("idSeccion", idSeccion);

        List<Persona> personaList = this.getNamedParameterJdbcTemplate().query(sql, params,
                new RowMapper() {
            public Persona mapRow(ResultSet rs, int rowNum) throws SQLException {
                Persona alumno = new Persona();
                alumno.setApellidoPaterno(rs.getString("APELLIDOPATERNO"));
                alumno.setApellidoMaterno(rs.getString("APELLIDOMATERNO"));
                alumno.setNombres(rs.getString("NOMBRES"));
                return alumno;
            }
        });

        return personaList;
    }

    /*
     * Actualiza información personal del Alumno
     */
    @Transactional
     
    public void updateInfo(int idAlumno, DatoPersonal datoPersonal, List<Email> emailList) {
        String sql = "UPDATE DATOSPERSONALES SET TELEFONOMOVIL = :movil, DIRECCIONCASA = :direccion, "
                + "TELEFONOCASA = :telefono,DIRECCIONPENSION = :direccionPension,TELEFONOPENSION = :telefonoPension, "
                + "TELEFONOEMERGENCIA = :telefonoEmergencia,PERSONACONTACTOEMERGENCIA = :personaContacto, "
                + "IDDISTRITOCASA = :idDistrito,IDDISTRITOPENSION = :idDistritoPension WHERE IDPERSONA = :id";
        
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("movil", datoPersonal.getTelefonoMovil());
        params.addValue("direccion", datoPersonal.getDireccion());
        params.addValue("telefono", datoPersonal.getTelefono());
        params.addValue("direccionPension", datoPersonal.getDireccionPension());
        params.addValue("telefonoPension", datoPersonal.getTelefonoPension());
        params.addValue("telefonoEmergencia", datoPersonal.getTelefonoEmergencia());
        params.addValue("personaContacto", datoPersonal.getContactoEmergencia());
        if(datoPersonal.getDistrito().getId() != 0){
            params.addValue("idDistrito", datoPersonal.getDistrito().getId());
        }else{
            params.addValue("idDistrito", null);
        }
        if(datoPersonal.getDistritoPension().getId() != 0){
            params.addValue("idDistritoPension", datoPersonal.getDistritoPension().getId());
        }else{
            params.addValue("idDistritoPension", null);
        }
        
        params.addValue("id", idAlumno);
        
        this.getNamedParameterJdbcTemplate().update(sql, params);
        
        if(emailList != null){
            sql = "SELECT COUNT(*) FROM PERSONAEMAIL WHERE IDPERSONA = ? AND IDTIPOEMAIL = ?";
            String sqlUpdate = "UPDATE PERSONAEMAIL SET EMAIL = ? WHERE IDPERSONA = ? AND IDTIPOEMAIL = ?";
            String sqlInsert = "INSERT INTO PERSONAEMAIL(IDPERSONA,IDTIPOEMAIL,EMAIL) VALUES (?,?,?)";
            for (Email email : emailList) {

                int count = this.getJdbcTemplate().queryForObject(sql,
                        new Object[]{idAlumno, email.getTipoEmail().getId()}, Integer.class);
                if (count > 0) {
                    if(email.getEmail().isEmpty()){
                        this.getJdbcTemplate().update(
                                "DELETE FROM PERSONAEMAIL WHERE IDPERSONA = ? AND IDTIPOEMAIL = ?", 
                                new Object[]{idAlumno, email.getTipoEmail().getId()});
                    }else{
                        this.getJdbcTemplate().update(sqlUpdate,
                                new Object[]{email.getEmail(), idAlumno, email.getTipoEmail().getId()});
                    }
                } else {
                    if(!email.getEmail().isEmpty()){
                        this.getJdbcTemplate().update(sqlInsert,
                                new Object[]{idAlumno, email.getTipoEmail().getId(), email.getEmail()});
                    }
                }
            }
        }
        //agregar tabla alumno ,tabla persona
        sql = "UPDATE ALUMNO SET ACTUALIZADATOS = 1 WHERE IDALUMNO = ?";
            this.getJdbcTemplate().update(sql,new Object[]{idAlumno});
        sql = "UPDATE PERSONA SET FECHAUPDATE =GETDATE(),PERSONAUPDATE = ? WHERE IDPERSONA = ?";
            this.getJdbcTemplate().update(sql,new Object[]{idAlumno,idAlumno});    
    }

     
    public boolean completoEncuesta(int id) {
        String sql = "SELECT COUNT(*) FROM ALUMNOESTUDIO WHERE IDESTADOALUMNO = 1 AND IDTIPOEXALUMNO = 0 AND IDALUMNO = ?";
        Integer count = this.getJdbcTemplate().queryForObject(sql,
                new Object[]{id}, Integer.class);
        return count <= 0;
    }

     
    public int getEdicionestudioByAsignaturaDictada(int idAlumno, int idAsignaturaDictada, int idSeccion) {
        String sql = "SELECT TOP 1 M.IDEDICIONESTUDIO FROM MATRICULA M WHERE M.IDALUMNO = ? AND "
                + "M.IDASIGNATURADICTADA = ? AND M.IDSECCION = ?";
        Integer idEdicionestudio = this.getJdbcTemplate().queryForObject(sql,
                new Object[]{idAlumno, idAsignaturaDictada, idSeccion}, Integer.class);
        
        return idEdicionestudio;
    }
    
    /*
     * ID SECRETARIO FACULTAD
     */
     
    public Persona getSecretarioFacultad(int idCentroAcademico, int idCampus) {
        String sql = "SELECT P.IDPERSONA,P.IDTIPODOCIDENTIDAD,P.IDSEXO,P.NOMBRES,"
                + "P.APELLIDOPATERNO,P.APELLIDOMATERNO,P.DNI,P.FOTO,P.ASESOR "
                + "FROM PERSONA P WHERE P.IDPERSONA = (SELECT ISNULL(IDPERSONASECRETARIO,0) "
                + "FROM CENTROACADEMICOCAMPUS WHERE IDCAMPUS = ? AND IDCENTROACADEMICO = ?)";

        List<Persona> list = this.getJdbcTemplate().query(sql,
                new Object[]{idCampus, idCentroAcademico}, UtilRowMapper.getPersonaMapper());

        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    public Boolean yaActualizoDatos(int idalumno) {
         String sql = "SELECT COUNT(*) FROM ALUMNO WHERE IDALUMNO = ? AND ACTUALIZADATOS=1";
        Integer count = this.getJdbcTemplate().queryForObject(sql,
                new Object[]{idalumno}, Integer.class);
        return count > 0;
    }

   
     
    public boolean verificaMatricula(int idAlumno) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.getJdbcTemplate())
            .withProcedureName("VERICA_ESTUDIO_PARABLOQUEO")
            .declareParameters(new SqlOutParameter("DEBEVALIDAR", Types.BIT));
                
        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("IDALUMNO", idAlumno, Types.INTEGER);
        
        return simpleJdbcCall.executeObject(Boolean.class, params);
    }

    public boolean showAvance(int idCampus, int idCentro, int idTipoEstudio) {
        String sql = "SELECT dbo.[S2_UDF_CONF_MOSTRAR_AVANCECREDITOS](?,?,?)";
        Boolean show = this.getJdbcTemplate().queryForObject(sql,
                new Object[]{idCampus,idCentro,idTipoEstudio}, Boolean.class);
        return show;
    }

    @Transactional
    public Integer registrarAsesoriaHistorial(int campusId, int alumnoId, int periodoAcademicoId, int tipo) {
        new SimpleJdbcCall(this.getJdbcTemplate())
                .withoutProcedureColumnMetaDataAccess()
                .withProcedureName("S2_PROC_I_ALUMNOASESORIA_HISTORIAL")
                .declareParameters(new SqlParameter("IDALUMNO",Types.INTEGER)
                                ,new SqlParameter("IDPERIODOACADEMICO",Types.INTEGER)
                                ,new SqlParameter("IDTIPO",Types.SMALLINT)
                                ,new SqlParameter("IDCAMPUS",Types.INTEGER)
                )
                .execute(new MapSqlParameterSource()
                        .addValue("IDALUMNO", alumnoId)
                        .addValue("IDPERIODOACADEMICO", periodoAcademicoId)
                        .addValue("IDTIPO", tipo)
                        .addValue("IDCAMPUS", campusId)
                );   
                
        return 1;
    }

    @Transactional(readOnly = true)
    public boolean opcionAsesoramientoEscogida(int alumnoId, int periodoAcademicoId, int campusId) {
        Map<String,Object> result =  new SimpleJdbcCall(this.getJdbcTemplate())
                                        .withoutProcedureColumnMetaDataAccess()
                                        .withProcedureName("S2_PROC_V_ALUMNOASESORIA_HISTORIAL")
                                        .declareParameters(new SqlParameter("IDALUMNO",Types.INTEGER)
                                                        ,new SqlParameter("IDPERIODOACADEMICO",Types.INTEGER)
                                                        ,new SqlParameter("IDCAMPUS",Types.INTEGER)
                                                        ,new SqlOutParameter("RESP", Types.INTEGER)
                                        )
                                        .execute(new MapSqlParameterSource()
                                                .addValue("IDALUMNO", alumnoId)
                                                .addValue("IDPERIODOACADEMICO", periodoAcademicoId)
                                                .addValue("IDCAMPUS", campusId)
                                        );
        
        return ((Integer)result.get("RESP")) > 0;
    }
}
