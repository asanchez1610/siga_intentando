package com.udep.siga.controller;

import com.udep.siga.bean.Alumno;
import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.TipoSolicitud;
import com.udep.siga.service.CommonsService;
import com.udep.siga.service.DocumentoOficialService;
import com.udep.siga.service.UsuarioService;
import com.udep.siga.util.AppConstants;
import com.udep.siga.util.Util;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 *
 * @author Wilfredo Atoche
 */
@Controller
@RequestMapping("/json/documentooficial/*")
public class DocumentoOficialController {

    @Autowired
    private DocumentoOficialService documentoOficialService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private CommonsService commonsService;

    @RequestMapping(value = "pendientesList.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getPendientesList(
            @RequestParam("idEdicionestudio") String idEdicionestudioRandom) {
        Integer idEdicionestudio = (Integer)usuarioService.getRefDirecta(idEdicionestudioRandom);
        AlumnoEstudio alumnoEstudio = (AlumnoEstudio) commonsService.getAlumnoEstudioSession(idEdicionestudio).clone();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Documentos oficiales");
//        data.put("moroso", alumnoEstudio.isMoroso());
        data.put("moroso", usuarioService.isAlumnoMoroso(alumnoEstudio));
        data.put("documentos", documentoOficialService.getPendientes(idEdicionestudio));
        return data;
    }

    @RequestMapping(value = "finalizadosList.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getFinalizadosList(
            @RequestParam("idEdicionestudio") String idEdicionestudioRandom) {
        Integer idEdicionestudio = (Integer)usuarioService.getRefDirecta(idEdicionestudioRandom);
        AlumnoEstudio alumnoEstudio = (AlumnoEstudio) commonsService.getAlumnoEstudioSession(idEdicionestudio).clone();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Documentos oficiales");
//        data.put("moroso", alumnoEstudio.isMoroso());
        data.put("moroso", usuarioService.isAlumnoMoroso(alumnoEstudio));
        data.put("documentos", documentoOficialService.getFinalizados(idEdicionestudio));
        return data;
    }

    @RequestMapping(value = "nuevoDocumento.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> nuevoDocumento(
            @RequestParam("idEdicionestudio") String idEdicionestudioRandom) {
        Integer idEdicionestudio = (Integer)usuarioService.getRefDirecta(idEdicionestudioRandom);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Nueva solicitud de DO");
        Alumno alumno = usuarioService.getInfoUsuario();
        data.put("nombres", alumno.getNombres());
        data.put("apellidos", String.format("%s %s",
                alumno.getApellidoPaterno(), alumno.getApellidoMaterno()));
        data.put("carne", alumno.getCarne());
        AlumnoEstudio alumnoEstudio = commonsService.getAlumnoEstudioSession(idEdicionestudio);
        data.put("pa", alumnoEstudio.getEdicionestudio().getEstudio().getNombre());
        List<TipoSolicitud> tipoSolicitudList = documentoOficialService
                .getTipoDocOficialesList(alumnoEstudio.getCampus().getId());
        data.put("tipoSolicitudList", tipoSolicitudList);
        return data;
    }

    @RequestMapping(value = "saveNuevoDocumento.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> saveNuevoDocumento(
            @RequestParam("tipo") Integer idTipoDocumento, @RequestParam("campus") Integer idCampus,
            @RequestParam("idioma") Integer idIdioma, @RequestParam("detalle") String detalle,
            @RequestParam("idedicionestudio") String idEdicionestudioRandom) {
        Integer idEdicionestudio = (Integer)usuarioService.getRefDirecta(idEdicionestudioRandom);
        Map<String, Object> data = new HashMap<String, Object>();
        List<String> errores = new ArrayList<String>();
        if (idTipoDocumento == null) {
            errores.add("Se requiere un tipo de documento.");
        }
        if (idCampus == null) {
            errores.add("Se requiere un campus de entrega.");
        }
        if (idIdioma == null) {
            errores.add("Se requiere un idioma.");
        }
        if (detalle == null) {
            errores.add("El detalle es requerido para la solicitud.");
        } else {
            if (detalle.isEmpty()) {
                errores.add("Ingrese el detalle de la solicitud.");
            }
        }
        data.put("docId", usuarioService.addRefIndirecta(0));
        if (errores.isEmpty()) {
            int idDocumento = documentoOficialService.saveDocumentoOficial(idTipoDocumento,
                    idCampus, idIdioma, detalle, idEdicionestudio);
            data.put("docId", usuarioService.addRefIndirecta(idDocumento));
        }
        data.put("errores", errores);
        return data;
    }

    @RequestMapping(value = "uploadVoucher.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> uploadVoucher(@RequestParam("voucher") CommonsMultipartFile voucher,
        @RequestParam("id") String idRandom) {
        Integer id = (Integer)usuarioService.getRefDirecta(idRandom);
        
        Map<String, Object> data = new HashMap<String, Object>();        
        Alumno alumno = usuarioService.getInfoUsuario();
        List<String> errores = new ArrayList<String>();
        FileOutputStream outputStream = null;
        try {
            String pathFolder = String.format("%s%s", AppConstants.PATH_VOUCHER,
                    alumno.getId());
            File carpeta = new File(pathFolder);
            if (!carpeta.exists()){
                carpeta.mkdirs();
            }
            
            String pathFile = String.format("%s/%s", pathFolder, voucher.getOriginalFilename());
            outputStream = new FileOutputStream(new File(pathFile));
            outputStream.write(voucher.getFileItem().get());
            outputStream.close();
            
            documentoOficialService.saveVoucher(id, pathFile);
        } catch (Exception e) {
            data.put("resultado", "error");
            errores.add("No se pudo subir el archivo. Intenta nuevamente.");            
            data.put("errores", errores);
            return data;
        }
        data.put("resultado", "ok");
        return data;
    }
    
    @RequestMapping(value = "downloadvoucher/{id}", method = RequestMethod.GET)
    public void download(@PathVariable(value = "id") String idRandom,
            HttpServletResponse response) {
        try {
            String id = (String)usuarioService.getRefDirecta(idRandom);
            Util.getDownload(id, id, response);
        } catch (Exception e) {
            System.out.println("Error al descargar documento: " + e.getMessage());
        }
    }
}
