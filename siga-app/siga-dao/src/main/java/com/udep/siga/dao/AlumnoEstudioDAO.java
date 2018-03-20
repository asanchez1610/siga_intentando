package com.udep.siga.dao;

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
import java.util.List;
import java.util.Map;

/**
 *
 * @author Wilfredo Atoche
 */
public interface AlumnoEstudioDAO {
    public List<AlumnoEstudio> getEstudiosInactivosByAlumno(int idAlumno);
    public List<AlumnoEstudio> getEstudiosEgExByAlumno(int idAlumno);
    public List<AlumnoEstudio> getEstudiosByAlumno(int idAlumno, EstadoAlumno estadoAlumno);
    public List<AlumnoEstudio> getEstudiosByAlumno(int idAlumno, EstadoAlumno estadoAlumno, 
            TipoEstudio tipoEstudio);
    public Edicionestudio getEdicionestudioById(int idEdicionEstudio);
    public PlanEstudio getPlanEstudioAlumnoEstudioActivoByAlumnoEstudio(int idAlumno, int idEdicionestudio);
    public String getTipoBeca(AlumnoEstudio alumnoEstudio);
    public List<RequisitoPlanEstudio> getRequisitosPlanEstudioList(int idPlan);
    public boolean isCumpleRequisitoPlanEstudio(int idPlan, AlumnoEstudio alumnoestudio,
            int idTipoRequisito) ;
    public String getCumpleRequisitosPlanEstudio
            (AlumnoEstudio alumnoEstudio, int idPlan, int idTipoRequisito);
    public List<RequisitoTipoAsignatura> getRequisitosTipoAsignaturaPlanEstudioList(int idPlan);
     public int getTotalCreditosCLC(AlumnoEstudio alumnoEstudio);
     public String getCreditosActuales(AlumnoEstudio alumnoEstudio, int idTipoAsignatura);
     public int getTotalCreditosActuales(AlumnoEstudio alumnoEstudio, int idTipoAsignatura);
     public boolean isCumpleRequisitoTipoAsignatura(int idPlan, AlumnoEstudio alumnoestudio,
            int idTipoAsignatura);
     public GradoAcademico getBachillerato(AlumnoEstudio alumnoEstudio);
     public GradoAcademico getTitulo(AlumnoEstudio alumnoEstudio);
     public List<AlumnoEstudioPeriodoAcademico> getHistorialPeriodoAcadByAlumnoEstudio
             (AlumnoEstudio alumnoEstudio);
     public List<Observacion> getObservacionesByAlumnoEstudioHistorial
             (AlumnoEstudioPeriodoAcademico alumnoEstudioPA);
     
     public List<AreaConocimiento> findAreaConocimientosByPlanEstudioId(
            int idPlanEstudio, AlumnoEstudio alumnoEstudio);
     
     public PlanEstudioAsignatura findAsignaturaById_AreaConocimientoPlanEstudioIdEspecialidad(int idAreaConocimiento,
            int idPlanEstudio, int idAsignatura, int idEspecialidad, AlumnoEstudio alumnoEstudio);
     
     public List<InfoRequisito> getRequisitosByPlanEstudioAsignatura(PlanEstudioAsignatura planEstudioAsignatura);
     
     public int getEstadoAsignaturaAlumnoPlanEstudio(int planEstudioId, AlumnoEstudio alumnoEstudio, int asignaturaId);
     
     public int getNotaHistorial(AlumnoEstudio alumnoEstudio, int asignaturaId, int planEstudioId);
     
     public List<PEAEAsignaturaEstado> getPlanEstudioAsignaturaEstado(int idAlumno, int idPlanEstudio,
             TipoAsignatura tipoAsignatura);
     
     public int countTotalCredDesaprobados(int idAlumno, int idPlanEstudio, 
            TipoAsignatura tipoAsignatura);
     
     public String getValorRequisitoPlanEstudio(int idPlanEstudio, TipoAsignatura tipoAsignatura);
     
     public Map<String, Object> isReincorporado(AlumnoEstudio alumnoEstudio);
     
     public boolean isCachimbo(AlumnoEstudio alumnoEstudio);
     public boolean isVerNiveles(int edicionestudio,int campus,int periodo);
     public boolean verDelegadosGeneral(int edicionestudio,int campus,int periodo);
     public boolean verCandidatosporNivel(int edicionestudio,int campus,int periodo,int nivel);
     public int contarvotoscandidato(Alumnocandidato  alumnocandidato);
     public boolean verFechaResultadosVotacion(int edicionestudio,int campus,int periodo);
     public boolean isVerDelegados(int idPeriodoAcademico, int idCampus,int edicionEstudio);
     public boolean isVerBotonElegir(int idPeriodoAcademico, int idCampus,int edicionEstudio);
     public String isFechaVotacion(int idPeriodoAcademico, int idCampus,int edicionEstudio);
      public String isFechaResultados(int idPeriodoAcademico, int idCampus,int edicionEstudio);
      public List<Alumnocandidato> getAlumnoCandidatoLista(int edicionestudio,int campus,int periodo,int nivel);
      
      public int guardarvotaciondelegado(int campus,int periodoacademico,int alumnocandidato,int idalumno,int idedicionestudio);       
      public boolean getAlumnoVoto(int idPeriodoAcademico, int idCampus,int edicionEstudio,int idalumno);
     public AlumnoEstudioPeriodoAcademico getPeriodoAcadAlumnoEstudioByPeriodo
             (AlumnoEstudio alumnoEstudio, PeriodoAcademico periodo);
    
    /**
     * OBTIENE LISTA DE REQUISITOPLANESTUDIOS POR IDALUMNO Y ESTUDIO
     * @param idAlumno
     * @param idEdicionEstudio
     * @return
     */
    public List<Map> getListRequisitosPlanEstudio(int idAlumno, int idEdicionEstudio);
    
    /**
     * 
     * @param idAlumno
     * @param idEdicionEstudio
     * @return
     */
    public List<Map> getListRequisitosTitulo(int idAlumno, int idEdicionEstudio);
    
}
