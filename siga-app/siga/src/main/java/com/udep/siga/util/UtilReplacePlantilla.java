package com.udep.siga.util;

import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.AlumnoEstudioPeriodoAcademico;
import com.udep.siga.bean.AsignaturaHistorial;
import com.udep.siga.bean.EstrategiaSilabo;
import com.udep.siga.bean.Evaluacion;
import com.udep.siga.bean.FechaCuotaPagoEspecial;
import com.udep.siga.bean.ObjetivoSilabo;
import com.udep.siga.bean.Silabo;
import com.udep.siga.bean.TipoEvaluacion;
import com.udep.siga.bean.enumeration.Campus;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import org.docx4j.convert.out.pdf.PdfConversion;
import org.docx4j.convert.out.pdf.viaXSLFO.Conversion;
import org.docx4j.convert.out.pdf.viaXSLFO.PdfSettings;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.ObjectFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Administrador
 */
public class UtilReplacePlantilla {

    public void generaDocPagoEspecial(AlumnoEstudio alumnoEstudio, List<FechaCuotaPagoEspecial> PagoEspecialList,
            HttpServletResponse response) {
        try {
            UtilGenerateDocs utils = new UtilGenerateDocs();
            Util util = new Util();
            WordprocessingMLPackage wordprocessingMLPackage = utils.getTemplate(AppConstants.PATH_PAGO_ESPECIAL);

            /*
             * pie de página
             */            
            ObjectFactory factory = Context.getWmlObjectFactory();
            String footer = String.format(AppConstants.FOOTER_DOCS,util.getFechaActual());
            Relationship relationship = utils.createFooterPart(wordprocessingMLPackage,factory,footer);
            utils.createFooterReference(wordprocessingMLPackage,relationship,factory);
            
            /**
             * parametros
             *
             * @periodo - periodo academico actual
             * @fecha - fecha actual
             * @carne - carné del alumno
             * @alumno - nombres y apellidos del alumno
             */
            utils.replacePlaceholder(wordprocessingMLPackage, alumnoEstudio.getPeriodoAcademicoVigente()
                    .getNombre(), "@periodo");
            utils.replacePlaceholder(wordprocessingMLPackage, util.getFechaActual(), "@fecha");
            utils.replacePlaceholder(wordprocessingMLPackage, alumnoEstudio.getCarne(), "@carne");

            if (alumnoEstudio.getAlumno() != null) {
                utils.replacePlaceholder(wordprocessingMLPackage, alumnoEstudio.getAlumno().getNombres() + " "
                        + alumnoEstudio.getAlumno().getApellidoPaterno() + " " + alumnoEstudio.getAlumno()
                        .getApellidoMaterno(), "@alumno");
            } else {
                utils.replacePlaceholder(wordprocessingMLPackage, "", "@alumno");
            }

            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            Map<String, String> item;
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
            for (FechaCuotaPagoEspecial fcpe : PagoEspecialList) {
                item = new HashMap<String, String>();
                item.put("@cuota", "Cuota N°" + fcpe.getCuota());
                item.put("@pago", sdf.format(fcpe.getFecha()));
                list.add(item);
            }
            utils.replaceTable(new String[]{"@cuota", "@pago"}, list, wordprocessingMLPackage);

            File file = File.createTempFile("wordexport-", ".docx");
            wordprocessingMLPackage.save(file);
            Util.getDownload(response, file, "Pagos_Especiales.docx", "Word");
        } catch (Docx4JException ex) {
            System.out.println("Docx4JException - " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("IOException - " + ex.getMessage());
        } catch (JAXBException ex) {
            System.out.println("JAXBException - " + ex.getMessage());
        }
    }

    public void generaHistorialToPdf(AlumnoEstudio alumnoEstudio, List<AlumnoEstudioPeriodoAcademico> historial,
            HttpServletResponse response) {
        try {
            UtilGenerateDocs utils = new UtilGenerateDocs();
            WordprocessingMLPackage wordprocessingMLPackage = utils.getTemplate(AppConstants.PATH_HISTORIAL_ALUMNO_TO_PDF);

            /**
             * parametros
             *
             * @facultad - facultad a la que pertenece.
             * @pa - programa academico del alumno.
             * @alumno - nombres y apellidos del alumno.
             * @carne - carné del alumno.
             * @sigla - sigla de una asignatura.
             * @asignatura - nombre de una asignatura.
             * @plan - plan de estudios a la que pertenece una asignatura.
             * @tipo - tipo de asignatura.
             * @credito - creditos de una asignatura.
             * @periodo - periodo academico en el que cursó una asignatura.
             * @nota - promedio final.
             */
            utils.replacePlaceholder(wordprocessingMLPackage, alumnoEstudio.getEdicionestudio().getEstudio()
                    .getCentroAcademico().getNombre(), "@facultad");
            utils.replacePlaceholder(wordprocessingMLPackage, alumnoEstudio.getEdicionestudio().getEstudio()
                    .getNombre(), "@acad");
            if (alumnoEstudio.getAlumno() != null) {
                utils.replacePlaceholder(wordprocessingMLPackage, alumnoEstudio.getAlumno().getNombres() + " "
                        + alumnoEstudio.getAlumno().getApellidoPaterno() + " " + alumnoEstudio.getAlumno()
                        .getApellidoMaterno(), "@alumno");
            } else {
                utils.replacePlaceholder(wordprocessingMLPackage, "", "@alumno");
            }
            utils.replacePlaceholder(wordprocessingMLPackage, alumnoEstudio.getCarne(), "@carne");

            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            Map<String, String> item;
            for (AlumnoEstudioPeriodoAcademico aepa : historial) {
                for (AsignaturaHistorial asig : aepa.getAsignaturaDictadaList()) {
                    item = new HashMap<String, String>();
                    item.put("@sigla", asig.getSigla());
                    item.put("@asignatura", asig.getNombre());
                    item.put("@plan", asig.getEstudio() + " - " + asig.getPlanEstudio());
                    item.put("@tipo", asig.getTipoAsignatura());
                    item.put("@numero", "" + asig.getCreditos());
                    item.put("@periodo", aepa.getPeriodoAcademico().getNombre());
                    if(asig.isRetiroCurso())
                    {
                    item.put("@nota", "Retiro de Curso");   
                    }
                    else
                    {
                     if(asig.isAnulaCiclo())
                     item.put("@nota", "Ciclo Anulado"); 
                     else
                     item.put("@nota", asig.getPromedio().getDescripcion());
                    }
                    
                    list.add(item);
                }
            }
            utils.replaceTable(new String[]{"@sigla", "@asignatura", "@plan", "@tipo", "@numero", 
                "@periodo", "@nota"}, list, wordprocessingMLPackage);

            PdfSettings pdfSettings = new PdfSettings();
            PdfConversion c = new Conversion(wordprocessingMLPackage);
            File file = File.createTempFile("pdfexport-", ".pdf");
            wordprocessingMLPackage.save(file);
            OutputStream out = new FileOutputStream(file);
            c.output(out, pdfSettings);
            Util.getDownload(response, file, "Historial.pdf", "pdf"); 
            out.close();
            
        } catch (Docx4JException ex) {
            System.out.println("Docx4JException - " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("IOException - " + ex.getMessage());
        } catch (JAXBException ex) {
            System.out.println("JAXBException - " + ex.getMessage());
        }
    }
    
    public void generaReportePensionesToPdf(Map<String, Object> valores,
            HttpServletResponse response,AlumnoEstudio alumnoEstudio) {
        try {
            UtilGenerateDocs utils = new UtilGenerateDocs();
            WordprocessingMLPackage wordprocessingMLPackage = utils.getTemplate(AppConstants.PATH_RECIBO_ALUMNO_TO_PDF );
             String direccionPiura="Piura: Av.Ramón Mugica 131,Urb. San Eduardo. T(073)284500";
             String cuentaBCPPiura="475-1082707-0-11";
             String cuentaBBVAPiura="0011-0278-0200002860";
             String direccionLima="Lima: Calle Mártir José Olaya 162, Miraflores. T(01)2139600";
             String cuentaBCPLima="475-1475434-0-51";
             String cuentaBBVALima="0011-0661-0100045375-61";
              if (alumnoEstudio.getCampus() == Campus.Piura) {
              utils.replacePlaceholder(wordprocessingMLPackage,direccionPiura, "@direccion");
              utils.replacePlaceholder(wordprocessingMLPackage,cuentaBCPPiura, "@cuentabcp");
              utils.replacePlaceholder(wordprocessingMLPackage,cuentaBBVAPiura, "@cuentabbva");
            } else {
             utils.replacePlaceholder(wordprocessingMLPackage,direccionLima, "@direccion");
             utils.replacePlaceholder(wordprocessingMLPackage,cuentaBCPLima, "@cuentabcp");
             utils.replacePlaceholder(wordprocessingMLPackage,cuentaBBVALima, "@cuentabbva");
            }
            utils.replacePlaceholder(wordprocessingMLPackage,alumnoEstudio.getEdicionestudio().getEstudio().getNombre(), "@programa");
            utils.replacePlaceholder(wordprocessingMLPackage, valores.get("facultad").toString(), "@facultad");
            utils.replacePlaceholder(wordprocessingMLPackage," "+valores.get("semestre").toString(), "@periodo");
             utils.replacePlaceholder(wordprocessingMLPackage, valores.get("sede").toString(), "@sede");
            utils.replacePlaceholder(wordprocessingMLPackage,valores.get("valor_cuota").toString(), "@valorcuota");
            utils.replacePlaceholder(wordprocessingMLPackage,valores.get("total").toString(), "@total");
             utils.replacePlaceholder(wordprocessingMLPackage,valores.get("descripcion").toString(), "@descripcion");
             utils.replacePlaceholder(wordprocessingMLPackage,valores.get("alumno").toString(), "@alumno");
             utils.replacePlaceholder(wordprocessingMLPackage,valores.get("cuota").toString(), "@cuota");
            utils.replacePlaceholder(wordprocessingMLPackage,valores.get("dni").toString(), "@carne");
             utils.replacePlaceholder(wordprocessingMLPackage,valores.get("num_recibo").toString(), "@numero");
             utils.replacePlaceholder(wordprocessingMLPackage,valores.get("fechaemision").toString(), "@fechaemision");
             utils.replacePlaceholder(wordprocessingMLPackage,valores.get("fechavencim").toString(), "@fechavenc");
              utils.replacePlaceholder(wordprocessingMLPackage,valores.get("montoletra").toString(), "@montoletra");
           // utils.replacePlaceholder(wordprocessingMLPackage, historial.get(0).getPeriodoAcademico().getNombre(), "@periodo");

            PdfSettings pdfSettings = new PdfSettings();
            PdfConversion c = new Conversion(wordprocessingMLPackage);
            File file = File.createTempFile("pdfexport-", ".pdf");
            wordprocessingMLPackage.save(file);
            OutputStream out = new FileOutputStream(file);
            c.output(out, pdfSettings);
            Util.getDownload(response, file, "Reciboelectronico.pdf", "pdf"); 
            out.close();
            
        } catch (Docx4JException ex) {
            System.out.println("Docx4JException - " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("IOException - " + ex.getMessage());
        } 
    }
    
    public void generaSilaboToPdf(Silabo silabo,HttpServletResponse response){
        try {
            UtilGenerateDocs utils = new UtilGenerateDocs();
            WordprocessingMLPackage wordprocessingMLPackage = utils.getTemplate(AppConstants.PATH_SILABO_TO_PDF);
            String profesores = "";
            /**
             * parametros
             *
             **/
            utils.replacePlaceholder(wordprocessingMLPackage, silabo.getCentros(), "@facultad");
            utils.replacePlaceholder(wordprocessingMLPackage, silabo.getProgramas(), "@programas");
            
            utils.replacePlaceholder(wordprocessingMLPackage, silabo.getAsignaturaDictada().getNombre(), "@asignatura");
            utils.replacePlaceholder(wordprocessingMLPackage, silabo.getAsignaturaDictada().getAsignaturaSeccion()
                    .getNombreSeccion(), "@seccion");            
            utils.replacePlaceholder(wordprocessingMLPackage, silabo.getAsignaturaDictada().getSigla(), "@sigla");
            utils.replacePlaceholder(wordprocessingMLPackage, ""+silabo.getAsignaturaDictada().getCreditos(), "@creditos");
            utils.replacePlaceholder(wordprocessingMLPackage, silabo.getAsignaturaDictada().getPeriodoAcademico().getNombre(), "@semestre");
            utils.replacePlaceholder(wordprocessingMLPackage, silabo.getAsignaturaDictada().getTipoAsignatura(), "@tipo");
            
            if(silabo.getAsignaturaDictada().getProfesorList() != null){
                for(int i = 0; i < silabo.getAsignaturaDictada().getProfesorList().size(); i++){
                    if(i > 0){
                        profesores += " / ";
                    }
                    profesores += silabo.getAsignaturaDictada().getProfesorList().get(i).getNombres() + " " 
                            + silabo.getAsignaturaDictada().getProfesorList().get(i).getApellidoPaterno();
                }
            }
            utils.replacePlaceholder(wordprocessingMLPackage, profesores, "@profesor");
            utils.replacePlaceholder(wordprocessingMLPackage, silabo.getSumilla(), "@sumilla");
            utils.replacePlaceholder(wordprocessingMLPackage, silabo.getFundamentacion(), "@fundamento");
            
            //OBJETIVOS
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            Map<String, String> item;
            int i = 0;
            for (ObjetivoSilabo objetivo : silabo.getObjetivoList()) {                
                item = new HashMap<String, String>();
                i++;
                item.put("@nObj",i +". ");
                item.put("@objetivo",objetivo.getDescripcion());
                list.add(item);
            }
            utils.replaceTable(new String[]{"@objetivo"}, list, wordprocessingMLPackage);
            
            //ESTRATEGIAS
            list = new ArrayList<Map<String, String>>();
            i = 0;
            for (EstrategiaSilabo estrategia : silabo.getEstrategiaList()) {                
                item = new HashMap<String, String>();
                i++;
                item.put("@nEst",i +". ");
                item.put("@estrategia",estrategia.getTitulo());
                list.add(item);
            }
            utils.replaceTable(new String[]{"@estrategia"}, list, wordprocessingMLPackage);
            
            //CONTENIDOS
            utils.replaceTable3Rows(new String[]{"@nCon", "@tCont", "@semanaCont", "@sCont", "@teorica", 
                "@practica"}, silabo.getUnidadList(), wordprocessingMLPackage);
            
            //EVALUACIÓN
            utils.replacePlaceholder(wordprocessingMLPackage, silabo.getDescripcionEvaluacion(), "@descripcion");
            list = new ArrayList<Map<String, String>>();
            i = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.applyPattern("dd-MMM-yyyy");
            for (TipoEvaluacion tipo : silabo.getTipoEvaluacionList()) {
                for (Evaluacion evaluacion : tipo.getEvaluacionList()) {
                    item = new HashMap<String, String>();
                    i++;
                    item.put("@eva", "" + (i));
                    item.put("@dEva", evaluacion.getNombre());
                    item.put("@tEva", tipo.getNombre());
                    item.put("@peso", "" + evaluacion.getPeso());
                    item.put("@anulable", evaluacion.isAnulable() ? "SI": "NO");
                    item.put("@fecha", sdf.format(evaluacion.getFechaEvaluacion()));
                    list.add(item);
                }
            }
            utils.replaceTable(new String[]{"@eva", "@dEva", "@tEva", "@peso", "@anulable", 
                "@fecha"}, list, wordprocessingMLPackage);
            
            //BIBLIOGRAFÍA BÁSICA
            list = new ArrayList<Map<String, String>>();
            i = 0;
            for (String basico : silabo.getBibliografiaBasica()) {                
                item = new HashMap<String, String>();
                i++;
                item.put("@nBas",i +". ");
                item.put("@basica",basico);
                list.add(item);
            }
            utils.replaceTable(new String[]{"@basica"}, list, wordprocessingMLPackage);
            
            //BIBLIOGRAFÍA AVANZADA
            list = new ArrayList<Map<String, String>>();
            i = 0;
            for (String avanzado : silabo.getBibliografiaavanzada()) {                
                item = new HashMap<String, String>();
                i++;
                item.put("@numero",i +". ");
                item.put("@avanzada", avanzado);
                list.add(item);
            }
            utils.replaceTable(new String[]{"@numero","@avanzada"}, list, wordprocessingMLPackage);
            
            PdfSettings pdfSettings = new PdfSettings();
            PdfConversion c = new Conversion(wordprocessingMLPackage);
            File file = File.createTempFile("pdfexport-", ".pdf");
            wordprocessingMLPackage.save(file);
            OutputStream out = new FileOutputStream(file);
            c.output(out, pdfSettings);
            String nameFile = "SILABO_" + silabo.getAsignaturaDictada().getNombre() 
                    + "_" + silabo.getAsignaturaDictada().getAsignaturaSeccion()
                    .getNombreSeccion() + "_" + silabo.getAsignaturaDictada()
                    .getPeriodoAcademico().getNombre() + ".pdf";
            Util.getDownload(response, file, nameFile, "pdf"); 
            out.close();
            
        } catch (Docx4JException ex) {
            System.out.println("Docx4JException - " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("IOException - " + ex.getMessage());
        } catch (JAXBException ex) {
            System.out.println("JAXBException - " + ex.getMessage());
        }
    }
    
}
