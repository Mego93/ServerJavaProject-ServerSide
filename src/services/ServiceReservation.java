/**
  * Classe ayant pour service réservation
 * @author VO Thierry & VYAS Ishan
 * @version 3.5
 */
package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import bibliothèque.Bibliothèque;
import codage.Decodage;
import exception.EmpruntException;

public class ServiceReservation implements Runnable {
	private final Socket client;
	private static Bibliothèque bibliothèque;

	public ServiceReservation(Socket socket, Bibliothèque bibliothèque) {
		client = socket;
		ServiceReservation.bibliothèque = bibliothèque;
	}

	/*
	 * On encode les messages ici vu que la communication
	 * serveur/client par message s'arrete lorsqu'un caractère de fin
	 * de ligne apparait (ici \n), les \n sont encodés en #n et sont
	 * décodés en \n lors de l'affichage au client
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
					out.print(Decodage.encoder(bibliothèque.toStringAbonnés() + bibliothèque.toStringDocs()));
				affichageBiblio = false;
				// Demande au client les instructions proposées
				out.println("Votre numéro d'abonné : ");
				int noAbo = Integer.parseInt(in.readLine());
				out.println("Le numéro de document à emprunter :");
				int noDoc = Integer.parseInt(in.readLine());

				System.out.println("Requète de l'abonné n°" + noAbo + " pour le document n°" + noDoc
						+ " pour une réservation (IP:" + this.client.getInetAddress() + ")");
				
				// Si le numéro d'abonné est dans la bibliothèque
				boolean verification = bibliothèque.getAbonnés().containsKey(noAbo);
				// Si le numéro de document est dans la bibliothèque
				boolean verification2 = bibliothèque.getBiblio().containsKey(noDoc);
				// Si l'abonné n'a pas déjà réservé le même document
				boolean verification3 = Bibliothèque.getListeAttente().containsValue(noAbo);
				if (!verification) {
					reponse = "Aucun abonné ne porte ce numéro";
				} else if (!verification2) {
					reponse = "Aucun document ne porte ce numéro";
				} else if (verification3) {
					reponse = "Vous voulez reserver un document que vous avez déjà reservé";
				} else {

					try {
						bibliothèque.getBiblio().get(noDoc).reserver(bibliothèque.getAbonnés().get(noAbo));
						reponse = Decodage.encoder("Réservation du document " + noDoc + " par l'abonné " + noAbo
								+ " réussie, vous avez 2 heures pour l'emprunter ou il sera retourné. \n");
					} catch (EmpruntException e) {
						System.out.println("Le document est déjà reservé, envoi d'une proposition de mail");
						out.println(
								"Document déjà reservé, voulez vous recevoir un mail de rappel ? ('O' sinon un autre caractère)");
						String repMail = in.readLine();
						if (repMail.equals("O")) {
							Bibliothèque.getListeAttente().put(bibliothèque.getBiblio().get(noDoc),
									bibliothèque.getAbonnés().get(noAbo));
							reponse = Decodage
									.encoder("Mail envoyé à " + bibliothèque.getAbonnés().get(noAbo).getEmail() + "\n");
						} else {
							reponse = Decodage.encoder("Mail non envoyé \n");
						}
					}

				}
				System.out.println(Decodage.decoder(reponse));
				out.println(reponse + "Voulez vous arrêter ? ('O')");
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
	 * Crée un thread et le lance
	 */
	public void lancer() {
		new Thread(this).start();

	}

}
