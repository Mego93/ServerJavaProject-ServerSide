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
import bibliothèque.Mail;
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
			out.print(bibliothèque.toStringDocs());
			// Demande au client les instructions proposées
			out.println("Votre numéro d'abonné : ");
			int noAbo = Integer.parseInt(in.readLine());
			out.println("Le numéro de document à emprunter :");
			int noDoc = Integer.parseInt(in.readLine());

			System.out.println("Requète de l'abonné n°" + noAbo + " pour le document n°" + noDoc
					+ " pour un emprunt (IP:" + this.client.getInetAddress() + ")");
			boolean verification = bibliothèque.getAbonnés().containsKey(noAbo);
			boolean verification2 = bibliothèque.getBiblio().containsKey(noDoc);
			if (!verification)
				reponse = "Aucun abonné ne porte ce numéro";
			else if (!verification2)
				reponse = "Aucun document ne porte ce numéro";
			else {
				try {
					bibliothèque.getBiblio().get(noDoc).emprunter(bibliothèque.getAbonnés().get(noAbo));
					reponse = "Emprunt du document " + noDoc + " par l'abonné " + noAbo
							+ " réussi, il n'est plus disponible à la bibliothèque";

				} catch (EmpruntException e) {
					out.println("Document déjà emprunté, voulez vous recevoir un mail de rappel ? (O/N)");
					String repMail = in.readLine();
					if (repMail.equals("O"))
						Mail.sendMail(bibliothèque.getAbonnés().get(noAbo), bibliothèque.getBiblio().get(noDoc));
					reponse = e.toString();
				}

			}

			System.out.println(reponse);
			out.println(reponse);

		} catch (IOException e) {
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
