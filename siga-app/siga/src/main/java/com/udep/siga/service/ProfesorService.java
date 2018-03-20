package com.udep.siga.service;

import com.udep.siga.bean.ArchivoInvestigacion;
import com.udep.siga.bean.Articulo;
import com.udep.siga.bean.ArticuloCV;
import com.udep.siga.bean.AsignaturaDictada;
import com.udep.siga.bean.AsociacionProfesionalCV;
import com.udep.siga.bean.CapacitacionCV;
import com.udep.siga.bean.CargoDirectivoCV;
import com.udep.siga.bean.ConsultoriaCv;
import com.udep.siga.bean.DocenciaCV;
import com.udep.siga.bean.EmpresaCV;
import com.udep.siga.bean.EstanciaInvestigacionCV;
import com.udep.siga.bean.EstudioCV;
import com.udep.siga.bean.Evento;
import com.udep.siga.bean.HorarioAsesoria;
import com.udep.siga.bean.IdiomasCV;
import com.udep.siga.bean.Interaccion;
import com.udep.siga.bean.InvestigacionGenerica;
import com.udep.siga.bean.Libro;
import com.udep.siga.bean.LineaInvestigacion;
import com.udep.siga.bean.MeritoCV;
import com.udep.siga.bean.OtroTrabajoCV;
import com.udep.siga.bean.PatenteCV;
import com.udep.siga.bean.PremioCV;
import com.udep.siga.bean.Profesor;
import com.udep.siga.bean.ProyectoCV;
import com.udep.siga.bean.RedAcademicaCV;
import com.udep.siga.bean.ReunionEventoCV;
import com.udep.siga.bean.Tesis;
import com.udep.siga.bean.TrabajoInvestigacion;
import com.udep.siga.bean.enumeration.RestriccionPublicacion;
import com.udep.siga.bean.enumeration.TipoGrado;
import com.udep.siga.bean.enumeration.TipoTesis;
import com.udep.siga.dao.AsignaturaDictadaDAO;
import com.udep.siga.dao.CVDAO;
import com.udep.siga.dao.InvestigacionDAO;
import com.udep.siga.dao.ProfesorDAO;
import com.udep.siga.dao.UtilDAO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Wilfredo Atoche
 */
@Service("profesorService")
public class ProfesorService {

    @Autowired
    private ProfesorDAO profesorDAO;
    @Autowired
    private InvestigacionDAO investigacionDAO;
    @Autowired
    private CVDAO cvDAO;
    @Autowired
    private AsignaturaDictadaDAO asignaturaDictadaDAO;
    @Autowired
    private UtilDAO utilDAO;

    public Map<String, Object> getInfoAcademico(int idProfesor) {
        return profesorDAO.getInfoAcademico(idProfesor);
    }

    public List<AsignaturaDictada> getHorarioList(int idProfesor) {
        List<AsignaturaDictada> list = asignaturaDictadaDAO.getAsignaturaDictadaProfesorList(idProfesor);
        for (AsignaturaDictada asignaturaDictada : list) {
            asignaturaDictada.setHorarioList(asignaturaDictadaDAO
                    .getHorarioByAsignaturaSeccion(
                    asignaturaDictada.getId(), asignaturaDictada.getAsignaturaSeccion().getIdSeccion()));
            asignaturaDictada.setPracticaProgramadaList(asignaturaDictadaDAO
                    .getPracticaProgramadaByAsignaturaSeccion(
                    asignaturaDictada.getId(), asignaturaDictada.getAsignaturaSeccion().getIdSeccion()));
        }

        return list;
    }

    public List<HorarioAsesoria> getHorarioAsesoriaList(int idProfesor) {
        return profesorDAO.getHorarioAsesoriaList(idProfesor);
    }

    /*
     * Asesorias del Alumno
     */
    public List<Interaccion> getAsesoriaInteraccionList(int idAlumno, int idAsesor) {
        return profesorDAO.getAsesoriaInteraccionList(idAlumno, idAsesor);
    }

    public List<Map<String, Object>> getAsesoriaFuturaList(int idAlumno, int idAsesor) {
        List<Date> fechas = profesorDAO.getAsesoriaFuturaList(idAlumno, idAsesor);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> item;
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd", new Locale("ES"));
        SimpleDateFormat dateFormatMes = new SimpleDateFormat("'@' MMMM '@' yyyy", new Locale("ES"));

        for (Date fecha : fechas) {
            item = new HashMap<String, Object>();
            item.put("diaNombre", WordUtils.capitalize(dateFormat.format(fecha)));
            item.put("mesNombre", WordUtils.capitalize(dateFormatMes.format(fecha)).replaceAll("@", "de"));
            list.add(item);
        }
        return list;
    }

    /*
     * Buscar Profesores
     */
    public List<Profesor> findByNombreApellidos(String nombre, String apePaterno, String apeMaterno) {
        if (nombre.isEmpty() && apePaterno.isEmpty() && apeMaterno.isEmpty()) {
            return new ArrayList<Profesor>();
        }

        List<Profesor> list = profesorDAO.findByNombreApellidos(nombre, apePaterno, apeMaterno);
        Map<String, Object> infoAcademica;
        for (Profesor profesor : list) {
            infoAcademica = this.getInfoAcademico(profesor.getId());
            profesor.setCentroAcademicoNombre("");
            profesor.setDepartamentoNombre("");
            if (infoAcademica.get("departamento") != null) {
                profesor.setDepartamentoNombre(infoAcademica.get("departamento").toString());
            }
            if (infoAcademica.get("centroacademico") != null) {
                profesor.setCentroAcademicoNombre(infoAcademica.get("centroacademico").toString());
            }
        }

        return list;
    }

    public List<TrabajoInvestigacion> getTrabajosInvestigacionByProfesor(int idProfesor) {
        return profesorDAO.getTrabajosInvestigacionByProfesor(idProfesor);
    }

    public List<Articulo> getArtByProfesor(int idProfesor) {
        return profesorDAO.getArtByProfesor(idProfesor);
    }

    public List<Libro> getLibrosByProfesor(int idProfesor) {
        return profesorDAO.getLibrosByProfesor(idProfesor);
    }

    public List<Tesis> getTesisByProfesor(int idProfesor) {
        return profesorDAO.getTesisByProfesor(idProfesor);
    }

    public TrabajoInvestigacion getDetalleTrabajosInvestigacion(int idTrabajo) {
        return profesorDAO.getDetalleTrabajosInvestigacion(idTrabajo);
    }

    public Articulo getArticulo(int idArticulo) {
        Articulo articulo = investigacionDAO.getArticulo(idArticulo);

        if (articulo.getInvestigacionGenerica() != null) {
            articulo.getInvestigacionGenerica().setRestriciones(this.getRestriciones(articulo.getInvestigacionGenerica()));
        }
        //modificar fecha - nuevo formato
        articulo.setFecha(utilDAO.getNewFormatFecha(articulo.getFecha()));
        if (articulo.getInvestigacionGenerica() != null
                && articulo.getInvestigacionGenerica().getEvento() != null) {
            articulo.getInvestigacionGenerica().getEvento().setFecha(utilDAO
                    .getNewFormatFecha(articulo.getInvestigacionGenerica().getEvento().getFecha()));
        }
        return articulo;
    }

    public Tesis getTesis(int idTesis) {
        Tesis tesis = investigacionDAO.getTesis(idTesis);

        if (tesis.getInvestigacionGenerica() != null) {
            tesis.getInvestigacionGenerica().setRestriciones(this.getRestriciones(tesis.getInvestigacionGenerica()));
        }
        //modificar fecha - nuevo formato
        tesis.setFechaInicio(utilDAO.getNewFormatFecha(tesis.getFechaInicio()));
        tesis.setFechaFin(utilDAO.getNewFormatFecha(tesis.getFechaFin()));
        tesis.setFechaSustentacion(utilDAO.getNewFormatFecha(tesis.getFechaSustentacion()));

        return tesis;
    }

    public Libro getLibro(int idLibro) {
        Libro libro = investigacionDAO.getLibro(idLibro);

        if (libro.getInvestigacionGenerica() != null) {
            libro.getInvestigacionGenerica().setRestriciones(this.getRestriciones(libro.getInvestigacionGenerica()));
        }
        //modificar fecha - nuevo formato
        libro.setFechaPublica(utilDAO.getNewFormatFecha(libro.getFechaPublica()));

        return libro;
    }

    public List<ArchivoInvestigacion> getArchivoInvestigacionList(int idTrabajo) {
        return investigacionDAO.getArchivoInvestigacionList(idTrabajo);
    }

    public List<String> getRestriciones(InvestigacionGenerica investigacion) {
        List<String> restriciones = new ArrayList<String>(0);
        if (investigacion != null && investigacion.getRestriccionPublicacion() != null) {
            if (investigacion.getRestriccionPublicacion().getId()
                    == RestriccionPublicacion.EMBARGO_PERSONAL.getId()) {
                restriciones.add("El o los archivo(s) cuenta con embargo personal.");
                if (investigacion.isEmbargoPersonal()) {
                    restriciones.add("No publicar los archivos de la investigación");
                } else {
                    restriciones.add("Publicar parcialmente (5 primeras hojas)");
                }
            }
            if (investigacion.getRestriccionPublicacion()
                    .getId() == RestriccionPublicacion.EMBARGO_COMERCIAL.getId()) {
                String dato = "" + investigacion.getVencEmbargoComerc();
                if (dato == null) {
                    dato = "";
                }
                restriciones.add("Los documentos de esta investigación cuentan "
                        + "con embargo comercial " + dato);
            }
        }
        if (restriciones.isEmpty()) {
            restriciones = null;
        }
        return restriciones;
    }

    //CV
    public List<EstudioCV> getEstudiosCVPregrado(int idProfesor) {
        return cvDAO.getEstudiosCV(idProfesor, TipoGrado.LICENCIADO.getId());
    }

    public List<EstudioCV> getEstudiosCVPosgrado(int idProfesor) {
        return cvDAO.getEstudiosCV(idProfesor, TipoGrado.MASTER.getId());
    }

    public List<CapacitacionCV> getCapacitacionesCV(int idProfesor) {
        return cvDAO.getCapacitacionesCV(idProfesor);
    }

    public List<LineaInvestigacion> getLineaInvestigacionByProfesor(int idProfesor) {
        return cvDAO.getLineaInvestigacionByProfesor(idProfesor);
    }

    public List<TrabajoInvestigacion> getWorkingPaper(int idProfesor) {
        return cvDAO.getWorkingPaper(idProfesor);
    }

    public List<Tesis> getTesisPregrado(int idProfesor) {
        List<Tesis> list = cvDAO.getTesisByProfesorYTipo(idProfesor, TipoTesis.PREGRADO.getId());
        if (list != null) {
            for (Tesis t : list) {
                if (t.getFechaSustentacion() != null) {
                    t.setAnio(utilDAO.getAnio(t.getFechaSustentacion()));
                }
                t.setInvestigacionGenerica(investigacionDAO.getInvestigacionGenerica(t.getInvestigacionGenerica().getId()));
            }
        }
        return list;
    }

    public List<Tesis> getTesisPostgrado(int idProfesor) {
        List<Tesis> list = cvDAO.getTesisByProfesorYTipo(idProfesor, TipoTesis.MAESTRIA.getId());
        if (list != null) {
            for (Tesis t : list) {
                if (t.getFechaSustentacion() != null) {
                    t.setAnio(utilDAO.getAnio(t.getFechaSustentacion()));
                }
                t.setInvestigacionGenerica(investigacionDAO.getInvestigacionGenerica(t.getInvestigacionGenerica().getId()));
            }
        }
        return list;
    }

    public List<EstanciaInvestigacionCV> getEstanciaInvestigaByProfesor(int idProfesor) {
        return cvDAO.getEstanciaInvestigaByProfesor(idProfesor);
    }

    public List<ProyectoCV> getProyectosByProfesor(int idProfesor) {
        return cvDAO.getProyectosByProfesor(idProfesor);
    }

    public List<ConsultoriaCv> getConsultoriasByProfesor(int idProfesor) {
        return cvDAO.getConsultoriasByProfesor(idProfesor);
    }

    public List<Evento> getEventoByProfesor(int idProfesor) {
        return cvDAO.getEventoByProfesor(idProfesor);
    }

    public List<ReunionEventoCV> getReunionEventoByProfesor(int idProfesor) {
        return cvDAO.getReunionEventoByProfesor(idProfesor);
    }
    
    public List<Libro> getLibrosList(int idProfesor){
        List<Libro> list = cvDAO.getLibrosByProfesorYTipo(idProfesor, false);
        if (list != null) {
            for (Libro l : list) {
                l.setFechaPublica(utilDAO.getAnio(l.getFechaPublica()));
                l.setInvestigacionGenerica(investigacionDAO.getInvestigacionGenerica(l.getInvestigacionGenerica().getId()));
            }
        }
        return list;
    }
    
    public List<Libro> getLibrosColectivosList(int idProfesor){
        List<Libro> list = cvDAO.getLibrosByProfesorYTipo(idProfesor, true);
        if (list != null) {
            for (Libro l : list) {   
                l.setFechaPublica(utilDAO.getAnio(l.getFechaPublica()));
                l.setCapitulos(investigacionDAO.getCapituloLibro(l.getId()));
                l.setInvestigacionGenerica(investigacionDAO.getInvestigacionGenerica(l.getInvestigacionGenerica().getId()));
            }
        }
        return list;
    }
    
    public List<Articulo> getArticulosIndexadosList(int idProfesor){
        List<Articulo> list = cvDAO.getArticulosByProfesorYMedio(idProfesor, "Revista indexada");
        if (list != null) {
            for (Articulo l : list) {
                l.setFecha(utilDAO.getAnio(l.getFecha()));
                l.setInvestigacionGenerica(investigacionDAO.getInvestigacionGenerica(l.getInvestigacionGenerica().getId()));
            }
        }
        return list;
    }
    
    public List<Articulo> getArticulosNoIndexadosList(int idProfesor){
        List<Articulo> list = cvDAO.getArticulosByProfesorYMedio(idProfesor, "No indexada");
        if (list != null) {
            for (Articulo l : list) {
                l.setFecha(utilDAO.getAnio(l.getFecha()));
                l.setInvestigacionGenerica(investigacionDAO.getInvestigacionGenerica(l.getInvestigacionGenerica().getId()));
            }
        }
        return list;
    }
    
    public List<Articulo> getArticulosByProfesorConActa(int idProfesor){
        List<Articulo> list = cvDAO.getArticulosByProfesorConActa(idProfesor);
        if (list != null) {
            for (Articulo l : list) {
                l.setFecha(utilDAO.getAnio(l.getFecha()));
                l.setInvestigacionGenerica(investigacionDAO.getInvestigacionGenerica(l.getInvestigacionGenerica().getId()));
            }
        }
        return list;
    }
    
    public List<ArticuloCV> getArticulosCVByProfesor(int idProfesor){
        return cvDAO.getArticulosCVByProfesor(idProfesor);
    } 
    
    public List<DocenciaCV> getDocenciaCVByProfesor(int idProfesor){
        List<DocenciaCV> list = cvDAO.getDocenciaCVByProfesor(idProfesor);
        if (list != null) {            
            for (DocenciaCV d : list) {
                d.setCursos(cvDAO.getCursoCVList(d.getId()));
            }
        }
        return list;
    }

    public List<OtroTrabajoCV> getOtrosTrabajosList(int idProfesor) {
        List<OtroTrabajoCV> list = cvDAO.getOtrosTrabajosList(idProfesor);
        if (list != null) {
            for (OtroTrabajoCV a : list) {
                a.setDesde(utilDAO.getNewFormatFecha
                        (a.getDiaInicio(), a.getMesInicio(), a.getAnioInicio(),true));
                a.setHasta(utilDAO.getNewFormatFecha
                        (a.getDiaFin(), a.getMesFin(), a.getAnioFin(),true));
            }
        }
        
        return list;
    }

    public List<CargoDirectivoCV> getCargoDirectivoList(int idProfesor) {
        List<CargoDirectivoCV> list = cvDAO.getCargoDirectivoList(idProfesor);
        if (list != null) {
            for (CargoDirectivoCV a : list) {
                a.setDesde(utilDAO.getNewFormatFecha
                        (a.getDiaInicio(), a.getMesInicio(), a.getAnioInicio(),true));
                a.setHasta(utilDAO.getNewFormatFecha
                        (a.getDiaFin(), a.getMesFin(), a.getAnioFin(),true));
            }
        }
        return list;
    }

    public List<EmpresaCV> getOtrosCargoList(int idProfesor) {
        List<EmpresaCV> list = cvDAO.getOtrosCargoList(idProfesor);
        if (list != null) {
            for (EmpresaCV a : list) {
                a.setDesde(utilDAO.getNewFormatFecha
                        (a.getDiaInicio(), a.getMesInicio(), a.getAnioInicio(),true));
                a.setHasta(utilDAO.getNewFormatFecha
                        (a.getDiaFin(), a.getMesFin(), a.getAnioFin(),true));
            }
        }
        return list;
    }

    public List<RedAcademicaCV> getRedesAcademicasList(int idProfesor) {
        return cvDAO.getRedesAcademicasList(idProfesor);
    }
    
    public List<AsociacionProfesionalCV> getAsociacionProfesionalList(int idProfesor) {
        List<AsociacionProfesionalCV> list = cvDAO.getAsociacionProfesionalList(idProfesor);
        if (list != null) {
            for (AsociacionProfesionalCV a : list) {
                a.setDesde(utilDAO.getNewFormatFecha
                        (a.getDiaInicio(), a.getMesInicio(), a.getAnioInicio(),true));
                a.setHasta(utilDAO.getNewFormatFecha
                        (a.getDiaFin(), a.getMesFin(), a.getAnioFin(),true));
            }
        }
        return list;
    }

    public List<IdiomasCV> getIdiomasList(int idProfesor) {
        return cvDAO.getIdiomasList(idProfesor, false);
    }

    public List<IdiomasCV> getIdiomasTitulosList(int idProfesor) {
        return cvDAO.getIdiomasList(idProfesor, true);
    }

    public List<PatenteCV> getPatenteList(int idProfesor) {
        return cvDAO.getPatenteList(idProfesor);
    }

    public List<PremioCV> getPremiosList(int idProfesor) {
        return cvDAO.getPremiosList(idProfesor);
    }

    public List<MeritoCV> getOtrosMeritosList(int idProfesor) {
        return cvDAO.getOtrosMeritosList(idProfesor);
    }
}
