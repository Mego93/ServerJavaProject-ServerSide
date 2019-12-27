package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import bibliothèque.Bibliothèque;
import exception.EmpruntException;

public class ServiceReservation implements Runnable {
	private final Socket client;
	private static Bibliothèque bibliothèque;

	public ServiceReservation(Socket socket, Bibliothèque bibliothèque) {
		client = socket;
		ServiceReservation.bibliothèque = bibliothèque;
	}

	@Override
	public void run() {
		String reponse = null;
		try {

			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);

			// Ajouter try catch
			out.println("Votre numéro d'abonné : ");
			int noAbo = Integer.parseInt(in.readLine());
			out.println("Le numéro de document à emprunter :");
			int noDoc = Integer.parseInt(in.readLine());

			System.out.println("Requète de l'abonné n°" + noAbo + " pour le document n°" + noDoc
					+ " pour une réservation (IP:" + this.client.getInetAddress() + ")");
			boolean verification = bibliothèque.getAbonnés().containsKey(noAbo);
			boolean verification2 = bibliothèque.getBiblio().containsKey(noDoc);
			if (!verification)
				reponse = "Aucun abonné ne porte ce numéro";
			else if (!verification2)
				reponse = "Aucun document ne porte ce numéro";

			else {

				try {
					bibliothèque.getBiblio().get(noDoc).reserver(bibliothèque.getAbonnés().get(noAbo));
					reponse = "Réservation du document " + noDoc + " par l'abonné " + noAbo
							+ " réussie, vous avez 2 heures pour l'emprunter ou il sera retourné";

				} catch (EmpruntException e) {
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

	protected void finalize() throws Throwable {
		client.close();
	}

	public void lancer() {
		new Thread(this).start();

	}

	public static boolean isParsable(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
