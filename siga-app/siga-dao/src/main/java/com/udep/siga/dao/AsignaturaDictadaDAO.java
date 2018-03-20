package com.udep.siga.dao;

import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.AlumnoEstudioPeriodoAcademico;
import com.udep.siga.bean.Asignatura;
import com.udep.siga.bean.AsignaturaDictada;
import com.udep.siga.bean.AsignaturaHistorial;
import com.udep.siga.bean.Horario;
import com.udep.siga.bean.Nota;
import com.udep.siga.bean.Persona;
import com.udep.siga.bean.PracticaProgramada;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Wilfredo Atoche
 */
public interface AsignaturaDictadaDAO {
    public List<AsignaturaDictada> getAsignaturaDictadaList(AlumnoEstudio alumnoEstudio);
    public List<AsignaturaDictada> getAsignaturaDictadaPosgradoList(AlumnoEstudio alumnoEstudio);
    public List<AsignaturaDictada> getAsignaturaDictadaList(int idAlumno);
    public List<AsignaturaDictada> getAsignaturaDictadaProfesorList(int idProfesor);
    public AsignaturaDictada getAsignaturaDictada(int idAsignaturaDictada, int idSeccion);
    public int countAlumnosAsignaturaDictada(AsignaturaDictada asignaturaDictada);
    public List<Persona> findProfesores(AsignaturaDictada asignaturaDictada);
    public Map<String, Object> getTipoPromedioAsignaturaDictada(int idAsignatura, int idSeccion);
    public List<Horario> getHorarioByAsignaturaSeccion(int idAsignaturaDictada, int idSeccion);
    public List<PracticaProgramada> getPracticaProgramadaByAsignaturaSeccion(int idAsignaturaDictada, int idSeccion);
    public List<Date> getFechasHorarioPosgrado(int idAlumno, boolean horarioRegular);
    public List<Horario> getHorarioAsignaturaPosgradoByFecha(int idAlumno, Date fecha, boolean horarioRegular);
    public List<AsignaturaHistorial> getAsignaturaDictadaHistorial(AlumnoEstudioPeriodoAcademico aepa);
    public List<AsignaturaDictada> findAsignaturaDictadaByCampusCAcadPeriodoAcademico(int idCampus, 
            int idCentroAcademico, int idPeriodoAcademico, String sigla, String nombre);
    public String getNombreDeEstudio(AsignaturaDictada asignaturaDictada);
    public Asignatura getAsignaturaById(int id);
    public Nota getPromedioAsignaturaDictadaByAlumno(int idAsignaturaDictada, int idSeccion, int idAlumno);
    public String getNotaLiteral(Nota nota, int idAsignaturaDictada);
    public boolean isAlumnoAsignatura(int idAsignatura, int idSeccion, int idAlumno);
    public Map<String, Boolean> statusNotificacion(int idAlumno, int idEdicionestudio);
    public Map<String, Boolean> statusNotificacion(int idAlumno, int idEdicionestudio, int idAsignaturaDictada,
            int idSeccion);
}
