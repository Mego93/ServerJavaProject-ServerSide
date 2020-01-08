/**
 * Classe ayant pour service retour
 * @author VO Thierry & VYAS Ishan
 * @version 3.0
 */
package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import bibliothèque.Bibliothèque;
import codage.Decodage;
import exception.RetourException;

public class ServiceRetour implements Runnable {
	private final Socket client;
	private static Bibliothèque bibliothèque;

	public ServiceRetour(Socket socket, Bibliothèque bibliothèque) {
		client = socket;
		ServiceRetour.bibliothèque = bibliothèque;
	}

	@Override
	public void run() {
		String reponse = null;
		try {

			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			out.print(Decodage.encoder(bibliothèque.toStringAbonnés() + "\n" + bibliothèque.toStringDocs()));
			// Demande au client l'instruction proposée
			out.println("Le numéro de document à retourner :");
			int noDoc = Integer.parseInt(in.readLine());
			System.out.println("Requète pour le document n°" + noDoc + " pour un retour (IP:"
					+ this.client.getInetAddress() + ")");
			boolean verification = bibliothèque.getBiblio().containsKey(noDoc);
			if (verification)
				try {
					bibliothèque.getBiblio().get(noDoc).retour();
					reponse = "Retour du document " + noDoc + " réussi, il est de nouveau disponible à la bibliothèque";
					System.out.println("Retour du document " + noDoc
							+ " réussie, il est de nouveau disponible à la bibliothèque.");
					out.println(reponse);
				} catch (RetourException e) {
					System.out.println("Le document ne peut être retourné");
					out.println("Fin du service de retour (tapez un caractère pour quitter)");
					client.close();
				}
			else {
				reponse = "Le document n'existe pas";
				System.out.println(reponse);
				out.println(reponse);

			}

		} catch (IOException e) {
		} catch (NumberFormatException e) {
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
