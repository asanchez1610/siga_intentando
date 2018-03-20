package com.udep.siga.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Wilfredo Atoche
 */
public class Util {
    /*
     * Verifica si un usuario tiene un determinado ROL
     */

    public static boolean isUsuarioAuthority(String rol) {
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (Iterator<GrantedAuthority> it = authorities.iterator(); it.hasNext();) {
            GrantedAuthority grantedAuthority = it.next();
            if (grantedAuthority.getAuthority().equals(rol)) {
                return true;
            }
        }
        return false;
    }

    /*
     * Obtiene los roles que tiene el usuario en sesión
     */
    public static List<Rol> getRolesUsuario() {
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        List<Rol> list = new ArrayList<Rol>();
        for (Iterator<GrantedAuthority> it = authorities.iterator(); it.hasNext();) {
            GrantedAuthority grantedAuthority = it.next();
            list.add(Rol.parse(grantedAuthority.getAuthority()));
        }
        return list;
    }

    /*
     * Descarga archivo
     */
    public static void getDownload(String fullPathArchivo, String nombre, HttpServletResponse response) {
        String[] array = nombre.split("/");
        String nombreArchivo = array[array.length - 1];
        nombreArchivo = nombreArchivo.replaceAll(" ", "_");
        nombreArchivo = nombreArchivo.replaceAll(",", "_");
        nombreArchivo = nombreArchivo.replaceAll("'", "_");
        nombreArchivo = nombreArchivo.replaceAll("\"", "_");
        
        try {
            File archivo = new File(fullPathArchivo);
            response.setHeader("Pragma", "public");
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Cache-Control", "public");

            response.setDateHeader("Last-Modified", archivo.lastModified());
            response.setContentType(new MimetypesFileTypeMap().getContentType(archivo));
            response.setContentLength((int) archivo.length());
            response.setHeader("Content-disposition", "attachment; filename=\"" + nombreArchivo + "\"");

            InputStream is = new FileInputStream(archivo);
            long length = archivo.length();
            byte[] bytes = new byte[(int) length];
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length
                    && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
            is.close();

            OutputStream os = response.getOutputStream();
            os.write(bytes);
            os.flush();
            os.close();
        } catch (Exception e) {
            System.out.println("Error al descargar archivo: " + e.getMessage());
        }
    }
    
    public static void getDownload(HttpServletResponse response, File file,String nombreArchivo,String tipo) {
        try {
            response.setHeader("Pragma", "public");
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Cache-Control", "public");
            
            response.setContentLength((int) file.length());            
            response.setHeader("Content-disposition", "attachment; filename=\"" +nombreArchivo +"\"");
            if(tipo.equals("Word")){
                response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            }else if (tipo.equals("Excel")) {
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            } else {
                response.setContentType(new MimetypesFileTypeMap().getContentType(file));
            }
            InputStream is = new FileInputStream(file);
            long length = file.length();
            byte[] bytes = new byte[(int) length];
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length
                    && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
            is.close();

            OutputStream os = response.getOutputStream();
            os.write(bytes);
            os.flush();
            os.close();
            file.delete();
            
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException - " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("IOException - " + ex.getMessage());
        }
    }
    
    /*
     * Formato de Fecha dd-MM-yyyy
     */
    public static String dateToString(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
    }
    public  String dateeventoToString(){
         Calendar calendar = Calendar.getInstance();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }
    
     public  String dateeventosumarestaToString(Integer id,String fecha,HttpSession httpSession) throws ParseException{
         Calendar calendar = Calendar.getInstance();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(fecha);
        
        
            calendar.setTime(date);
        
        if(id==1){
            calendar.add(Calendar.DAY_OF_MONTH, 7);
            httpSession.setAttribute("fecha",dateFormat.format(calendar.getTime()));
            
        }else{
            if(id==2){
            calendar.add(Calendar.DAY_OF_MONTH, -7);
             httpSession.setAttribute("fecha",dateFormat.format(calendar.getTime()));
             
            }
        }
        return dateFormat.format(calendar.getTime());
    }
    
    public static String dateToStringLong(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        return dateFormat.format(date);
    }
    public String getNewFormatFecha(int dia, int mes, int anio, boolean SimpleDateFormat) {

        SimpleDateFormat sdf;
        String pattern = "dd/MM/yyyy";
        String nuevo_pattern = "dd 'de' MMMM 'de' yyyy";
        String novo_pattern = pattern;
        if (dia == 0) {
            dia = 1;
            nuevo_pattern = "MMMM 'de' yyyy";
            if (SimpleDateFormat) {novo_pattern = "MM/yyyy";}
        }
        if (mes == 0) {
            mes = 1;
            nuevo_pattern = "yyyy";
            if (SimpleDateFormat) {novo_pattern = "yyyy";}
        }
        if (anio == 0) {
            return "";
        }
        sdf = new SimpleDateFormat(pattern);
        Date date;
        try {
            date = sdf.parse(String.format("%02d/%02d/%04d", dia, mes, anio));
            if (SimpleDateFormat) {
                nuevo_pattern = novo_pattern;
            }
            sdf.applyPattern(nuevo_pattern);
            return sdf.format(date);
        } catch (ParseException ex) {
            return "";
        }
    }
    public String getFechaActual() {
        Calendar calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DATE);
        int mes = calendar.get(Calendar.MONTH) + 1;
        int anio = calendar.get(Calendar.YEAR);
        
        String dato = this.getNewFormatFecha(dia, mes, anio, true);
        dato += " " + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) 
                + ":" + calendar.get(Calendar.SECOND);
        return dato;

    }
    
}
