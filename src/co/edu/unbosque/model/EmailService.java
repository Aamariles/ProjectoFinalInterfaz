package co.edu.unbosque.model;

public interface EmailService {

    /**
     * Envía un correo electrónico simple
     * 
     * @param to Dirección de correo del destinatario
     * @param subject Asunto del correo
     * @param body Cuerpo del mensaje
     * @return true si el correo se envió correctamente, false en caso contrario
     */
    boolean sendEmail(String to, String subject, String body);
    
    /**
     * Envía un correo electrónico con un archivo adjunto
     * 
     * @param 
     * @param subject Asunto del correo
     * @param body Cuerpo del mensaje
     * @param attachmentPath Ruta del archivo adjunto
     * @return true si el correo se envió correctamente, false en caso contrario
     */
    boolean sendEmailWithAttachment(String to, String subject, String body, String attachmentPath);
}

