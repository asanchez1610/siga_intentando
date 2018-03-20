package com.udep.siga.dao;

import com.udep.siga.bean.CategoriaConsulta;
import com.udep.siga.bean.Consulta;
import java.util.List;

/**
 *
 * @author Administrador
 */
public interface ConsultaDAO {
    public List<Consulta> getPendientes(int idalumno);
    public List<Consulta> getFinalizados (int idalumno);
    public List<CategoriaConsulta> getCategorias();
    public CategoriaConsulta getCategoriaById (int idCategoria);
    public void saveConsulta(Consulta consulta, int idAlumno);
    public void EditConsulta(Consulta consulta, int idAlumno);
}
