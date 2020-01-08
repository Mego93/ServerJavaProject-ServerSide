/**
 * Classe permettant l'envoi de mail de rappel
 * @author VO Thierry & VYAS Ishan
 * @version 2.5
 */
package bibliothèque;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Mail {

    private static final String SMTP_SERVER = "127.0.0.1";
    /**
     * Méthode d'envoi d'un mail à un client
     * Notammant pour les rappels lorsqu'un document est disponible
     * @param client
     * @param document
     */
    public static void sendMail(Abonne client, Document document) {

        String source = "biblioVYOS@boss.fr";
        String destinataire = client.getEmail();

        String objetEmail = "Client n°" + client.getNumero() + ": Document " + document.numero() + " disponible";
        String cotenuEmail = "Client n°" + client.getNumero() + " : Le document n°" + document.numero()
                + " que vous souhaitez réserver ou emprunter ";

        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", SMTP_SERVER);
        prop.put("mail.smtp.port", "3000");

        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);

        try {
            msg.setFrom(new InternetAddress(source));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire, false));
            msg.setSubject(objetEmail);
            msg.setText(cotenuEmail);
            msg.setSentDate(new Date());

            Transport.send(msg);
            System.out.println("Email envoyé !");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}