package com.udep.siga.util;

/**
 *
 * @author Wilfredo Atoche
 */
public final class AppConstants {
    
    /* Complemento de Login */
    public static final String LOGIN_PREGRADO = "@pregrado";
    public static final String LOGIN_POSGRADO = "@postgrado";
    
    /* Variables para el envio de correo */
    public static final String MAIL_FROM = "no-reply@udep.pe";
    public static final String MAIL_SIGA = "siga@udep.pe";
    public static final boolean MAIL_ACTIVO = true;        
    public static final String MAIL_URL_FROM = "https://siga.udep.edu.pe";
    public static final String MAIL_FINAL_TEXT = "\n\n\n\nNota: Mensaje automático, por favor no responder. ";
    
    /* Contenido para el envio de mensajes Asesor */
    public static final String MAIL_SUBJECT_ASESOR = "Tiene un nuevo mensaje por "
            + "revisar en la bandeja de entrada de asesoramiento";
    public static final String MAIL_BODY_ASESOR = "De: %s %s %s %s\nAsunto: %s \n\nMensaje:\n\n %s ";
    
    /* Contenido para el envio de mensajes por reclamos,consultas y/o sugerencias */
    public static final String MAIL_SUBJECT_CONSULTA = "Tiene un(a) nuevo(a) %s registrado(a).";
    public static final String MAIL_BODY_CONSULTA = "De: %s %s %s \n %s: %s";
    
    /* Contenido para el envio de mensajes por auditoria */
    public static final String MAIL_SUBJECT_AUDITORIA = "SIGA";
    public static final String MAIL_BODY_AUDITORIA = "Estimado alumno: %s %s %s \n\nNuestros servidores "
            + "han recibo consultas de acceso a información a través de la modificación de parámetros en la "
            + "dirección de siga (https://siga.udep.edu.pe). Estas consultas se han producido desde su cuenta, "
            + "por lo que le sugerimos utilizar las opciones del sistema para visualizar su información.\n\n"
            + "Si tuviera alguna consulta o dificultad para visualizar su información por favor comunicarse con siga@udep.pe.";
    
    /* Contenido para el envio de mensajes por fechas cuotas pensiones especiales */
    public static final String MAIL_SUBJECT_PAGO_ESPECIAL = "Constancia - Cronograma de Fechas de Pagos Especiales";
    public static final String MAIL_BODY_PAGO_ESPECIAL = "\nUNIVERSIDAD DE PIURA \n\nAlumno: %s %s %s \nCarné: %s "
            + "\nFecha/Hora: %s \n\nLe Confirmamos sus Fechas de Pago para %s \n %s \n\nSi efectuó "
            + "esta solicitud antes de las 5 p.m. considere este cambio como \"realizado\" al siguiente día hábil. "
            + "Si efectuó esta solicitud después de las 5 p.m. considere este cambio como \"realizado\" al 2do.día hábil. "
            + "Si efectuó esta solicitud después de las 5 p.m. del día viernes, considere este cambio como \"realizado\" el martes próximo.";	
    
    /* Carga de Archivo de Archivos */
    public static final String PATH_VOUCHER = "/vouchers/";
    
    /* Cantidad de Dias para resumen - Alumno */
    public static final int RESUMEN_DIAS = 3;
    
    /*Ruta para acceder a las fotos personal*/
    public static final String RUTA_FOTO ="https://sigadocentes.udep.edu.pe/fotossiga/persona/";
    
    /* Plazo de Acceso para ExAlumno y Egresados */
    public static final int PLAZO_ACCESO_EG_EX_DIAS = 60;
    
    /* Archivo  Pagos Especiales - Pensiones */    
    public static final String MAIL_PENSIONES_PIURA = "pensiones@udep.pe";
    public static final String MAIL_PENSIONES_LIMA = "pensiones.lima@udep.pe";
    public static final String PATH_PAGO_ESPECIAL = "/opt/jboss-as-7.1.1.Alumno/plantilla/pagos_especiales.docx";    
    
    /* Archivo  fotos default */
    public static final String PATH_FOTO_MASCULINO_DEFAULT = "user_male.png";
    public static final String PATH_FOTO_FEMENINO_DEFAULT = "user_female.png";
    
    /* Archivo  Historial - alumno */    
    public static final String PATH_HISTORIAL_ALUMNO_TO_PDF = "/opt/jboss-as-7.1.1.Alumno/plantilla/HistorialNotasAlumno.docx";      
    //public static final String PATH_HISTORIAL_ALUMNO_TO_PDF = "H:/andysigaalumno/plantilla/HistorialNotasAlumno.docx"; 
    /* Archivo Generacion de Recibo Electronico*/
    //public static final String PATH_RECIBO_ALUMNO_TO_PDF = "H:/andysigaalumno/plantilla/ReciboElectronico.docx";
    public static final String PATH_RECIBO_ALUMNO_TO_PDF = "/opt/jboss-as-7.1.1.Alumno/plantilla/ReciboElectronico.docx";
    
    /* Datos - generación automática de doc. */    
    public static final String FOOTER_DOCS = "Generado automáticamente por SIGA "
            + "(Sistema Integrado de Gestión Académica) el %s ";
    
    /* Archivo  Sílabo */  
    public static final String PATH_SILABO_TO_PDF = "/opt/jboss-as-7.1.1.Alumno/plantilla/silabo.docx"; 
   // public static final String PATH_SILABO_TO_PDF = "H:/andysigaalumno/plantilla/silabo.docx"; 
    
    /* IP Servidor Web Service */
//    public static final String SERVIDOR_WEB_SERVICE_URL = "http://movil.udep.edu.pe/webservice/"; 
    public static final String SERVIDOR_WEB_SERVICE_URL = "http://172.30.20.224:8180/webservice/";
}
