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

	/*
	 * On encode les messages ici vu que la communication
	 * serveur/client par message s'arrete lorsqu'un caract�re de fin
	 * de ligne apparait (ici \n), les \n sont encod�s en #n et sont
	 * d�cod�s en \n lors de l'affichage au client
	 */
	
	@Override
	public void run() {
		String reponse = null;
		boolean affichageBiblio = true;

		do {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintWriter out = new PrintWriter(client.getOutputStream(), true);

				if (affichageBiblio)
					out.print(Decodage.encoder(biblioth�que.toStringAbonn�s() + biblioth�que.toStringDocs()));
				affichageBiblio = false;
				// Demande au client les instructions propos�es
				out.println("Votre num�ro d'abonn� : ");
				int noAbo = Integer.parseInt(in.readLine());
				out.println("Le num�ro de document � emprunter :");
				int noDoc = Integer.parseInt(in.readLine());

				System.out.println("Requ�te de l'abonn� n�" + noAbo + " pour le document n�" + noDoc
						+ " pour une r�servation (IP:" + this.client.getInetAddress() + ")");
				
				// Si le num�ro d'abonn� est dans la biblioth�que
				boolean verification = biblioth�que.getAbonn�s().containsKey(noAbo);
				// Si le num�ro de document est dans la biblioth�que
				boolean verification2 = biblioth�que.getBiblio().containsKey(noDoc);
				// Si l'abonn� n'a pas d�j� r�serv� le m�me document
				boolean verification3 = Biblioth�que.getListeAttente().containsValue(noAbo);
				if (!verification) {
					reponse = "Aucun abonn� ne porte ce num�ro";
				} else if (!verification2) {
					reponse = "Aucun document ne porte ce num�ro";
				} else if (verification3) {
					reponse = "Vous voulez reserver un document que vous avez d�j� reserv�";
				} else {

					try {
						biblioth�que.getBiblio().get(noDoc).reserver(biblioth�que.getAbonn�s().get(noAbo));
						reponse = Decodage.encoder("R�servation du document " + noDoc + " par l'abonn� " + noAbo
								+ " r�ussie, vous avez 2 heures pour l'emprunter ou il sera retourn�. \n");
					} catch (EmpruntException e) {
						System.out.println("Le document est d�j� reserv�, envoi d'une proposition de mail");
						out.println(
								"Document d�j� reserv�, voulez vous recevoir un mail de rappel ? ('O' sinon un autre caract�re)");
						String repMail = in.readLine();
						if (repMail.equals("O")) {
							Biblioth�que.getListeAttente().put(biblioth�que.getBiblio().get(noDoc),
									biblioth�que.getAbonn�s().get(noAbo));
							reponse = Decodage
									.encoder("Mail envoy� � " + biblioth�que.getAbonn�s().get(noAbo).getEmail() + "\n");
						} else {
							reponse = Decodage.encoder("Mail non envoy� \n");
						}
					}

				}
				System.out.println(Decodage.decoder(reponse));
				out.println(reponse + "Voulez vous arr�ter ? ('O')");
				String repArret = in.readLine();
				if (repArret.equals("O"))
					break;

			} catch (IOException e) {
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		} while (true);
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
	 * Cr�e un thread et le lance
	 */
	public void lancer() {
		new Thread(this).start();

	}

}
