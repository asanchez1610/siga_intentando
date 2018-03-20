package com.udep.siga.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Wilfredo Atoche
 */
@Controller
public class PublicController {
    /*
     * Pantalla de logueo
     */
    @RequestMapping(value = "/login.htm", method = RequestMethod.GET)
    public ModelAndView login() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        ModelAndView mav = new ModelAndView();
                
        mav.setViewName("login");

        return mav;
    }
      
    /*
     * Página de Error general
     */
    @RequestMapping(value = "/error.htm", method = RequestMethod.GET)
    public ModelAndView pageError() {
        ModelAndView mav = new ModelAndView();        
        mav.setViewName("error");
        return mav;
    }
}
