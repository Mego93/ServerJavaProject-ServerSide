package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import biblioth�que.Biblioth�que;
import exception.RetourException;

public class ServiceRetour implements Runnable {
	private final Socket client;
	private static Biblioth�que biblioth�que;

	public ServiceRetour(Socket socket, Biblioth�que biblioth�que) {
		client = socket;
		ServiceRetour.biblioth�que = biblioth�que;
	}

	@Override
	public void run() {
		String reponse = null;
		try {

			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			
			// Ajouter try catch 
			out.println("Le num�ro de document � retourner :");
			int noDoc = Integer.parseInt(in.readLine());
			
			System.out.println("Requ�te de " + this.client.getInetAddress() + " doc n� " + noDoc);
			boolean verification = biblioth�que.getBiblio().containsKey(noDoc);
			if (verification)
				try {
					biblioth�que.getBiblio().get(noDoc).retour();
					reponse = "Retour r�ussi";

				} catch (RetourException e) {
					reponse = e.toString();
				}
			else
				reponse = "Aucun document ne porte ce num�ro";
			System.out.println(reponse);
			out.println(reponse);
			out.println("Voulez vous arr�ter ? (O/N)");
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
