/**
  * Classe ayant pour service r�servation
 * @author VO Thierry & VYAS Ishan
 * @version 3.5
 */
package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import biblioth�que.Biblioth�que;
import codage.Decodage;
import exception.EmpruntException;

public class ServiceReservation implements Runnable {
	private final Socket client;
	private static Biblioth�que biblioth�que;

	public ServiceReservation(Socket socket, Biblioth�que biblioth�que) {
		client = socket;
		ServiceReservation.biblioth�que = biblioth�que;
	}
	
	/**
	 * Lance la portion de code du thread
	 */
	@Override
	public void run() {
		String reponse = null;
		try {

			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			out.print(Decodage.encoder(biblioth�que.toStringAbonn�s()+"\n"+biblioth�que.toStringDocs()));
			// Demande au client les instructions propos�es
			out.println("Votre num�ro d'abonn� : ");
			int noAbo = Integer.parseInt(in.readLine());
			out.println("Le num�ro de document � emprunter :");
			int noDoc = Integer.parseInt(in.readLine());

			System.out.println("Requ�te de l'abonn� n�" + noAbo + " pour le document n�" + noDoc
					+ " pour une r�servation (IP:" + this.client.getInetAddress() + ")");
			boolean verification = biblioth�que.getAbonn�s().containsKey(noAbo);
			boolean verification2 = biblioth�que.getBiblio().containsKey(noDoc);
			boolean verification3 = Biblioth�que.getListeAttente().containsValue(noAbo);
			if (!verification) {
				reponse = "Aucun abonn� ne porte ce num�ro";
			System.out.println(reponse);
			out.println(reponse);

			}
			else if (!verification2) {
				reponse = "Aucun document ne porte ce num�ro";
				System.out.println(reponse);
				out.println(reponse);

			} else if (verification3) {
				reponse = "Vous voulez reserver un document que vous avez d�j� reserv�";
				System.out.println(reponse);
				out.println(reponse);
			}

			else {

				try {
					biblioth�que.getBiblio().get(noDoc).reserver(biblioth�que.getAbonn�s().get(noAbo));
					reponse = Decodage.encoder("R�servation du document " + noDoc + " par l'abonn� " + noAbo
							+ " r�ussie, vous avez 2 heures pour l'emprunter ou il sera retourn�.\nFin du service, tapez un caract�re pour quitter");

					System.out.println("R�servation du document " + noDoc + " par l'abonn� " + noAbo + " r�ussie, l'abonn� a 2 heures pour emprunter le document ou il sera retourn�.");
					out.println(reponse);
				} catch (EmpruntException e) {
					System.out.println("Le document est d�j� reserv�, envoi d'une proposition de mail");
					out.println("Document d�j� reserv�, voulez vous recevoir un mail de rappel ? ('O' sinon un autre caract�re)");
					String repMail = in.readLine();
					if (repMail.equals("O")) {
						Biblioth�que.getListeAttente().put(biblioth�que.getBiblio().get(noDoc), biblioth�que.getAbonn�s().get(noAbo));
						out.println("Mail envoy� � " + biblioth�que.getAbonn�s().get(noAbo).getEmail() + ", fin du service de r�servation (tapez un caract�re pour quitter)");
						client.close();
					}
					else {
						out.println("Mail non envoy�, fin du service de r�servation (tapez un caract�re pour quitter)");
						client.close();
					}
				}

			}


		}
		catch (IOException e) {}
		catch(NumberFormatException e) {}
		try {
			client.close();
		} catch (IOException e2) {
		}
	}

	/**
	 * Ferme le client
	 */
	protected void finalize() throws Throwable {
		client.close();
	}

	/**
	 * Cr�e un thread
	 */
	public void lancer() {
		new Thread(this).start();

	}
	
}
