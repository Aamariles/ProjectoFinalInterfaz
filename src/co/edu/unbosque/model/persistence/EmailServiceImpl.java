package co.edu.unbosque.model.persistence;

import co.edu.unbosque.model.EmailService;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailServiceImpl implements EmailService {
	private final String username = "maxellenviocorreoproject@gmail.com"; // Cambiar por correo real
    private final String password = "ndrxtaertvsjnunj"; // Cambiar por contraseña real
    private final Properties props;
    private static final String[] ALLOWED_DOMAINS = {
            "unbosque.edu.co", "outlook.com", "yahoo.com", "hotmail.com", 
            "gmail.com"
        };
    public EmailServiceImpl() {
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
    }
    
    @Override
    public boolean sendEmail(String to, String subject, String body) {
        try {
            Session session = createSession();
            Message message = prepareMessage(session, to, subject, body);
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            System.err.println("Error al enviar correo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean sendEmailWithAttachment(String to, String subject, String body, String attachmentPath) {
        // Implementación para enviar correo con archivo adjunto
        
        try {
            Session session = createSession();
            
            // Crear mensaje multiparte para manejar adjuntos
            
            
            return true;
        } catch (Exception e) {
            System.err.println("Error al enviar correo con adjunto: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    private Session createSession() {
        return Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
    
    private Message prepareMessage(Session session, String to, String subject, String body) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);
        return message;
    }
    


}
