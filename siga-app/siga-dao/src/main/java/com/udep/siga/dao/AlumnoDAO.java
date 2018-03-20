package com.udep.siga.dao;

import com.udep.siga.bean.Alumno;
import com.udep.siga.bean.AsesorSugerido;
import com.udep.siga.bean.DatoPersonal;
import com.udep.siga.bean.Email;
import com.udep.siga.bean.Persona;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Wilfredo Atoche
 */
public interface AlumnoDAO {
    public Alumno getById(int id);
    public String getCarne(int id);
    public Persona getAsesorPeriodoAcademicoByIdAlumno(int id);
    public AsesorSugerido getAsesoresSugeridosByPeriodo(int idAlumno,int idPeriodoAcademico);
    public List<Map<String, Object>> getCentroAcademicoListParaAsesores(int idAlumno);
    public List<Persona> getAsesorListByCentro(int idCentroAcademico, int idCampus, int idSexo, int idPeriodo);
    public void saveAsesorSugerido(int idAlumno, int idPeriodo, AsesorSugerido newAsesorSugerido);
    public List<Persona> getAlumnosAsignaturaDictadaList(int idAsignatura, int idSeccion);    
    public void updateInfo(int idAlumno, DatoPersonal datoPersonal, List<Email> emailList);
    public boolean completoEncuesta(int idAlumno);
    public Boolean yaeligioasesor(int idalumno,int idperiodo);
    public Boolean yaActualizoDatos(int idalumno);
    public int getEdicionestudioByAsignaturaDictada(int idAlumno, int idAsignaturaDictada, int idSeccion);
    public Persona getSecretarioFacultad(int idCentroAcademico, int idCampus);
    public boolean verificaMatricula(int idAlumno);
    public boolean showAvance(int idCampus, int idCentro, int idTipoEstudio);
    Integer registrarAsesoriaHistorial(int campusId,int alumnoId,int periodoAcademicoId,int tipo);
    boolean opcionAsesoramientoEscogida(int alumnoId,int periodoAcademicoId,int campusId);
}
