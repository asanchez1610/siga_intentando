package com.udep.siga.controller;

import com.udep.siga.bean.Articulo;
import com.udep.siga.bean.AsignaturaDictada;
import com.udep.siga.bean.AsignaturaSeccion;
import com.udep.siga.bean.DatoPersonal;
import com.udep.siga.bean.Email;
import com.udep.siga.bean.Horario;
import com.udep.siga.bean.Libro;
import com.udep.siga.bean.Persona;
import com.udep.siga.bean.PracticaProgramada;
import com.udep.siga.bean.Tesis;
import com.udep.siga.bean.TrabajoInvestigacion;
import com.udep.siga.service.MensajeService;
import com.udep.siga.service.ProfesorService;
import com.udep.siga.service.UsuarioService;
import com.udep.siga.util.AppConstants;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Wilfredo Atoche
 */
@Controller
@RequestMapping("/json/profesor/*")
public class ProfesorController {

    @Autowired
    private MensajeService mensajeService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ProfesorService profesorService;

    @RequestMapping(value = "perfil.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> perfil(@RequestParam(value = "idProfesor", required = true) String idProfesorRandom) {
        Integer idProfesor = (Integer) usuarioService.getRefDirecta(idProfesorRandom);
        Map<String, Object> data = new HashMap<String, Object>();

        data.put("title", "Profesores");
        Persona profesor = usuarioService.getPersonaById(idProfesor);
        data.put("profesor", null);
        if (profesor != null) {
            Map<String, Object> itemProfesor = new HashMap<String, Object>();
            itemProfesor.put("id", usuarioService.addRefIndirecta(profesor.getId()));
            itemProfesor.put("nombres", profesor.getNombres());
            itemProfesor.put("apellidoPaterno", profesor.getApellidoPaterno());
            itemProfesor.put("apellidoMaterno", profesor.getApellidoMaterno());
            itemProfesor.put("foto", profesor.getFoto());

            data.put("profesor", itemProfesor);

            DatoPersonal datoPersonal = usuarioService.getDatoPersonal(profesor.getId());
            Map<String, Object> infoAcademica = profesorService.getInfoAcademico(profesor.getId());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM", new Locale("ES"));
            if (datoPersonal != null) {
                if (datoPersonal.getFechaNacimiento() != null) {
                    data.put("fechaCumpleanios", WordUtils.capitalize(dateFormat.format(datoPersonal.getFechaNacimiento())));
                } else {
                    data.put("fechaCumpleanios", "-");
                }

                if (datoPersonal.getBlog() != null) {
                    data.put("blog", datoPersonal.getBlog());
                } else {
                    data.put("blog", "");
                }

                if (datoPersonal.getTwitter() != null) {
                    data.put("twitter", datoPersonal.getTwitter());
                } else {
                    data.put("twitter", "");
                }

                if (datoPersonal.getGoogleScholar() != null) {
                    data.put("googleScholar", datoPersonal.getGoogleScholar());
                } else {
                    data.put("googleScholar", "");
                }

                if (datoPersonal.getPaginaPersonal() != null) {
                    data.put("pagPersonal", datoPersonal.getPaginaPersonal());
                } else {
                    data.put("pagPersonal", "");
                }
            } else {
                data.put("fechaCumpleanios", "-");
                data.put("blog", "");
                data.put("twitter", "");
                data.put("googleScholar", "");
                data.put("pagPersonal", "");
            }

            List<Email> asesorEmailList = mensajeService.getEmailList(profesor.getId());
            if (!asesorEmailList.isEmpty()) {
                data.put("asesorEmail", asesorEmailList.get(0).getEmail());
            } else {
                data.put("asesorEmail", null);
            }

            data.putAll(infoAcademica);

            if (infoAcademica.get("oficina") == null) {
                data.put("oficina", "");
            }
            if (infoAcademica.get("edificio") == null) {
                data.put("edificio", "");
            }
            if (infoAcademica.get("anexo") == null) {
                data.put("anexo", "");
            }
            if (infoAcademica.get("departamento") == null) {
                data.put("departamento", "");
            }
            if (infoAcademica.get("centroacademico") == null) {
                data.put("centroacademico", "");
            }
            data.put("asignaturaList", profesorService.getHorarioList(profesor.getId()));
        }
        return data;
    }

    @RequestMapping(value = "investigacion.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> investigacion(@RequestParam(value = "idProfesor", required = true) String idProfesorRandom) {
        Integer idProfesor = (Integer) usuarioService.getRefDirecta(idProfesorRandom);
        Map<String, Object> data = new HashMap<String, Object>();
        List<Map<String, Object>> listEnCurso;
        List<Map<String, Object>> listArts;
        List<Map<String, Object>> listLibros;
        List<Map<String, Object>> listTesis;
        data.put("title", "Profesores");
        Persona profesor = usuarioService.getPersonaById(idProfesor);
        data.put("profesor", null);
        if (profesor != null) {
            Map<String, Object> itemProfesor = new HashMap<String, Object>();
            itemProfesor.put("id", usuarioService.addRefIndirecta(profesor.getId()));
            itemProfesor.put("nombres", profesor.getNombres());
            itemProfesor.put("apellidoPaterno", profesor.getApellidoPaterno());
            itemProfesor.put("apellidoMaterno", profesor.getApellidoMaterno());
            itemProfesor.put("foto", profesor.getFoto());

            data.put("profesor", itemProfesor);
            List<TrabajoInvestigacion> investiga = profesorService.getTrabajosInvestigacionByProfesor(idProfesor);
            listEnCurso = new ArrayList<Map<String, Object>>();
            if (investiga != null) {
                Map<String, Object> itemEnCurso;
                for (TrabajoInvestigacion t : investiga) {
                    itemEnCurso = new HashMap<String, Object>();
                    itemEnCurso.put("id", usuarioService.addRefIndirecta(t.getId()));
                    itemEnCurso.put("titulo", t.getTitulo());
                    listEnCurso.add(itemEnCurso);
                }
                data.put("enCurso", listEnCurso);
            } else {
                data.put("enCurso", null);
            }

            List<Articulo> arts = profesorService.getArtByProfesor(idProfesor);
            listArts = new ArrayList<Map<String, Object>>();
            data.put("articulos", null);
            if (arts != null) {
                Map<String, Object> itemArts;
                for (Articulo t : arts) {
                    itemArts = new HashMap<String, Object>();
                    itemArts.put("id", usuarioService.addRefIndirecta(t.getId()));
                    itemArts.put("titulo", t.getInvestigacionGenerica().getTitulo());
                    listArts.add(itemArts);
                }
                data.put("articulos", listArts);
            }
            List<Libro> libros = profesorService.getLibrosByProfesor(idProfesor);
            listLibros = new ArrayList<Map<String, Object>>();
            data.put("libros", null);
            if (libros != null) {
                Map<String, Object> itemLibros;
                for (Libro t : libros) {
                    itemLibros = new HashMap<String, Object>();
                    itemLibros.put("id", usuarioService.addRefIndirecta(t.getId()));
                    itemLibros.put("titulo", t.getInvestigacionGenerica().getTitulo());
                    itemLibros.put("isCapitulo", t.isColectivo());
                    listLibros.add(itemLibros);
                }
                data.put("libros", listLibros);
            }
            List<Tesis> tesis = profesorService.getTesisByProfesor(idProfesor);
            listTesis = new ArrayList<Map<String, Object>>();
            data.put("tesis", null);
            if (tesis != null) {
                Map<String, Object> itemTesis;
                for (Tesis t : tesis) {
                    itemTesis = new HashMap<String, Object>();
                    itemTesis.put("id", usuarioService.addRefIndirecta(t.getId()));
                    itemTesis.put("titulo", t.getInvestigacionGenerica().getTitulo());
                    itemTesis.put("tipo", t.getTipoTesis().getNombre());
                    listTesis.add(itemTesis);
                }
                data.put("tesis", listTesis);
            }
        }
        return data;
    }

    @RequestMapping(value = "investigacionCurso.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> investigacionEncurso(@RequestParam(value = "id", required = true) String idTrbajoRandom) {
        Integer idTrbajo = (Integer) usuarioService.getRefDirecta(idTrbajoRandom);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Investigaciones");
        data.put("archivos", profesorService.getArchivoInvestigacionList(idTrbajo));

        TrabajoInvestigacion trabajo = profesorService.getDetalleTrabajosInvestigacion(idTrbajo);
        data.put("investigacion", null);
        if (trabajo != null) {
            Map<String, Object> itemTrab;
            itemTrab = new HashMap<String, Object>();

            itemTrab.put("autores", trabajo.getAutores());
            itemTrab.put("campoInvestigacion", trabajo.getCampoInvestigacion().getNombre());
            itemTrab.put("resultado", trabajo.getResultado());
            itemTrab.put("fechaInicio", trabajo.getFechaInicio());
            itemTrab.put("fechaFin", trabajo.getFechaFin());
            itemTrab.put("descripcion", trabajo.getDescripcion());

            data.put("investigacion", itemTrab);
        }
        return data;
    }

    @RequestMapping(value = "investigacionArt.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> investigacionArticulo(@RequestParam(value = "id", required = true) String idArtRandom) {
        Integer idArt = (Integer) usuarioService.getRefDirecta(idArtRandom);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Artículos");

        Articulo art = profesorService.getArticulo(idArt);
        data.put("articulo", null);
        if (art != null) {
            Map<String, Object> itemArt;
            itemArt = new HashMap<String, Object>();

            itemArt.put("colaboradores", art.getColaboradores());
            itemArt.put("medioPublicacion", art.getMedioPublicacion());
            itemArt.put("proceso", art.getProceso());
            itemArt.put("nombreMedio", art.getNombreMedio());
            itemArt.put("indexada", art.getIndexada());
            itemArt.put("publicadoActa", art.isPublicadoActa());
            itemArt.put("isbn", art.getIsbn());
            itemArt.put("acta", art.getActa());
            itemArt.put("issn", art.getIssn());
            itemArt.put("edicion", art.getEdicion());
            itemArt.put("tomoVolumen", art.getTomoVolumen());
            itemArt.put("numeroFasciculo", art.getNumeroFasciculo());
            itemArt.put("inicio", art.getInicio());
            itemArt.put("fin", art.getFin());
            itemArt.put("doi", art.getDoi());
            itemArt.put("fecha", art.getFecha());
            itemArt.put("ciudadPublica", art.getCiudadPublica());
            itemArt.put("paisPublica", art.getPaisPublica());
            itemArt.put("otraVersion", art.getOtraVersion());

            if (art.getInvestigacionGenerica() != null) {
                itemArt.put("centroAcademico", art.getInvestigacionGenerica().getCentroAcademico().getNombre());
                itemArt.put("estudio", art.getInvestigacionGenerica().getEstudio().getNombre());
                itemArt.put("departamento", art.getInvestigacionGenerica().getDepartamento().getNombre());
                itemArt.put("areaInvestigacion", art.getInvestigacionGenerica().getAreaInvestigacion().getNombre());
                itemArt.put("campoInvestigacion", art.getInvestigacionGenerica().getCampoInvestigacion().getNombre());
                itemArt.put("lineaInvestigacion", art.getInvestigacionGenerica().getLineaInvestigacion().getNombre());
                itemArt.put("titulo", art.getInvestigacionGenerica().getTitulo());
                itemArt.put("autor", art.getInvestigacionGenerica().getAutor());
                itemArt.put("estado", art.getInvestigacionGenerica().getEstado().getNombre());
                itemArt.put("depositoLegal", art.getInvestigacionGenerica().getDepositoLegal());
                itemArt.put("palabraClave", art.getInvestigacionGenerica().getPalabraClave());
                itemArt.put("resumen", art.getInvestigacionGenerica().getResumen());
                itemArt.put("englishResumen", art.getInvestigacionGenerica().getEnglishResumen());
                itemArt.put("restriciones", art.getInvestigacionGenerica().getRestriciones());
                itemArt.put("documentos", art.getInvestigacionGenerica().getDocumentos());
                itemArt.put("titularDerechos", art.getInvestigacionGenerica().getTitularDerechos());
                itemArt.put("derechos", art.getInvestigacionGenerica().getDerechos());
                itemArt.put("urlLicenciaDerecho", art.getInvestigacionGenerica().getUrlLicenciaDerecho());
                itemArt.put("evento", null);
                if (art.getInvestigacionGenerica().getEvento() != null) {
                    Map<String, Object> itemEvento = new HashMap<String, Object>();
                    itemEvento.put("tituloPonencia", art.getInvestigacionGenerica().getEvento().getTituloPonencia());
                    itemEvento.put("numEvento", art.getInvestigacionGenerica().getEvento().getNumEvento());
                    itemEvento.put("nombre", art.getInvestigacionGenerica().getEvento().getNombre());
                    itemEvento.put("organizadoPor", art.getInvestigacionGenerica().getEvento().getOrganizadoPor());
                    itemEvento.put("ambito", art.getInvestigacionGenerica().getEvento().getAmbito());
                    itemEvento.put("ciudad", art.getInvestigacionGenerica().getEvento().getCiudad());
                    itemEvento.put("pais", art.getInvestigacionGenerica().getEvento().getPais());
                    itemEvento.put("fecha", art.getInvestigacionGenerica().getEvento().getFecha());
                    itemEvento.put("descripcion", art.getInvestigacionGenerica().getEvento().getDescripcion());

                    itemArt.put("evento", itemEvento);
                }
            } else {
                itemArt.put("centroAcademico", "");
                itemArt.put("estudio", "");
                itemArt.put("departamento", "");
                itemArt.put("areaInvestigacion", "");
                itemArt.put("campoInvestigacion", "");
                itemArt.put("lineaInvestigacion", "");
                itemArt.put("titulo", "");
                itemArt.put("autor", "");
                itemArt.put("estado", "");
                itemArt.put("depositoLegal", "");
                itemArt.put("palabraClave", "");
                itemArt.put("resumen", "");
                itemArt.put("englishResumen", "");
                itemArt.put("restriciones", null);
                itemArt.put("documentos", null);
                itemArt.put("titularDerechos", "");
                itemArt.put("derechos", "");
                itemArt.put("urlLicenciaDerecho", "");
                itemArt.put("evento", null);
            }
            if (art.getTipoArticulo() != null) {
                itemArt.put("tipoArticulo", art.getTipoArticulo().getNombre());
            } else {
                itemArt.put("tipoArticulo", "");
            }
            data.put("articulo", itemArt);
        }
        return data;
    }

    @RequestMapping(value = "investigacionTesis.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> investigacionTesis(@RequestParam(value = "id", required = true) String idTesisRandom) {
        Integer idTesis = (Integer) usuarioService.getRefDirecta(idTesisRandom);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Tesis");

        Tesis tesis = profesorService.getTesis(idTesis);
        data.put("tesis", null);
        if (tesis != null) {
            Map<String, Object> itemTesis;

            itemTesis = new HashMap<String, Object>();
            itemTesis.put("gradoObtenido", tesis.getGradoObtenido());
            itemTesis.put("asesor", tesis.getAsesor());
            itemTesis.put("universidad", tesis.getUniversidad());
            itemTesis.put("fechaInicio", tesis.getFechaInicio());
            itemTesis.put("fechaFin", tesis.getFechaFin());
            itemTesis.put("fechaSustentacion", tesis.getFechaSustentacion());
            itemTesis.put("isbn", tesis.getIsbn());
            itemTesis.put("paginas", tesis.getPaginas());

            if (tesis.getInvestigacionGenerica() != null) {
                itemTesis.put("centroAcademico", tesis.getInvestigacionGenerica().getCentroAcademico().getNombre());
                itemTesis.put("estudio", tesis.getInvestigacionGenerica().getEstudio().getNombre());
                itemTesis.put("departamento", tesis.getInvestigacionGenerica().getDepartamento().getNombre());
                itemTesis.put("areaInvestigacion", tesis.getInvestigacionGenerica().getAreaInvestigacion().getNombre());
                itemTesis.put("campoInvestigacion", tesis.getInvestigacionGenerica().getCampoInvestigacion().getNombre());
                itemTesis.put("lineaInvestigacion", tesis.getInvestigacionGenerica().getLineaInvestigacion().getNombre());
                itemTesis.put("titulo", tesis.getInvestigacionGenerica().getTitulo());
                itemTesis.put("autor", tesis.getInvestigacionGenerica().getAutor());
                itemTesis.put("estado", tesis.getInvestigacionGenerica().getEstado().getNombre());
                itemTesis.put("depositoLegal", tesis.getInvestigacionGenerica().getDepositoLegal());
                itemTesis.put("palabraClave", tesis.getInvestigacionGenerica().getPalabraClave());
                itemTesis.put("resumen", tesis.getInvestigacionGenerica().getResumen());
                itemTesis.put("englishResumen", tesis.getInvestigacionGenerica().getEnglishResumen());
                itemTesis.put("restriciones", tesis.getInvestigacionGenerica().getRestriciones());
                itemTesis.put("documentos", tesis.getInvestigacionGenerica().getDocumentos());
                itemTesis.put("titularDerechos", tesis.getInvestigacionGenerica().getTitularDerechos());
                itemTesis.put("derechos", tesis.getInvestigacionGenerica().getDerechos());
                itemTesis.put("urlLicenciaDerecho", tesis.getInvestigacionGenerica().getUrlLicenciaDerecho());
            } else {
                itemTesis.put("centroAcademico", "");
                itemTesis.put("estudio", "");
                itemTesis.put("departamento", "");
                itemTesis.put("areaInvestigacion", "");
                itemTesis.put("campoInvestigacion", "");
                itemTesis.put("lineaInvestigacion", "");
                itemTesis.put("titulo", "");
                itemTesis.put("autor", "");
                itemTesis.put("estado", "");
                itemTesis.put("depositoLegal", "");
                itemTesis.put("palabraClave", "");
                itemTesis.put("resumen", "");
                itemTesis.put("englishResumen", "");
                itemTesis.put("restriciones", null);
                itemTesis.put("documentos", null);
                itemTesis.put("titularDerechos", "");
                itemTesis.put("derechos", "");
                itemTesis.put("urlLicenciaDerecho", "");
            }
            if (tesis.getTipoTesis() != null) {
                itemTesis.put("tipoTesis", tesis.getTipoTesis().getNombre());
            } else {
                itemTesis.put("tipoTesis", "");
            }
            data.put("tesis", itemTesis);
        }
        return data;
    }

    @RequestMapping(value = "investigacionLibro.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> investigacionLibro(@RequestParam(value = "id", required = true) String idLibroRandom) {
        Integer idLibro = (Integer) usuarioService.getRefDirecta(idLibroRandom);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Libros");

        Libro libro = profesorService.getLibro(idLibro);
        data.put("libro", null);
        if (libro != null) {
            Map<String, Object> itemLibro;
            itemLibro = new HashMap<String, Object>();

            itemLibro.put("colaboradores", libro.getColaboradores());
            itemLibro.put("colectivo", libro.isColectivo());
            itemLibro.put("coordinador", libro.getCoordinador());
            itemLibro.put("serieColeccion", libro.getSerieColeccion());
            itemLibro.put("editorial", libro.getEditorial());
            itemLibro.put("isbn", libro.getIsbn());
            itemLibro.put("paginas", libro.getPaginas());
            itemLibro.put("edicion", libro.getEdicion());
            itemLibro.put("fechaPublica", libro.getFechaPublica());
            itemLibro.put("ciudad", libro.getCiudad());
            itemLibro.put("pais", libro.getPais());
            itemLibro.put("otrasVersiones", libro.getOtrasVersiones());
            itemLibro.put("capitulos", libro.getCapitulos());
            
            if (libro.getInvestigacionGenerica() != null) {
                itemLibro.put("centroAcademico", libro.getInvestigacionGenerica().getCentroAcademico().getNombre());
                itemLibro.put("estudio", libro.getInvestigacionGenerica().getEstudio().getNombre());
                itemLibro.put("departamento", libro.getInvestigacionGenerica().getDepartamento().getNombre());
                itemLibro.put("areaInvestigacion", libro.getInvestigacionGenerica().getAreaInvestigacion().getNombre());
                itemLibro.put("campoInvestigacion", libro.getInvestigacionGenerica().getCampoInvestigacion().getNombre());
                itemLibro.put("lineaInvestigacion", libro.getInvestigacionGenerica().getLineaInvestigacion().getNombre());
                itemLibro.put("titulo", libro.getInvestigacionGenerica().getTitulo());
                itemLibro.put("tituloAlt", libro.getInvestigacionGenerica().getTituloAlt());
                itemLibro.put("autor", libro.getInvestigacionGenerica().getAutor());
                itemLibro.put("estado", libro.getInvestigacionGenerica().getEstado().getNombre());
                itemLibro.put("depositoLegal", libro.getInvestigacionGenerica().getDepositoLegal());
                itemLibro.put("palabraClave", libro.getInvestigacionGenerica().getPalabraClave());
                itemLibro.put("resumen", libro.getInvestigacionGenerica().getResumen());
                itemLibro.put("englishResumen", libro.getInvestigacionGenerica().getEnglishResumen());
                itemLibro.put("restriciones", libro.getInvestigacionGenerica().getRestriciones());
                itemLibro.put("documentos", libro.getInvestigacionGenerica().getDocumentos());
                itemLibro.put("titularDerechos", libro.getInvestigacionGenerica().getTitularDerechos());
                itemLibro.put("derechos", libro.getInvestigacionGenerica().getDerechos());
                itemLibro.put("urlLicenciaDerecho", libro.getInvestigacionGenerica().getUrlLicenciaDerecho());
                itemLibro.put("evento", null);
                if (libro.getInvestigacionGenerica().getEvento() != null) {
                    Map<String, Object> itemEvento = new HashMap<String, Object>();
                    itemEvento.put("tituloPonencia", libro.getInvestigacionGenerica().getEvento().getTituloPonencia());
                    itemEvento.put("numEvento", libro.getInvestigacionGenerica().getEvento().getNumEvento());
                    itemEvento.put("nombre", libro.getInvestigacionGenerica().getEvento().getNombre());
                    itemEvento.put("organizadoPor", libro.getInvestigacionGenerica().getEvento().getOrganizadoPor());
                    itemEvento.put("ambito", libro.getInvestigacionGenerica().getEvento().getAmbito());
                    itemEvento.put("ciudad", libro.getInvestigacionGenerica().getEvento().getCiudad());
                    itemEvento.put("pais", libro.getInvestigacionGenerica().getEvento().getPais());
                    itemEvento.put("fecha", libro.getInvestigacionGenerica().getEvento().getFecha());
                    itemEvento.put("descripcion", libro.getInvestigacionGenerica().getEvento().getDescripcion());

                    itemLibro.put("evento", itemEvento);
                }
            } else {
                itemLibro.put("centroAcademico", "");
                itemLibro.put("estudio", "");
                itemLibro.put("departamento", "");
                itemLibro.put("areaInvestigacion", "");
                itemLibro.put("campoInvestigacion", "");
                itemLibro.put("lineaInvestigacion", "");
                itemLibro.put("titulo", "");
                itemLibro.put("autor", "");
                itemLibro.put("estado", "");
                itemLibro.put("depositoLegal", "");
                itemLibro.put("palabraClave", "");
                itemLibro.put("resumen", "");
                itemLibro.put("englishResumen", "");
                itemLibro.put("restriciones", null);
                itemLibro.put("documentos", null);
                itemLibro.put("titularDerechos", "");
                itemLibro.put("derechos", "");
                itemLibro.put("urlLicenciaDerecho", "");
                itemLibro.put("evento", null);
            }
            
            data.put("libro", itemLibro);
        }
        return data;
    }

    @RequestMapping(value = "cvProfesor.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> cvProfesor(@RequestParam(value = "idProfesor", required = true) String idProfesorRandom) {
        Integer idProfesor = (Integer) usuarioService.getRefDirecta(idProfesorRandom);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Profesores");
        Persona profesor = usuarioService.getPersonaById(idProfesor);
        data.put("profesor", null);
        if (profesor != null) {
            Map<String, Object> itemProfesor = new HashMap<String, Object>();
            itemProfesor.put("id", usuarioService.addRefIndirecta(profesor.getId()));
            itemProfesor.put("nombres", profesor.getNombres());
            itemProfesor.put("apellidoPaterno", profesor.getApellidoPaterno());
            itemProfesor.put("apellidoMaterno", profesor.getApellidoMaterno());

            data.put("profesor", itemProfesor);

            data.put("faPregrado", profesorService.getEstudiosCVPregrado(idProfesor));
            data.put("faPosgrado", profesorService.getEstudiosCVPosgrado(idProfesor));
            data.put("complementarios", profesorService.getCapacitacionesCV(idProfesor));
            data.put("camposInvestiga", profesorService.getLineaInvestigacionByProfesor(idProfesor));

            //ACTIVIDAD DE INVESTIGACIÓN ACADÉMICA Y PROFESIONAL
            data.put("workingPaper", profesorService.getWorkingPaper(idProfesor));

            List<Tesis> tesis = profesorService.getTesisPregrado(idProfesor);
            ArrayList<Map<String, Object>> listTesisPre = new ArrayList<Map<String, Object>>();
            data.put("tesisPregrado", null);
            if (tesis != null) {
                Map<String, Object> itemTesis;
                for (Tesis t : tesis) {
                    itemTesis = new HashMap<String, Object>();
                    itemTesis.put("anio", t.getAnio());
                    itemTesis.put("titulo", t.getInvestigacionGenerica().getTitulo());
                    itemTesis.put("autor", t.getInvestigacionGenerica().getAutor());
                    itemTesis.put("gradoObtenido", t.getGradoObtenido());
                    itemTesis.put("centroAcademico", t.getInvestigacionGenerica().getCentroAcademico().getNombre());
                    listTesisPre.add(itemTesis);
                }
                data.put("tesisPregrado", listTesisPre);
            }

            tesis = profesorService.getTesisPostgrado(idProfesor);
            ArrayList<Map<String, Object>> listTesisPos = new ArrayList<Map<String, Object>>();
            data.put("tesisPostgrado", null);
            if (tesis != null) {
                Map<String, Object> itemTesis;
                for (Tesis t : tesis) {
                    itemTesis = new HashMap<String, Object>();
                    itemTesis.put("anio", t.getAnio());
                    itemTesis.put("titulo", t.getInvestigacionGenerica().getTitulo());
                    itemTesis.put("autor", t.getInvestigacionGenerica().getAutor());
                    itemTesis.put("gradoObtenido", t.getGradoObtenido());
                    itemTesis.put("centroAcademico", t.getInvestigacionGenerica().getCentroAcademico().getNombre());
                    listTesisPos.add(itemTesis);
                }
                data.put("tesisPostgrado", listTesisPos);
            }

            data.put("estancias", profesorService.getEstanciaInvestigaByProfesor(idProfesor));
            data.put("proyectos", profesorService.getProyectosByProfesor(idProfesor));
            data.put("consultoria", profesorService.getConsultoriasByProfesor(idProfesor));
            data.put("ponencias", profesorService.getEventoByProfesor(idProfesor));
            data.put("reuniones", profesorService.getReunionEventoByProfesor(idProfesor));

            //EXPERIENCIA PROFESIONAL
            data.put("libros", profesorService.getLibrosList(idProfesor));
            data.put("librosColectivos", profesorService.getLibrosColectivosList(idProfesor));
            data.put("indexado", profesorService.getArticulosIndexadosList(idProfesor));
            data.put("noIndexado", profesorService.getArticulosNoIndexadosList(idProfesor));
            data.put("conActa", profesorService.getArticulosByProfesorConActa(idProfesor));
            data.put("articuloCV", profesorService.getArticulosCVByProfesor(idProfesor));

            //EXPERIENCIA DOCENTE            
            data.put("docencia", profesorService.getDocenciaCVByProfesor(idProfesor));

            //EXPERIENCIA PROFESIONAL
            data.put("actual", profesorService.getOtrosTrabajosList(idProfesor));
            data.put("directivo", profesorService.getCargoDirectivoList(idProfesor));
            data.put("otros", profesorService.getOtrosCargoList(idProfesor));

            //ASOCIACIONES Y REDES A LAS QUE PERTENECE
            data.put("redes", profesorService.getRedesAcademicasList(idProfesor));
            data.put("asociaciones", profesorService.getAsociacionProfesionalList(idProfesor));
            //IDIOMAS
            data.put("idiomas", profesorService.getIdiomasList(idProfesor));
            data.put("idiomaTitulo", profesorService.getIdiomasTitulosList(idProfesor));
            //
            data.put("patentes", profesorService.getPatenteList(idProfesor));
            //
            data.put("premios", profesorService.getPremiosList(idProfesor));
            //
            data.put("otrosMeritos", profesorService.getOtrosMeritosList(idProfesor));

            data.put("tieneRegistros", true);

            if (data.get("faPregrado") == null && data.get("faPosgrado") == null && data.get("complementarios") == null
                    && data.get("camposInvestiga") == null && data.get("workingPaper") == null && data.get("tesisPregrado") == null
                    && data.get("tesisPostgrado") == null && data.get("estancias") == null && data.get("proyectos") == null
                    && data.get("consultoria") == null && data.get("ponencias") == null && data.get("reuniones") == null
                    && data.get("libros") == null && data.get("librosColectivos") == null && data.get("indexado") == null
                    && data.get("noIndexado") == null && data.get("conActa") == null && data.get("articuloCV") == null
                    && data.get("docencia") == null && data.get("actual") == null && data.get("directivo") == null
                    && data.get("otros") == null && data.get("redes") == null && data.get("asociaciones") == null
                    && data.get("idiomas") == null && data.get("idiomaTitulo") == null && data.get("patentes") == null
                    && data.get("premios") == null && data.get("otrosMeritos") == null) {
                data.put("tieneRegistros", false);
            }
        }

        return data;
    }

    @RequestMapping(value = "horario.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> horario(@RequestParam(value = "idProfesor", required = true) String idProfesorRandom) {
        Integer idProfesor = (Integer) usuarioService.getRefDirecta(idProfesorRandom);
        Map<String, Object> data = new HashMap<String, Object>();
        //inicio
        List<Map<String, Object>> asignaturaDictadaList;
        List<Map<String, Object>> listHorarioList;
        List<Map<String, Object>> listPracticaProgramadaList;


        Map<String, Object> itemAsignaturaDictada;
        Map<String, Object> itemAsignaturaSeccion;
        Map<String, Object> itemHorarioList;
        Map<String, Object> itemPracticaProgramadaList;
        asignaturaDictadaList = new ArrayList<Map<String, Object>>();
        for (AsignaturaDictada asignaturaDictada : profesorService.getHorarioList(idProfesor)) {

            itemAsignaturaDictada = new HashMap<String, Object>();
            itemAsignaturaDictada.put("id", usuarioService.addRefIndirecta(asignaturaDictada.getId()));
            itemAsignaturaDictada.put("sigla", asignaturaDictada.getSigla());
            AsignaturaSeccion asignaturaSeccion = asignaturaDictada.getAsignaturaSeccion();
            itemAsignaturaSeccion = new HashMap<String, Object>();
            itemAsignaturaSeccion.put("idSeccion", usuarioService.addRefIndirecta(asignaturaSeccion.getIdSeccion()));
            itemAsignaturaSeccion.put("nombreSeccion", asignaturaSeccion.getNombreSeccion());
            itemAsignaturaSeccion.put("descripcion", asignaturaSeccion.getDescripcion());
            itemAsignaturaDictada.put("asignaturaSeccion", itemAsignaturaSeccion);
            listHorarioList = new ArrayList<Map<String, Object>>();
            for (Horario horario : asignaturaDictada.getHorarioList()) {
                itemHorarioList = new HashMap<String, Object>();
                itemHorarioList.put("bloqueHorario", horario.getBloqueHorario());
                itemHorarioList.put("dia", horario.getDia());
                itemHorarioList.put("aula", horario.getAula());
                listHorarioList.add(itemHorarioList);
            }
            itemAsignaturaDictada.put("horarioList", listHorarioList);
            listPracticaProgramadaList = new ArrayList<Map<String, Object>>();
            for (PracticaProgramada practica : asignaturaDictada.getPracticaProgramadaList()) {
                itemPracticaProgramadaList = new HashMap<String, Object>();
                itemPracticaProgramadaList.put("bloqueHorario", practica.getBloqueHorario());
                itemPracticaProgramadaList.put("dia", practica.getDia());
                itemPracticaProgramadaList.put("grupoPractica", practica.getGrupoPractica());
                listPracticaProgramadaList.add(itemPracticaProgramadaList);
            }
            itemAsignaturaDictada.put("practicaProgramadaList", listPracticaProgramadaList);
            asignaturaDictadaList.add(itemAsignaturaDictada);
        }
        data.put("asignaturaList", asignaturaDictadaList);
        //data.put("asignaturaList", profesorService.getHorarioList(idProfesor));
        data.put("asesoriaList", profesorService.getHorarioAsesoriaList(idProfesor));
        return data;
    }
}
