package com.udep.siga.service;

import com.udep.siga.bean.Alumno;
import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.AsignaturaDictada;
import com.udep.siga.bean.Asistencia;
import com.udep.siga.bean.EstrategiaSilabo;
import com.udep.siga.bean.Evaluacion;
import com.udep.siga.bean.HorarioEvento;
import com.udep.siga.bean.Material;
import com.udep.siga.bean.Persona;
import com.udep.siga.bean.PlanEstudio;
import com.udep.siga.bean.Silabo;
import com.udep.siga.bean.TipoEvaluacion;
import com.udep.siga.bean.UnidadSilabo;
import com.udep.siga.bean.enumeration.TipoMaterial;
import com.udep.siga.dao.AlumnoDAO;
import com.udep.siga.dao.AlumnoEstudioDAO;
import com.udep.siga.dao.AsignaturaDictadaDAO;
import com.udep.siga.dao.AsistenciaDAO;
import com.udep.siga.dao.AvisoDAO;
import com.udep.siga.dao.EvaluacionDAO;
import com.udep.siga.dao.MaterialDAO;
import com.udep.siga.dao.PeriodoAcademicoDAO;
import com.udep.siga.dao.SilaboDAO;
import com.udep.siga.util.Util;
import java.text.SimpleDateFormat;
import com.udep.siga.bean.Nota;
import com.udep.siga.bean.TemaSilabo;
import com.udep.siga.bean.enumeration.TipoBibliografiaSilabo;
import com.udep.siga.util.Rol;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Wilfredo Atoche
 */
@Service("asignaturaDictadaService")
public class AsignaturaDictadaService {

    @Autowired
    private CommonsService commonsService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private AsignaturaDictadaDAO asignaturaDictadaDAO;
    @Autowired
    private EvaluacionDAO evaluacionDAO;
    @Autowired
    private MaterialDAO materialDAO;
    @Autowired
    private AsistenciaDAO asistenciaDAO;
    @Autowired
    private AlumnoDAO alumnoDAO;
    @Autowired
    private SilaboDAO silaboDAO;
    @Autowired
    private AlumnoEstudioDAO alumnoEstudioDAO;
    @Autowired
    private AvisoDAO avisoDAO;
    @Autowired
    private PeriodoAcademicoDAO periodoAcademicoDAO;
    @Autowired
    private AlumnoEstudioService alumnoEstudioService;

    public List<AsignaturaDictada> getAsignaturaDictadaList(int idEdicionestudio,boolean moroso) {
        AlumnoEstudio alumnoEstudio = commonsService.getAlumnoEstudioSession(idEdicionestudio);

        List<AsignaturaDictada> list;

        if (Util.isUsuarioAuthority(Rol.ALUMNO_POSGRADO.getRol())) {
            list = asignaturaDictadaDAO.getAsignaturaDictadaPosgradoList(alumnoEstudio);
        } else {
            list = asignaturaDictadaDAO.getAsignaturaDictadaList(alumnoEstudio);
        }
        for (AsignaturaDictada asignatura : list) {
            
            //nota literal
            if (asignatura.getPromedio() != null) {
                if(moroso){
                    Nota n=new Nota(0);
                    asignatura.setPromedio(n);
                }
                
                if (asignatura.getPromedio().getId() != 0 && asignatura.getPromedio().isLiteral()) {
                    asignatura.getPromedio().setDescripcion(this.getNotaLiteral(asignatura.getId(),
                            asignatura.getPromedio()));
                }
            }
        }
        return list;
    }

    public List<TipoEvaluacion> getAsignaturaEvaluacionAlumnoList(int idAsignaturaDictada,
            int idSeccion,boolean moroso) {
        Alumno alumno = usuarioService.getInfoUsuario();

        List<TipoEvaluacion> list = evaluacionDAO.getTipoEvaluacionList(idAsignaturaDictada, idSeccion);

        for (TipoEvaluacion tipoEvaluacion : list) {
            List<Evaluacion> evaluacionList = evaluacionDAO.getEvaluacionList(
                    idAsignaturaDictada, idSeccion, tipoEvaluacion);

            for (Evaluacion evaluacion : evaluacionList) {
                evaluacion.setEvaluacionAlumno(evaluacionDAO
                        .getEvaluacionAlumno(evaluacion.getId(), alumno.getId()));
                //nota literal
                if (evaluacion.getEvaluacionAlumno() != null) {
                    if(moroso){
                        Nota n=new Nota(0);
                       evaluacion.getEvaluacionAlumno().setNota(n); 
                    }
                    if (evaluacion.getEvaluacionAlumno().getNota() != null) {
                        if (evaluacion.getEvaluacionAlumno().getNota().isLiteral()) {
                            evaluacion.getEvaluacionAlumno().getNota().setDescripcion(this.getNotaLiteral(evaluacion.getAsignaturaDictada().getId(),
                                    evaluacion.getEvaluacionAlumno().getNota()));
                        }
                    }
                }

                String notify = evaluacionDAO.notifyEvaluacion(evaluacion.getId());
                if (notify != null) {
                    evaluacion.setNotifyTooltip(String.format(notify, tipoEvaluacion.getNombre()));
                }
                evaluacion.setAsignaturaDictada(null);
                evaluacion.setIdRandom(usuarioService.addRefIndirecta(evaluacion.getId()));
                evaluacion.setId(0);
            }

            tipoEvaluacion.setEvaluacionList(evaluacionList);
        }

        return list;
    }

    public Nota getPromedioAsignaturaDictadaByAlumno(int idAsignaturaDictada,
            int idSeccion,boolean moroso) {
        if(moroso){
           Nota n=new Nota(0); 
           return n;
        }
        Alumno alumno = usuarioService.getInfoUsuario();
        Nota nota = asignaturaDictadaDAO.getPromedioAsignaturaDictadaByAlumno(idAsignaturaDictada, idSeccion, alumno.getId());
        
        //nota literal
        if (nota != null) {
            if (nota.isLiteral()) {
                nota.setDescripcion(this.getNotaLiteral(idAsignaturaDictada, nota));
            }
        }
        return nota;
    }

    public String getNotaLiteral(int idAsignaturaDictada, Nota nota) {
        return asignaturaDictadaDAO.getNotaLiteral(nota, idAsignaturaDictada);
    }

    public AsignaturaDictada getAsignaturaDictadaDetalle(int idAsignaturaDictada, int idSeccion) {
        AsignaturaDictada asignaturaDictada = asignaturaDictadaDAO
                .getAsignaturaDictada(idAsignaturaDictada, idSeccion);
        asignaturaDictada.setProfesorList(asignaturaDictadaDAO.findProfesores(asignaturaDictada));

        return asignaturaDictada;
    }

    public AsignaturaDictada getAsignaturaDictadaDetalleB(int idAsignaturaDictada, int idSeccion) {
        AsignaturaDictada asignaturaDictada = asignaturaDictadaDAO
                .getAsignaturaDictada(idAsignaturaDictada, idSeccion);
        asignaturaDictada.setProfesorList(asignaturaDictadaDAO.findProfesores(asignaturaDictada));
        asignaturaDictada.setPeriodoAcademico(periodoAcademicoDAO.getPA(
                asignaturaDictada.getPeriodoAcademico().getId()));
        asignaturaDictada.setEstudio(asignaturaDictadaDAO.getNombreDeEstudio(asignaturaDictada));
        return asignaturaDictada;
    }

    public int countAlumnosAsignaturaDictada(AsignaturaDictada asignaturaDictada) {
        return asignaturaDictadaDAO.countAlumnosAsignaturaDictada(asignaturaDictada);
    }

    public Map<String, Object> getFormulaPromedioAsignaturaDictada(int idAsignatura, int idSeccion) {

        int sumaPesos = 0;
        int numAnulables = 0;
        String formula = "", anulables = "";

        Map<String, Object> datos = asignaturaDictadaDAO
                .getTipoPromedioAsignaturaDictada(idAsignatura, idSeccion);

        if ((Boolean) datos.get("promedioEstandar")) {
            /*FORMULA / ANULABLE*/
            List<Evaluacion> evaluaciones = evaluacionDAO
                    .getEvaluacionesByAsignaturaDictada(idAsignatura, idSeccion);

            for (Evaluacion e : evaluaciones) {
                if ((!e.isInformativa() && e.getTipoEvaluacion().getId() != 7)
                        || e.getTipoEvaluacion().getId() == 2) {

                    if (formula.length() > 0) {
                        formula += " + ";
                    }

                    formula += "[" + e.getNombre() + "]x" + e.getPeso();
                    sumaPesos += e.getPeso();

                    if (e.getTipoEvaluacion().getId() != 2) {
                        if (e.isAnulable()) {
                            numAnulables += e.getPeso();
                            if (anulables.length() > 0) {
                                anulables += ", ";
                            }
                            anulables += "[" + e.getNombre() + "]";
                        }
                    }
                }
            }
            if (numAnulables >= 3) {
                formula += " - [NotaMenor]x1";
                sumaPesos--;
            } else {
                anulables = "";
            }

            formula = "( " + formula + " ) / " + sumaPesos;
            datos.put("formula", formula);
        }
        datos.put("anulables", anulables);

        return datos;

    }

    public List<Map<String, Object>> getTipoMaterialList(int idAsignatura, int idSeccion) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> item;
        for (TipoMaterial tipoMaterial : TipoMaterial.values()) {
            List<Material> materialList = materialDAO
                    .getMaterialByTipo(idAsignatura, idSeccion, tipoMaterial.getId());

            if (!materialList.isEmpty()) {
                List<Map<String, Object>> materialMapList = new ArrayList<Map<String, Object>>();
                for (Material material : materialList) {
                    item = new HashMap<String, Object>();
                    Persona persona = usuarioService.getPersonaById(material.getPublicador().getId());
                    int id = 0;
                    id = persona.getId();
                    Boolean esAsistente = false;
                    esAsistente = usuarioService.isAsistente(id, idAsignatura, idSeccion);
                    if (esAsistente == false) {
                        material.setPublicador(usuarioService.getPersonaById(material.getPublicador().getId()));

                        item.put("id", usuarioService.addRefIndirecta(material.getId()));
                        item.put("titulo", material.getTitulo());
                        item.put("descripcion", material.getDescripcion());
                        item.put("fecha", Util.dateToStringLong(material.getFecha()));
                        item.put("publicador", material.getPublicador().getApellidoPaterno()
                                + " " + material.getPublicador().getNombres());
                        item.put("tamanio", material.getTamanio());
                        item.put("toCentroCopiado", material.isToCentroCopiado());
                        materialMapList.add(item);
                    } else {
                        int idprofesor = usuarioService.getIdProfesor(id, idAsignatura, idSeccion);
                        material.setPublicador(usuarioService.getPersonaById(idprofesor));

                        item.put("id", usuarioService.addRefIndirecta(material.getId()));
                        item.put("titulo", material.getTitulo());
                        item.put("descripcion", material.getDescripcion());
                        item.put("fecha", Util.dateToStringLong(material.getFecha()));
                        item.put("publicador", material.getPublicador().getApellidoPaterno()
                                + " " + material.getPublicador().getNombres());
                        item.put("tamanio", material.getTamanio());
                        item.put("toCentroCopiado", material.isToCentroCopiado());
                        materialMapList.add(item);
                    }

                }

                item = new HashMap<String, Object>();
                item.put("tipo", tipoMaterial);
                item.put("archivos", materialMapList);
                list.add(item);
            }
        }

        return list;
    }

    /*
     * Recupera material disponible si el usuario a sido alumno de la misma.
     */
    public List<Map<String, Object>> getMaterialDisponibleList(int idAsignatura, int idSeccion) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (isAlumnoAsignatura(idAsignatura, idSeccion)) {
            list = this.getTipoMaterialList(idAsignatura, idSeccion);
        }
        return list;
    }

    public String getRutaMaterialDownload(int idMaterial) {
        String fullPathArchivo = materialDAO.getMaterialById(idMaterial).getRutaArchivo();

        if (!fullPathArchivo.isEmpty()) {
            return fullPathArchivo;
        } else {
            return "";
        }
    }
    
    public Material getMaterialDownload(int idMaterial) {
        return materialDAO.getMaterialById(idMaterial);
    }

    /*
     * Asistencias
     */
    public int getPorcentajeInasistenciasAlumno(int numClases, int numFaltas) {
        if (numClases > 0) {
            return 100 * numFaltas / numClases;
        } else {
            return 0;
        }
    }

    public Map<String, Object> getAsistenciaAsignatura(int idAsignatura, int idSeccion) {
        Map<String, Object> data = new HashMap<String, Object>();
        Alumno alumno = usuarioService.getInfoUsuario();

        Map<String, Object> dataAsignatura = asistenciaDAO
                .getAsistenciaAsignatura(idAsignatura, idSeccion);
        //DESCUENTO FALTA
        if ((Boolean) dataAsignatura.get("descuentoFaltas")) {
            data.put("descuentos", "Se aplicará descuentos por inasistencias.");
            data.put("horas", dataAsignatura.get("numClases"));

            //DESCUENTO
            Map<String, Object> dataDescuento = asistenciaDAO
                    .getDescuentoInasistencias(idAsignatura, idSeccion, alumno.getId());
            data.put("totalDescuento", dataDescuento.get("descuentoInasistencia"));

            //INASISTENCIAS
            int numFaltas;
            if ((Integer) dataAsignatura.get("modoAsistencia") == 1) {
                numFaltas = asistenciaDAO
                        .getCountInasistencia(idAsignatura, idSeccion, alumno.getId());
            } else {
                numFaltas = (Integer) (dataDescuento.get("numFaltas") == null ? 0 : dataDescuento.get("numFaltas"));
            }
            data.put("inasistencias", numFaltas + " ("
                    + getPorcentajeInasistenciasAlumno((Integer) dataAsignatura.get("numClases"), numFaltas)
                    + "%)");
        } else {
            data.put("descuentos", "No se aplicará descuentos por inasistencias.");
            data.put("horas", 0);
            data.put("inasistencias", 0 + " (0%)");
            data.put("totalDescuento", null);
        }
        //MODO ASISTENCIA
        if ((Integer) dataAsignatura.get("modoAsistencia") == 1) {
            data.put("modo", "Control diario");
            data.put("descripcion", "El profesor registrará en "
                    + "el sistema las asistencias tomadas en cada clase.");
        } else {
            data.put("modo", "Control consolidado");
            data.put("descripcion", "El profesor registrará "
                    + "el total de inasistencias de cada alumno al final del ciclo.");
        }

        return data;
    }

    public Map<String, Object> getDatosAsistenciaAsignatura(int idAsignatura, int idSeccion,boolean moroso) {
        Map<String, Object> data = new HashMap<String, Object>();
        Alumno alumno = usuarioService.getInfoUsuario();

       
        data.put("totalDescuento", "");
        data.put("notaSinDcto", "");
        if(moroso){
            return data;
        }
         Map<String, Object> dataAsignatura = asistenciaDAO
                .getAsistenciaAsignatura(idAsignatura, idSeccion);
        //DESCUENTO FALTA
        if ((Boolean) dataAsignatura.get("descuentoFaltas")) {
            //DESCUENTO
            Map<String, Object> dataDescuento = asistenciaDAO
                    .getDescuentoInasistencias(idAsignatura, idSeccion, alumno.getId());
            data.put("totalDescuento", dataDescuento.get("descuentoInasistencia"));
            if ((Integer) dataDescuento.get("notaSinDcto") > 0) {
                Nota nota = evaluacionDAO.getNota((Integer) dataDescuento.get("notaSinDcto"));
                if (nota != null) {
                    data.put("notaSinDcto", nota.getDescripcion());
                }
            }
        }

        return data;
    }

    public List<Asistencia> getAsistenciaAlumnoAsignaturaList(int idAsignatura, int idSeccion) {
        Alumno alumno = usuarioService.getInfoUsuario();
        return asistenciaDAO.getAsistenciaAlumnoAsignaturaList(idAsignatura, idSeccion, alumno.getId());
    }

    public List<Persona> getAlumnosAsignaturaDictadaList(int idAsignatura, int idSeccion) {
        return alumnoDAO.getAlumnosAsignaturaDictadaList(idAsignatura, idSeccion);
    }

    public Silabo getSilaboByAsignaturaDictada(int id, int idEdicionestudio) {
        Silabo silabo = silaboDAO.getSilaboById(id);
        silabo = getSilaboDetalle(silabo, idEdicionestudio);

        silabo.setUnidadList(silaboDAO.getUnidadesList(id));
        for (UnidadSilabo item : silabo.getUnidadList()) {
            item.setTemaList(getTemasByUnidad(item.getId()));
        }
        silabo.setEstrategiaList(this.getEstrategiasSilaboList(silabo.getId()));
        silabo.setBibliografiaBasica(this.getBibliografiaBasicaSilaboList(silabo.getId()));
        silabo.setBibliografiaavanzada(this.getBibliografiaAvanzadaSilaboList(silabo.getId()));

        silabo.setTipoEvaluacionList(this.getAsignaturaEvaluacionAlumnoList(silabo.getAsignaturaDictada().getId(), silabo.getAsignaturaDictada()
                .getAsignaturaSeccion().getIdSeccion(),false));

        return silabo;
    }

    public Silabo getSilaboByAsignaturaDictada(int idAsignatura, int idSeccion, int idEdicionestudio) {
        Silabo silabo = silaboDAO.getSilaboByAsignaturaDictada(idAsignatura, idSeccion);
        return getSilaboDetalle(silabo, idEdicionestudio);
    }

    private Silabo getSilaboDetalle(Silabo silabo, int idEdicionestudio) {
        Alumno alumno = usuarioService.getInfoUsuario();

        if (silabo == null) {
            return null;
        }

        PlanEstudio planEstudio = alumnoEstudioDAO
                .getPlanEstudioAlumnoEstudioActivoByAlumnoEstudio(alumno.getId(), idEdicionestudio);

        Map<String, Object> detalles = silaboDAO
                .getDetallesSilaboAsignatura(silabo.getAsignaturaDictada().getId(), planEstudio.getId());
        silabo.getAsignaturaDictada().setTipoAsignatura((String) detalles.get("tipoAsignatura"));

        silabo.getAsignaturaDictada().setProfesorList(
                asignaturaDictadaDAO.findProfesores(silabo.getAsignaturaDictada()));

        silabo.setObjetivoList(silaboDAO.getObjetivosList(silabo.getId()));

        return silabo;
    }

    public List<TemaSilabo> getTemasByUnidad(int idUnidad) {
        List<TemaSilabo> temasList = silaboDAO.getTemasByUnidad(idUnidad);
        for (TemaSilabo tema : temasList) {
            tema.setFechasSesion(silaboDAO.getFechaSessionListByTema(tema.getId()));
        }
        return temasList;
    }

    public List<EstrategiaSilabo> getEstrategiasSilaboList(int idSilabo) {
        List<EstrategiaSilabo> list = silaboDAO.getEstrategiasSilaboDictadaList(idSilabo);
        EstrategiaSilabo eS;
        for (EstrategiaSilabo estrategia : list) {
            if (estrategia.getId() != 0) {
                eS = silaboDAO.getEstrategiaSilabo(estrategia.getId());
                estrategia.setTitulo(eS.getTitulo());
                estrategia.setDescripcion(eS.getDescripcion());
            }
        }
        return list;
    }

    public List<String> getBibliografiaBasicaSilaboList(int idSilabo) {
        return silaboDAO.getBibliografiaSilaboByTipo(idSilabo, TipoBibliografiaSilabo.BASICA.getId());
    }

    public List<String> getBibliografiaAvanzadaSilaboList(int idSilabo) {
        return silaboDAO.getBibliografiaSilaboByTipo(idSilabo, TipoBibliografiaSilabo.AVANZADA.getId());
    }

    public String getCentrosBySilabo(int idSilabo) {
        List<String> list = silaboDAO.getCentrosBySilabo(idSilabo);
        String centros = "";
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                centros += ", ";
            }
            centros += list.get(i);
        }
        centros += ".";
        return centros;
    }

    public String getProgramasBySilabo(int idSilabo) {
        List<String> list = silaboDAO.getProgramasBySilabo(idSilabo);
        String programas = "";
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                programas += ", ";
            }
            programas += list.get(i);
        }
        programas += ".";
        return programas;
    }

    public List<Evaluacion> getEvaluacionesByAsignaturaDictada(int idAsignatura, int idSeccion,boolean moroso) {
        Alumno alumno = usuarioService.getInfoUsuario();
        List<Evaluacion> evaluacionList = evaluacionDAO.getEvaluacionesByAsignaturaDictada(idAsignatura, idSeccion);
        for (Evaluacion evaluacion : evaluacionList) {
            evaluacion.setEvaluacionAlumno(evaluacionDAO
                    .getEvaluacionAlumno(evaluacion.getId(), alumno.getId()));
            //nota literal
            if (evaluacion.getEvaluacionAlumno() != null) {
                 if(moroso){
                        Nota n=new Nota(0);
                        n.setDescripcion("No Disponible");
                       evaluacion.getEvaluacionAlumno().setNota(n); 
                    }
                if (evaluacion.getEvaluacionAlumno().getNota() != null) {
                    if (evaluacion.getEvaluacionAlumno().getNota().isLiteral()) {
                        evaluacion.getEvaluacionAlumno().getNota().setDescripcion(this.getNotaLiteral(evaluacion.getAsignaturaDictada().getId(),
                                evaluacion.getEvaluacionAlumno().getNota()));
                    }
                }
            }
        }
        return evaluacionList;
    }

    public List<Persona> findProfesores(AsignaturaDictada asignaturaDictada) {
        return asignaturaDictadaDAO.findProfesores(asignaturaDictada);
    }

    /*
     * Horario asignaturas Pregrado
     */
    public List<AsignaturaDictada> getHorarioAsignaturaDictadaPregradoList() {
        Alumno alumno = usuarioService.getInfoUsuario();
        List<AsignaturaDictada> asignaturaDictadaList = asignaturaDictadaDAO
                .getAsignaturaDictadaList(alumno.getId());
        for (AsignaturaDictada asignaturaDictada : asignaturaDictadaList) {

            if (alumnoEstudioService.anuloCicloByAlumnoEstudioPeriodoAcademico(alumno.getId(),
                    asignaturaDictada.getId(), asignaturaDictada.getAsignaturaSeccion().getIdSeccion())) {
                asignaturaDictada.setRetiroCurso(true);
            }
            asignaturaDictada.setHorarioList(asignaturaDictadaDAO
                    .getHorarioByAsignaturaSeccion(
                            asignaturaDictada.getId(), asignaturaDictada.getAsignaturaSeccion().getIdSeccion()));
            asignaturaDictada.setPracticaProgramadaList(asignaturaDictadaDAO
                    .getPracticaProgramadaByAsignaturaSeccion(
                            asignaturaDictada.getId(), asignaturaDictada.getAsignaturaSeccion().getIdSeccion()));
        }
        return asignaturaDictadaList;
    }

    public AsignaturaDictada getHorarioAsignaturaDictadaPregrado(int idAsignaturaDictada, int idSeccion) {
        AsignaturaDictada asignaturaDictada = asignaturaDictadaDAO
                .getAsignaturaDictada(idAsignaturaDictada, idSeccion);
        asignaturaDictada.setHorarioList(asignaturaDictadaDAO
                .getHorarioByAsignaturaSeccion(idAsignaturaDictada, idSeccion));
        asignaturaDictada.setPracticaProgramadaList(asignaturaDictadaDAO
                .getPracticaProgramadaByAsignaturaSeccion(idAsignaturaDictada, idSeccion));
        return asignaturaDictada;
    }

    /*
     * Horario asignaturas Posgrado
     */
    public List<Map<String, Object>> getHorarioPosgradoList(boolean horarioRegular) {
        Alumno alumno = usuarioService.getInfoUsuario();
        List<Date> fechas = asignaturaDictadaDAO.getFechasHorarioPosgrado(alumno.getId(), horarioRegular);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> item;
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd", new Locale("ES"));
        SimpleDateFormat dateFormatMes = new SimpleDateFormat("'@' MMMM '@' yyyy", new Locale("ES"));

        for (Date fecha : fechas) {
            item = new HashMap<String, Object>();
            item.put("diaNombre", WordUtils.capitalize(dateFormat.format(fecha)));
            item.put("mesNombre", WordUtils.capitalize(dateFormatMes.format(fecha)).replaceAll("@", "de"));
            item.put("horaList", asignaturaDictadaDAO.getHorarioAsignaturaPosgradoByFecha(
                    alumno.getId(), fecha, horarioRegular));
            list.add(item);
        }
        return list;
    }

    public List<AsignaturaDictada> getAsignaturas() {
        Alumno alumno = usuarioService.getInfoUsuario();
        List<AsignaturaDictada> asignaturaDictadaList = asignaturaDictadaDAO
                .getAsignaturaDictadaList(alumno.getId());
        return asignaturaDictadaList;
    }

    public List<String> getResumen() {
        List<String> list = new ArrayList<String>();
        String nuevo;
        int cantNuevo;
        for (AsignaturaDictada asignaturaDictada : this.getAsignaturas()) {
            nuevo = "";
            //Aviso
            cantNuevo = avisoDAO.getTotalAvisosAsignaturaNuevos(asignaturaDictada.getId(),
                    asignaturaDictada.getAsignaturaSeccion().getIdSeccion());
            if (cantNuevo > 0) {
                nuevo += "Nuevo aviso";
            }
            //Material
            cantNuevo = materialDAO.getTotalMaterialNuevo(asignaturaDictada.getId(),
                    asignaturaDictada.getAsignaturaSeccion().getIdSeccion());
            if (cantNuevo > 0) {
                if (nuevo.length() > 0) {
                    nuevo += " / ";
                }
                nuevo += "Nuevo material agregado";
            }

            if (nuevo.length() > 0) {
                list.add("<a href=\"#/cursos/" + asignaturaDictada.getSigla()
                        + "/" + asignaturaDictada.getId()
                        + "/" + asignaturaDictada.getAsignaturaSeccion().getIdSeccion()
                        + "\"><strong>" + asignaturaDictada.getSigla()
                        + "</strong> - " + nuevo + "</a>");
            }
        }

        return list;
    }

    /*
     * Busqueda de Asignaturas
     */
    public List<AsignaturaDictada> findAsignaturaDictadaByCampusCAcadPeriodoAcademico(int idCampus,
            int idCentroAcademico, int idPeriodoAcademico, String sigla, String nombre) {
        List<AsignaturaDictada> list = asignaturaDictadaDAO.findAsignaturaDictadaByCampusCAcadPeriodoAcademico(
                idCampus, idCentroAcademico, idPeriodoAcademico, sigla, nombre);

        for (AsignaturaDictada asignaturaDictada : list) {
            asignaturaDictada.setPeriodoAcademico(periodoAcademicoDAO.getPA(
                    asignaturaDictada.getPeriodoAcademico().getId()));
            asignaturaDictada.setEstudio(asignaturaDictadaDAO.getNombreDeEstudio(asignaturaDictada));
            asignaturaDictada.setTotalMatriculado(asignaturaDictadaDAO.countAlumnosAsignaturaDictada(asignaturaDictada));
        }
        return list;
    }

    /*
     * Temas a dictar en una evaluación
     */
    public Map<String, Object> getEvaluacionTemasList(int idEvaluacion) {
        Map<String, Object> result = evaluacionDAO.getNombreEvaluacionAsignatura(idEvaluacion);

        List<UnidadSilabo> unidadList = silaboDAO.getUnidadesToEvaluacion(idEvaluacion);
        for (UnidadSilabo unidadSilabo : unidadList) {
            unidadSilabo.setTemaList(silaboDAO.getTemasUnidadToEvaluacion(idEvaluacion, unidadSilabo.getId()));
            unidadSilabo.setId(0);
            unidadSilabo.setNumero(0);
        }
        result.put("unidades", unidadList);
        return result;
    }

    /*
     verificar si un alumno contiene en matricula determinada asignatura.
     */
    public boolean isAlumnoAsignatura(int idAsignatura, int idSeccion) {
        Alumno alumno = usuarioService.getInfoUsuario();
        return asignaturaDictadaDAO.isAlumnoAsignatura(idAsignatura, idSeccion, alumno.getId());
    }

    /*
     * Verifica notificaciones en asignaturas matriculadas
     */
    public Map<String, Boolean> statusNotificacion(int idEdicionestudio) {
        AlumnoEstudio alumnoEstudio = commonsService.getAlumnoEstudioSession(idEdicionestudio);
        return asignaturaDictadaDAO
                .statusNotificacion(alumnoEstudio.getAlumno().getId(), alumnoEstudio.getEdicionestudio().getId());
    }

    public Map<String, Boolean> statusNotificacion(int idEdicionestudio, int idAsignaturaDictada,
            int idSeccion) {
        AlumnoEstudio alumnoEstudio = commonsService.getAlumnoEstudioSession(idEdicionestudio);
        return asignaturaDictadaDAO
                .statusNotificacion(alumnoEstudio.getAlumno().getId(), alumnoEstudio.getEdicionestudio().getId(),
                        idAsignaturaDictada, idSeccion);
    }

    public boolean isHorarioevento() {
        return commonsService.isHorarioevento();
    }

    public List<HorarioEvento> getHorarioEventos(Integer id,HttpSession httpSession) throws ParseException {
       
        return commonsService.getHorarioEventoList(id,httpSession);
    }
    public List<String> getHorarioFechas(HttpSession httpSession) {
      
      String fecha=(String)httpSession.getAttribute("fecha");
       return commonsService.getHorarioFechasList(fecha);
    }
}
