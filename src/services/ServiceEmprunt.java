/**
 * Classe ayant pour service emprunt
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

public class ServiceEmprunt implements Runnable {
	private final Socket client;
	private static Bibliothèque bibliothèque;

	public ServiceEmprunt(Socket socket, Bibliothèque bibliothèque) {
		client = socket;
		ServiceEmprunt.bibliothèque = bibliothèque;
	}

	/**
	 * Lance la portion de code du thread
	 */
	@Override
	public void run() {
		String reponse = null;
		try {
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out.print(Decodage.encoder(bibliothèque.toStringAbonnés()+"\n"+bibliothèque.toStringDocs()));
			// Demande au client les instructions proposées
			out.println("Votre numéro d'abonné : ");
			int noAbo = Integer.parseInt(in.readLine());
			out.println("Le numéro de document à emprunter :");
			int noDoc = Integer.parseInt(in.readLine());

			System.out.println("Requète de l'abonné n°" + noAbo + " pour le document n°" + noDoc
					+ " pour un emprunt (IP:" + this.client.getInetAddress() + ")");
			boolean verification = bibliothèque.getAbonnés().containsKey(noAbo);
			boolean verification2 = bibliothèque.getBiblio().containsKey(noDoc);
			boolean verification3 = Bibliothèque.getListeAttente().containsKey(noAbo);
			if (!verification) {
				reponse = "Aucun abonné ne porte ce numéro";
			System.out.println(reponse);
			out.println(reponse);

			}
			else if (!verification2) {
				reponse = "Aucun document ne porte ce numéro";
				System.out.println(reponse);
				out.println(reponse);

			}else if (verification3) {
			reponse = "Vous voulez reserver un document que vous avez déjà reservé";
			System.out.println(reponse);
			out.println(reponse);
		}
			else {
				try {
					bibliothèque.getBiblio().get(noDoc).emprunter(bibliothèque.getAbonnés().get(noAbo));
					reponse = Decodage.encoder("Emprunt du document " + noDoc + " par l'abonné " + noAbo
							+ " réussie, il n'est plus disponible à la bibliothèque.\nFin du service d'emprunt , tapez un caractère pour quitter");

					System.out.println("Emprunt du document " + noDoc + " par l'abonné " + noAbo + " réussie, il n'est plus disponible à la bibliothèque.");
					out.println(reponse);

				} catch (EmpruntException e) {
					System.out.println("Le document est déjà emprunté, envoi d'une proposition de mail");
					out.println("Document déjà emprunté, voulez vous recevoir un mail de rappel ? ('O' sinon un autre caractère)");
					String repMail = in.readLine();
					if (repMail.equals("O")) {
						Bibliothèque.getListeAttente().put(bibliothèque.getBiblio().get(noDoc), bibliothèque.getAbonnés().get(noAbo));
						out.println("Mail envoyé à " + bibliothèque.getAbonnés().get(noAbo).getEmail() + ", fin du service d'emprunt (tapez un caractère pour quitter)");
						client.close();
					}
					else {
						out.println("Mail non envoyé, fin du service d'emprunt (tapez un caractère pour quitter)");
						client.close();
					}
				}

			}


		} catch (IOException e) {
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
		}
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
	 * Crée un thread
	 */
	public void lancer() {
		new Thread(this).start();

	}
}
