package com.udep.siga.service;

import com.udep.siga.util.AppConstants;
import java.io.File;
import javax.mail.MessagingException;  
import javax.mail.internet.MimeMessage; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 *
 * @author Wilfredo Atoche
 */
@Service("mailService")
public class MailService {
    
    @Autowired
    private JavaMailSenderImpl mailSender;  
    
    private static final File[] NO_ATTACHMENTS = null;  
    
    /** envío de email  
     * @param to correo electrónico del destinatario 
     * @param subject asunto del mensaje 
     * @param text cuerpo del mensaje 
     */  
    public void send(String to,String subject, String text) {  
        send(to, subject, text, NO_ATTACHMENTS);  
    } 
    
    /** envío de email  
     * @param to correo electrónico del destinatario 
     * @param cc correo electrónico del destinatario (cc)
     * @param subject asunto del mensaje 
     * @param text cuerpo del mensaje 
     */  
    public void send(String to,String cc, String subject, String text) {  
        send(to,cc, subject, text, NO_ATTACHMENTS);  
    }  
    
    /** envío de email con attachments 
     * @param to correo electrónico del destinatario 
     * @param subject asunto del mensaje 
     * @param text cuerpo del mensaje 
     * @param attachments ficheros que se anexarán al mensaje  
     */  
    public void send(String to,String subject, String text, File... attachments) {        
          
        // el servicio esta activo?  
        if (!AppConstants.MAIL_ACTIVO) return;  
          
        // plantilla para el envío de email  
        final MimeMessage message = mailSender.createMimeMessage();  
  
        try {  
            // el flag a true indica que va a ser multipart  
            final MimeMessageHelper helper = new MimeMessageHelper(message, true);  
              
            // settings de los parámetros del envío  
            helper.setTo(to); 
            helper.setSubject(subject);  
            helper.setFrom(AppConstants.MAIL_FROM);  
            helper.setText(text + AppConstants.MAIL_FINAL_TEXT + AppConstants.MAIL_URL_FROM, false);            
  
            // adjuntando los ficheros  
            if (attachments != null) {  
                for (int i = 0; i < attachments.length; i++) {  
                    FileSystemResource file = new FileSystemResource(attachments[i]);  
                    helper.addAttachment(attachments[i].getName(), file);                      
                }  
            }  
  
        } catch (MessagingException e) {  
            new RuntimeException(e);  
        }  
          
        // el envío  
        try{
            this.mailSender.send(message);  
        }catch(Exception ex){
            System.out.println("Error: En el envio de correo. Detalle: " + to + " - " 
                    + ex.getMessage());
        }
    }  
    /** envío de email con attachments 
     * @param to correo electrónico del destinatario 
     * @param cc correo electrónico del destinatario (cc)
     * @param subject asunto del mensaje 
     * @param text cuerpo del mensaje 
     * @param attachments ficheros que se anexarán al mensaje  
     */  
    public void send(String to,String cc, String subject, String text, File... attachments) {        
          
        // el servicio esta activo?  
        if (!AppConstants.MAIL_ACTIVO) return;  
          
        // plantilla para el envío de email  
        final MimeMessage message = mailSender.createMimeMessage();  
  
        try {  
            // el flag a true indica que va a ser multipart  
            final MimeMessageHelper helper = new MimeMessageHelper(message, true);  
              
            // settings de los parámetros del envío  
            helper.setTo(to); 
            helper.setCc(cc);
            helper.setSubject(subject);  
            helper.setFrom(AppConstants.MAIL_FROM);  
            helper.setText(text + AppConstants.MAIL_FINAL_TEXT + AppConstants.MAIL_URL_FROM, false);            
  
            // adjuntando los ficheros  
            if (attachments != null) {  
                for (int i = 0; i < attachments.length; i++) {  
                    FileSystemResource file = new FileSystemResource(attachments[i]);  
                    helper.addAttachment(attachments[i].getName(), file);                      
                }  
            }  
  
        } catch (MessagingException e) {  
            new RuntimeException(e);  
        }  
          
        // el envío  
        try{
            this.mailSender.send(message);  
        }catch(Exception ex){
            System.out.println("Error: En el envio de correo. Detalle: " + to + " - " 
                    + ex.getMessage());
        }
    }  
}
