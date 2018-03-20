package com.udep.siga.service;

import com.udep.siga.bean.Alumno;
import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.AlumnoEstudioPeriodoAcademico;
import com.udep.siga.bean.Alumnocandidato;
import com.udep.siga.bean.AreaConocimiento;
import com.udep.siga.bean.AsesorSugerido;
import com.udep.siga.bean.CentroAcademico;
import com.udep.siga.bean.DatoPersonal;
import com.udep.siga.bean.Edicionestudio;
import com.udep.siga.bean.Email;
import com.udep.siga.bean.GradoAcademico;
import com.udep.siga.bean.PEAEAsignaturaEstado;
import com.udep.siga.bean.PeriodoAcademico;
import com.udep.siga.bean.Persona;
import com.udep.siga.bean.PlanEstudio;
import com.udep.siga.bean.PlanEstudioAsignatura;
import com.udep.siga.bean.RequisitoPlanEstudio;
import com.udep.siga.bean.RequisitoTipoAsignatura;
import com.udep.siga.bean.enumeration.Campus;
import com.udep.siga.bean.enumeration.Sexo;
import com.udep.siga.bean.enumeration.TipoAsignatura;
import com.udep.siga.bean.enumeration.TipoRequisitoPlan;
import com.udep.siga.dao.AlumnoDAO;
import com.udep.siga.dao.AlumnoEstudioDAO;
import com.udep.siga.dao.AsignaturaDictadaDAO;
import com.udep.siga.dao.PeriodoAcademicoDAO;
import com.udep.siga.dao.WebServiceArgusDAO;
import com.udep.siga.util.AppConstants;
import com.udep.siga.util.Rol;
import com.udep.siga.util.Util;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Wilfredo Atoche
 */
@Service("alumnoEstudioService")
public class AlumnoEstudioService {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private CommonsService commonsService;
    @Autowired
    private AlumnoEstudioDAO alumnoEstudioDAO;
    @Autowired
    private AlumnoDAO alumnoDAO;
    @Autowired
    private PeriodoAcademicoDAO periodoAcademicoDAO;
    @Autowired
    private AsignaturaDictadaDAO asignaturaDictadaDAO;
    private @Autowired WebServiceArgusDAO webServiceArgusDAO;

    public List<AlumnoEstudio> getEstudiosAlumnoList() {
        Alumno alumno = usuarioService.llenarEdicionEstudioSession();
        return alumno.getAlumnoEstudioList();
    }

    public List<AlumnoEstudio> getEstudioAllList() {
        List<AlumnoEstudio> list = new ArrayList<AlumnoEstudio>();

        Alumno alumno = usuarioService.llenarEdicionEstudioSession();

        list.addAll(alumno.getAlumnoEstudioList());

        List<AlumnoEstudio> inactivoList = alumnoEstudioDAO.getEstudiosInactivosByAlumno(alumno.getId());
        for (AlumnoEstudio alumnoEstudio : inactivoList) {
            Edicionestudio edicionestudio = alumnoEstudioDAO
                    .getEdicionestudioById(alumnoEstudio.getEdicionestudio().getId());
            alumnoEstudio.setEdicionestudio(edicionestudio);
            alumnoEstudio.setPeriodoAcademicoVigente(null);
        }
        alumno.setAlumnoEstudioInactivoList(inactivoList);

        if(Util.isUsuarioAuthority(Rol.EX_EG_PREGRADO.getRol()) || 
                        Util.isUsuarioAuthority(Rol.EX_EG_POSGRADO.getRol())){
            for (AlumnoEstudio aeInactivo : inactivoList) {
                boolean exists = false;
                for (AlumnoEstudio ae : list) {
                    if(ae.getEdicionestudio().getId() == aeInactivo.getEdicionestudio().getId()){
                        exists = true;
                        break;
                    }
                }
                if(!exists){
                    list.add(aeInactivo);
                }
            }
        }else{        
            list.addAll(inactivoList);
        }
        return list;
    }

    /*
     * Recuperamos Alumno Estudio
     */
    public AlumnoEstudio getAlumnoEstudio(int idEdicionestudio) {
        AlumnoEstudio alumnoEstudio = commonsService.getAlumnoEstudioSession(idEdicionestudio);
        return alumnoEstudio;
    }

    public String getTipoBeca(AlumnoEstudio alumnoEstudio) {
        return webServiceArgusDAO.infoAlumnoTipoBeca(alumnoEstudio.getCampus()
                        , usuarioService.getInfoUsuario().getCarne()
                        , alumnoEstudio.getEdicionestudio().getId()
                        , alumnoEstudio.getPeriodoAcademicoVigente().getId());
//        return alumnoEstudioDAO.getTipoBeca(alumnoEstudio);
    }

    public Persona getAsesorAlumno() {
        Alumno alumno = usuarioService.llenarEdicionEstudioSession();
        Persona persona = alumnoDAO.getAsesorPeriodoAcademicoByIdAlumno(alumno.getId());

        return persona;
    }
    
    public AsesorSugerido getAsesoresSugeridosByPeriodo(int idAlumno,int idPeriodoAcademico){
        AsesorSugerido asesor = alumnoDAO.getAsesoresSugeridosByPeriodo(idAlumno, idPeriodoAcademico);
        asesor.setAsesor_sugerido_1(usuarioService.getPersonaById(asesor.getAsesor_sugerido_1().getId()));
        asesor.setAsesor_sugerido_2(usuarioService.getPersonaById(asesor.getAsesor_sugerido_2().getId()));
        return asesor;
    }
    
    public List<Map<String, Object>> getSugeridosList(int idAlumno,int idPeriodoAcademico){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> item;
        Map<String, Object> itemAsesor;
        List<Persona> asesores;
        CentroAcademico centroAcad;
        Alumno alumno;
        List<Map<String, Object>> centros = alumnoDAO.getCentroAcademicoListParaAsesores(idAlumno);
        List<Map<String, Object>> asesoresList;
        for (Map<String, Object> data : centros) {
            item = new HashMap<String, Object>();
            centroAcad = (CentroAcademico)data.get("centroAcademico");
            alumno = (Alumno) data.get("alumno");
            asesores = alumnoDAO.getAsesorListByCentro(centroAcad.getId()
                    ,(Integer)data.get("idCampus"),alumno.getSexo().getId() , idPeriodoAcademico);   
            
            if (alumno.getSexo().getId() == Sexo.MASCULINO.getId()) {
                item.put("fotoDefault", AppConstants.PATH_FOTO_MASCULINO_DEFAULT);
            } else {
                if (alumno.getSexo().getId() == Sexo.FEMENINO.getId()) {
                    item.put("fotoDefault", AppConstants.PATH_FOTO_FEMENINO_DEFAULT);
                }
            }
            item.put("centro", centroAcad.getNombre());  
            asesoresList = new ArrayList<Map<String, Object>>();
            for (Persona asesor : asesores) {
                itemAsesor = new HashMap<String, Object>();
                itemAsesor.put("id", usuarioService.addRefIndirecta(asesor.getId()));
                itemAsesor.put("nombres", asesor.getApellidoPaterno() + " " 
                        + asesor.getApellidoMaterno() + ", " + asesor.getNombres());
                asesoresList.add(itemAsesor);
            }
            item.put("asesorList", asesoresList);
            list.add(item);
        }
        return list;
    }
    
    public List<Map<String, Object>> getSugeridosListUpdated(int idAlumno,int idPeriodoAcademico){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> item;
        Map<String, Object> itemAsesor;
        List<Persona> asesores;
        CentroAcademico centroAcad;
        Alumno alumno;
        List<Map<String, Object>> centros = alumnoDAO.getCentroAcademicoListParaAsesores(idAlumno);
        List<Map<String, Object>> asesoresList;
        for (Map<String, Object> data : centros) {
            item = new HashMap<String, Object>();
            centroAcad = (CentroAcademico)data.get("centroAcademico");
            alumno = (Alumno) data.get("alumno");
            asesores = alumnoDAO.getAsesorListByCentro(centroAcad.getId()
                    ,(Integer)data.get("idCampus"),alumno.getSexo().getId() , idPeriodoAcademico);   
            
            if (alumno.getSexo().getId() == Sexo.MASCULINO.getId()) {
                item.put("fotoDefault", AppConstants.PATH_FOTO_MASCULINO_DEFAULT);
            } else {
                if (alumno.getSexo().getId() == Sexo.FEMENINO.getId()) {
                    item.put("fotoDefault", AppConstants.PATH_FOTO_FEMENINO_DEFAULT);
                }
            }
            AsesorSugerido asesoressugeridos =getAsesoresSugeridosByPeriodo(idAlumno, idPeriodoAcademico);
            item.put("fotoasesor1",asesoressugeridos.getAsesor_sugerido_1().getFoto() );
           // System.out.println("url 1"+asesoressugeridos.getAsesor_sugerido_1().getFoto());
             item.put("fotoasesor2",asesoressugeridos.getAsesor_sugerido_2().getFoto() );
           //   System.out.println("url 2"+asesoressugeridos.getAsesor_sugerido_2().getFoto());
             item.put("idasesor1",usuarioService.addRefIndirecta(asesoressugeridos.getAsesor_sugerido_1().getId()) );
             item.put("idasesor2",usuarioService.addRefIndirecta(asesoressugeridos.getAsesor_sugerido_2().getId()) );
            item.put("centro", centroAcad.getNombre());  
            asesoresList = new ArrayList<Map<String, Object>>();
            for (Persona asesor : asesores) {
                itemAsesor = new HashMap<String, Object>();
                itemAsesor.put("id", usuarioService.addRefIndirecta(asesor.getId()));
                itemAsesor.put("nombres", asesor.getApellidoPaterno() + " " 
                        + asesor.getApellidoMaterno() + ", " + asesor.getNombres());
                asesoresList.add(itemAsesor);
            }
            item.put("asesorList", asesoresList);
            list.add(item);
        }
        return list;
    }
    
    public void saveAsesorSugerido(int idAlumno, int idPeriodo, AsesorSugerido newAsesorSugerido){
        alumnoDAO.saveAsesorSugerido(idAlumno, idPeriodo, newAsesorSugerido);             
    }

    public PlanEstudio getPlanEstudioAlumnoEstudioActivoByEstudio(int idEdicionestudio) {
        Alumno alumno = usuarioService.getInfoUsuario();
        return alumnoEstudioDAO.getPlanEstudioAlumnoEstudioActivoByAlumnoEstudio(alumno.getId(), idEdicionestudio);
    }

    public String getOrdenMeritoAlumno(int ordenMeritoTotal, int ordenMerito,
            boolean quinto, boolean tercio) {
        String orden;
        if (ordenMeritoTotal != 0) {
            orden = (ordenMerito + "/" + ordenMeritoTotal) + "";
            if (quinto) {
                orden += " - Quinto Superior";
                return orden;
            }
            if (tercio) {
                orden += " - Tercio Superior";
                return orden;
            }
            return orden;
        }
        return "No calculado";
    }

    public String getRendimientoAlumnoIndicadores(int creditosTotalesAprobados,
            int creditosTotalesDesaprobados) {

        int total = creditosTotalesAprobados + creditosTotalesDesaprobados;

        if (total != 0) {
            DecimalFormat df = new DecimalFormat("#.##");

            double r = (Double.parseDouble(String.valueOf(creditosTotalesAprobados))
                    / (Double.parseDouble(String.valueOf(total)))) * 100;

            return df.format(r) + "%";
        } else {
            return "-";
        }
    }

    public PeriodoAcademico getPeriodoAnterior(AlumnoEstudio alumnoEstudio, int numero) {
        if (alumnoEstudio.getPeriodoAcademicoVigente() != null) {
            /*return periodoAcademicoDAO.getPeriodoAnterior(alumnoEstudio.getAlumno().getId(),
             alumnoEstudio.getEdicionestudio().getId(), numero,
             alumnoEstudio.getPeriodoAcademicoVigente().getOrden());*/
            return periodoAcademicoDAO.getPeriodoAnterior(alumnoEstudio.getAlumno().getId(),
                    alumnoEstudio.getEdicionestudio().getId(), numero);
        } else {
            PeriodoAcademico periodoAcademico = periodoAcademicoDAO.getUltimoPAByAlumnoEstudioInactivo(alumnoEstudio);
            if (periodoAcademico != null) {
                /*return periodoAcademicoDAO.getPeriodoAnterior(alumnoEstudio.getAlumno().getId(),
                 alumnoEstudio.getEdicionestudio().getId(), 0,
                 periodoAcademico.getOrden());*/
                return periodoAcademicoDAO.getPeriodoAnterior(alumnoEstudio.getAlumno().getId(),
                        alumnoEstudio.getEdicionestudio().getId(), 0);
            }
            return null;
        }
    }

    public String getBiPeriodoAnterior(AlumnoEstudio alumnoEstudio, int numero) {
        if (alumnoEstudio.getPeriodoAcademicoVigente() != null) {
            return periodoAcademicoDAO.getBiPeriodoAnterior(alumnoEstudio.getAlumno().getId(),
                    alumnoEstudio.getEdicionestudio().getId(), numero,
                    alumnoEstudio.getPeriodoAcademicoVigente().getOrden());
        } else {
            PeriodoAcademico periodoAcademico = periodoAcademicoDAO.getUltimoPAByAlumnoEstudioInactivo(alumnoEstudio);
            if (periodoAcademico != null) {
                return periodoAcademicoDAO.getBiPeriodoAnterior(alumnoEstudio.getAlumno().getId(),
                        alumnoEstudio.getEdicionestudio().getId(), 0,
                        periodoAcademico.getOrden());
            }
            return null;
        }
    }
    
    public List<Map> getListRequisitosPlanEstudio(int idAlumno, int idEdicionEstudio){
        return alumnoEstudioDAO.getListRequisitosPlanEstudio(idAlumno, idEdicionEstudio);
    }
    
    public List<Map> getListRequisitosTitulo(int idAlumno, int idEdicionEstudio){
        return alumnoEstudioDAO.getListRequisitosTitulo(idAlumno, idEdicionEstudio);
    }

    public List<RequisitoPlanEstudio> getRequisitosPlanEstudioList(AlumnoEstudio alumnoEstudio) {
        int countCreditos = 0;
        int idEdicionEstudio = alumnoEstudio.getEdicionestudio().getId();
        PlanEstudio planEstudio = alumnoEstudioDAO
                .getPlanEstudioAlumnoEstudioActivoByAlumnoEstudio(alumnoEstudio.getAlumno().getId(),
                alumnoEstudio.getEdicionestudio().getId());
        
        if (planEstudio == null) {
            return new ArrayList<RequisitoPlanEstudio>();
        }
        List<RequisitoPlanEstudio> requisitoList = alumnoEstudioDAO.getRequisitosPlanEstudioList(planEstudio.getId());

        for (RequisitoPlanEstudio requisitoPlanEstudio : requisitoList) {
            if (requisitoPlanEstudio.getTipoRequisitoPlan().equals(TipoRequisitoPlan.CREDITOS_TOTALES)) {
                requisitoPlanEstudio.setRequisitoTipoAsignaturaList(alumnoEstudioDAO
                        .getRequisitosTipoAsignaturaPlanEstudioList(planEstudio.getId()));

                for (RequisitoTipoAsignatura requisitoTA : requisitoPlanEstudio.getRequisitoTipoAsignaturaList()) {
                    requisitoTA.setValorActual(alumnoEstudioDAO
                            .getCreditosActuales(alumnoEstudio, requisitoTA.getTipoAsignatura().getId()));
                    requisitoTA.setCumpleRequisito(alumnoEstudioDAO
                            .isCumpleRequisitoTipoAsignatura(planEstudio.getId(), alumnoEstudio, requisitoTA.getTipoAsignatura().getId()));
                    if(!requisitoTA.getValorActual().contains("-")){
                        countCreditos += Integer.parseInt(requisitoTA.getValorActual());
                    }
                }
                
                if (idEdicionEstudio == 4 || idEdicionEstudio == 5 || idEdicionEstudio == 6 || idEdicionEstudio == 7) {
                    requisitoPlanEstudio.setValorActual(String.valueOf(countCreditos));
                }else{
                    requisitoPlanEstudio.setValorActual(String.valueOf(alumnoEstudio.getCreditosTotalesCumplidos()));
                }
                requisitoPlanEstudio.setCumpleRequisito(
                        alumnoEstudioDAO.isCumpleRequisitoPlanEstudio(planEstudio.getId(),
                        alumnoEstudio, requisitoPlanEstudio.getTipoRequisitoPlan().getId()));
            } else {
                requisitoPlanEstudio.setCumpleRequisito(
                        alumnoEstudioDAO.isCumpleRequisitoPlanEstudio(planEstudio.getId(),
                        alumnoEstudio, requisitoPlanEstudio.getTipoRequisitoPlan().getId()));
                if (requisitoPlanEstudio.isCumpleRequisito()) {
                    requisitoPlanEstudio.setValorActual(requisitoPlanEstudio.getValor());
                } else {
                    requisitoPlanEstudio.setValorActual(
                            alumnoEstudioDAO.getCumpleRequisitosPlanEstudio(
                            alumnoEstudio, planEstudio.getId(), requisitoPlanEstudio.getTipoRequisitoPlan().getId()));
                }
            }

        }

        return requisitoList;
    }

    public List<RequisitoPlanEstudio> getRequisitosTituloList(AlumnoEstudio alumnoEstudio) {
        List<RequisitoPlanEstudio> tituloList = new ArrayList<RequisitoPlanEstudio>();
        RequisitoPlanEstudio requisitoPlanEstudio = new RequisitoPlanEstudio();
        requisitoPlanEstudio.setValor("Bachiller");
        GradoAcademico gradoAcademico = alumnoEstudioDAO.getBachillerato(alumnoEstudio);
        if (gradoAcademico != null) {
            requisitoPlanEstudio.setValorActual(gradoAcademico.getModoObtencion());
            requisitoPlanEstudio.setCumpleRequisito(true);
        } else {
            requisitoPlanEstudio.setValorActual("No cumplido");
            requisitoPlanEstudio.setCumpleRequisito(false);
        }
        tituloList.add(requisitoPlanEstudio);

        requisitoPlanEstudio = new RequisitoPlanEstudio();
        requisitoPlanEstudio.setValor("Modo de obtención");
        gradoAcademico = alumnoEstudioDAO.getTitulo(alumnoEstudio);
        if (gradoAcademico != null) {
            requisitoPlanEstudio.setValorActual(gradoAcademico.getModoObtencion());
            requisitoPlanEstudio.setCumpleRequisito(true);
        } else {
            requisitoPlanEstudio.setValorActual("No cumplido");
            requisitoPlanEstudio.setCumpleRequisito(false);
        }
        tituloList.add(requisitoPlanEstudio);
        return tituloList;
    }

    public List<AlumnoEstudioPeriodoAcademico> getEstudiosHistorialByAlumno(AlumnoEstudio alumnoEstudio) {
        List<AlumnoEstudioPeriodoAcademico> list = alumnoEstudioDAO.getHistorialPeriodoAcadByAlumnoEstudio(alumnoEstudio);
        int count = 0;
        for (AlumnoEstudioPeriodoAcademico aepa : list) {
            aepa.setPeriodoAcademico(periodoAcademicoDAO.getPA(aepa.getPeriodoAcademico().getId()));
            aepa.setRendimientoShow(this.getRendimientoAlumnoIndicadores(aepa.getCreditosTotalesAprobados(),
                    aepa.getCreditosTotalesDesaprobados()));
            aepa.setBiperiodoTituloShow(periodoAcademicoDAO.getBiPeriodoAnterior(
                    aepa.getAlumno().getId(), aepa.getEdicionestudio().getId(),
                    count /*0*/, aepa.getPeriodoAcademico().getOrden()));
            aepa.setOrdenMeritoShow(this.getOrdenMeritoAlumno(
                    aepa.getOrdenMeritoTotal(), aepa.getOrdenMerito(), aepa.isQuinto(), aepa.isTercio()));
            aepa.setObservacionList(alumnoEstudioDAO.getObservacionesByAlumnoEstudioHistorial(aepa));
            aepa.setAsignaturaDictadaList(asignaturaDictadaDAO.getAsignaturaDictadaHistorial(aepa));
            count ++;
        }
        return list;
    }

    /*
     * Actualiza Información Personal del Alumno
     */
    public List<String> updateInfo(int idAlumno, DatoPersonal datoPersonal, List<Email> emailList) {
        List<String> errores = new ArrayList<String>();
        if (idAlumno != 0 && datoPersonal != null && !emailList.isEmpty()) {
            alumnoDAO.updateInfo(idAlumno, datoPersonal, emailList);
        } else {
            errores.add("Error al actualizar información personal.");
        }
        return errores;
    }

    /*
     * Matriz de Periodo Academico - Indice Acumulado
     */
    public List<Map<String, Object>> getPeriodosAcademicosAlumnoList(int idEdicionestudio) {
        AlumnoEstudio alumnoEstudio = commonsService.getAlumnoEstudioSession(idEdicionestudio);
        return periodoAcademicoDAO.getPeriodosAcademicosAlumnoList(alumnoEstudio);
    }

    /*
     * Areas de Conocimiento para Malla
     */
    public List<AreaConocimiento> getAreasDeConocimiento(int idPlanEstudio, int idEdicionestudio) {
        AlumnoEstudio alumnoEstudio = commonsService.getAlumnoEstudioSession(idEdicionestudio);
        List<AreaConocimiento> areaConocimientosList = alumnoEstudioDAO.
                findAreaConocimientosByPlanEstudioId(idPlanEstudio, alumnoEstudio);
        return areaConocimientosList;
    }
    
    public PlanEstudioAsignatura findAsignaturaById_AreaConocimientoPlanEstudioIdEspecialidad(int idAreaConocimiento,
            int idPlanEstudio, int idAsignatura, int idEspecialidad, int idEdicionestudio) {
        AlumnoEstudio alumnoEstudio = commonsService.getAlumnoEstudioSession(idEdicionestudio);
        return alumnoEstudioDAO.findAsignaturaById_AreaConocimientoPlanEstudioIdEspecialidad(idAreaConocimiento, idPlanEstudio, idAsignatura, idEspecialidad, alumnoEstudio);
    }

    /*
     * Lista de Estado de asignaturas de acuerdo al Plan Activo del Alumno
     */
    public Map<String, Object> getPlanEstudioAsignaturaEstado(int idAlumno, int idPlanEstudio,
            TipoAsignatura tipoAsignatura) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<PEAEAsignaturaEstado> asignaturaEstadoList = alumnoEstudioDAO
                .getPlanEstudioAsignaturaEstado(idAlumno, idPlanEstudio, tipoAsignatura);
        int credCumplidos = 0;
        int credLlevados = 0;
        int credDesaprobados = alumnoEstudioDAO.countTotalCredDesaprobados(idAlumno, idPlanEstudio, tipoAsignatura);
        int credNoLlevados = 0;
        int credExonerados = 0;
        int credOfrecidos = 0;
        for (PEAEAsignaturaEstado item : asignaturaEstadoList) {
            credOfrecidos += item.getCreditos();
            if (item.getEstado().equals("En curso")) {
                item.setNota("-");
                item.setColor("#faef94");
                credLlevados += item.getCreditos();
            } else if (item.isAprobada() && !item.isEquivalente()) {
                item.setColor("#9cd08b");
                if (item.getEstado().equals("Cumplida (Igualdad)")) {
                    String partesCadena[] = item.getRegla().split("\\,");
                    String regla = partesCadena[0];
                    item.setNota(partesCadena[1]);
                    item.setRegla(regla);
                }
                credCumplidos += item.getCreditos();
            } else if (item.isDesaprobada() && !item.isEquivalente()) {
                item.setColor("#f15861");
            } else if (item.isConvalidada() && !item.isEquivalente()) {
                item.setColor("#9cd08b");
                item.setNota("C");
                credCumplidos += item.getCreditos();
            } else if (item.isConvalidadaexterna() && !item.isEquivalente()) {
                item.setColor("#9cd08b");
                item.setNota("CE");
                credCumplidos += item.getCreditos();
            } else if (item.isExonerada() && !item.isEquivalente()) {
                item.setColor("#fbad56");
                item.setNota("E");
                credExonerados += item.getCreditos();
            } else if (item.isEquivalente()) {
                item.setColor("#9cd08b");
                item.setNota("Q");
                credCumplidos += item.getCreditos();
            } else if (item.getEstado().equals("No llevada")) {
                item.setColor("#ffffff");
                item.setNota("");
                credNoLlevados += item.getCreditos();
            }
        }

        result.put("list", asignaturaEstadoList);

        result.put("credCumplidos", credCumplidos);
        result.put("credLlevados", credLlevados);
        result.put("credDesaprobados", credDesaprobados);
        result.put("credNoLlevados", credNoLlevados);
        result.put("credExonerados", credExonerados);
        result.put("credRequeridos", alumnoEstudioDAO.getValorRequisitoPlanEstudio(idPlanEstudio, tipoAsignatura));
        result.put("credOfrecidos", credOfrecidos);

        return result;
    }

    public int getCreditosActuales(AlumnoEstudio alumnoEstudio, int idTipoAsignatura){
        return alumnoEstudioDAO.getTotalCreditosActuales(alumnoEstudio, idTipoAsignatura);
    }
    /*
     * Verifica si anulo Ciclo
     */
    public boolean anuloCicloByAlumnoEstudioPeriodoAcademico(int idEdicionestudio) {
        AlumnoEstudio alumnoEstudio = commonsService.getAlumnoEstudioSession(idEdicionestudio);
        
        if(alumnoEstudio.getPeriodoAcademicoVigente() != null){
            return periodoAcademicoDAO.anuloCicloByAlumnoEstudioPeriodoAcademico(alumnoEstudio.getAlumno().getId(),
                    idEdicionestudio, alumnoEstudio.getPeriodoAcademicoVigente().getId());
        }else{
            return false;
        }
    }
    
    public boolean anuloCicloByAlumnoEstudioPeriodoAcademico(int idAlumno, int idAsignaturaDictada, int idSeccion) {
        int idEdicionestudio = alumnoDAO.getEdicionestudioByAsignaturaDictada(idAlumno, 
                idAsignaturaDictada, idSeccion);
        return anuloCicloByAlumnoEstudioPeriodoAcademico(idEdicionestudio);
    }

    /*
     * Condición de Reincorporado
     */
    public Map<String, Object> isReincorporado(AlumnoEstudio alumnoEstudio) {
        return alumnoEstudioDAO.isReincorporado(alumnoEstudio);
    }
    
    public boolean isPregrado(AlumnoEstudio alumnoEstudio) {
        String isPregrado = alumnoEstudio.getEdicionestudio().getEstudio().getTipoEstudio().getNombre();
        if(isPregrado.equals("Pregrado")){
            return true;
        }
        return false;    
    }
    
    public boolean isCachimbo(AlumnoEstudio alumnoEstudio){
        return alumnoEstudioDAO.isCachimbo(alumnoEstudio);
    }
   public boolean getAlumnoVoto(int idPeriodoAcademico, int idCampus,int edicionEstudio,int idalumno)
   {
       return alumnoEstudioDAO.getAlumnoVoto(idPeriodoAcademico,idCampus,edicionEstudio,idalumno);
   }
    public String isFechaVotacion(int idPeriodoAcademico, int idCampus,int edicionEstudio){
        return alumnoEstudioDAO.isFechaVotacion(idPeriodoAcademico,  idCampus, edicionEstudio);
    }
    public String isFechaResultados(int idPeriodoAcademico, int idCampus,int edicionEstudio){
        return alumnoEstudioDAO.isFechaResultados(idPeriodoAcademico,  idCampus, edicionEstudio);
    }
    public boolean isVerBotonElegir(int idPeriodoAcademico, int idCampus,int edicionEstudio){
        return alumnoEstudioDAO.isVerBotonElegir(idPeriodoAcademico,  idCampus, edicionEstudio);
    }
    public boolean isVer(int idPeriodoAcademico, int idCampus,int edicionEstudio){
        return alumnoEstudioDAO.isVerDelegados(idPeriodoAcademico,  idCampus, edicionEstudio);
    }
     public boolean isDelegados(int edicionestudio,int campus,int periodo){
        return alumnoEstudioDAO.verDelegadosGeneral(edicionestudio,campus,periodo);
    }
      public boolean verCandidatosporNivel(int edicionestudio,int campus,int periodo,int nivel){
        return alumnoEstudioDAO.verCandidatosporNivel(edicionestudio,campus,periodo,nivel);
    }
      public boolean verFechaResultadosVotacion(int edicionestudio,int campus,int periodo){
        return alumnoEstudioDAO.verFechaResultadosVotacion(edicionestudio,campus,periodo);
    }
     public List<Alumnocandidato> getAlumnoCandidatoVotos(int edicionestudio,int campus,int periodo,int nivel){
         List<Alumnocandidato> lista=alumnoEstudioDAO.getAlumnoCandidatoLista(edicionestudio, campus, periodo, nivel);
          for (int i = 0; i < lista.size(); i++) {  
           lista.get(i).setAlumno(alumnoDAO.getById(lista.get(i).getIdalumno()));
           lista.get(i).setAlumnostr(usuarioService.addRefIndirecta(lista.get(i).getIdalumno()));
           lista.get(i).setCantidadvotos(alumnoEstudioDAO.contarvotoscandidato(lista.get(i)));
           
    }  
        return lista;
    }
    public List<Alumnocandidato> getAlumnoCandidatoLista(int edicionestudio,int campus,int periodo,int nivel){ 
        List<Alumnocandidato> lista=alumnoEstudioDAO.getAlumnoCandidatoLista(edicionestudio, campus, periodo, nivel);
            for (int i = 0; i < lista.size(); i++) {  
           lista.get(i).setAlumno(alumnoDAO.getById(lista.get(i).getIdalumno()));
           lista.get(i).setAlumnostr(usuarioService.addRefIndirecta(lista.get(i).getIdalumno()));
    }  
          return lista;
    }
    public int guardarvotaciondelegado(int campus,int periodoacademico,int alumnocandidato,int idalumno,int idedicionestudio)
    {      
        return alumnoEstudioDAO.guardarvotaciondelegado(campus, periodoacademico, alumnocandidato, idalumno, idedicionestudio);
    }
    public AlumnoEstudioPeriodoAcademico getPeriodoAcadAlumnoEstudioByPeriodo
            (AlumnoEstudio alumnoEstudio, PeriodoAcademico periodo) {
        return alumnoEstudioDAO.getPeriodoAcadAlumnoEstudioByPeriodo(alumnoEstudio, periodo);
    }
    
    public Alumno getById(int id){
        return alumnoDAO.getById(id);
    }
    
    public Map<String,Object> infoAlumnoEstudio(AlumnoEstudio alumnoEstudio){
        return webServiceArgusDAO.infoAlumnoEstudio(alumnoEstudio.getCampus()
                , usuarioService.getInfoUsuario().getCarne(), alumnoEstudio.getEdicionestudio().getId());
    }
    
    public Map<String,Object> obtenerHistorialIdiomas(Campus campus){
        return webServiceArgusDAO.infoAlumnoHistorialIdiomas(campus, usuarioService.getInfoUsuario().getCarne());
    }
    
    public boolean showAvance(int idCampus, int idCentro, int idTipoEstudio){
        return alumnoDAO.showAvance(idCampus, idCentro, idTipoEstudio);
    }
}
