package com.udep.siga.controller;

import com.udep.siga.bean.DocumentoInfoCateg;
import com.udep.siga.service.DocumentoInfoService;
import com.udep.siga.service.UsuarioService;
import com.udep.siga.util.Util;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Wilfredo Atoche
 */
@Controller
@RequestMapping("/json/universidad/*")
public class UniversidadController {
    
    @Autowired
    private DocumentoInfoService documentoInfoService;
    @Autowired
    private UsuarioService usuarioService;
    
    @RequestMapping(value = "documentoList.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> documentoList() {
        Map<String, Object> data = new HashMap<String, Object>();              
        List<DocumentoInfoCateg> list = documentoInfoService.getCategoriaList();
        
        data.put("title", "Universidad");
        data.put("categoriaList", list);
        return data;
    }
    
    @RequestMapping(value = "download/{id}", method = RequestMethod.GET)
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
