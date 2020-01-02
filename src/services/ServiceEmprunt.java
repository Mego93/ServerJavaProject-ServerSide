package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import biblioth�que.Biblioth�que;
import exception.EmpruntException;

public class ServiceEmprunt implements Runnable {
	private final Socket client;
	private static Biblioth�que biblioth�que;

	public ServiceEmprunt(Socket socket, Biblioth�que biblioth�que) {
		client = socket;
		ServiceEmprunt.biblioth�que = biblioth�que;
	}

	@Override
	public void run() {
		String reponse = null;
		try {
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			// Ajouter try catch 
			out.println("Votre num�ro d'abonn� : ");
			int noAbo = Integer.parseInt(in.readLine());
			out.println("Le num�ro de document � emprunter :");
			int noDoc = Integer.parseInt(in.readLine());

			System.out
					.println("Requ�te de l'abonn� n�" + noAbo + " pour le document n�" + noDoc + " pour un emprunt (IP:"+this.client.getInetAddress()+")");
			boolean verification = biblioth�que.getAbonn�s().containsKey(noAbo);
			boolean verification2 = biblioth�que.getBiblio().containsKey(noDoc);
			if (!verification)
				reponse = "Aucun abonn� ne porte ce num�ro";
			else if (!verification2)
				reponse = "Aucun document ne porte ce num�ro";
			else {
					try {
						biblioth�que.getBiblio().get(noDoc).emprunter(biblioth�que.getAbonn�s().get(noAbo));
						reponse = "Emprunt du document "+ noDoc +" par l'abonn� "+ noAbo + " r�ussi, il n'est plus disponible � la biblioth�que";

					} catch (EmpruntException e) {
						reponse = e.toString();
					}
				
			}

			System.out.println(reponse);
			out.println(reponse);

		} catch (IOException e) {}

		try {
			client.close();
		} catch (IOException e2) {}
	}

	protected void finalize() throws Throwable {
		client.close();
	}
	
	public void lancer() {
		new Thread(this).start();

	}
}
