package com.udep.siga.dao;

import com.udep.siga.bean.Mensaje;
import com.udep.siga.bean.Email;
import java.util.List;

/**
 *
 * @author Wilfredo Atoche
 */
public interface MensajeriaDAO {
    
    public List<Mensaje> getInbox(int id, boolean all);
    public List<Mensaje> getOutbox(int id);
    public Mensaje getMensaje(int id);
    public void updateLeido(int id, boolean actualizaFechaLeido);
    public void saveMensajeria(Mensaje mensaje) ;
    public List<Email> getEmailList(int id);
    public int getCantidadMensaje(int idpersona,boolean nuevo);
    public List<Email> getEmailValidSendList(int idpersona);
    public List<Email> getEmailValidSend(int idpersona);
}
