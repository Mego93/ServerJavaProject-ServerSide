package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import bibliothèque.Bibliothèque;
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
			
			// Ajouter try catch 
			out.println("Le numéro de document à retourner :");
			int noDoc = Integer.parseInt(in.readLine());
			
			System.out.println("Requète de " + this.client.getInetAddress() + " doc n° " + noDoc);
			boolean verification = bibliothèque.getBiblio().containsKey(noDoc);
			if (verification)
				try {
					bibliothèque.getBiblio().get(noDoc).retour();
					reponse = "Retour réussi";

				} catch (RetourException e) {
					reponse = e.toString();
				}
			else
				reponse = "Aucun document ne porte ce numéro";
			System.out.println(reponse);
			out.println(reponse);
			out.println("Voulez vous arrêter ? (O/N)");
			String repArret = in.readLine();
			if (repArret.equals("O"))
				out.println("Arret du service");

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
}
