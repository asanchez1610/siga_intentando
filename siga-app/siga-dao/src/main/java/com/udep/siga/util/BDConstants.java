/*
 * Copyright (c) 2014, Universidad de Piura. All rights reserved.
 * UDEP PROPRIETARY/CONFIDENTIAL.
 *
 */
package com.udep.siga.util;

/**
 *
 * @author Jose Chero Sojo
 */
public final class BDConstants {
    //SP's
    public static final String SP_GET_AVISOS          = "S2_PROC_V_AVISOS_ALUMNO";
    public static final String SP_GET_ACTIVA_SELECC_ESPEC_ALUMNO            = "ESP_ACTIVA_SELECCION_ESPECIALIDAD_ALUMNO";
    public static final String SP_GET_AREASCONOCIMIENTO_BYESPECIALIDAD      = "ESP_AREASCONOCIMIENTO_BYESPECIALIDAD";
    public static final String SP_GET_AREASCONOCIMIENTO_BYIDESPECIALIDAD    = "ESP_AREASCONOCIMIENTO_BYIDESPECIALIDAD";
    public static final String SP_GET_AREASCONOCIMIENTO_BYPLANESTUDIOS      = "ESP_AREASCONOCIMIENTO_BYPLANESTUDIOS_ESP_AL";
    public static final String SP_GET_ASIGNATURAS_BYESPECIALIDAD_AC         = "ESP_ASIGNATURAS_BYESPECIALIDAD_AREACONOCIMIENTO";
    public static final String SP_GET_ASIGNATURAS_BYPLANESTUDIO_AC          = "ESP_ASIGNATURAS_BYPLANESTUDIO_AREASCONOCIMIENTO";
    public static final String SP_GET_ESPECIALIDAD_BYALUMNO                 = "ESP_ESPECIALIDAD_BYALUMNO";
    public static final String SP_GET_ESPECIALIDADES_BYPLANESTUDIO          = "ESP_ESPECIALIDADES_PLANESTUDIOS";
    public static final String SP_REGISTRAR_ESPECIALIDADBYALUMNO            = "ESP_REGISTRAR_ESPECIALIDADBYALUMNO";
    public static final String SP_GET_ASIGNATURAS_ENCUESTA                  = "ASIGNATURAS_PARA_ENCUESTA";
    public static final String SP_GUARDAR_ASIGNATURAS_ENCUESTA              = "ENC_GUARDAR_ASIGNATURAS";
    public static final String SP_REQUISITOPLANESTUDIOS                     = "S2_PROC_V_REQUISITOPLANESTUDIOS_BYIDALUMNOESTUDIO";
    public static final String SP_REQUISITOTITULO                           = "S2_PROC_V_REQUISITOSTITULO_BYIDALUMNOESTUDIO"; 
    public static final String SP_REQUISITOPLANESTUDIOS_BYASIGNATURA        = "S2_PROC_V_REQUISITOPLANESTUDIOS_BYASIGNATURA"; 
    
    public static final String SP_ASIGNATURASDICTADAS_BYALUMNO_PERIODO_EE   = "S2_PROC_V_ASIGNATURADICTADA_BYALUMNO_PERIODO_EE";
    public static final String SP_HORARIOEVENTO                             ="S2_PROC_V_PERIODOACADEMICO_PREGRADO_HORARIOEVENTO";
    public static final String SP_LISTAHORARIOEVENTO                       	="S2_PROC_V_HORARIO_ALUMNO_EVENTO";
    public static final String SP_LISTAHORARIOFECHAS                       	="S2_PROC_V_HORARIO_DIASSEMANA";
    public static final String SP_GET_AMBIENTES_DISPONIBLES					="S2_AMBIENTES_DISPONIBLES_X_RANGOHORARIO_PERIODOACADEMICO_CAMPUS"; 
     
    //UDF's
     public static final String FN_CHECK_LLENARENCUESTA                     = "SELECT dbo.CHECK_LLENARENCUESTA(?,?,?)";
    public static final String FN_CHECK_HORARIOCALENDARIZADO                = "SELECT dbo.S2_UDF_V_HORARIO_EVENTO_CAMPUS_PERSONA(?,?)";
    public static final String FN_CHECK_ESPECIALIDADALUMNOESTUDIO           = "SELECT dbo.ESP_CHECK_ESPECIALIDADALUMNOESTUDIO(?, ?)";
    public static final String FN_GET_IDPLANESTUDIO_BYALUMNOESTUDIO         = "SELECT dbo.ESP_GET_PLANESTUDIO_ACTIVO_BYALUMNOEDICIONESTUDIO(?, ?)";
    public static final String FN_GET_ESTADO_ASIGNATURA                     = "SELECT dbo.S2_UDF_GET_ESTADO_ASIGNATURA(?, ?, ?, ?, ?, ?)";
}
