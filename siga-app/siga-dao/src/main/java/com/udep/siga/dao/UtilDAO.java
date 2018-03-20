package com.udep.siga.dao;

import com.udep.siga.bean.CentroAcademico;
import com.udep.siga.bean.Departamento;
import com.udep.siga.bean.Distrito;
import com.udep.siga.bean.HorarioEvento;
import com.udep.siga.bean.Provincia;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Wilfredo Atoche
 */
public interface UtilDAO {

    public List<Distrito> getDistritoList(int idProvincia);

    public List<Provincia> getProvinciaList(int idDepartamento);

    public List<Departamento> getDepartamentoList();

    public List<CentroAcademico> getCentroAcademicoList(int idCampus);

    public boolean getContieneEspaciosEnBlanco(String pass);

    public boolean getContieneNumeros(String pass);

    public boolean getContieneMinusculas(String pass);

    public boolean getContieneMayusculas(String pass);

    public String getNewFormatFecha(String fecha);

    public String getAnio(String fecha);

    public String getNewFormatFecha(int dia, int mes, int anio,boolean SimpleDateFormat);
    
    public String getFechaActual();
    
    public short getSemestreSigaToArgus(int idCampus, int idPeriodoAcademico);

    public boolean isHorarioevento(int idalumno);

    public List<HorarioEvento> getHorarioeventoList(int idalumno,String fecha,int idperiodoacademico);

    public List<String> getHorarioFechasList(String string);
}
