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

/*
 * RAPPEL :
 * On doit d'abord lancer le fakeSTMP.jar
 * pour pouvoir simuler les envois de mail !
 */

public class Mail {

    private static final String SMTP_SERVER = "localhost";
    /**
     * Méthode d'envoi d'un mail à un client
     * Notammant pour les rappels lorsqu'un document est disponible
     * @param client
     * @param document
     */
    public static void sendMail(Abonne client, Document document) {

        String source = "bibliothèqueVYOS@contact.fr";
        String destinataire = client.getEmail();

        String objetEmail = "M/Mme " + client.getNom() + " : Document " + document.numero() + " à nouveau disponible !";
        String contenuEmail = "Cher M/Mme"+ client.getNom()+ "(abonné n°" + client.getNumero() + ") : Le document n°" + document.numero()
                + " que vous souhaitez réserver ou emprunter est de renouveau disponible à la biliothèque VYOS, n'attendez plus pour le réserver ! ";

        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", SMTP_SERVER);
        // Le port dépend de ce qu'on a choisi avec fakeSMTP
        prop.put("mail.smtp.port", "25");

        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);

        try {
            msg.setFrom(new InternetAddress(source));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire, false));
            msg.setSubject(objetEmail);
            msg.setText(contenuEmail);
            msg.setSentDate(new Date());

            Transport.send(msg);
            System.out.println("Email(s) envoyé(s) à la liste d'attente");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}