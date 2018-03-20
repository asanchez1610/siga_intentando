package com.udep.siga.controller;

import com.udep.siga.bean.Silabo;
import com.udep.siga.service.AsignaturaDictadaService;
import com.udep.siga.service.UsuarioService;
import com.udep.siga.util.UtilReplacePlantilla;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Wilfredo Atoche
 */
@Controller
@RequestMapping("/silabo/*")
public class SilaboController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private AsignaturaDictadaService asignaturaDictadaService;
    /*
     * Página de Ver Silabus
     */
    @RequestMapping(value = "ver.htm", method = RequestMethod.GET)
    public ModelAndView ver(@RequestParam(value = "id", required = true) String idRandom,
        @RequestParam(value = "idee", required = true) String idEdicionestudioRandom){
        Integer id = (Integer) usuarioService.getRefDirecta(idRandom);
        Integer idEdicionestudio = (Integer) usuarioService.getRefDirecta(idEdicionestudioRandom);

        Silabo silabo = asignaturaDictadaService.getSilaboByAsignaturaDictada(id, idEdicionestudio);
        
        ModelAndView mav = new ModelAndView(); 
        mav.setViewName("silabo.view");
        
        mav.addObject("silabo", silabo);
        
        return mav;
    }
    
    @RequestMapping(value = "descargaSilaboToPDF.htm", method = RequestMethod.GET)
    public void
            descargaSilaboToPDF(@RequestParam(value = "id", required = true) String idRandom,
            @RequestParam(value = "idee", required = true) String idEdicionestudioRandom,
            HttpServletResponse response) {
        try {
            Integer id = (Integer) usuarioService.getRefDirecta(idRandom);
            Integer idEdicionestudio = (Integer) usuarioService.getRefDirecta(idEdicionestudioRandom);
            
            Silabo silabo = asignaturaDictadaService.getSilaboByAsignaturaDictada(id, idEdicionestudio);
            silabo.setCentros(asignaturaDictadaService.getCentrosBySilabo(silabo.getId()));
            silabo.setProgramas(asignaturaDictadaService.getProgramasBySilabo(silabo.getId()));
        
            UtilReplacePlantilla utils = new UtilReplacePlantilla();
            utils.generaSilaboToPdf(silabo, response);  
        } catch (Exception e) {
            
            System.out.println("Error al descargar silabo: " + e.getMessage());
        }
    }
}
