package com.udep.siga.service;

import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.AlumnoHistorial;
import com.udep.siga.bean.AsignaturaHistorial;
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
import com.udep.siga.bean.enumeration.CLCCategoria;
import com.udep.siga.bean.enumeration.CLCTipoVoluntariado;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.udep.siga.dao.CLCCategoriaDAO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Administrador
 */
@Service("clcService")
public class CLCService {

    @Autowired
    private CLCCategoriaDAO clcCategoriaDAO;

    public List<CLCCategoriaAlumno> getCategoriasByAlumnoList(AlumnoEstudio alumnoestudio) {

        List<CLCCategoriaAlumno> list = new ArrayList<CLCCategoriaAlumno>(0);
        if (alumnoestudio.getEdicionestudio().getId() == 4
                || alumnoestudio.getEdicionestudio().getId() == 5
                || alumnoestudio.getEdicionestudio().getId() == 6
                || alumnoestudio.getEdicionestudio().getId() == 7) {
            CLCCategoriaAlumno clcCategoriaAlumno;
            //ACTIVIDAD_ARTISTICA_DESTACADA        
            clcCategoriaAlumno = clcCategoriaDAO
                    .getCategoriaCLC(CLCCategoria.ACTIVIDAD_ARTISTICA_DESTACADA.getId());
            clcCategoriaAlumno.setId(CLCCategoria.ACTIVIDAD_ARTISTICA_DESTACADA.getId());
            clcCategoriaAlumno.setTotalHoras(clcCategoriaDAO
                    .getCLCObtenidoByAlumnoCateg(alumnoestudio,
                    CLCCategoria.ACTIVIDAD_ARTISTICA_DESTACADA.getTabla()));
            if (clcCategoriaAlumno.getTotalHoras() > clcCategoriaAlumno.getMaxValor()) {
                clcCategoriaAlumno.setClcObtenidos(clcCategoriaAlumno.getMaxValor());
            } else {
                clcCategoriaAlumno.setClcObtenidos((int) clcCategoriaAlumno.getTotalHoras());
            }
            list.add(clcCategoriaAlumno);

            //ACTIVIDAD_INVESTIGACION
            clcCategoriaAlumno = clcCategoriaDAO
                    .getCategoriaCLC(CLCCategoria.ACTIVIDAD_INVESTIGACION.getId());
            clcCategoriaAlumno.setId(CLCCategoria.ACTIVIDAD_INVESTIGACION.getId());
            clcCategoriaAlumno.setTotalHoras(clcCategoriaDAO
                    .getCLCObtenidoByAlumnoCateg(alumnoestudio,
                    CLCCategoria.ACTIVIDAD_INVESTIGACION.getTabla()));
            if (clcCategoriaAlumno.getTotalHoras() > clcCategoriaAlumno.getMaxValor()) {
                clcCategoriaAlumno.setClcObtenidos(clcCategoriaAlumno.getMaxValor());
            } else {
                clcCategoriaAlumno.setClcObtenidos((int) clcCategoriaAlumno.getTotalHoras());
            }
            list.add(clcCategoriaAlumno);

            //PROYECTO_SOCIAL_ASISTENCIAL
            clcCategoriaAlumno = clcCategoriaDAO
                    .getCategoriaCLC(CLCCategoria.PROYECTO_SOCIAL_ASISTENCIAL.getId());
            clcCategoriaAlumno.setId(CLCCategoria.PROYECTO_SOCIAL_ASISTENCIAL.getId());
            clcCategoriaAlumno.setTotalHoras(clcCategoriaDAO
                    .getCLCObtenidoByAlumnoProySocial(alumnoestudio,
                    CLCTipoVoluntariado.VOLUNTARIADO_ASISTENCIAL.getId()));
            if (clcCategoriaAlumno.getTotalHoras() > clcCategoriaAlumno.getMaxValor()) {
                clcCategoriaAlumno.setClcObtenidos(clcCategoriaAlumno.getMaxValor());
            } else {
                clcCategoriaAlumno.setClcObtenidos((int) clcCategoriaAlumno.getTotalHoras());
            }
            list.add(clcCategoriaAlumno);

            //PROYECTO_SOCIAL_PROFESIONAL
            clcCategoriaAlumno = clcCategoriaDAO
                    .getCategoriaCLC(CLCCategoria.PROYECTO_SOCIAL_PROFESIONAL.getId());
            clcCategoriaAlumno.setId(CLCCategoria.PROYECTO_SOCIAL_PROFESIONAL.getId());
            clcCategoriaAlumno.setTotalHoras(clcCategoriaDAO
                    .getCLCObtenidoByAlumnoProySocial(alumnoestudio,
                    CLCTipoVoluntariado.VOLUNTARIADO_PROFESIONAL.getId()));
            if (clcCategoriaAlumno.getTotalHoras() > clcCategoriaAlumno.getMaxValor()) {
                clcCategoriaAlumno.setClcObtenidos(clcCategoriaAlumno.getMaxValor());
            } else {
                clcCategoriaAlumno.setClcObtenidos((int) clcCategoriaAlumno.getTotalHoras());
            }
            list.add(clcCategoriaAlumno);

            //ACTIVIDAD_DEPORTIVA_DESTACADA
            clcCategoriaAlumno = clcCategoriaDAO
                    .getCategoriaCLC(CLCCategoria.ACTIVIDAD_DEPORTIVA_DESTACADA.getId());
            clcCategoriaAlumno.setId(CLCCategoria.ACTIVIDAD_DEPORTIVA_DESTACADA.getId());
            clcCategoriaAlumno.setClcObtenidos(clcCategoriaDAO
                    .getCLCObtenidoByAlumnoDeportivaDest(alumnoestudio, clcCategoriaAlumno.getMaxValor()));
            list.add(clcCategoriaAlumno);

            //ASIGNATURA_DE_OTROS_PLANES
            clcCategoriaAlumno = new CLCCategoriaAlumno();
            clcCategoriaAlumno.setNombre("Asignaturas de otros planes de estudio (*)");
            clcCategoriaAlumno.setMaxValor(4);
            clcCategoriaAlumno.setModalidad("CARRERA");
            clcCategoriaAlumno.setTotalHoras(clcCategoriaDAO
                    .getCLCObtenidoByOtrosPlanesDeEstudio(alumnoestudio));
            clcCategoriaAlumno.setClcObtenidos((int)clcCategoriaAlumno.getTotalHoras() > 4 ? 4 : (int)clcCategoriaAlumno.getTotalHoras());
            list.add(clcCategoriaAlumno);

            //ACTIVIDAD_EXTRACURRICULAR_CONVENIO
            clcCategoriaAlumno = clcCategoriaDAO
                    .getCategoriaCLC(CLCCategoria.ACTIVIDAD_EXTRACURRICULAR_CONVENIO.getId());
            clcCategoriaAlumno.setId(CLCCategoria.ACTIVIDAD_EXTRACURRICULAR_CONVENIO.getId());
            clcCategoriaAlumno.setTotalHoras(clcCategoriaDAO
                    .getCLCObtenidoByAlumnoCateg(alumnoestudio,
                    CLCCategoria.ACTIVIDAD_EXTRACURRICULAR_CONVENIO.getTabla()));
            if (clcCategoriaAlumno.getTotalHoras() > clcCategoriaAlumno.getMaxValor()) {
                clcCategoriaAlumno.setClcObtenidos(clcCategoriaAlumno.getMaxValor());
            } else {
                clcCategoriaAlumno.setClcObtenidos((int) clcCategoriaAlumno.getTotalHoras());
            }
            list.add(clcCategoriaAlumno);

            //ASIGNATURA_PROGRAMA_DISNEY
            clcCategoriaAlumno = clcCategoriaDAO
                    .getCategoriaCLC(CLCCategoria.ASIGNATURA_PROGRAMA_DISNEY.getId());
            clcCategoriaAlumno.setId(CLCCategoria.ASIGNATURA_PROGRAMA_DISNEY.getId());
            clcCategoriaAlumno.setTotalHoras(clcCategoriaDAO
                    .getCLCObtenidoByAlumnoCateg(alumnoestudio,
                    CLCCategoria.ASIGNATURA_PROGRAMA_DISNEY.getTabla()));
              if (clcCategoriaAlumno.getTotalHoras() > clcCategoriaAlumno.getMaxValor()) {
                clcCategoriaAlumno.setClcObtenidos(clcCategoriaAlumno.getMaxValor());
            } else {
                clcCategoriaAlumno.setClcObtenidos((int) clcCategoriaAlumno.getTotalHoras());
            }
            //clcCategoriaAlumno.setClcObtenidos((int) clcCategoriaAlumno.getTotalHoras());
            list.add(clcCategoriaAlumno);

            //ASIGNATURA_INTERCAMBIO_ESTUDIANTIL
            //agregamos asignaturas 
             //ASIGNATURA_DE_OTROS_PLANES
            CLCCategoriaAlumno clcCategoriaAlumno2;
            clcCategoriaAlumno2 = new CLCCategoriaAlumno();
            clcCategoriaAlumno2.setNombre("Asignaturas de otros planes de estudio (*)");
            clcCategoriaAlumno2.setMaxValor(4);
            clcCategoriaAlumno2.setModalidad("CARRERA");
            clcCategoriaAlumno2.setTotalHoras(clcCategoriaDAO
                    .getCLCObtenidoByOtrosPlanesDeEstudio(alumnoestudio));
            clcCategoriaAlumno2.setClcObtenidos((int)clcCategoriaAlumno2.getTotalHoras() > 4 ? 4 : (int)clcCategoriaAlumno2.getTotalHoras());
           
            //
            clcCategoriaAlumno = clcCategoriaDAO
                    .getCategoriaCLC(CLCCategoria.ASIGNATURA_INTERCAMBIO_ESTUDIANTIL.getId());
            clcCategoriaAlumno.setId(CLCCategoria.ASIGNATURA_INTERCAMBIO_ESTUDIANTIL.getId());
            clcCategoriaAlumno.setClcObtenidos(clcCategoriaDAO
                    .getCLCObtenidoByAlumnoIntercambioEst(alumnoestudio));
            
            clcCategoriaAlumno.setClcObtenidos(clcCategoriaAlumno.getClcObtenidos()+clcCategoriaAlumno2.getClcObtenidos());
           
             if (clcCategoriaAlumno.getClcObtenidos()> clcCategoriaAlumno.getMaxValor()) {
                clcCategoriaAlumno.setClcObtenidos(clcCategoriaAlumno.getMaxValor());
            }
            list.add(clcCategoriaAlumno);

            //HORAS_CAPACITACION_PROFESIONAL
            clcCategoriaAlumno = clcCategoriaDAO
                    .getCategoriaCLC(CLCCategoria.HORAS_CAPACITACION_PROFESIONAL.getId());
            clcCategoriaAlumno.setId(CLCCategoria.HORAS_CAPACITACION_PROFESIONAL.getId());
            clcCategoriaAlumno.setTotalHoras(clcCategoriaDAO
                    .getCLCObtenidoByAlumnoCateg(alumnoestudio,
                    CLCCategoria.HORAS_CAPACITACION_PROFESIONAL.getTabla()));
            clcCategoriaAlumno.setClcObtenidos((int) clcCategoriaAlumno.getTotalHoras());
             if (clcCategoriaAlumno.getClcObtenidos()> clcCategoriaAlumno.getMaxValor()) {
                clcCategoriaAlumno.setClcObtenidos(clcCategoriaAlumno.getMaxValor());
            }
            list.add(clcCategoriaAlumno);

            //HORAS_ESTUDIO_DIRIGIDO
            clcCategoriaAlumno = clcCategoriaDAO
                    .getCategoriaCLC(CLCCategoria.HORAS_ESTUDIO_DIRIGIDO.getId());
            clcCategoriaAlumno.setId(CLCCategoria.HORAS_ESTUDIO_DIRIGIDO.getId());
            clcCategoriaAlumno.setTotalHoras(clcCategoriaDAO
                    .getCLCObtenidoByAlumnoCateg(alumnoestudio,
                    CLCCategoria.HORAS_ESTUDIO_DIRIGIDO.getTabla()));
            if (clcCategoriaAlumno.getTotalHoras() > clcCategoriaAlumno.getMaxValor()) {
                clcCategoriaAlumno.setClcObtenidos(clcCategoriaAlumno.getMaxValor());
            } else {
                clcCategoriaAlumno.setClcObtenidos((int) clcCategoriaAlumno.getTotalHoras());
            }
            list.add(clcCategoriaAlumno);

            //IDIOMAS
            clcCategoriaAlumno = clcCategoriaDAO
                    .getCategoriaCLC(CLCCategoria.NIVEL_BASICO_TERCER_IDIOMA.getId());
            int valor = clcCategoriaAlumno.getMaxValor();
            clcCategoriaAlumno = clcCategoriaDAO
                    .getCategoriaCLC(CLCCategoria.INTERMEDIO_TERCER_IDIOMA.getId());
            valor += clcCategoriaAlumno.getMaxValor();
            clcCategoriaAlumno = clcCategoriaDAO
                    .getCategoriaCLC(CLCCategoria.NIVEL_BASICO_CUARTO_IDIOMA.getId());
            valor += clcCategoriaAlumno.getMaxValor();
            clcCategoriaAlumno.setNombre("Tercer o Cuarto Idioma");
            clcCategoriaAlumno.setMaxValor(valor);
            clcCategoriaAlumno.setClcObtenidos(clcCategoriaDAO
                    .getCLCObtenidoByIdiomas(alumnoestudio, clcCategoriaAlumno.getMaxValor()));
            list.add(clcCategoriaAlumno);

            //HORAS_PRACTICA_PREPROFESIONALES
            clcCategoriaAlumno = clcCategoriaDAO
                    .getCategoriaCLC(CLCCategoria.HORAS_PRACTICA_PREPROFESIONALES.getId());
            clcCategoriaAlumno.setId(CLCCategoria.HORAS_PRACTICA_PREPROFESIONALES.getId());
            clcCategoriaAlumno.setTotalHoras(clcCategoriaDAO
                    .getCLCObtenidoByAlumnoCateg(alumnoestudio,
                    CLCCategoria.HORAS_PRACTICA_PREPROFESIONALES.getTabla()));
            if (clcCategoriaAlumno.getTotalHoras() > clcCategoriaAlumno.getMaxValor()) {
                clcCategoriaAlumno.setClcObtenidos(clcCategoriaAlumno.getMaxValor());
            } else {
                clcCategoriaAlumno.setClcObtenidos((int) clcCategoriaAlumno.getTotalHoras());
            }
            list.add(clcCategoriaAlumno);
        }

        return list;
    }

    //ACTIVIDAD_ARTISTICA_DESTACADA
    public List<CLCCategoriaActividad> getCategActividadArtisticaDestacada(int idAlumno, int idEdicionesEstudio) {
        return clcCategoriaDAO.getCategActividadArtisticaDestacada(idAlumno, idEdicionesEstudio);
    }

    //ACTIVIDAD_INVESTIGACION
    public List<CLCCategActividadInvestigacion> getCategActividadInvestigacion(int idAlumno, int idEdicionesEstudio) {
        return clcCategoriaDAO.getCategActividadInvestigacion(idAlumno, idEdicionesEstudio);
    }

    //PROYECTO_SOCIAL_ASISTENCIAL
    public List<CLCCategActividadProyectoSocial> getCategActividadProyeSocialAsist(int idAlumno, int idEdicionesEstudio) {
        return clcCategoriaDAO.getCategActividadProyeSocial(idAlumno, idEdicionesEstudio,
                CLCTipoVoluntariado.VOLUNTARIADO_ASISTENCIAL.getId());
    }

    //PROYECTO_SOCIAL_PROFESIONAL
    public List<CLCCategActividadProyectoSocial> getCategActividadProyeSocialProf(int idAlumno, int idEdicionesEstudio) {
        return clcCategoriaDAO.getCategActividadProyeSocial(idAlumno, idEdicionesEstudio,
                CLCTipoVoluntariado.VOLUNTARIADO_PROFESIONAL.getId());
    }

    //ACTIVIDAD_DEPORTIVA_DESTACADA
    public List<CLCCategActividadDeportivaDest> getCategActividadDeportivaDest(int idAlumno, int idEdicionesEstudio) {
        return clcCategoriaDAO.getCategActividadDeportivaDest(idAlumno, idEdicionesEstudio);
    }

    //ASIGNATURA_DE_OTROS_PLANES
    public List<AsignaturaHistorial> getClcOtrosPlanesDeEstudio(int idAlumno, int idEdicionesEstudio) {
        return clcCategoriaDAO.getClcOtrosPlanesDeEstudio(idAlumno, idEdicionesEstudio);
    }

    //ACTIVIDAD_EXTRACURRICULAR_CONVENIO
    public List<CLCCategoriaActividad> getCategActividadExtraConvenio(int idAlumno, int idEdicionesEstudio) {
        return clcCategoriaDAO.getCategActividadExtraConvenio(idAlumno, idEdicionesEstudio);
    }

    //ASIGNATURA_PROGRAMA_DISNEY
    public List<CLCCategActividadDisney> getCategActividadDisney(int idAlumno, int idEdicionesEstudio) {
        return clcCategoriaDAO.getCategActividadDisney(idAlumno, idEdicionesEstudio);
    }

    //ASIGNATURA_INTERCAMBIO_ESTUDIANTIL
    public List<CLCCategAsigIntercambioEstud> getCategAsigIntercambioEstud(int idAlumno, int idEdicionesEstudio) {
        return clcCategoriaDAO.getCategAsigIntercambioEstud(idAlumno, idEdicionesEstudio);
    }

    //HORAS_CAPACITACION_PROFESIONAL
    public List<CLCCategCapacitacionProfesional> getCategCapacitacionProfesional(int idAlumno, int idEdicionesEstudio) {
        return clcCategoriaDAO.getCategCapacitacionProfesional(idAlumno, idEdicionesEstudio);
    }

    //HORAS_ESTUDIO_DIRIGIDO
    public List<CLCCategEstudioDirigido> getCategEstudioDirigido(int idAlumno, int idEdicionesEstudio) {
        return clcCategoriaDAO.getCategEstudioDirigido(idAlumno, idEdicionesEstudio);
    }

    //IDIOMAS
    public List<CLCCategoriaIdioma> getCategoriaIdioma(int idAlumno, int idEdicionesEstudio) {
        return clcCategoriaDAO.getCategoriaIdioma(idAlumno, idEdicionesEstudio);
    }

    //HORAS_PRACTICA_PREPROFESIONALES
    public List<CLCCategPractPreProfesional> getCategHorasPractProfesionales(int idAlumno, int idEdicionesEstudio) {
        return clcCategoriaDAO.getCategHorasPractProfesionales(idAlumno, idEdicionesEstudio);
    }
}
