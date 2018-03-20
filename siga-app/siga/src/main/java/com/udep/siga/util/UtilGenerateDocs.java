package com.udep.siga.util;

import com.udep.siga.bean.TemaSilabo;
import com.udep.siga.bean.UnidadSilabo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import org.docx4j.XmlUtils;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.FooterPart;
import org.docx4j.openpackaging.parts.WordprocessingML.StyleDefinitionsPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.FooterReference;
import org.docx4j.wml.Ftr;
import org.docx4j.wml.HdrFtrRef;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.RFonts;
import org.docx4j.wml.RPr;
import org.docx4j.wml.SectPr;
import org.docx4j.wml.Style;
import org.docx4j.wml.Styles;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Text;
import org.docx4j.wml.Tr;
/**
 *
 * @author Administrador
 */
public class UtilGenerateDocs {
    public WordprocessingMLPackage getTemplate(String name) {
        try {
            WordprocessingMLPackage template = WordprocessingMLPackage.load(new FileInputStream(new File(name)));
            return template;
        } catch (Docx4JException ex) {
            System.out.println("Docx4JException - " + ex.getMessage());
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException - " + ex.getMessage());
        }
        return null;
    }

    private static List<Object> getAllElementFromObject(Object obj, Class<?> toSearch) {
        List<Object> result = new ArrayList<Object>();
        if (obj instanceof JAXBElement) {
            obj = ((JAXBElement<?>) obj).getValue();
        }

        if (obj.getClass().equals(toSearch)) {
            result.add(obj);
        } else if (obj instanceof ContentAccessor) {
            List<?> children = ((ContentAccessor) obj).getContent();
            for (Object child : children) {
                result.addAll(getAllElementFromObject(child, toSearch));
            }

        }
        return result;
    }
    
    public void replacePlaceholder(WordprocessingMLPackage template, String name, String placeholder) {
        List<Object> texts = getAllElementFromObject(template.getMainDocumentPart(), Text.class);

        for (Object text : texts) {
            Text textElement = (Text) text;
            if (textElement.getValue().indexOf(placeholder) != -1) {
                textElement.setValue(textElement.getValue().replaceAll(placeholder, name));
            }
        }
    }

    /* Utilidades */
    
    /* 
     * Este método encuentra la tabla, coge la primera fila y 
     * se añade una nueva fila a la tabla. 
     * Antes de regresar elimina la fila de la plantilla
     */    
    public void replaceTable(String[] placeholders, List<Map<String, String>> textToAdd,
                    WordprocessingMLPackage template) throws Docx4JException, JAXBException {
            List<Object> tables = getAllElementFromObject(template.getMainDocumentPart(), Tbl.class);

            // 1. find the table
            Tbl tempTable = getTemplateTable(tables, placeholders[0]);
            List<Object> rows = getAllElementFromObject(tempTable, Tr.class);

            // first row is header, second row is content
            if (rows.size() == 2) {
                    // this is our template row
                    Tr templateRow = (Tr) rows.get(1);

                    for (Map<String, String> replacements : textToAdd) {
                            // 2 and 3 are done in this method
                            addRowToTable(tempTable, templateRow, replacements);
                    }

                    // 4. remove the template row
                    tempTable.getContent().remove(templateRow);
            }else{
                if (rows.size() == 1) {
                        // this is our template row
                        Tr templateRow = (Tr) rows.get(0);

                        for (Map<String, String> replacements : textToAdd) {
                                // 2 and 3 are done in this method
                                addRowToTable(tempTable, templateRow, replacements);
                        }

                        // 4. remove the template row
                        tempTable.getContent().remove(templateRow);
                }
            }
    }
    
    public Tbl getTemplateTable(List<Object> tables, String templateKey) throws Docx4JException, JAXBException {
            for (Iterator<Object> iterator = tables.iterator(); iterator.hasNext();) {
                    Object tbl = iterator.next();
                    List<?> textElements = getAllElementFromObject(tbl, Text.class);
                    for (Object text : textElements) {
                            Text textElement = (Text) text;
                            if (textElement.getValue() != null && textElement.getValue().equals(templateKey))
                                    return (Tbl) tbl;
                    }
            }
            return null;
    }
    
    private static void addRowToTable(Tbl reviewtable, Tr templateRow, Map<String, String> replacements) {
            Tr workingRow = (Tr) XmlUtils.deepCopy(templateRow);
            List<?> textElements = getAllElementFromObject(workingRow, Text.class);
            for (Object object : textElements) {
                    Text text = (Text) object;
                    String replacementValue = (String) replacements.get(text.getValue());
                    if (replacementValue != null)
                            text.setValue(replacementValue);
            }

            reviewtable.getContent().add(workingRow);
    }
    
    public void replaceTable3Rows(String[] placeholders, List<UnidadSilabo> textToAdd,
                    WordprocessingMLPackage template) throws Docx4JException, JAXBException {
            List<Object> tables = getAllElementFromObject(template.getMainDocumentPart(), Tbl.class);

//             1. find the table
            Tbl tempTable = getTemplateTable(tables, placeholders[0]);
            List<Object> rows = getAllElementFromObject(tempTable, Tr.class);

             
            if (rows.size() == 3) {
//                     this is our template row (title)
                    Tr templateRow = (Tr) rows.get(0);
//                     this is our template row (body)
                    Tr templateRow2 = (Tr) rows.get(2);
//                     this is our template row (body)
                    Tr templateRowHead = (Tr) rows.get(1);
                    HashMap<String, String> item;
                    HashMap<String, String> espacio = new HashMap<String, String>();  
                    espacio.put("@contenido", ""); 
                    
                    for (UnidadSilabo unidad : textToAdd) {
                        item = new HashMap<String, String>();
                        item.put("@contenido", unidad.getNumero() + " " + unidad.getDescripcion()); 
                        addRowToTable(tempTable, templateRow, item);                           
                        addRowToTable(tempTable, templateRow, espacio);
                        if(!unidad.getTemaList().isEmpty()){
                            addRowToTable(tempTable, templateRowHead, item);  
                            for (TemaSilabo tema : unidad.getTemaList()) {
                                item = new HashMap<String, String>();
                                item.put("@nCon", tema.getNumero());
                                item.put("@tCont", tema.getTema());
                                item.put("@semanaCont", tema.getSemana());
                                String fechas = "";
                                if(tema.getFechasSesion() != null){
                                    for(int i = 0; i < tema.getFechasSesion().size(); i++){
                                        if(i > 0){
                                            fechas += " / ";
                                        }
                                        fechas += tema.getFechasSesion().get(i);
                                    }
                                }
                                item.put("@sCont", "" + fechas);
                                item.put("@teorica", "" + tema.getHorasTeoricas());
                                item.put("@practica", "" + tema.getHorasPracticas());
                                addRowToTable(tempTable, templateRow2, item);
                            }
                            addRowToTable(tempTable, templateRow, espacio);
                        }
                        
                    }
//                     4. remove the template row
                    tempTable.getContent().remove(templateRow);
                    tempTable.getContent().remove(templateRow2);
                    tempTable.getContent().remove(templateRowHead);
            }
    }
    
    /*
     * Pie de página
     */
    
     /**
     *  This method creates a footer part and set the package on it. Finally we return the
     *  corresponding relationship.
     *
     *  @return
     *  @throws InvalidFormatException
     */
    public Relationship createFooterPart(WordprocessingMLPackage template,ObjectFactory factory,
            String footer) 
            throws InvalidFormatException {
        FooterPart footerPart = new FooterPart();
        footerPart.setPackage(template);
 
        footerPart.setJaxbElement(createFooter(footer,factory));
 
        return template.getMainDocumentPart().addTargetPart(footerPart);
    }
 
    /**
     *  First we create a footer, a paragraph, a run and a text. We add the given
     *  given content to the text and add that to the run. The run is then added to
     *  the paragraph, which is in turn added to the footer. Finally we return the
     *  footer.
     *
     *  @param content
     *  @return
     */
    private static Ftr createFooter(String content,ObjectFactory factory) {
        Ftr footer = factory.createFtr();
        P paragraph = factory.createP();
        R run = factory.createR();
        Text text = new Text();
        text.setValue(content);
        run.getContent().add(text);
        paragraph.getContent().add(run);
        footer.getContent().add(paragraph);
        return footer;
    }
 
    /**
     *  First we retrieve the document sections from the package. As we want to add
     *  a footer, we get the last section and take the section properties from it.
     *  The section is always present, but it might not have properties, so we check
     *  if they exist to see if we should create them. If they need to be created,
     *  we do and add them to the main document part and the section.
     *  Then we create a reference to the footer, give it the id of the relationship,
     *  set the type to header/footer reference and add it to the collection of
     *  references to headers and footers in the section properties.
     *
     * @param relationship
     */
    public void createFooterReference(WordprocessingMLPackage template,
            Relationship relationship,ObjectFactory factory) {
        List<SectionWrapper> sections =
            template.getDocumentModel().getSections();
 
        SectPr sectionProperties = sections.get(sections.size() - 1).getSectPr();
        // There is always a section wrapper, but it might not contain a sectPr
        if (sectionProperties==null ) {
            sectionProperties = factory.createSectPr();
            template.getMainDocumentPart().addObject(sectionProperties);
            sections.get(sections.size() - 1).setSectPr(sectionProperties);
        }
 
        FooterReference footerReference = factory.createFooterReference();
        footerReference.setId(relationship.getId());
        footerReference.setType(HdrFtrRef.DEFAULT);
        sectionProperties.getEGHdrFtrReferences().add(footerReference);
    }
    
    /**
     *  This method alters the default style sheet that is part of each document.
     *
     *  To do this, we first retrieve the style sheet from the package and then
     *  get the Styles object from it. From this object, we get the list of actual
     *  styles and iterate over them.
     *  We check against all styles we want to alter and apply the alterations if
     *  applicable.
     *
     *  @param template
     */
    public void alterStyleSheet(WordprocessingMLPackage template) {
        StyleDefinitionsPart styleDefinitionsPart =
            template.getMainDocumentPart().getStyleDefinitionsPart();
        Styles styles = styleDefinitionsPart.getJaxbElement();
 
        List<Style>  stylesList = styles.getStyle();
        for (Style style : stylesList) {
                alterStyle(style);            
        }
    }
    /**
     *  First we create a run properties object as we want to remove nearly all of
     *  the existing styling. Then we change the font and font size and set the
     *  run properties on the given style. As in previous examples, the font size
     *  is defined to be in half-point size.
     */
    private static void alterStyle(Style style) {
        // we want to change (or remove) almost all the run properties of the
        // normal style, so we create a new one.
        RPr rpr = new RPr();
        changeFontToCalibri(rpr);
        style.setRPr(rpr);
    }
    
    private static void changeFontToCalibri(RPr runProperties) {
        RFonts runFont = new RFonts();
        runFont.setAscii("Calibri");
        runFont.setHAnsi("Calibri");
        runProperties.setRFonts(runFont);
    }
}
