/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.udep.security;

import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.Usuario;
import com.udep.siga.bean.enumeration.Configuracion;
import com.udep.siga.bean.enumeration.EstadoAlumno;
import com.udep.siga.dao.AlumnoDAO;
import com.udep.siga.dao.AlumnoEstudioDAO;
import com.udep.siga.dao.ConfiguracionDAO;
import com.udep.siga.dao.UsuarioDAO;
import com.udep.siga.service.CommonsService;
import com.udep.siga.service.PensionService;
import com.udep.siga.util.Rol;
import edu.udep.siga.auth.CustomAuthProvider;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author lucinda.silva
 */
public class CustomClientAuthProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private CustomAuthProvider customAuthProvider;
    @Autowired
    private UsuarioDAO usuarioDAO;
    @Autowired
    private AlumnoDAO alumnoDAO;
    @Autowired
    private AlumnoEstudioDAO alumnoEstudioDAO;
    @Autowired
    private ConfiguracionDAO configuracionDAO;
    @Autowired
    private CommonsService commonsService;
    @Autowired
    private PensionService pensionService;

    private static final Logger log = Logger.getLogger(CustomClientAuthProvider.class);

    private UserDetails verify(UserDetails user) {
        String username = user.getUsername();
        log.info(String.format("Verificando acceso...%s", username));

        Usuario usuario = usuarioDAO.getUsuarioByLogin(username);
        if (usuario != null) {
            Map<String,Object> bloqueo = customAuthProvider.bloqueoUsuario(usuario.getId());
            
            if(bloqueo != null){
                if((Boolean) bloqueo.get("bloqueo")){
                    throw new BadCredentialsException( bloqueo.get("mensaje").toString());
                }
            }
            log.info(String.format("Iniciando sesión como %s", usuario.getLogin()));

            /*   IMPORTANTE!
             *   Se usa un SP (VERICA_ESTUDIO_PARABLOQUEO) para validar si el alumno tiene algún estudio que requiera el pago de matricula para acceder a SIGA;
             *   los ID's de los estudios se encuentran en ese SP.
             */
            if (configuracionDAO.getConfiguracion(Configuracion.BLOQUEO_MATRICULA).toUpperCase().equals("SI")) {
                if (alumnoDAO.verificaMatricula(usuario.getId())) {
                    List<AlumnoEstudio> alumnoEstudioList = alumnoEstudioDAO.getEstudiosByAlumno(usuario.getId(), EstadoAlumno.Alumno);
                    if (alumnoEstudioList != null && !alumnoEstudioList.isEmpty()) {
                        for (AlumnoEstudio alumoEstudio : alumnoEstudioList) {
                            if (pensionService.tieneDeudaMatricula(alumoEstudio.getCampus(), alumoEstudio.getCarne(), alumoEstudio.getEdicionestudio().getId())) {
                                System.out.println(String.format("Se bloqueado el acceso al usuario %s por haber incumplido con el pago de su matrícula", usuario.getLogin()));
                                throw new BadCredentialsException(commonsService.getRecursoMensaje("matricula.deuda", null));
                            }
                        }
                    }
                }
            }

            Set<GrantedAuthority> authorities;
            authorities = this.verifyRoles(usuario);

            if (usuario.isPregrado()) {
                boolean completoEnc = alumnoDAO.completoEncuesta(usuario.getId());
                if (!completoEnc) {
                    throw new BadCredentialsException(commonsService.getRecursoMensaje("encuestas.login", null));
                }
            }

            return new User(usuario.getLogin(), user.getPassword(), usuario.isActivo(), true, true, true, authorities);
        } else {
            throw new BadCredentialsException("Los datos ingresados son incorrectos.");
        }
    }

    private Set<GrantedAuthority> verifyRoles(Usuario usuario) {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

        if (usuario.isPregrado()) {
            authorities.add(new SimpleGrantedAuthority(Rol.ALUMNO_PREGRADO.getRol()));
            System.out.println(String.format("Rol: %s", Rol.ALUMNO_PREGRADO.toString()));
        }
        if (usuario.isPosgrado()) {
            authorities.add(new SimpleGrantedAuthority(Rol.ALUMNO_POSGRADO.getRol()));
            System.out.println(String.format("Rol: %s", Rol.ALUMNO_POSGRADO.toString()));
        }

        if (authorities.isEmpty()) {
            if (usuario.isExEgPregrado()) {
                authorities.add(new SimpleGrantedAuthority(Rol.EX_EG_PREGRADO.getRol()));
                System.out.println(String.format("Rol: %s", Rol.EX_EG_PREGRADO.toString()));
            }

            if (usuario.isExEgPosgrado()) {
                authorities.add(new SimpleGrantedAuthority(Rol.EX_EG_POSGRADO.getRol()));
                System.out.println(String.format("Rol: %s", Rol.EX_EG_POSGRADO.toString()));
            }

            if (authorities.isEmpty()) {
                throw new BadCredentialsException("El usuario no tiene roles asignados.");
            }
        }
        return authorities;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails ud, UsernamePasswordAuthenticationToken upat) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String string, UsernamePasswordAuthenticationToken upat) throws AuthenticationException {
        System.out.println(upat.getCredentials().toString());
    	UserDetails currentUser = customAuthProvider.auth(string, upat);
        return verify(currentUser);
    }

}
