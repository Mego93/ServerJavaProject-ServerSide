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

			System.out.println("Requ�te pour le document n�" + noDoc + " pour un retour (IP:"
					+ this.client.getInetAddress() + ")");
			boolean verification = biblioth�que.getBiblio().containsKey(noDoc);
			if (verification)
				try {
					biblioth�que.getBiblio().get(noDoc).retour();
					reponse = "Retour du document " + noDoc + " r�ussi, il est de nouveau disponible � la biblioth�que";

				} catch (RetourException e) {
					reponse = e.toString();
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
