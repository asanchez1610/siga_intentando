package com.udep.siga.controller;

import com.udep.siga.bean.Aviso;
import com.udep.siga.bean.Destacado;
import com.udep.siga.service.AvisoService;
import com.udep.siga.service.UsuarioService;
import com.udep.siga.util.SessionConstants;
import com.udep.siga.util.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
@RequestMapping("/json/aviso/*")
public class AvisoController {

    @Autowired
    private AvisoService avisoService;
    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping(value = "avisosList.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getAvisosList() {
        Map<String, Object> data = new HashMap<String, Object>();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> item;
        for (Aviso aviso : avisoService.getAvisos(true, 0)) {
            item = new HashMap<String, Object>();
            item.put("id", usuarioService.addRefIndirecta(aviso.getId()));
            item.put("persona", aviso.getPersona().getApellidoPaterno() + ", " + aviso.getPersona().getNombres());
            item.put("titulo", aviso.getTitulo());
            item.put("descripcion", aviso.getDescripcion());
            item.put("fecha", Util.dateToString(aviso.getFecha()));
            item.put("caducidad", aviso.getCaducidad());
            item.put("general", aviso.isGeneral());
            item.put("rutaArchivo", aviso.getRutaArchivo());
            item.put("paraAlumnos", aviso.isParaAlumnos());
            item.put("paraProfesores", aviso.isParaProfesores());
            item.put("sticky", aviso.isStick());
            list.add(item);
        }

        data.put("title", "Avisos");
        data.put("avisos", list);
        return data;
    }

    @RequestMapping(value = "destacado.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getDestacado(HttpServletRequest request) {
        Map<String, Object> data;
        
        HttpSession httpSession = request.getSession();
        if (httpSession.getAttribute(SessionConstants.DESTACADO) == null) {
            data = new HashMap<String, Object>();
            data.put("title", "Destacado");
            data.put("contenido", "");
            data.put("enlace", "");
            Destacado destacado = avisoService.getDestacado();
            if (destacado != null) {
                data.put("contenido", destacado.getHtml());
                data.put("enlace", destacado.getEnlace());
            }
            httpSession.setAttribute(SessionConstants.DESTACADO, data);
        } else {
            data = (Map<String, Object>)httpSession.getAttribute(SessionConstants.DESTACADO);
        }
        
        return data;
    }

    @RequestMapping(value = "download.htm")
    public void getDownload(@RequestParam(value = "id", required = true) String idRandom,
            HttpServletResponse response) {
        Integer id = (Integer) usuarioService.getRefDirecta(idRandom);
        try {
            String ruta = avisoService.getRutaDownload(id);
            Util.getDownload(ruta, ruta, response);
        } catch (Exception e) {
            System.out.println("Error al descargar aviso: " + e.getMessage());
        }
    }
    
    @RequestMapping(value = "mensajeEleccionEspecialidad.json", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getMensajeEleccionEspecialidad(){
        Map<String, Object> data = new HashMap<String, Object>();
        if (usuarioService.getGeneralSession(SessionConstants.MENSAJE) != null){
            data.put("mensaje", usuarioService.getGeneralSession(SessionConstants.MENSAJE));
            usuarioService.removeGeneralSession(SessionConstants.MENSAJE);
        }
        
        return data;
    }
}
