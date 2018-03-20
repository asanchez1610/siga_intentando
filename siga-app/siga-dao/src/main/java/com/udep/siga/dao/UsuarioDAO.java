package com.udep.siga.dao;

import com.udep.siga.bean.DatoPersonal;
import com.udep.siga.bean.Departamento;
import com.udep.siga.bean.Distrito;
import com.udep.siga.bean.Persona;
import com.udep.siga.bean.Provincia;
import com.udep.siga.bean.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Wilfredo Atoche
 */
public interface UsuarioDAO {    
    public Usuario getUsuarioByLogin(String login);
    public String getPasswordGeneral();
    public Persona getPersonaById(int id);
    public Boolean isAsistente(int id,int idasignatura,int idseccion);
    public Integer getIdProfesor(int id,int idasignatura,int idseccion);
    public DatoPersonal getDatoPersonalById(int id);
    public Distrito loadProvinciaDepartamentoByIdDistrito(int idDistrito);
    public List<Distrito> getDistritoList(int idProvincia);
    public List<Provincia> getProvinciaList(int idDepartamento);
    public List<Departamento> getDepartamentoList();
    public ArrayList<String> updatePassword(String login, String pass, String newPass, String repeatNewPass);
    
}
